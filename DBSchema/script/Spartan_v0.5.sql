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

-- Dumping data for table spartan.customer: ~2 rows (approximately)
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`phone`, `fname`, `lname`, `address`) VALUES
	('123123', 'kavitha', 'rachabattuni', 'Bangalore India'),
	('912312879812', 'vijay', 'rachabattuni', 'Bangalore India 55439945');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


-- Dumping structure for table spartan.orderdetails
CREATE TABLE IF NOT EXISTS `orderdetails` (
  `orderNo` int(11) NOT NULL,
  `widgetId` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table spartan.orderdetails: ~30 rows (approximately)
/*!40000 ALTER TABLE `orderdetails` DISABLE KEYS */;
INSERT INTO `orderdetails` (`orderNo`, `widgetId`, `quantity`) VALUES
	(1, 2, 100),
	(1, 3, 200),
	(2, 2, 100),
	(2, 3, 200),
	(3, 2, 100),
	(3, 3, 200),
	(4, 2, 100),
	(4, 3, 200),
	(5, 2, 100),
	(5, 3, 200),
	(6, 2, 100),
	(6, 3, 200),
	(7, 2, 100),
	(7, 3, 200),
	(8, 2, 100),
	(8, 3, 200),
	(9, 2, 100),
	(9, 3, 200),
	(10, 2, 100),
	(10, 3, 200),
	(11, 2, 100),
	(11, 3, 200),
	(12, 2, 100),
	(12, 3, 200),
	(13, 2, 100),
	(13, 3, 200),
	(14, 2, 100),
	(14, 3, 200),
	(15, 2, 100),
	(15, 3, 200);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1 COMMENT='Status: \r\n0 - Pending\r\n1 - In progress\r\n2 - Backordered\r\n3- Complete';

-- Dumping data for table spartan.orderinfo: ~15 rows (approximately)
/*!40000 ALTER TABLE `orderinfo` DISABLE KEYS */;
INSERT INTO `orderinfo` (`orderNo`, `orderTime`, `shippingTime`, `status`, `phone`) VALUES
	(1, '2014-06-16 01:28:36', NULL, 1, '912312879812'),
	(2, '2014-06-16 01:28:37', NULL, 2, '912312879812'),
	(3, '2014-06-16 01:28:38', NULL, 3, '912312879812'),
	(4, '2014-06-16 01:28:38', NULL, 4, '912312879812'),
	(5, '2014-06-16 01:28:38', NULL, 0, '912312879812'),
	(6, '2014-06-16 01:28:38', NULL, 1, '912312879812'),
	(7, '2014-06-16 01:28:38', NULL, 1, '912312879812'),
	(8, '2014-06-16 01:28:39', NULL, 1, '912312879812'),
	(9, '2014-06-16 01:28:46', NULL, 0, '912312879812'),
	(10, '2014-06-16 01:28:47', NULL, 0, '912312879812'),
	(11, '2014-06-16 01:28:47', NULL, 0, '912312879812'),
	(12, '2014-06-16 01:28:47', NULL, 0, '912312879812'),
	(13, '2014-06-16 01:28:47', NULL, 0, '912312879812'),
	(14, '2014-06-16 01:38:01', NULL, 0, '912312879812'),
	(15, '2014-06-16 02:53:50', NULL, 0, '912312879812');
/*!40000 ALTER TABLE `orderinfo` ENABLE KEYS */;


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
  `status` tinyint(4) DEFAULT '0' COMMENT '0 - Idle, 1 - Busy, 2 - Error, 3- Complete',
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

-- Dumping data for table spartan.widget: ~4 rows (approximately)
/*!40000 ALTER TABLE `widget` DISABLE KEYS */;
INSERT INTO `widget` (`widgetId`, `name`, `description`, `quantity`, `stationId`) VALUES
	(1, 'Football', 'klasdjfl', 50, 1),
	(2, 'RugbyBall', 'Rugby ball', 200, 1),
	(3, 'BasketBall', 'Basket ball', 200, 2),
	(4, 'CricketBall', 'cricket ball', 200, 3);
/*!40000 ALTER TABLE `widget` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
