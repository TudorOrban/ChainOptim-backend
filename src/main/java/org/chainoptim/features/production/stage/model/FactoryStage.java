package org.chainoptim.features.production.stage.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.chainoptim.features.goods.stage.model.Stage;
import org.chainoptim.features.production.factory.model.Factory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "factory_stages")
public class FactoryStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "capacity")
    private Float capacity;

    @Column(name = "duration")
    private Float duration;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "minimum_required_capacity")
    private Float minimumRequiredCapacity;

    @Column(name = "organization_id")
    private Integer organizationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factory_id", referencedColumnName = "id")
    @JsonIgnore
    private Factory factory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id", referencedColumnName = "id")
    private Stage stage;
}
