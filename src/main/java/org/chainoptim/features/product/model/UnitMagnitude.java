package org.chainoptim.features.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UnitMagnitude {

    MILLI("milli", "m", 0.001f),
    CENTI("centi", "c", 0.01f),
    DECI("deci", "d", 0.1f),
    BASE("base", "", 1f),
    DECA("deca", "da", 10f),
    HECTO("hecto", "h", 100f),
    KILO("kilo", "k", 1000f);

    private final String name;
    private final String abbreviation;
    private final float magnitude;
}
