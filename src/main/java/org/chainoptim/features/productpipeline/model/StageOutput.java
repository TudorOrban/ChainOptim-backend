package org.chainoptim.features.productpipeline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stage_outputs")
public class StageOutput {

    @Id
    @Column(updatable = false, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "stage_id", nullable = false)
    @JsonIgnore
    private Stage stage;

    @ManyToOne
    @JoinColumn(name = "component_id", nullable = false)
    private Component component;

    @Column(name = "quantity")
    private Float quantity;
}
