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
  `address` varchar(40) NOT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Stores the customer details like First Name, Last Name, Phone Number and Address.\r\nNote: Phone number is unique';


-- Dumping structure for table spartan.orderdetails
CREATE TABLE IF NOT EXISTS `orderdetails` (
  `orderNo` int(11) NOT NULL,
  `widgetId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  KEY `widgetId` (`widgetId`),
  CONSTRAINT `widgetId` FOREIGN KEY (`widgetId`) REFERENCES `widget` (`widgetId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1 COMMENT='Status: \r\n0 - Pending\r\n1 - In progress\r\n2 - Backordered\r\n3- Complete';


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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping data for table spartan.robotstatus: ~0 rows (approximately)
/*!40000 ALTER TABLE `robotstatus` DISABLE KEYS */;
/*!40000 ALTER TABLE `robotstatus` ENABLE KEYS */;


-- Dumping structure for table spartan.warehouse
CREATE TABLE IF NOT EXISTS `warehouse` (
  `warehouseId` int(11) NOT NULL,
  `invStations` int(11) NOT NULL,
  `shippingStations` int(11) NOT NULL,
  `robots` int(11) NOT NULL,
  PRIMARY KEY (`warehouseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Stores the Warehouse ID, No of Inventory Stations , No of Shipping Stations, No of Robots';

-- Dumping data for table spartan.warehouse: ~0 rows (approximately)
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;


-- Dumping structure for table spartan.widget
CREATE TABLE IF NOT EXISTS `widget` (
  `widgetId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(100) NOT NULL,
  `quantity` int(11) NOT NULL,
  `stationId` int(11) NOT NULL DEFAULT '0' COMMENT 'Station Id like 1, 2 or 3',
  PRIMARY KEY (`widgetId`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;


/*!40000 ALTER TABLE `widget` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
