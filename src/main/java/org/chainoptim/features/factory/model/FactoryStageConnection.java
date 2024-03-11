package org.chainoptim.features.factory.model;

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

    @Column(name = "factory_id", nullable = false)
    private Integer factoryId;
}
