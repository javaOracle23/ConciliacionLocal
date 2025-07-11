package com.conciliacion.parquet.services.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface IParametrosEjecutadosService {

    public Object consultarParametrosEjecutadosPorIdEjecucion(Integer idEjecucion);

    public Object consultarParametrosEjecutados();

}
