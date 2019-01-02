-- --------------------------------------------------------
-- Host:                         localhost
-- Versión del servidor:         5.7.19 - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para tenejob
CREATE DATABASE IF NOT EXISTS `tenejob` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `tenejob`;

-- Volcando estructura para tabla tenejob.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- La exportación de datos fue deseleccionada.
-- Volcando estructura para tabla tenejob.matching
CREATE TABLE IF NOT EXISTS `matching` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `shift_id` bigint(20) DEFAULT NULL,
  `worker_id` bigint(20) DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKojb7hwsvvw3w72n2frk1cpudx` (`shift_id`),
  KEY `FKt0nldbkutx111phjxkmhc1k5g` (`worker_id`),
  CONSTRAINT `FKojb7hwsvvw3w72n2frk1cpudx` FOREIGN KEY (`shift_id`) REFERENCES `shift` (`id`),
  CONSTRAINT `FKt0nldbkutx111phjxkmhc1k5g` FOREIGN KEY (`worker_id`) REFERENCES `worker` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- La exportación de datos fue deseleccionada.
-- Volcando estructura para tabla tenejob.shift
CREATE TABLE IF NOT EXISTS `shift` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `day` tinyblob,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- La exportación de datos fue deseleccionada.
-- Volcando estructura para tabla tenejob.worker
CREATE TABLE IF NOT EXISTS `worker` (
  `id` bigint(20) NOT NULL,
  `availability` tinyblob,
  `created_at` datetime NOT NULL,
  `payrate` float DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- La exportación de datos fue deseleccionada.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
