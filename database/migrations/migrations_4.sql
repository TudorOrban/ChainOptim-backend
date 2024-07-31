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

ALTER TABLE products
ADD COLUMN unit_of_measurement JSON;
