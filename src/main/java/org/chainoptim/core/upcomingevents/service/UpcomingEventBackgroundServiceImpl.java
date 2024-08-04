package org.chainoptim.core.upcomingevents.service;

import org.chainoptim.core.upcomingevents.model.UpcomingEvent;
import org.chainoptim.core.upcomingevents.repository.UpcomingEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UpcomingEventBackgroundServiceImpl implements UpcomingEventBackgroundService {

    private final UpcomingEventRepository upcomingEventRepository;
    private static final int REFRESH_INTERVAL = 60000 * 60 * 24; // 24 hours

    @Autowired
    public UpcomingEventBackgroundServiceImpl(UpcomingEventRepository upcomingEventRepository) {
        this.upcomingEventRepository = upcomingEventRepository;
    }

    @Async
//    @Scheduled(fixedDelay = REFRESH_INTERVAL)
    public void deleteOldUpcomingEvents() {
        List<UpcomingEvent> upcomingEvents = upcomingEventRepository.findByDateTimeLessThanEqual(LocalDateTime.now());
        upcomingEventRepository.deleteAll(upcomingEvents);
    }
}
