package com.conciliacion.parquet.repository;


import org.springframework.stereotype.Service;

@Service
public interface ParametrizacionRepository {

    /*@Query(value = "SELECT * FROM DBO.Parametrizacion con WHERE con.id_parametro  = :idParametro", nativeQuery = true)
    public ParametrizacionEntity obtenerParametrizacion(@Param("idParametro") Integer idParametro);

    @Query(value = "SELECT * FROM DBO.Parametrizacion"  , nativeQuery = true)
    public List<ParametrizacionEntity> consultarParametrizaciones();

    @Query(value = "SELECT * FROM DBO.Parametrizacion con WHERE con.id_conciliacion  = :idConciliacion", nativeQuery = true)
    public ParametrizacionEntity consultarParametrizacionPorIdConciliacion(@Param("idConciliacion") Integer idConciliacion);

    @Query(value = "SELECT * FROM DBO.Parametrizacion con WHERE con.id_tipo_parametro  = :idTipoParametro", nativeQuery = true)
    public ParametrizacionEntity consultarParametrizacionPorIdTipoParametro(@Param("idTipoParametro") Integer idTipoParametro);*/

}
