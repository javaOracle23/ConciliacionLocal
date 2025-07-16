package com.conciliacion.parquet.services.implementation;


import com.conciliacion.parquet.entity.ParametrosEjecutadosEntity;
import com.conciliacion.parquet.repository.ParametrosEjecutadosRepository;
import com.conciliacion.parquet.services.interfaces.IParametrosEjecutadosService;
import com.conciliacion.parquet.utilities.ConstantesGenericas;
import com.conciliacion.parquet.utilities.ConstantesParametrosEjecuciones;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParametrosEjecutadosServiceImpl implements IParametrosEjecutadosService {

    private static Logger LOGGER = LoggerFactory.getLogger(ParametrosEjecutadosServiceImpl.class);

    @Autowired(required=false)
    private ParametrosEjecutadosRepository parametrosEjecutadosRepository;

    @Override
    public Object consultarParametrosEjecutadosPorIdEjecucion(Integer idEjecucion) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        ParametrosEjecutadosEntity parametrosEjecutadosEntity = null;
        try{
            //parametrosEjecutadosEntity = parametrosEjecutadosRepository.consultarParametrosEjecutadosPorIdEjecucion(idEjecucion);
            parametrosEjecutadosEntity = ConstantesParametrosEjecuciones.consultarParametrosEjecutadosPorId(Long.valueOf(idEjecucion));
            if(parametrosEjecutadosEntity != null){
                mapResponse.put(ConstantesParametrosEjecuciones.PARAMETROS_EJECUCIONES, parametrosEjecutadosEntity );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesParametrosEjecuciones.MENSAJE_PARAMETROS_EJECUCION );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            }
        } catch (Exception e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_BASE_DATOS );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            return mapResponse;
        }
        return mapResponse;
    }

    @Override
    public Object consultarParametrosEjecutados() {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        List<ParametrosEjecutadosEntity> listParametrosEjecutados = null;
        try{
            //listParametrosEjecutados = parametrosEjecutadosRepository.consultarParametrosEjecutados();
            listParametrosEjecutados = ConstantesParametrosEjecuciones.consultarParametrosEjecutados();
            if(listParametrosEjecutados != null && listParametrosEjecutados.size() != 0){
                mapResponse.put(ConstantesParametrosEjecuciones.LISTA_PARAMETROS_EJECUCIONES, listParametrosEjecutados );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesParametrosEjecuciones.MENSAJE_LISTA_PARAMETROS_EJECUCION );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            }

        } catch (Exception e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_BASE_DATOS );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            return mapResponse;
        }
        return mapResponse;
    }


}
