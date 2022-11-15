<?php

require_once('../app/model/dto/Categoria.php');

class CategoriaDao{

    private $connection;
    private $mensaje;

    public function __construct() {
        $this->connection=new Connection;
    }

    public function categoriaSelect(){
        $sql = "SELECT idCategoria, nombreCategoria FROM categoria";
        $arreglo = NULL;
        try {
            $puente = $this->connection->getConnection();
            $consultaPreparada = $puente->prepare($sql);
            if ($consultaPreparada->execute()) {
                $arreglo = array();
                while ($fila = $consultaPreparada->fetch(PDO::FETCH_ASSOC)) {
                    $categoria = new Categoria;
                    $categoria->setIdCategoria($fila["idCategoria"]);
                    $categoria->setNombreCategoria($fila["nombreCategoria"]);
                    $arreglo[] = $categoria;
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