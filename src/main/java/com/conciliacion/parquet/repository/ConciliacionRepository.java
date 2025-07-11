package com.conciliacion.parquet.repository;


import com.conciliacion.parquet.entity.ConciliacionEntity;
import org.springframework.stereotype.Service;

@Service
public interface ConciliacionRepository  {

    /*@Query(value = "SELECT * FROM DBO.Conciliacion con WHERE con.id_conciliacion  = :idConciliacion", nativeQuery = true)
    public ConciliacionEntity obtenerConciliacion(@Param("idConciliacion") Integer idConciliacion);

    @Query(value = "SELECT * FROM DBO.Conciliacion"  , nativeQuery = true)
    public List<ConciliacionEntity> consultarConcilaciones();*/

    public ConciliacionEntity obtenerConciliacion(Integer idConciliacion);

}
