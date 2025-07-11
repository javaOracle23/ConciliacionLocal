package com.conciliacion.parquet.controllers;


import com.conciliacion.parquet.dto.ConexionDto;
import com.conciliacion.parquet.entity.TipoConexionEntity;
import com.conciliacion.parquet.services.interfaces.*;
import com.conciliacion.parquet.utilities.ConstantesConexion;
import com.conciliacion.parquet.utilities.ConstantesTipoConexion;
import com.conciliacion.parquet.utilities.ValidarTipoConexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("apiGestionFabricaBD")
public class GestionApiRestFabricaBD {

    private IGestionFabricaBD iGestionBDIndependientes;
    @Autowired
    private ITipoConexionService iTipoConexionService;
    @Autowired
    private IConexionService iConexionService;
    @Autowired
    private IRepositorioAzure iRepositorioAzure;
    @Autowired
    private IParametrizacionService iParametrizacionService;

    @Autowired
    private  IGestionBDGeneral iGestionBDGeneral;

    @PostMapping(value ="/probarConexion")
    public ResponseEntity<Object> probarConexion(@RequestBody ConexionDto conexionDto) throws Exception{
        Map<String, Object> mapTipoConexion = (Map<String, Object>)iTipoConexionService.obtenerTipoConexion(conexionDto.getIdTipoConexion());
        if (!mapTipoConexion.containsKey(ConstantesTipoConexion.DATOS_TIPO_CONEXION)) {
            return ResponseEntity.status(HttpStatus.OK).body(mapTipoConexion);
        }
        TipoConexionEntity tipoConexionEntity =  (TipoConexionEntity)mapTipoConexion.get(ConstantesTipoConexion.DATOS_TIPO_CONEXION);
        iGestionBDIndependientes = ValidarTipoConexion.ValidateConexion(tipoConexionEntity.getNombre());
        return ResponseEntity.status(HttpStatus.OK).body(iGestionBDIndependientes != null ? iGestionBDIndependientes.probarConexion(conexionDto):ValidarTipoConexion.mensajeTipoConexionVacia());
    }

    @GetMapping(value ="/consultarTiposConexiones")
    public ResponseEntity<Object> consultarTiposConexiones() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iTipoConexionService.consultarTiposConexiones());
    }

    @GetMapping(value ="/consultarTipoConexionPorId/{ID}")
    public ResponseEntity<Object> consultarTipoConexionPorId(@PathVariable(value = "ID") Integer id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iTipoConexionService.obtenerTipoConexion(id));
    }

    @GetMapping(value ="/consultarTablas/{ID_Conexion}")
    public ResponseEntity<Object> consultarTablas(@PathVariable(value = "ID_Conexion") Integer idConexion) throws Exception {
        Map<String, Object> mapConexion = (Map<String, Object>)iConexionService.obtenerConexion(idConexion);
        if (!mapConexion.containsKey(ConstantesConexion.DATOS_CONEXION)) {
            return ResponseEntity.status(HttpStatus.OK).body(mapConexion);
        }
        ConexionDto conexionDto = (ConexionDto)mapConexion.get(ConstantesConexion.DATOS_CONEXION);
        Map<String, Object> mapTipoConexion = (Map<String, Object>)iTipoConexionService.obtenerTipoConexion(conexionDto.getIdTipoConexion());
        if (!mapTipoConexion.containsKey(ConstantesTipoConexion.DATOS_TIPO_CONEXION)) {
            return ResponseEntity.status(HttpStatus.OK).body(mapTipoConexion);
        }
        TipoConexionEntity tipoConexionEntity =  (TipoConexionEntity)mapTipoConexion.get(ConstantesTipoConexion.DATOS_TIPO_CONEXION);
        iGestionBDIndependientes = ValidarTipoConexion.ValidateConexion(tipoConexionEntity.getNombre());
        return ResponseEntity.status(HttpStatus.OK).body(iGestionBDIndependientes.consultaTablas(conexionDto,tipoConexionEntity.getConsultaTablas()));
    }

    @GetMapping(value ="/consultarColumnas/{ID_Conexion}/{nombreTablaBD}")
    public ResponseEntity<Object> consultarColumnas(@PathVariable(value = "ID_Conexion") Integer idConexion,@PathVariable(value = "nombreTablaBD") String nombreTablaBD) throws Exception {
        Map<String, Object> mapConexion = (Map<String, Object>)iConexionService.obtenerConexion(idConexion);
        if (!mapConexion.containsKey(ConstantesConexion.DATOS_CONEXION)) {
            return ResponseEntity.status(HttpStatus.OK).body(mapConexion);
        }
        ConexionDto conexionDto = (ConexionDto)mapConexion.get(ConstantesConexion.DATOS_CONEXION);
        Map<String, Object> mapTipoConexion = (Map<String, Object>)iTipoConexionService.obtenerTipoConexion(conexionDto.getIdTipoConexion());
        if (!mapTipoConexion.containsKey(ConstantesTipoConexion.DATOS_TIPO_CONEXION)) {
            return ResponseEntity.status(HttpStatus.OK).body(mapTipoConexion);
        }
        TipoConexionEntity tipoConexionEntity =  (TipoConexionEntity)mapTipoConexion.get(ConstantesTipoConexion.DATOS_TIPO_CONEXION);
        iGestionBDIndependientes = ValidarTipoConexion.ValidateConexion(tipoConexionEntity.getNombre());
        return ResponseEntity.status(HttpStatus.OK).body(iGestionBDIndependientes.consultaColumnas(conexionDto,tipoConexionEntity.getConsultaColumnas(),nombreTablaBD));
    }


    @GetMapping(value ="/generarArchivosOrigenDestisnoPorIdConciliacion/{id_conciliacion}")
    public ResponseEntity<Object> extraccionOrigen(@PathVariable(value = "id_conciliacion") Integer idConciliacion) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iGestionBDGeneral.extraerGenerarArchivoOrigenPorIdConciliacion(idConciliacion));
    }

//    @GetMapping(value ="/extraccionDestino/{id_conciliacion}")
//    public ResponseEntity<Object> extraccionDestino(@PathVariable(value = "id_conciliacion") Integer idConciliacion) throws Exception {
//        return ResponseEntity.status(HttpStatus.OK).body(iGestionBDGeneral.extraerGenerarArchivoDestinoPorIdConciliacion(idConciliacion));
//    }



    @GetMapping("/probarTiposConexiones")
    public String holaMundo(){

        //Object obj = iRepositorioAzure.guardarArchivo();
        //gestionSQLServerImpl.probarConexion(null);
     return "hola mundo";
    }

}
