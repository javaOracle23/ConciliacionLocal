package com.conciliacion.parquet.services.interfaces;

import com.conciliacion.parquet.entity.ParametrizacionEntity;
import org.springframework.stereotype.Service;

@Service
public interface IGestionBDGeneral {

    public IGestionFabricaBD agregarBaseDatosPorIdConciliacion(Integer idConciliacion);

    public Object extraerGenerarArchivoOrigenPorIdConciliacion(Integer idConciliacion);

    public Object extraerGenerarArchivoDestinoPorIdConciliacion(ParametrizacionEntity parametrizacionEntity, Integer id_extraccion);


}
