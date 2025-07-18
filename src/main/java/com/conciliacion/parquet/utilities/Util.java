package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.dto.ParametrosTXTDto;

import java.util.List;

public class Util {


    public static boolean validarCadenaVacia(String cadena) {
        if(cadena != null && !cadena.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean validarNumeroVacia(Long numero) {
        if(numero != null) {
            return true;
        }
        return false;
    }


    public static boolean validarNumeroEnteroVacio(Integer numero) {
        if(numero != null) {
            return true;
        }
        return false;
    }


    public static boolean validarNumeroDoubleVacia(Double numero) {
        if(numero != null) {
            return true;
        }
        return false;
    }

    public static String construirQuery(ParametrosTXTDto parametrosTXTDto){

        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        List<String> columns = parametrosTXTDto.getColumns();
        int tamColumna = columns.size() -1;
        for(int i=0;i<=tamColumna;i++){
            if(i != tamColumna){
                query.append(columns.get(i) + ",");
            }else{
                query.append(columns.get(i) + " ");
            }
        }
        query.append("FROM ").append(parametrosTXTDto.getTable_name() );
        return query.toString();
    }

    public static String obtenerSubcadena(String palabraInicio,String palabraFin,String texto){

        int inicio = texto.indexOf(palabraInicio);
        int fin = texto.indexOf(palabraFin);
        String subcadena = "";
        if (inicio != -1 && fin != -1) {
            inicio += palabraInicio.length();
            subcadena = texto.substring(inicio, fin);
        }
        return subcadena;
    }


}
