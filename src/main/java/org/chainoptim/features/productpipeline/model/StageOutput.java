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

    @OneToMany(mappedBy = "stageOutput", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StageOutputComponent> components = new ArrayList<>();
}
