package com.conciliacion.parquet.services.implementation;


import com.conciliacion.parquet.dto.ConciliacionDto;
import com.conciliacion.parquet.dto.TipoParametroDto;
import com.conciliacion.parquet.repository.TipoParametroRepository;
import com.conciliacion.parquet.services.interfaces.ITipoParametroServicie;
import com.conciliacion.parquet.utilities.ConstantesConciliacion;
import com.conciliacion.parquet.utilities.ConstantesGenericas;
import com.conciliacion.parquet.utilities.ConstantesTipoParametro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TipoParametroServiceImpl implements ITipoParametroServicie {

    private static Logger LOGGER = LoggerFactory.getLogger(TipoParametroServiceImpl.class);

    @Autowired(required=false)
    private TipoParametroRepository tipoParametroRepository;

    @Override
    public Object consultarTiposParametros() {

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        List<TipoParametroDto> listTipoParametro = null;

        try {
            //listTipoParametro = tipoParametroRepository.consultarTipoParametro();

            listTipoParametro = ConstantesTipoParametro.listarTipoParametro();
            if (listTipoParametro != null && listTipoParametro.size() != 0) {
                mapResponse.put(ConstantesTipoParametro.LISTA_TIPO_PARAMETRO, listTipoParametro);
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK);
            } else {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesTipoParametro.MENSAJE_LISTA_TIPO_PARAMETRO);
                mapResponse.put(ConstantesTipoParametro.LISTA_TIPO_PARAMETRO, listTipoParametro);
            }

        } catch (Exception e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_BASE_DATOS);
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD);
            LOGGER.error(ConstantesGenericas.MENSAJE, e.getMessage());
            return mapResponse;

        }
        return mapResponse;
    }

    @Override
    public Object consultarTipoParametroByid(Long id) {

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        TipoParametroDto tipoParametroDto = null;
        try{
            //conciliacionEntity = conciliacionRepository.obtenerConciliacion(idConciliacion);
            tipoParametroDto = ConstantesTipoParametro.consultarTipoParametroPorId(Long.valueOf(id));
            if(tipoParametroDto != null){
                mapResponse.put(ConstantesTipoParametro.TIPO_PARAMETRO, tipoParametroDto );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }else{
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesTipoParametro.MENSAJE_TIPO_PARAMETRO );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            }
        } catch (Exception e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_BASE_DATOS );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            return mapResponse;
        }
        return mapResponse;
    }
}
