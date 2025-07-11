package com.conciliacion.parquet.controllers;

import com.conciliacion.parquet.services.interfaces.IEjecucionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("apiEjecuciones")
public class EjecucionesApiRestController {

    @Autowired
    private IEjecucionesService iEjecucionesService;

    @GetMapping(value ="/consultarEjecuciones")
    public ResponseEntity<Object> consultarEjecuciones() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iEjecucionesService.consultarEjecuciones());
    }

    @GetMapping(value ="/consultarEjecutadosPorIdEjecucion/{ID}")
    public ResponseEntity<Object> consultarEjecutadosPorIdEjecucion(@PathVariable(value = "ID") Integer id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iEjecucionesService.consultarEjecutadosPorIdEjecucion(id));
    }

}
