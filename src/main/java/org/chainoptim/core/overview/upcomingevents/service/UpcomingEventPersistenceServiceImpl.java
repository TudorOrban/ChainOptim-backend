package org.chainoptim.core.overview.upcomingevents.service;

import org.chainoptim.core.overview.upcomingevents.repository.UpcomingEventRepository;
import org.chainoptim.core.overview.upcomingevents.model.UpcomingEvent;
import org.chainoptim.shared.search.model.SearchParams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

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

    public List<UpcomingEvent> getUpcomingEventsAdvanced(Integer organizationId, SearchParams searchParams) {
        // Attempt to parse filters JSON
        Map<String, String> filters;
        if (!searchParams.getFiltersJson().isEmpty()) {
            try {
                filters = new ObjectMapper().readValue(searchParams.getFiltersJson(), new TypeReference<Map<String, String>>(){});
                searchParams.setFilters(filters);
            } catch (JsonProcessingException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filters format");
            }
        }

        return upcomingEventRepository.findByOrganizationIdAdvanced(organizationId, searchParams);
    }

}
