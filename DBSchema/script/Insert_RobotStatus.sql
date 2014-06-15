-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.17 - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             8.3.0.4694
-- --------------------------------------------------------

USE `spartan`;

INSERT INTO `robotstatus` (`orderNo`, `stn1Visited`, `stn2Visited`, `stn3Visited`, `stn4Visited`, `stn1Need`, `stn2Need`, `stn3Need`, `stn4Need` ) VALUES
	(121, 1, 0, 0, 0, 1, 1, 1, 1),
	(130, 1, 1, 0, 0, 1, 1, 1, 1),
	(131, 1, 1, 1, 0, 1, 1, 1, 1),
	(132, 0, 0, 0, 0, 1, 1, 1, 1),
	(133, 1, 1, 1, 1, 1, 1, 1, 1),
	(134, 1, 0, 1, 0, 1, 1, 1, 1)
    ;
