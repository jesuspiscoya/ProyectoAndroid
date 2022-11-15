<?php

require_once('../app/model/dto/Cliente.php');

class ClienteDao{

    private $connection;
    private $mensaje;

    public function __construct() {
        $this->connection=new Connection;
    }

    public function clienteLogin($correo){
        $sql = "SELECT idCliente, nombreCliente, direccion, numTelef, correo FROM cliente WHERE correo = :correo";
        $Cliente = NULL;
        try {
            $puente = $this->connection->getConnection();
            $consultaPreparada = $puente->prepare($sql);
            $consultaPreparada->bindParam(':correo', $correo, PDO::PARAM_STR);
            if ($consultaPreparada->execute()) {
                if ($fila = $consultaPreparada->fetch(PDO::FETCH_ASSOC)) {
                    $Cliente=new Cliente;
                    $Cliente->setIdCliente($fila["idCliente"]);
                    $Cliente->setNombreCliente($fila["nombreCliente"]);
                    $Cliente->setDireccion($fila["direccion"]);
                    $Cliente->setNumTelef($fila["numTelef"]);
                    $Cliente->setCorreo($fila["correo"]);
                } else {
                    $this->mensaje = "Usuario o contraseña incorrecta";
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
        return $Cliente;
    }

    public function clientePerfil($idCliente){
        $sql = "SELECT idCliente, nombreCliente, direccion, numTelef, correo FROM cliente WHERE idCliente = :idCliente";
        $Cliente = NULL;
        try {
            $puente = $this->connection->getConnection();
            $consultaPreparada = $puente->prepare($sql);
            $consultaPreparada->bindParam(':idCliente', $idCliente, PDO::PARAM_INT);
            if ($consultaPreparada->execute()) {
                if ($fila = $consultaPreparada->fetch(PDO::FETCH_ASSOC)) {
                    $Cliente=new Cliente;
                    $Cliente->setIdCliente($fila["idCliente"]);
                    $Cliente->setNombreCliente($fila["nombreCliente"]);
                    $Cliente->setDireccion($fila["direccion"]);
                    $Cliente->setNumTelef($fila["numTelef"]);
                    $Cliente->setCorreo($fila["correo"]);
                } else {
                    $this->mensaje = "No se ha encontrado ningún cliente con el id ".$idCliente;
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
        return $Cliente;
    }

    public function clienteIns($Cliente){
        $sql = "INSERT INTO cliente(nombreCliente, direccion, numTelef, correo) VALUES(:nombreCliente,:direccion, :numTelef, :correo);";
        try {
            $puente = $this->connection->getConnection();
            $consultaPreparada = $puente->prepare($sql);
            $nombreCliente = $Cliente->getNombreCliente();
            $direccion = $Cliente->getDireccion();
            $numTelef = $Cliente->getNumTelef();
            $correo = $Cliente->getCorreo();
            $consultaPreparada->bindParam(':nombreCliente', $nombreCliente, PDO::PARAM_STR);
            $consultaPreparada->bindParam(':direccion', $direccion, PDO::PARAM_STR);
            $consultaPreparada->bindParam(':numTelef', $numTelef, PDO::PARAM_STR);
            $consultaPreparada->bindParam(':correo', $correo, PDO::PARAM_STR);
            if ($consultaPreparada->execute()) {
                if ($consultaPreparada->rowCount() === 0) {
                    $this->mensaje = "No se ha insertado ningun Cliente";
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

    public function clienteUpdate($Cliente){
        $sql = "UPDATE cliente SET nombreCliente = :nombreCliente, direccion = :direccion, numTelef = :numTelef, correo= :correo WHERE idCliente = :idCliente";
        try {
            $puente = $this->connection->getConnection();
            $consultaPreparada = $puente->prepare($sql);
            $nombreCliente = $Cliente->getNombreCliente();
            $direccion = $Cliente->getDireccion();
            $numTelef = $Cliente->getNumTelef();
            $correo = $Cliente->getCorreo();
            $idCliente = $Cliente->getIdCliente();
            $consultaPreparada->bindParam(':nombreCliente', $nombreCliente, PDO::PARAM_STR);
            $consultaPreparada->bindParam(':direccion', $direccion, PDO::PARAM_STR);
            $consultaPreparada->bindParam(':numTelef', $numTelef, PDO::PARAM_STR);
            $consultaPreparada->bindParam(':correo', $correo, PDO::PARAM_STR);
            $consultaPreparada->bindParam(':idCliente', $idCliente, PDO::PARAM_INT);
            if ($consultaPreparada->execute()) {
                if ($consultaPreparada->rowCount() === 0) {
                    $this->mensaje = "No se ha actualizado ningun Cliente";
                }else{
                    $this->mensaje = "Se ha actualizado correctamente";
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

    public function obtenerUltimoCliente(){
        $sql = "SELECT idCliente, nombreCliente, direccion, numTelef, correo FROM `cliente` ORDER BY idCliente DESC LIMIT 1";
        $Cliente = NULL;
        try {
            $puente = $this->connection->getConnection();
            $consultaPreparada = $puente->prepare($sql);
            if ($consultaPreparada->execute()) {
                if ($fila = $consultaPreparada->fetch(PDO::FETCH_ASSOC)) {
                    $Cliente=new Cliente;
                    $Cliente->setIdCliente($fila["idCliente"]);
                    $Cliente->setNombreCliente($fila["nombreCliente"]);
                    $Cliente->setDireccion($fila["direccion"]);
                    $Cliente->setNumTelef($fila["numTelef"]);
                    $Cliente->setCorreo($fila["correo"]);
                } else {
                    $this->mensaje = "No se ha encontrado ningún cliente";
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
        return $Cliente;
    }

    public function getMensaje(){
        return $this->mensaje;
    }
    
}