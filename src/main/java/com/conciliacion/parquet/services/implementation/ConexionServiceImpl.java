package com.conciliacion.parquet.services.implementation;

import com.conciliacion.parquet.converter.implementation.ConexionConvetImpl;
import com.conciliacion.parquet.dto.ConexionDto;
import com.conciliacion.parquet.entity.ConexionEntity;
import com.conciliacion.parquet.entity.TipoConexionEntity;
import com.conciliacion.parquet.repository.ConexionRepository;
import com.conciliacion.parquet.services.interfaces.IConexionService;
import com.conciliacion.parquet.utilities.ConstantesConexion;
import com.conciliacion.parquet.utilities.ConstantesGenericas;
import com.conciliacion.parquet.utilities.ConstantesTipoConexion;
import com.conciliacion.parquet.utilities.validateDto.ValidateConexion;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ConexionServiceImpl implements IConexionService {

    private static Logger LOGGER = LoggerFactory.getLogger(ConexionServiceImpl.class);

    @Autowired(required=false)
    private ConexionRepository conexionRepository;

    @Autowired(required=false)
    private TipoConexionServiceImpl tipoConexionServiceImpl;

    @Override
    public Object guardarConexion(ConexionDto conexionDto) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        ConexionConvetImpl conexionConvetImpl = null;
        ConexionEntity conexionEntity =  null;
        String msgValidacion = "";

        Map<String, Object> mapTipoConexion = (Map<String, Object>)tipoConexionServiceImpl.obtenerTipoConexion(conexionDto.getIdTipoConexion());
        if (!mapTipoConexion.containsKey(ConstantesTipoConexion.DATOS_TIPO_CONEXION)) {
            return ResponseEntity.status(HttpStatus.OK).body(mapTipoConexion);
        }
        TipoConexionEntity tipoConexionEntity =  (TipoConexionEntity)mapTipoConexion.get(ConstantesTipoConexion.DATOS_TIPO_CONEXION);
        if(!tipoConexionEntity.getNombre().equalsIgnoreCase("BIGQUERY")  ){
            msgValidacion = ValidateConexion.ValidateConexion(conexionDto);
        }else{
            msgValidacion = ValidateConexion.ValidateConexionBigQuery(conexionDto);
        }

        if(!msgValidacion.isEmpty()) {
            mapResponse.put(ConstantesGenericas.MENSAJE, msgValidacion);
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_DATOS_INVALIDOS );
            return mapResponse;
        }


        try{
            //conexionEntity = conexionRepository.consultarConexion(conexionDto.getNombre() ,conexionDto.getHost(),conexionDto.getPuerto());
//            if(conexionEntity!=null) {
//                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConexion.MENSAJE_INSERT_CONEXION );
//                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_REGISTRO_EXISTENTE );
//                return mapResponse;
//            }
            conexionConvetImpl = new ConexionConvetImpl();
            conexionEntity = conexionConvetImpl.fromDto(conexionDto);
            conexionEntity.setID_Conexion (1);

            //conexionEntity = conexionRepository.save(conexionEntity);

            if(ConstantesConexion.guardar(conexionDto)) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConexion.MENSAJE_INSERT_OK );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConexion.MENSAJE_INSERT_ERROR_CONEXION );
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
    public Object actualizarConexion(ConexionDto conexionDto) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        ConexionConvetImpl conexionConvetImpl = null;
        ConexionEntity conexionEntity =  null;

        String msgValidacion = ValidateConexion.ValidateConexion(conexionDto);
        if(!msgValidacion.isEmpty()) {
            mapResponse.put(ConstantesGenericas.MENSAJE, msgValidacion);
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_CAMPO_OBLIGATORIO );
            return mapResponse;
        }

        try{
            conexionConvetImpl = new ConexionConvetImpl();
            conexionEntity = conexionConvetImpl.fromDto(conexionDto);

            //conexionEntity = conexionRepository.save(conexionEntity );

            if( ConstantesConexion.actualizarPorId(conexionDto)) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConexion.MENSAJE_UPDATE_OK );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConexion.MENSAJE_UPDATE_ERROR_CONEXION );
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
    public Object consultarConexiones() {

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        List<ConexionDto> listConexiones = null;
        try{
            //listConexiones = conexionRepository.consultarConexiones();
            listConexiones = ConstantesConexion.consultarConexiones();
            if(listConexiones != null && listConexiones.size() != 0){
                mapResponse.put(ConstantesConexion.LISTA_CONEXIONES, listConexiones );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConexion.MENSAJE_LISTA_CONEXIONES );
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
    public Object eliminarConexion(long id) {

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        try{

            if(ConstantesConexion.eliminarPorId(id)){
                //conexionRepository.deleteById(id);
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConexion.MENSAJE_DELETE_CONEXION );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }
//            else{
//                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConexion.MENSAJE_DELETE_ERROR_CONEXION );
//                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_REGISTRO_EXISTENTE );
//            }

        } catch (Exception e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_BASE_DATOS );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            return mapResponse;
        }
        return mapResponse;

    }

    @Override
    public Object obtenerConexion(Integer idConexion) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        ConexionDto conexionDto = null;

        try{
            //ConexionEntity = conexionRepository.obtenerConexion(idConexion);
            conexionDto = ConstantesConexion.consultarConexionesPorId(Long.valueOf(idConexion));
            if(conexionDto != null){
                ConexionConvetImpl conexionConvetImpl = new ConexionConvetImpl();
                mapResponse.put(ConstantesConexion.DATOS_CONEXION, conexionDto );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesConexion.MENSAJE_DATOS_CONEXION );
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
