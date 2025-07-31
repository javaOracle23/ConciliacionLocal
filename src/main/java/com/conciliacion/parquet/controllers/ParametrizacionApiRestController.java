package com.conciliacion.parquet.controllers;

import com.conciliacion.parquet.dto.ParametrizacionDto;
import com.conciliacion.parquet.services.interfaces.IParametrizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("apiParametrizacion")
public class ParametrizacionApiRestController {

    @Autowired
    private IParametrizacionService iParametrizacionService;

    @GetMapping(value = "/ping")
    public ResponseEntity ping (){
        return ResponseEntity.status(HttpStatus.OK).body("200");
    }

    @GetMapping(value ="/consultaParametrizaciones")
    public ResponseEntity<Object> consultarParametrizacion() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iParametrizacionService.consultarParametrizacion());
    }

    @GetMapping(value = "/consultaParametrizacion/{ID}")
    public ResponseEntity<Object> consultarParametrizacion(@PathVariable(value = "ID") Integer id) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iParametrizacionService.obtenerParametrizacion(id));
    }

    @GetMapping(value = "/consultaParametrizacionPorConciliacion/{ID}")
    public ResponseEntity<Object> consultaParametrizacionPorConciliacion(@PathVariable(value = "ID") Integer id) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iParametrizacionService.consultarParametrizacionPorIdConciliacion(id));
    }

    @GetMapping(value = "/consultaParametrizacionPorTipoParametro/{ID}")
    public ResponseEntity<Object> consultaParametrizacionPorTipoParametro(@PathVariable(value = "ID") Integer id) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iParametrizacionService.consultarParametrizacionPorIdTipoParametro(id));
    }

    @PostMapping(value ="/guardarParametrizacion")
    public ResponseEntity<Object> guardarParametrizacion(@RequestBody ParametrizacionDto parametrizacionDto) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iParametrizacionService.guardarParametrizacion(parametrizacionDto));
    }

    @PutMapping(value ="/actualizarParametrizacion")
    public ResponseEntity<Object> actualizarParametrizacion(@RequestBody ParametrizacionDto parametrizacionDto) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(iParametrizacionService.actualizarParametrizacion(parametrizacionDto));
    }

    @DeleteMapping(value ="/eliminarParametrizacion/{ID}")
    public ResponseEntity<Object> eliminarParametrizacion(@PathVariable(value = "ID") Integer id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(iParametrizacionService.eliminarParametrizacion(id));
    }
}
