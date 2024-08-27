package org.chainoptim.features.goods.stage.model;

import org.chainoptim.features.goods.component.model.Component;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stage_outputs")
public class StageOutput {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "stage_id", nullable = false)
    @JsonIgnore
    private Stage stage;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component component;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "quantity")
    private Float quantity;
}
