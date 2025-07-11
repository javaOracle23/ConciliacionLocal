package com.conciliacion.parquet.services.interfaces;


import com.conciliacion.parquet.dto.ParametrizacionDto;
import org.springframework.stereotype.Service;

@Service
public interface IParametrizacionService {

    public Object obtenerParametrizacion(Integer idConciliacion);

    public Object guardarParametrizacion(ParametrizacionDto parametrizacionDto);

    public Object actualizarParametrizacion(ParametrizacionDto parametrizacionDto);

    public Object consultarParametrizacion();

    public Object eliminarParametrizacion(Integer id);

    public Object consultarParametrizacionPorIdConciliacion(Integer idConciliacion);

    public Object consultarParametrizacionPorIdTipoParametro(Integer idTipoParametro);
}
