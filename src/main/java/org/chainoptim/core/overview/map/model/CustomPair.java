package org.chainoptim.core.overview.map.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomPair<S, T> {

    private S first;
    private T second;
}
