<?php

require_once('../app/model/dto/Producto.php');

class ProductoDao{

    private $connection;
    private $mensaje;

    public function __construct() {
        $this->connection=new Connection;
    }

    public function productoCategoria($idCategoria){
        $sql = "SELECT idProducto, nombreProducto, precio, idCategoria, imagen FROM producto WHERE idCategoria = :idCategoria";
        $arreglo = NULL;
        try {
            $puente = $this->connection->getConnection();
            $consultaPreparada = $puente->prepare($sql);
            $consultaPreparada->bindParam(':idCategoria', $idCategoria, PDO::PARAM_INT);
            if ($consultaPreparada->execute()) {
                $arreglo = array();
                while ($fila = $consultaPreparada->fetch(PDO::FETCH_ASSOC)) {
                    $producto = new Producto;
                    $producto->setIdProducto($fila["idProducto"]);
                    $producto->setNombreProducto($fila["nombreProducto"]);
                    $producto->setPrecio($fila["precio"]);
                    $producto->setIdCategoria($fila["idCategoria"]);
                    $producto->setImagen($fila["imagen"]);
                    $arreglo[] = $producto;
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

    public function getMensaje(){
        return $this->mensaje;
    }

}

?>