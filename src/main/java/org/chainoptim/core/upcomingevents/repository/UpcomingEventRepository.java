package org.chainoptim.core.upcomingevents.repository;

import org.chainoptim.core.upcomingevents.model.UpcomingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UpcomingEventRepository extends JpaRepository<UpcomingEvent, Integer> {

    List<UpcomingEvent> findByOrganizationId(Integer organizationId);
}
