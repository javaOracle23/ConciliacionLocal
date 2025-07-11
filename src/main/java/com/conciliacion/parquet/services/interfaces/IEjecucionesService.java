package com.conciliacion.parquet.services.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface IEjecucionesService {

    public Object consultarEjecuciones();

    public Object consultarEjecutadosPorIdEjecucion(Integer idEjecucion);
}
