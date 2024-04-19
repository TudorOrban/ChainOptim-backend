package org.chainoptim.core.notifications.service;

import org.chainoptim.core.notifications.model.NotificationExtraInfo;
import org.chainoptim.features.product.model.UnitOfMeasurement;
import org.chainoptim.features.productpipeline.model.Component;
import org.chainoptim.features.supplier.model.SupplierOrderEvent;
import org.chainoptim.shared.enums.OrderStatus;
import org.chainoptim.shared.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ExtraInfoFormatterServiceImpl implements ExtraInfoFormatterService {

    public NotificationExtraInfo formatExtraInfo(SupplierOrderEvent orderEvent) {
        NotificationExtraInfo extraInfo = new NotificationExtraInfo();
        List<String> extraMessages = new ArrayList<>();

        if (orderEvent.getNewEntity() == null || orderEvent.getOldEntity() == null) return extraInfo; // No extra info to report

        formatComponentChange(orderEvent, extraMessages);
        formatQuantityChange(orderEvent, extraMessages);
        formatDeliveredQuantityChange(orderEvent, extraMessages);
        formatExpectedDeliveryDateChange(orderEvent, extraMessages);
        formatStatusChange(orderEvent, extraMessages);

        extraInfo.setExtraMessages(extraMessages);
        return extraInfo;
    }

    private void formatComponentChange(SupplierOrderEvent orderEvent, List<String> extraMessages) {
        Component oldComponent = orderEvent.getOldEntity().getComponent();
        Component newComponent = orderEvent.getNewEntity().getComponent();
        if (oldComponent == null || newComponent == null) return; // Do not report component addition/removal

        if (!Objects.equals(oldComponent.getId(), newComponent.getId())) {
            extraMessages.add("The component has been changed from " + oldComponent.getName() + " to " + newComponent.getName() + ".");
        }
    }

    private void formatQuantityChange(SupplierOrderEvent orderEvent, List<String> extraMessages) {
        Component newComponent = orderEvent.getNewEntity().getComponent();
        String unitName = (newComponent != null && newComponent.getUnit() != null) ? newComponent.getUnit().getName() : "units";

        Float oldQuantity = orderEvent.getOldEntity().getQuantity();
        Float newQuantity = orderEvent.getNewEntity().getQuantity();
        if (newQuantity == null) return;
        if (oldQuantity == null) {
            extraMessages.add("The order quantity has been set to " + newQuantity + " " + unitName + ".");
            return;
        }

        float quantityDifference = newQuantity - oldQuantity;
        if (quantityDifference > 0) {
            extraMessages.add("The order quantity has been increased by " + quantityDifference + " " + unitName + ".");
        } else if (quantityDifference < 0) {
            extraMessages.add("The order quantity has been decreased by " + -quantityDifference + " " + unitName + ".");
        }
    }

    private void formatDeliveredQuantityChange(SupplierOrderEvent orderEvent, List<String> extraMessages) {
        Component newComponent = orderEvent.getNewEntity().getComponent();
        String unitName = (newComponent != null && newComponent.getUnit() != null) ? newComponent.getUnit().getName() : "units";

        Float oldDeliveredQuantity = orderEvent.getOldEntity().getDeliveredQuantity();
        Float newDeliveredQuantity = orderEvent.getNewEntity().getDeliveredQuantity();
        if (newDeliveredQuantity == null) return;
        if (oldDeliveredQuantity == null) {
            extraMessages.add("The order delivered quantity has been set to " + newDeliveredQuantity + " " + unitName + ".");
            return;
        }

        float deliveredQuantityDifference = newDeliveredQuantity - oldDeliveredQuantity;
        if (deliveredQuantityDifference > 0) {
            extraMessages.add("The delivered quantity has been increased by " + deliveredQuantityDifference + " " + unitName + ".");
        } else if (deliveredQuantityDifference < 0) {
            extraMessages.add("The delivered quantity has been decreased by " + -deliveredQuantityDifference + " " + unitName + ".");
        }
    }

    private void formatExpectedDeliveryDateChange(SupplierOrderEvent orderEvent, List<String> extraMessages) {
        LocalDateTime oldExpectedDeliveryDate = orderEvent.getOldEntity().getEstimatedDeliveryDate();
        LocalDateTime newExpectedDeliveryDate = orderEvent.getNewEntity().getEstimatedDeliveryDate();
        if (oldExpectedDeliveryDate == null && newExpectedDeliveryDate == null) return;
        if (oldExpectedDeliveryDate == null) {
            extraMessages.add("The order is now expected to be delivered on " + newExpectedDeliveryDate + ".");
            return;
        }
        if (newExpectedDeliveryDate == null) {
            extraMessages.add("The order is no longer expected to be delivered on " + oldExpectedDeliveryDate + ".");
            return;
        }

        Duration deliveryTimeDifference = Duration.between(orderEvent.getOldEntity().getEstimatedDeliveryDate(), orderEvent.getNewEntity().getEstimatedDeliveryDate());
        String formattedDuration = TimeUtil.formatDuration(deliveryTimeDifference.toSeconds() / 86400.0f);
        if (deliveryTimeDifference.isNegative()) {
            extraMessages.add("The order is now expected to be delivered " + formattedDuration + " sooner.");
        } else if (deliveryTimeDifference.isPositive()) {
            extraMessages.add("The order is now expected to be delivered " + formattedDuration + " later.");
        }
    }

    private void formatStatusChange(SupplierOrderEvent orderEvent, List<String> extraMessages) {
        OrderStatus oldStatus = orderEvent.getOldEntity().getStatus();
        OrderStatus newStatus = orderEvent.getNewEntity().getStatus();
        if (oldStatus == null && newStatus == null) return;
        if (oldStatus == null) {
            extraMessages.add("The order status has been set to " + newStatus + ".");
            return;
        }
        if (newStatus == null) {
            extraMessages.add("The order status is no longer " + oldStatus + ".");
            return;
        }

        if (!oldStatus.equals(newStatus)) {
            switch (newStatus) {
                case INITIATED -> extraMessages.add("The order has been initiated.");
                case NEGOTIATED -> extraMessages.add("The order has been negotiated.");
                case PLACED -> extraMessages.add("The order has been placed.");
                case DELIVERED -> extraMessages.add("The order has been delivered.");
                case CANCELED -> extraMessages.add("The order has been canceled.");
            }
        }
    }
}
