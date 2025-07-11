package com.conciliacion.parquet.repository;

import org.springframework.stereotype.Service;


@Service
public interface TipoConexionRepository{

    /*@Query(value = "SELECT * FROM DBO.Tipo_conexion tc WHERE tc.ID_Tipo_Conexion  = :idTipoConexion", nativeQuery = true)
    public TipoConexionEntity obtenerTipoConexion(@Param("idTipoConexion") int idTipoConexion);

    @Query(value = "SELECT * FROM DBO.Tipo_conexion"  , nativeQuery = true)
    public List<TipoConexionEntity> consultarTiposConexiones();*/
}
