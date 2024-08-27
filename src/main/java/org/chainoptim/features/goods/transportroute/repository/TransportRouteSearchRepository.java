package org.chainoptim.features.goods.transportroute.repository;

import org.chainoptim.features.goods.transportroute.model.ResourceTransportRoute;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

public interface TransportRouteSearchRepository {

    PaginatedResults<ResourceTransportRoute> findByOrganizationIdAdvanced(
            Integer organizationId,
            SearchParams searchParams
    );
}
