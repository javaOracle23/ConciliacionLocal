package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.dto.ConexionDto;

public final class ConstantesPostgreSQL {

    public static String obtenerConexionPostgreSQL(ConexionDto conexionDto){
        StringBuilder cadenaUrl = new StringBuilder();
        //"jdbc:sqlserver://<serverName>:<portNumber>;databaseName=<databaseName>;user=<user>;password=<password>;encrypt=true";
        cadenaUrl.append("jdbc:postgresql://")
                .append(conexionDto.getHost())
                .append(":")
                .append(conexionDto.getPuerto())
                .append("/")
                .append(conexionDto.getNombreBaseDeDatos());

        return cadenaUrl.toString();
    }

    public static final String driverPostgreSql = "org.postgresql.Driver";

    public static final String LISTA_TABLAS = "listatablas";

    public static final String LISTA_COLUMNAS_TABLA = "listacolumnastabla";
}
