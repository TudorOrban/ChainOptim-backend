package org.chainoptim.core.notifications.repository;

import org.chainoptim.core.notifications.model.NotificationUser;
import org.chainoptim.shared.search.model.PaginatedResults;

public interface NotificationSearchRepository {

    PaginatedResults<NotificationUser> findByUserIdAdvanced(String userId, String searchQuery, String sortBy, boolean ascending, int page, int itemsPerPage);
}
