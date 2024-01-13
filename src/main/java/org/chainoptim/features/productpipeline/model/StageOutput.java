package org.chainoptim.features.productpipeline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    @Column(name = "component_id")
    private Integer componentId;

    @Column(nullable = false)
    private Float quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id", nullable = false)
    @JsonIgnore
    private Stage stage;

}
