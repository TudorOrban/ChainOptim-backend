-- Changes -->

CREATE TABLE factory_stage_connections (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    stage_input_id INT REFERENCES stage_inputs(id),
    stage_output_id INT REFERENCES stage_outputs(id)
);

ALTER TABLE factory_stage_connections
ADD FOREIGN KEY (stage_input_id) REFERENCES stage_inputs(id);

ALTER TABLE factory_stage_connections
ADD FOREIGN KEY (stage_output_id) REFERENCES stage_outputs(id);

ALTER TABLE stage_outputs DROP FOREIGN KEY stage_outputs_ibfk_1;
ALTER TABLE stage_outputs DROP INDEX stage_id;
ALTER TABLE stage_outputs ADD CONSTRAINT stage_outputs_ibfk_1 FOREIGN KEY (stage_id) REFERENCES stages(id);

INSERT INTO factory_stage_connections (stage_input_id, stage_output_id) VALUES (3, 4); 
INSERT INTO stage_outputs (stage_id, quantity, component_id) VALUES (5, 21, 2);
INSERT INTO factory_stage_connections (stage_input_id, stage_output_id) VALUES (7, 6);

ALTER TABLE factory_stage_connections
ADD COLUMN factory_id INT NOT NULL;

UPDATE `chain_optimizer_schema`.`factory_stage_connections` SET `factory_id` = '3' WHERE (`id` = '1');
UPDATE `chain_optimizer_schema`.`factory_stage_connections` SET `factory_id` = '3' WHERE (`id` = '2');

ALTER TABLE factory_stage_connections
ADD FOREIGN KEY (factory_id) REFERENCES factories(id);

ALTER TABLE factory_stage_connections
RENAME COLUMN stage_output_id TO incoming_stage_output_id;

ALTER TABLE factory_stages
ADD COLUMN priority INT;

INSERT INTO stages (name, organization_id, product_id)
VALUES ("Stage 2", 1, 21);

INSERT INTO components (name, unit_id, organization_id)
VALUES ("Microphone", 1, 1);
INSERT INTO components (name, unit_id, organization_id)
VALUES ("Camera", 1, 1);

INSERT INTO stage_inputs (stage_id, quantity, component_id)
VALUES (7, 21, 2);
INSERT INTO stage_inputs (stage_id, quantity, component_id)
VALUES (7, 2, 3);
INSERT INTO stage_outputs (stage_id, quantity, component_id)
VALUES (7, 6, 4);
INSERT INTO factory_stages (stage_id, factory_id, capacity, duration, minimum_desired_capacity, minimum_required_capacity, priority)
VALUES (7, 3, 14, 10, 0.9, 0.9, 1);
INSERT INTO factory_stage_connections (outgoing_stage_input_id, incoming_stage_output_id, factory_id)
VALUES (7, 6, 3);

ALTER TABLE factory_stage_connections
ADD COLUMN incoming_stage_id INT NOT NULL;

ALTER TABLE factory_stage_connections
ADD COLUMN outgoing_stage_id INT NOT NULL;

UPDATE `chain_optimizer_schema`.`factory_stage_connections` SET `outgoing_stage_id` = '5', `incoming_stage_id` = '1' WHERE (`id` = '1');
UPDATE `chain_optimizer_schema`.`factory_stage_connections` SET `outgoing_stage_id` = '5', `incoming_stage_id` = '1' WHERE (`id` = '2');
UPDATE `chain_optimizer_schema`.`factory_stage_connections` SET `outgoing_stage_id` = '7', `incoming_stage_id` = '7' WHERE (`id` = '3');

ALTER TABLE factory_stage_connections
RENAME COLUMN incoming_stage_id TO incoming_factory_stage_id;

ALTER TABLE factory_stage_connections
RENAME COLUMN outgoing_stage_id TO outgoing_factory_stage_id;

UPDATE `chain_optimizer_schema`.`factory_stage_connections` SET `outgoing_factory_stage_id` = '4', `incoming_factory_stage_id` = '3' WHERE (`id` = '3');

DELETE FROM factory_stage_connections
WHERE id = 1 OR id = 2;

ALTER TABLE factory_stage_connections
MODIFY COLUMN incoming_factory_stage_id BIGINT;
ALTER TABLE factory_stage_connections
MODIFY COLUMN outgoing_factory_stage_id BIGINT;

ALTER TABLE factory_stage_connections
ADD FOREIGN KEY (incoming_factory_stage_id) REFERENCES factory_stages(id);
ALTER TABLE factory_stage_connections
ADD FOREIGN KEY (outgoing_factory_stage_id) REFERENCES factory_stages(id);

INSERT INTO stages (name, organization_id, product_id)
VALUES ("Stage 3 - Indep", 1, 21);

INSERT INTO components (name, unit_id, organization_id)
VALUES ("Sensor", 1, 1);

INSERT INTO stage_inputs (stage_id, quantity, component_id)
VALUES (8, 40, 4);
INSERT INTO stage_outputs (stage_id, quantity, component_id)
VALUES (8, 60, 5);
INSERT INTO factory_stages (stage_id, factory_id, capacity, duration, minimum_desired_capacity, minimum_required_capacity, priority)
VALUES (8, 3, 20, 12, 0.9, 0.9, 2);

UPDATE `chain_optimizer_schema`.`factory_stages` SET `priority` = '0' WHERE (`id` = '3');
