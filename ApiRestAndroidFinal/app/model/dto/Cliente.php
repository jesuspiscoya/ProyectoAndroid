<?php

class Cliente implements JsonSerializable{

    private $idCliente;
    private $nombreCliente;
    private $direccion;
    private $numTelef;
    private $correo;

    public function jsonSerialize (){
		return [
			'idCliente'=>$this->idCliente,
			'nombreCliente'=>$this->nombreCliente,
			'direccion'=>$this->direccion,
            'numTelef'=>$this->numTelef,
            'correo'=>$this->correo,
		];
	}

    public function getIdCliente(){
        return $this->idCliente;
    }

    public function setIdCliente($idCliente){
        $this->idCliente = $idCliente;
    }

    public function getNombreCliente(){
        return $this->nombreCliente;
    }

    public function setNombreCliente($nombreCliente){
        $this->nombreCliente = $nombreCliente;
    }

    public function getDireccion(){
        return $this->direccion;
    }

    public function setDireccion($direccion){
        $this->direccion = $direccion;
    }

    public function getNumTelef(){
        return $this->numTelef;
    }

    public function setNumTelef($numTelef){
        $this->numTelef = $numTelef;
    }

    public function getCorreo(){
        return $this->correo;
    }

    public function setCorreo($correo){
        $this->correo = $correo;
    }
    
}

?>