package org.chainoptim.core.upcomingevents.repository;

import org.chainoptim.core.upcomingevents.model.UpcomingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UpcomingEventRepository extends JpaRepository<UpcomingEvent, Integer>, UpcomingEventsSearchRepository {

    List<UpcomingEvent> findByOrganizationId(Integer organizationId);

    @Query("SELECT ue FROM UpcomingEvent ue WHERE ue.dateTime <= :eventDateTime")
    List<UpcomingEvent> findByDateTimeLessThanEqual(@Param("eventDateTime") LocalDateTime eventDateTime);
}
