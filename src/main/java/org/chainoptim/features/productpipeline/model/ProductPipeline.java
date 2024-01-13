package org.chainoptim.features.productpipeline.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_pipelines")
public class ProductPipeline {

    @Id
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "stage_id", nullable = false)
    private Integer stageId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;
}
