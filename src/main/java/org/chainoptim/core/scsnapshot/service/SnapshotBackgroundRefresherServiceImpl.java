package org.chainoptim.core.scsnapshot.service;

import org.chainoptim.core.organization.repository.OrganizationRepository;
import org.chainoptim.core.scsnapshot.dto.CreateSnapshotDTO;
import org.chainoptim.core.scsnapshot.dto.UpdateSnapshotDTO;
import org.chainoptim.core.scsnapshot.model.Snapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SnapshotBackgroundRefresherServiceImpl implements SnapshotBackgroundRefresherService {

    private final SnapshotFinderService snapshotFinderService;
    private final SnapshotPersistenceService snapshotPersistenceService;
    private final OrganizationRepository organizationRepository;

    private static final int REFRESH_INTERVAL = 60000 * 60 * 24; // 24 hours

    @Autowired
    public SnapshotBackgroundRefresherServiceImpl(SnapshotFinderService snapshotFinderService,
                                                  SnapshotPersistenceService snapshotPersistenceService,
                                                  OrganizationRepository organizationRepository) {
        this.snapshotFinderService = snapshotFinderService;
        this.snapshotPersistenceService = snapshotPersistenceService;
        this.organizationRepository = organizationRepository;
    }

    @Async
//    @Scheduled(fixedDelay = REFRESH_INTERVAL)
    public void refreshAndSaveSnapshots() {
        List<Integer> organizationIds = organizationRepository.getAllOrganizationIds();

        System.out.println("Refreshing and saving snapshots: " + organizationIds);

        for (Integer organizationId : organizationIds) {
            // Compute the snapshot
            Snapshot snapshot = snapshotFinderService.getSnapshotByOrganizationId(organizationId);

            // Create or update the snapshot
            Optional<Integer> snapshotIdOptional = snapshotPersistenceService.getIdByOrganizationId(organizationId);
            if (snapshotIdOptional.isEmpty()) {
                System.out.println("Creating snapshot for organization ID: " + organizationId);
                snapshotPersistenceService.createSnapshot(new CreateSnapshotDTO(organizationId, snapshot));
            } else {
                System.out.println("Updating snapshot for organization ID: " + organizationId);
                snapshotPersistenceService.updateSnapshot(new UpdateSnapshotDTO(snapshotIdOptional.get(), organizationId, snapshot));
            }
        }

        System.out.println("Finished refreshing and saving snapshots");
    }
}
