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
