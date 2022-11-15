<?php

//Configuraciones contiene la programación que se trae desde la url, el controlador, etc.
//Este archivo va a mapear la url en el navegador en el siguiente orden
//1. Contolador
//2. Método
//3. Parámetro

class Core{
    protected $controladorActual = 'ClienteController';
    protected $metodoActual = 'clienteLogin';
    protected $parametros = [];

    public function __construct(){
        $url = $this->getUrl();
        if (isset($url) && file_exists('../app/controller/' . $url[0] . '.php')) {
            $this->controladorActual = ucwords($url[0]);
            unset($url[0]);
        }
        require_once '../app/controller/' . $this->controladorActual . '.php';
        $this->controladorActual = new $this->controladorActual;

        //Obtener método
        if (isset($url[1])) {
            if (method_exists($this->controladorActual, $url[1])) {
                $this->metodoActual = $url[1];
                unset($url[1]);
            }
        }

        //Obtener los posibles parametros
        $this->parametros = isset($url) ? array_values($url) : [];
        //Llamamos o invocamos al método de la clase especificado
        //Más informacion a este link https://www.php.net/manual/es/function.call-user-func-array.php 
        call_user_func_array(array($this->controladorActual, $this->metodoActual), $this->parametros);
    }

    public function getUrl(){
        if (isset($_GET['url'])) {
            //rtrim elimina el ultimo espacio indicado por el segundo parametro y retorna dicha cadena
            $url = rtrim($_GET['url'], '/');
            //Se encarga de validar si la cadena es lo que se especifica en su segundo parametro, es decir, una url
            //Devuelve la cadena en caso de exito, falso si es error
            $url = filter_var($url, FILTER_SANITIZE_URL);
            if ($url) {
                //Divide un string en varios string y el delimitador es el primer parametro
                $url = explode('/', $url);
                return $url;
            }
        }
    }
}
