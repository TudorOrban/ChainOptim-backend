ALTER TABLE warehouse_inventory_items
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE supplier_orders
ADD COLUMN supplier_warehouse_id INT;

ALTER TABLE supplier_orders
ADD FOREIGN KEY (supplier_warehouse_id) REFERENCES warehouses(id);

ALTER TABLE supplier_orders
RENAME COLUMN supplier_warehouse_id to supplier_delivery_warehouse_id;

ALTER TABLE supplier_orders
ADD COLUMN supplier_delively_factory_id INT;

ALTER TABLE supplier_orders
ADD FOREIGN KEY (supplier_delively_factory_id) REFERENCES factories(id);

ALTER TABLE supplier_orders
RENAME COLUMN supplier_delivery_warehouse_id to delivery_warehouse_id;

ALTER TABLE supplier_orders
RENAME COLUMN supplier_delively_factory_id to delivery_factory_id;

INSERT INTO supplier_shipments (supplier_order_id, quantity, transporter_type, status, source_location_id, destination_location_id)
VALUES (1, 120, 'Ship', 'Initiated', 1, 6);

INSERT INTO supplier_shipments (supplier_order_id, quantity, transporter_type, status, source_location_id, destination_location_id)
VALUES (1, 12000, 'Ship', 'Initiated', 1, 6);

CREATE TABLE factory_production_graphs (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    factory_graph JSON
);

ALTER TABLE factory_production_graphs 
ADD COLUMN factory_id INT;

ALTER TABLE factory_production_graphs
ADD FOREIGN KEY (factory_id) REFERENCES factories(id);

INSERT INTO factory_production_graphs (factory_id, factory_graph)
VALUES (3, '{}');