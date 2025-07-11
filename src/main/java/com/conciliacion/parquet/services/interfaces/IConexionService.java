package com.conciliacion.parquet.services.interfaces;

import com.conciliacion.parquet.dto.ConexionDto;
import org.springframework.stereotype.Service;

@Service
public interface IConexionService {

    public Object obtenerConexion(Integer idConexion);

    public Object guardarConexion(ConexionDto conexionDto);

    public Object actualizarConexion(ConexionDto conexionDto);

    public Object consultarConexiones();

    public Object eliminarConexion(long id);

}
