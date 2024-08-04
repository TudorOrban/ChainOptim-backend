package org.chainoptim.core.upcomingevents.service;

import org.chainoptim.core.upcomingevents.model.UpcomingEvent;

import java.util.List;

public interface UpcomingEventPersistenceService {

    List<UpcomingEvent> getUpcomingEventsByOrganizationId(Integer organizationId);
}
