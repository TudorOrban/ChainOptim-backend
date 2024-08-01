package org.chainoptim.features.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StandardUnit {

    METER("meter", "m", "Length"),
    INCH("inch", "in", "Length"),

    KILOGRAM("gram", "g", "Mass"),
    POUND("pound", "lb", "Mass"),

    LITER("liter", "l", "Volume");

    private final String name;
    private final String abbreviation;
    private final String category;
}
