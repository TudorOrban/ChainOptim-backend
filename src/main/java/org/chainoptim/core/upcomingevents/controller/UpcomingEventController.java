package org.chainoptim.core.upcomingevents.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.core.upcomingevents.model.UpcomingEvent;
import org.chainoptim.core.upcomingevents.service.UpcomingEventPersistenceService;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.shared.enums.SearchMode;
import org.chainoptim.shared.search.model.PaginatedResults;
import org.chainoptim.shared.search.model.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/upcoming-events")
public class UpcomingEventController {

    private final UpcomingEventPersistenceService upcomingEventPersistenceService;
    private final SecurityService securityService;

    @Autowired
    public UpcomingEventController(UpcomingEventPersistenceService upcomingEventPersistenceService,
                                   SecurityService securityService) {
        this.upcomingEventPersistenceService = upcomingEventPersistenceService;
        this.securityService = securityService;
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Organization\", \"Read\")")
    @RequestMapping("/organization/{organizationId}")
    public List<UpcomingEvent> getUpcomingEventsByOrganizationId(@PathVariable Integer organizationId) {
        return upcomingEventPersistenceService.getUpcomingEventsByOrganizationId(organizationId);
    }

    @PreAuthorize("@securityService.canAccessOrganizationEntity(#organizationId, \"Organization\", \"Read\")")
    @GetMapping("/organization/advanced/{organizationId}")
    public ResponseEntity<List<UpcomingEvent>> getUpcomingEventsByOrganizationIdAdvanced(
            @PathVariable Integer organizationId,
            @RequestParam(name = "filters", required = false, defaultValue = "") String filtersJson
    ) {
        SearchParams searchParams = new SearchParams("", filtersJson, null, "", true, 1, 1);
        List<UpcomingEvent> upcomingEvents = upcomingEventPersistenceService.getUpcomingEventsAdvanced(organizationId, searchParams);
        return ResponseEntity.ok(upcomingEvents);
    }
}
