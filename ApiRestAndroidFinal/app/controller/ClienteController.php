<?php

class ClienteController extends Controller{

    private $clienteModelo;

    public function __construct(){
        $this->clienteModelo=$this->model('ClienteDao');  
    }

    public function clientePerfil($id){
        $cliente = $this->clienteModelo->clientePerfil($id);
        if(is_null($cliente))
            echo $this->clienteModelo->getMensaje();
        else
            echo json_encode(["cliente"=>$cliente]);
    }

    public function clienteLogin(){
        $correo = $_POST["correo"];
        $cliente = $this->clienteModelo->clienteLogin($correo);
        if(is_null($cliente))
            echo $this->clienteModelo->getMensaje();
        else
            echo json_encode(["cliente"=>$cliente]);
    }

    public function clienteInsert(){
        $cliente = new Cliente;
        $nombreCliente = $_POST["nombreCliente"];
        $direccion = $_POST["direccion"];
        $numTelef = $_POST["numTelef"];
        $correo = $_POST["correo"];
        $cliente->setNombreCliente($nombreCliente);
        $cliente->setDireccion($direccion);
        $cliente->setNumTelef($numTelef);
        $cliente->setCorreo($correo);
        $mensaje = $this->clienteModelo->clienteIns($cliente);
        echo $mensaje;
    }

    public function clienteUpdate($idCliente){
        $cliente = new Cliente;
        $nombreCliente = $_POST["nombreCliente"];
        $direccion = $_POST["direccion"];
        $numTelef = $_POST["numTelef"];
        $correo = $_POST["correo"];
        $cliente->setIdCliente($idCliente);
        $cliente->setNombreCliente($nombreCliente);
        $cliente->setDireccion($direccion);
        $cliente->setNumTelef($numTelef);
        $cliente->setCorreo($correo);
        $mensaje = $this->clienteModelo->clienteUpdate($cliente);
        echo $mensaje;
    }

    public function obtenerUltimoCliente(){
        $cliente = $this->clienteModelo->obtenerUltimoCliente();
        if(is_null($cliente))
            echo $this->clienteModelo->getMensaje();
        else
            echo json_encode(["cliente"=>$cliente]);
    }
}
