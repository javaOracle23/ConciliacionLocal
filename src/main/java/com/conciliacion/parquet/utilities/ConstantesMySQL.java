package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.dto.ConexionDto;

public final class ConstantesMySQL {

    private ConstantesMySQL() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static String obtenerConexionMySQL(ConexionDto conexionDto) {
        StringBuilder cadenaUrl = new StringBuilder();
        // jdbc:mysql://<host>:<port>/<database>
        cadenaUrl.append("jdbc:mysql://")
                .append(conexionDto.getHost())
                .append(":")
                .append(conexionDto.getPuerto())
                .append("/")
                .append(conexionDto.getNombreBaseDeDatos());
        return cadenaUrl.toString();
    }

    public static final String driverMySQL = "com.mysql.cj.jdbc.Driver";

    public static final String LISTA_TABLAS = "listatablas";

    public static final String LISTA_COLUMNAS_TABLA = "listacolumnastabla";
}
