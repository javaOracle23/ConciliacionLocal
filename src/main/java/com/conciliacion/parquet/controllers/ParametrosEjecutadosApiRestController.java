package com.conciliacion.parquet.controllers;

import com.conciliacion.parquet.services.interfaces.IParametrosEjecutadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("apiParametrosEjecutados")
public class ParametrosEjecutadosApiRestController {

    @Autowired
    private IParametrosEjecutadosService iParametrosEjecutadosService;

    @GetMapping(value ="/consultarParametrosEjecutados")
    public ResponseEntity<Object> consultarParametrosEjecutados() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iParametrosEjecutadosService.consultarParametrosEjecutados());
    }

    @GetMapping(value ="/consultarParametrosEjecutadosPorIdEjecucion/{ID}")
    public ResponseEntity<Object> consultarParametrosEjecutadosPorIdEjecucion(@PathVariable(value = "ID") Integer id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iParametrosEjecutadosService.consultarParametrosEjecutadosPorIdEjecucion(id));
    }

}
