package com.conciliacion.parquet.services.interfaces;


import com.conciliacion.parquet.dto.ConciliacionDto;
import org.springframework.stereotype.Service;

@Service
public interface IConciliacionService {

    public Object obtenerConciliacion(Integer idConciliacion);

    public Object guardarConciliacion(ConciliacionDto conciliacionDto);

    public Object actualizarConciliacion(ConciliacionDto conciliacionDto);

    public Object consultarConciliacion();

    public Object eliminarConciliacion(Integer id);
}
