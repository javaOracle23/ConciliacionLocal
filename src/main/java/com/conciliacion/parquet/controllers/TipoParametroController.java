package com.conciliacion.parquet.controllers;


import com.conciliacion.parquet.services.interfaces.ITipoParametroServicie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("apiTipoParametro")
public class TipoParametroController {

    @Autowired
    private ITipoParametroServicie iTipoParametroServicie;

    @GetMapping(value ="/consultarTipoParametros")
    public ResponseEntity<Object> consultarTipoParametros() throws Exception {
        Object obj = iTipoParametroServicie.consultarTiposParametros();
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }

    @GetMapping(value ="/consultarTipoParametrosPorId/{ID}")
    public ResponseEntity<Object> consultarTipoParametrobyId(@PathVariable (value = "ID") Long id) throws Exception {
        Object obj = iTipoParametroServicie.consultarTipoParametroByid(id);
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }
}
