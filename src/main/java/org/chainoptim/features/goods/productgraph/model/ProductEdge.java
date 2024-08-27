package org.chainoptim.features.goods.productgraph.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEdge {
    Integer srcStageId;
    Integer srcStageOutputId;
    Integer destStageId;
    Integer destStageInputId;
}
