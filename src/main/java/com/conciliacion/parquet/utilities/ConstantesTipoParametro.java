package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.dto.ConexionDto;
import com.conciliacion.parquet.dto.TipoParametroDto;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Clase donde se definen las constantes que se van a utilizar en la lógica y peticiones del
 * aplicativo web.
 *
 * @since 1.0.0
 */
public final class ConstantesTipoParametro {

    /** Constantes de la aplicación */
    private ConstantesTipoParametro() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final String NOMBRE_SCHEMA_DATABASE_CONCILIACION =
            "dbo";

    public static final String VALOR_N =
            "N";


    public static final String LISTA_TIPO_PARAMETRO = "ListaTipoParametro";

    public static final String MENSAJE_LISTA_TIPO_PARAMETRO = "No hay parametros registrados";

    public static final  String TIPO_PARAMETRO = "tipo parametro";

    public static final String MENSAJE_TIPO_PARAMETRO = "No existe el tipo parametro a consultar";

    public static List<TipoParametroDto> listarTipoParametro(){

        String nombreArchivo = "TBTipoparametro.parquet";

        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;
        List<TipoParametroDto> listTipoParametro = null;
        TipoParametroDto tipoParametroDto = null;


        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = TipoParametroDto.class;
        for(java.lang.reflect.Field campo:miClase.getDeclaredFields()){
            listField.add(new Schema.Field(campo.getName(), Schema.create(Schema.Type.STRING), null, null) );
        }
        schema.setFields(listField);

        outputFiles = new File(nombreArchivo);
        if (outputFiles.exists()) {
            path= new  org.apache.hadoop.fs.Path(outputFiles.getPath() );
            try (ParquetReader<GenericRecord> parquetReader =
                         AvroParquetReader.<GenericRecord>builder(path)
                                 .withDataModel(GenericData.get())
                                 .build()) {
                GenericRecord rec;
                listTipoParametro = new ArrayList<>();
                while ((rec = parquetReader.read()) != null) {

                    tipoParametroDto = new TipoParametroDto();
                    String idTipoParametro =  rec.get(0).toString();
                    tipoParametroDto.setIdTipoParamtero( Integer.parseInt(idTipoParametro) );
                    String tipo = rec.get(1).toString();
                    tipoParametroDto.setTipo(tipo);

                    listTipoParametro.add(tipoParametroDto);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return listTipoParametro;
    }

    public static TipoParametroDto consultarTipoParametroPorId(Long id){

        String nombreArchivo = "TBTipoparametro.parquet";

        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;
        TipoParametroDto tipoParametroDto = null;

        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = TipoParametroDto.class;
        for(java.lang.reflect.Field campo:miClase.getDeclaredFields()){
            listField.add(new Schema.Field(campo.getName(), Schema.create(Schema.Type.STRING), null, null) );
        }
        schema.setFields(listField);

        outputFiles = new File(nombreArchivo);
        if (outputFiles.exists()) {
            path= new  org.apache.hadoop.fs.Path(outputFiles.getPath() );
            try (ParquetReader<GenericRecord> parquetReader =
                         AvroParquetReader.<GenericRecord>builder(path)
                                 .withDataModel(GenericData.get())
                                 .build()) {
                GenericRecord rec;
                while ((rec = parquetReader.read()) != null) {


                    String idTipoParametro =  rec.get(0).toString();
                    String idBusqueda = id.toString();

                    if(idTipoParametro.equalsIgnoreCase(idBusqueda)) {
                        tipoParametroDto = new TipoParametroDto();
                        tipoParametroDto.setIdTipoParamtero(Integer.parseInt(idTipoParametro));
                        tipoParametroDto.setTipo(rec.get(1).toString());
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tipoParametroDto;
    }


}
