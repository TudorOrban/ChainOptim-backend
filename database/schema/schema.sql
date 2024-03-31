-- MySQL dump 10.13  Distrib 8.2.0, for Win64 (x86_64)
--
-- Host: localhost    Database: chain_optimizer_schema
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `client_orders`
--

DROP TABLE IF EXISTS `client_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client_orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `product_id` int DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `order_date` timestamp NULL DEFAULT NULL,
  `estimated_delivery_date` timestamp NULL DEFAULT NULL,
  `delivery_date` timestamp NULL DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `organization_id` int NOT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `column_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`),
  KEY `product_id` (`product_id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `client_orders_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`),
  CONSTRAINT `client_orders_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `client_orders_ibfk_3` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_orders`
--

LOCK TABLES `client_orders` WRITE;
/*!40000 ALTER TABLE `client_orders` DISABLE KEYS */;
INSERT INTO `client_orders` VALUES (1,1,'2024-03-18 14:44:45','2024-03-18 15:40:37',3,124,NULL,NULL,NULL,'Initiated',1,NULL,NULL),(2,4,'2024-03-27 14:48:07','2024-03-27 14:48:07',NULL,125,NULL,NULL,NULL,NULL,1,'#123',NULL),(3,4,'2024-03-27 14:48:38','2024-03-27 14:48:38',NULL,1125,NULL,NULL,NULL,NULL,1,'#124123',NULL),(5,4,'2024-03-27 13:33:45','2024-03-27 13:42:50',3,1123,NULL,NULL,NULL,NULL,1,NULL,NULL),(6,4,'2024-03-27 13:33:45','2024-03-27 13:42:50',3,5315,NULL,NULL,NULL,NULL,1,NULL,NULL);
/*!40000 ALTER TABLE `client_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_shipments`
--

DROP TABLE IF EXISTS `client_shipments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client_shipments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_order_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `quantity` float DEFAULT NULL,
  `shipment_starting_date` timestamp NULL DEFAULT NULL,
  `estimated_arrival_date` timestamp NULL DEFAULT NULL,
  `arrival_date` timestamp NULL DEFAULT NULL,
  `transporter_type` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `source_location_id` int DEFAULT NULL,
  `destination_location_id` int DEFAULT NULL,
  `current_location_latitude` float DEFAULT NULL,
  `current_location_longitude` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `client_order_id` (`client_order_id`),
  KEY `source_location_id` (`source_location_id`),
  KEY `destination_location_id` (`destination_location_id`),
  CONSTRAINT `client_shipments_ibfk_1` FOREIGN KEY (`client_order_id`) REFERENCES `client_orders` (`id`),
  CONSTRAINT `client_shipments_ibfk_2` FOREIGN KEY (`source_location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `client_shipments_ibfk_3` FOREIGN KEY (`destination_location_id`) REFERENCES `locations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_shipments`
--

LOCK TABLES `client_shipments` WRITE;
/*!40000 ALTER TABLE `client_shipments` DISABLE KEYS */;
INSERT INTO `client_shipments` VALUES (1,1,'2024-03-18 14:46:24','2024-03-18 14:46:24',40,NULL,NULL,NULL,'Cargo Ship',NULL,5,6,NULL,NULL);
/*!40000 ALTER TABLE `client_shipments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `organization_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `location_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `organization_id` (`organization_id`),
  KEY `location_id` (`location_id`),
  CONSTRAINT `clients_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `clients_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'Viva Enterprises',1,'2024-03-18 14:43:01','2024-03-18 16:43:01',5),(2,'BB Enterprises',1,'2024-03-18 14:24:06','2024-03-23 16:43:54',15),(3,'Client Test from JavaFX',1,'2024-03-18 15:11:38','2024-03-18 17:11:38',NULL),(4,'Siame Enterprises',1,'2024-03-21 18:56:51','2024-03-21 20:56:51',11);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `components`
--

DROP TABLE IF EXISTS `components`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `components` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  `unit_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `organization_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `unit_id` (`unit_id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `components_ibfk_1` FOREIGN KEY (`unit_id`) REFERENCES `units_of_measurement` (`id`),
  CONSTRAINT `components_ibfk_2` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `components`
--

LOCK TABLES `components` WRITE;
/*!40000 ALTER TABLE `components` DISABLE KEYS */;
INSERT INTO `components` VALUES (1,'Chip',NULL,1,'2024-01-13 23:08:04','2024-01-13 23:08:04',2),(2,'Test Component 2',NULL,1,'2024-03-09 17:26:50','2024-03-09 17:26:50',1),(3,'Microphone',NULL,1,'2024-03-11 13:19:09','2024-03-11 13:19:09',1),(4,'Camera',NULL,1,'2024-03-11 13:20:01','2024-03-11 13:20:01',1),(5,'Sensor',NULL,1,'2024-03-11 17:13:11','2024-03-11 17:13:11',1),(7,'New Component',NULL,NULL,'2024-03-21 19:57:30','2024-03-21 19:57:30',1),(8,'New Component Without Unit',NULL,1,'2024-03-21 19:57:48','2024-03-21 19:59:12',1);
/*!40000 ALTER TABLE `components` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `custom_roles`
--

DROP TABLE IF EXISTS `custom_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `custom_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `organization_id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `permissions` json NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `custom_roles_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `custom_roles`
--

LOCK TABLES `custom_roles` WRITE;
/*!40000 ALTER TABLE `custom_roles` DISABLE KEYS */;
INSERT INTO `custom_roles` VALUES (1,1,'Common Member','{\"factories\": {\"canRead\": true, \"canCreate\": false}}','2024-03-24 09:26:15','2024-03-24 11:59:42'),(2,1,'Privileged Member','{\"clients\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": false}, \"products\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"factories\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"suppliers\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"warehouses\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": true, \"canUpdate\": false}}','2024-03-24 08:04:10','2024-03-25 16:25:22'),(4,1,'Privileged Member 2','{\"clients\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": true}, \"products\": {\"canRead\": true, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": true}, \"factories\": {\"canRead\": true, \"canCreate\": false, \"canDelete\": true, \"canUpdate\": true}, \"suppliers\": {\"canRead\": true, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": true}, \"warehouses\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": true}}','2024-03-24 11:19:00','2024-03-26 13:14:04'),(16,1,'Custom Admin','{\"clients\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"products\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"factories\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"suppliers\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"warehouses\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}}','2024-03-26 18:52:47','2024-03-26 20:52:59');
/*!40000 ALTER TABLE `custom_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_publication`
--

DROP TABLE IF EXISTS `event_publication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_publication` (
  `id` binary(16) NOT NULL,
  `completion_date` datetime(6) DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  `listener_id` varchar(255) DEFAULT NULL,
  `publication_date` datetime(6) DEFAULT NULL,
  `serialized_event` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_publication`
--

LOCK TABLES `event_publication` WRITE;
/*!40000 ALTER TABLE `event_publication` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_publication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factories`
--

DROP TABLE IF EXISTS `factories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `location_id` int DEFAULT NULL,
  `organization_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `location_id` (`location_id`),
  KEY `FKronhjti6859iso27solqwxf6l` (`organization_id`),
  CONSTRAINT `factories_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `FKronhjti6859iso27solqwxf6l` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factories`
--

LOCK TABLES `factories` WRITE;
/*!40000 ALTER TABLE `factories` DISABLE KEYS */;
INSERT INTO `factories` VALUES (1,'NJ Factory 1',1,2,'2024-03-04 21:07:24','2024-03-04 23:07:24'),(2,'NJ Factory 2',1,2,'2024-03-04 21:07:24','2024-03-04 23:07:24'),(3,'New York Factory 1',12,1,'2024-03-06 09:26:51','2024-03-23 15:58:42'),(4,'China factory MegaF',5,1,'2024-03-06 09:32:38','2024-03-06 11:32:38'),(5,'Tokio factory',6,1,'2024-03-06 09:32:58','2024-03-06 11:32:58'),(6,'Chicago factory',4,1,'2024-03-06 09:33:21','2024-03-06 11:33:21'),(9,'New Factory Test Sanitization',NULL,1,'2024-03-10 13:23:20','2024-03-10 15:23:20'),(10,'New Factory Test Sanitization',1,1,'2024-03-10 13:28:05','2024-03-10 15:28:05'),(11,'SS Factory',5,1,'2024-03-21 19:05:10','2024-03-21 21:05:10');
/*!40000 ALTER TABLE `factories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factory_inventory_items`
--

DROP TABLE IF EXISTS `factory_inventory_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factory_inventory_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `factory_id` int NOT NULL,
  `raw_material_id` int DEFAULT NULL,
  `component_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  `minimum_required_quantity` double DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `factory_id` (`factory_id`),
  KEY `raw_material_id` (`raw_material_id`),
  KEY `component_id` (`component_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `factory_inventory_items_ibfk_1` FOREIGN KEY (`factory_id`) REFERENCES `factories` (`id`),
  CONSTRAINT `factory_inventory_items_ibfk_2` FOREIGN KEY (`raw_material_id`) REFERENCES `raw_materials` (`id`),
  CONSTRAINT `factory_inventory_items_ibfk_3` FOREIGN KEY (`component_id`) REFERENCES `components` (`id`),
  CONSTRAINT `factory_inventory_items_ibfk_4` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factory_inventory_items`
--

LOCK TABLES `factory_inventory_items` WRITE;
/*!40000 ALTER TABLE `factory_inventory_items` DISABLE KEYS */;
INSERT INTO `factory_inventory_items` VALUES (1,3,NULL,1,NULL,231,1421,'2024-03-09 13:28:41','2024-03-09 15:28:41'),(3,3,NULL,2,1,2391,141,'2024-03-09 13:29:04','2024-03-09 22:51:40'),(4,3,NULL,3,2,211,1400,'2024-03-09 13:29:13','2024-03-11 23:46:46'),(5,3,NULL,4,3,91,414000,'2024-03-09 13:29:25','2024-03-11 23:46:46'),(9,3,NULL,NULL,3,200.5,214.12,'2024-03-09 12:33:03','2024-03-09 14:33:03');
/*!40000 ALTER TABLE `factory_inventory_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factory_production_graphs`
--

DROP TABLE IF EXISTS `factory_production_graphs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factory_production_graphs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `factory_graph` json DEFAULT NULL,
  `factory_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `factory_id` (`factory_id`),
  CONSTRAINT `factory_production_graphs_ibfk_1` FOREIGN KEY (`factory_id`) REFERENCES `factories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factory_production_graphs`
--

LOCK TABLES `factory_production_graphs` WRITE;
/*!40000 ALTER TABLE `factory_production_graphs` DISABLE KEYS */;
INSERT INTO `factory_production_graphs` VALUES (3,'2024-03-14 08:34:54','2024-03-20 22:38:30','{\"nodes\": {\"3\": {\"priority\": 0, \"smallStage\": {\"id\": 3, \"stageName\": \"Stage 1\", \"stageInputs\": [{\"id\": 7, \"componentId\": 2, \"quantityPerStage\": 12.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}, {\"id\": 3, \"componentId\": 1, \"quantityPerStage\": 214.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 6, \"componentId\": 2, \"outputPerRequest\": null, \"quantityPerStage\": 21.0, \"expectedOutputPerAllocation\": null}, {\"id\": 4, \"componentId\": 1, \"outputPerRequest\": null, \"quantityPerStage\": 12.0, \"expectedOutputPerAllocation\": null}]}, \"perDuration\": 211.41, \"numberOfStepsCapacity\": 144.14, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": null}, \"4\": {\"priority\": 1, \"smallStage\": {\"id\": 4, \"stageName\": \"Stage 2\", \"stageInputs\": [{\"id\": 9, \"componentId\": 3, \"quantityPerStage\": 2.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}, {\"id\": 8, \"componentId\": 2, \"quantityPerStage\": 21.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 7, \"componentId\": 4, \"outputPerRequest\": null, \"quantityPerStage\": 6.0, \"expectedOutputPerAllocation\": null}]}, \"perDuration\": 10.0, \"numberOfStepsCapacity\": 14.0, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": 0.9}, \"5\": {\"priority\": 2, \"smallStage\": {\"id\": 5, \"stageName\": \"Stage 3 - Indep\", \"stageInputs\": [{\"id\": 10, \"componentId\": 4, \"quantityPerStage\": 40.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 8, \"componentId\": 5, \"outputPerRequest\": null, \"quantityPerStage\": 60.0, \"expectedOutputPerAllocation\": null}]}, \"perDuration\": 12.0, \"numberOfStepsCapacity\": 20.0, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": 0.9}, \"37\": {\"priority\": 10, \"smallStage\": {\"id\": 37, \"stageName\": \"Another Stage\", \"stageInputs\": [{\"id\": 11, \"componentId\": 4, \"quantityPerStage\": 124.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 9, \"componentId\": 5, \"outputPerRequest\": null, \"quantityPerStage\": 153.0, \"expectedOutputPerAllocation\": null}]}, \"perDuration\": 64800.0, \"numberOfStepsCapacity\": 21.0, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": 22.0}, \"38\": {\"priority\": 12, \"smallStage\": {\"id\": 38, \"stageName\": \"Create Stage Test\", \"stageInputs\": [], \"stageOutputs\": []}, \"perDuration\": 1123200.0, \"numberOfStepsCapacity\": 312.0, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": 312.0}}, \"adjList\": {\"3\": [{\"outgoingStageInputId\": 8, \"incomingStageOutputId\": 6, \"incomingFactoryStageId\": 3, \"outgoingFactoryStageId\": 4}], \"4\": [], \"5\": [], \"37\": [], \"38\": []}, \"pipelinePriority\": null}',3),(4,'2024-03-18 13:55:53','2024-03-18 15:55:53','{\"nodes\": {\"3\": {\"priority\": 0, \"smallStage\": {\"id\": 3, \"stageName\": \"Stage 1\", \"stageInputs\": [{\"id\": 3, \"componentId\": 1, \"quantityPerStage\": 214.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}, {\"id\": 7, \"componentId\": 2, \"quantityPerStage\": 12.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 6, \"componentId\": 2, \"outputPerRequest\": null, \"quantityPerStage\": 21.0, \"expectedOutputPerAllocation\": null}, {\"id\": 4, \"componentId\": 1, \"outputPerRequest\": null, \"quantityPerStage\": 12.0, \"expectedOutputPerAllocation\": null}]}, \"perDuration\": 211.41, \"numberOfStepsCapacity\": 144.14, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": null}, \"4\": {\"priority\": 1, \"smallStage\": {\"id\": 4, \"stageName\": \"Stage 2\", \"stageInputs\": [{\"id\": 8, \"componentId\": 2, \"quantityPerStage\": 21.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}, {\"id\": 9, \"componentId\": 3, \"quantityPerStage\": 2.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 7, \"componentId\": 4, \"outputPerRequest\": null, \"quantityPerStage\": 6.0, \"expectedOutputPerAllocation\": null}]}, \"perDuration\": 10.0, \"numberOfStepsCapacity\": 14.0, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": 0.9}, \"5\": {\"priority\": 2, \"smallStage\": {\"id\": 5, \"stageName\": \"Stage 3 - Indep\", \"stageInputs\": [{\"id\": 10, \"componentId\": 4, \"quantityPerStage\": 40.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 8, \"componentId\": 5, \"outputPerRequest\": null, \"quantityPerStage\": 60.0, \"expectedOutputPerAllocation\": null}]}, \"perDuration\": 12.0, \"numberOfStepsCapacity\": 20.0, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": 0.9}}, \"adjList\": {\"3\": [{\"outgoingStageInputId\": 8, \"incomingStageOutputId\": 6, \"incomingFactoryStageId\": 3, \"outgoingFactoryStageId\": 4}], \"4\": [], \"5\": []}, \"pipelinePriority\": null}',3);
/*!40000 ALTER TABLE `factory_production_graphs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factory_stage_connections`
--

DROP TABLE IF EXISTS `factory_stage_connections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factory_stage_connections` (
  `id` int NOT NULL AUTO_INCREMENT,
  `outgoing_stage_input_id` int DEFAULT NULL,
  `incoming_stage_output_id` int DEFAULT NULL,
  `factory_id` int NOT NULL,
  `outgoing_factory_stage_id` bigint DEFAULT NULL,
  `incoming_factory_stage_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stage_input_id` (`outgoing_stage_input_id`),
  KEY `stage_output_id` (`incoming_stage_output_id`),
  KEY `factory_id` (`factory_id`),
  KEY `incoming_factory_stage_id` (`incoming_factory_stage_id`),
  KEY `outgoing_factory_stage_id` (`outgoing_factory_stage_id`),
  CONSTRAINT `factory_stage_connections_ibfk_1` FOREIGN KEY (`outgoing_stage_input_id`) REFERENCES `stage_inputs` (`id`),
  CONSTRAINT `factory_stage_connections_ibfk_2` FOREIGN KEY (`incoming_stage_output_id`) REFERENCES `stage_outputs` (`id`),
  CONSTRAINT `factory_stage_connections_ibfk_3` FOREIGN KEY (`factory_id`) REFERENCES `factories` (`id`),
  CONSTRAINT `factory_stage_connections_ibfk_4` FOREIGN KEY (`incoming_factory_stage_id`) REFERENCES `factory_stages` (`id`),
  CONSTRAINT `factory_stage_connections_ibfk_5` FOREIGN KEY (`outgoing_factory_stage_id`) REFERENCES `factory_stages` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factory_stage_connections`
--

LOCK TABLES `factory_stage_connections` WRITE;
/*!40000 ALTER TABLE `factory_stage_connections` DISABLE KEYS */;
INSERT INTO `factory_stage_connections` VALUES (3,8,6,3,4,3);
/*!40000 ALTER TABLE `factory_stage_connections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factory_stages`
--

DROP TABLE IF EXISTS `factory_stages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factory_stages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `factory_id` int NOT NULL,
  `stage_id` int NOT NULL,
  `capacity` double DEFAULT NULL,
  `duration` double DEFAULT NULL,
  `minimum_desired_capacity` decimal(10,0) DEFAULT NULL,
  `minimum_required_capacity` float DEFAULT NULL,
  `priority` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `factory_id` (`factory_id`),
  KEY `stage_id` (`stage_id`),
  CONSTRAINT `factory_stages_ibfk_1` FOREIGN KEY (`factory_id`) REFERENCES `factories` (`id`),
  CONSTRAINT `factory_stages_ibfk_2` FOREIGN KEY (`stage_id`) REFERENCES `stages` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factory_stages`
--

LOCK TABLES `factory_stages` WRITE;
/*!40000 ALTER TABLE `factory_stages` DISABLE KEYS */;
INSERT INTO `factory_stages` VALUES (3,3,5,144.14,211.41,1,NULL,0),(4,3,7,14,10,1,0.9,1),(5,3,8,20,12,1,0.9,2),(37,3,9,21,64800,NULL,22,10),(38,3,10,312,1123200,NULL,312,12);
/*!40000 ALTER TABLE `factory_stages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `organization_id` int DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_organization_id` (`organization_id`),
  CONSTRAINT `fk_organization_id` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` VALUES (1,'Hemingway Boulevard no. 13','New Jersey','New Jersey','USA',21.12152,41.6325,1,'142142'),(2,'Hemingway Boulevard no. 13','New Jersey','New Jersey','USA',21.12152,41.6325,2,'142142'),(3,'New Way Boulevard no 2','LA','California','USA',12.14215,53.1241,1,'142134'),(4,'Freemont Boulevard no 2','Chicago','Illinois','USA',19.14215,40.1241,1,'142134'),(5,'HK street no 4','Shenzen','Shenzen','China',70.14215,10.1241,1,'142134'),(6,'Ichio street no 4','Tokio',NULL,'Japan',90.14215,12.1241,1,'142134'),(8,'Strada Avram Iancu no. 20','Bucharest',NULL,'Romania',80.1,41.4,1,'077081'),(9,'Churchill Boulevard no. 211','London','','UK',12.41,51.14,1,'12151'),(10,'','Hannover','','Germany',NULL,NULL,1,''),(11,'La View Boulevard','Paris','','France',12.42,NULL,1,'01204'),(12,'JFK Boulevard no. 20','New York','New York','USA',12.31,41.21,1,'012041'),(13,'Herbrew Street','Jerusalem','','Israel',NULL,NULL,1,''),(14,'Templeton Street, no 20','Chicago Downtown','Illinois','USA',NULL,NULL,1,'13252'),(15,'Heinburg Dan','Innsbruck','','Switzerland',1.21,90.12,1,'12352');
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification_users`
--

DROP TABLE IF EXISTS `notification_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification_users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `notification_id` int NOT NULL,
  `user_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `read_status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `notification_id` (`notification_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `notification_users_ibfk_1` FOREIGN KEY (`notification_id`) REFERENCES `notifications` (`id`),
  CONSTRAINT `notification_users_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_users`
--

LOCK TABLES `notification_users` WRITE;
/*!40000 ALTER TABLE `notification_users` DISABLE KEYS */;
INSERT INTO `notification_users` VALUES (1,1,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(2,2,'086e9e96-a8ef-11ee-bffa-00155de90539',1);
/*!40000 ALTER TABLE `notification_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `entity_id` int DEFAULT NULL,
  `entity_type` varchar(255) DEFAULT NULL,
  `message` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `read_status` tinyint(1) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,'Supplier Order 1 Alert',21,'Factory','The Supplier Order 1 has arrived at the Factory','2024-03-28 22:18:36','2024-03-29 00:18:36',0,'Alert'),(2,'Client Order #213 Alert',2,'Client Order','The Client Akia Motors has published an order','2024-03-28 22:20:51','2024-03-29 00:20:51',0,'Alert');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `organization_id` int NOT NULL,
  `product_id` int NOT NULL,
  `order_status` varchar(255) DEFAULT NULL,
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `delivery_date` timestamp NULL DEFAULT NULL,
  `order_date_price` float DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_organization_orders` (`organization_id`),
  KEY `fk_product_orders` (`product_id`),
  CONSTRAINT `fk_organization_orders` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `fk_product_orders` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization_invites`
--

DROP TABLE IF EXISTS `organization_invites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization_invites` (
  `id` int NOT NULL AUTO_INCREMENT,
  `organization_id` int NOT NULL,
  `inviter_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `invitee_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `organization_id` (`organization_id`),
  KEY `inviter_id` (`inviter_id`),
  KEY `invitee_id` (`invitee_id`),
  CONSTRAINT `organization_invites_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `organization_invites_ibfk_2` FOREIGN KEY (`inviter_id`) REFERENCES `users` (`id`),
  CONSTRAINT `organization_invites_ibfk_3` FOREIGN KEY (`invitee_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_invites`
--

LOCK TABLES `organization_invites` WRITE;
/*!40000 ALTER TABLE `organization_invites` DISABLE KEYS */;
INSERT INTO `organization_invites` VALUES (1,1,'bc3dfdde-a8ef-11ee-bffa-00155de90539','43b836ac-a8e8-11ee-bffa-00155de90539',0,'2024-01-04 21:59:10'),(3,3,'43b836ac-a8e8-11ee-bffa-00155de90539','bc3dfdde-a8ef-11ee-bffa-00155de90539',0,'2024-01-05 10:58:28'),(4,5,'322f2f9c-8b7a-4274-942c-13536394f1b8','04badd68-29b7-4798-853b-a70cb727ac77',0,'2024-01-05 11:05:49'),(5,6,'04badd68-29b7-4798-853b-a70cb727ac77','322f2f9c-8b7a-4274-942c-13536394f1b8',0,'2024-01-05 11:18:48'),(6,10,'430f3a7b-6d9b-411e-95a3-eb794ba3c430','322f2f9c-8b7a-4274-942c-13536394f1b8',0,'2024-01-05 11:28:00'),(7,16,'43b836ac-a8e8-11ee-bffa-00155de90539','322f2f9c-8b7a-4274-942c-13536394f1b8',0,'2024-01-05 13:15:15'),(8,18,'43b836ac-a8e8-11ee-bffa-00155de90539','9af23564-a8c1-11ee-bffa-00155de90539',0,'2024-02-16 07:21:16');
/*!40000 ALTER TABLE `organization_invites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization_requests`
--

DROP TABLE IF EXISTS `organization_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization_requests` (
  `id` int NOT NULL AUTO_INCREMENT,
  `organization_id` int NOT NULL,
  `requester_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `request_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `organization_id` (`organization_id`),
  KEY `requester_id` (`requester_id`),
  CONSTRAINT `organization_requests_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `organization_requests_ibfk_2` FOREIGN KEY (`requester_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_requests`
--

LOCK TABLES `organization_requests` WRITE;
/*!40000 ALTER TABLE `organization_requests` DISABLE KEYS */;
INSERT INTO `organization_requests` VALUES (1,1,'9af23564-a8c1-11ee-bffa-00155de90539',1,'2024-01-04 22:00:10','');
/*!40000 ALTER TABLE `organization_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizations`
--

DROP TABLE IF EXISTS `organizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `contact_info` varchar(255) DEFAULT NULL,
  `subscription_plan` enum('NONE','BASIC','PRO') NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizations`
--

LOCK TABLES `organizations` WRITE;
/*!40000 ALTER TABLE `organizations` DISABLE KEYS */;
INSERT INTO `organizations` VALUES (1,'Bourbonki','Calea Victoriei nr 2','tudororban5@gmail.com','PRO','2024-01-02 20:48:32','2024-01-04 11:36:02'),(2,'Apple','San Francisco','stevejobs@gmail.com','BASIC','2024-01-04 20:02:55','2024-01-04 20:02:55'),(3,'ExxonMobil','Houston, Texas, USA',NULL,'PRO','2024-01-05 10:58:27','2024-01-05 10:58:27'),(5,'Microsoft','San Francisco, California, USA',NULL,'PRO','2024-01-05 11:05:49','2024-01-05 11:05:49'),(6,'Microsoft','San Francisco, California, USA',NULL,'PRO','2024-01-05 11:18:48','2024-01-05 11:18:48'),(10,'NVIDIA','San Francisco, California, USA',NULL,'PRO','2024-01-05 11:28:00','2024-01-05 11:28:00'),(12,'Huawei','Shenzen','','NONE','2024-01-05 12:23:06','2024-01-05 12:23:06'),(16,'TestUsers','Shenzen','0722415124','NONE','2024-01-05 13:15:15','2024-01-05 13:15:15'),(17,'','','','NONE','2024-01-05 13:47:04','2024-01-05 13:47:04'),(18,'New-Test-Organization','Address',NULL,'PRO','2024-02-16 07:21:16','2024-02-16 07:21:16');
/*!40000 ALTER TABLE `organizations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_pipelines`
--

DROP TABLE IF EXISTS `product_pipelines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_pipelines` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `stage_id` int NOT NULL,
  `order_index` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `stage_id` (`stage_id`),
  CONSTRAINT `product_pipelines_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `product_pipelines_ibfk_2` FOREIGN KEY (`stage_id`) REFERENCES `stages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_pipelines`
--

LOCK TABLES `product_pipelines` WRITE;
/*!40000 ALTER TABLE `product_pipelines` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_pipelines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_production_graphs`
--

DROP TABLE IF EXISTS `product_production_graphs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_production_graphs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `product_id` int NOT NULL,
  `product_graph` json DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_production_graphs_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_production_graphs`
--

LOCK TABLES `product_production_graphs` WRITE;
/*!40000 ALTER TABLE `product_production_graphs` DISABLE KEYS */;
INSERT INTO `product_production_graphs` VALUES (1,'2024-03-20 18:10:32','2024-03-21 10:07:49',21,'{\"nodes\": {\"5\": {\"id\": 5, \"stageName\": \"Stage 1\", \"stageInputs\": [{\"id\": 3, \"componentId\": 1, \"quantityPerStage\": 214.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}, {\"id\": 7, \"componentId\": 2, \"quantityPerStage\": 12.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 4, \"componentId\": 1, \"outputPerRequest\": null, \"quantityPerStage\": 12.0, \"expectedOutputPerAllocation\": null}, {\"id\": 6, \"componentId\": 2, \"outputPerRequest\": null, \"quantityPerStage\": 21.0, \"expectedOutputPerAllocation\": null}]}, \"7\": {\"id\": 7, \"stageName\": \"Stage 2\", \"stageInputs\": [{\"id\": 8, \"componentId\": 2, \"quantityPerStage\": 21.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}, {\"id\": 9, \"componentId\": 3, \"quantityPerStage\": 2.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 7, \"componentId\": 4, \"outputPerRequest\": null, \"quantityPerStage\": 6.0, \"expectedOutputPerAllocation\": null}]}, \"8\": {\"id\": 8, \"stageName\": \"Stage 3 - Indep\", \"stageInputs\": [{\"id\": 10, \"componentId\": 4, \"quantityPerStage\": 40.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 8, \"componentId\": 5, \"outputPerRequest\": null, \"quantityPerStage\": 60.0, \"expectedOutputPerAllocation\": null}]}, \"10\": {\"id\": 10, \"stageName\": \"Create Stage Test\", \"stageInputs\": [], \"stageOutputs\": []}, \"11\": {\"id\": 11, \"stageName\": \"New Test\", \"stageInputs\": [], \"stageOutputs\": []}, \"12\": {\"id\": 12, \"stageName\": \"Another another test\", \"stageInputs\": [], \"stageOutputs\": []}, \"13\": {\"id\": 13, \"stageName\": \"Another new new test\", \"stageInputs\": [], \"stageOutputs\": []}, \"14\": {\"id\": 14, \"stageName\": \"EEEEnew new test\", \"stageInputs\": [], \"stageOutputs\": []}}, \"adjList\": {\"5\": [{\"incomingStageId\": 5, \"outgoingStageId\": 7, \"outgoingStageInputId\": 8, \"incomingStageOutputId\": 6}], \"7\": [], \"8\": [], \"10\": [], \"11\": [], \"12\": [], \"13\": [], \"14\": []}}');
/*!40000 ALTER TABLE `product_production_graphs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_stage_connections`
--

DROP TABLE IF EXISTS `product_stage_connections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_stage_connections` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `outgoing_stage_input_id` int NOT NULL,
  `outgoing_stage_id` int NOT NULL,
  `incoming_stage_output_id` int NOT NULL,
  `incoming_stage_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `outgoing_stage_input_id` (`outgoing_stage_input_id`),
  KEY `outgoing_stage_id` (`outgoing_stage_id`),
  KEY `incoming_stage_output_id` (`incoming_stage_output_id`),
  KEY `incoming_stage_id` (`incoming_stage_id`),
  CONSTRAINT `product_stage_connections_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `product_stage_connections_ibfk_2` FOREIGN KEY (`outgoing_stage_input_id`) REFERENCES `stage_inputs` (`id`),
  CONSTRAINT `product_stage_connections_ibfk_3` FOREIGN KEY (`outgoing_stage_id`) REFERENCES `stages` (`id`),
  CONSTRAINT `product_stage_connections_ibfk_4` FOREIGN KEY (`incoming_stage_output_id`) REFERENCES `stage_outputs` (`id`),
  CONSTRAINT `product_stage_connections_ibfk_5` FOREIGN KEY (`incoming_stage_id`) REFERENCES `stages` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_stage_connections`
--

LOCK TABLES `product_stage_connections` WRITE;
/*!40000 ALTER TABLE `product_stage_connections` DISABLE KEYS */;
INSERT INTO `product_stage_connections` VALUES (1,21,8,7,6,5);
/*!40000 ALTER TABLE `product_stage_connections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `organization_id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `unit_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_organization_products` (`organization_id`),
  KEY `FKcgg9ayc3i1f44ktralk68qq0x` (`unit_id`),
  CONSTRAINT `fk_organization_products` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `FKcgg9ayc3i1f44ktralk68qq0x` FOREIGN KEY (`unit_id`) REFERENCES `units_of_measurement` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,2,'Iphone 21','The newest version of our product','2024-01-13 20:22:49','2024-01-13 20:22:49',NULL),(2,2,'Iphone 10','Old product','2024-01-13 20:23:15','2024-01-13 20:23:15',NULL),(3,1,'Samsung','New phone','2024-02-23 10:02:02','2024-02-23 10:02:02',NULL),(4,1,'Iphone 21','Another new phone','2024-02-28 09:07:56','2024-02-28 09:07:56',NULL),(5,1,'Pocophone','Brand new phone','2024-02-28 09:08:20','2024-02-28 09:08:20',NULL),(6,1,'Google Pixel 6','','2024-02-28 09:08:31','2024-02-28 09:08:31',NULL),(7,1,'Motorolla v1','New version','2024-03-05 16:09:01','2024-03-05 16:09:01',NULL),(8,1,'Blackberry 5','','2024-03-05 16:09:19','2024-03-05 16:09:19',NULL),(9,1,'Iphone 18','Mid-size','2024-03-05 16:09:54','2024-03-05 16:09:54',NULL),(11,1,'Test product update 2',NULL,'2024-03-08 12:47:03','2024-03-08 12:50:42',1),(13,1,'test name','test','2024-03-08 20:26:05','2024-03-08 20:26:05',1),(14,1,'test name','test','2024-03-09 06:46:48','2024-03-09 06:46:48',1),(15,1,'test Product','test','2024-03-09 06:51:02','2024-03-09 06:51:02',1),(16,1,'test Product','test','2024-03-09 06:52:59','2024-03-09 06:52:59',1),(17,1,'test Product','test','2024-03-09 06:54:25','2024-03-09 06:54:25',1),(18,1,'test Product','test','2024-03-09 06:56:46','2024-03-09 06:56:46',1),(19,1,'eqweqwe','qweqweqw','2024-03-09 07:00:23','2024-03-09 07:00:23',1),(20,1,'Create Test Product','Test of the createproduct','2024-03-09 07:22:16','2024-03-09 07:22:16',1),(21,1,'Test pipeline',NULL,'2024-03-09 17:16:17','2024-03-09 17:16:17',1),(22,1,'TestPr','Test','2024-03-16 14:41:23','2024-03-16 14:41:23',1),(23,1,'Alluminum',NULL,'2024-03-22 06:47:57','2024-03-22 06:47:57',5),(24,1,'Copper','Metal','2024-03-22 06:50:48','2024-03-22 06:50:48',NULL),(25,1,'TestCreateUnit','','2024-03-22 06:56:55','2024-03-22 06:56:55',NULL),(26,1,'Product with New Unit','New Unit Test','2024-03-22 07:00:20','2024-03-22 07:00:20',NULL),(27,1,'Another Create unit test','Test Create Unit','2024-03-22 07:01:57','2024-03-22 07:01:57',6),(28,1,'Test New Product','','2024-03-23 07:58:01','2024-03-23 07:58:01',6);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `raw_materials`
--

DROP TABLE IF EXISTS `raw_materials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `raw_materials` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  `unit_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `organization_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `unit_id` (`unit_id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `raw_materials_ibfk_1` FOREIGN KEY (`unit_id`) REFERENCES `units_of_measurement` (`id`),
  CONSTRAINT `raw_materials_ibfk_2` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `raw_materials`
--

LOCK TABLES `raw_materials` WRITE;
/*!40000 ALTER TABLE `raw_materials` DISABLE KEYS */;
INSERT INTO `raw_materials` VALUES (1,'Lithium','Heavy metal',2,'2024-01-13 23:07:16','2024-01-13 23:07:16',2);
/*!40000 ALTER TABLE `raw_materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stage_inputs`
--

DROP TABLE IF EXISTS `stage_inputs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stage_inputs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stage_id` int DEFAULT NULL,
  `quantity` float NOT NULL,
  `component_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `component_id` (`component_id`),
  KEY `stage_inputs_ibfk_1` (`stage_id`),
  CONSTRAINT `stage_inputs_ibfk_1` FOREIGN KEY (`stage_id`) REFERENCES `stages` (`id`),
  CONSTRAINT `stage_inputs_ibfk_2` FOREIGN KEY (`component_id`) REFERENCES `components` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stage_inputs`
--

LOCK TABLES `stage_inputs` WRITE;
/*!40000 ALTER TABLE `stage_inputs` DISABLE KEYS */;
INSERT INTO `stage_inputs` VALUES (3,5,214,1),(7,5,12,2),(8,7,21,2),(9,7,2,3),(10,8,40,4),(11,9,124,4);
/*!40000 ALTER TABLE `stage_inputs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stage_outputs`
--

DROP TABLE IF EXISTS `stage_outputs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stage_outputs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stage_id` int DEFAULT NULL,
  `quantity` float NOT NULL,
  `component_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `component_id` (`component_id`),
  KEY `stage_outputs_ibfk_1` (`stage_id`),
  CONSTRAINT `stage_outputs_ibfk_1` FOREIGN KEY (`stage_id`) REFERENCES `stages` (`id`),
  CONSTRAINT `stage_outputs_ibfk_2` FOREIGN KEY (`component_id`) REFERENCES `components` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stage_outputs`
--

LOCK TABLES `stage_outputs` WRITE;
/*!40000 ALTER TABLE `stage_outputs` DISABLE KEYS */;
INSERT INTO `stage_outputs` VALUES (4,5,12,1),(6,5,21,2),(7,7,6,4),(8,8,60,5),(9,9,153,5);
/*!40000 ALTER TABLE `stage_outputs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stages`
--

DROP TABLE IF EXISTS `stages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `organization_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `organization_id` (`organization_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `stages_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `stages_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stages`
--

LOCK TABLES `stages` WRITE;
/*!40000 ALTER TABLE `stages` DISABLE KEYS */;
INSERT INTO `stages` VALUES (1,'First stage','Assembling microchip','2024-01-13 20:25:48','2024-01-13 20:25:48',NULL,1),(2,'Second stage','Assembling speakers','2024-01-13 20:26:12','2024-01-13 20:26:12',NULL,1),(3,'Third stage','Assembling product','2024-01-13 20:26:24','2024-01-13 20:26:24',NULL,1),(4,'Start','Manufacturing lithium batteries','2024-01-13 20:26:58','2024-01-13 20:26:58',NULL,2),(5,'Stage 1','Test','2024-03-09 17:16:58','2024-03-09 17:16:58',1,21),(7,'Stage 2',NULL,'2024-03-11 13:17:47','2024-03-11 13:17:47',1,21),(8,'Stage 3 - Indep',NULL,'2024-03-11 17:12:26','2024-03-11 17:12:26',1,21),(9,'Another Stage',NULL,'2024-03-19 21:36:01','2024-03-19 21:36:01',1,6),(10,'Create Stage Test',NULL,'2024-03-20 20:23:32','2024-03-20 20:23:32',1,21),(11,'New Test',NULL,'2024-03-20 20:24:56','2024-03-20 20:24:56',1,21),(12,'Another another test',NULL,'2024-03-20 20:26:36','2024-03-20 20:26:36',1,21),(13,'Another new new test',NULL,'2024-03-20 20:30:16','2024-03-20 20:30:16',1,21),(14,'EEEEnew new test',NULL,'2024-03-20 20:35:43','2024-03-20 20:35:43',1,21);
/*!40000 ALTER TABLE `stages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_orders`
--

DROP TABLE IF EXISTS `supplier_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplier_id` int NOT NULL,
  `raw_material_id` int DEFAULT NULL,
  `component_id` int DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  `order_date` timestamp NULL DEFAULT NULL,
  `estimated_delivery_date` timestamp NULL DEFAULT NULL,
  `delivery_date` timestamp NULL DEFAULT NULL,
  `status` enum('Initiated','Negociated','Placed','Delivered') DEFAULT NULL,
  `organization_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delivery_warehouse_id` int DEFAULT NULL,
  `delivery_factory_id` int DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `supplier_id` (`supplier_id`),
  KEY `raw_material_id` (`raw_material_id`),
  KEY `component_id` (`component_id`),
  KEY `organization_id` (`organization_id`),
  KEY `supplier_warehouse_id` (`delivery_warehouse_id`),
  KEY `supplier_delively_factory_id` (`delivery_factory_id`),
  CONSTRAINT `supplier_orders_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`),
  CONSTRAINT `supplier_orders_ibfk_2` FOREIGN KEY (`raw_material_id`) REFERENCES `raw_materials` (`id`),
  CONSTRAINT `supplier_orders_ibfk_3` FOREIGN KEY (`component_id`) REFERENCES `components` (`id`),
  CONSTRAINT `supplier_orders_ibfk_4` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `supplier_orders_ibfk_5` FOREIGN KEY (`delivery_warehouse_id`) REFERENCES `warehouses` (`id`),
  CONSTRAINT `supplier_orders_ibfk_6` FOREIGN KEY (`delivery_factory_id`) REFERENCES `factories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_orders`
--

LOCK TABLES `supplier_orders` WRITE;
/*!40000 ALTER TABLE `supplier_orders` DISABLE KEYS */;
INSERT INTO `supplier_orders` VALUES (1,1,NULL,1,124,'2024-02-28 09:07:56','2023-08-28 08:07:56','2023-01-28 09:07:56','Initiated',1,'2024-03-06 20:03:10','2024-03-27 16:09:49',NULL,NULL,'#124'),(2,3,NULL,1,1123,NULL,NULL,NULL,'Initiated',1,'2024-03-27 14:04:55','2024-03-27 15:56:06',NULL,NULL,'#5323'),(3,4,NULL,1,5315,NULL,NULL,NULL,'Initiated',1,'2024-03-27 14:12:45','2024-03-27 15:56:06',NULL,NULL,'#123'),(4,3,NULL,1,23,NULL,NULL,NULL,'Initiated',1,'2024-03-27 13:58:51','2024-03-27 15:58:51',NULL,NULL,NULL),(5,3,NULL,1,512315,NULL,NULL,NULL,'Initiated',1,'2024-03-27 13:58:52','2024-03-27 15:58:52',NULL,NULL,NULL);
/*!40000 ALTER TABLE `supplier_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_shipments`
--

DROP TABLE IF EXISTS `supplier_shipments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_shipments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplier_order_id` int NOT NULL,
  `quantity` float DEFAULT NULL,
  `shipment_starting_date` timestamp NULL DEFAULT NULL,
  `estimated_arrival_date` timestamp NULL DEFAULT NULL,
  `arrival_date` timestamp NULL DEFAULT NULL,
  `transporter_type` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `source_location_id` int DEFAULT NULL,
  `destination_location_id` int DEFAULT NULL,
  `destination_location_type` varchar(255) DEFAULT NULL,
  `current_location_latitude` float DEFAULT NULL,
  `current_location_longitude` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `source_location_id` (`source_location_id`),
  KEY `destination_location_id` (`destination_location_id`),
  KEY `supplier_order_id` (`supplier_order_id`),
  CONSTRAINT `supplier_shipments_ibfk_1` FOREIGN KEY (`supplier_order_id`) REFERENCES `supplier_orders` (`id`),
  CONSTRAINT `supplier_shipments_ibfk_2` FOREIGN KEY (`source_location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `supplier_shipments_ibfk_3` FOREIGN KEY (`destination_location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `supplier_shipments_ibfk_4` FOREIGN KEY (`supplier_order_id`) REFERENCES `supplier_orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_shipments`
--

LOCK TABLES `supplier_shipments` WRITE;
/*!40000 ALTER TABLE `supplier_shipments` DISABLE KEYS */;
INSERT INTO `supplier_shipments` VALUES (1,1,120,NULL,NULL,NULL,'Ship','Initiated',1,6,NULL,NULL,NULL),(2,1,12000,NULL,NULL,NULL,'Ship','Initiated',1,6,NULL,NULL,NULL);
/*!40000 ALTER TABLE `supplier_shipments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `organization_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `location_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcdasbtce0ulnp7hhdryn3mo5s` (`organization_id`),
  KEY `FK62w6tt0p4ti7f1ofonportcsg` (`location_id`),
  CONSTRAINT `FK62w6tt0p4ti7f1ofonportcsg` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `FKcdasbtce0ulnp7hhdryn3mo5s` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (1,'LA Supplier 1',2,'2024-03-04 21:07:43','2024-03-04 23:07:43',NULL),(2,'Motors Internationals',1,'2024-03-06 09:34:45','2024-03-23 16:20:45',13),(3,'AKio Motors',1,'2024-03-06 09:35:10','2024-03-06 11:35:10',6),(4,'Lu bu Co.',1,'2024-03-06 09:35:35','2024-03-06 11:35:35',5),(7,'Ford',1,'2024-03-21 18:49:38','2024-03-21 20:49:38',3),(8,'Ford',1,'2024-03-21 18:50:17','2024-03-21 20:50:17',10);
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `units_of_measurement`
--

DROP TABLE IF EXISTS `units_of_measurement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `units_of_measurement` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `unit_type` varchar(255) DEFAULT NULL,
  `organization_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `units_of_measurement_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units_of_measurement`
--

LOCK TABLES `units_of_measurement` WRITE;
/*!40000 ALTER TABLE `units_of_measurement` DISABLE KEYS */;
INSERT INTO `units_of_measurement` VALUES (1,'Unit','2024-01-13 23:06:01','Integer',NULL),(2,'Kilogram','2024-01-13 23:06:21','Decimal',NULL),(3,'Liters','2024-01-13 23:06:29','Decimal',NULL),(5,'grams','2024-03-21 13:29:57','discrete',1),(6,'Create Unit Test','2024-03-22 07:01:57','',1);
/*!40000 ALTER TABLE `units_of_measurement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password_hash` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `organization_id` int DEFAULT NULL,
  `role` enum('ADMIN','MEMBER','NONE') COLLATE utf8mb4_general_ci NOT NULL,
  `custom_role_id` int DEFAULT NULL,
  `is_profile_visible` tinyint(1) DEFAULT NULL,
  `is_profile_public` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_users_organizations` (`organization_id`),
  KEY `custom_role_id` (`custom_role_id`),
  CONSTRAINT `fk_users_organizations` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`custom_role_id`) REFERENCES `custom_roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('04badd68-29b7-4798-853b-a70cb727ac77','MatthewJonson','$2a$10$M8wcgZJEglAJq6gwqC6ZFu8h.EtwR3QaT4p0MtRooBvZeYC4eaiyK','mjonson@gmail.com','2024-01-05 08:17:24','2024-01-05 08:17:24',6,'ADMIN',NULL,NULL,NULL),('086e9e96-a8ef-11ee-bffa-00155de90539','TudorAOrban1','$2y$10$VfHMNhpQ1WOnrReNTySMvOqGggHd5MoXsfCpOkQx2s5Cp6vVmOWhy','tudororban3@gmail.com','2024-01-01 21:45:08','2024-03-28 12:53:34',1,'ADMIN',NULL,NULL,NULL),('1caaef49-e9fb-480c-9371-b932139d6c80','Jensen Huang','$2a$10$XV67s/5/D/Xq2O1BdIhc/uwpT4HjZA7zw0Q74GjmJeuVe6UyW3.we','huang@outlook.com','2024-01-05 11:28:00','2024-03-27 21:05:03',NULL,'NONE',NULL,NULL,_binary ''),('1f6d88dd-325b-4124-b393-71926bcbd2cb','Bill Gates','$2a$10$SoM.XQoRbwMtEd9KnI18vuNh6EP4cv81gZA0r2DaAtnRm32cgwXZ2','bg@outlook.com','2024-01-05 11:18:48','2024-01-05 11:18:48',6,'MEMBER',NULL,NULL,NULL),('229a84d6-ee6d-4e53-af42-5ee859c871c6','GabrielMajeri1','$2a$10$39CZd5cByMUENWFh3P6YRO6EruFcBo77alQED7Gz9xjEgoR/fneKO','anotheruser@example.com','2024-01-04 11:46:54','2024-03-27 14:55:29',1,'MEMBER',2,NULL,NULL),('322f2f9c-8b7a-4274-942c-13536394f1b8','Josh Hinton','$2a$10$5ohVUzHTwHvUvCZ7g3sH3uNsDlMTGZ6F8TTmNTqTRXqFGkttDsA02','jhint79@gmail.com','2024-01-05 07:56:47','2024-01-13 21:29:36',2,'ADMIN',NULL,NULL,NULL),('430f3a7b-6d9b-411e-95a3-eb794ba3c430','newuser','$2a$10$RvmBaBdPD0vfAyqSy6kaquDdB.90liiqY9n./GfIV1T8Fsdsrpl6O','newuser@example.com','2024-01-03 08:12:13','2024-01-04 20:00:08',10,'ADMIN',NULL,NULL,NULL),('43b836ac-a8e8-11ee-bffa-00155de90539','TudorAOrban','$2y$10$3Lsc1LeiiTLzZOgtVp7g/uJKM7VtcdYVCxT2A7DBggTy97fOkSAam','tudororban2@gmail.com','2024-01-01 20:56:41','2024-03-28 11:45:44',1,'NONE',16,NULL,NULL),('5cafa54a-74ea-4149-b245-c11c64d1361e','New-Test-User','$2a$10$gahZkePcBkQcy0BzX6Zu5.9tjq9GYp3ZrlFSFGe9prjBw3/bI/S4O','New-Test-Email','2024-02-16 07:21:16','2024-02-16 07:21:16',18,'MEMBER',NULL,NULL,NULL),('7dfa2892-673a-4fd2-8fc1-1330b084f909','notempty','$2a$10$n1GdvrTWL5XhG0mrjuMNoOYuXtwa4ooCLZeSiS4hAvZsKxI1H7KJW','','2024-01-03 12:54:08','2024-03-27 21:06:24',NULL,'NONE',NULL,NULL,_binary ''),('9af23564-a8c1-11ee-bffa-00155de90539','myusername','hashed_password','user@example.com','2024-01-01 16:19:57','2024-03-27 19:44:49',NULL,'NONE',NULL,NULL,_binary ''),('b880508b-b12f-4a25-8578-fa9d1d997394','user','$2a$10$mWDG.NhUdsvJkugVqsDTPeSVpdkrugP1X.qIPHzudHb7OhVkZfjm.','user@yahoo.com','2024-01-03 08:25:51','2024-03-27 19:44:49',NULL,'NONE',NULL,NULL,_binary ''),('bc3dfdde-a8ef-11ee-bffa-00155de90539','TudorAOrban2','$2a$10$5frWFt.qXudTyLvDhQzAQOh26ThALaiiMsR0Ir9WykuO8NyOctzCC','tudororban4@gmail.com','2024-01-01 21:50:10','2024-03-27 21:04:45',1,'ADMIN',1,NULL,_binary '');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse_inventory_items`
--

DROP TABLE IF EXISTS `warehouse_inventory_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse_inventory_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `warehouse_id` int NOT NULL,
  `raw_material_id` int DEFAULT NULL,
  `component_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` float DEFAULT NULL,
  `minimum_required_quantity` float DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `raw_material_id` (`raw_material_id`),
  KEY `component_id` (`component_id`),
  KEY `product_id` (`product_id`),
  KEY `warehouse_id` (`warehouse_id`),
  CONSTRAINT `warehouse_inventory_items_ibfk_1` FOREIGN KEY (`warehouse_id`) REFERENCES `factories` (`id`),
  CONSTRAINT `warehouse_inventory_items_ibfk_2` FOREIGN KEY (`raw_material_id`) REFERENCES `raw_materials` (`id`),
  CONSTRAINT `warehouse_inventory_items_ibfk_3` FOREIGN KEY (`component_id`) REFERENCES `components` (`id`),
  CONSTRAINT `warehouse_inventory_items_ibfk_4` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `warehouse_inventory_items_ibfk_5` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse_inventory_items`
--

LOCK TABLES `warehouse_inventory_items` WRITE;
/*!40000 ALTER TABLE `warehouse_inventory_items` DISABLE KEYS */;
INSERT INTO `warehouse_inventory_items` VALUES (1,6,NULL,1,NULL,341,240,'2024-03-12 16:38:07','2024-03-12 18:38:07'),(2,6,NULL,4,NULL,30,240,'2024-03-12 16:38:07','2024-03-12 18:38:07');
/*!40000 ALTER TABLE `warehouse_inventory_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouses`
--

DROP TABLE IF EXISTS `warehouses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `location_id` int DEFAULT NULL,
  `organization_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `location_id` (`location_id`),
  KEY `FKs5ri0quwqfiti3scdftfij40q` (`organization_id`),
  CONSTRAINT `FKs5ri0quwqfiti3scdftfij40q` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `warehouses_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouses`
--

LOCK TABLES `warehouses` WRITE;
/*!40000 ALTER TABLE `warehouses` DISABLE KEYS */;
INSERT INTO `warehouses` VALUES (1,'NJ Factory 1',1,2,'2024-03-04 21:07:37','2024-03-04 23:07:37'),(2,'Chicago warehouse 2',14,1,'2024-03-06 09:34:29','2024-03-23 16:40:53'),(3,'Chicago Warehouse',4,1,'2024-03-06 09:36:04','2024-03-06 11:36:04'),(4,'Shenzen Warehouse',5,1,'2024-03-06 09:51:05','2024-03-06 11:51:05'),(5,'Kyoto Warehouse',6,1,'2024-03-06 09:51:15','2024-03-06 11:51:15'),(6,'LA Warehouse',3,1,'2024-03-06 09:51:24','2024-03-06 11:51:24'),(9,'Warehouse S.E.A.',9,1,'2024-03-21 17:56:30','2024-03-21 19:56:30');
/*!40000 ALTER TABLE `warehouses` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-31 14:32:33
