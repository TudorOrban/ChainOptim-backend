package org.chainoptim.core.upcomingevents.repository;

import org.chainoptim.core.upcomingevents.model.UpcomingEvent;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

import java.util.List;

public interface UpcomingEventsSearchRepository {

    List<UpcomingEvent> findByOrganizationIdAdvanced(
            Integer organizationId,
            SearchParams searchParams
    );
}
