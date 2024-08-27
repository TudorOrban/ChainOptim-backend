package org.chainoptim.shared.util;

import org.chainoptim.features.goods.unit.model.UnitOfMeasurement;
import org.chainoptim.features.goods.unit.model.StandardUnit;

import org.springframework.data.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class UnitConversionUtil {

    private static final Map<Pair<StandardUnit, StandardUnit>, Double> conversionMap = new HashMap<>();

    static {
        conversionMap.put(Pair.of(StandardUnit.METER, StandardUnit.INCH), 39.3701);
        conversionMap.put(Pair.of(StandardUnit.KILOGRAM, StandardUnit.POUND), 2.20462);
    }

    public static double getConversionFactor(StandardUnit from, StandardUnit to) {
        return conversionMap.getOrDefault(Pair.of(from, to), Double.NaN);
    }

    public static double convert(double value, UnitOfMeasurement from, UnitOfMeasurement to) {
        if (!from.getStandardUnit().getCategory().equals(to.getStandardUnit().getCategory())) {
            throw new IllegalArgumentException("Cannot convert between different categories of units");
        }

        double baseValue = value * from.getUnitMagnitude().getMagnitude();

        double convertedValue = baseValue * getConversionFactor(from.getStandardUnit(), to.getStandardUnit());

        return convertedValue / to.getUnitMagnitude().getMagnitude();
    }
}
