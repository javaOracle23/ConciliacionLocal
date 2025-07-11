package com.conciliacion.parquet.controllers;

import com.conciliacion.parquet.dto.ConexionDto;
import com.conciliacion.parquet.services.interfaces.IConexionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("apiConexion")
public class ConexionApiRestController {

    @Autowired
    private IConexionService iConexionService;


    @GetMapping(value ="/consultarConexiones")
    public ResponseEntity<Object> consultarConexiones() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iConexionService.consultarConexiones());
    }

    @GetMapping(value ="/consultarConexionPorId/{ID}")
    public ResponseEntity<Object> consultarConexionPorId(@PathVariable(value = "ID") Integer id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iConexionService.obtenerConexion(id));
    }


    @PostMapping(value ="/guardarConexion")
    public ResponseEntity<Object> guardarConexion(@RequestBody ConexionDto conexionDto) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iConexionService.guardarConexion(conexionDto));
    }

    @PutMapping(value ="/actualizarConexion")
    public ResponseEntity<Object> actualizarConexion(@RequestBody ConexionDto conexionDto) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iConexionService.actualizarConexion(conexionDto));
    }

    @DeleteMapping(value ="/eliminarConexion/{ID}")
    public ResponseEntity<Object> existeTercero(@PathVariable(value = "ID") Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iConexionService.eliminarConexion(id));
    }




}
