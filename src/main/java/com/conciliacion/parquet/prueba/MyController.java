package com.conciliacion.parquet.prueba;

import com.conciliacion.parquet.converter.implementation.ConexionConvetImpl;
import com.conciliacion.parquet.dto.ConexionDto;
import com.conciliacion.parquet.entity.ConexionEntity;
import com.conciliacion.parquet.services.interfaces.IConexionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController{

    @Autowired
    private IConexionService iConexionService;

    //@GetMapping("/")
    //public String holaMundo(){
       // return "hola mundo";
    //}

    @GetMapping("/")
    public ResponseEntity<Object> ConsultarConexion(){
        Object objMaestro = null;

        ConexionConvetImpl conexionConvetImpl = new ConexionConvetImpl();
        ConexionDto conexionDto = new ConexionDto();
        conexionDto.setNombreBaseDeDatos("bdprueba");
        ConexionEntity conexionEntity = conexionConvetImpl.fromDto(conexionDto);
        objMaestro = conexionEntity;
        //objMaestro = iConexionService.obtenerConexion("CONEXIONAZURE");
        return ResponseEntity.status(HttpStatus.OK).body(objMaestro);
    }



    //    @GetMapping(value ="/consultarConexion/{NOMBRE}")
//    public ResponseEntity<Object> ConsultarConexion(@PathVariable(value = "NIT") Double nit, @PathVariable(value = "TIPODOCUMENTO") String tipoDocumento) throws Exception {
//        Map<String, Object> response = new HashMap<>();
//        Object objMaestro = null;
//        try {
//            objMaestro = iConexionService.consultarConexion(nombre);
//        } catch (DataAccessException e) {
//            response.put("Codigo","003");
//            response.put("Mensaje", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(objMaestro);
//    }


}
