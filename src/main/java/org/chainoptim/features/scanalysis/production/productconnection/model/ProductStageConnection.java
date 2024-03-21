package org.chainoptim.features.scanalysis.production.productconnection.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_stage_connections")
public class ProductStageConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "outgoing_stage_input_id", nullable = false)
    private Integer outgoingStageInputId;

    @Column(name = "incoming_stage_output_id", nullable = false)
    private Integer incomingStageOutputId;

    @Column(name = "outgoing_stage_id", nullable = false)
    private Integer outgoingStageId;

    @Column(name = "incoming_stage_id", nullable = false)
    private Integer incomingStageId;

}
