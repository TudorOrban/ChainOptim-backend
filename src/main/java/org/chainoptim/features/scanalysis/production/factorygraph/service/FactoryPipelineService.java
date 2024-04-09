package org.chainoptim.features.scanalysis.production.factorygraph.service;

import org.chainoptim.features.scanalysis.production.factorygraph.model.FactoryGraph;
import org.chainoptim.features.scanalysis.production.factorygraph.model.StageNode;
import org.chainoptim.features.factory.model.Factory;
import org.chainoptim.features.scanalysis.production.factoryconnection.model.FactoryStageConnection;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FactoryPipelineService {

    public FactoryGraph getStageGraph(Factory factory, List<FactoryStageConnection> connections) {
        FactoryGraph graph = new FactoryGraph();

        graph.populateGraph(factory, connections);

        return graph;
    }

    public List<FactoryGraph> splitIntoIndependentPipelines(FactoryGraph factoryGraph) {
        List<StageNode> allNodes = new ArrayList<>(factoryGraph.getNodes().values());
        Set<StageNode> visited = new HashSet<>();
        List<FactoryGraph> independentPipelines = new ArrayList<>();

        List<Integer> sinkNodeIds = allNodes.stream()
                .filter(node -> factoryGraph.getAdjList().getOrDefault(node.getSmallStage().getId(), List.of()).isEmpty())
                .map(node -> node.getSmallStage().getId())
                .toList();

        for (Integer sinkNodeId : sinkNodeIds) {
            if (!visited.contains(factoryGraph.getNodes().get(sinkNodeId))) {
                FactoryGraph subGraph = new FactoryGraph();
                exploreNode(factoryGraph, sinkNodeId, visited, subGraph);
                // Calculate and set pipeline_priority after the subgraph is fully constructed
                float averagePriority = (float) subGraph.getNodes().values().stream()
                        .mapToInt(StageNode::getPriority)
                        .average()
                        .orElse(0); // Use 0 as default if there are no nodes or priorities are null
                subGraph.setPipelinePriority(averagePriority / subGraph.getNodes().size());
                independentPipelines.add(subGraph);
            }
        }

        return independentPipelines;
    }

    private void exploreNode(FactoryGraph factoryGraph, Integer nodeId, Set<StageNode> visited, FactoryGraph subGraph) {
        StageNode currentNode = factoryGraph.getNodes().get(nodeId);
        if (visited.contains(currentNode)) return;
        visited.add(currentNode);
        subGraph.getNodes().put(nodeId, currentNode);

        // Find nodes that connect to this node (backwards traversal)
        factoryGraph.getAdjList().forEach((key, edges) ->
            edges.forEach(edge -> {
                if (edge.getOutgoingFactoryStageId().equals(nodeId)) {
                    // Make sure to check and add the reverse connection if it isn't already present in the subGraph
                    if (!visited.contains(factoryGraph.getNodes().get(key))) {
                        exploreNode(factoryGraph, key, visited, subGraph);
                    }

                    // Add the edge to the subGraph's adjacency list
                    subGraph.getAdjList().computeIfAbsent(nodeId, k -> new ArrayList<>()).add(edge);
                }
            })
        );
    }

    public void sortFactoryGraphNodesByPriority(FactoryGraph factoryGraph) {
        LinkedHashMap<Integer, StageNode> sortedMap = factoryGraph.getNodes().entrySet().stream()
                .sorted((entry1, entry2) -> {
                    Integer priority1 = entry1.getValue().getPriority();
                    Integer priority2 = entry2.getValue().getPriority();

                    // Handle nulls as lowest priority
                    if (priority1 == null && priority2 == null) return 0;
                    if (priority1 == null) return 1;
                    if (priority2 == null) return -1;

                    return priority1.compareTo(priority2);
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new)
                        );

        factoryGraph.setNodes(sortedMap);
    }


}
