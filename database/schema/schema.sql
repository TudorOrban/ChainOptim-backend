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
-- Table structure for table `active_resource_allocation_plans`
--

DROP TABLE IF EXISTS `active_resource_allocation_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `active_resource_allocation_plans` (
  `id` int NOT NULL AUTO_INCREMENT,
  `factory_id` int NOT NULL,
  `allocation_plan` json DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `activation_date` timestamp NULL DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `factory_id` (`factory_id`),
  CONSTRAINT `active_resource_allocation_plans_ibfk_1` FOREIGN KEY (`factory_id`) REFERENCES `factories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `active_resource_allocation_plans`
--

LOCK TABLES `active_resource_allocation_plans` WRITE;
/*!40000 ALTER TABLE `active_resource_allocation_plans` DISABLE KEYS */;
INSERT INTO `active_resource_allocation_plans` VALUES (1,3,'{\"results\": [{\"stageName\": \"Stage 1\", \"fullAmount\": 137.928, \"componentId\": 2, \"actualAmount\": null, \"componentName\": \"Test Component 2\", \"stageOutputId\": 6, \"factoryStageId\": 3, \"resultedAmount\": 8.315583}, {\"stageName\": \"Stage 1\", \"fullAmount\": 137.928, \"componentId\": 1, \"actualAmount\": null, \"componentName\": \"Chip\", \"stageOutputId\": 4, \"factoryStageId\": 3, \"resultedAmount\": 4.7517624}, {\"stageName\": \"Stage 2\", \"fullAmount\": 1088.6399, \"componentId\": 4, \"actualAmount\": null, \"componentName\": \"Camera\", \"stageOutputId\": 7, \"factoryStageId\": 4, \"resultedAmount\": 1088.6399}, {\"stageName\": \"Stage 3 - Indep\", \"fullAmount\": 10800, \"componentId\": 5, \"actualAmount\": null, \"componentName\": \"Sensor\", \"stageOutputId\": 8, \"factoryStageId\": 5, \"resultedAmount\": 409.5}, {\"stageName\": \"Another Stage\", \"fullAmount\": 0.0009916668, \"componentId\": 5, \"actualAmount\": null, \"componentName\": \"Sensor\", \"stageOutputId\": 9, \"factoryStageId\": 37, \"resultedAmount\": 0}], \"allocations\": [{\"stageName\": \"Stage 1\", \"componentId\": 1, \"actualAmount\": null, \"stageInputId\": 3, \"componentName\": \"Chip\", \"factoryStageId\": 3, \"allocatedAmount\": 231, \"requestedAmount\": 5252.6113, \"allocatorInventoryItemId\": 1}, {\"stageName\": \"Stage 1\", \"componentId\": 2, \"actualAmount\": null, \"stageInputId\": 7, \"componentName\": \"Test Component 2\", \"factoryStageId\": 3, \"allocatedAmount\": 294.53894, \"requestedAmount\": 294.53894, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 2, \"actualAmount\": null, \"stageInputId\": 8, \"componentName\": \"Test Component 2\", \"factoryStageId\": 4, \"allocatedAmount\": 1058.3999, \"requestedAmount\": 1058.3999, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 3, \"actualAmount\": null, \"stageInputId\": 9, \"componentName\": \"Microphone\", \"factoryStageId\": 4, \"allocatedAmount\": 100.799995, \"requestedAmount\": 100.799995, \"allocatorInventoryItemId\": 4}, {\"stageName\": \"Stage 3 - Indep\", \"componentId\": 4, \"actualAmount\": null, \"stageInputId\": 10, \"componentName\": \"Camera\", \"factoryStageId\": 5, \"allocatedAmount\": 91, \"requestedAmount\": 2400, \"allocatorInventoryItemId\": 5}, {\"stageName\": \"Another Stage\", \"componentId\": 4, \"actualAmount\": null, \"stageInputId\": 11, \"componentName\": \"Camera\", \"factoryStageId\": 37, \"allocatedAmount\": 0, \"requestedAmount\": 1.4466667, \"allocatorInventoryItemId\": 5}], \"durationDays\": 36, \"factoryGraph\": {\"nodes\": {\"3\": {\"priority\": 0, \"smallStage\": {\"id\": 3, \"stageName\": \"Stage 1\", \"stageInputs\": [{\"id\": 3, \"componentId\": 1, \"componentName\": null, \"quantityPerStage\": 214, \"allocatedQuantity\": 231, \"requestedQuantity\": 5252.6113}, {\"id\": 7, \"componentId\": 2, \"componentName\": null, \"quantityPerStage\": 12, \"allocatedQuantity\": 294.53894, \"requestedQuantity\": 294.53894}], \"stageOutputs\": [{\"id\": 6, \"componentId\": 2, \"componentName\": null, \"outputPerRequest\": 87.77236, \"quantityPerStage\": 21, \"expectedOutputPerAllocation\": 8.315583}, {\"id\": 4, \"componentId\": 1, \"componentName\": null, \"outputPerRequest\": 50.155636, \"quantityPerStage\": 12, \"expectedOutputPerAllocation\": 4.7517624}]}, \"perDuration\": 211.41, \"numberOfStepsCapacity\": 144.14, \"allocationCapacityRatio\": 0.043978125, \"minimumRequiredCapacity\": null}, \"4\": {\"priority\": 1, \"smallStage\": {\"id\": 4, \"stageName\": \"Stage 2\", \"stageInputs\": [{\"id\": 8, \"componentId\": 2, \"componentName\": null, \"quantityPerStage\": 21, \"allocatedQuantity\": 1058.3999, \"requestedQuantity\": 1058.3999}, {\"id\": 9, \"componentId\": 3, \"componentName\": null, \"quantityPerStage\": 2, \"allocatedQuantity\": 100.799995, \"requestedQuantity\": 100.799995}], \"stageOutputs\": [{\"id\": 7, \"componentId\": 4, \"componentName\": null, \"outputPerRequest\": 1088.6399, \"quantityPerStage\": 6, \"expectedOutputPerAllocation\": 1088.6399}]}, \"perDuration\": 10, \"numberOfStepsCapacity\": 14, \"allocationCapacityRatio\": 1, \"minimumRequiredCapacity\": 0.9}, \"5\": {\"priority\": 2, \"smallStage\": {\"id\": 5, \"stageName\": \"Stage 3 - Indep\", \"stageInputs\": [{\"id\": 10, \"componentId\": 4, \"componentName\": null, \"quantityPerStage\": 40, \"allocatedQuantity\": 91, \"requestedQuantity\": 2400}], \"stageOutputs\": [{\"id\": 8, \"componentId\": 5, \"componentName\": null, \"outputPerRequest\": 10800, \"quantityPerStage\": 60, \"expectedOutputPerAllocation\": 409.5}]}, \"perDuration\": 12, \"numberOfStepsCapacity\": 20, \"allocationCapacityRatio\": 0.037916668, \"minimumRequiredCapacity\": 0.9}, \"37\": {\"priority\": 10, \"smallStage\": {\"id\": 37, \"stageName\": \"Another Stage\", \"stageInputs\": [{\"id\": 11, \"componentId\": 4, \"componentName\": null, \"quantityPerStage\": 124, \"allocatedQuantity\": 0, \"requestedQuantity\": 1.4466667}], \"stageOutputs\": [{\"id\": 9, \"componentId\": 5, \"componentName\": null, \"outputPerRequest\": 0.0009916668, \"quantityPerStage\": 153, \"expectedOutputPerAllocation\": 0}]}, \"perDuration\": 64800, \"numberOfStepsCapacity\": 21, \"allocationCapacityRatio\": 0, \"minimumRequiredCapacity\": 22}, \"38\": {\"priority\": 12, \"smallStage\": {\"id\": 38, \"stageName\": \"Create Stage Test\", \"stageInputs\": [], \"stageOutputs\": []}, \"perDuration\": 1123200, \"numberOfStepsCapacity\": 312, \"allocationCapacityRatio\": 0, \"minimumRequiredCapacity\": 312}}, \"adjList\": {\"3\": [{\"destStageInputId\": 8, \"srcStageOutputId\": 6, \"srcFactoryStageId\": 4, \"destFactoryStageId\": 3}], \"4\": [], \"5\": [], \"37\": [], \"38\": []}, \"pipelinePriority\": null}, \"inventoryBalance\": {\"1\": {\"id\": 1, \"product\": null, \"quantity\": 0, \"component\": {\"id\": 1, \"name\": \"Chip\", \"unit\": {\"id\": 1, \"name\": \"Unit\", \"unitType\": \"Integer\", \"createdAt\": [2024, 1, 13, 23, 6, 1], \"organizationId\": null}, \"createdAt\": [2024, 1, 13, 23, 8, 4], \"updatedAt\": [2024, 1, 13, 23, 8, 4], \"description\": null, \"organizationId\": 2}, \"createdAt\": [2024, 3, 9, 13, 28, 41], \"factoryId\": 3, \"updatedAt\": [2024, 4, 3, 8, 58, 58], \"organizationId\": null, \"minimumRequiredQuantity\": 1421}, \"2\": {\"id\": 3, \"product\": {\"id\": 1, \"name\": \"Iphone 21\", \"unit\": null, \"stages\": null, \"createdAt\": [2024, 1, 13, 20, 22, 49], \"updatedAt\": [2024, 1, 13, 20, 22, 49], \"description\": \"The newest version of our product\", \"organizationId\": 2}, \"quantity\": 1038.061, \"component\": {\"id\": 2, \"name\": \"Test Component 2\", \"unit\": {\"id\": 1, \"name\": \"Unit\", \"unitType\": \"Integer\", \"createdAt\": [2024, 1, 13, 23, 6, 1], \"organizationId\": null}, \"createdAt\": [2024, 3, 9, 17, 26, 50], \"updatedAt\": [2024, 3, 9, 17, 26, 50], \"description\": null, \"organizationId\": 1}, \"createdAt\": [2024, 3, 9, 13, 29, 4], \"factoryId\": 3, \"updatedAt\": [2024, 4, 3, 8, 58, 58], \"organizationId\": null, \"minimumRequiredQuantity\": 141}, \"3\": {\"id\": 4, \"product\": {\"id\": 2, \"name\": \"Iphone 10\", \"unit\": null, \"stages\": null, \"createdAt\": [2024, 1, 13, 20, 23, 15], \"updatedAt\": [2024, 1, 13, 20, 23, 15], \"description\": \"Old product\", \"organizationId\": 2}, \"quantity\": 118.51559, \"component\": {\"id\": 3, \"name\": \"Microphone\", \"unit\": {\"id\": 1, \"name\": \"Unit\", \"unitType\": \"Integer\", \"createdAt\": [2024, 1, 13, 23, 6, 1], \"organizationId\": null}, \"createdAt\": [2024, 3, 11, 13, 19, 9], \"updatedAt\": [2024, 3, 11, 13, 19, 9], \"description\": null, \"organizationId\": 1}, \"createdAt\": [2024, 3, 9, 13, 29, 13], \"factoryId\": 3, \"updatedAt\": [2024, 4, 3, 8, 58, 58], \"organizationId\": null, \"minimumRequiredQuantity\": 1400}, \"4\": {\"id\": 5, \"product\": {\"id\": 3, \"name\": \"Samsung\", \"unit\": null, \"stages\": null, \"createdAt\": [2024, 2, 23, 10, 2, 2], \"updatedAt\": [2024, 2, 23, 10, 2, 2], \"description\": \"New phone\", \"organizationId\": 1}, \"quantity\": 0, \"component\": {\"id\": 4, \"name\": \"Camera\", \"unit\": {\"id\": 1, \"name\": \"Unit\", \"unitType\": \"Integer\", \"createdAt\": [2024, 1, 13, 23, 6, 1], \"organizationId\": null}, \"createdAt\": [2024, 3, 11, 13, 20, 1], \"updatedAt\": [2024, 3, 11, 13, 20, 1], \"description\": null, \"organizationId\": 1}, \"createdAt\": [2024, 3, 9, 13, 29, 25], \"factoryId\": 3, \"updatedAt\": [2024, 4, 3, 8, 58, 58], \"organizationId\": null, \"minimumRequiredQuantity\": 414000}}}','2024-04-09 20:13:05','2024-08-12 01:40:24','2024-04-13 00:58:08',1);
/*!40000 ALTER TABLE `active_resource_allocation_plans` ENABLE KEYS */;
UNLOCK TABLES;

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
  `organization_id` int NOT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `column_id` varchar(255) DEFAULT NULL,
  `delivered_quantity` float DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `client_id` (`client_id`),
  KEY `product_id` (`product_id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `client_orders_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`),
  CONSTRAINT `client_orders_ibfk_3` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_orders`
--

LOCK TABLES `client_orders` WRITE;
/*!40000 ALTER TABLE `client_orders` DISABLE KEYS */;
INSERT INTO `client_orders` VALUES (1,1,'2024-03-18 14:44:45','2024-04-26 11:43:39',1,NULL,NULL,NULL,NULL,1,'13251',NULL,NULL,NULL),(2,1,'2024-03-27 14:48:07','2024-05-20 12:42:57',NULL,125,NULL,NULL,NULL,1,'#123',NULL,NULL,NULL),(3,1,'2024-03-27 14:48:38','2024-05-20 12:42:57',NULL,1125,NULL,NULL,NULL,1,'#124123',NULL,NULL,NULL),(5,1,'2024-03-27 13:33:45','2024-05-20 12:42:57',21,1123,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(6,1,'2024-03-27 13:33:45','2024-05-20 12:42:57',3,5315,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(10,1,'2024-04-26 13:55:41','2024-04-26 13:59:30',5,1231,NULL,NULL,NULL,1,'2321',NULL,213,NULL),(11,1,'2024-04-26 13:57:09','2024-04-26 13:57:09',5,1231,NULL,NULL,NULL,1,'12124',NULL,213,NULL),(12,1,'2024-04-26 13:59:30','2024-04-26 13:59:30',5,1231,NULL,NULL,NULL,1,'#321',NULL,213,NULL),(13,1,'2024-04-26 13:59:44','2024-04-30 03:50:37',3,100,NULL,NULL,NULL,1,'12124212311',NULL,200,0),(14,1,'2024-04-26 14:08:06','2024-04-26 14:08:06',1,NULL,NULL,NULL,NULL,1,'13251',NULL,NULL,NULL),(15,1,'2024-04-30 03:50:37','2024-04-30 04:54:14',3,100,NULL,'2024-04-09 07:53:37','2024-04-09 07:53:37',1,'12124212311',NULL,200,0),(16,1,'2024-04-30 04:54:14','2024-04-30 04:54:14',5,1231,NULL,NULL,NULL,1,'12124212311',NULL,213,NULL),(17,1,'2024-04-30 09:37:12','2024-04-30 09:38:01',4,20,NULL,'2024-04-02 12:36:46','2024-04-03 12:36:46',1,'123',NULL,15,0),(18,1,'2024-04-30 09:38:01','2024-05-02 06:22:59',4,25,NULL,'2024-04-03 12:36:46','2024-04-04 12:36:46',1,'1234',NULL,15,1),(19,1,'2024-05-02 06:22:59','2024-05-02 06:34:53',4,20,NULL,'2024-04-02 12:36:46','2024-04-03 12:36:46',1,'123',NULL,15,1),(20,1,'2024-05-02 06:34:52','2024-05-02 06:34:52',4,20,NULL,'2024-04-02 12:36:46','2024-04-03 12:36:46',1,'123',NULL,10,0),(21,1,'2024-05-02 06:37:56','2024-05-02 06:38:20',3,15,NULL,'2024-05-02 09:37:23','2024-05-03 09:37:23',1,'1234',NULL,10,3),(22,1,'2024-05-02 06:38:20','2024-05-02 06:38:20',3,10,NULL,'2024-05-02 09:37:23','2024-05-03 09:37:23',1,'1234',NULL,5,1),(23,1,'2024-05-02 06:40:28','2024-05-02 06:41:28',4,20,NULL,'2024-05-02 09:37:23','2024-05-03 09:37:23',1,'1234',NULL,10,3),(24,1,'2024-05-02 06:41:28','2024-05-02 06:45:30',5,10,NULL,'2024-05-02 09:37:23','2024-05-03 09:37:23',1,'1234',NULL,5,1),(26,1,'2024-05-02 06:47:13','2024-05-02 06:47:13',3,10,NULL,'2024-05-02 09:37:23','2024-05-03 09:37:23',1,'1234',NULL,5,1),(27,1,'2024-05-02 06:50:05','2024-05-02 06:55:25',4,20,NULL,'2024-05-31 09:37:23','2024-05-31 09:37:23',1,'1234',NULL,15,0),(28,1,'2024-05-02 06:55:25','2024-05-02 07:00:32',7,100,NULL,'2024-05-02 09:37:23','2024-05-03 09:37:23',1,'1234',NULL,50,1),(30,1,'2024-08-04 16:59:19','2024-08-04 16:59:19',5,214124,NULL,NULL,'2024-08-06 19:59:02',1,'553412',NULL,21412,4),(31,1,'2024-08-04 17:04:45','2024-08-04 17:04:45',3,214214,NULL,NULL,'2024-08-06 20:04:29',1,'1421424',NULL,21412,3),(32,4,'2024-08-09 11:47:30','2024-08-09 11:53:18',7,1,'2024-08-08 21:00:00','2024-08-14 21:00:00',NULL,1,'2131',NULL,NULL,0),(33,4,'2024-08-09 11:53:18','2024-08-09 11:54:16',4,1,'2024-08-08 21:00:00','2024-08-14 21:00:00',NULL,1,'2131',NULL,NULL,0),(34,4,'2024-08-09 11:54:16','2024-08-09 11:54:16',15,1,'2024-08-08 21:00:00','2024-08-14 21:00:00',NULL,1,'2131',NULL,NULL,0);
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
  `source_location_id` int DEFAULT NULL,
  `destination_location_id` int DEFAULT NULL,
  `current_location_latitude` float DEFAULT NULL,
  `current_location_longitude` float DEFAULT NULL,
  `organization_id` int NOT NULL,
  `client_id` int NOT NULL,
  `src_factory_id` int DEFAULT NULL,
  `src_warehouse_id` int DEFAULT NULL,
  `departure_date` timestamp NULL DEFAULT NULL,
  `transport_type` enum('ROAD','RAIL','SEA','AIR') DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `client_order_id` (`client_order_id`),
  KEY `source_location_id` (`source_location_id`),
  KEY `destination_location_id` (`destination_location_id`),
  KEY `client_shipments_ibfk_4_idx` (`client_id`),
  KEY `client_shipments_ibfk_5_idx` (`organization_id`),
  CONSTRAINT `client_shipments_ibfk_1` FOREIGN KEY (`client_order_id`) REFERENCES `client_orders` (`id`),
  CONSTRAINT `client_shipments_ibfk_2` FOREIGN KEY (`source_location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `client_shipments_ibfk_3` FOREIGN KEY (`destination_location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `client_shipments_ibfk_4` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`),
  CONSTRAINT `client_shipments_ibfk_5` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_shipments`
--

LOCK TABLES `client_shipments` WRITE;
/*!40000 ALTER TABLE `client_shipments` DISABLE KEYS */;
INSERT INTO `client_shipments` VALUES (1,1,'2024-03-18 14:46:24','2024-05-21 02:03:30',40,'2024-05-15 14:54:18','2024-05-16 14:54:18','2024-05-16 14:54:00',5,6,NULL,NULL,1,1,NULL,NULL,NULL,NULL,NULL),(2,1,'2024-05-09 14:47:42','2024-05-21 02:03:30',23,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,NULL,NULL,NULL,NULL,NULL),(3,1,'2024-05-09 11:52:36','2024-08-06 19:19:22',100,'2024-05-16 14:52:02','2024-05-17 14:52:02','2024-05-17 14:52:02',NULL,NULL,NULL,NULL,1,1,NULL,NULL,NULL,'SEA',NULL),(4,1,'2024-05-10 04:01:46','2024-05-21 02:03:30',110,'2024-05-15 07:01:19','2024-05-17 07:01:19','2024-05-18 07:01:19',NULL,NULL,NULL,NULL,1,1,NULL,NULL,NULL,NULL,NULL),(5,1,'2024-05-10 15:33:18','2024-08-06 20:30:56',120,'2024-05-15 18:32:48','2024-05-20 18:32:48','2024-05-20 18:32:48',22,24,NULL,NULL,1,1,5,NULL,NULL,'AIR',NULL),(6,1,'2024-05-20 12:45:47','2024-05-21 02:03:30',134,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,NULL,NULL,NULL,NULL,NULL),(7,1,'2024-05-20 12:45:47','2024-05-31 18:32:26',143,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,2,NULL,NULL,NULL,NULL,NULL),(8,1,'2024-05-20 09:47:15','2024-08-06 19:19:22',100,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,2,NULL,NULL,NULL,'ROAD',NULL),(9,1,'2024-05-31 17:02:19','2024-05-31 17:02:19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,NULL,NULL,NULL,NULL,NULL),(10,1,'2024-05-31 17:02:19','2024-05-31 17:02:19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,1,NULL,NULL,NULL,NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'Viva Enterprises',1,'2024-03-18 14:43:01','2024-05-20 15:49:34',5),(2,'BB Enterprises',1,'2024-03-18 14:24:06','2024-03-23 16:43:54',15),(4,'Siame Enterprises',1,'2024-03-21 18:56:51','2024-04-22 20:22:55',5),(5,'New Test Client',1,'2024-04-22 20:30:42','2024-04-22 20:30:42',3),(6,'TestClient',1,'2024-08-08 11:34:47','2024-08-08 14:34:47',5);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compartments`
--

DROP TABLE IF EXISTS `compartments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compartments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `warehouse_id` int NOT NULL,
  `organization_id` int NOT NULL,
  `data_json` json DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `warehouse_id` (`warehouse_id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `compartments_ibfk_1` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouses` (`id`),
  CONSTRAINT `compartments_ibfk_2` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compartments`
--

LOCK TABLES `compartments` WRITE;
/*!40000 ALTER TABLE `compartments` DISABLE KEYS */;
INSERT INTO `compartments` VALUES (1,'Comp 1',1,1,'{}'),(2,'Compr 21',1,1,'{\"crateSpecs\": [{\"crateId\": 1, \"maxCrates\": 16.0, \"componentId\": 1}, {\"crateId\": 2, \"maxCrates\": 100.0, \"componentId\": 2}], \"currentCrates\": [{\"crateId\": 1, \"numberOfCrates\": 10}]}'),(3,'Compr 21',1,1,'{\"crateSpecs\": [{\"crateId\": 1, \"maxCrates\": 12.0, \"componentId\": 1}, {\"crateId\": 2, \"maxCrates\": 60.0, \"componentId\": 2}], \"currentCrates\": [{\"crateId\": 1, \"numberOfCrates\": 8}, {\"crateId\": 2, \"numberOfCrates\": 58}]}'),(4,'C 14',1,1,'{\"crateSpecs\": [{\"crateId\": 1, \"maxCrates\": 129.0, \"componentId\": 1}, {\"crateId\": 2, \"maxCrates\": 41.0, \"componentId\": 2}], \"currentCrates\": [{\"crateId\": 1, \"numberOfCrates\": 20}, {\"crateId\": 2, \"numberOfCrates\": 40}]}'),(5,'NewComp',1,1,'null'),(6,'NC2',1,1,'null'),(7,'weqw',1,1,'null'),(8,'NCC@!',1,1,'null'),(9,'NC12',1,1,'{\"crateSpecs\": [{\"crateId\": 1, \"maxCrates\": 2132.0, \"componentId\": null}, {\"crateId\": 2, \"maxCrates\": 21.23, \"componentId\": null}], \"currentCrates\": null}'),(10,'BDSA1',1,1,'{\"crateSpecs\": [{\"crateId\": 1, \"maxCrates\": 121.0, \"componentId\": null}, {\"crateId\": 2, \"maxCrates\": 213.21, \"componentId\": null}], \"currentCrates\": null}'),(11,'New!',1,1,'{\"crateSpecs\": [{\"crateId\": 1, \"maxCrates\": 231.0, \"componentId\": null}, {\"crateId\": 2, \"maxCrates\": 21421.0, \"componentId\": null}, {\"crateId\": 1, \"maxCrates\": 1231.0, \"componentId\": null}, {\"crateId\": 2, \"maxCrates\": 1535.0, \"componentId\": null}], \"currentCrates\": null}');
/*!40000 ALTER TABLE `compartments` ENABLE KEYS */;
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
  `unit_of_measurement` json DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `unit_id` (`unit_id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `components_ibfk_1` FOREIGN KEY (`unit_id`) REFERENCES `units_of_measurement` (`id`),
  CONSTRAINT `components_ibfk_2` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `components`
--

LOCK TABLES `components` WRITE;
/*!40000 ALTER TABLE `components` DISABLE KEYS */;
INSERT INTO `components` VALUES (1,'Chip',NULL,1,'2024-01-13 23:08:04','2024-01-13 23:08:04',2,NULL),(2,'Test Component 2',NULL,1,'2024-03-09 17:26:50','2024-03-09 17:26:50',1,NULL),(3,'Microphone',NULL,1,'2024-03-11 13:19:09','2024-03-11 13:19:09',1,NULL),(4,'Camera',NULL,1,'2024-03-11 13:20:01','2024-03-11 13:20:01',1,NULL),(5,'Sensor',NULL,1,'2024-03-11 17:13:11','2024-03-11 17:13:11',1,NULL),(7,'New Component',NULL,NULL,'2024-03-21 19:57:30','2024-03-21 19:57:30',1,NULL),(8,'New Component Without Unit','TestUpdate',1,'2024-03-21 19:57:48','2024-08-01 06:01:25',1,'{\"standardUnit\": \"KILOGRAM\", \"unitMagnitude\": null}'),(9,'NewTestComp','N..',NULL,'2024-08-01 05:43:51','2024-08-01 05:43:51',1,'{\"standardUnit\": \"INCH\", \"unitMagnitude\": \"BASE\"}'),(10,'Test2',NULL,NULL,'2024-08-01 05:53:28','2024-08-01 05:53:28',1,'{\"standardUnit\": \"LITER\", \"unitMagnitude\": \"MILLI\"}');
/*!40000 ALTER TABLE `components` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crates`
--

DROP TABLE IF EXISTS `crates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crates` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `component_id` int NOT NULL,
  `quantity` decimal(10,0) NOT NULL,
  `volume_m3` decimal(10,0) DEFAULT NULL,
  `stackable` tinyint(1) DEFAULT NULL,
  `height_m` decimal(10,0) DEFAULT NULL,
  `organization_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `component_id` (`component_id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `crates_ibfk_1` FOREIGN KEY (`component_id`) REFERENCES `components` (`id`),
  CONSTRAINT `crates_ibfk_2` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crates`
--

LOCK TABLES `crates` WRITE;
/*!40000 ALTER TABLE `crates` DISABLE KEYS */;
INSERT INTO `crates` VALUES (1,'CRate 1',1,1,123,1,3,1),(2,'Cargo Crate',2,4,19,1,2,1);
/*!40000 ALTER TABLE `crates` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `custom_roles`
--

LOCK TABLES `custom_roles` WRITE;
/*!40000 ALTER TABLE `custom_roles` DISABLE KEYS */;
INSERT INTO `custom_roles` VALUES (2,1,'Privileged Member 1','{\"clients\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": false}, \"products\": {\"canRead\": false, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"factories\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"suppliers\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"warehouses\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": true, \"canUpdate\": false}, \"organization\": null, \"featurePermissions\": {\"NONE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CRATE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CLIENT\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": false}, \"MEMBER\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"PRICING\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"PRODUCT\": {\"canRead\": false, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"LOCATION\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"SUPPLIER\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"COMPONENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"WAREHOUSE\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": true, \"canUpdate\": false}, \"COMPARTMENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CUSTOM_ROLE\": null, \"CLIENT_ORDER\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY_STAGE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"PRODUCT_STAGE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"SUPPLIER_ORDER\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"UPCOMING_EVENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CLIENT_SHIPMENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"TRANSPORT_ROUTE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CLIENT_EVALUATION\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY_INVENTORY\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"SUPPLIER_SHIPMENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY_PERFORMANCE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"WAREHOUSE_INVENTORY\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"SUPPLIER_PERFORMANCE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"RESOURCE_ALLOCATION_PLAN\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY_PRODUCTION_HISTORY\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}}}','2024-03-24 08:04:10','2024-09-13 14:54:26'),(4,1,'Privileged Member 2','{\"clients\": {\"canRead\": false, \"canCreate\": true, \"canDelete\": false, \"canUpdate\": true}, \"products\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": false, \"canUpdate\": true}, \"factories\": {\"canRead\": true, \"canCreate\": false, \"canDelete\": true, \"canUpdate\": true}, \"suppliers\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": false, \"canUpdate\": true}, \"warehouses\": {\"canRead\": false, \"canCreate\": true, \"canDelete\": false, \"canUpdate\": true}, \"organization\": null, \"featurePermissions\": {\"NONE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CRATE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CLIENT\": {\"canRead\": false, \"canCreate\": true, \"canDelete\": false, \"canUpdate\": true}, \"MEMBER\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY\": {\"canRead\": true, \"canCreate\": false, \"canDelete\": true, \"canUpdate\": true}, \"PRICING\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"PRODUCT\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": false, \"canUpdate\": true}, \"LOCATION\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"SUPPLIER\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": false, \"canUpdate\": true}, \"COMPONENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"WAREHOUSE\": {\"canRead\": false, \"canCreate\": true, \"canDelete\": false, \"canUpdate\": true}, \"COMPARTMENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CUSTOM_ROLE\": null, \"CLIENT_ORDER\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY_STAGE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"PRODUCT_STAGE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"SUPPLIER_ORDER\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"UPCOMING_EVENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CLIENT_SHIPMENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"TRANSPORT_ROUTE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CLIENT_EVALUATION\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY_INVENTORY\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"SUPPLIER_SHIPMENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY_PERFORMANCE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"WAREHOUSE_INVENTORY\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"SUPPLIER_PERFORMANCE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"RESOURCE_ALLOCATION_PLAN\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY_PRODUCTION_HISTORY\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}}}','2024-03-24 11:19:00','2024-09-13 14:54:26'),(16,1,'Custom Admin','{\"clients\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"products\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"factories\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"suppliers\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"warehouses\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"organization\": null, \"featurePermissions\": {\"NONE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CRATE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CLIENT\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"MEMBER\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"PRICING\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"PRODUCT\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"LOCATION\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"SUPPLIER\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"COMPONENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"WAREHOUSE\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": true, \"canUpdate\": true}, \"COMPARTMENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CUSTOM_ROLE\": null, \"CLIENT_ORDER\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY_STAGE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"PRODUCT_STAGE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"SUPPLIER_ORDER\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"UPCOMING_EVENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CLIENT_SHIPMENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"TRANSPORT_ROUTE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"CLIENT_EVALUATION\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY_INVENTORY\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"SUPPLIER_SHIPMENT\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY_PERFORMANCE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"WAREHOUSE_INVENTORY\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"SUPPLIER_PERFORMANCE\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"RESOURCE_ALLOCATION_PLAN\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}, \"FACTORY_PRODUCTION_HISTORY\": {\"canRead\": null, \"canCreate\": null, \"canDelete\": null, \"canUpdate\": null}}}','2024-03-26 18:52:47','2024-09-13 14:54:26'),(19,1,'TestRole','{\"clients\": null, \"products\": null, \"factories\": null, \"suppliers\": null, \"warehouses\": null, \"organization\": null, \"featurePermissions\": {\"CLIENT\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"MEMBER\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"FACTORY\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"PRODUCT\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"SUPPLIER\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"COMPONENT\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"WAREHOUSE\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"CUSTOM_ROLE\": {\"canRead\": true, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"CLIENT_ORDER\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"FACTORY_STAGE\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": true}, \"PRODUCT_STAGE\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": true}, \"SUPPLIER_ORDER\": {\"canRead\": true, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"CLIENT_SHIPMENT\": {\"canRead\": true, \"canCreate\": true, \"canDelete\": false, \"canUpdate\": false}, \"CLIENT_EVALUATION\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"FACTORY_INVENTORY\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"SUPPLIER_SHIPMENT\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"FACTORY_PERFORMANCE\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"WAREHOUSE_INVENTORY\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"SUPPLIER_PERFORMANCE\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"RESOURCE_ALLOCATION_PLAN\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}, \"FACTORY_PRODUCTION_HISTORY\": {\"canRead\": false, \"canCreate\": false, \"canDelete\": false, \"canUpdate\": false}}}','2024-09-13 12:03:51','2024-09-13 15:03:51');
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
  `overall_score` decimal(10,0) DEFAULT NULL,
  `resource_distribution_score` decimal(10,0) DEFAULT NULL,
  `resource_readiness_score` decimal(10,0) DEFAULT NULL,
  `resource_utilization_score` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `location_id` (`location_id`),
  KEY `FKronhjti6859iso27solqwxf6l` (`organization_id`),
  CONSTRAINT `factories_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `FKronhjti6859iso27solqwxf6l` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factories`
--

LOCK TABLES `factories` WRITE;
/*!40000 ALTER TABLE `factories` DISABLE KEYS */;
INSERT INTO `factories` VALUES (1,'NJ Factory 1',1,2,'2024-03-04 21:07:24','2024-03-04 23:07:24',NULL,NULL,NULL,NULL),(2,'NJ Factory 2',1,2,'2024-03-04 21:07:24','2024-03-04 23:07:24',NULL,NULL,NULL,NULL),(3,'New York Factory 1',12,1,'2024-03-06 09:26:51','2024-08-13 14:31:56',37,0,0,74),(4,'China factory MegaF',5,1,'2024-03-06 09:32:38','2024-03-06 11:32:38',NULL,NULL,NULL,NULL),(5,'Tokio factory',6,1,'2024-03-06 09:32:58','2024-03-06 11:32:58',NULL,NULL,NULL,NULL),(6,'Chicago factory 2',24,1,'2024-03-06 09:33:21','2024-08-05 11:16:03',NULL,NULL,NULL,NULL),(10,'New Factory Test Sanitization',1,1,'2024-03-10 13:28:05','2024-03-10 15:28:05',NULL,NULL,NULL,NULL),(11,'SS Factory',5,1,'2024-03-21 19:05:10','2024-03-21 21:05:10',NULL,NULL,NULL,NULL),(13,'RefactoredFormTest1',4,1,'2024-04-25 19:54:17','2024-04-25 19:54:17',NULL,NULL,NULL,NULL),(14,'Refactored Form Test 2',21,1,'2024-04-25 19:54:28','2024-04-25 19:57:41',NULL,NULL,NULL,NULL),(15,'asda',NULL,1,'2024-08-08 06:28:02','2024-08-08 09:28:02',NULL,NULL,NULL,NULL),(16,'eqweqw',NULL,1,'2024-08-08 07:58:38','2024-08-08 10:58:38',NULL,NULL,NULL,NULL),(17,'NewTest',14,1,'2024-08-08 08:01:46','2024-08-08 11:01:46',NULL,NULL,NULL,NULL),(18,'swqeqw',25,1,'2024-08-08 09:14:51','2024-08-08 12:14:51',NULL,NULL,NULL,NULL),(19,'weqeqwq',32,1,'2024-08-08 09:49:39','2024-08-08 12:49:39',NULL,NULL,NULL,NULL),(20,'ewqeqw',10,1,'2024-08-08 09:52:46','2024-08-08 12:52:46',NULL,NULL,NULL,NULL),(21,'qweqw',33,1,'2024-08-08 09:53:06','2024-08-08 12:53:06',NULL,NULL,NULL,NULL),(22,'EQWQE',34,1,'2024-08-08 09:58:50','2024-08-08 12:58:50',NULL,NULL,NULL,NULL),(23,'wqeqe',35,1,'2024-08-08 10:31:45','2024-08-08 13:31:45',NULL,NULL,NULL,NULL),(24,'TestFactory',37,1,'2024-08-08 10:59:12','2024-08-08 13:59:12',NULL,NULL,NULL,NULL),(26,'TestFactory2',37,1,'2024-08-08 11:26:35','2024-08-08 14:26:35',NULL,NULL,NULL,NULL);
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
  `organization_id` int DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `factory_id` (`factory_id`),
  KEY `raw_material_id` (`raw_material_id`),
  KEY `component_id` (`component_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `factory_inventory_items_ibfk_1` FOREIGN KEY (`factory_id`) REFERENCES `factories` (`id`),
  CONSTRAINT `factory_inventory_items_ibfk_3` FOREIGN KEY (`component_id`) REFERENCES `components` (`id`),
  CONSTRAINT `factory_inventory_items_ibfk_4` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factory_inventory_items`
--

LOCK TABLES `factory_inventory_items` WRITE;
/*!40000 ALTER TABLE `factory_inventory_items` DISABLE KEYS */;
INSERT INTO `factory_inventory_items` VALUES (1,3,NULL,1,NULL,231,1421,'2024-03-09 13:28:41','2024-04-03 08:58:58',1,NULL),(3,3,NULL,2,1,2391,141,'2024-03-09 13:29:04','2024-04-03 08:58:58',1,NULL),(4,3,NULL,3,2,211,1400,'2024-03-09 13:29:13','2024-04-03 08:58:58',1,NULL),(5,3,NULL,4,3,91,414000,'2024-03-09 13:29:25','2024-04-03 08:58:58',1,NULL),(9,3,NULL,NULL,3,200.5,214.12,'2024-03-09 12:33:03','2024-04-03 08:58:58',1,NULL),(19,11,NULL,5,15,312,NULL,'2024-08-09 13:06:54','2024-08-09 19:13:19',1,NULL);
/*!40000 ALTER TABLE `factory_inventory_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factory_performances`
--

DROP TABLE IF EXISTS `factory_performances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factory_performances` (
  `id` int NOT NULL AUTO_INCREMENT,
  `factory_id` int NOT NULL,
  `factory_performance_report` json DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `factory_id` (`factory_id`),
  CONSTRAINT `factory_performances_ibfk_1` FOREIGN KEY (`factory_id`) REFERENCES `factories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factory_performances`
--

LOCK TABLES `factory_performances` WRITE;
/*!40000 ALTER TABLE `factory_performances` DISABLE KEYS */;
INSERT INTO `factory_performances` VALUES (1,3,'{\"overallScore\": 37.19539, \"stageReports\": {\"3\": {\"errorRate\": 0.85776895, \"stageName\": \"Stage 1\", \"overallScore\": 7.112401, \"totalDeficits\": {}, \"totalTimeDays\": 99.0, \"factoryStageId\": 3, \"averageDeficits\": {}, \"totalExecutedStages\": 336.25372, \"resourceReadinessScore\": 0.0016973182, \"resourceUtilizationScore\": 14.2231045, \"averageExecutedStagesPerDay\": 0.22704166, \"daysUnderCapacityPercentage\": 0.0006060606, \"minimumExecutedCapacityPerDay\": 2.0335877}, \"4\": {\"errorRate\": 0.0, \"stageName\": \"Stage 2\", \"overallScore\": 50.0015, \"totalDeficits\": {}, \"totalTimeDays\": 99.0, \"factoryStageId\": 4, \"averageDeficits\": {}, \"totalExecutedStages\": 1262.8881, \"resourceReadinessScore\": 0.0029985532, \"resourceUtilizationScore\": 100.0, \"averageExecutedStagesPerDay\": 0.7683858, \"daysUnderCapacityPercentage\": 0.0006060606, \"minimumExecutedCapacityPerDay\": 8.333334}, \"5\": {\"errorRate\": 0.0, \"stageName\": \"Stage 3b - Independent\", \"overallScore\": 50.000175, \"totalDeficits\": {}, \"totalTimeDays\": 99.0, \"factoryStageId\": 5, \"averageDeficits\": {}, \"totalExecutedStages\": 51.074326, \"resourceReadinessScore\": 0.00035075514, \"resourceUtilizationScore\": 100.0, \"averageExecutedStagesPerDay\": 0.037521478, \"daysUnderCapacityPercentage\": 0.0006060606, \"minimumExecutedCapacityPerDay\": 0.45045048}, \"37\": {\"errorRate\": 0.16666667, \"stageName\": \"Another Stage\", \"overallScore\": 41.667492, \"totalDeficits\": {}, \"totalTimeDays\": 99.0, \"factoryStageId\": 37, \"averageDeficits\": {}, \"totalExecutedStages\": 0.0, \"resourceReadinessScore\": 0.0016589861, \"resourceUtilizationScore\": 83.33333, \"averageExecutedStagesPerDay\": 0.0, \"daysUnderCapacityPercentage\": 0.0006060606, \"minimumExecutedCapacityPerDay\": 0.0}}, \"resourceReadinessScore\": 0.0016764031, \"resourceUtilizationScore\": 74.38911, \"resourceDistributionScore\": 0.0}','2024-04-09 19:48:25','2024-08-13 14:31:56');
/*!40000 ALTER TABLE `factory_performances` ENABLE KEYS */;
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
INSERT INTO `factory_production_graphs` VALUES (3,'2024-03-14 08:34:54','2024-08-12 19:16:34','{\"nodes\": {\"3\": {\"priority\": 0, \"smallStage\": {\"id\": 3, \"stageName\": \"Stage 1\", \"stageInputs\": [{\"id\": 3, \"componentId\": 1, \"componentName\": \"Chip\", \"quantityPerStage\": 214.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}, {\"id\": 7, \"componentId\": 2, \"componentName\": \"Test Component 2\", \"quantityPerStage\": 12.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 4, \"productId\": null, \"componentId\": 1, \"productName\": null, \"componentName\": \"Chip\", \"outputPerRequest\": null, \"quantityPerStage\": 12.0, \"expectedOutputPerAllocation\": null}, {\"id\": 6, \"productId\": null, \"componentId\": 2, \"productName\": null, \"componentName\": \"Test Component 2\", \"outputPerRequest\": null, \"quantityPerStage\": 21.0, \"expectedOutputPerAllocation\": null}]}, \"perDuration\": 211.41, \"numberOfStepsCapacity\": 144.14, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": null}, \"4\": {\"priority\": 1, \"smallStage\": {\"id\": 4, \"stageName\": \"Stage 2\", \"stageInputs\": [{\"id\": 8, \"componentId\": 2, \"componentName\": \"Test Component 2\", \"quantityPerStage\": 123.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}, {\"id\": 9, \"componentId\": 3, \"componentName\": \"Microphone\", \"quantityPerStage\": 2.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 12, \"productId\": null, \"componentId\": 4, \"productName\": null, \"componentName\": \"Camera\", \"outputPerRequest\": null, \"quantityPerStage\": 123.0, \"expectedOutputPerAllocation\": null}, {\"id\": 13, \"productId\": null, \"componentId\": 3, \"productName\": null, \"componentName\": \"Microphone\", \"outputPerRequest\": null, \"quantityPerStage\": 231.0, \"expectedOutputPerAllocation\": null}, {\"id\": 7, \"productId\": null, \"componentId\": 4, \"productName\": null, \"componentName\": \"Camera\", \"outputPerRequest\": null, \"quantityPerStage\": 6.0, \"expectedOutputPerAllocation\": null}]}, \"perDuration\": 10.0, \"numberOfStepsCapacity\": 14.0, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": 0.9}, \"5\": {\"priority\": 2, \"smallStage\": {\"id\": 5, \"stageName\": \"Stage 3b - Independent\", \"stageInputs\": [{\"id\": 10, \"componentId\": 4, \"componentName\": \"Camera\", \"quantityPerStage\": 40.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 8, \"productId\": null, \"componentId\": 5, \"productName\": null, \"componentName\": \"Sensor\", \"outputPerRequest\": null, \"quantityPerStage\": 61.0, \"expectedOutputPerAllocation\": null}, {\"id\": 11, \"productId\": 21, \"componentId\": null, \"productName\": null, \"componentName\": null, \"outputPerRequest\": null, \"quantityPerStage\": 13.0, \"expectedOutputPerAllocation\": null}]}, \"perDuration\": 12.0, \"numberOfStepsCapacity\": 25.0, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": null}, \"37\": {\"priority\": 11, \"smallStage\": {\"id\": 37, \"stageName\": \"Another Stage\", \"stageInputs\": [{\"id\": 11, \"componentId\": 4, \"componentName\": \"Camera\", \"quantityPerStage\": 124.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 9, \"productId\": null, \"componentId\": 5, \"productName\": null, \"componentName\": \"Sensor\", \"outputPerRequest\": null, \"quantityPerStage\": 153.0, \"expectedOutputPerAllocation\": null}]}, \"perDuration\": 64800.0, \"numberOfStepsCapacity\": 124.0, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": null}, \"52\": {\"priority\": 12, \"smallStage\": {\"id\": 52, \"stageName\": \"First stage\", \"stageInputs\": [], \"stageOutputs\": []}, \"perDuration\": 12.0, \"numberOfStepsCapacity\": 12.0, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": 31.0}, \"53\": {\"priority\": 12, \"smallStage\": {\"id\": 53, \"stageName\": \"First stage\", \"stageInputs\": [], \"stageOutputs\": []}, \"perDuration\": 12.0, \"numberOfStepsCapacity\": 12.0, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": 23.0}, \"54\": {\"priority\": 3, \"smallStage\": {\"id\": 54, \"stageName\": \"EEEEnew new test\", \"stageInputs\": [{\"id\": 12, \"componentId\": 3, \"componentName\": \"Microphone\", \"quantityPerStage\": 213.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": []}, \"perDuration\": 4.0, \"numberOfStepsCapacity\": 123.0, \"allocationCapacityRatio\": null, \"minimumRequiredCapacity\": 123.0}}, \"adjList\": {\"3\": [{\"destStageInputId\": 8, \"srcStageOutputId\": 4, \"srcFactoryStageId\": 3, \"destFactoryStageId\": 4}, {\"destStageInputId\": 10, \"srcStageOutputId\": 4, \"srcFactoryStageId\": 3, \"destFactoryStageId\": 5}, {\"destStageInputId\": 8, \"srcStageOutputId\": 6, \"srcFactoryStageId\": 3, \"destFactoryStageId\": 4}, {\"destStageInputId\": 9, \"srcStageOutputId\": 6, \"srcFactoryStageId\": 3, \"destFactoryStageId\": 4}], \"4\": [], \"5\": [], \"37\": [{\"destStageInputId\": 10, \"srcStageOutputId\": 9, \"srcFactoryStageId\": 37, \"destFactoryStageId\": 5}], \"52\": [], \"53\": [], \"54\": []}, \"pipelinePriority\": null}',3);
/*!40000 ALTER TABLE `factory_production_graphs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factory_production_histories`
--

DROP TABLE IF EXISTS `factory_production_histories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factory_production_histories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `factory_id` int NOT NULL,
  `production_history` json DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `s3_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `factory_id` (`factory_id`),
  CONSTRAINT `factory_production_histories_ibfk_1` FOREIGN KEY (`factory_id`) REFERENCES `factories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factory_production_histories`
--

LOCK TABLES `factory_production_histories` WRITE;
/*!40000 ALTER TABLE `factory_production_histories` DISABLE KEYS */;
INSERT INTO `factory_production_histories` VALUES (1,3,'{\"startDate\": [2023, 2, 28, 9, 7, 56], \"dailyProductionRecords\": {\"293.0\": {\"results\": [{\"stageName\": \"Stage 1\", \"fullAmount\": 66.9419, \"componentId\": 2, \"actualAmount\": 4.0, \"componentName\": \"Test Component 2\", \"stageOutputId\": 6, \"factoryStageId\": 3, \"resultedAmount\": 4.035881}, {\"stageName\": \"Stage 1\", \"fullAmount\": 66.9419, \"componentId\": 1, \"actualAmount\": 2.0, \"componentName\": \"Chip\", \"stageOutputId\": 4, \"factoryStageId\": 3, \"resultedAmount\": 2.306218}, {\"stageName\": \"Stage 2\", \"fullAmount\": 528.3599, \"componentId\": 4, \"actualAmount\": 520.0, \"componentName\": \"Camera\", \"stageOutputId\": 7, \"factoryStageId\": 4, \"resultedAmount\": 528.3599}, {\"stageName\": \"Stage 3 - Indep\", \"fullAmount\": 5241.6665, \"componentId\": 5, \"actualAmount\": 100.0, \"componentName\": \"Sensor\", \"stageOutputId\": 8, \"factoryStageId\": 5, \"resultedAmount\": 198.74652}, {\"stageName\": \"Another Stage\", \"fullAmount\": 0.00048129508, \"componentId\": 5, \"actualAmount\": 0.0, \"componentName\": \"Sensor\", \"stageOutputId\": 9, \"factoryStageId\": 37, \"resultedAmount\": 0.0}], \"allocations\": [{\"stageName\": \"Stage 1\", \"componentId\": 1, \"actualAmount\": 112.0, \"stageInputId\": 3, \"componentName\": \"Chip\", \"factoryStageId\": 3, \"allocatedAmount\": 112.11343, \"requestedAmount\": 2549.2996, \"allocatorInventoryItemId\": 1}, {\"stageName\": \"Stage 1\", \"componentId\": 2, \"actualAmount\": 142.0, \"stageInputId\": 7, \"componentName\": \"Test Component 2\", \"factoryStageId\": 3, \"allocatedAmount\": 142.95139, \"requestedAmount\": 142.95139, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 2, \"actualAmount\": 513.0, \"stageInputId\": 8, \"componentName\": \"Test Component 2\", \"factoryStageId\": 4, \"allocatedAmount\": 513.6833, \"requestedAmount\": 513.6833, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 3, \"actualAmount\": 48.0, \"stageInputId\": 9, \"componentName\": \"Microphone\", \"factoryStageId\": 4, \"allocatedAmount\": 48.92222, \"requestedAmount\": 48.92222, \"allocatorInventoryItemId\": 4}, {\"stageName\": \"Stage 3 - Indep\", \"componentId\": 4, \"actualAmount\": 40.0, \"stageInputId\": 10, \"componentName\": \"Camera\", \"factoryStageId\": 5, \"allocatedAmount\": 44.165894, \"requestedAmount\": 1164.8148, \"allocatorInventoryItemId\": 5}, {\"stageName\": \"Another Stage\", \"componentId\": 4, \"actualAmount\": 0.0, \"stageInputId\": 11, \"componentName\": \"Camera\", \"factoryStageId\": 37, \"allocatedAmount\": 0.0, \"requestedAmount\": 0.7021245, \"allocatorInventoryItemId\": 5}], \"durationDays\": 17.0}, \"297.0\": {\"results\": [{\"stageName\": \"Stage 1\", \"fullAmount\": 66.9419, \"componentId\": 2, \"actualAmount\": 4.0, \"componentName\": \"Test Component 2\", \"stageOutputId\": 6, \"factoryStageId\": 3, \"resultedAmount\": 4.035881}, {\"stageName\": \"Stage 1\", \"fullAmount\": 66.9419, \"componentId\": 1, \"actualAmount\": 2.0, \"componentName\": \"Chip\", \"stageOutputId\": 4, \"factoryStageId\": 3, \"resultedAmount\": 2.306218}, {\"stageName\": \"Stage 2\", \"fullAmount\": 528.3599, \"componentId\": 4, \"actualAmount\": 520.0, \"componentName\": \"Camera\", \"stageOutputId\": 7, \"factoryStageId\": 4, \"resultedAmount\": 528.3599}, {\"stageName\": \"Stage 3 - Indep\", \"fullAmount\": 5241.6665, \"componentId\": 5, \"actualAmount\": 100.0, \"componentName\": \"Sensor\", \"stageOutputId\": 8, \"factoryStageId\": 5, \"resultedAmount\": 198.74652}, {\"stageName\": \"Another Stage\", \"fullAmount\": 0.00048129508, \"componentId\": 5, \"actualAmount\": 0.0, \"componentName\": \"Sensor\", \"stageOutputId\": 9, \"factoryStageId\": 37, \"resultedAmount\": 0.0}], \"allocations\": [{\"stageName\": \"Stage 1\", \"componentId\": 1, \"actualAmount\": 112.0, \"stageInputId\": 3, \"componentName\": \"Chip\", \"factoryStageId\": 3, \"allocatedAmount\": 112.11343, \"requestedAmount\": 2549.2996, \"allocatorInventoryItemId\": 1}, {\"stageName\": \"Stage 1\", \"componentId\": 2, \"actualAmount\": 142.0, \"stageInputId\": 7, \"componentName\": \"Test Component 2\", \"factoryStageId\": 3, \"allocatedAmount\": 142.95139, \"requestedAmount\": 142.95139, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 2, \"actualAmount\": 513.0, \"stageInputId\": 8, \"componentName\": \"Test Component 2\", \"factoryStageId\": 4, \"allocatedAmount\": 513.6833, \"requestedAmount\": 513.6833, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 3, \"actualAmount\": 48.0, \"stageInputId\": 9, \"componentName\": \"Microphone\", \"factoryStageId\": 4, \"allocatedAmount\": 48.92222, \"requestedAmount\": 48.92222, \"allocatorInventoryItemId\": 4}, {\"stageName\": \"Stage 3 - Indep\", \"componentId\": 4, \"actualAmount\": 40.0, \"stageInputId\": 10, \"componentName\": \"Camera\", \"factoryStageId\": 5, \"allocatedAmount\": 44.165894, \"requestedAmount\": 1164.8148, \"allocatorInventoryItemId\": 5}, {\"stageName\": \"Another Stage\", \"componentId\": 4, \"actualAmount\": 0.0, \"stageInputId\": 11, \"componentName\": \"Camera\", \"factoryStageId\": 37, \"allocatedAmount\": 0.0, \"requestedAmount\": 0.7021245, \"allocatorInventoryItemId\": 5}], \"durationDays\": 17.0}, \"307.0\": {\"results\": [{\"stageName\": \"Stage 1\", \"fullAmount\": 66.9419, \"componentId\": 2, \"actualAmount\": 4.0, \"componentName\": \"Test Component 2\", \"stageOutputId\": 6, \"factoryStageId\": 3, \"resultedAmount\": 4.035881}, {\"stageName\": \"Stage 1\", \"fullAmount\": 66.9419, \"componentId\": 1, \"actualAmount\": 2.0, \"componentName\": \"Chip\", \"stageOutputId\": 4, \"factoryStageId\": 3, \"resultedAmount\": 2.306218}, {\"stageName\": \"Stage 2\", \"fullAmount\": 528.3599, \"componentId\": 4, \"actualAmount\": 520.0, \"componentName\": \"Camera\", \"stageOutputId\": 7, \"factoryStageId\": 4, \"resultedAmount\": 528.3599}, {\"stageName\": \"Stage 3 - Indep\", \"fullAmount\": 5241.6665, \"componentId\": 5, \"actualAmount\": 100.0, \"componentName\": \"Sensor\", \"stageOutputId\": 8, \"factoryStageId\": 5, \"resultedAmount\": 198.74652}, {\"stageName\": \"Another Stage\", \"fullAmount\": 0.00048129508, \"componentId\": 5, \"actualAmount\": 0.0, \"componentName\": \"Sensor\", \"stageOutputId\": 9, \"factoryStageId\": 37, \"resultedAmount\": 0.0}], \"allocations\": [{\"stageName\": \"Stage 1\", \"componentId\": 1, \"actualAmount\": 112.0, \"stageInputId\": 3, \"componentName\": \"Chip\", \"factoryStageId\": 3, \"allocatedAmount\": 112.11343, \"requestedAmount\": 2549.2996, \"allocatorInventoryItemId\": 1}, {\"stageName\": \"Stage 1\", \"componentId\": 2, \"actualAmount\": 142.0, \"stageInputId\": 7, \"componentName\": \"Test Component 2\", \"factoryStageId\": 3, \"allocatedAmount\": 142.95139, \"requestedAmount\": 142.95139, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 2, \"actualAmount\": 513.0, \"stageInputId\": 8, \"componentName\": \"Test Component 2\", \"factoryStageId\": 4, \"allocatedAmount\": 513.6833, \"requestedAmount\": 513.6833, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 3, \"actualAmount\": 48.0, \"stageInputId\": 9, \"componentName\": \"Microphone\", \"factoryStageId\": 4, \"allocatedAmount\": 48.92222, \"requestedAmount\": 48.92222, \"allocatorInventoryItemId\": 4}, {\"stageName\": \"Stage 3 - Indep\", \"componentId\": 4, \"actualAmount\": 40.0, \"stageInputId\": 10, \"componentName\": \"Camera\", \"factoryStageId\": 5, \"allocatedAmount\": 44.165894, \"requestedAmount\": 1164.8148, \"allocatorInventoryItemId\": 5}, {\"stageName\": \"Another Stage\", \"componentId\": 4, \"actualAmount\": 0.0, \"stageInputId\": 11, \"componentName\": \"Camera\", \"factoryStageId\": 37, \"allocatedAmount\": 0.0, \"requestedAmount\": 0.7021245, \"allocatorInventoryItemId\": 5}], \"durationDays\": 17.0}, \"344.0\": {\"results\": [{\"stageName\": \"Stage 1\", \"fullAmount\": 3.8313332, \"componentId\": 2, \"actualAmount\": 0.2, \"componentName\": \"Test Component 2\", \"stageOutputId\": 6, \"factoryStageId\": 3, \"resultedAmount\": 0.23098843}, {\"stageName\": \"Stage 1\", \"fullAmount\": 3.8313332, \"componentId\": 1, \"actualAmount\": 0.1, \"componentName\": \"Chip\", \"stageOutputId\": 4, \"factoryStageId\": 3, \"resultedAmount\": 0.1319934}, {\"stageName\": \"Stage 2\", \"fullAmount\": 30.239998, \"componentId\": 4, \"actualAmount\": 30.0, \"componentName\": \"Camera\", \"stageOutputId\": 7, \"factoryStageId\": 4, \"resultedAmount\": 30.239998}, {\"stageName\": \"Stage 3 - Indep\", \"fullAmount\": 300.0, \"componentId\": 5, \"actualAmount\": 11.0, \"componentName\": \"Sensor\", \"stageOutputId\": 8, \"factoryStageId\": 5, \"resultedAmount\": 11.375}, {\"stageName\": \"Another Stage\", \"fullAmount\": 0.0000275463, \"componentId\": 5, \"actualAmount\": 0.0, \"componentName\": \"Sensor\", \"stageOutputId\": 9, \"factoryStageId\": 37, \"resultedAmount\": 0.0}], \"allocations\": [{\"stageName\": \"Stage 1\", \"componentId\": 1, \"actualAmount\": 6.0, \"stageInputId\": 3, \"componentName\": \"Chip\", \"factoryStageId\": 3, \"allocatedAmount\": 6.4166665, \"requestedAmount\": 145.90587, \"allocatorInventoryItemId\": 1}, {\"stageName\": \"Stage 1\", \"componentId\": 2, \"actualAmount\": 8.0, \"stageInputId\": 7, \"componentName\": \"Test Component 2\", \"factoryStageId\": 3, \"allocatedAmount\": 8.181638, \"requestedAmount\": 8.181638, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 2, \"actualAmount\": 29.0, \"stageInputId\": 8, \"componentName\": \"Test Component 2\", \"factoryStageId\": 4, \"allocatedAmount\": 29.399998, \"requestedAmount\": 29.399998, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 3, \"actualAmount\": 2.0, \"stageInputId\": 9, \"componentName\": \"Microphone\", \"factoryStageId\": 4, \"allocatedAmount\": 2.8, \"requestedAmount\": 2.8, \"allocatorInventoryItemId\": 4}, {\"stageName\": \"Stage 3 - Indep\", \"componentId\": 4, \"actualAmount\": 2.5, \"stageInputId\": 10, \"componentName\": \"Camera\", \"factoryStageId\": 5, \"allocatedAmount\": 2.527778, \"requestedAmount\": 66.666664, \"allocatorInventoryItemId\": 5}, {\"stageName\": \"Another Stage\", \"componentId\": 4, \"actualAmount\": 0.04, \"stageInputId\": 11, \"componentName\": \"Camera\", \"factoryStageId\": 37, \"allocatedAmount\": 0.0, \"requestedAmount\": 0.040185187, \"allocatorInventoryItemId\": 5}], \"durationDays\": 1.0}, \"367.0\": {\"results\": [{\"stageName\": \"Stage 1\", \"fullAmount\": 38.31333, \"componentId\": 2, \"actualAmount\": 2.0, \"componentName\": \"Test Component 2\", \"stageOutputId\": 6, \"factoryStageId\": 3, \"resultedAmount\": 2.3098843}, {\"stageName\": \"Stage 1\", \"fullAmount\": 38.31333, \"componentId\": 1, \"actualAmount\": 1.0, \"componentName\": \"Chip\", \"stageOutputId\": 4, \"factoryStageId\": 3, \"resultedAmount\": 1.319934}, {\"stageName\": \"Stage 2\", \"fullAmount\": 302.4, \"componentId\": 4, \"actualAmount\": 180.0, \"componentName\": \"Camera\", \"stageOutputId\": 7, \"factoryStageId\": 4, \"resultedAmount\": 302.4}, {\"stageName\": \"Stage 3 - Indep\", \"fullAmount\": 3000.0002, \"componentId\": 5, \"actualAmount\": 110.0, \"componentName\": \"Sensor\", \"stageOutputId\": 8, \"factoryStageId\": 5, \"resultedAmount\": 113.75001}, {\"stageName\": \"Another Stage\", \"fullAmount\": 0.000275463, \"componentId\": 5, \"actualAmount\": 0.0, \"componentName\": \"Sensor\", \"stageOutputId\": 9, \"factoryStageId\": 37, \"resultedAmount\": 0.0}], \"allocations\": [{\"stageName\": \"Stage 1\", \"componentId\": 1, \"actualAmount\": 64.0, \"stageInputId\": 3, \"componentName\": \"Chip\", \"factoryStageId\": 3, \"allocatedAmount\": 64.16667, \"requestedAmount\": 1459.0587, \"allocatorInventoryItemId\": 1}, {\"stageName\": \"Stage 1\", \"componentId\": 2, \"actualAmount\": 81.0, \"stageInputId\": 7, \"componentName\": \"Test Component 2\", \"factoryStageId\": 3, \"allocatedAmount\": 81.816376, \"requestedAmount\": 81.816376, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 2, \"actualAmount\": 220.0, \"stageInputId\": 8, \"componentName\": \"Test Component 2\", \"factoryStageId\": 4, \"allocatedAmount\": 294.0, \"requestedAmount\": 294.0, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 3, \"actualAmount\": 20.0, \"stageInputId\": 9, \"componentName\": \"Microphone\", \"factoryStageId\": 4, \"allocatedAmount\": 28.0, \"requestedAmount\": 28.0, \"allocatorInventoryItemId\": 4}, {\"stageName\": \"Stage 3 - Indep\", \"componentId\": 4, \"actualAmount\": 25.0, \"stageInputId\": 10, \"componentName\": \"Camera\", \"factoryStageId\": 5, \"allocatedAmount\": 25.277779, \"requestedAmount\": 666.6667, \"allocatorInventoryItemId\": 5}, {\"stageName\": \"Another Stage\", \"componentId\": 4, \"actualAmount\": 0.0, \"stageInputId\": 11, \"componentName\": \"Camera\", \"factoryStageId\": 37, \"allocatedAmount\": 0.0, \"requestedAmount\": 0.4018519, \"allocatorInventoryItemId\": 5}], \"durationDays\": 10.0}, \"378.0\": {\"results\": [{\"stageName\": \"Stage 1\", \"fullAmount\": 141.75932, \"componentId\": 2, \"actualAmount\": 8.5, \"componentName\": \"Test Component 2\", \"stageOutputId\": 6, \"factoryStageId\": 3, \"resultedAmount\": 8.546572}, {\"stageName\": \"Stage 1\", \"fullAmount\": 141.75932, \"componentId\": 1, \"actualAmount\": 2.0, \"componentName\": \"Chip\", \"stageOutputId\": 4, \"factoryStageId\": 3, \"resultedAmount\": 4.8837557}, {\"stageName\": \"Stage 2\", \"fullAmount\": 1118.8799, \"componentId\": 4, \"actualAmount\": 1000.0, \"componentName\": \"Camera\", \"stageOutputId\": 7, \"factoryStageId\": 4, \"resultedAmount\": 1118.8799}, {\"stageName\": \"Stage 3 - Indep\", \"fullAmount\": 11100.0, \"componentId\": 5, \"actualAmount\": 200.0, \"componentName\": \"Sensor\", \"stageOutputId\": 8, \"factoryStageId\": 5, \"resultedAmount\": 420.875}, {\"stageName\": \"Another Stage\", \"fullAmount\": 0.0010192131, \"componentId\": 5, \"actualAmount\": 0.0, \"componentName\": \"Sensor\", \"stageOutputId\": 9, \"factoryStageId\": 37, \"resultedAmount\": 0.0}], \"allocations\": [{\"stageName\": \"Stage 1\", \"componentId\": 1, \"actualAmount\": 237.0, \"stageInputId\": 3, \"componentName\": \"Chip\", \"factoryStageId\": 3, \"allocatedAmount\": 237.41667, \"requestedAmount\": 5398.517, \"allocatorInventoryItemId\": 1}, {\"stageName\": \"Stage 1\", \"componentId\": 2, \"actualAmount\": 302.0, \"stageInputId\": 7, \"componentName\": \"Test Component 2\", \"factoryStageId\": 3, \"allocatedAmount\": 302.72058, \"requestedAmount\": 302.72058, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 2, \"actualAmount\": 1000.0, \"stageInputId\": 8, \"componentName\": \"Test Component 2\", \"factoryStageId\": 4, \"allocatedAmount\": 1087.7999, \"requestedAmount\": 1087.7999, \"allocatorInventoryItemId\": 3}, {\"stageName\": \"Stage 2\", \"componentId\": 3, \"actualAmount\": 100.0, \"stageInputId\": 9, \"componentName\": \"Microphone\", \"factoryStageId\": 4, \"allocatedAmount\": 103.6, \"requestedAmount\": 103.6, \"allocatorInventoryItemId\": 4}, {\"stageName\": \"Stage 3 - Indep\", \"componentId\": 4, \"actualAmount\": 80.0, \"stageInputId\": 10, \"componentName\": \"Camera\", \"factoryStageId\": 5, \"allocatedAmount\": 93.52778, \"requestedAmount\": 2466.6667, \"allocatorInventoryItemId\": 5}, {\"stageName\": \"Another Stage\", \"componentId\": 4, \"actualAmount\": 0.0, \"stageInputId\": 11, \"componentName\": \"Camera\", \"factoryStageId\": 37, \"allocatedAmount\": 0.0, \"requestedAmount\": 1.4868519, \"allocatorInventoryItemId\": 5}], \"durationDays\": 37.0}}}','2024-04-08 18:25:10','2024-04-19 13:02:26','productionHistories/factory-3');
/*!40000 ALTER TABLE `factory_production_histories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factory_stage_connections`
--

DROP TABLE IF EXISTS `factory_stage_connections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factory_stage_connections` (
  `id` int NOT NULL AUTO_INCREMENT,
  `factory_id` int NOT NULL,
  `outgoing_factory_stage_id` bigint DEFAULT NULL,
  `incoming_factory_stage_id` bigint DEFAULT NULL,
  `outgoing_stage_output_id` int DEFAULT NULL,
  `incoming_stage_input_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `factory_id` (`factory_id`),
  KEY `incoming_factory_stage_id` (`incoming_factory_stage_id`),
  KEY `outgoing_factory_stage_id` (`outgoing_factory_stage_id`),
  KEY `outgoing_stage_output_id` (`outgoing_stage_output_id`),
  KEY `incoming_stage_input_id` (`incoming_stage_input_id`),
  CONSTRAINT `factory_stage_connections_ibfk_3` FOREIGN KEY (`factory_id`) REFERENCES `factories` (`id`),
  CONSTRAINT `factory_stage_connections_ibfk_4` FOREIGN KEY (`incoming_factory_stage_id`) REFERENCES `factory_stages` (`id`),
  CONSTRAINT `factory_stage_connections_ibfk_5` FOREIGN KEY (`outgoing_factory_stage_id`) REFERENCES `factory_stages` (`id`),
  CONSTRAINT `factory_stage_connections_ibfk_6` FOREIGN KEY (`outgoing_stage_output_id`) REFERENCES `stage_outputs` (`id`),
  CONSTRAINT `factory_stage_connections_ibfk_7` FOREIGN KEY (`incoming_stage_input_id`) REFERENCES `stage_inputs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factory_stage_connections`
--

LOCK TABLES `factory_stage_connections` WRITE;
/*!40000 ALTER TABLE `factory_stage_connections` DISABLE KEYS */;
INSERT INTO `factory_stage_connections` VALUES (7,3,3,4,4,8),(8,3,3,5,4,10),(9,3,37,5,9,10),(12,3,3,4,6,8),(13,3,3,4,6,9);
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
  `organization_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `factory_id` (`factory_id`),
  KEY `stage_id` (`stage_id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `factory_stages_ibfk_1` FOREIGN KEY (`factory_id`) REFERENCES `factories` (`id`),
  CONSTRAINT `factory_stages_ibfk_2` FOREIGN KEY (`stage_id`) REFERENCES `stages` (`id`),
  CONSTRAINT `factory_stages_ibfk_3` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factory_stages`
--

LOCK TABLES `factory_stages` WRITE;
/*!40000 ALTER TABLE `factory_stages` DISABLE KEYS */;
INSERT INTO `factory_stages` VALUES (3,3,5,144.14,211.41,1,NULL,0,NULL),(4,3,7,14,10,1,0.9,1,NULL),(5,3,8,25,12,1,NULL,2,NULL),(37,3,9,124,64800,NULL,NULL,11,NULL),(39,26,1,213,321,NULL,NULL,321,NULL),(40,26,1,121,12,NULL,412,13,NULL),(41,26,1,31,31,NULL,31,42,NULL),(42,26,1,214,31,NULL,14,31,NULL),(43,26,1,21,121,NULL,113,31,NULL),(44,26,1,3141,13,NULL,3113,1442,NULL),(45,26,1,123,31,NULL,42,133,NULL),(52,3,1,12,12,NULL,31,12,NULL),(53,3,1,12,12,NULL,23,12,NULL),(54,3,14,123,4,NULL,123,3,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
INSERT INTO `locations` VALUES (1,'Hemingway Boulevard no. 13','New Jersey','New Jersey','USA',21.12152,41.6325,1,'142142'),(2,'Hemingway Boulevard no. 13','New Jersey','New Jersey','USA',21.12152,41.6325,2,'142142'),(3,'New Way Boulevard no 2','LA','California','USA',12.14215,53.1241,1,'142134'),(4,'Freemont Boulevard no 2','Chicago','Illinois','USA',19.14215,40.1241,1,'142134'),(5,'HK street no 4','Shenzen','Shenzen','China',70.14215,10.1241,1,'142134'),(6,'Ichio street no 4','Tokio',NULL,'Japan',90.14215,12.1241,1,'142134'),(8,'Strada Avram Iancu no. 20','Bucharest',NULL,'Romania',80.1,41.4,1,'077081'),(9,'Churchill Boulevard no. 211','London','','UK',12.41,51.14,1,'12151'),(10,'','Hannover','','Germany',NULL,NULL,1,''),(11,'La View Boulevard','Paris','','France',12.42,NULL,1,'01204'),(12,'JFK Boulevard no. 20','New York','New York','USA',12.31,41.21,1,'012041'),(13,'Herbrew Street','Jerusalem','','Israel',NULL,NULL,1,''),(14,'Templeton Street, no 20','Chicago Downtown','Illinois','USA',NULL,NULL,1,'13252'),(15,'Heinburg Dan','Innsbruck','','Switzerland',1.21,90.12,1,'12352'),(16,'455 Market St','San Franscisco','CA','USA',NULL,NULL,1,'94105'),(17,'455 Market St','San Franscisco','CA','USA',37.79079189999999,-122.3984543,1,'94105'),(18,NULL,'Berlin',NULL,'Germany',52.52000659999999,13.404954,1,NULL),(19,NULL,'Berlin',NULL,'Germany',NULL,NULL,1,NULL),(20,'Test',NULL,NULL,NULL,NULL,NULL,1,NULL),(21,NULL,'Test',NULL,NULL,NULL,NULL,1,NULL),(22,'St Montgomerry','Timbuktu',NULL,'Timbuktu',22,5,1,'2131'),(23,'New St Peter','Omaha','Omaha','USA',39.6,-98.3,1,'123'),(24,NULL,NULL,NULL,NULL,36.7,-119.4,1,'1353'),(25,'w','e','e','q',12132,2,1,''),(32,'','','','',12.2,12.21,1,''),(33,'','','','',11.2,2.2,1,''),(34,'','','','',2,1,1,''),(35,'','','','',21,-10,1,''),(36,'','','','',21,1,1,''),(37,'St. Boulevard','Chicago','','',20,-15,1,''),(38,'HK street no 4','Shenzen','','China',70.14215,10.1241,1,'142134'),(39,'New','Boston','','USA',45,-20,1,'');
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
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `read_status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `notification_id` (`notification_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `notification_users_ibfk_1` FOREIGN KEY (`notification_id`) REFERENCES `notifications` (`id`),
  CONSTRAINT `notification_users_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_users`
--

LOCK TABLES `notification_users` WRITE;
/*!40000 ALTER TABLE `notification_users` DISABLE KEYS */;
INSERT INTO `notification_users` VALUES (1,1,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(2,2,'086e9e96-a8ef-11ee-bffa-00155de90539',1),(3,5,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(4,6,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(5,7,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(6,8,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(7,9,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(8,10,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(9,11,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(10,12,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(11,13,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(12,14,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(13,15,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(14,16,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(15,17,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(16,18,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(17,19,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(18,20,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(19,21,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(20,22,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(21,23,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(22,24,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(23,25,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(24,26,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(25,27,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(26,28,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(27,29,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(28,30,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(29,31,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(30,32,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(31,33,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(32,34,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(33,35,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(34,36,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(35,37,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(36,38,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(37,39,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(38,40,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(39,41,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(40,42,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(41,43,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(42,44,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(43,45,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(44,46,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(45,47,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(46,48,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(47,49,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(48,50,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(49,51,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(50,52,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(51,53,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(52,54,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(53,55,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(54,56,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(55,57,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(56,58,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(57,59,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(58,60,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(59,61,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(60,62,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(61,63,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(62,64,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(63,67,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(64,68,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(65,68,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(66,69,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(67,69,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(68,70,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(69,70,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(70,71,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(71,71,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(72,72,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(73,72,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(74,73,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(75,73,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(76,74,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(77,74,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(78,75,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(79,75,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(80,76,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(81,76,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(82,77,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(83,77,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(84,78,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(85,78,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(86,79,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(87,79,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(88,80,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(89,80,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(90,81,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(91,81,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(92,82,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(93,82,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(94,83,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(95,83,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(96,84,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(97,84,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(98,85,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(99,85,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(100,86,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(101,86,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(102,87,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(103,87,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(104,88,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(105,88,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(106,89,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(107,89,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(108,90,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(109,90,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(110,91,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(111,91,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(112,92,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(113,92,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(114,93,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(115,93,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(116,94,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(117,94,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(118,95,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(119,95,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(120,96,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(121,97,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(122,98,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(123,98,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(124,99,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(125,99,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(126,100,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(127,100,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(128,101,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(129,101,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(130,102,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(131,102,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(132,103,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(133,103,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(134,104,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(135,104,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(136,105,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(137,105,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(138,106,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(139,106,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(140,107,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(141,107,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(142,108,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(143,108,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(144,109,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(145,109,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(146,110,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(147,110,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(148,111,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(149,111,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(150,112,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(151,112,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(152,113,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(153,113,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(154,114,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(155,114,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(156,115,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(157,115,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(158,116,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(159,116,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(160,117,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(161,117,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(162,118,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(163,118,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(164,119,'086e9e96-a8ef-11ee-bffa-00155de90539',0),(165,119,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(166,120,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(167,121,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(168,122,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0),(169,123,'430f3a7b-6d9b-411e-95a3-eb794ba3c430',0);
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
  `entity_type` enum('MEMBER','CUSTOM_ROLE','PRODUCT','PRODUCT_STAGE','COMPONENT','UNIT_OF_MEASUREMENT','FACTORY','FACTORY_STAGE','RESOURCE_ALLOCATION_PLAN','FACTORY_INVENTORY','FACTORY_PRODUCTION_HISTORY','FACTORY_PERFORMANCE','WAREHOUSE','WAREHOUSE_INVENTORY','SUPPLIER','SUPPLIER_ORDER','SUPPLIER_SHIPMENT','SUPPLIER_PERFORMANCE','CLIENT','CLIENT_ORDER','CLIENT_SHIPMENT','CLIENT_EVALUATION') DEFAULT NULL,
  `message` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `read_status` tinyint(1) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `extra_info` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,'Supplier Order 1 Alert',21,'SUPPLIER_ORDER','The Supplier Order 1 has arrived at the Factory','2024-03-28 22:18:36','2024-04-18 15:00:35',0,'Alert',NULL),(2,'Client Order #213 Alert',2,'SUPPLIER_ORDER','The Client Akia Motors has published an order','2024-03-28 22:20:51','2024-04-18 15:00:35',0,'Alert',NULL),(4,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-03 11:56:48','2024-04-18 15:00:35',0,NULL,NULL),(5,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-03 11:57:08','2024-04-18 15:00:35',0,NULL,NULL),(6,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-03 14:14:20','2024-04-18 15:00:35',0,NULL,NULL),(7,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-03 14:14:20','2024-04-18 15:00:35',0,NULL,NULL),(8,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 12:43:56','2024-04-18 15:00:35',0,NULL,NULL),(9,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 12:43:56','2024-04-18 15:00:35',0,NULL,NULL),(10,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 12:43:56','2024-04-18 15:00:35',0,NULL,NULL),(11,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 12:43:56','2024-04-18 15:00:35',0,NULL,NULL),(12,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 12:43:56','2024-04-18 15:00:35',0,NULL,NULL),(13,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 12:43:57','2024-04-18 15:00:35',0,NULL,NULL),(14,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 12:43:57','2024-04-18 15:00:35',0,NULL,NULL),(15,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:34:49','2024-04-18 15:00:35',0,NULL,NULL),(16,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:34:49','2024-04-18 15:00:35',0,NULL,NULL),(17,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:34:50','2024-04-18 15:00:35',0,NULL,NULL),(18,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:34:50','2024-04-18 15:00:35',0,NULL,NULL),(19,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:34:50','2024-04-18 15:00:34',0,NULL,NULL),(20,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:34:50','2024-04-18 15:00:34',0,NULL,NULL),(21,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:34:50','2024-04-18 15:00:34',0,NULL,NULL),(22,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:36:10','2024-04-18 15:00:34',0,NULL,NULL),(23,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:36:10','2024-04-18 15:00:34',0,NULL,NULL),(24,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:36:10','2024-04-18 15:00:34',0,NULL,NULL),(25,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:36:10','2024-04-18 15:00:34',0,NULL,NULL),(26,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:36:10','2024-04-18 15:00:34',0,NULL,NULL),(27,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:36:10','2024-04-18 15:00:34',0,NULL,NULL),(28,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-04 14:36:10','2024-04-18 15:00:34',0,NULL,NULL),(29,'Supplier Order',NULL,'SUPPLIER_ORDER','A Supplier Order has been deleted','2024-04-05 20:24:59','2024-04-18 15:00:34',0,NULL,NULL),(30,'Supplier Order',NULL,'SUPPLIER_ORDER','A Supplier Order has been deleted','2024-04-05 20:24:59','2024-04-18 15:00:34',0,NULL,NULL),(31,'Supplier Order',NULL,'SUPPLIER_ORDER','A Supplier Order has been deleted','2024-04-05 20:24:59','2024-04-18 15:00:34',0,NULL,NULL),(32,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-16 15:58:27','2024-04-18 15:00:34',0,NULL,NULL),(33,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-16 18:59:41','2024-04-18 15:00:34',0,NULL,NULL),(34,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-17 08:52:47','2024-04-18 15:00:34',0,NULL,NULL),(35,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-17 12:33:11','2024-04-18 15:00:34',0,NULL,NULL),(36,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-17 12:38:03','2024-04-18 15:00:34',0,NULL,NULL),(37,'Supplier Order',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created','2024-04-17 13:10:32','2024-04-18 15:00:34',0,NULL,NULL),(38,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Order Test','2024-04-17 15:19:31','2024-04-18 15:00:33',0,NULL,NULL),(39,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Order Test','2024-04-17 16:04:55','2024-04-18 15:00:33',0,NULL,NULL),(40,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Order Test','2024-04-17 19:01:59','2024-04-18 15:00:33',0,NULL,NULL),(41,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Order Test','2024-04-17 19:51:30','2024-04-18 15:00:34',0,NULL,NULL),(42,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-04-24 08:25:39','2024-04-24 08:25:39',0,NULL,NULL),(43,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-04-24 08:26:37','2024-04-24 08:26:37',0,NULL,NULL),(44,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-04-24 08:27:33','2024-04-24 08:27:33',0,NULL,NULL),(45,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-04-26 08:23:11','2024-04-26 08:23:11',0,NULL,NULL),(46,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-04-26 08:23:11','2024-04-26 08:23:11',0,NULL,NULL),(47,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-04-26 08:35:43','2024-04-26 08:35:43',0,NULL,NULL),(48,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-04-26 08:40:16','2024-04-26 08:40:16',0,NULL,NULL),(49,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-04-26 08:50:13','2024-04-26 08:50:13',0,NULL,NULL),(50,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-26 10:44:44','2024-04-26 10:44:44',0,NULL,NULL),(51,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-26 10:48:49','2024-04-26 10:48:49',0,NULL,NULL),(52,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-04-26 10:53:34','2024-04-26 10:53:34',0,NULL,NULL),(53,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Client Test','2024-04-26 10:57:17','2024-04-26 10:57:17',0,NULL,NULL),(54,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-26 11:04:36','2024-04-26 11:04:36',0,NULL,NULL),(55,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-26 11:15:46','2024-04-26 11:15:46',0,NULL,NULL),(56,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-26 11:33:15','2024-04-26 11:33:15',0,NULL,NULL),(57,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-26 13:44:56','2024-04-26 13:44:56',0,NULL,NULL),(58,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-26 13:54:44','2024-04-26 13:54:44',0,NULL,NULL),(59,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-26 13:55:42','2024-04-26 13:55:42',0,NULL,NULL),(60,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-26 13:57:09','2024-04-26 13:57:09',0,NULL,NULL),(61,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-26 13:59:30','2024-04-26 13:59:30',0,NULL,NULL),(62,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-26 13:59:44','2024-04-26 13:59:44',0,NULL,NULL),(63,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-26 14:08:07','2024-04-26 14:08:07',0,NULL,NULL),(64,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-04-26 14:08:56','2024-04-26 14:08:56',0,NULL,NULL),(65,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-04-26 14:09:23','2024-04-26 14:09:23',0,NULL,NULL),(66,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-04-26 14:09:39','2024-04-26 14:09:39',0,NULL,NULL),(67,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-04-26 14:11:09','2024-04-26 14:11:09',0,NULL,NULL),(68,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-30 03:50:37','2024-04-30 06:50:37',0,NULL,NULL),(69,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-30 04:54:14','2024-04-30 07:54:14',0,NULL,NULL),(70,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Client Test','2024-04-30 09:37:12','2024-04-30 12:37:12',0,NULL,NULL),(71,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-04-30 09:38:01','2024-04-30 12:38:01',0,NULL,NULL),(72,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-05-02 06:22:59','2024-05-02 09:22:59',0,NULL,NULL),(73,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-05-02 06:26:31','2024-05-02 09:26:31',0,NULL,NULL),(74,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-05-02 06:34:25','2024-05-02 09:34:25',0,NULL,NULL),(75,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-05-02 06:34:53','2024-05-02 09:34:53',0,NULL,NULL),(76,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Client Test','2024-05-02 06:37:56','2024-05-02 09:37:56',0,NULL,NULL),(77,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-05-02 06:38:20','2024-05-02 09:38:20',0,NULL,NULL),(78,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-05-02 06:40:28','2024-05-02 09:40:28',0,NULL,NULL),(79,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-05-02 06:41:28','2024-05-02 09:41:28',0,NULL,NULL),(80,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-05-02 06:45:30','2024-05-02 09:45:30',0,NULL,NULL),(81,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-05-02 06:47:13','2024-05-02 09:47:13',0,NULL,NULL),(82,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-05-02 06:50:05','2024-05-02 09:50:05',0,NULL,NULL),(83,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-05-02 06:55:25','2024-05-02 09:55:25',0,NULL,NULL),(84,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Client Test','2024-05-02 07:00:32','2024-05-02 10:00:32',0,NULL,NULL),(85,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-05-02 08:23:45','2024-05-02 11:23:45',0,NULL,NULL),(86,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-05-02 08:23:45','2024-05-02 11:23:45',0,NULL,NULL),(87,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-05-02 08:24:34','2024-05-02 11:24:34',0,NULL,NULL),(88,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-05-02 08:24:34','2024-05-02 11:24:34',0,NULL,NULL),(89,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-05-02 08:26:49','2024-05-02 11:26:49',0,NULL,NULL),(90,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-05-02 08:26:50','2024-05-02 11:26:50',0,NULL,NULL),(91,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-05-28 03:19:39','2024-05-28 06:19:39',0,NULL,NULL),(92,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-05-28 03:19:39','2024-05-28 06:19:39',0,NULL,NULL),(93,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-05-28 03:20:07','2024-05-28 06:20:07',0,NULL,NULL),(94,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-04 16:51:35','2024-08-04 19:51:35',0,NULL,NULL),(95,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-04 16:57:14','2024-08-04 19:57:14',0,NULL,NULL),(96,'Client Order Added',NULL,'CLIENT_ORDER','A new Client Order has been created for Client Test','2024-08-04 16:59:19','2024-08-04 19:59:19',0,NULL,NULL),(97,'Client Order Added',NULL,'CLIENT_ORDER','A new Client Order has been created for Client Test','2024-08-04 17:04:46','2024-08-04 20:04:46',0,NULL,NULL),(98,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-04 17:14:07','2024-08-04 20:14:07',0,NULL,NULL),(99,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-04 17:20:45','2024-08-04 20:20:45',0,NULL,NULL),(100,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-07 17:43:08','2024-08-07 20:43:08',0,NULL,NULL),(101,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-07 17:46:56','2024-08-07 20:46:56',0,NULL,NULL),(102,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-08 18:58:52','2024-08-08 21:58:52',0,NULL,NULL),(103,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-09 04:03:06','2024-08-09 07:03:06',0,NULL,NULL),(104,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-09 04:03:06','2024-08-09 07:03:06',0,NULL,NULL),(105,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-09 04:05:36','2024-08-09 07:05:36',0,NULL,NULL),(106,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-09 04:10:47','2024-08-09 07:10:47',0,NULL,NULL),(107,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-09 04:26:37','2024-08-09 07:26:37',0,NULL,NULL),(108,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-09 04:30:13','2024-08-09 07:30:13',0,NULL,NULL),(109,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-09 04:35:05','2024-08-09 07:35:05',0,NULL,NULL),(110,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-09 04:37:06','2024-08-09 07:37:06',0,NULL,NULL),(111,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-09 04:47:08','2024-08-09 07:47:08',0,NULL,NULL),(112,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-09 04:50:14','2024-08-09 07:50:14',0,NULL,NULL),(113,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-09 04:51:58','2024-08-09 07:51:58',0,NULL,NULL),(114,'Supplier Order Added',NULL,'SUPPLIER_ORDER','A new Supplier Order has been created for Supplier Test','2024-08-09 04:58:24','2024-08-09 07:58:24',0,NULL,NULL),(115,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-08-09 06:07:47','2024-08-09 09:07:47',0,NULL,NULL),(116,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-08-09 06:10:07','2024-08-09 09:10:07',0,NULL,NULL),(117,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-08-09 06:10:07','2024-08-09 09:10:07',0,NULL,NULL),(118,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-08-09 06:10:55','2024-08-09 09:10:55',0,NULL,NULL),(119,'Supplier Order Updated',NULL,'SUPPLIER_ORDER','A Supplier Order has been updated for Supplier Test','2024-08-09 06:11:56','2024-08-09 09:11:56',0,NULL,NULL),(120,'Client Order Added',NULL,'CLIENT_ORDER','A new Client Order has been created for Client Test','2024-08-09 11:47:30','2024-08-09 14:47:30',0,NULL,NULL),(121,'Client Order Added',NULL,'CLIENT_ORDER','A new Client Order has been created for Client Test','2024-08-09 11:47:42','2024-08-09 14:47:42',0,NULL,NULL),(122,'Client Order Updated',NULL,'CLIENT_ORDER','A Client Order has been updated for Client Test','2024-08-09 11:53:18','2024-08-09 14:53:18',0,NULL,NULL),(123,'Client Order Updated',NULL,'CLIENT_ORDER','A Client Order has been updated for Client Test','2024-08-09 11:54:16','2024-08-09 14:54:16',0,NULL,NULL);
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
  CONSTRAINT `organization_invites_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`) ON DELETE CASCADE,
  CONSTRAINT `organization_invites_ibfk_2` FOREIGN KEY (`inviter_id`) REFERENCES `users` (`id`),
  CONSTRAINT `organization_invites_ibfk_3` FOREIGN KEY (`invitee_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_invites`
--

LOCK TABLES `organization_invites` WRITE;
/*!40000 ALTER TABLE `organization_invites` DISABLE KEYS */;
INSERT INTO `organization_invites` VALUES (1,1,'bc3dfdde-a8ef-11ee-bffa-00155de90539','43b836ac-a8e8-11ee-bffa-00155de90539',0,'2024-01-04 21:59:10'),(3,3,'43b836ac-a8e8-11ee-bffa-00155de90539','bc3dfdde-a8ef-11ee-bffa-00155de90539',0,'2024-01-05 10:58:28'),(4,5,'322f2f9c-8b7a-4274-942c-13536394f1b8','04badd68-29b7-4798-853b-a70cb727ac77',0,'2024-01-05 11:05:49'),(6,10,'430f3a7b-6d9b-411e-95a3-eb794ba3c430','322f2f9c-8b7a-4274-942c-13536394f1b8',0,'2024-01-05 11:28:00'),(7,16,'43b836ac-a8e8-11ee-bffa-00155de90539','322f2f9c-8b7a-4274-942c-13536394f1b8',0,'2024-01-05 13:15:15');
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
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_plan_active` tinyint(1) DEFAULT NULL,
  `is_plan_basic` tinyint(1) DEFAULT NULL,
  `plan_tier` enum('NONE','BASIC','PROFESSIONAL','ENTERPRISE') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizations`
--

LOCK TABLES `organizations` WRITE;
/*!40000 ALTER TABLE `organizations` DISABLE KEYS */;
INSERT INTO `organizations` VALUES (1,'Univel','San Franscisco, California, USA','tudororban2@gmail.com','2024-01-02 20:48:32','2024-09-13 13:05:31',1,1,'ENTERPRISE'),(2,'Apple','San Francisco','stevejobs@gmail.com','2024-01-04 20:02:55','2024-08-26 11:49:10',NULL,NULL,'ENTERPRISE'),(3,'ExxonMobil','Houston, Texas, USA',NULL,'2024-01-05 10:58:27','2024-08-26 11:49:10',NULL,NULL,'ENTERPRISE'),(5,'Microsoft','San Francisco, California, USA',NULL,'2024-01-05 11:05:49','2024-08-26 11:49:10',NULL,NULL,'ENTERPRISE'),(10,'NVIDIA','San Francisco, California, USA',NULL,'2024-01-05 11:28:00','2024-08-26 10:04:47',1,1,'NONE'),(12,'Huawei','Shenzen','','2024-01-05 12:23:06','2024-08-26 11:49:10',NULL,NULL,'ENTERPRISE'),(16,'TestUsers','Shenzen','0722415124','2024-01-05 13:15:15','2024-08-26 11:49:10',NULL,NULL,'ENTERPRISE'),(17,'','','','2024-01-05 13:47:04','2024-08-26 11:49:10',NULL,NULL,'ENTERPRISE');
/*!40000 ALTER TABLE `organizations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pricings`
--

DROP TABLE IF EXISTS `pricings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pricings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `product_pricing` json DEFAULT NULL,
  `organization_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_id` (`product_id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `pricings_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `pricings_ibfk_2` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pricings`
--

LOCK TABLES `pricings` WRITE;
/*!40000 ALTER TABLE `pricings` DISABLE KEYS */;
INSERT INTO `pricings` VALUES (1,1,'{\"pricePerUnit\": \"1\"}',1),(3,2,'{\"pricePerUnit\": 1.0, \"pricePerVolume\": {\"1.2\": 3.3, \"4.5\": 2.2}}',1);
/*!40000 ALTER TABLE `pricings` ENABLE KEYS */;
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
INSERT INTO `product_production_graphs` VALUES (1,'2024-03-20 18:10:32','2024-08-12 19:06:15',21,'{\"nodes\": {\"5\": {\"id\": 5, \"stageName\": \"Stage 1\", \"stageInputs\": [{\"id\": 7, \"componentId\": 2, \"componentName\": \"Test Component 2\", \"quantityPerStage\": 12.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}, {\"id\": 3, \"componentId\": 1, \"componentName\": \"Chip\", \"quantityPerStage\": 214.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 4, \"productId\": null, \"componentId\": 1, \"productName\": null, \"componentName\": \"Chip\", \"outputPerRequest\": null, \"quantityPerStage\": 12.0, \"expectedOutputPerAllocation\": null}, {\"id\": 6, \"productId\": null, \"componentId\": 2, \"productName\": null, \"componentName\": \"Test Component 2\", \"outputPerRequest\": null, \"quantityPerStage\": 21.0, \"expectedOutputPerAllocation\": null}]}, \"7\": {\"id\": 7, \"stageName\": \"Stage 2\", \"stageInputs\": [{\"id\": 9, \"componentId\": 3, \"componentName\": \"Microphone\", \"quantityPerStage\": 2.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}, {\"id\": 8, \"componentId\": 2, \"componentName\": \"Test Component 2\", \"quantityPerStage\": 123.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 12, \"productId\": null, \"componentId\": 4, \"productName\": null, \"componentName\": \"Camera\", \"outputPerRequest\": null, \"quantityPerStage\": 123.0, \"expectedOutputPerAllocation\": null}, {\"id\": 13, \"productId\": null, \"componentId\": 3, \"productName\": null, \"componentName\": \"Microphone\", \"outputPerRequest\": null, \"quantityPerStage\": 231.0, \"expectedOutputPerAllocation\": null}, {\"id\": 7, \"productId\": null, \"componentId\": 4, \"productName\": null, \"componentName\": \"Camera\", \"outputPerRequest\": null, \"quantityPerStage\": 6.0, \"expectedOutputPerAllocation\": null}]}, \"8\": {\"id\": 8, \"stageName\": \"Stage 3b - Independent\", \"stageInputs\": [{\"id\": 10, \"componentId\": 4, \"componentName\": \"Camera\", \"quantityPerStage\": 40.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": [{\"id\": 11, \"productId\": 21, \"componentId\": null, \"productName\": null, \"componentName\": null, \"outputPerRequest\": null, \"quantityPerStage\": 13.0, \"expectedOutputPerAllocation\": null}, {\"id\": 8, \"productId\": null, \"componentId\": 5, \"productName\": null, \"componentName\": \"Sensor\", \"outputPerRequest\": null, \"quantityPerStage\": 61.0, \"expectedOutputPerAllocation\": null}]}, \"10\": {\"id\": 10, \"stageName\": \"Create Stage Test\", \"stageInputs\": [], \"stageOutputs\": []}, \"12\": {\"id\": 12, \"stageName\": \"Another another test\", \"stageInputs\": [], \"stageOutputs\": []}, \"13\": {\"id\": 13, \"stageName\": \"Another new new test\", \"stageInputs\": [], \"stageOutputs\": []}, \"14\": {\"id\": 14, \"stageName\": \"EEEEnew new test\", \"stageInputs\": [{\"id\": 12, \"componentId\": 3, \"componentName\": \"Microphone\", \"quantityPerStage\": 213.0, \"allocatedQuantity\": null, \"requestedQuantity\": null}], \"stageOutputs\": []}, \"15\": {\"id\": 15, \"stageName\": \"qwqwwq\", \"stageInputs\": [], \"stageOutputs\": []}, \"16\": {\"id\": 16, \"stageName\": \"eqeqqw\", \"stageInputs\": [], \"stageOutputs\": []}, \"17\": {\"id\": 17, \"stageName\": \"eqwe\", \"stageInputs\": [], \"stageOutputs\": []}}, \"adjList\": {\"5\": [{\"srcStageId\": 5, \"destStageId\": 7, \"destStageInputId\": 9, \"srcStageOutputId\": 4}], \"7\": [], \"8\": [{\"srcStageId\": 8, \"destStageId\": 7, \"destStageInputId\": 8, \"srcStageOutputId\": 8}], \"10\": [], \"12\": [], \"13\": [], \"14\": [], \"15\": [], \"16\": [], \"17\": []}}');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_stage_connections`
--

LOCK TABLES `product_stage_connections` WRITE;
/*!40000 ALTER TABLE `product_stage_connections` DISABLE KEYS */;
INSERT INTO `product_stage_connections` VALUES (3,21,9,5,4,7),(4,21,8,8,8,7);
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
  `unit_of_measurement` json DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_organization_products` (`organization_id`),
  KEY `FKcgg9ayc3i1f44ktralk68qq0x` (`unit_id`),
  CONSTRAINT `fk_organization_products` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `FKcgg9ayc3i1f44ktralk68qq0x` FOREIGN KEY (`unit_id`) REFERENCES `units_of_measurement` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,1,'Iphone 21','The newest version of our product','2024-01-13 20:22:49','2024-07-30 19:44:13',NULL,NULL),(2,1,'Iphone 10','Old product','2024-01-13 20:23:15','2024-08-28 19:26:29',NULL,'{\"standardUnit\": \"METER\", \"unitMagnitude\": \"MILLI\"}'),(3,1,'Samsung','New phone','2024-02-23 10:02:02','2024-02-23 10:02:02',NULL,NULL),(4,1,'Iphone 21','Another new phone','2024-02-28 09:07:56','2024-02-28 09:07:56',NULL,NULL),(5,1,'Pocophone','Brand new phone','2024-02-28 09:08:20','2024-02-28 09:08:20',NULL,NULL),(6,1,'Google Pixel 6','','2024-02-28 09:08:31','2024-02-28 09:08:31',NULL,NULL),(7,1,'Motorolla v1','New version','2024-03-05 16:09:01','2024-03-05 16:09:01',NULL,NULL),(9,1,'Iphone 18','Mid-size','2024-03-05 16:09:54','2024-03-05 16:09:54',NULL,NULL),(11,1,'Test product update 2',NULL,'2024-03-08 12:47:03','2024-03-08 12:50:42',1,NULL),(13,1,'test name','test','2024-03-08 20:26:05','2024-03-08 20:26:05',1,NULL),(14,1,'test name','test','2024-03-09 06:46:48','2024-03-09 06:46:48',1,NULL),(15,1,'test Product','test','2024-03-09 06:51:02','2024-03-09 06:51:02',1,NULL),(16,1,'test Product','test','2024-03-09 06:52:59','2024-03-09 06:52:59',1,NULL),(17,1,'test Product','test','2024-03-09 06:54:25','2024-03-09 06:54:25',1,NULL),(19,1,'eqweqwe','qweqweqw','2024-03-09 07:00:23','2024-03-09 07:00:23',1,NULL),(20,1,'Create Test Product','Test of the createproduct','2024-03-09 07:22:16','2024-03-09 07:22:16',1,NULL),(21,1,'Test pipeline',NULL,'2024-03-09 17:16:17','2024-03-09 17:16:17',1,NULL),(22,1,'TestPr','Test','2024-03-16 14:41:23','2024-03-16 14:41:23',1,NULL),(23,1,'Alluminum',NULL,'2024-03-22 06:47:57','2024-03-22 06:47:57',5,NULL),(24,1,'Copper','Metal','2024-03-22 06:50:48','2024-03-22 06:50:48',NULL,NULL),(27,1,'Another Create unit test','Test Create Unit','2024-03-22 07:01:57','2024-03-22 07:01:57',6,NULL),(29,1,'Test Refactor Product','R','2024-04-22 19:11:45','2024-04-22 19:11:45',7,NULL),(30,1,'Working?','Think so','2024-04-22 19:49:09','2024-04-22 19:49:09',5,NULL),(32,1,'Xperia 2000',NULL,'2024-04-25 19:01:40','2024-08-01 06:03:11',NULL,'{\"standardUnit\": \"INCH\", \"unitMagnitude\": null}'),(34,1,'TestNewUnit','Product for testing new Standardized Units of Measurement','2024-07-31 11:39:02','2024-07-31 11:39:02',7,'{\"fullName\": \"MeterDeci\", \"abbreviation\": \"md\", \"standardUnit\": \"METER\", \"unitMagnitude\": \"DECI\"}'),(36,1,'Yet another test updat','of New Unit','2024-07-31 11:50:59','2024-08-28 19:26:29',NULL,'{\"standardUnit\": \"METER\", \"unitMagnitude\": \"MILLI\"}'),(37,1,'FinalTest Unit',NULL,'2024-07-31 12:10:27','2024-07-31 12:10:27',NULL,'{\"standardUnit\": \"POUND\", \"unitMagnitude\": \"CENTI\"}'),(40,1,'wqqw','','2024-08-07 10:55:44','2024-08-07 10:55:44',NULL,'{\"standardUnit\": \"METER\", \"unitMagnitude\": \"CENTI\"}'),(41,1,'qwqw','','2024-08-07 17:20:26','2024-08-07 17:20:26',NULL,'{\"standardUnit\": \"METER\", \"unitMagnitude\": \"BASE\"}'),(42,1,'DSA','dSAW','2024-08-27 12:28:36','2024-08-27 12:28:36',NULL,'{\"standardUnit\": \"METER\", \"unitMagnitude\": \"BASE\"}');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource_transport_routes`
--

DROP TABLE IF EXISTS `resource_transport_routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resource_transport_routes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `transport_route` json DEFAULT NULL,
  `organization_id` int DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `resource_transport_routes_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource_transport_routes`
--

LOCK TABLES `resource_transport_routes` WRITE;
/*!40000 ALTER TABLE `resource_transport_routes` DISABLE KEYS */;
INSERT INTO `resource_transport_routes` VALUES (1,NULL,NULL,'{\"waypoints\": null, \"srcLocation\": {\"first\": 36.03133177633189, \"second\": -119.18328624861556}, \"destLocation\": {\"first\": 38.61687046392973, \"second\": -98.3622815642739}, \"liveLocation\": null, \"srcFacilityId\": null, \"srcLocationId\": null, \"transportType\": \"ROAD\", \"destFacilityId\": null, \"destLocationId\": null, \"shipmentStatus\": null, \"arrivalDateTime\": [2024, 8, 19, 21, 0], \"srcFacilityName\": null, \"srcFacilityType\": null, \"destFacilityName\": null, \"destFacilityType\": null, \"departureDateTime\": [2024, 8, 17, 21, 0], \"transportedEntities\": null, \"estimatedArrivalDateTime\": [2024, 8, 19, 21, 0]}',1,NULL),(6,NULL,NULL,'{\"waypoints\": [], \"srcLocation\": {\"first\": 39.356953918477814, \"second\": -98.1296855190628}, \"destLocation\": {\"first\": 19.75291769408274, \"second\": -15.041862221448577}, \"liveLocation\": {\"first\": 31.153275247981124, \"second\": -72.2160190498956}, \"srcFacilityId\": null, \"srcLocationId\": null, \"transportType\": \"AIR\", \"destFacilityId\": null, \"destLocationId\": null, \"shipmentStatus\": null, \"arrivalDateTime\": [2024, 8, 20, 13, 24, 13, 841000000], \"srcFacilityName\": null, \"srcFacilityType\": null, \"destFacilityName\": null, \"destFacilityType\": null, \"departureDateTime\": [2024, 8, 20, 13, 24, 13, 841000000], \"transportedEntities\": [{\"entityId\": 5, \"quantity\": 20, \"entityName\": \"Sensor\", \"entityType\": \"COMPONENT\", \"deliveredQuantity\": null}, {\"entityId\": 2, \"quantity\": 21, \"entityName\": \"Test Component 2\", \"entityType\": \"COMPONENT\", \"deliveredQuantity\": null}, {\"entityId\": 23, \"quantity\": 80, \"entityName\": \"Alluminum\", \"entityType\": \"PRODUCT\", \"deliveredQuantity\": null}], \"estimatedArrivalDateTime\": [2024, 8, 20, 13, 24, 13, 841000000]}',1,'24124');
/*!40000 ALTER TABLE `resource_transport_routes` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stage_inputs`
--

LOCK TABLES `stage_inputs` WRITE;
/*!40000 ALTER TABLE `stage_inputs` DISABLE KEYS */;
INSERT INTO `stage_inputs` VALUES (3,5,214,1),(7,5,12,2),(8,7,123,2),(9,7,2,3),(10,8,40,4),(11,9,124,4),(12,14,213,3);
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
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `component_id` (`component_id`),
  KEY `stage_outputs_ibfk_1` (`stage_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `stage_outputs_ibfk_1` FOREIGN KEY (`stage_id`) REFERENCES `stages` (`id`),
  CONSTRAINT `stage_outputs_ibfk_2` FOREIGN KEY (`component_id`) REFERENCES `components` (`id`),
  CONSTRAINT `stage_outputs_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stage_outputs`
--

LOCK TABLES `stage_outputs` WRITE;
/*!40000 ALTER TABLE `stage_outputs` DISABLE KEYS */;
INSERT INTO `stage_outputs` VALUES (4,5,12,1,NULL),(6,5,21,2,NULL),(7,7,6,4,NULL),(8,8,61,5,NULL),(9,9,153,5,NULL),(11,8,13,NULL,21),(12,7,123,4,NULL),(13,7,231,3,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stages`
--

LOCK TABLES `stages` WRITE;
/*!40000 ALTER TABLE `stages` DISABLE KEYS */;
INSERT INTO `stages` VALUES (1,'First stage','Assembling microchip','2024-01-13 20:25:48','2024-01-13 20:25:48',NULL,1),(2,'Second stage','Assembling speakers','2024-01-13 20:26:12','2024-01-13 20:26:12',NULL,1),(3,'Third stage','Assembling product','2024-01-13 20:26:24','2024-01-13 20:26:24',NULL,1),(4,'Start','Manufacturing lithium batteries','2024-01-13 20:26:58','2024-01-13 20:26:58',NULL,2),(5,'Stage 1','Test','2024-03-09 17:16:58','2024-03-09 17:16:58',1,21),(7,'Stage 2',NULL,'2024-03-11 13:17:47','2024-03-11 13:17:47',1,21),(8,'Stage 3b - Independent','Not connected to anything','2024-03-11 17:12:26','2024-08-12 07:47:50',1,21),(9,'Another Stage',NULL,'2024-03-19 21:36:01','2024-03-19 21:36:01',1,6),(10,'Create Stage Test',NULL,'2024-03-20 20:23:32','2024-03-20 20:23:32',1,21),(12,'Another another test',NULL,'2024-03-20 20:26:36','2024-03-20 20:26:36',1,21),(13,'Another new new test',NULL,'2024-03-20 20:30:16','2024-03-20 20:30:16',1,21),(14,'EEEEnew new test',NULL,'2024-03-20 20:35:43','2024-03-20 20:35:43',1,21),(15,'qwqwwq','wqwq','2024-08-12 07:16:06','2024-08-12 07:16:06',1,21),(16,'eqeqqw','qweqqwq','2024-08-12 07:17:57','2024-08-12 07:17:57',1,21),(17,'eqwe','eqwe','2024-08-12 15:10:49','2024-08-12 15:10:49',1,21);
/*!40000 ALTER TABLE `stages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription_plans`
--

DROP TABLE IF EXISTS `subscription_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscription_plans` (
  `id` int NOT NULL AUTO_INCREMENT,
  `organization_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_basic` tinyint(1) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `custom_plan` json DEFAULT NULL,
  `is_paid` tinyint(1) DEFAULT NULL,
  `last_payment_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `subscription_plans_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription_plans`
--

LOCK TABLES `subscription_plans` WRITE;
/*!40000 ALTER TABLE `subscription_plans` DISABLE KEYS */;
INSERT INTO `subscription_plans` VALUES (1,1,NULL,'2024-09-13 13:08:36',1,1,'{\"planTier\": \"ENTERPRISE\", \"additionalFeatures\": {\"PRODUCT\": \"50\"}}',1,NULL),(7,10,'2024-08-26 12:59:23','2024-08-27 15:28:01',0,1,'{\"planTier\": \"BASIC\", \"additionalFeatures\": {\"PRODUCT\": \"12312\"}}',1,NULL);
/*!40000 ALTER TABLE `subscription_plans` ENABLE KEYS */;
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
  `organization_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delivery_warehouse_id` int DEFAULT NULL,
  `delivery_factory_id` int DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `delivered_quantity` double DEFAULT NULL,
  `status` enum('INITIATED','NEGOTIATED','PLACED','DELIVERED','CANCELED') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `supplier_id` (`supplier_id`),
  KEY `raw_material_id` (`raw_material_id`),
  KEY `component_id` (`component_id`),
  KEY `organization_id` (`organization_id`),
  KEY `supplier_warehouse_id` (`delivery_warehouse_id`),
  KEY `supplier_delively_factory_id` (`delivery_factory_id`),
  CONSTRAINT `supplier_orders_ibfk_3` FOREIGN KEY (`component_id`) REFERENCES `components` (`id`),
  CONSTRAINT `supplier_orders_ibfk_4` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`),
  CONSTRAINT `supplier_orders_ibfk_5` FOREIGN KEY (`delivery_warehouse_id`) REFERENCES `warehouses` (`id`),
  CONSTRAINT `supplier_orders_ibfk_6` FOREIGN KEY (`delivery_factory_id`) REFERENCES `factories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_orders`
--

LOCK TABLES `supplier_orders` WRITE;
/*!40000 ALTER TABLE `supplier_orders` DISABLE KEYS */;
INSERT INTO `supplier_orders` VALUES (1,1,NULL,1,NULL,NULL,NULL,NULL,1,'2024-03-06 20:03:10','2024-04-26 14:09:39',NULL,NULL,'13251',NULL,'DELIVERED'),(2,3,NULL,1,1123,'2022-03-12 08:05:53','2022-03-18 08:05:51','2022-03-19 08:05:53',1,'2024-03-27 14:04:55','2024-04-17 18:40:32',NULL,NULL,'#5323',1123,'DELIVERED'),(3,4,NULL,3,5315,'2022-04-12 08:05:53','2022-03-23 08:05:53','2022-03-24 13:31:21',1,'2024-03-27 14:12:45','2024-04-17 18:40:32',NULL,NULL,'#123',5300,'DELIVERED'),(4,3,NULL,4,233,'2022-05-12 08:05:53','2022-05-13 08:05:53','2022-05-13 08:30:33',1,'2024-03-27 13:58:51','2024-04-17 18:40:32',NULL,NULL,'#142',23,'DELIVERED'),(6,3,NULL,4,3434,NULL,NULL,NULL,1,'2024-04-03 11:56:48','2024-04-17 18:40:32',NULL,NULL,NULL,NULL,'DELIVERED'),(7,3,NULL,5,3,NULL,NULL,NULL,1,'2024-04-03 11:57:08','2024-04-06 12:06:47',NULL,NULL,NULL,NULL,NULL),(8,3,NULL,1,153,NULL,NULL,NULL,1,'2024-04-03 14:14:20','2024-04-17 18:40:32',NULL,NULL,NULL,NULL,'CANCELED'),(9,3,NULL,1,352.012,'2022-07-01 08:05:53','2022-07-09 08:05:53','2022-07-10 08:05:53',1,'2024-04-03 14:14:20','2024-04-17 18:40:32',NULL,NULL,NULL,351,'DELIVERED'),(10,3,NULL,1,32,NULL,NULL,NULL,1,'2024-04-04 12:43:55','2024-04-06 12:01:52',NULL,NULL,NULL,NULL,NULL),(11,3,NULL,1,1536,NULL,NULL,NULL,1,'2024-04-04 12:43:55','2024-04-06 12:01:52',NULL,NULL,NULL,NULL,NULL),(12,3,NULL,1,463,NULL,NULL,NULL,1,'2024-04-04 12:43:55','2024-04-17 18:40:32',NULL,NULL,NULL,NULL,'CANCELED'),(13,3,NULL,1,643,NULL,NULL,NULL,1,'2024-04-04 12:43:55','2024-04-06 12:01:52',NULL,NULL,NULL,NULL,NULL),(14,3,NULL,1,3464,NULL,NULL,NULL,1,'2024-04-04 12:43:55','2024-04-06 12:01:52',NULL,NULL,NULL,NULL,NULL),(15,3,NULL,1,4,NULL,NULL,NULL,1,'2024-04-04 12:43:55','2024-04-17 18:40:56',NULL,NULL,NULL,NULL,'DELIVERED'),(16,3,NULL,1,326,'2022-11-12 08:05:53','2022-11-16 08:05:53','2022-11-19 08:05:53',1,'2024-04-04 12:43:55','2024-04-17 18:40:56',NULL,NULL,NULL,325,'DELIVERED'),(17,3,NULL,1,124,NULL,NULL,NULL,1,'2024-04-04 14:34:49','2024-04-17 18:40:32',NULL,NULL,NULL,325,'DELIVERED'),(18,3,NULL,1,644,'2023-02-12 08:05:53','2023-03-01 08:05:53','2023-02-27 08:05:53',1,'2024-04-04 14:34:49','2024-04-17 18:40:32',NULL,NULL,NULL,640,'DELIVERED'),(19,3,NULL,2,352,NULL,NULL,NULL,1,'2024-04-04 14:34:49','2024-04-17 18:40:32',NULL,NULL,NULL,NULL,'DELIVERED'),(23,3,NULL,4,64,'2023-01-12 08:05:53','2023-01-15 08:05:53','2023-01-14 08:05:53',1,'2024-04-04 14:34:49','2024-04-17 18:40:32',NULL,NULL,NULL,54,'NEGOTIATED'),(24,3,NULL,1,12,NULL,NULL,NULL,1,'2024-04-04 14:36:09','2024-04-06 12:01:13',NULL,NULL,NULL,NULL,NULL),(25,3,NULL,7,1235,'2023-07-19 08:05:53','2023-08-01 08:05:53','2023-08-01 05:03:43',1,'2024-04-04 14:36:10','2024-04-06 12:22:12',NULL,NULL,NULL,1235,NULL),(26,3,NULL,2,24,NULL,NULL,NULL,1,'2024-04-04 14:36:10','2024-04-06 12:22:12',NULL,NULL,NULL,1235,NULL),(27,3,NULL,5,153,NULL,NULL,NULL,1,'2024-04-04 14:36:10','2024-04-06 12:01:13',NULL,NULL,NULL,NULL,NULL),(28,3,NULL,1,64,NULL,NULL,NULL,1,'2024-04-04 14:36:10','2024-04-06 12:01:13',NULL,NULL,NULL,NULL,NULL),(29,3,NULL,1,365,NULL,NULL,NULL,1,'2024-04-04 14:36:10','2024-04-06 12:01:13',NULL,NULL,NULL,NULL,NULL),(30,3,NULL,1,214,NULL,NULL,NULL,1,'2024-04-04 14:36:10','2024-04-06 12:01:13',NULL,NULL,NULL,NULL,NULL),(31,3,NULL,1,NULL,NULL,NULL,NULL,1,'2024-04-16 15:58:25','2024-04-16 15:58:25',NULL,NULL,NULL,NULL,NULL),(32,3,NULL,1,NULL,NULL,NULL,NULL,1,'2024-04-16 18:59:40','2024-04-16 18:59:40',NULL,NULL,NULL,NULL,NULL),(33,3,NULL,1,NULL,NULL,NULL,NULL,1,'2024-04-17 08:52:46','2024-04-17 08:52:46',NULL,NULL,NULL,NULL,NULL),(34,4,NULL,1,NULL,NULL,NULL,NULL,1,'2024-04-17 12:33:10','2024-04-17 12:33:10',NULL,NULL,NULL,NULL,NULL),(35,3,NULL,4,13213,NULL,NULL,NULL,1,'2024-04-17 12:38:03','2024-04-17 12:38:03',NULL,NULL,'#132',NULL,NULL),(36,3,NULL,4,124,NULL,NULL,NULL,1,'2024-04-17 13:10:32','2024-04-26 14:11:09',NULL,NULL,'12215121',NULL,'PLACED'),(37,4,NULL,1,NULL,NULL,NULL,NULL,1,'2024-04-17 15:19:31','2024-04-17 15:19:31',NULL,NULL,NULL,NULL,NULL),(39,3,NULL,2,NULL,NULL,NULL,NULL,1,'2024-04-26 08:23:10','2024-04-26 08:35:43',NULL,NULL,'1321',NULL,'PLACED'),(40,3,NULL,3,NULL,NULL,NULL,NULL,1,'2024-04-26 08:23:10','2024-04-26 08:23:10',NULL,NULL,'N/',NULL,'PLACED'),(41,3,NULL,2,100,NULL,'2024-05-02 09:26:06','2024-05-03 09:26:06',1,'2024-04-26 08:40:16','2024-05-02 09:26:31',NULL,NULL,'123',50,'PLACED'),(42,3,NULL,4,12,NULL,'2024-05-10 09:33:54','2024-05-11 09:33:54',1,'2024-05-02 06:34:25','2024-05-02 09:34:25',NULL,NULL,'1234',10,'INITIATED'),(43,3,NULL,1,100,'2023-03-31 21:00:00','2023-04-09 21:00:00','2023-04-09 21:00:00',1,'2024-05-02 08:23:45','2024-05-02 11:23:45',NULL,NULL,'1',NULL,'INITIATED'),(44,3,NULL,2,200,'2023-04-01 21:00:00','2023-04-10 21:00:00','2023-04-10 21:00:00',1,'2024-05-02 08:23:45','2024-05-02 11:23:45',NULL,NULL,'1',NULL,'INITIATED'),(45,3,NULL,1,100,'2023-03-31 21:00:00','2023-04-09 21:00:00','2023-04-09 21:00:00',1,'2024-05-02 08:24:34','2024-05-02 11:24:34',NULL,NULL,'1',NULL,'INITIATED'),(46,3,NULL,2,200,'2023-04-01 21:00:00','2023-04-10 21:00:00','2023-04-10 21:00:00',1,'2024-05-02 08:24:34','2024-05-02 11:24:34',NULL,NULL,'1',NULL,'INITIATED'),(47,3,NULL,4,50,'2023-03-31 21:00:00','2023-04-09 21:00:00','2023-04-09 21:00:00',1,'2024-05-02 08:26:49','2024-05-28 06:19:39',NULL,NULL,'1',NULL,'CANCELED'),(49,3,NULL,5,124,NULL,NULL,'2024-08-06 19:51:04',1,'2024-08-04 16:51:34','2024-08-04 19:51:34',NULL,NULL,'213',412,'PLACED'),(50,3,NULL,5,14,NULL,NULL,'2024-08-06 19:56:19',1,'2024-08-04 16:57:13','2024-08-04 19:57:13',NULL,NULL,'1264364',NULL,'PLACED'),(51,3,NULL,3,411,NULL,NULL,'2024-08-06 20:13:49',1,'2024-08-04 17:14:06','2024-08-04 20:14:06',NULL,NULL,'42314',14,'PLACED'),(52,3,NULL,5,131,NULL,NULL,'2024-08-06 20:20:25',1,'2024-08-04 17:20:44','2024-08-04 20:20:44',NULL,NULL,'34252',1313,'NEGOTIATED'),(53,3,NULL,3,123,NULL,NULL,'2024-08-06 20:42:50',1,'2024-08-07 17:43:07','2024-08-07 20:43:07',NULL,NULL,'21312',213,'CANCELED'),(55,3,NULL,7,NULL,NULL,NULL,'2024-08-13 20:46:19',1,'2024-08-07 17:46:55','2024-08-07 20:46:55',NULL,NULL,NULL,NULL,'NEGOTIATED'),(59,3,NULL,1,0,'2024-08-08 18:58:46','2024-08-08 18:58:46',NULL,1,'2024-08-08 18:58:50','2024-08-08 21:58:50',NULL,NULL,'',NULL,'DELIVERED'),(60,3,NULL,1,0,'2024-08-09 04:02:46','2024-08-09 04:02:46',NULL,1,'2024-08-09 04:03:05','2024-08-09 07:03:05',NULL,NULL,'',NULL,'DELIVERED'),(61,3,NULL,1,0,'2024-08-09 04:02:47','2024-08-09 04:02:47',NULL,1,'2024-08-09 04:03:05','2024-08-09 07:03:05',NULL,NULL,'',NULL,'DELIVERED'),(62,3,NULL,1,0,'2024-08-09 04:05:34','2024-08-09 04:05:34',NULL,1,'2024-08-09 04:05:36','2024-08-09 07:05:36',NULL,NULL,'',NULL,'DELIVERED'),(63,3,NULL,1,0,'2024-08-09 04:10:42','2024-08-09 04:10:42',NULL,1,'2024-08-09 04:10:47','2024-08-09 07:10:47',NULL,NULL,'',NULL,'DELIVERED'),(64,3,NULL,1,0,'2024-08-09 04:26:31','2024-08-09 04:26:31',NULL,1,'2024-08-09 04:26:37','2024-08-09 07:26:37',NULL,NULL,'',NULL,'DELIVERED'),(65,3,NULL,4,32,'2024-08-09 04:30:09','2024-08-09 04:30:09',NULL,1,'2024-08-09 04:30:13','2024-08-09 09:10:07',NULL,NULL,'',NULL,'PLACED'),(66,3,NULL,7,214,'2024-08-05 21:00:00','2024-08-09 04:34:43',NULL,1,'2024-08-09 04:35:04','2024-08-09 09:11:56',NULL,NULL,'1213',NULL,'CANCELED'),(67,3,NULL,9,21,'2024-08-09 04:36:56','2024-08-09 04:36:56',NULL,1,'2024-08-09 04:37:05','2024-08-09 07:37:05',NULL,NULL,'1221',NULL,'INITIATED');
/*!40000 ALTER TABLE `supplier_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_performances`
--

DROP TABLE IF EXISTS `supplier_performances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_performances` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplier_id` int NOT NULL,
  `supplier_performance_report` json DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `supplier_id` (`supplier_id`),
  CONSTRAINT `supplier_performances_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_performances`
--

LOCK TABLES `supplier_performances` WRITE;
/*!40000 ALTER TABLE `supplier_performances` DISABLE KEYS */;
INSERT INTO `supplier_performances` VALUES (4,3,'{\"totalDelays\": 4.0, \"overallScore\": 18.107143, \"qualityScore\": 100.0, \"timelinessScore\": -27.57143, \"availabilityScore\": 0.0, \"averageDelayPerOrder\": 0.2857143, \"quantityPerTimeScore\": 0.0, \"totalDeliveredOrders\": 14, \"componentPerformances\": {\"1\": {\"componentId\": 1, \"componentName\": \"Chip\", \"firstDeliveryDate\": [2022, 3, 19, 10, 5, 53], \"averageOrderQuantity\": 440.83533, \"totalDeliveredOrders\": 6.0, \"totalDeliveredQuantity\": 2439.0, \"averageShipmentQuantity\": 0.0, \"averageDeliveredQuantity\": 406.5, \"deliveredPerOrderedRatio\": 406.5, \"deliveredQuantityOverTime\": {\"0.0\": 1123.0, \"113.0\": 351.0, \"245.0\": 325.0, \"345.0\": 640.0, \"386.0\": 0.0}}, \"2\": {\"componentId\": 2, \"componentName\": \"Test Component 2\", \"firstDeliveryDate\": [2023, 4, 11, 0, 0], \"averageOrderQuantity\": 166.66667, \"totalDeliveredOrders\": 3.0, \"totalDeliveredQuantity\": 50.0, \"averageShipmentQuantity\": 0.0, \"averageDeliveredQuantity\": 16.666666, \"deliveredPerOrderedRatio\": 16.666666, \"deliveredQuantityOverTime\": {\"0.0\": 0.0, \"388.0\": 50.0}}, \"4\": {\"componentId\": 4, \"componentName\": \"Camera\", \"firstDeliveryDate\": [2022, 5, 13, 11, 30, 33], \"averageOrderQuantity\": 89.75, \"totalDeliveredOrders\": 4.0, \"totalDeliveredQuantity\": 87.0, \"averageShipmentQuantity\": 0.0, \"averageDeliveredQuantity\": 21.75, \"deliveredPerOrderedRatio\": 21.75, \"deliveredQuantityOverTime\": {\"0.0\": 23.0, \"245.0\": 54.0, \"331.0\": 0.0, \"729.0\": 10.0}}, \"7\": {\"componentId\": 7, \"componentName\": \"New Component\", \"firstDeliveryDate\": [2023, 8, 1, 8, 3, 43], \"averageOrderQuantity\": 1235.0, \"totalDeliveredOrders\": 1.0, \"totalDeliveredQuantity\": 1235.0, \"averageShipmentQuantity\": 0.0, \"averageDeliveredQuantity\": 1235.0, \"deliveredPerOrderedRatio\": 1235.0, \"deliveredQuantityOverTime\": {\"0.0\": 1235.0}}}, \"averageTimeToShipOrder\": 7.0, \"averageDelayPerShipment\": 0.2857143, \"averageShipmentsPerOrder\": 0.0, \"ratioOfOnTimeOrderDeliveries\": 0.35714287, \"ratioOfOnTimeShipmentDeliveries\": 0.0}','2024-04-06 13:52:35','2024-07-21 09:47:55'),(5,4,'{\"totalDelays\": 1.0, \"overallScore\": 0.25, \"qualityScore\": 100.0, \"timelinessScore\": -99.0, \"availabilityScore\": 0.0, \"averageDelayPerOrder\": 1.0, \"quantityPerTimeScore\": 0.0, \"totalDeliveredOrders\": 1, \"componentPerformances\": {\"3\": {\"componentId\": 3, \"componentName\": \"Microphone\", \"firstDeliveryDate\": [2022, 3, 24, 15, 31, 21], \"averageOrderQuantity\": 5315.0, \"totalDeliveredOrders\": 1.0, \"totalDeliveredQuantity\": 5300.0, \"averageShipmentQuantity\": 0.0, \"averageDeliveredQuantity\": 5300.0, \"deliveredPerOrderedRatio\": 5300.0, \"deliveredQuantityOverTime\": {\"0.0\": 5300.0}}}, \"averageTimeToShipOrder\": -18.0, \"averageDelayPerShipment\": 1.0, \"averageShipmentsPerOrder\": 0.0, \"ratioOfOnTimeOrderDeliveries\": 0.0, \"ratioOfOnTimeShipmentDeliveries\": 0.0}','2024-07-21 09:53:23','2024-07-21 09:53:23');
/*!40000 ALTER TABLE `supplier_performances` ENABLE KEYS */;
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
  `source_location_id` int DEFAULT NULL,
  `destination_location_id` int DEFAULT NULL,
  `destination_location_type` varchar(255) DEFAULT NULL,
  `current_location_latitude` float DEFAULT NULL,
  `current_location_longitude` float DEFAULT NULL,
  `organization_id` int NOT NULL,
  `supplier_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `dest_warehouse_id` int DEFAULT NULL,
  `dest_factory_id` int DEFAULT NULL,
  `departure_date` timestamp NULL DEFAULT NULL,
  `transport_type` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `source_location_id` (`source_location_id`),
  KEY `destination_location_id` (`destination_location_id`),
  KEY `supplier_order_id` (`supplier_order_id`),
  KEY `supplier_shipments_ibfk_4_idx` (`organization_id`),
  KEY `supplier_shipments_ibfk_5_idx` (`supplier_id`),
  KEY `dest_warehouse_id` (`dest_warehouse_id`),
  KEY `dest_factory_id` (`dest_factory_id`),
  CONSTRAINT `supplier_shipments_ibfk_1` FOREIGN KEY (`supplier_order_id`) REFERENCES `supplier_orders` (`id`),
  CONSTRAINT `supplier_shipments_ibfk_2` FOREIGN KEY (`source_location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `supplier_shipments_ibfk_3` FOREIGN KEY (`destination_location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `supplier_shipments_ibfk_4` FOREIGN KEY (`dest_warehouse_id`) REFERENCES `warehouses` (`id`),
  CONSTRAINT `supplier_shipments_ibfk_5` FOREIGN KEY (`dest_factory_id`) REFERENCES `factories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_shipments`
--

LOCK TABLES `supplier_shipments` WRITE;
/*!40000 ALTER TABLE `supplier_shipments` DISABLE KEYS */;
INSERT INTO `supplier_shipments` VALUES (12,32,330,'2024-05-16 09:56:02','2024-05-18 09:56:02','2024-05-19 09:56:02',1,24,NULL,NULL,NULL,1,2,'2024-05-21 02:13:09','2024-08-08 13:21:45',1,NULL,NULL,'SEA','DELIVERED'),(13,1,NULL,NULL,NULL,NULL,22,1,NULL,NULL,NULL,1,1,'2024-06-04 04:24:54','2024-08-08 13:21:45',NULL,1,NULL,'ROAD','IN_TRANSIT'),(15,1,14120,'2024-08-15 09:56:02','2024-08-21 09:56:02','2024-08-22 09:56:02',23,24,NULL,31,-8,1,1,'2024-08-05 09:04:46','2024-08-08 13:21:45',NULL,6,NULL,'AIR','CANCELLED'),(16,1,12,NULL,'2024-08-09 12:16:55','2024-08-09 12:16:55',NULL,NULL,NULL,NULL,NULL,1,4,'2024-08-09 12:17:05','2024-08-09 12:17:28',NULL,NULL,NULL,NULL,'IN_TRANSIT');
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
  `overall_score` decimal(10,0) DEFAULT NULL,
  `timeliness_score` decimal(10,0) DEFAULT NULL,
  `quantity_per_time_score` decimal(10,0) DEFAULT NULL,
  `availability_score` decimal(10,0) DEFAULT NULL,
  `quality_score` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcdasbtce0ulnp7hhdryn3mo5s` (`organization_id`),
  KEY `FK62w6tt0p4ti7f1ofonportcsg` (`location_id`),
  CONSTRAINT `FK62w6tt0p4ti7f1ofonportcsg` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `FKcdasbtce0ulnp7hhdryn3mo5s` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (2,'Motors Internationals',1,'2024-03-06 09:34:45','2024-03-23 16:20:45',13,NULL,NULL,NULL,NULL,NULL),(3,'AKio Motors',1,'2024-03-06 09:35:10','2024-08-29 11:33:01',6,67,78,72,38,89),(4,'Lu bu Co.',1,'2024-03-06 09:35:35','2024-07-21 12:53:23',5,0,-99,0,0,100),(7,'Ford',1,'2024-03-21 18:49:38','2024-08-05 11:15:47',23,NULL,NULL,NULL,NULL,NULL),(8,'Ford',1,'2024-03-21 18:50:17','2024-03-21 20:50:17',10,NULL,NULL,NULL,NULL,NULL),(9,'TestS',1,'2024-08-08 11:08:34','2024-08-08 14:08:34',8,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supply_chain_maps`
--

DROP TABLE IF EXISTS `supply_chain_maps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supply_chain_maps` (
  `id` int NOT NULL AUTO_INCREMENT,
  `organization_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `map_data` json DEFAULT NULL,
  `last_refresh` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `supply_chain_maps_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supply_chain_maps`
--

LOCK TABLES `supply_chain_maps` WRITE;
/*!40000 ALTER TABLE `supply_chain_maps` DISABLE KEYS */;
INSERT INTO `supply_chain_maps` VALUES (1,1,'2024-08-03 09:47:35','2024-09-13 04:52:08','{\"facilities\": [{\"id\": 3, \"name\": \"New York Factory 1\", \"type\": \"FACTORY\", \"latitude\": 12.31, \"longitude\": 41.21}, {\"id\": 4, \"name\": \"China factory MegaF\", \"type\": \"FACTORY\", \"latitude\": 70.14215, \"longitude\": 10.1241}, {\"id\": 5, \"name\": \"Tokio factory\", \"type\": \"FACTORY\", \"latitude\": 90.14215, \"longitude\": 12.1241}, {\"id\": 6, \"name\": \"Chicago factory 2\", \"type\": \"FACTORY\", \"latitude\": 36.7, \"longitude\": -119.4}, {\"id\": 10, \"name\": \"New Factory Test Sanitization\", \"type\": \"FACTORY\", \"latitude\": 21.12152, \"longitude\": 41.6325}, {\"id\": 11, \"name\": \"SS Factory\", \"type\": \"FACTORY\", \"latitude\": 70.14215, \"longitude\": 10.1241}, {\"id\": 13, \"name\": \"RefactoredFormTest1\", \"type\": \"FACTORY\", \"latitude\": 19.14215, \"longitude\": 40.1241}, {\"id\": 18, \"name\": \"swqeqw\", \"type\": \"FACTORY\", \"latitude\": 12132.0, \"longitude\": 2.0}, {\"id\": 19, \"name\": \"weqeqwq\", \"type\": \"FACTORY\", \"latitude\": 12.2, \"longitude\": 12.21}, {\"id\": 21, \"name\": \"qweqw\", \"type\": \"FACTORY\", \"latitude\": 11.2, \"longitude\": 2.2}, {\"id\": 22, \"name\": \"EQWQE\", \"type\": \"FACTORY\", \"latitude\": 2.0, \"longitude\": 1.0}, {\"id\": 23, \"name\": \"wqeqe\", \"type\": \"FACTORY\", \"latitude\": 21.0, \"longitude\": -10.0}, {\"id\": 24, \"name\": \"TestFactory\", \"type\": \"FACTORY\", \"latitude\": 20.0, \"longitude\": -15.0}, {\"id\": 26, \"name\": \"TestFactory2\", \"type\": \"FACTORY\", \"latitude\": 20.0, \"longitude\": -15.0}, {\"id\": 1, \"name\": \"NJ Warehouse\", \"type\": \"WAREHOUSE\", \"latitude\": 21.12152, \"longitude\": 41.6325}, {\"id\": 4, \"name\": \"Shenzen Warehouse\", \"type\": \"WAREHOUSE\", \"latitude\": 22.0, \"longitude\": 5.0}, {\"id\": 5, \"name\": \"Kyoto Warehouse\", \"type\": \"WAREHOUSE\", \"latitude\": 90.14215, \"longitude\": 12.1241}, {\"id\": 6, \"name\": \"LA Warehouse\", \"type\": \"WAREHOUSE\", \"latitude\": 12.14215, \"longitude\": 53.1241}, {\"id\": 9, \"name\": \"Warehouse S.E.A.\", \"type\": \"WAREHOUSE\", \"latitude\": 70.14215, \"longitude\": 10.1241}, {\"id\": 10, \"name\": \"QWwq\", \"type\": \"WAREHOUSE\", \"latitude\": 70.14215, \"longitude\": 10.1241}, {\"id\": 12, \"name\": \"Warehouse S.E.A.\", \"type\": \"WAREHOUSE\", \"latitude\": 70.14215, \"longitude\": 10.1241}, {\"id\": 3, \"name\": \"AKio Motors\", \"type\": \"SUPPLIER\", \"latitude\": 90.14215, \"longitude\": 12.1241}, {\"id\": 4, \"name\": \"Lu bu Co.\", \"type\": \"SUPPLIER\", \"latitude\": 70.14215, \"longitude\": 10.1241}, {\"id\": 7, \"name\": \"Ford\", \"type\": \"SUPPLIER\", \"latitude\": 39.6, \"longitude\": -98.3}, {\"id\": 9, \"name\": \"TestS\", \"type\": \"SUPPLIER\", \"latitude\": 80.1, \"longitude\": 41.4}, {\"id\": 1, \"name\": \"Viva Enterprises\", \"type\": \"CLIENT\", \"latitude\": 70.14215, \"longitude\": 10.1241}, {\"id\": 2, \"name\": \"BB Enterprises\", \"type\": \"CLIENT\", \"latitude\": 1.21, \"longitude\": 90.12}, {\"id\": 4, \"name\": \"Siame Enterprises\", \"type\": \"CLIENT\", \"latitude\": 70.14215, \"longitude\": 10.1241}, {\"id\": 5, \"name\": \"New Test Client\", \"type\": \"CLIENT\", \"latitude\": 12.14215, \"longitude\": 53.1241}, {\"id\": 6, \"name\": \"TestClient\", \"type\": \"CLIENT\", \"latitude\": 70.14215, \"longitude\": 10.1241}], \"transportRoutes\": [{\"entityId\": 12, \"waypoints\": null, \"entityType\": \"SUPPLIER_SHIPMENT\", \"srcLocation\": {\"first\": 21.12152, \"second\": 41.6325}, \"destLocation\": {\"first\": 36.7, \"second\": -119.4}, \"liveLocation\": null, \"srcFacilityId\": null, \"transportType\": \"SEA\", \"destFacilityId\": 1, \"shipmentStatus\": \"DELIVERED\", \"arrivalDateTime\": [2024, 5, 19, 12, 56, 2], \"srcFacilityType\": null, \"destFacilityType\": \"WAREHOUSE\", \"departureDateTime\": null, \"estimatedArrivalDateTime\": [2024, 5, 18, 12, 56, 2]}, {\"entityId\": 13, \"waypoints\": null, \"entityType\": \"SUPPLIER_SHIPMENT\", \"srcLocation\": {\"first\": 22.0, \"second\": 5.0}, \"destLocation\": {\"first\": 21.12152, \"second\": 41.6325}, \"liveLocation\": null, \"srcFacilityId\": null, \"transportType\": \"ROAD\", \"destFacilityId\": 1, \"shipmentStatus\": \"IN_TRANSIT\", \"arrivalDateTime\": null, \"srcFacilityType\": null, \"destFacilityType\": \"FACTORY\", \"departureDateTime\": null, \"estimatedArrivalDateTime\": null}, {\"entityId\": 15, \"waypoints\": null, \"entityType\": \"SUPPLIER_SHIPMENT\", \"srcLocation\": {\"first\": 39.6, \"second\": -98.3}, \"destLocation\": {\"first\": 36.7, \"second\": -119.4}, \"liveLocation\": {\"first\": 31.0, \"second\": -8.0}, \"srcFacilityId\": null, \"transportType\": \"AIR\", \"destFacilityId\": 6, \"shipmentStatus\": \"CANCELLED\", \"arrivalDateTime\": [2024, 8, 22, 12, 56, 2], \"srcFacilityType\": null, \"destFacilityType\": \"FACTORY\", \"departureDateTime\": null, \"estimatedArrivalDateTime\": [2024, 8, 21, 12, 56, 2]}, {\"entityId\": 1, \"waypoints\": null, \"entityType\": \"CLIENT_SHIPMENT\", \"srcLocation\": {\"first\": 70.14215, \"second\": 10.1241}, \"destLocation\": {\"first\": 90.14215, \"second\": 12.1241}, \"liveLocation\": null, \"srcFacilityId\": null, \"transportType\": null, \"destFacilityId\": null, \"shipmentStatus\": null, \"arrivalDateTime\": [2024, 5, 16, 17, 54], \"srcFacilityType\": null, \"destFacilityType\": null, \"departureDateTime\": null, \"estimatedArrivalDateTime\": [2024, 5, 16, 17, 54, 18]}, {\"entityId\": 5, \"waypoints\": null, \"entityType\": \"CLIENT_SHIPMENT\", \"srcLocation\": {\"first\": 22.0, \"second\": 5.0}, \"destLocation\": {\"first\": 36.7, \"second\": -119.4}, \"liveLocation\": null, \"srcFacilityId\": null, \"transportType\": \"AIR\", \"destFacilityId\": 5, \"shipmentStatus\": null, \"arrivalDateTime\": [2024, 5, 20, 21, 32, 48], \"srcFacilityType\": null, \"destFacilityType\": \"FACTORY\", \"departureDateTime\": null, \"estimatedArrivalDateTime\": [2024, 5, 20, 21, 32, 48]}]}','2024-09-13 07:52:07');
/*!40000 ALTER TABLE `supply_chain_maps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supply_chain_snapshots`
--

DROP TABLE IF EXISTS `supply_chain_snapshots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supply_chain_snapshots` (
  `id` int NOT NULL AUTO_INCREMENT,
  `organization_id` int NOT NULL,
  `snapshot` json DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `fk_org_id_snapshots_cascade` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supply_chain_snapshots`
--

LOCK TABLES `supply_chain_snapshots` WRITE;
/*!40000 ALTER TABLE `supply_chain_snapshots` DISABLE KEYS */;
INSERT INTO `supply_chain_snapshots` VALUES (1,1,'{\"clientsCount\": 4, \"membersCount\": 6, \"productsCount\": 24, \"factoriesCount\": 7, \"suppliersCount\": 5, \"componentsCount\": 9, \"warehousesCount\": 6, \"customRolesCount\": 4, \"clientOrdersCount\": 5, \"factoryStagesCount\": 5, \"productStagesCount\": 6, \"supplierOrdersCount\": 35, \"clientShipmentsCount\": 1, \"supplierShipmentsCount\": 9, \"factoryInventoryItemsCount\": 5, \"warehouseInventoryItemsCount\": 2}','2024-04-03 08:56:16','2024-04-20 11:55:08'),(2,2,'{\"clientsCount\": 0, \"membersCount\": 1, \"productsCount\": 2, \"factoriesCount\": 2, \"suppliersCount\": 1, \"componentsCount\": 0, \"warehousesCount\": 1, \"customRolesCount\": 0, \"clientOrdersCount\": 0, \"factoryStagesCount\": 0, \"productStagesCount\": 1, \"supplierOrdersCount\": 0, \"clientShipmentsCount\": 0, \"supplierShipmentsCount\": 0, \"factoryInventoryItemsCount\": 0, \"warehouseInventoryItemsCount\": 0}','2024-04-03 08:56:16','2024-04-20 11:55:08'),(3,3,'{\"clientsCount\": 0, \"membersCount\": 0, \"productsCount\": 0, \"factoriesCount\": 0, \"suppliersCount\": 0, \"componentsCount\": 0, \"warehousesCount\": 0, \"customRolesCount\": 0, \"clientOrdersCount\": 0, \"factoryStagesCount\": 0, \"productStagesCount\": 0, \"supplierOrdersCount\": 0, \"clientShipmentsCount\": 0, \"supplierShipmentsCount\": 0, \"factoryInventoryItemsCount\": 0, \"warehouseInventoryItemsCount\": 0}','2024-04-03 08:56:16','2024-04-20 11:55:08'),(4,5,'{\"clientsCount\": 0, \"membersCount\": 0, \"productsCount\": 0, \"factoriesCount\": 0, \"suppliersCount\": 0, \"componentsCount\": 0, \"warehousesCount\": 0, \"customRolesCount\": 0, \"clientOrdersCount\": 0, \"factoryStagesCount\": 0, \"productStagesCount\": 0, \"supplierOrdersCount\": 0, \"clientShipmentsCount\": 0, \"supplierShipmentsCount\": 0, \"factoryInventoryItemsCount\": 0, \"warehouseInventoryItemsCount\": 0}','2024-04-03 08:56:17','2024-04-20 11:55:08'),(6,10,'{\"clientsCount\": 0, \"membersCount\": 0, \"productsCount\": 0, \"factoriesCount\": 0, \"suppliersCount\": 0, \"componentsCount\": 0, \"warehousesCount\": 0, \"customRolesCount\": 0, \"clientOrdersCount\": 0, \"factoryStagesCount\": 0, \"productStagesCount\": 0, \"supplierOrdersCount\": 0, \"clientShipmentsCount\": 0, \"supplierShipmentsCount\": 0, \"factoryInventoryItemsCount\": 0, \"warehouseInventoryItemsCount\": 0}','2024-04-03 08:56:17','2024-04-20 11:55:09'),(7,12,'{\"clientsCount\": 0, \"membersCount\": 0, \"productsCount\": 0, \"factoriesCount\": 0, \"suppliersCount\": 0, \"componentsCount\": 0, \"warehousesCount\": 0, \"customRolesCount\": 0, \"clientOrdersCount\": 0, \"factoryStagesCount\": 0, \"productStagesCount\": 0, \"supplierOrdersCount\": 0, \"clientShipmentsCount\": 0, \"supplierShipmentsCount\": 0, \"factoryInventoryItemsCount\": 0, \"warehouseInventoryItemsCount\": 0}','2024-04-03 08:56:17','2024-04-20 11:55:09'),(8,16,'{\"clientsCount\": 0, \"membersCount\": 0, \"productsCount\": 0, \"factoriesCount\": 0, \"suppliersCount\": 0, \"componentsCount\": 0, \"warehousesCount\": 0, \"customRolesCount\": 0, \"clientOrdersCount\": 0, \"factoryStagesCount\": 0, \"productStagesCount\": 0, \"supplierOrdersCount\": 0, \"clientShipmentsCount\": 0, \"supplierShipmentsCount\": 0, \"factoryInventoryItemsCount\": 0, \"warehouseInventoryItemsCount\": 0}','2024-04-03 08:56:17','2024-04-20 11:55:09'),(9,17,'{\"clientsCount\": 0, \"membersCount\": 0, \"productsCount\": 0, \"factoriesCount\": 0, \"suppliersCount\": 0, \"componentsCount\": 0, \"warehousesCount\": 0, \"customRolesCount\": 0, \"clientOrdersCount\": 0, \"factoryStagesCount\": 0, \"productStagesCount\": 0, \"supplierOrdersCount\": 0, \"clientShipmentsCount\": 0, \"supplierShipmentsCount\": 0, \"factoryInventoryItemsCount\": 0, \"warehouseInventoryItemsCount\": 0}','2024-04-03 08:56:17','2024-04-20 11:55:09');
/*!40000 ALTER TABLE `supply_chain_snapshots` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units_of_measurement`
--

LOCK TABLES `units_of_measurement` WRITE;
/*!40000 ALTER TABLE `units_of_measurement` DISABLE KEYS */;
INSERT INTO `units_of_measurement` VALUES (1,'Unit','2024-01-13 23:06:01','Integer',NULL),(2,'Kilogram','2024-01-13 23:06:21','Decimal',NULL),(3,'Liters','2024-01-13 23:06:29','Decimal',NULL),(5,'grams','2024-03-21 13:29:57','discrete',1),(6,'Create Unit Test','2024-03-22 07:01:57','',1),(7,'NewUnit','2024-04-22 19:11:45','float',1),(8,'ewq','2024-04-25 18:55:19','eqwr',1);
/*!40000 ALTER TABLE `units_of_measurement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `upcoming_events`
--

DROP TABLE IF EXISTS `upcoming_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `upcoming_events` (
  `id` int NOT NULL AUTO_INCREMENT,
  `organization_id` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `message` text,
  `date_time` timestamp NULL DEFAULT NULL,
  `associated_entity_id` int DEFAULT NULL,
  `associated_entity_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `upcoming_events_ibfk_1` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `upcoming_events`
--

LOCK TABLES `upcoming_events` WRITE;
/*!40000 ALTER TABLE `upcoming_events` DISABLE KEYS */;
INSERT INTO `upcoming_events` VALUES (4,1,'Incoming Supplier Order','The Supplier Order with Company ID 34252 is due to arrive on 2024-08-06T23:20:25','2024-09-06 20:20:25',52,'SUPPLIER_ORDER'),(5,1,'Incoming Supplier Order','The Supplier Order with Company ID 21312 is due to arrive on 2024-08-06T23:42:50','2024-08-06 20:42:50',53,'SUPPLIER_ORDER'),(6,1,'Incoming Supplier Order','The Supplier Order with Company ID null is due to arrive on 2024-08-13T23:46:19','2024-08-13 20:46:19',55,'SUPPLIER_ORDER');
/*!40000 ALTER TABLE `upcoming_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_settings`
--

DROP TABLE IF EXISTS `user_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_settings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `notification_settings` json DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `general_settings` json DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_settings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_settings`
--

LOCK TABLES `user_settings` WRITE;
/*!40000 ALTER TABLE `user_settings` DISABLE KEYS */;
INSERT INTO `user_settings` VALUES (1,'04badd68-29b7-4798-853b-a70cb727ac77','{}','2024-04-03 11:47:41','2024-04-15 20:45:17','{\"infoLevel\": \"ALL\"}'),(2,'086e9e96-a8ef-11ee-bffa-00155de90539','{\"clientOrdersOn\": false, \"supplierOrdersOn\": true, \"factoryInventoryOn\": true, \"emailClientOrdersOn\": true, \"warehouseInventoryOn\": false, \"emailSupplierOrdersOn\": true, \"emailFactoryInventoryOn\": false, \"emailWarehouseInventoryOn\": false}','2024-04-03 11:48:13','2024-08-21 09:21:07','{\"infoLevel\": \"ADVANCED\"}'),(3,'430f3a7b-6d9b-411e-95a3-eb794ba3c430','{\"clientOrdersOn\": true, \"supplierOrdersOn\": true, \"factoryInventoryOn\": true, \"emailClientOrdersOn\": false, \"warehouseInventoryOn\": true, \"emailSupplierOrdersOn\": false, \"emailFactoryInventoryOn\": false, \"emailWarehouseInventoryOn\": false}','2024-04-17 14:17:46','2024-04-29 20:39:12','{\"infoLevel\": null}');
/*!40000 ALTER TABLE `user_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `organization_id` int DEFAULT NULL,
  `role` enum('ADMIN','MEMBER','NONE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `custom_role_id` int DEFAULT NULL,
  `is_profile_visible` tinyint(1) DEFAULT NULL,
  `is_profile_public` bit(1) DEFAULT NULL,
  `verification_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `verification_token_expiration_date` timestamp NULL DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `is_first_confirmation_email` tinyint(1) DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `image_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_users_organizations` (`organization_id`),
  KEY `custom_role_id` (`custom_role_id`),
  CONSTRAINT `fk_users_organization` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`) ON DELETE SET NULL,
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`custom_role_id`) REFERENCES `custom_roles` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('04badd68-29b7-4798-853b-a70cb727ac77','MatthewJonson','$2a$10$M8wcgZJEglAJq6gwqC6ZFu8h.EtwR3QaT4p0MtRooBvZeYC4eaiyK','mjonson@gmail.com','2024-01-05 08:17:24','2024-08-26 12:18:09',NULL,'ADMIN',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('086e9e96-a8ef-11ee-bffa-00155de90539','TudorAOrban12','$2y$10$VfHMNhpQ1WOnrReNTySMvOqGggHd5MoXsfCpOkQx2s5Cp6vVmOWhy','tudororban2@gmail.com','2024-01-01 21:45:08','2024-08-26 17:14:13',1,'ADMIN',NULL,NULL,_binary '',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('0c163e59-b961-4b39-b939-e70097a3c40c','ExampleUser','$2y$10$03X68xV4KwIIiy5PhWj76ep7AhHq4Zo93lRZhw/4ihE5pZuOGIrF2','exampleemail@gmail.com','2024-04-14 19:35:31','2024-04-14 19:35:31',1,'ADMIN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('1caaef49-e9fb-480c-9371-b932139d6c80','Jensen Huang','$2a$10$XV67s/5/D/Xq2O1BdIhc/uwpT4HjZA7zw0Q74GjmJeuVe6UyW3.we','huang@outlook.com','2024-01-05 11:28:00','2024-08-26 12:00:31',NULL,'ADMIN',2,NULL,_binary '',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('1f6d88dd-325b-4124-b393-71926bcbd2cb','Bill Gates','$2a$10$SoM.XQoRbwMtEd9KnI18vuNh6EP4cv81gZA0r2DaAtnRm32cgwXZ2','bg@outlook.com','2024-01-05 11:18:48','2024-08-26 12:18:09',NULL,'MEMBER',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('1fcdf89a-545e-4bf6-bd52-bc6e7dd53697','TestEmailUser','$2a$10$EhEAoaiflZptksd4vBIgi.wezcFlIl2zWw7Kkue4fdrBGVTKYEPdm','tudororban2313@gmail.com','2024-04-16 11:43:21','2024-04-16 12:14:06',NULL,'NONE',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),('229a84d6-ee6d-4e53-af42-5ee859c871c6','GabrielMajeri1','$2a$10$39CZd5cByMUENWFh3P6YRO6EruFcBo77alQED7Gz9xjEgoR/fneKO','anotheruser@example.com','2024-01-04 11:46:54','2024-03-27 14:55:29',1,'MEMBER',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('292c94cd-51c8-4d09-b4d8-59e2099a98fe','TestEmailUser2','$2a$10$Brxf0r4IYfqXYTFkfwp5kO8HHPAnbulr.q01ymF.4IxmZiKE2mNGO','tudororban312@gmail.com','2024-04-16 12:14:28','2024-04-16 12:53:25',NULL,'NONE',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),('322f2f9c-8b7a-4274-942c-13536394f1b8','Josh Hinton','$2a$10$5ohVUzHTwHvUvCZ7g3sH3uNsDlMTGZ6F8TTmNTqTRXqFGkttDsA02','jhint79@gmail.com','2024-01-05 07:56:47','2024-01-13 21:29:36',2,'ADMIN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('430f3a7b-6d9b-411e-95a3-eb794ba3c430','Sorin','$2y$10$UwhB6/PGfI.LeEBIXJQoEOIaRZOGsDQA3vwIu1tCMEww3uYIDb98i','newuser@example.com','2024-01-03 08:12:13','2024-04-03 08:58:05',1,'ADMIN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('43b836ac-a8e8-11ee-bffa-00155de90539','TudorAOrban','$2y$10$3Lsc1LeiiTLzZOgtVp7g/uJKM7VtcdYVCxT2A7DBggTy97fOkSAam','tudororban2213@gmail.com','2024-01-01 20:56:41','2024-04-16 12:53:25',1,'NONE',16,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('5cafa54a-74ea-4149-b245-c11c64d1361e','NewTestUser','$2a$12$iHq2QAJ7iNxuvx/HVeANi.LHOAwMrBhVFVNgl3ZgBAdPVUMkHpm1u','New-Test-Email','2024-02-16 07:21:16','2024-08-26 09:32:02',NULL,'ADMIN',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('7dfa2892-673a-4fd2-8fc1-1330b084f909','jeffbezos','$2a$12$S5suv8afHxC1ZeE7sF1AC.IIeerje45Np75dxHhH85KjhTWWESVZu','','2024-01-03 12:54:08','2024-08-26 12:58:16',10,'ADMIN',NULL,NULL,_binary '',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('9af23564-a8c1-11ee-bffa-00155de90539','myusername','hashed_password','user@example.com','2024-01-01 16:19:57','2024-03-27 19:44:49',NULL,'NONE',NULL,NULL,_binary '',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('b880508b-b12f-4a25-8578-fa9d1d997394','user','$2a$10$mWDG.NhUdsvJkugVqsDTPeSVpdkrugP1X.qIPHzudHb7OhVkZfjm.','user@yahoo.com','2024-01-03 08:25:51','2024-03-27 19:44:49',NULL,'NONE',NULL,NULL,_binary '',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('bc3dfdde-a8ef-11ee-bffa-00155de90539','TudorAOrban2','$2a$10$5frWFt.qXudTyLvDhQzAQOh26ThALaiiMsR0Ir9WykuO8NyOctzCC','tudororban4@gmail.com','2024-01-01 21:50:10','2024-03-27 21:04:45',1,'ADMIN',NULL,NULL,_binary '',NULL,NULL,NULL,NULL,NULL,NULL,NULL),('e5fc91ac-25fc-4e04-9059-f816a113b912','TestOrgEmail','$2a$10$6RdmGMdVizQEpxKin0SgmewbZ7k8XVV68SegUPS8xaU/.6djpjW7G','tudororban3@gmail.com','2024-04-16 12:53:29','2024-08-26 12:00:31',NULL,'ADMIN',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL);
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
  `organization_id` int DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `raw_material_id` (`raw_material_id`),
  KEY `component_id` (`component_id`),
  KEY `product_id` (`product_id`),
  KEY `warehouse_id` (`warehouse_id`),
  KEY `organization_id` (`organization_id`),
  CONSTRAINT `warehouse_inventory_items_ibfk_1` FOREIGN KEY (`warehouse_id`) REFERENCES `factories` (`id`),
  CONSTRAINT `warehouse_inventory_items_ibfk_3` FOREIGN KEY (`component_id`) REFERENCES `components` (`id`),
  CONSTRAINT `warehouse_inventory_items_ibfk_4` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `warehouse_inventory_items_ibfk_5` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouses` (`id`),
  CONSTRAINT `warehouse_inventory_items_ibfk_6` FOREIGN KEY (`organization_id`) REFERENCES `organizations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse_inventory_items`
--

LOCK TABLES `warehouse_inventory_items` WRITE;
/*!40000 ALTER TABLE `warehouse_inventory_items` DISABLE KEYS */;
INSERT INTO `warehouse_inventory_items` VALUES (1,6,NULL,1,1,341,240,'2024-03-12 16:38:07','2024-04-30 09:40:51',1,NULL),(2,6,NULL,4,1,30,240,'2024-03-12 16:38:07','2024-04-30 09:40:51',1,NULL),(3,6,NULL,1,1,214,NULL,'2024-04-03 14:37:44','2024-08-09 19:35:30',1,NULL),(4,6,NULL,1,1,400,NULL,'2024-04-03 14:37:44','2024-08-09 19:35:30',1,NULL),(5,6,NULL,1,1,214,NULL,'2024-04-03 14:38:51','2024-08-02 11:42:12',1,NULL),(6,6,NULL,1,1,400,NULL,'2024-04-03 14:38:51','2024-08-02 11:42:12',1,NULL),(7,6,NULL,1,1,214,NULL,'2024-04-03 14:39:24','2024-08-02 11:42:12',1,NULL),(8,2,NULL,1,1,400,NULL,'2024-04-03 14:39:24','2024-08-09 19:29:48',1,NULL),(9,6,NULL,1,1,214,NULL,'2024-04-03 14:39:26','2024-08-02 11:42:12',1,NULL),(10,6,NULL,1,1,400,NULL,'2024-04-03 14:39:26','2024-08-02 11:42:12',1,NULL),(11,6,NULL,1,1,330,NULL,'2024-04-03 14:39:28','2024-08-02 11:42:12',1,NULL),(12,6,NULL,1,21,400,NULL,'2024-04-03 14:39:28','2024-08-02 11:42:12',1,NULL),(13,6,NULL,1,1,350,200,'2024-04-03 14:39:29','2024-08-02 11:42:12',1,NULL),(14,6,NULL,1,21,400,NULL,'2024-04-03 14:39:29','2024-08-02 11:42:12',1,NULL),(15,6,NULL,2,3,200,300,'2024-04-30 03:48:12','2024-08-02 11:42:12',1,NULL),(16,4,NULL,5,11,3112,NULL,'2024-08-09 13:30:08','2024-08-09 19:35:30',1,NULL),(17,5,NULL,8,14,123,NULL,'2024-08-09 13:33:59','2024-08-09 19:35:30',1,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouses`
--

LOCK TABLES `warehouses` WRITE;
/*!40000 ALTER TABLE `warehouses` DISABLE KEYS */;
INSERT INTO `warehouses` VALUES (1,'NJ Warehouse',1,1,'2024-03-04 21:07:37','2024-08-03 11:45:06'),(2,'Chicago warehouse 2',14,1,'2024-03-06 09:34:29','2024-03-23 16:40:53'),(4,'Shenzen Warehouse',22,1,'2024-03-06 09:51:05','2024-08-05 11:16:28'),(5,'Kyoto Warehouse',6,1,'2024-03-06 09:51:15','2024-03-06 11:51:15'),(6,'LA Warehouse',3,1,'2024-03-06 09:51:24','2024-03-06 11:51:24'),(9,'Warehouse S.E.A.',5,1,'2024-03-21 17:56:30','2024-04-22 21:26:16'),(10,'QWwq',5,1,'2024-08-08 10:31:09','2024-08-08 13:31:09'),(12,'Warehouse S.E.A.',38,1,'2024-08-08 11:03:22','2024-08-08 14:03:22');
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

-- Dump completed on 2024-09-13 18:06:25
