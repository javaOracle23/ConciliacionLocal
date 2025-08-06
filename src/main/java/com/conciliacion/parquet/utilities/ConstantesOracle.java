package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.dto.ConexionDto;

public final class ConstantesOracle {

    public static String obtenerConexionOracle(ConexionDto conexionDto) {
        StringBuilder cadenaUrl = new StringBuilder();
        //"jdbc:sqlserver://<serverName>:<portNumber>;databaseName=<databaseName>;user=<user>;password=<password>;encrypt=true";
        cadenaUrl.append("jdbc:oracle:thin:@//")
                .append(conexionDto.getHost())
                .append(":")
                .append(conexionDto.getPuerto())
                .append("/")
                .append(conexionDto.getNombreBaseDeDatos());

        //return cadenaUrl.toString();
        return cadenaUrl.toString();
    }

    public static final String driverOracle = "oracle.jdbc.OracleDriver";

    public static final String LISTA_TABLAS = "listatablas";

    public static final String LISTA_COLUMNAS_TABLA = "listacolumnastabla";
}
