package org.chainoptim.core.upcomingevents.service;

import org.chainoptim.core.upcomingevents.model.UpcomingEvent;
import org.chainoptim.shared.search.model.SearchParams;

import java.util.List;

public interface UpcomingEventPersistenceService {

    List<UpcomingEvent> getUpcomingEventsByOrganizationId(Integer organizationId);
    List<UpcomingEvent> getUpcomingEventsAdvanced(Integer organizationId, SearchParams searchParams);
}
