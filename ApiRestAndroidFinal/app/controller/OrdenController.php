<?php

class OrdenController extends Controller{

    private $ordenModelo;

    public function __construct(){
        $this->ordenModelo=$this->model('OrdenDao');  
    }

    public function ordenesActuales(){
        $ordenes = $this->ordenModelo->ordenSelect();
        if(is_null($ordenes)){
            echo $this->ordenModel->getMensaje();
        }else{
            echo json_encode($ordenes);
        }
    }

    public function ultimoPedido($idCliente){
        $idOrden = $this->ordenModelo->idOrdenFinalCliente($idCliente);
        if(is_null($idOrden)){
            echo $this->ordenModel->getMensaje();
        }else{
            echo json_encode($idOrden);
        }
    }

    public function ordenInsert(){
        $orden = new Orden;
        $direccionOrden = $_POST["direccionOrden"];
        $fechaOrden = $_POST["fechaOrden"];
        $total = $_POST["total"];
        $idCliente = $_POST["idCliente"];
        $tipoServicio = $_POST["tipoServicio"];

        $orden->setDireccionOrden($direccionOrden);
        $orden->setFechaOrden($fechaOrden);
        $orden->setTotal($total);
        $orden->setIdCliente($idCliente);
        $orden->setTipoServicio($tipoServicio);

        $mensaje = $this->ordenModelo->ordenInsert($orden);

        echo $mensaje;
    }

}