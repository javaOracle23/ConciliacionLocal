package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.entity.ParametrosEjecutadosEntity;
import com.conciliacion.parquet.entity.TipoConexionEntity;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ConstantesTipoConexion {



    public static final String MENSAJE_TIPO_CONEXION = "El tipo de conexión no existe";

    public static final String LISTA_TIPO_CONEXION = "listatipoconexion";

    public static final String MENSAJE_LISTA_TIPOCONEXIONES = "No hay tipos de conexiones registrados";

    public static final String DATOS_TIPO_CONEXION = "datostipoconexion";


    public static List<TipoConexionEntity> consultarTiposConexion(){

        String nombreArchivo = "TBTipo_conexion.parquet";

        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;
        List<TipoConexionEntity> listTiposConexion = null;
        TipoConexionEntity tipoConexionEntity = null;

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
                listTiposConexion = new ArrayList<>();
                while ((rec = parquetReader.read()) != null) {

                    tipoConexionEntity = new TipoConexionEntity();
                    String ID_Tipo_Conexion =  rec.get(0).toString();
                    tipoConexionEntity.setNombre( rec.get(1).toString());
                    tipoConexionEntity.setConsultaTablas(rec.get(2).toString());
                    tipoConexionEntity.setConsultaColumnas(rec.get(3).toString());

                    listTiposConexion.add(tipoConexionEntity);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return listTiposConexion;
    }


    public static  TipoConexionEntity consultarTipoConexionPorId(Long id){

        String nombreArchivo = "TBTipo_conexion.parquet";

        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;
        TipoConexionEntity tipoConexionEntity = null;

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
                        tipoConexionEntity = new TipoConexionEntity();
                        String ID_Tipo_Conexion =  rec.get(0).toString();
                        tipoConexionEntity.setNombre( rec.get(1).toString());
                        tipoConexionEntity.setConsultaTablas(rec.get(2).toString());
                        tipoConexionEntity.setConsultaColumnas(rec.get(3).toString());
                        break;
                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tipoConexionEntity;
    }

}
