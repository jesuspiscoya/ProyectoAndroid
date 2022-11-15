<?php

//Conexion a la base de datos

class Connection{

    private $host=DB_HOST;
    private $user=DB_USER;
    private $pass=DB_PASS;
    private $base=DB_NAME;

    public function getConnection(){
        $connection=NULL;
        try {
            $connection=new PDO("mysql:host=".$this->host.";dbname=".$this->base,$this->user,$this->pass);
            $connection->setAttribute(PDO::ERRMODE_WARNING, PDO::ERRMODE_EXCEPTION);
            $connection->exec('SET CHARACTER SET utf8');
        } catch (PDOException $e) {
            throw new Exception('Error en la conexion '.$e->getMessage());
        }
        return $connection;
    }

}