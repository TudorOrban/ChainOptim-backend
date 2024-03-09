package org.chainoptim.features.factory.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chainoptim.features.productpipeline.model.Stage;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "factory_stages")
public class FactoryStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capacity")
    private Double capacity;

    @Column(name = "duration")
    private Double duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factory_id", referencedColumnName = "id")
    @JsonIgnore
    private Factory factory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    private Stage stage;
}
