-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 15-07-2022 a las 08:42:22
-- Versión del servidor: 10.4.22-MariaDB
-- Versión de PHP: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdyutwusac`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `idCategoria` tinyint(3) UNSIGNED NOT NULL,
  `nombreCategoria` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`idCategoria`, `nombreCategoria`) VALUES
(1, 'Tallarines'),
(2, 'Clasicos'),
(3, 'Sopas'),
(4, 'Bebidas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `idCliente` int(10) UNSIGNED NOT NULL,
  `nombreCliente` varchar(100) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `numTelef` varchar(12) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `password` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`idCliente`, `nombreCliente`, `direccion`, `numTelef`, `correo`, `password`) VALUES
(1, 'usuario', 'Mz f1 lt6 chorrillos', '123456789', 'usuario@gmail.com', 0x0acd7af6ce3a2ccc2e2f2acaac2ac0c9),
(2, 'Abraham', 'direccion', '123456789', 'abraham@gmail.com', 0x26708d53b0bf85d4151ddb97c3fbf08c),
(3, 'Abraham', 'direccion', '123456789', 'abraham@gmail.com', 0x26708d53b0bf85d4151ddb97c3fbf08c),
(4, 'dwad', 'dawdwad', '54863464', 'correo', 0xc6bfe586dd40698d5642b5212b3793b4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleorden`
--

CREATE TABLE `detalleorden` (
  `idOrden` int(11) UNSIGNED NOT NULL,
  `idProducto` tinyint(4) UNSIGNED NOT NULL,
  `precioUnit` decimal(3,2) NOT NULL,
  `totalDetalle` decimal(4,2) NOT NULL,
  `cantidad` tinyint(3) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden`
--

CREATE TABLE `orden` (
  `idOrden` int(11) UNSIGNED NOT NULL,
  `direccionOrden` varchar(100) NOT NULL,
  `fechaOrden` date NOT NULL,
  `total` decimal(4,2) NOT NULL,
  `idCliente` int(11) UNSIGNED NOT NULL,
  `idPago` tinyint(3) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `idPago` tinyint(3) UNSIGNED NOT NULL,
  `tipo` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `idProducto` tinyint(4) UNSIGNED NOT NULL,
  `nombreProducto` varchar(60) NOT NULL,
  `precio` decimal(3,0) NOT NULL,
  `idCategoria` tinyint(4) UNSIGNED NOT NULL,
  `imagen` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`idProducto`, `nombreProducto`, `precio`, `idCategoria`, `imagen`) VALUES
(1, 'Tallarines de pollo en trozo con verduras', '17', 1, '/Tallarines/T1.png'),
(2, 'Tallarín Sahofan con carne', '15', 1, '/Tallarines/T2.png'),
(3, 'Tallarin Taypa con langostino y verdura', '14', 1, '/Tallarines/T3.png'),
(4, 'Pollo chi jau kay', '22', 2, '/Clasicos/C1.PNG'),
(5, 'Pollo ti pa kay', '18', 2, '/Clasicos/C2.png'),
(6, 'Chancho cru yok', '20', 2, '/Clasicos/C3.png'),
(7, 'Arroz chaufa', '18', 2, '/platos/Clasicos/C4.png'),
(8, 'Sopa wantan especial', '18', 3, '/Sopas/s1.png'),
(9, 'Sopa pac pow', '22', 3, '/Sopas/s2.png'),
(10, 'Sopa fuchifu', '19', 3, '/Sopas/s3.png'),
(11, 'Inka Cola personal', '3', 4, '/Bebidas/b1.jpg'),
(12, 'Coca Cola personal', '3', 4, '/Bebidas/b2.png'),
(13, 'Fanta personal', '3', 4, '/Bebidas/b3.png');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`idCategoria`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`idCliente`);

--
-- Indices de la tabla `detalleorden`
--
ALTER TABLE `detalleorden`
  ADD PRIMARY KEY (`idOrden`,`idProducto`),
  ADD KEY `idProducto` (`idProducto`);

--
-- Indices de la tabla `orden`
--
ALTER TABLE `orden`
  ADD PRIMARY KEY (`idOrden`),
  ADD KEY `idCliente` (`idCliente`),
  ADD KEY `idPago` (`idPago`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`idPago`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`idProducto`),
  ADD KEY `idCategoria` (`idCategoria`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `idCategoria` tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `idCliente` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `orden`
--
ALTER TABLE `orden`
  MODIFY `idOrden` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `idPago` tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `idProducto` tinyint(4) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalleorden`
--
ALTER TABLE `detalleorden`
  ADD CONSTRAINT `detalleorden_ibfk_1` FOREIGN KEY (`idOrden`) REFERENCES `orden` (`idOrden`),
  ADD CONSTRAINT `detalleorden_ibfk_2` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`);

--
-- Filtros para la tabla `orden`
--
ALTER TABLE `orden`
  ADD CONSTRAINT `orden_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`),
  ADD CONSTRAINT `orden_ibfk_2` FOREIGN KEY (`idPago`) REFERENCES `pago` (`idPago`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `producto_ibfk_1` FOREIGN KEY (`idCategoria`) REFERENCES `categoria` (`idCategoria`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
