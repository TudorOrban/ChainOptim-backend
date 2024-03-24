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

INSERT INTO clients (name, organization_id, location_id)
VALUES ("Viva Enterprises", 1, 5);

INSERT INTO client_orders (client_id, product_id, quantity, status)
VALUES (1, 3,  124, 'Initiated');

INSERT INTO client_shipments (client_order_id, quantity, transporter_type, source_location_id, destination_location_id)
VALUES (1, 40, 'Cargo Ship', 5, 6);

ALTER TABLE client_orders
ADD COLUMN organization_id INT NOT NULL;

UPDATE client_orders
SET organization_id = 1 WHERE id = 1;

ALTER TABLE client_orders
ADD FOREIGN KEY (organization_id) REFERENCES organizations(id);

INSERT INTO stages (name, organization_id, product_id)
VALUES ("Another Stage", 1, 6);

INSERT INTO stage_inputs (stage_id, quantity, component_id)
VALUES (9, 124, 4);

INSERT INTO stage_outputs (stage_id, quantity, component_id)
VALUES (9, 153, 5);

DELETE FROM factory_stages
WHERE id > 5;

CREATE TABLE product_production_graphs (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    product_id INT NOT NULL,
    product_graph JSON,
    FOREIGN KEY (product_id) REFERENCES products(id)
);


CREATE TABLE product_stage_connections (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
	outgoing_stage_input_id INT NOT NULL,
	outgoing_stage_id INT NOT NULL,
	incoming_stage_output_id INT NOT NULL,
	incoming_stage_id INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (outgoing_stage_input_id) REFERENCES stage_inputs(id),
    FOREIGN KEY (outgoing_stage_id) REFERENCES stages(id),
    FOREIGN KEY (incoming_stage_output_id) REFERENCES stage_outputs(id),
    FOREIGN KEY (incoming_stage_id) REFERENCES stages(id)
);

INSERT INTO product_stage_connections (product_id, outgoing_stage_input_id, outgoing_stage_id, incoming_stage_output_id, incoming_stage_id)
VALUES (21, 8, 7, 6, 5);

CREATE TABLE custom_roles (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    organization_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    permissions JSON NOT NULL,
    FOREIGN KEY (organization_id) REFERENCES organizations(id)
);

ALTER TABLE users 
ADD COLUMN custom_role_id INT;

ALTER TABLE users
ADD FOREIGN KEY (custom_role_id) REFERENCES custom_roles(id);

INSERT INTO custom_roles (organization_id, name, permissions)
VALUES (1, 'Common Member', '{\"factories\": {\"read\": true, \"create\": false}}');