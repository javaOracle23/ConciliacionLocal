package com.conciliacion.parquet.services.implementation;

import com.conciliacion.parquet.dto.ConexionDto;
import com.conciliacion.parquet.entity.AchivoExtraccionEntity;
import com.conciliacion.parquet.entity.ParametrizacionEntity;
import com.conciliacion.parquet.entity.TipoConexionEntity;
import com.conciliacion.parquet.repository.AchivoExtraccionRepository;
import com.conciliacion.parquet.services.interfaces.IGestionBDGeneral;
import com.conciliacion.parquet.services.interfaces.IGestionFabricaBD;
import com.conciliacion.parquet.services.interfaces.IRepositorioAzure;
import com.conciliacion.parquet.utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GestionBDGeneralServiceImpl implements IGestionBDGeneral {

    @Autowired(required=false)
    private ParametrizacionServiceImpl parametrizacionServiceImpl;
    @Autowired(required=false)
    private ConexionServiceImpl conexionServiceImpl;
    @Autowired(required=false)
    private TipoConexionServiceImpl tipoConexionServiceImpl;
    @Autowired(required=false)
    private IRepositorioAzure iRepositorioAzure;

    @Autowired(required=false)
    private AchivoExtraccionRepository achivoExtraccionRepository;

    @Override
    public IGestionFabricaBD agregarBaseDatosPorIdConciliacion(Integer idConciliacion) {
        return null;
    }


    private ConexionDto conexionDto;
    private TipoConexionEntity tipoConexionEntity;
    private IGestionFabricaBD iGestionFabricaBD;

    @Override
    public Object extraerGenerarArchivoOrigenPorIdConciliacion(Integer idConciliacion) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        //Map<String, Object> mapParametrizacionConciliacion = (Map<String, Object>)parametrizacionServiceImpl.consultarParametrizacionPorIdConciliacion(idConciliacion);
        ParametrizacionEntity parametrizacionEntity = ConstantesParametrizacion.consultarParametrizacionPorIdConciliacion(Long.valueOf(idConciliacion));
//        if (!mapParametrizacionConciliacion.containsKey(ConstantesParametrizacion.PARAMETRIZACION)) {
//            return mapParametrizacionConciliacion;
//        }

        //ParametrizacionEntity parametrizacionEntity = (ParametrizacionEntity)mapParametrizacionConciliacion.get(ConstantesParametrizacion.PARAMETRIZACION);
        Map<String, Object> mapConexion = (Map<String, Object>)conexionServiceImpl.obtenerConexion(parametrizacionEntity.getIdConexion1());
        if (!mapConexion.containsKey(ConstantesConexion.DATOS_CONEXION)) {
            return mapConexion;
        }
        conexionDto = (ConexionDto)mapConexion.get(ConstantesConexion.DATOS_CONEXION);
        Map<String, Object> mapTipoConexion = (Map<String, Object>)tipoConexionServiceImpl.obtenerTipoConexion(conexionDto.getIdTipoConexion());
        if (!mapTipoConexion.containsKey(ConstantesTipoConexion.DATOS_TIPO_CONEXION)) {
            return mapTipoConexion;
        }
        tipoConexionEntity =  (TipoConexionEntity)mapTipoConexion.get(ConstantesTipoConexion.DATOS_TIPO_CONEXION);
        iGestionFabricaBD = ValidarTipoConexion.ValidateConexion(tipoConexionEntity.getNombre());
        if(iGestionFabricaBD == null){
            return ValidarTipoConexion.mensajeTipoConexionVacia();
        }
        AchivoExtraccionEntity achivoExtraccionEntity = new AchivoExtraccionEntity();
        achivoExtraccionEntity.setId_extraccion(null);
        achivoExtraccionEntity.setIdConciliacion(parametrizacionEntity.getIdConciliacion());
        achivoExtraccionEntity.setNombreOrigen(ConstantesArchivoExtraccion.ARCHIVO_ORIGEN);
        achivoExtraccionEntity.setNombreDestino(ConstantesArchivoExtraccion.ARCHIVO_DESTINO);
        achivoExtraccionEntity = ConstantesArchivoExtraccion.guardar(achivoExtraccionEntity);
        Map<String, Object> mapArchivoOrigen = (Map<String, Object>)iGestionFabricaBD.consultaExtraccionOrigen(conexionDto,parametrizacionEntity, achivoExtraccionRepository,achivoExtraccionEntity.getId_extraccion());
        if (mapArchivoOrigen.containsKey(ConstantesGenericas.CODIGO)) {
            Object objCodigo = ConstantesGenericas.CODIGO;
            String codigo = mapArchivoOrigen.get(objCodigo).toString();
            if(!codigo.equalsIgnoreCase("200")){
                if(achivoExtraccionEntity.getId_extraccion() != null){
                    ConstantesArchivoExtraccion.eliminarPorId(Long.valueOf(achivoExtraccionEntity.getId_extraccion()));
                }
                return mapArchivoOrigen;
            }
        }
        Map<String, Object> mapArchivoDestino = (Map<String, Object>) extraerGenerarArchivoDestinoPorIdConciliacion(parametrizacionEntity,achivoExtraccionEntity.getId_extraccion());
        if (mapArchivoDestino.containsKey(ConstantesGenericas.CODIGO)) {
            Object objCodigo = ConstantesGenericas.CODIGO;
            String codigo = mapArchivoDestino.get(objCodigo).toString();
            if(!codigo.equalsIgnoreCase("200")){
                if(achivoExtraccionEntity.getId_extraccion() != null){
                    ConstantesArchivoExtraccion.eliminarPorId(Long.valueOf(achivoExtraccionEntity.getId_extraccion()));
                }
                return mapArchivoDestino;
            }
        }
        return mapArchivoDestino;
    }

    @Override
    public Object extraerGenerarArchivoDestinoPorIdConciliacion(ParametrizacionEntity parametrizacionEntity,Integer id_extraccion) {

        if(parametrizacionEntity.getIdConexion1().intValue() !=  parametrizacionEntity.getIdConexion2().intValue()) {
            Map<String, Object> mapConexion = (Map<String, Object>) conexionServiceImpl.obtenerConexion(parametrizacionEntity.getIdConexion2());
            if (!mapConexion.containsKey(ConstantesConexion.DATOS_CONEXION)) {
                return mapConexion;
            }
            conexionDto = (ConexionDto) mapConexion.get(ConstantesConexion.DATOS_CONEXION);
            Map<String, Object> mapTipoConexion = (Map<String, Object>) tipoConexionServiceImpl.obtenerTipoConexion(conexionDto.getIdTipoConexion());
            if (!mapTipoConexion.containsKey(ConstantesTipoConexion.DATOS_TIPO_CONEXION)) {
                return mapTipoConexion;
            }
            tipoConexionEntity = (TipoConexionEntity) mapTipoConexion.get(ConstantesTipoConexion.DATOS_TIPO_CONEXION);
            IGestionFabricaBD iGestionFabricaBD = ValidarTipoConexion.ValidateConexion(tipoConexionEntity.getNombre());
            if (iGestionFabricaBD == null) {
                return ValidarTipoConexion.mensajeTipoConexionVacia();
            }
        }
        return iGestionFabricaBD.consultaExtraccionDestino(conexionDto,parametrizacionEntity, achivoExtraccionRepository,id_extraccion);

    }
}
