package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.services.implementation.GestionBDBigQueryImpl;
import com.conciliacion.parquet.services.implementation.GestionBDPOSTGRESSQLServerImpl;
import com.conciliacion.parquet.services.implementation.GestionBDSQLServerImpl;
import com.conciliacion.parquet.services.interfaces.IGestionFabricaBD;

import java.util.HashMap;
import java.util.Map;

public class ValidarTipoConexion {

    public static IGestionFabricaBD ValidateConexion(String nombreTipoConexion){
        IGestionFabricaBD iGestionBDIndependientes = null;
        if(nombreTipoConexion.equalsIgnoreCase("SQLSERVER")  ){
            iGestionBDIndependientes = new GestionBDSQLServerImpl();
        }else if(nombreTipoConexion.equalsIgnoreCase("POSTGRESQL")  ){
            iGestionBDIndependientes = new GestionBDPOSTGRESSQLServerImpl();
        }else if(nombreTipoConexion.equalsIgnoreCase("BIGQUERY")  ){
            iGestionBDIndependientes = new GestionBDBigQueryImpl();
        }

        return iGestionBDIndependientes;
    }

    public static Object mensajeTipoConexionVacia(){
        Map<String, Object> mapResponse = null;
        mapResponse = new HashMap<String, Object>();
        mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesTipoConexion.MENSAJE_TIPO_CONEXION );
        mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_DATOS_INVALIDOS );
        return mapResponse;
    }


}
