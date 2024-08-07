CREATE TABLE crates (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    component_id INT NOT NULL,
    quantity DECIMAL NOT NULL,
    volume_m3 DECIMAL,
    stackable BOOL,
    height_m DECIMAL,
    FOREIGN KEY (component_id) REFERENCES components(id)
);

CREATE TABLE compartments (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    warehouse_id INT NOT NULL,
    organization_id INT NOT NULL,
    data_json JSON,
    
    FOREIGN KEY (warehouse_id) REFERENCES warehouses(id),
    FOREIGN KEY (organization_id) REFERENCES organizations(id)
);

INSERT INTO crates(name, component_id, quantity, volume_m3, stackable, height_m)
VALUES ("Cargo Crate", 2, 4.2, 19, true, 1.56);

INSERT INTO compartments(name, warehouse_id, organization_id, data_json)
VALUES ("Comp 1", 1, 1, "{}");

ALTER TABLE crates
ADD COLUMN organization_id INT;

ALTER TABLE crates
ADD FOREIGN KEY (organization_id) REFERENCES organizations(id);

UPDATE `chain_optimizer_schema`.`crates` SET `organization_id` = '1' WHERE (`id` = '1');

ALTER TABLE components
ADD COLUMN unit_of_measurement JSON;

ALTER TABLE factories
ADD COLUMN resource_utilization_score DECIMAL;

ALTER TABLE supplier_shipments
ADD COLUMN dest_warehouse_id INT;

ALTER TABLE supplier_shipments
ADD COLUMN dest_factory_id INT;

ALTER TABLE supplier_shipments
ADD FOREIGN KEY (dest_factory_id) REFERENCES factories(id);

ALTER TABLE stage_outputs
ADD COLUMN product_id INT;

ALTER TABLE stage_outputs
ADD FOREIGN KEY (product_id) REFERENCES products(id);


ALTER TABLE client_shipments
ADD COLUMN src_factory_id INT;

ALTER TABLE client_shipments
ADD FOREIGN KEY (src_factory_id) REFERENCES factories(id);

ALTER TABLE client_shipments
ADD COLUMN src_warehouse_id INT;

ALTER TABLE client_shipments
ADD FOREIGN KEY (src_warehouse_id) REFERENCES warehouses(id);

CREATE TABLE supply_chain_maps (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    organization_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    map_data JSON,
    FOREIGN KEY (organization_id) REFERENCES organizations(id)
);	

CREATE TABLE upcoming_events (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    organization_id INT NOT NULL,
    title VARCHAR(255),
    message TEXT,
    date_time TIMESTAMP,
    associated_entity_id INTEGER,
    associated_entity_type VARCHAR(255),
    FOREIGN KEY (organization_id) REFERENCES organizations(id)
);	

INSERT INTO upcoming_events (organization_id, title, message, date_time, associated_entity_id, associated_entity_type)
VALUES (1, "Outgoing Client Shipment", "The Client Shipment with Company ID #427 leaves Warehouse NewYork.", "2024-08-05 14:22:49", 1, "CLIENT_SHIPMENT");

ALTER TABLE supply_chain_maps 
ADD COLUMN last_refresh TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE supplier_shipments
DROP COLUMN transporter_type;

ALTER TABLE supplier_shipments
ADD COLUMN transport_type ENUM("ROAD", "RAIL", "SEA", "AIR");

ALTER TABLE supplier_shipments
ADD COLUMN departure_date TIMESTAMP;