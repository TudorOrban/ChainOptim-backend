package org.chainoptim.core.upcomingevents.service;

import org.chainoptim.core.upcomingevents.model.UpcomingEvent;
import org.chainoptim.features.demand.model.ClientOrder;
import org.chainoptim.features.supply.model.SupplierOrder;

public interface UpcomingEventProcessorService {

    UpcomingEvent processUpcomingEvent(SupplierOrder supplierOrder);
    UpcomingEvent processUpcomingEvent(ClientOrder clientOrder);
}
