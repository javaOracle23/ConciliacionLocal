package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.dto.ConexionDto;

public final class ConstantesSQLServer {



    public static String obtenerConexionSQLServer(ConexionDto conexionDto){
        StringBuilder cadenaUrl = new StringBuilder();
        //"jdbc:sqlserver://<serverName>:<portNumber>;databaseName=<databaseName>;user=<user>;password=<password>;encrypt=true";
        cadenaUrl.append("jdbc:sqlserver://")
                .append(conexionDto.getHost())
                .append(".database.windows.net:")
                .append(conexionDto.getPuerto())
                .append(";databaseName=")
                .append(conexionDto.getNombreBaseDeDatos())
                .append(";user=")
                .append(conexionDto.getUsuario())
                .append(";password=")
                .append(conexionDto.getClave())
                .append(";encrypt=true");
        return cadenaUrl.toString();
    }

    public static final String driverSqlServer = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public static final String LISTA_TABLAS = "listatablas";

    public static final String LISTA_COLUMNAS_TABLA = "listacolumnastabla";
}
