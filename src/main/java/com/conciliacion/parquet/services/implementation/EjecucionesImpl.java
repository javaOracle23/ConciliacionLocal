package com.conciliacion.parquet.services.implementation;

import com.conciliacion.parquet.entity.EjecucionesEntity;
import com.conciliacion.parquet.repository.EjecucionesRepository;
import com.conciliacion.parquet.services.interfaces.IEjecucionesService;
import com.conciliacion.parquet.utilities.ConstantesEjecuciones;
import com.conciliacion.parquet.utilities.ConstantesGenericas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EjecucionesImpl implements IEjecucionesService {

    private static Logger LOGGER = LoggerFactory.getLogger(EjecucionesImpl.class);

    @Autowired(required=false)
    private EjecucionesRepository ejecucionesRepository;

    @Override
    public Object consultarEjecuciones() {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        List<EjecucionesEntity> listEjecuciones = null;
        try{
            //listEjecuciones = ejecucionesRepository.consultarEjecuciones();

            if(listEjecuciones != null && listEjecuciones.size() != 0){
                mapResponse.put(ConstantesEjecuciones.LISTA_EJECUCIONES, listEjecuciones );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesEjecuciones.MENSAJE_EJECUCION );
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
    public Object consultarEjecutadosPorIdEjecucion(Integer idEjecucion) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        EjecucionesEntity ejecucionesEntity = null;
        try{
            //ejecucionesEntity = ejecucionesRepository.consultarEjecutadosPorIdEjecucion (idEjecucion);
            if(ejecucionesEntity != null){
                mapResponse.put(ConstantesEjecuciones.EJECUCIONES, ejecucionesEntity );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesEjecuciones.MENSAJE_EJECUCIONES );
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
