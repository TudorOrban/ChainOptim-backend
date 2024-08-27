package org.chainoptim.core.overview.upcomingevents.repository;

import org.chainoptim.core.overview.upcomingevents.model.UpcomingEvent;
import org.chainoptim.shared.search.model.SearchParams;

import java.util.List;

public interface UpcomingEventsSearchRepository {

    List<UpcomingEvent> findByOrganizationIdAdvanced(
            Integer organizationId,
            SearchParams searchParams
    );
}
