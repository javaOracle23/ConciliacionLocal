package com.conciliacion.parquet.services.implementation;


import com.conciliacion.parquet.entity.TipoConexionEntity;
import com.conciliacion.parquet.repository.TipoConexionRepository;
import com.conciliacion.parquet.services.interfaces.ITipoConexionService;
import com.conciliacion.parquet.utilities.ConstantesGenericas;
import com.conciliacion.parquet.utilities.ConstantesTipoConexion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TipoConexionServiceImpl implements ITipoConexionService {

    private static Logger LOGGER = LoggerFactory.getLogger(TipoConexionServiceImpl.class);

    @Autowired(required=false)
    private TipoConexionRepository tipoConexionRepository;

    @Override
    public Object obtenerTipoConexion(int idTipoConexion) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        TipoConexionEntity tipoConexionEntity = null;
        try{
            //tipoConexionEntity = tipoConexionRepository.obtenerTipoConexion(idTipoConexion);
            if(tipoConexionEntity != null){
                mapResponse.put(ConstantesTipoConexion.DATOS_TIPO_CONEXION,  tipoConexionEntity );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesTipoConexion.MENSAJE_TIPO_CONEXION );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_REGISTRO_EXISTENTE );
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
    public Object consultarTiposConexiones() {

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        List<TipoConexionEntity> listTiposConexiones = null;
        try{
            //listTiposConexiones = tipoConexionRepository.consultarTiposConexiones();

            if(listTiposConexiones != null && listTiposConexiones.size() != 0){
                mapResponse.put(ConstantesTipoConexion.LISTA_TIPO_CONEXION, listTiposConexiones );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesTipoConexion.MENSAJE_LISTA_TIPOCONEXIONES );
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
