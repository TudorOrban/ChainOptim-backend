package org.chainoptim.features.evaluation.production.connection.model;

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

    @Column(name = "outgoing_stage_input_id", nullable = false)
    private Integer outgoingStageInputId;

    @Column(name = "incoming_stage_output_id", nullable = false)
    private Integer incomingStageOutputId;

    @Column(name = "outgoing_factory_stage_id", nullable = false)
    private Integer outgoingFactoryStageId;

    @Column(name = "incoming_factory_stage_id", nullable = false)
    private Integer incomingFactoryStageId;

    @Column(name = "factory_id", nullable = false)
    private Integer factoryId;
}
