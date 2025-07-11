package com.conciliacion.parquet.repository;


import org.springframework.stereotype.Service;


@Service
public interface ConexionRepository {

    /*@Query(value = "SELECT * FROM DBO.Conexion con WHERE con.id_conexion  = :idConexion", nativeQuery = true)
    public ConexionEntity obtenerConexion(@Param("idConexion") Integer idConexion);

    @Query(value = "SELECT * FROM DBO.Conexion con WHERE con.nombre  = :nombre and con.host = :host and con.puerto = :port"  , nativeQuery = true)
    public ConexionEntity consultarConexion(@Param("nombre") String nombre,@Param("host") String host,@Param("port") String port);

    @Query(value = "SELECT * FROM DBO.Conexion"  , nativeQuery = true)
    public List<ConexionEntity> consultarConexiones();*/


}
