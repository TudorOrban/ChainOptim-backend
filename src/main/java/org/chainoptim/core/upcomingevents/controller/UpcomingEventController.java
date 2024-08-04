package org.chainoptim.core.upcomingevents.controller;

import org.chainoptim.config.security.SecurityService;
import org.chainoptim.core.upcomingevents.model.UpcomingEvent;
import org.chainoptim.core.upcomingevents.service.UpcomingEventPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
