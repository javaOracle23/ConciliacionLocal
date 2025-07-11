package com.conciliacion.parquet.services.implementation;

import com.conciliacion.parquet.services.interfaces.IGestionFabricaBD;

public class GestionTipoBD {

    public static Boolean probarConexion(IGestionFabricaBD iGestionBDIndependientes){
        iGestionBDIndependientes.probarConexion(null);
        return true;
    }

}
