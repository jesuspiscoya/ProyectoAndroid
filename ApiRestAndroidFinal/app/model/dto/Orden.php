<?php

class Orden implements JsonSerializable{

    private $idOrden;
    private $direccionOrden;
    private $fechaOrden;
    private $total;
    private $idCliente;
    private $tipoServicio;

    public function jsonSerialize (){
		return [
			'idOrden'=>$this->idOrden,
			'direccionOrden'=>$this->direccionOrden,
			'fechaOrden'=>$this->fechaOrden,
            'total'=>$this->total,
            'idCliente'=>$this->idCliente,
            'tipoPago'=>$this->tipoPago
		];
	}

    public function getIdOrden(){
        return $this->idOrden;
    }

    public function setIdOrden($idOrden){
        $this->idOrden = $idOrden;
    }

    public function getDireccionOrden(){
        return $this->direccionOrden;
    }

    public function setDireccionOrden($direccionOrden){
        $this->direccionOrden = $direccionOrden;
    }

    public function getFechaOrden(){
        return $this->fechaOrden;
    }

    public function setFechaOrden($fechaOrden){
        $this->fechaOrden = $fechaOrden;
    }

    public function getTotal(){
        return $this->total;
    }

    public function setTotal($total){
        $this->total = $total;
    }

    public function getIdCliente(){
        return $this->idCliente;
    }

    public function setIdCliente($idCliente){
        $this->idCliente = $idCliente;
    }

    public function getTipoServicio(){
        return $this->tipoServicio;
    }

    public function setTipoServicio($tipoServicio){
        $this->tipoServicio = $tipoServicio;
    }
}