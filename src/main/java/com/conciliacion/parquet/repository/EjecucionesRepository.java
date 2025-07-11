package com.conciliacion.parquet.repository;

import org.springframework.stereotype.Service;


@Service
public interface EjecucionesRepository {

    /*@Query(value = "SELECT * FROM DBO.ejecuciones"  , nativeQuery = true)
    public List<EjecucionesEntity> consultarEjecuciones();

    @Query(value = "SELECT * FROM DBO.ejecuciones ej WHERE ej.id_ejecuciones  = :idEjecucion", nativeQuery = true)
    public EjecucionesEntity consultarEjecutadosPorIdEjecucion(@Param("idEjecucion") Integer idEjecucion);*/

}
