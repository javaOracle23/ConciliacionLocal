package com.conciliacion.parquet.controllers;

import com.conciliacion.parquet.dto.ConciliacionDto;
import com.conciliacion.parquet.services.interfaces.IConciliacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("apiConciliacion")
public class ConciliacionApiRestController {

    @Autowired
    private IConciliacionService iConciliacionService;

    @GetMapping(value ="/consultaConciliaciones")
    public ResponseEntity<Object> consultarConciliaciones() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iConciliacionService.consultarConciliacion());
    }

    @GetMapping(value = "/consultaConciliacionPorId/{ID}")
    public ResponseEntity<Object> consultarConsiliacionPorID(@PathVariable(value = "ID") Integer id) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iConciliacionService.obtenerConciliacion(id));
    }

    @PostMapping(value ="/guardarConciliacion")
    public ResponseEntity<Object> guardarConciliacion(@RequestBody ConciliacionDto conciliacionDto) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iConciliacionService.guardarConciliacion(conciliacionDto));
    }

    @PutMapping(value ="/actualizarConciliacion")
    public ResponseEntity<Object> actualizarConciliacion(@RequestBody ConciliacionDto conexionDto) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iConciliacionService.actualizarConciliacion(conexionDto));
    }

    @DeleteMapping(value ="/eliminarConciliacion/{ID}")
    public ResponseEntity<Object> eliminarConciliacion(@PathVariable(value = "ID") Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iConciliacionService.eliminarConciliacion(id));
    }
}
