package org.chainoptim.core.upcomingevents.service;

import org.chainoptim.core.upcomingevents.model.UpcomingEvent;
import org.chainoptim.core.upcomingevents.repository.UpcomingEventRepository;
import org.chainoptim.features.client.model.ClientOrder;
import org.chainoptim.features.supplier.model.SupplierOrder;
import org.chainoptim.shared.enums.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpcomingEventProcessorServiceImpl implements UpcomingEventProcessorService {

    private final UpcomingEventRepository upcomingEventRepository;

    @Autowired
    public UpcomingEventProcessorServiceImpl(UpcomingEventRepository upcomingEventRepository) {
        this.upcomingEventRepository = upcomingEventRepository;
    }

    public UpcomingEvent processUpcomingEvent(SupplierOrder supplierOrder) {
        System.out.println("UpcomingEventProcessorServiceImpl.processUpcomingEvent");
        UpcomingEvent upcomingEvent = new UpcomingEvent();
        upcomingEvent.setAssociatedEntityType(Feature.SUPPLIER_ORDER);

        return processUpcomingEvent(upcomingEvent, supplierOrder.getOrganizationId(), supplierOrder.getCompanyId(), supplierOrder.getId(), supplierOrder.getDeliveryDate());
    }


    public UpcomingEvent processUpcomingEvent(ClientOrder clientOrder) {
        System.out.println("UpcomingEventProcessorServiceImpl.processUpcomingEvent");
        UpcomingEvent upcomingEvent = new UpcomingEvent();
        upcomingEvent.setAssociatedEntityType(Feature.CLIENT_ORDER);

        return processUpcomingEvent(upcomingEvent, clientOrder.getOrganizationId(), clientOrder.getCompanyId(), clientOrder.getId(), clientOrder.getDeliveryDate());
    }

    private UpcomingEvent processUpcomingEvent(UpcomingEvent upcomingEvent,
                                               Integer organizationId, String companyId, Integer orderId, LocalDateTime dateTime) {
        upcomingEvent.setOrganizationId(organizationId);
        upcomingEvent.setAssociatedEntityId(orderId);
        upcomingEvent.setDateTime(dateTime);
        if (upcomingEvent.getAssociatedEntityType() == Feature.SUPPLIER_ORDER) {
            upcomingEvent.setTitle("Incoming Supplier Order");
            upcomingEvent.setMessage("The Supplier Order with Company ID " + companyId + " is due to arrive on " + dateTime);
        } else if (upcomingEvent.getAssociatedEntityType() == Feature.CLIENT_ORDER) {
            upcomingEvent.setTitle("Outgoing Client Order");
            upcomingEvent.setMessage("The Client Order with Company ID " + companyId + " is due to be shipped to the client on " + dateTime);
        }

        upcomingEventRepository.save(upcomingEvent);

        return upcomingEvent;
    }
}
