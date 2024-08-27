package org.chainoptim.core.upcomingevents.repository;

import org.chainoptim.core.upcomingevents.model.UpcomingEvent;
import org.chainoptim.shared.search.model.SearchParams;

import java.util.List;

public interface UpcomingEventsSearchRepository {

    List<UpcomingEvent> findByOrganizationIdAdvanced(
            Integer organizationId,
            SearchParams searchParams
    );
}
