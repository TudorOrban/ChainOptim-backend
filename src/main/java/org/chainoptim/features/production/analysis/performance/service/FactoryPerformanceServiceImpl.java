package org.chainoptim.features.production.analysis.performance.service;

import org.chainoptim.features.production.factorygraph.model.FactoryGraph;
import org.chainoptim.features.production.factorygraph.model.SmallStageInput;
import org.chainoptim.features.production.factorygraph.model.SmallStageOutput;
import org.chainoptim.features.production.factorygraph.model.StageNode;
import org.chainoptim.features.production.analysis.performance.model.FactoryPerformanceReport;
import org.chainoptim.features.production.analysis.performance.model.FactoryStagePerformanceReport;
import org.chainoptim.features.production.analysis.productionhistory.model.DailyProductionRecord;
import org.chainoptim.features.production.analysis.productionhistory.model.FactoryProductionHistory;
import org.chainoptim.features.production.analysis.productionhistory.model.ProductionHistory;
import org.chainoptim.features.production.analysis.resourceallocation.model.AllocationResult;
import org.chainoptim.features.production.analysis.resourceallocation.model.ResourceAllocation;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FactoryPerformanceServiceImpl implements FactoryPerformanceService {

    public FactoryPerformanceReport computeFactoryPerformanceReport(FactoryProductionHistory productionHistory, FactoryGraph factoryGraph) {
        FactoryPerformanceReport factoryPerformanceReport = new FactoryPerformanceReport();

        Map<Integer, Map<Float, DailyProductionRecord>> recordsByStageId = groupRecordsByFactoryStageId(productionHistory.getProductionHistory());

        for (Map.Entry<Integer, Map<Float, DailyProductionRecord>> entry : recordsByStageId.entrySet()) {
            Integer factoryStageId = entry.getKey();
            Map<Float, DailyProductionRecord> records = entry.getValue();

            FactoryStagePerformanceReport stagePerformanceReport = computeFactoryStagePerformanceReport(factoryStageId, records, factoryGraph);
            factoryPerformanceReport.getStageReports().put(factoryStageId, stagePerformanceReport);
        }

        computeFactoryScores(factoryPerformanceReport);

        return factoryPerformanceReport;
    }

    private FactoryStagePerformanceReport computeFactoryStagePerformanceReport(Integer factoryStageId, Map<Float, DailyProductionRecord> productionRecords, FactoryGraph factoryGraph) {
        FactoryStagePerformanceReport factoryStagePerformanceReport = new FactoryStagePerformanceReport();
        factoryStagePerformanceReport.setFactoryStageId(factoryStageId);
        factoryStagePerformanceReport.setStageName(factoryGraph.getNodes().get(factoryStageId).getSmallStage().getStageName());

        float overallScore = 0;
        float resourceReadinessScore = 0;
        float resourceUtilizationScore = 0;
        float errorRate = 0;

        float totalExecutedStages = 0f;
        float totalTimeDays = determineRecordsDuration(productionRecords);
        float averageExecutedStagesPerDay = 0f;
        float minimumExecutedCapacityPerDay = Float.MAX_VALUE;
        float daysUnderCapacityPercentage = 0f;

        for (Map.Entry<Float, DailyProductionRecord> entry : productionRecords.entrySet()) {
            DailyProductionRecord dailyRecord = entry.getValue();
            StageNode stageNode = factoryGraph.getNodes().get(factoryStageId);
            float stageCapacity = stageNode.getNumberOfStepsCapacity() * dailyRecord.getDurationDays();

            float executedStages = determineStageExecutedStages(dailyRecord.getResults(), stageCapacity);
            totalExecutedStages += executedStages;

            if (executedStages < stageCapacity) {
                daysUnderCapacityPercentage++;
            }

            float executedStagesPerDay = executedStages / dailyRecord.getDurationDays();
            averageExecutedStagesPerDay += executedStagesPerDay;

            if (executedStagesPerDay < minimumExecutedCapacityPerDay) {
                minimumExecutedCapacityPerDay = executedStagesPerDay;
            }

            for (ResourceAllocation allocation : dailyRecord.getAllocations()) {
                resourceReadinessScore += allocation.getActualAmount() / allocation.getRequestedAmount();

            }
            resourceReadinessScore /= dailyRecord.getAllocations().size();

            float recordErrorRate = determineStageRecordErrorRate(dailyRecord, stageNode);
            errorRate += recordErrorRate;
        }

        averageExecutedStagesPerDay /= totalTimeDays;
        daysUnderCapacityPercentage /= totalTimeDays * 100;

        errorRate /= productionRecords.size();
        resourceReadinessScore /= productionRecords.size() * 100;
        resourceUtilizationScore = (1 - errorRate) * 100;
        overallScore = (resourceReadinessScore + resourceUtilizationScore) / 2;

        // Prepare the report
        factoryStagePerformanceReport.setOverallScore(overallScore);
        factoryStagePerformanceReport.setResourceReadinessScore(resourceReadinessScore);
        factoryStagePerformanceReport.setResourceUtilizationScore(resourceUtilizationScore);

        factoryStagePerformanceReport.setErrorRate(errorRate);

        factoryStagePerformanceReport.setTotalExecutedStages(totalExecutedStages);
        factoryStagePerformanceReport.setTotalTimeDays(totalTimeDays);
        factoryStagePerformanceReport.setAverageExecutedStagesPerDay(averageExecutedStagesPerDay);
        factoryStagePerformanceReport.setMinimumExecutedCapacityPerDay(minimumExecutedCapacityPerDay);
        factoryStagePerformanceReport.setDaysUnderCapacityPercentage(daysUnderCapacityPercentage);

        return factoryStagePerformanceReport;
    }


    private float determineRecordsDuration(Map<Float, DailyProductionRecord> productionRecords) {
        return productionRecords.values().stream()
                .map(DailyProductionRecord::getDurationDays)
                .reduce(0f, Float::sum);
    }

    private float determineStageExecutedStages(List<AllocationResult> stageResults, float stageCapacity) {
        return stageResults.stream()
                .map(result -> result.getActualAmount() / result.getFullAmount())
                .min(Float::compare).orElse(0.0f)
                * stageCapacity;
    }

    private float determineStageRecordErrorRate(DailyProductionRecord dailyRecord, StageNode stageNode) {
        float errorRate = 0;

        float totalActualInput = dailyRecord.getAllocations().stream()
                .map(ResourceAllocation::getActualAmount)
                .reduce(0.0f, Float::sum);

        float totalInitialOutputs = stageNode.getSmallStage().getStageOutputs().stream()
                .map(SmallStageOutput::getQuantityPerStage)
                .reduce(0.0f, Float::sum);

        float totalInitialInputs = stageNode.getSmallStage().getStageInputs().stream()
                .map(SmallStageInput::getQuantityPerStage)
                .reduce(0.0f, Float::sum);

        float durationRatio = dailyRecord.getDurationDays() / stageNode.getPerDuration();
        float inputOutputRatio = (totalInitialInputs / totalInitialOutputs) * durationRatio;
        float expectedOutput = inputOutputRatio * totalActualInput;

        for (SmallStageOutput output : stageNode.getSmallStage().getStageOutputs()) {
            float outputRatio = output.getQuantityPerStage() / totalInitialOutputs;
            float expectedOutputPerAllocation = expectedOutput * outputRatio;

            AllocationResult correspondingResult = dailyRecord.getResults().stream()
                    .filter(result -> result.getStageOutputId().equals(output.getId()))
                    .findFirst().orElse(null);

            if (correspondingResult != null) {
                float stageOutputError = (expectedOutputPerAllocation - correspondingResult.getActualAmount()) / expectedOutputPerAllocation;
                errorRate += stageOutputError > 0 ? stageOutputError : 0; // Only add positive errors
            }
        }
        errorRate /= stageNode.getSmallStage().getStageOutputs().size();

        return errorRate;
    }

    private void computeFactoryScores(FactoryPerformanceReport factoryPerformanceReport) {
        float overallScore = factoryPerformanceReport.getStageReports().values().stream()
                .map(FactoryStagePerformanceReport::getOverallScore)
                .reduce(0f, Float::sum) / factoryPerformanceReport.getStageReports().size();

        float resourceReadinessScore = factoryPerformanceReport.getStageReports().values().stream()
                .map(FactoryStagePerformanceReport::getResourceReadinessScore)
                .reduce(0f, Float::sum) / factoryPerformanceReport.getStageReports().size();

        float resourceUtilizationScore = factoryPerformanceReport.getStageReports().values().stream()
                .map(FactoryStagePerformanceReport::getResourceUtilizationScore)
                .reduce(0f, Float::sum) / factoryPerformanceReport.getStageReports().size();

        factoryPerformanceReport.setOverallScore(overallScore);
        factoryPerformanceReport.setResourceDistributionScore(0f); // TODO: Implement
        factoryPerformanceReport.setResourceReadinessScore(resourceReadinessScore);
        factoryPerformanceReport.setResourceUtilizationScore(resourceUtilizationScore);
    }

    private Map<Integer, Map<Float, DailyProductionRecord>> groupRecordsByFactoryStageId(ProductionHistory productionHistory) {
        Map<Integer, Map<Float, DailyProductionRecord>> recordsByStageId = new HashMap<>();

        // Get all factory stage IDs
        for (Map.Entry<Float, DailyProductionRecord> entry : productionHistory.getDailyProductionRecords().entrySet()) {
            DailyProductionRecord dailyRecord = entry.getValue();

            for (ResourceAllocation allocation : dailyRecord.getAllocations()) {
                Integer stageId = allocation.getFactoryStageId();

                recordsByStageId.computeIfAbsent(stageId, k -> new HashMap<>());
            }
        }

        // Distribute record allocations and results to each factory stage
        for (Map.Entry<Integer, Map<Float, DailyProductionRecord>> stageEntry : recordsByStageId.entrySet()) {
            Integer factoryStageId = stageEntry.getKey();

            Map<Float, DailyProductionRecord> stageRecords = new HashMap<>();

            for (Map.Entry<Float, DailyProductionRecord> entry : productionHistory.getDailyProductionRecords().entrySet()) {
                Float daysSinceStart = entry.getKey();
                DailyProductionRecord dailyRecord = entry.getValue();

                DailyProductionRecord restrictedRecord = new DailyProductionRecord();
                restrictedRecord.setDurationDays(dailyRecord.getDurationDays());
                restrictedRecord.setAllocations(dailyRecord.getAllocations().stream()
                        .filter(allocation -> allocation.getFactoryStageId().equals(factoryStageId))
                        .toList());
                restrictedRecord.setResults(dailyRecord.getResults().stream()
                        .filter(result -> result.getFactoryStageId().equals(factoryStageId))
                        .toList());

                stageRecords.put(daysSinceStart, restrictedRecord);
            }

            recordsByStageId.put(factoryStageId, stageRecords);
        }

        return recordsByStageId;
    }
}
