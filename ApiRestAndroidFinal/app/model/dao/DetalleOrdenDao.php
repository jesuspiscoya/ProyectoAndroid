<?php

require_once('../app/model/dto/DetalleOrden.php');

class DetalleOrdenDao{

    private $connection;
    private $mensaje;

    public function __construct(){
        $this->connection = new Connection;        
    }

    public function detalleOrdenSelect($idOrden){
        $sql = "SELECT p.nombreProducto, p.imagen, p.precio, do.cantidad, do.totalDetalle FROM detalleorden as do INNER JOIN producto as p on do.idProducto = p.idProducto WHERE idOrden = :idOrden";
        $arreglo = NULL;
        try {
            $puente = $this->connection->getConnection();
            $consultaPreparada = $puente->prepare($sql);
            $consultaPreparada->bindParam(":idOrden", $idOrden, PDO::PARAM_INT);
            if ($consultaPreparada->execute()) {
                $arreglo = array();
                while ($fila = $consultaPreparada->fetch(PDO::FETCH_ASSOC)) {
                    $elemento = [];
                    $elemento[] = $fila["nombreProducto"];
                    $elemento[] = $fila["imagen"];
                    $elemento[] = $fila["precio"];
                    $elemento[] = $fila["cantidad"];
                    $elemento[] = $fila["totalDetalle"];
                    $arreglo[] = $elemento;
                }
                if (count($arreglo) === 0) {
                    $this->mensaje = "No existen registros en la tabla producto";
                }
            } else {
                $this->mensaje = "Error en la ejecucion de la consulta";
            }
            $consultaPreparada->closeCursor();
        } catch (Exception $e) {
            $this->mensaje = $e->getMessage();
        } finally {
            $puente = NULL;
        }
        return $arreglo;
    }

    public function detalleOrdenInsert($detalleOrden){
        $sql = "INSERT INTO detalleorden VALUES (:idOrden, :idProducto, :precioUnit, :totalDetalle, :cantidad)";
        try {
    $puente = $this->connection->getConnection();
            $consultaPreparada = $puente->prepare($sql);
            $idOrden = $detalleOrden->getIdOrden();
            $idProducto = $detalleOrden->getIdProducto();
            $precioUnit = $detalleOrden->getPrecioUnit();
            $totalDetalle = $detalleOrden->getTotalDetalle();
            $cantidad = $detalleOrden->getCantidad();
            $consultaPreparada->bindParam(':idOrden', $idOrden, PDO::PARAM_INT);
            $consultaPreparada->bindParam(':idProducto', $idProducto, PDO::PARAM_INT);
            $consultaPreparada->bindParam(':precioUnit', $precioUnit, PDO::PARAM_STR);
            $consultaPreparada->bindParam(':totalDetalle', $totalDetalle, PDO::PARAM_STR);
            $consultaPreparada->bindParam(':cantidad', $cantidad, PDO::PARAM_STR);
            if ($consultaPreparada->execute()) {
                if ($consultaPreparada->rowCount() === 0) {
                    $this->mensaje = "No se ha insertado ningun Detalle";
                }else{
                    $this->mensaje = "Se ha registrado correctamente";
                }
            }
            $consultaPreparada->closeCursor();
        } catch (Exception $e) {
            $this->mensaje = $e->getMessage();
        } finally {
            $puente = NULL;
        }
        return $this->mensaje;
    }

    public function getMensaje(){
        return $this->mensaje;
    }
}