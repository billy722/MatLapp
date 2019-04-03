-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: matlapp
-- ------------------------------------------------------
-- Server version	5.7.16-log

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
-- Table structure for table `tb_cursos`
--

DROP TABLE IF EXISTS `tb_cursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_cursos` (
  `id_curso` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion_curso` varchar(45) NOT NULL,
  PRIMARY KEY (`id_curso`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_cursos`
--

LOCK TABLES `tb_cursos` WRITE;
/*!40000 ALTER TABLE `tb_cursos` DISABLE KEYS */;
INSERT INTO `tb_cursos` VALUES (1,'Primero'),(2,'Segundo'),(3,'Tercero'),(4,'Cuarto'),(5,'Quinto'),(6,'Sexto'),(7,'Septimo'),(8,'Octavo');
/*!40000 ALTER TABLE `tb_cursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_ejes_tematicos`
--

DROP TABLE IF EXISTS `tb_ejes_tematicos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_ejes_tematicos` (
  `id_eje_tematico` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion_eje_tematico` varchar(45) NOT NULL,
  PRIMARY KEY (`id_eje_tematico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ejes_tematicos`
--

LOCK TABLES `tb_ejes_tematicos` WRITE;
/*!40000 ALTER TABLE `tb_ejes_tematicos` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_ejes_tematicos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_estado_juego`
--

DROP TABLE IF EXISTS `tb_estado_juego`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_estado_juego` (
  `id_estado` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion_estado` varchar(45) NOT NULL,
  PRIMARY KEY (`id_estado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_estado_juego`
--

LOCK TABLES `tb_estado_juego` WRITE;
/*!40000 ALTER TABLE `tb_estado_juego` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_estado_juego` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_juegos`
--

DROP TABLE IF EXISTS `tb_juegos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_juegos` (
  `id_juego` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `jugador_creador` int(11) NOT NULL,
  `jugador_ganador` int(11) NOT NULL,
  `estado` int(11) NOT NULL,
  PRIMARY KEY (`id_juego`),
  KEY `fk_jugadores_creador_juego_idx` (`jugador_creador`),
  KEY `fk_jugadores_ganador_juego_idx` (`jugador_ganador`),
  KEY `fk_estado_juegos_idx` (`estado`),
  CONSTRAINT `fk_estado_juegos` FOREIGN KEY (`estado`) REFERENCES `tb_estado_juego` (`id_estado`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_jugadores_creador_juego` FOREIGN KEY (`jugador_creador`) REFERENCES `tb_jugadores` (`rut`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_jugadores_ganador_juego` FOREIGN KEY (`jugador_ganador`) REFERENCES `tb_jugadores` (`rut`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_juegos`
--

LOCK TABLES `tb_juegos` WRITE;
/*!40000 ALTER TABLE `tb_juegos` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_juegos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_jugadores`
--

DROP TABLE IF EXISTS `tb_jugadores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_jugadores` (
  `rut` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `curso` int(11) NOT NULL,
  PRIMARY KEY (`rut`),
  KEY `fk_curso_idx` (`curso`),
  CONSTRAINT `fk_curso` FOREIGN KEY (`curso`) REFERENCES `tb_cursos` (`id_curso`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18273353 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_jugadores`
--

LOCK TABLES `tb_jugadores` WRITE;
/*!40000 ALTER TABLE `tb_jugadores` DISABLE KEYS */;
INSERT INTO `tb_jugadores` VALUES (18273352,'Billy Salazar',1);
/*!40000 ALTER TABLE `tb_jugadores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_preguntarjetas`
--

DROP TABLE IF EXISTS `tb_preguntarjetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_preguntarjetas` (
  `id_preguntarjeta` int(11) NOT NULL AUTO_INCREMENT,
  `ruta_imagen` text NOT NULL,
  `alternativa_1` varchar(45) NOT NULL,
  `alternativa_2` varchar(45) NOT NULL,
  `alternativa_3` varchar(45) NOT NULL,
  `alternativa_4` varchar(45) NOT NULL,
  `alternativa_correcta` int(11) NOT NULL,
  `eje_tematico` int(11) NOT NULL,
  `curso` int(11) NOT NULL,
  PRIMARY KEY (`id_preguntarjeta`),
  KEY `fk_eje_tematico_preguntarjeta_idx` (`eje_tematico`),
  KEY `fk_curso_preguntarjeta_idx` (`curso`),
  CONSTRAINT `fk_curso_preguntarjeta` FOREIGN KEY (`curso`) REFERENCES `tb_cursos` (`id_curso`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_eje_tematico_preguntarjeta` FOREIGN KEY (`eje_tematico`) REFERENCES `tb_ejes_tematicos` (`id_eje_tematico`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_preguntarjetas`
--

LOCK TABLES `tb_preguntarjetas` WRITE;
/*!40000 ALTER TABLE `tb_preguntarjetas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_preguntarjetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_registro_respuestas`
--

DROP TABLE IF EXISTS `tb_registro_respuestas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_registro_respuestas` (
  `id_respuesta` int(11) NOT NULL AUTO_INCREMENT,
  `alumno` int(11) NOT NULL,
  `pregunta` int(11) NOT NULL,
  `correcta` int(11) NOT NULL COMMENT '0:incorrecta\n1:correcta',
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `id_juego` int(11) NOT NULL,
  PRIMARY KEY (`id_respuesta`),
  KEY `fk_alumnos_respuesta_idx` (`alumno`),
  KEY `fk_preguntarjeta_respuesta_idx` (`pregunta`),
  KEY `fk_juegos_respuestas_idx` (`id_juego`),
  CONSTRAINT `fk_alumnos_respuesta` FOREIGN KEY (`alumno`) REFERENCES `tb_jugadores` (`rut`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_juegos_respuestas` FOREIGN KEY (`id_juego`) REFERENCES `tb_juegos` (`id_juego`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_preguntarjeta_respuesta` FOREIGN KEY (`pregunta`) REFERENCES `tb_preguntarjetas` (`id_preguntarjeta`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_registro_respuestas`
--

LOCK TABLES `tb_registro_respuestas` WRITE;
/*!40000 ALTER TABLE `tb_registro_respuestas` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_registro_respuestas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'matlapp'
--

--
-- Dumping routines for database 'matlapp'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-03 18:49:18
