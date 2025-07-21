package com.conciliacion.parquet.services.interfaces;

import com.conciliacion.parquet.dto.ConexionDto;
import com.conciliacion.parquet.entity.AchivoExtraccionEntity;
import com.conciliacion.parquet.entity.ParametrizacionEntity;
import com.conciliacion.parquet.repository.AchivoExtraccionRepository;
import org.springframework.stereotype.Service;

@Service
public interface IGestionFabricaBD {

    public Object probarConexion(ConexionDto conexionDto);

    public Object consultaTablas(ConexionDto conexionDto, String consultaTablas);

    public Object consultaColumnas(ConexionDto conexionDto, String consultaColumnas, String nombreTablaBD);

    public Object consultaExtraccionOrigen(ConexionDto conexionDto, ParametrizacionEntity parametrizacionEntity,  AchivoExtraccionEntity achivoExtraccionEntity);

    public Object consultaExtraccionDestino(ConexionDto conexionDto, ParametrizacionEntity parametrizacionEntity, Integer id_extraccion);


}
