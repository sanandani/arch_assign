create database inventory;
use inventory;
-- MySQL dump 10.13  Distrib 5.1.42, for Win32 (ia32)
--
-- Host: localhost    Database: inventory
-- ------------------------------------------------------
-- Server version	5.1.42-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `seeds`
--

DROP TABLE IF EXISTS `seeds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seeds` (
  `product_code` varchar(10) DEFAULT NULL,
  `description` varchar(80) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` float(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seeds`
--

LOCK TABLES `seeds` WRITE;
/*!40000 ALTER TABLE `seeds` DISABLE KEYS */;
INSERT INTO `seeds` VALUES ('MJ001','Madagascar Jasimine',100,22.00),('BP001','Butterfly Pea',100,26.00),('CS001','Camellia Senensis Tea',80,48.00),('CO001','Chocolate Orage Rudbeckia',120,28.00),('PP001','Purple Pitcher Plant',60,62.00),('AG001','American Ginsing',600,12.00),('QS001','Queen Sago',200,20.00),('AS001','Alpine Strawberry',400,16.00),('DG001','Dwarf Godetia',20,36.00),('BB001','Black Bat Plant',40,100.00),('MB001','Miniature Blue Popcorn',500,11.00),('BM002','Black Maui Orchids',200,150.00),('VF002','Venus Fly Trap',500,30.00),('BC003','Black Currant Whirl Hollyhock',300,23.00);
/*!40000 ALTER TABLE `seeds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shrubs`
--

DROP TABLE IF EXISTS `shrubs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shrubs` (
  `product_code` varchar(10) DEFAULT NULL,
  `description` varchar(80) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` float(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shrubs`
--

LOCK TABLES `shrubs` WRITE;
/*!40000 ALTER TABLE `shrubs` DISABLE KEYS */;
INSERT INTO `shrubs` VALUES ('BC001','Blue Camphor',60,45.00),('KP001','Kangaroo Paw',68,34.00),('YB001','Yellow Buckeye',71,28.00),('BB001','Butterfly Bush',36,81.00),('BB002','Boxwood Bush',48,31.00),('YC001','Yello Callicarpa',30,67.00),('SB001','Smoke Bush',100,26.00),('AF001','African Forsythia',80,38.00),('BP001','Blue Paeonia',60,41.00),('JB001','Juniper Berry',40,18.00),('SH001','Saaz Hops',300,6.00),('QS002','Queen Sago',30,60.00),('GN003','Great Northern Camellias',120,97.00);
/*!40000 ALTER TABLE `shrubs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trees`
--

DROP TABLE IF EXISTS `trees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trees` (
  `product_code` varchar(10) DEFAULT NULL,
  `description` varchar(80) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` float(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trees`
--

LOCK TABLES `trees` WRITE;
/*!40000 ALTER TABLE `trees` DISABLE KEYS */;
INSERT INTO `trees` VALUES ('EF001','Elephant Foot',6,800.00),('BB001','Black Bamboo',30,100.00),('BF001','Banyan Fig',13,325.00),('RSM001','Red Snakebark Maple',200,98.00),('BE001','Box Elder',400,36.00),('JYM001','Japanese Yama Maple',500,42.00),('EOM001','European Olive',62,225.00),('YK001','Yemen Khat',21,625.00),('AT001','Asian Teak',83,260.00),('WW001','Worm Wood',91,115.00),('LC001','Lemon Cypress',280,67.00),('GB001','Ginkgo Biloba',75,80.00),('CT001','Cigar Tree',10,83.00),('AM002','Arden Maple',40,70.00),('FL002','Finger Leaf Elm',16,75.00);
/*!40000 ALTER TABLE `trees` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-02-10 15:33:35

create database orderinfo; 
use orderinfo;
-- MySQL dump 10.13  Distrib 5.1.42, for Win32 (ia32)
--
-- Host: localhost    Database: orderinfo
-- ------------------------------------------------------
-- Server version	5.1.42-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `order_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order_date` varchar(30) DEFAULT NULL,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `address` varchar(80) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `total_cost` float(10,2) DEFAULT NULL,
  `shipped` tinyint(1) DEFAULT NULL,
  `ordertable` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-02-10 19:32:41

create database leaftech;
use lefttech;
-- MySQL dump 10.13  Distrib 5.6.15, for Win32 (x86)
--
-- Host: localhost    Database: leaftech
-- ------------------------------------------------------
-- Server version	5.6.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cultureboxes`
--

DROP TABLE IF EXISTS `cultureboxes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cultureboxes` (
  `productid` varchar(10) DEFAULT NULL,
  `productdescription` varchar(80) DEFAULT NULL,
  `productquantity` int(11) DEFAULT NULL,
  `productprice` float(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cultureboxes`
--

LOCK TABLES `cultureboxes` WRITE;
/*!40000 ALTER TABLE `cultureboxes` DISABLE KEYS */;
INSERT INTO `cultureboxes` VALUES ('PB001','Magenta Vessels',10,350.00),('PB002','Mebrane Raft',75,20.00),('PB003','Growth Mebrane Wetting Agent',200,7.50),('PB004','Phytatray (sterile)',59,86.50),('PB005','B-Cap',45,250.00),('PB006','Sun Bags',170,50.00),('PB007','Plant tissue culture vessel',25,62.60);
/*!40000 ALTER TABLE `cultureboxes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genomics`
--

DROP TABLE IF EXISTS `genomics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genomics` (
  `productid` varchar(10) DEFAULT NULL,
  `productdescription` varchar(80) DEFAULT NULL,
  `productquantity` int(11) DEFAULT NULL,
  `productprice` float(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genomics`
--

LOCK TABLES `genomics` WRITE;
/*!40000 ALTER TABLE `genomics` DISABLE KEYS */;
INSERT INTO `genomics` VALUES ('GN001','Nylon transfer membrane sheet size 12.5 cm × 8 cm ',100,250.00),('GN002','Nylon transfer membrane sheet size 15 cm × 20 cm',100,275.00),('GN003','Nylon transfer membrane sheet size 20 cm × 20 cm ',100,300.00),('GN004','Nylon transfer membrane sheet size 30 cm × 60 cm',100,325.00),('GN005','Nylon transfer membrane disc size 137 mm',100,330.00),('GN006',' Nylon transfer membrane roll size 20 cm × 3.5 m',99,340.00),('GN007','Nylon transfer membrane roll size 20 cm × 12 m ',100,365.00),('GN008','Nylon transfer membrane roll size 30 cm × 3.5 m ',100,385.00),('GN009','Cover glasses size 22 mm × 22 mm',200,25.00),('GN010','Cover glasses size 24 mm × 40 mm',200,25.00),('GN011','Cover glasses size 24 mm × 50 mm ',200,30.00),('GN012','Slide moat microscope slide incubator AC input 115 V',5,2300.00);
/*!40000 ALTER TABLE `genomics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `processing`
--

DROP TABLE IF EXISTS `processing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `processing` (
  `productid` varchar(10) DEFAULT NULL,
  `productdescription` varchar(80) DEFAULT NULL,
  `productquantity` int(11) DEFAULT NULL,
  `productprice` float(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `processing`
--

LOCK TABLES `processing` WRITE;
/*!40000 ALTER TABLE `processing` DISABLE KEYS */;
INSERT INTO `processing` VALUES ('DJ001','Double-ended pestle',100,59.50),('DJ002','Plant tissue grinder',100,78.90),('DJ003','Microcentruifuge tubes',75,25.00),('DJ004','Pellet pestles blue polypropylene',50,90.00),('DJ005','Plant tissue emulsifier',40,47.70),('DJ006','Micro centerfuge',4,1754.00);
/*!40000 ALTER TABLE `processing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `referencematerials`
--

DROP TABLE IF EXISTS `referencematerials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `referencematerials` (
  `productid` varchar(10) DEFAULT NULL,
  `productdescription` varchar(80) DEFAULT NULL,
  `productquantity` int(11) DEFAULT NULL,
  `productprice` float(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `referencematerials`
--

LOCK TABLES `referencematerials` WRITE;
/*!40000 ALTER TABLE `referencematerials` DISABLE KEYS */;
INSERT INTO `referencematerials` VALUES ('RF002','Advances In Medicinal Plant Research',10,82.00),('RF003','Pharmaceutical Validation Methods',15,90.00),('RF004','Pharmaceutical Substances: Syntheses, Patents and Applications',15,530.00),('RF005','Handbook of Stability Testing in Pharmaceutical Development',18,195.90),('RF006','The Encyclopedia of Pharmaceutical Drugs',22,75.00),('RF007','Drug Eruption Reference Manual',60,22.00),('RF008','Medical Botany: Plants Affecting Human Health',11,115.00),('RF009','Chemical Engineering in the Pharmaceutical Industry',21,165.00),('RF010','Plant Processing Equipment',7,100.00),('RF011','Chemical Engineering Design: Principles, Practice, and Economics',17,98.00),('RF001','Handbook of Pharmaceutical Manufacturing Formulation',22,900.00);
/*!40000 ALTER TABLE `referencematerials` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-02-13 15:49:42
