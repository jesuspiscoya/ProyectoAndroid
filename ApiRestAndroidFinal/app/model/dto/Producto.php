<?php

class Producto implements JsonSerializable{

    private $idProducto;
    private $nombreProducto;
    private $precio;
    private $idCategoria;
    private $imagen;

    public function jsonSerialize (){
		return [
			'idProducto'=>$this->idProducto,
			'nombreProducto'=>$this->nombreProducto,
			'precio'=>$this->precio,
            'idCategoria'=>$this->idCategoria,
            'imagen'=>$this->imagen,
		];
	}


    public function getIdProducto(){
        return $this->idProducto;
    }

    public function setIdProducto($idProducto){
        $this->idProducto = $idProducto;
    }

    public function getNombreProducto(){
        return $this->idProducto;
    }

    public function setNombreProducto($nombreProducto){
        $this->nombreProducto = $nombreProducto;
    }

    public function getPrecio(){
        return $this->precio;
    }

    public function setPrecio($precio){
        $this->precio = $precio;
    }

    public function getIdCategoria(){
        return $this->idCategoria;
    }

    public function setIdCategoria($idCategoria){
        $this->idCategoria = $idCategoria;
    }

    public function getImagen(){
        return $this->imagen;
    }

    public function setImagen($imagen){
        $this->imagen = $imagen;
    }

}


?>