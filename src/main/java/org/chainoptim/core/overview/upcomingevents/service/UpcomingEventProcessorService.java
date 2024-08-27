package org.chainoptim.core.overview.upcomingevents.service;

import org.chainoptim.core.overview.upcomingevents.model.UpcomingEvent;
import org.chainoptim.features.demand.clientorder.model.ClientOrder;
import org.chainoptim.features.supply.supplierorder.model.SupplierOrder;

public interface UpcomingEventProcessorService {

    UpcomingEvent processUpcomingEvent(SupplierOrder supplierOrder);
    UpcomingEvent processUpcomingEvent(ClientOrder clientOrder);
}
