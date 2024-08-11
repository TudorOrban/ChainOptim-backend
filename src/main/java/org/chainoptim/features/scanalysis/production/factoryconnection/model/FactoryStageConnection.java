package org.chainoptim.features.scanalysis.production.factoryconnection.model;

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
@Table(name = "factory_stage_connections")
public class FactoryStageConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "outgoing_factory_stage_id", nullable = false)
    private Integer srcFactoryStageId;

    @Column(name = "outgoing_stage_output_id", nullable = false)
    private Integer srcStageOutputId;

    @Column(name = "incoming_factory_stage_id", nullable = false)
    private Integer destFactoryStageId;

    @Column(name = "incoming_stage_input_id", nullable = false)
    private Integer destStageInputId;

    @Column(name = "factory_id", nullable = false)
    private Integer factoryId;
}
