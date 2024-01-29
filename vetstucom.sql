-- phpMyAdmin SQL Dump
-- version 4.0.4.2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 11-02-2021 a las 18:19:44
-- Versión del servidor: 5.6.13
-- Versión de PHP: 5.4.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `vetstucom`
--
CREATE DATABASE IF NOT EXISTS `vetstucom` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `vetstucom`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `expedientes`
--

CREATE TABLE IF NOT EXISTS `expedientes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `apellidos` varchar(30) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `dni` varchar(12) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `cp` varchar(6) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `fechaalta` date NOT NULL,
  `telefono` varchar(12) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `nmascotas` int(11) NOT NULL,
  `usuarioalta` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usuarioAlta` (`usuarioalta`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `apellidos` varchar(25) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `dni` varchar(12) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `matricula` varchar(45) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `pass` varchar(8) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `tipousuario` int(11) NOT NULL,
  `ultimoacceso` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `apellidos`, `dni`, `matricula`, `pass`, `tipousuario`, `ultimoacceso`) VALUES
(1, 'admin', 'admin', '12345678Z', 'Ingeniero', 'admin', 3, NULL);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `expedientes`
--
ALTER TABLE `expedientes`
  ADD CONSTRAINT `expedientes_ibfk_1` FOREIGN KEY (`usuarioalta`) REFERENCES `usuarios` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

select * from usuarios;
select * from expedientes;

