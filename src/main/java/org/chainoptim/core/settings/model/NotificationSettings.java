package org.chainoptim.core.settings.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationSettings {

    private boolean supplierOrdersOn;
    private boolean clientOrdersOn;
    private boolean factoryInventoryOn;
    private boolean warehouseInventoryOn;
    private boolean emailSupplierOrdersOn;
    private boolean emailClientOrdersOn;
    private boolean emailFactoryInventoryOn;
    private boolean emailWarehouseInventoryOn;
}
