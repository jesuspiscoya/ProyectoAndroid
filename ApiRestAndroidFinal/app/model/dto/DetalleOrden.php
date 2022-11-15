<?php

class DetalleOrden implements JsonSerializable{

    private $idOrden;
    private $idProducto;
    private $precioUnit;
    private $totalDetalle;
    private $cantidad;

    public function jsonSerialize (){
		return [
			'idOrden'=>$this->idOrden,
			'idProducto'=>$this->idProducto,
			'precioUnit'=>$this->precioUnit,
            'totalDetalle'=>$this->totalDetalle,
            'cantidad'=>$this->cantidad
		];
	}

    public function getIdOrden(){
        return $this->idOrden;
    }

    public function setIdOrden($idOrden){
        $this->idOrden = $idOrden;
    }

    public function getIdProducto(){
        return $this->idProducto;
    }

    public function setIdProducto($idProducto){
        $this->idProducto = $idProducto;
    }
    
    public function getPrecioUnit(){
        return $this->precioUnit;
    }

    public function setPrecioUnit($precioUnit){
        $this->precioUnit = $precioUnit;
    }

    public function getTotalDetalle(){
        return $this->totalDetalle;
    }

    public function setTotalDetalle($totalDetalle){
        $this->totalDetalle = $totalDetalle;
    }

    public function getCantidad(){
        return $this->cantidad;
    }

    public function setCantidad($cantidad){
        $this->cantidad = $cantidad;
    }
}