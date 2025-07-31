package com.conciliacion.parquet.services.implementation;

import com.conciliacion.parquet.converter.implementation.ParametrizacionConvertImpl;
import com.conciliacion.parquet.dto.ParametrizacionDto;
import com.conciliacion.parquet.entity.ParametrizacionEntity;
import com.conciliacion.parquet.repository.ParametrizacionRepository;
import com.conciliacion.parquet.services.interfaces.IParametrizacionService;
import com.conciliacion.parquet.utilities.ConstantesGenericas;
import com.conciliacion.parquet.utilities.ConstantesParametrizacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParametrizacionServiceImpl implements IParametrizacionService {


    private static Logger LOGGER = LoggerFactory.getLogger(ParametrizacionServiceImpl.class);

    @Autowired(required=false)
    private ParametrizacionRepository parametrizacionRepository;

    @Override
    public Object obtenerParametrizacion(Integer idParametrizacion) {

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        ParametrizacionDto parametrizacionDto = null;
        try{
            //parametrizacionEntity = parametrizacionRepository.obtenerParametrizacion(idParametrizacion);
            parametrizacionDto = ConstantesParametrizacion.consultarParametrizacionPorId(Long.valueOf(idParametrizacion));
            if(parametrizacionDto != null){
                mapResponse.put(ConstantesParametrizacion.PARAMETRIZACION, parametrizacionDto );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesParametrizacion.MENSAJE_PARAMETRIZACION );
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
    public Object guardarParametrizacion(ParametrizacionDto parametrizacionDto ) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        ParametrizacionConvertImpl parametrizacionConvert = null;
        ParametrizacionEntity parametrizacionEntity =  null;

        try{
            parametrizacionConvert = new ParametrizacionConvertImpl();
            parametrizacionEntity = parametrizacionConvert.fromDto(parametrizacionDto);
            parametrizacionEntity.setIdParameto(null);
            //parametrizacionEntity = parametrizacionRepository.save(parametrizacionEntity);
            ConstantesParametrizacion.guardar(parametrizacionDto);

            if(ConstantesParametrizacion.guardar(parametrizacionDto)) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesParametrizacion.MENSAJE_INSERT_OK );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesParametrizacion.MENSAJE_INSERT_ERROR_PARAMETRIZACION);
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
    public Object actualizarParametrizacion(ParametrizacionDto parametrizacionDto) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        ParametrizacionConvertImpl parametrizacionConvert = null;
        ParametrizacionEntity parametrizacionEntity =  null;

        try{
            parametrizacionConvert = new ParametrizacionConvertImpl();
            parametrizacionEntity = parametrizacionConvert.fromDto(parametrizacionDto);
            //parametrizacionEntity = parametrizacionRepository.save(parametrizacionEntity);

            if(ConstantesParametrizacion.actualizarPorId(parametrizacionDto)) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesParametrizacion.MENSAJE_UPDATE_OK );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesParametrizacion.MENSAJE_UPDATE_ERROR_PARAMETRIZACION);
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
    public Object consultarParametrizacion() {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        List<ParametrizacionDto> listParametrizacion = null;
        try{
            //listParametrizacion = parametrizacionRepository.consultarParametrizaciones();
            listParametrizacion = ConstantesParametrizacion.consultarParametrizaciones();

            if(listParametrizacion != null && !listParametrizacion.isEmpty()){
                mapResponse.put(ConstantesParametrizacion.LISTA_PARAMETRIZACION, listParametrizacion );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesParametrizacion.MENSAJE_LISTA_PARAMETRIZACION );
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
    public Object eliminarParametrizacion(Integer id) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        try{

//            if(parametrizacionRepository.existsById(id)){
//                parametrizacionRepository.deleteById(id);
//                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesParametrizacion.MENSAJE_DELETE_PARAMETRIZACION );
//                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
//            }else{
//                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesParametrizacion.MENSAJE_DELETE_PARAMETRIZACION);
//                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_REGISTRO_EXISTENTE );
//            }
            if(ConstantesParametrizacion.eliminarPorId(Long.valueOf(id))){
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesParametrizacion.MENSAJE_DELETE_PARAMETRIZACION );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
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
    public Object consultarParametrizacionPorIdConciliacion(Integer idConciliacion) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        ParametrizacionEntity parametrizacionEntity = null;
        try{
            //parametrizacionEntity = parametrizacionRepository.consultarParametrizacionPorIdConciliacion(idConciliacion);
            if(parametrizacionEntity != null){
                mapResponse.put(ConstantesParametrizacion.PARAMETRIZACION, parametrizacionEntity );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesParametrizacion.MENSAJE_PARAMETRIZACION_CONCILIACION );
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
    public Object consultarParametrizacionPorIdTipoParametro(Integer idTipoParametro) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        ParametrizacionEntity parametrizacionEntity = null;
        try{
            //parametrizacionEntity = parametrizacionRepository.consultarParametrizacionPorIdTipoParametro(idTipoParametro);
            if(parametrizacionEntity != null){
                mapResponse.put(ConstantesParametrizacion.PARAMETRIZACION, parametrizacionEntity );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesParametrizacion.MENSAJE_PARAMETRIZACION_CONCILIACION );
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
