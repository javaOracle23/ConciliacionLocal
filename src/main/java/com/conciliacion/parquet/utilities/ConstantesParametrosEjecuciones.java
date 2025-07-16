package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.dto.ConexionDto;
import com.conciliacion.parquet.entity.ParametrosEjecutadosEntity;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class ConstantesParametrosEjecuciones {

    public static final String MENSAJE_LISTA_PARAMETROS_EJECUCION = "No existen parametros ejecuciones registrados";

    public static final String LISTA_PARAMETROS_EJECUCIONES = "listaparametrosejecuciones";

    public static final String PARAMETROS_EJECUCIONES = "parametroejecucion";

    public static final String MENSAJE_PARAMETROS_EJECUCION = "No existen parametros ejecuciones para la ejecucion consultada";

    public static List<ParametrosEjecutadosEntity> consultarParametrosEjecutados(){

        String nombreArchivo = "TBParametrosEjecutados.parquet";

        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;
        List<ParametrosEjecutadosEntity> listParametrosEjecutados = null;
        ParametrosEjecutadosEntity parametrosEjecutadosEntity = null;

        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ParametrosEjecutadosEntity.class;
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
                listParametrosEjecutados = new ArrayList<>();
                while ((rec = parquetReader.read()) != null) {

                    parametrosEjecutadosEntity = new ParametrosEjecutadosEntity();
                    String id_parametros_ejecutados =  rec.get(0).toString();
                    parametrosEjecutadosEntity.setId_parametros_ejecutados( Integer.parseInt(id_parametros_ejecutados) );
                    String idEjecuciones = rec.get(1).toString();
                    parametrosEjecutadosEntity.setIdEjecuciones(Integer.parseInt(idEjecuciones));
                    parametrosEjecutadosEntity.setJsonParametrosOrigen( rec.get(2).toString());
                    parametrosEjecutadosEntity.setJsonParametrosDestino(rec.get(3).toString());
                    String jobId =  rec.get(4) != null ? rec.get(4).toString() : "0";
                    parametrosEjecutadosEntity.setJobId(Integer.parseInt(jobId));
                    listParametrosEjecutados.add(parametrosEjecutadosEntity);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return listParametrosEjecutados;
    }


    public static  ParametrosEjecutadosEntity consultarParametrosEjecutadosPorId(Long id){

        String nombreArchivo = "TBParametrosEjecutados.parquet";

        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;
        ParametrosEjecutadosEntity parametrosEjecutadosEntity = null;

        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ParametrosEjecutadosEntity.class;
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


                    String id_parametros_ejecutados =  rec.get(0).toString();
                    String idBusqueda = id.toString();

                    if(id_parametros_ejecutados.equalsIgnoreCase(idBusqueda)) {
                        parametrosEjecutadosEntity = new ParametrosEjecutadosEntity();
                        parametrosEjecutadosEntity.setId_parametros_ejecutados( Integer.parseInt(id_parametros_ejecutados) );
                        String idEjecuciones = rec.get(1).toString();
                        parametrosEjecutadosEntity.setIdEjecuciones(Integer.parseInt(idEjecuciones));
                        parametrosEjecutadosEntity.setJsonParametrosOrigen( rec.get(2).toString());
                        parametrosEjecutadosEntity.setJsonParametrosDestino(rec.get(3).toString());
                        String jobId =  rec.get(4) != null ? rec.get(4).toString() : "0";
                        parametrosEjecutadosEntity.setJobId(Integer.parseInt(jobId));
                        break;
                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return parametrosEjecutadosEntity;
    }

}
