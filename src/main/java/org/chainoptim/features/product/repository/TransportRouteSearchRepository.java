package org.chainoptim.features.product.repository;

import org.chainoptim.features.product.model.ResourceTransportRoute;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;

public interface TransportRouteSearchRepository {

    PaginatedResults<ResourceTransportRoute> findByOrganizationIdAdvanced(
            Integer organizationId,
            SearchParams searchParams
    );
}
