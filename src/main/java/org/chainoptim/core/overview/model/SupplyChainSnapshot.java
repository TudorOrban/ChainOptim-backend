package org.chainoptim.core.overview.model;

import org.chainoptim.exception.ValidationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supply_chain_snapshots")
public class SupplyChainSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    // Manual deserialization and caching of JSON column
    @Column(name = "snapshot", columnDefinition = "json")
    private String snapshotJson;

    @Transient // Ignore field
    private Snapshot snapshot;

    public Snapshot getSnapshot() {
        if (this.snapshot == null && this.snapshotJson != null) {
            // Deserialize when accessed
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.snapshot = mapper.readValue(this.snapshotJson, Snapshot.class);
            } catch (JsonProcessingException e) {
                throw new ValidationException("Invalid Snapshot json");
            }
        }
        return this.snapshot;
    }

    public void setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
        // Serialize when setting the object
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.snapshotJson = mapper.writeValueAsString(snapshot);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Snapshot json");
        }
    }

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
