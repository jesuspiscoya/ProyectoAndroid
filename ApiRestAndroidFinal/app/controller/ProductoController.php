<?php

class ProductoController extends Controller{

    private $productoModelo;

    public function __construct(){
        $this->productoModelo=$this->model('ProductoDao');  
    }

    public function productoCategoria($idCategoria){
        $arreglo = $this->productoModelo->productoCategoria($idCategoria);
        if(is_null($arreglo)){
            echo $this->productoModelo->getMensaje();
        }else{
            echo json_encode(["productos"=>$arreglo]);
        }
    }

}


?>