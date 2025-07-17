package com.conciliacion.parquet.services.implementation;

import com.conciliacion.parquet.converter.implementation.ConciliacionConvertImpl;
import com.conciliacion.parquet.dto.ConciliacionDto;
import com.conciliacion.parquet.entity.ConciliacionEntity;
import com.conciliacion.parquet.repository.ConciliacionRepository;
import com.conciliacion.parquet.services.interfaces.IConciliacionService;
import com.conciliacion.parquet.utilities.ConstantesConciliacion;
import com.conciliacion.parquet.utilities.ConstantesGenericas;
import org.apache.parquet.example.data.simple.LongValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConciliacionServiceImpl implements IConciliacionService {


    private static Logger LOGGER = LoggerFactory.getLogger(ConciliacionServiceImpl.class);

    @Autowired(required=false)
    private ConciliacionRepository conciliacionRepository;

    @Override
    public Object obtenerConciliacion(Integer idConciliacion) {

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        ConciliacionDto conciliacionDto = null;
        try{
            //conciliacionEntity = conciliacionRepository.obtenerConciliacion(idConciliacion);
            conciliacionDto = ConstantesConciliacion.consultarConciliacionPorId(Long.valueOf(idConciliacion));
            if(conciliacionDto != null){
                mapResponse.put(ConstantesConciliacion.CONCILIACION, conciliacionDto );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConciliacion.MENSAJE_CONCILIACION );
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
    public Object guardarConciliacion(ConciliacionDto conciliacionDto) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        ConciliacionConvertImpl conciliacionConvert = null;
        ConciliacionEntity conciliacionEntity =  null;

        try{
            if (!conciliacionDto.getNombreConciliacion().isEmpty()){
//                conciliacionConvert = new ConciliacionConvertImpl();
//                conciliacionEntity = conciliacionConvert.fromDto(conciliacionDto);
//                conciliacionEntity.setIdConciliacion (null);
//                conciliacionEntity.setFechaCreacion(new Date());
                //conciliacionEntity = conciliacionRepository.save(conciliacionEntity);

                if(ConstantesConciliacion.guardar(conciliacionDto)) {
                    mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConciliacion.MENSAJE_INSERT_OK );
                    mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
                }else{
                    mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConciliacion.MENSAJE_INSERT_ERROR_CONCILIACION);
                    mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
                }
            } else {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CAMPOS_OBLIGATORIOS);
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_CAMPO_OBLIGATORIO);
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
    public Object actualizarConciliacion(ConciliacionDto conciliacionDto) {
        Map<String, Object> mapResponse = new HashMap<>();

        try {
            if (ConstantesConciliacion.actualizarPorId(conciliacionDto)) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConciliacion.MENSAJE_UPDATE_OK);
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK);
            }else {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConciliacion.MENSAJE_UPDATE_ERROR_CONCILIACION);
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD);
            }

        } catch (Exception e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_BASE_DATOS);
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD);
            LOGGER.error(ConstantesGenericas.MENSAJE, e);
        }

        return mapResponse;
    }

    @Override
    public Object consultarConciliacion() {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        List<ConciliacionDto> listConciliacion = null;
        try{
            //listConciliacion = conciliacionRepository.consultarConcilaciones();
            listConciliacion = ConstantesConciliacion.consultarConciliacion();
            if(listConciliacion != null && !listConciliacion.isEmpty()){
                mapResponse.put(ConstantesConciliacion.LISTA_CONCILIACION, listConciliacion );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConciliacion.MENSAJE_LISTA_CONCILIACION );
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
    public Object eliminarConciliacion(Long id) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        try{

            if(ConstantesConciliacion.eliminarPorId(id)){
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConciliacion.MENSAJE_DELETE_CONCILIACION );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConciliacion.MENSAJE_DELETE_CONCILIACION);
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
}
