-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.17 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for spartan
CREATE DATABASE IF NOT EXISTS `spartan` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `spartan`;


-- Dumping structure for table spartan.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `phone` varchar(15) NOT NULL,
  `fname` varchar(20) DEFAULT NULL,
  `lname` varchar(20) DEFAULT NULL,
  `address` varchar(200) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Stores the customer details like First Name, Last Name, Phone Number and Address.\r\nNote: Phone number is unique';

-- Dumping data for table spartan.customer: ~0 rows (approximately)
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


-- Dumping structure for table spartan.orderdetails
CREATE TABLE IF NOT EXISTS `orderdetails` (
  `orderNo` int(11) NOT NULL,
  `widgetId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  KEY `widgetId` (`widgetId`),
  KEY `FK_orderdetails_orderinfo` (`orderNo`),
  CONSTRAINT `FK_orderdetails_orderinfo` FOREIGN KEY (`orderNo`) REFERENCES `orderinfo` (`orderNo`),
  CONSTRAINT `FK_orderdetails_widget` FOREIGN KEY (`widgetId`) REFERENCES `widget` (`widgetId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table spartan.orderdetails: ~0 rows (approximately)
/*!40000 ALTER TABLE `orderdetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderdetails` ENABLE KEYS */;


-- Dumping structure for table spartan.orderinfo
CREATE TABLE IF NOT EXISTS `orderinfo` (
  `orderNo` int(11) NOT NULL AUTO_INCREMENT,
  `orderTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `shippingTime` timestamp NULL DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  `phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`orderNo`),
  KEY `FK_orderinfo_customer` (`phone`),
  CONSTRAINT `FK_orderinfo_customer` FOREIGN KEY (`phone`) REFERENCES `customer` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COMMENT='Status: \r\n0 - Pending\r\n1 - In progress\r\n2 - Backordered\r\n3- Complete';

-- Dumping data for table spartan.orderinfo: ~0 rows (approximately)
/*!40000 ALTER TABLE `orderinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `orderinfo` ENABLE KEYS */;


-- Dumping structure for table spartan.robot
CREATE TABLE IF NOT EXISTS `robot` (
  `robotId` int(11) NOT NULL,
  `warehouseId` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `desc` varchar(50) DEFAULT NULL,
  `ipaddress` varchar(20) DEFAULT NULL,
  `status` tinyint(2) NOT NULL,
  PRIMARY KEY (`robotId`),
  UNIQUE KEY `robotId` (`robotId`),
  UNIQUE KEY `name` (`name`),
  KEY `FK_robot_warehouse` (`warehouseId`),
  CONSTRAINT `FK_robot_warehouse` FOREIGN KEY (`warehouseId`) REFERENCES `warehouse` (`warehouseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='status\r\n0 - Idle\r\n1 - Busy\r\n2 - Error\r\n3 - Complete';

-- Dumping data for table spartan.robot: ~0 rows (approximately)
/*!40000 ALTER TABLE `robot` DISABLE KEYS */;
INSERT INTO `robot` (`robotId`, `warehouseId`, `name`, `desc`, `ipaddress`, `status`) VALUES
	(1, 1, 'SpartanRobot1', 'Spartan Robot at Warehouse 1', '10.221.34.11', 0);
/*!40000 ALTER TABLE `robot` ENABLE KEYS */;


-- Dumping structure for table spartan.robotmoves
CREATE TABLE IF NOT EXISTS `robotmoves` (
  `robotId` int(11) DEFAULT NULL,
  `orderNo` int(11) DEFAULT NULL,
  `stationsToVisit` varchar(50) DEFAULT NULL COMMENT 'Example: 1, 3, 2 the stations to be visited in the order',
  `stationsVisited` varchar(50) DEFAULT '0' COMMENT 'Example: 1,2 if 1 and 2 stations are visited in that order',
  `currentStation` int(11) DEFAULT NULL COMMENT 'stationId of the current Station',
  `nextStation` int(11) DEFAULT NULL COMMENT 'stationId of the next Station to be visited',
  UNIQUE KEY `orderNo` (`orderNo`),
  KEY `FK_robotmoves_robot` (`robotId`),
  KEY `FK_robotmoves_station` (`stationsToVisit`),
  KEY `FK_robotmoves_station_cur` (`currentStation`),
  KEY `FK_robotmoves_station_next` (`nextStation`),
  CONSTRAINT `FK_robotmoves_orderinfo` FOREIGN KEY (`orderNo`) REFERENCES `orderinfo` (`orderNo`),
  CONSTRAINT `FK_robotmoves_robot` FOREIGN KEY (`robotId`) REFERENCES `robot` (`robotId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='This table informs the stations to be visited by Robot and the stations already visited by Robot and the next station to be visited by Robot\r\nThis table looks like following\r\nrobotId  | orderNo | stationsToVisit | stationVisited \r\n1             | 101          |     1                    |   N\r\n1             | 101          |     2                    |   N\r\n1             | 101          |     3                    |   N';

-- Dumping data for table spartan.robotmoves: ~0 rows (approximately)
/*!40000 ALTER TABLE `robotmoves` DISABLE KEYS */;
INSERT INTO `robotmoves` (`robotId`, `orderNo`, `stationsToVisit`, `stationsVisited`, `currentStation`, `nextStation`) VALUES
	(1, 1, '1,3,2', '1,3,2', 2, 0);
/*!40000 ALTER TABLE `robotmoves` ENABLE KEYS */;


-- Dumping structure for table spartan.robotstatus
CREATE TABLE IF NOT EXISTS `robotstatus` (
  `orderNo` int(11) DEFAULT NULL,
  `stn1Visited` tinyint(4) DEFAULT '0' COMMENT '0 - Not visited; 1- visited',
  `stn2Visited` tinyint(4) DEFAULT '0' COMMENT '0 - Not visited; 1- visited',
  `stn3Visited` tinyint(4) DEFAULT '0' COMMENT '0 - Not visited; 1- visited',
  `stn4Visited` tinyint(4) DEFAULT '0' COMMENT '0 - Not visited; 1- visited',
  `stn1Need` tinyint(4) DEFAULT '0' COMMENT '0 - No need; 1- need',
  `stn2Need` tinyint(4) DEFAULT '0' COMMENT '0 - No need; 1- need',
  `stn3Need` tinyint(4) DEFAULT '0' COMMENT '0 - No need; 1- need',
  `stn4Need` tinyint(4) DEFAULT '0' COMMENT '0 - No need; 1- need',
  `nextStn` tinyint(4) DEFAULT '-1',
  `state` tinyint(4) DEFAULT '0' COMMENT '0 - Idle, 1 - Busy, 2 - Error, 3- Complete',
  KEY `FK_robotStatus_order` (`orderNo`),
  CONSTRAINT `FK_robotStatus_order` FOREIGN KEY (`orderNo`) REFERENCES `orderinfo` (`orderNo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='stnxVisited, stnxNeed fields to be removed. Same info available in robotMoves\r\nstate';

-- Dumping data for table spartan.robotstatus: ~0 rows (approximately)
/*!40000 ALTER TABLE `robotstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `robotstatus` ENABLE KEYS */;


-- Dumping structure for table spartan.station
CREATE TABLE IF NOT EXISTS `station` (
  `stationId` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `desc` varchar(50) DEFAULT NULL,
  `warehouseId` int(11) NOT NULL,
  `type` tinyint(2) NOT NULL,
  PRIMARY KEY (`stationId`),
  UNIQUE KEY `stationId` (`stationId`),
  UNIQUE KEY `name` (`name`),
  KEY `FK__warehouse` (`warehouseId`),
  CONSTRAINT `FK__warehouse` FOREIGN KEY (`warehouseId`) REFERENCES `warehouse` (`warehouseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='type:\r\n0 - Inventory Station\r\n1 - Shipping Station\r\n';

-- Dumping data for table spartan.station: ~4 rows (approximately)
/*!40000 ALTER TABLE `station` DISABLE KEYS */;
INSERT INTO `station` (`stationId`, `name`, `desc`, `warehouseId`, `type`) VALUES
	(1, 'Station1_Inventory', 'Inventory Station', 1, 0),
	(2, 'Station2_Inventory', 'Inventory Station', 1, 0),
	(3, 'Station3_Inventory', 'Inventory Station', 1, 0),
	(4, 'Station4_Shipping', 'Shipping Station', 1, 1);
/*!40000 ALTER TABLE `station` ENABLE KEYS */;


-- Dumping structure for table spartan.warehouse
CREATE TABLE IF NOT EXISTS `warehouse` (
  `warehouseId` int(11) NOT NULL,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `desc` varchar(50) NOT NULL DEFAULT '0',
  `invStations` int(11) NOT NULL DEFAULT '0',
  `shippingStations` int(11) NOT NULL DEFAULT '0',
  `robots` int(11) NOT NULL DEFAULT '0',
  `ipaddress` varchar(20) DEFAULT NULL,
  `status` tinyint(2) NOT NULL,
  `serverheartbeatts` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`warehouseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Stores the Warehouse ID, No of Inventory Stations , No of Shipping Stations, No of Robots\r\nStatus: \r\n1 - Available-Working Fine\r\n2 - Available-Not Working Fine\r\n3 - Not available\r\n\r\nserverheartbeatts format : long value in milliseconds';

-- Dumping data for table spartan.warehouse: ~1 rows (approximately)
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` (`warehouseId`, `name`, `desc`, `invStations`, `shippingStations`, `robots`, `ipaddress`, `status`, `serverheartbeatts`) VALUES
	(1, '0', '0', 3, 1, 1, '10.221.121.2', 1, 1403553912140);
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;


-- Dumping structure for table spartan.widget
CREATE TABLE IF NOT EXISTS `widget` (
  `widgetId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(100) NOT NULL,
  `quantity` int(11) NOT NULL,
  `stationId` int(11) NOT NULL DEFAULT '0' COMMENT 'Station Id like 1, 2 or 3',
  `cost` float NOT NULL COMMENT 'Cost in dollars',
  PRIMARY KEY (`widgetId`),
  UNIQUE KEY `name` (`name`),
  KEY `FK_widget_station` (`stationId`),
  CONSTRAINT `FK_widget_station` FOREIGN KEY (`stationId`) REFERENCES `station` (`stationId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table spartan.widget: ~5 rows (approximately)
/*!40000 ALTER TABLE `widget` DISABLE KEYS */;
INSERT INTO `widget` (`widgetId`, `name`, `description`, `quantity`, `stationId`, `cost`) VALUES
	(1, 'Football', 'Foot ball', 100, 1, 0),
	(2, 'RugbyBall', 'Rugby ball', 100, 1, 0),
	(3, 'BasketBall', 'Basket ball', 100, 2, 0),
	(4, 'Yellow BaseBall', 'Yellow colored Base ball', 100, 3, 0),
	(5, 'White BaseBall', 'White colored Base ball', 100, 3, 0);
/*!40000 ALTER TABLE `widget` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
