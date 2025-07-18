package com.conciliacion.parquet.utilities.validateDto;

import com.conciliacion.parquet.dto.ConexionDto;
import com.conciliacion.parquet.utilities.ConstantesGenericas;
import com.conciliacion.parquet.utilities.Util;

public class ValidateConexion {

    public static String ValidateConexion(ConexionDto conexionDto){

        boolean esObligatorio = false;

        if(!Util.validarNumeroEnteroVacio (conexionDto.getIdTipoConexion())){
            esObligatorio = true;
        }

        if(!Util.validarCadenaVacia(conexionDto.getNombre())){
            esObligatorio = true;
        }

        if(!Util.validarCadenaVacia(conexionDto.getHost())){
            esObligatorio = true;
        }

        if(!Util.validarCadenaVacia(conexionDto.getPuerto())){
            esObligatorio = true;
        }

        if(!Util.validarCadenaVacia(conexionDto.getNombreBaseDeDatos())){
            esObligatorio = true;
        }

        if(!Util.validarCadenaVacia(conexionDto.getUsuario())){
            esObligatorio = true;
        }

        if(!Util.validarCadenaVacia(conexionDto.getClave())){
            esObligatorio = true;
        }

        if(esObligatorio){
            return ConstantesGenericas.MENSAJE_CAMPOS_OBLIGATORIOS;
        }
        return "";
    }


    public static String ValidateConexionBigQuery(ConexionDto conexionDto){

        boolean esObligatorio = false;

        if(!Util.validarNumeroEnteroVacio (conexionDto.getIdTipoConexion())){
            esObligatorio = true;
        }

        if(!Util.validarCadenaVacia(conexionDto.getNombre())){
            esObligatorio = true;
        }


        if(!Util.validarCadenaVacia(conexionDto.getNombreBaseDeDatos())){
            esObligatorio = true;
        }

        if(!Util.validarCadenaVacia(conexionDto.getJsonBigQuery() )){
            esObligatorio = true;
        }


        if(esObligatorio){
            return ConstantesGenericas.MENSAJE_CAMPOS_OBLIGATORIOS;
        }
        return "";
    }

}
