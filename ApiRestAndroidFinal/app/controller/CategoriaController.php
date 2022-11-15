<?php

class CategoriaController extends Controller{

    private $productoModelo;

    public function __construct(){
        $this->productoModelo=$this->model('CategoriaDao');  
    }

    public function categoriaSelect(){
        $arreglo = $this->productoModelo->categoriaSelect();
        if(is_null($arreglo)){
            echo $this->productoModelo->getMensaje();
        }else{
            echo json_encode(["categorias"=>$arreglo]);
        }
    }

}

?>