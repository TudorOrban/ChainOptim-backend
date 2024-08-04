package org.chainoptim.core.upcomingevents.service;

import org.chainoptim.core.upcomingevents.model.UpcomingEvent;
import org.chainoptim.core.upcomingevents.repository.UpcomingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpcomingEventPersistenceServiceImpl implements UpcomingEventPersistenceService {

    private final UpcomingEventRepository upcomingEventRepository;

    @Autowired
    public UpcomingEventPersistenceServiceImpl(UpcomingEventRepository upcomingEventRepository) {
        this.upcomingEventRepository = upcomingEventRepository;
    }

    public List<UpcomingEvent> getUpcomingEventsByOrganizationId(Integer organizationId) {
        return upcomingEventRepository.findByOrganizationId(organizationId);
    }

}
