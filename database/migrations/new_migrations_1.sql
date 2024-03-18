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

DELETE FROM factory_production_graphs WHERE id = 1;

CREATE TABLE clients (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    organization_id INT NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    location_id INT,
    FOREIGN KEY (organization_id) REFERENCES organizations(id),
    FOREIGN KEY (location_id) REFERENCES locations(id)
);

CREATE TABLE client_orders (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    client_id INT NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    product_id INT NOT NULL,
    quantity DECIMAL,
    order_date TIMESTAMP,
    estimated_delivery_date TIMESTAMP,
    delivery_date TIMESTAMP,
    status ENUM('Initiated', 'Negotiated', 'Placed', 'Delivered'),
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);


CREATE TABLE client_shipments (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    client_order_id INT NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    quantity DECIMAL,
    shipment_starting_date TIMESTAMP,
    estimated_arrival_date TIMESTAMP,
    arrival_date TIMESTAMP,
    transporter_type VARCHAR(255),
    status VARCHAR(255),
    source_location_id INT,
    destination_location_id INT,
    current_location_latitude DECIMAL,
    current_location_longitude DECIMAL,
    FOREIGN KEY (client_order_id) REFERENCES client_orders(id),
    FOREIGN KEY (source_location_id) REFERENCES locations(id),
    FOREIGN KEY (destination_location_id) REFERENCES locations(id)
);

