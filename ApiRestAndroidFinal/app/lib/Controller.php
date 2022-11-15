<?php

//Plantilla para los demás controllers

class Controller
{

    //cargar modelo
    public function model($model)
    {
        //Cargar modelo
        require_once '../app/model/dao/' . $model . '.php';
        //Instanciar modelo
        return new $model;
    }
}
