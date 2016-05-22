/*
SQLyog ‰ºÅ‰∏öÁâà - MySQL GUI v8.14 
MySQL - 5.6.24 : Database - dsdata
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dsdata` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dsdata`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `commentid` varchar(32) NOT NULL,
  `problemid` varchar(32) DEFAULT NULL,
  `userid` varchar(32) DEFAULT NULL,
  `ccontent` longtext,
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`commentid`),
  KEY `FK_problemToComment` (`problemid`),
  KEY `FK_userToComent` (`userid`),
  CONSTRAINT `FK_problemToComment` FOREIGN KEY (`problemid`) REFERENCES `problem` (`problemid`),
  CONSTRAINT `FK_userToComent` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='∆¿¬€µƒ±Ì';

/*Table structure for table `problem` */

DROP TABLE IF EXISTS `problem`;

CREATE TABLE `problem` (
  `problemid` varchar(32) NOT NULL,
  `problemname` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`problemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ã‚ƒøµƒ±Ì';

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userid` varchar(32) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='”√ªß±Ì';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
