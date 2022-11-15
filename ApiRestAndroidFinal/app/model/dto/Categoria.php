<?php

class Categoria implements JsonSerializable{

    private $idCategoria;
    private $nombreCategoria;

    public function jsonSerialize (){
		return [
			'idCategoria'=>$this->idCategoria,
			'nombreCategoria'=>$this->nombreCategoria
		];
	}

    public function getIdCategoria(){
        return $this->idCategoria;
    }

    public function setIdCategoria($idCategoria){
        $this->idCategoria = $idCategoria;
    }

    public function getNombreCategoria(){
        return $this->nombreCategoria;
    }

    public function setNombreCategoria($nombreCategoria){
        $this->nombreCategoria = $nombreCategoria;
    }

}

?>