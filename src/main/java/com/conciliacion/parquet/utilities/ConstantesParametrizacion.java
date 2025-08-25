package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.dto.ParametrizacionDto;
import com.conciliacion.parquet.entity.ParametrizacionEntity;
import com.conciliacion.parquet.entity.ParametrosEjecutadosEntity;
import com.conciliacion.parquet.entity.TipoConexionEntity;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Clase donde se definen las constantes que se van a utilizar en la lógica y peticiones del
 * aplicativo web.
 *
 * @since 1.0.0
 */
public final class ConstantesParametrizacion {

    /** Constantes de la aplicación */
    private ConstantesParametrizacion() {
        throw new IllegalStateException("Clase de constantes");
    }


    public static final String MENSAJE_INSERT_OK = "Registro guardado con exito";

    public static final String MENSAJE_UPDATE_OK = "Registro actualizado con exito";

    public static final String MENSAJE_INSERT_ERROR_PARAMETRIZACION = "La parametrizacion no se registro";

    public static final String MENSAJE_UPDATE_ERROR_PARAMETRIZACION = "La parametrizacion no se actualizo";

    public static final String LISTA_PARAMETRIZACION = "ListaParametrizacion";

    public static final String MENSAJE_LISTA_PARAMETRIZACION = "No hay parametrizacion registradas";

    public static final String MENSAJE_DELETE_PARAMETRIZACION = "La parametrizacion se elimino";

    public static final String MENSAJE_PARAMETRIZACION = "No existe registro para la parametrizacion";

    public static final String MENSAJE_PARAMETRIZACION_CONCILIACION = "No existe parametrizacion para la conciliacion";

    public static final String PARAMETRIZACION = "parametrizacion";

    public static ParametrizacionEntity consultarParametrizacionPorIdConciliacion(Long id){

        String nombreArchivo = "TBParametrizacion.parquet";

        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;
        ParametrizacionEntity parametrizacionEntity = null;

        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ParametrizacionEntity.class;
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


                    String id_parametros_ejecutados =  rec.get(2).toString();
                    String idBusqueda = id.toString();

                    if(id_parametros_ejecutados.equalsIgnoreCase(idBusqueda)) {
                        parametrizacionEntity = new ParametrizacionEntity();
                        String idParameto =  rec.get(0).toString();
                        parametrizacionEntity.setIdParameto(Integer.parseInt(idParameto));
                        String idTipoParameto =  rec.get(1).toString();
                        parametrizacionEntity.setIdTipoParametro(Integer.parseInt(idTipoParameto));
                        String idConciliacion =  rec.get(2).toString();
                        parametrizacionEntity.setIdConciliacion  ( Integer.parseInt(idConciliacion));
                        String idConexion1 =  rec.get(3).toString();
                        parametrizacionEntity.setIdConexion1(Integer.parseInt(idConexion1));
                        String idConexion2 =  rec.get(4).toString();
                        parametrizacionEntity.setIdConexion2(Integer.parseInt(idConexion2));
                        parametrizacionEntity.setDataSolicitudOrigen(rec.get(5).toString());
                        parametrizacionEntity.setDataSolicitudDestino(rec.get(6).toString());
                        parametrizacionEntity.setKeys(rec.get(7).toString());
                        break;
                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return parametrizacionEntity;
    }

    public static boolean guardar(ParametrizacionDto parametrizacionDto) {

        boolean guardadoExitoso = false;
        String nombreArchivo = "TBParametrizacion.parquet";
        List<GenericRecord> records = null;
        org.apache.hadoop.fs.Path path = null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        Integer id = 0;
        File outputFiles = null;

        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ParametrizacionDto.class;
        for (java.lang.reflect.Field campo : miClase.getDeclaredFields()) {
            listField.add(new Schema.Field(campo.getName(), Schema.create(Schema.Type.STRING), null, null));
        }
        schema.setFields(listField);

        outputFiles = new File(nombreArchivo);
        if (outputFiles.exists()) {
            path = new org.apache.hadoop.fs.Path(outputFiles.getPath());
            records = new ArrayList<>();
            try (ParquetReader<GenericRecord> parquetReader =
                         AvroParquetReader.<GenericRecord>builder(path)
                                 .withDataModel(GenericData.get())
                                 .build()) {
                GenericRecord rec;
                while ((rec = parquetReader.read()) != null) {
                    records.add(rec);
                    id = Integer.parseInt(rec.get(0).toString());

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        parametrizacionDto.setIdParameto(parametrizacionDto.getIdParameto());
        parametrizacionDto.setIdTipoParametro(parametrizacionDto.getIdTipoParametro());
        parametrizacionDto.setIdConciliacion(parametrizacionDto.getIdConciliacion());
        parametrizacionDto.setIdConexion1(parametrizacionDto.getIdConexion1());
        parametrizacionDto.setIdConexion2(parametrizacionDto.getIdConexion2());
        parametrizacionDto.setDataSolicitudOrigen(parametrizacionDto.getDataSolicitudOrigen());
        parametrizacionDto.setDataSolicitudDestino(parametrizacionDto.getDataSolicitudDestino());
        parametrizacionDto.setKeys(parametrizacionDto.getKeys());
        parametrizacionDto.setFechaCreacion(new Date());
        parametrizacionDto.setFechaModificacion(new Date());

        Object objParametrizacion = parametrizacionDto;
        Class<?> clase = objParametrizacion.getClass();
        java.lang.reflect.Field[] campos = clase.getDeclaredFields();

        if (outputFiles.exists()) {
            outputFiles.delete();
        }

        LocalOutputFile outputFile = new LocalOutputFile(Path.of(nombreArchivo));
        try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(outputFile)
                .withCompressionCodec(CompressionCodecName.GZIP)
                .withSchema(schema)
                .withPageSize(1 << 20)
                .build()) {

            if (records != null) {
                for (GenericRecord old : records) {
                    GenericData.Record normalized = new GenericData.Record(schema);
                    for (Schema.Field f : schema.getFields()) {
                        Object v = old.get(f.name());
                        normalized.put(f.name(), v != null ? v.toString() : "");
                    }
                    writer.write(normalized);
                }
            }
            GenericData.Record record = new GenericData.Record(schema);
            Object fieldValue = null;
            for (java.lang.reflect.Field campo : campos) {
                try {
                    campo.setAccessible(true); // Permite acceder a campos privados
                    Object valor = campo.get(objParametrizacion);
                    record.put(campo.getName(), valor != null ? valor.toString() : "");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            writer.write(record);
            guardadoExitoso = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return guardadoExitoso;
    }

    public static boolean actualizarPorId(ParametrizacionDto parametrizacionDto){

        boolean actualizarExitoso = false;
        String nombreArchivo = "TBParametrizacion.parquet";
        List<GenericRecord> records = null;
        org.apache.hadoop.fs.Path path = null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;
        parametrizacionDto.setFechaCreacion(new Date());
        parametrizacionDto.setFechaModificacion(new Date());
        Object objParametrizacion = parametrizacionDto;
        Class<?> clase = objParametrizacion.getClass();
        java.lang.reflect.Field[] campos = clase.getDeclaredFields();

        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ParametrizacionDto.class;
        for(java.lang.reflect.Field campo:miClase.getDeclaredFields()){
            listField.add(new Schema.Field(campo.getName(), Schema.create(Schema.Type.STRING), null, null) );
        }
        schema.setFields(listField);

        outputFiles = new File(nombreArchivo);
        if (outputFiles.exists()) {
            path= new  org.apache.hadoop.fs.Path(outputFiles.getPath() );
            records = new ArrayList<>();
            try (ParquetReader<GenericRecord> parquetReader =
                         AvroParquetReader.<GenericRecord>builder(path)
                                 .withDataModel(GenericData.get())
                                 .build()) {
                GenericRecord rec;
                while ((rec = parquetReader.read()) != null) {
                    GenericData.Record normalized = new GenericData.Record(schema);
                    for (Schema.Field f : schema.getFields()) {
                        Object v = rec.get(f.name());
                        normalized.put(f.name(), v != null ? v.toString() : "");
                    }
                    String idRegistro = normalized.get(0).toString();
                    String idParametrizacion = parametrizacionDto.getIdParameto().toString();
                    if(idRegistro.equalsIgnoreCase(idParametrizacion)){
                        for (java.lang.reflect.Field campo : campos) {
                            try {
                                campo.setAccessible(true);
                                Object valor = campo.get(objParametrizacion);
                                normalized.put(campo.getName(), valor != null ? valor.toString(): "");
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    records.add(normalized);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (outputFiles.exists()) {
            outputFiles.delete();
        }

        LocalOutputFile outputFile = new LocalOutputFile(Path.of(nombreArchivo));
        try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(outputFile)
                .withCompressionCodec(CompressionCodecName.GZIP)
                .withSchema(schema)
                .withPageSize(1 << 20)
                .build())
        {

            if(records != null){
                for(GenericRecord record:records){
                    writer.write(record);
                }
            }
            actualizarExitoso = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return actualizarExitoso;
    }

    public static boolean eliminarPorId(Long id){

        boolean eliminarExitoso = false;
        String nombreArchivo = "TBParametrizacion.parquet";
        List<GenericRecord> records = null;
        org.apache.hadoop.fs.Path path = null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;

        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ParametrizacionDto.class;
        for(java.lang.reflect.Field campo:miClase.getDeclaredFields()){
            listField.add(new Schema.Field(campo.getName(), Schema.create(Schema.Type.STRING), null, null) );
        }
        schema.setFields(listField);

        outputFiles = new File(nombreArchivo);
        if (outputFiles.exists()) {
            path= new  org.apache.hadoop.fs.Path(outputFiles.getPath() );
            records = new ArrayList<>();
            try (ParquetReader<GenericRecord> parquetReader =
                         AvroParquetReader.<GenericRecord>builder(path)
                                 .withDataModel(GenericData.get())
                                 .build()) {
                GenericRecord rec;
                while ((rec = parquetReader.read()) != null) {
                    GenericData.Record normalized = new GenericData.Record(schema);
                    for (Schema.Field f : schema.getFields()) {
                        Object v = rec.get(f.name());
                        normalized.put(f.name(), v != null ? v.toString() : "");
                    }
                    String idRegistro = normalized.get(0).toString();
                    String idBusqueda = id.toString();
                    if(!idRegistro.equalsIgnoreCase(idBusqueda)){
                        records.add(normalized);
                    }
                }
                eliminarExitoso = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (outputFiles.exists()) {
            outputFiles.delete();
        }

        LocalOutputFile outputFile = new LocalOutputFile(Path.of(nombreArchivo));
        try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(outputFile)
                .withCompressionCodec(CompressionCodecName.GZIP)
                .withSchema(schema)
                .withPageSize(1 << 20)
                .build())
        {

            if(records != null){
                for(GenericRecord record:records){
                    writer.write(record);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return eliminarExitoso;
    }

    public static List<ParametrizacionDto> consultarParametrizaciones(){

        String nombreArchivo = "TBParametrizacion.parquet";

        org.apache.hadoop.fs.Path path = null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;
        List<ParametrizacionDto> listParametrizacion = null;
        ParametrizacionDto parametrizacionDto = null;

        Date dateCreacion = null;
        Date dateModificacion = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("EST"));

        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ParametrizacionDto.class;
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
                listParametrizacion = new ArrayList<>();
                while ((rec = parquetReader.read()) != null) {

                    parametrizacionDto = new ParametrizacionDto();
                    String idParameto =  rec.get(0).toString();
                    parametrizacionDto.setIdParameto(Integer.parseInt(idParameto));
                    String idTipoParametro =  rec.get(1).toString();
                    parametrizacionDto.setIdTipoParametro(Integer.parseInt(idTipoParametro));
                    String idConciliacion =  rec.get(2).toString();
                    parametrizacionDto.setIdConciliacion(Integer.parseInt(idConciliacion));
                    String idConexion1 =  rec.get(3).toString();
                    parametrizacionDto.setIdConexion1(Integer.parseInt(idConexion1));
                    String idConexion2 =  rec.get(4).toString();
                    parametrizacionDto.setIdConexion2(Integer.parseInt(idConexion2));
                    parametrizacionDto.setDataSolicitudOrigen(rec.get(5).toString());
                    parametrizacionDto.setDataSolicitudDestino(rec.get(6).toString());
                    parametrizacionDto.setKeys(rec.get(7).toString());
                    String fechaCreacion = rec.get(8).toString();
                    String fechaModificacion = rec.get(9).toString();

                    try {
                        dateCreacion = formatter.parse(fechaCreacion);
                        dateModificacion = formatter.parse(fechaModificacion);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    parametrizacionDto.setFechaCreacion(dateCreacion);
                    parametrizacionDto.setFechaModificacion(dateModificacion);

                    listParametrizacion.add(parametrizacionDto);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return listParametrizacion;
    }

    public static ParametrizacionDto consultarParametrizacionPorId(Long id){

        String nombreArchivo = "TBParametrizacion.parquet";

        org.apache.hadoop.fs.Path path = null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;
        ParametrizacionDto parametrizacionDto = null;

        Date dateCreacion = null;
        Date dateModificacion = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("EST"));

        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ParametrizacionDto.class;
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

                    String idParameto =  rec.get(0).toString();
                    String idBusqueda = id.toString();

                    if(idParameto.equalsIgnoreCase(idBusqueda)) {
                        parametrizacionDto = new ParametrizacionDto();
                        parametrizacionDto.setIdParameto(Integer.parseInt(idParameto));
                        String idTipoParametro =  rec.get(1).toString();
                        parametrizacionDto.setIdTipoParametro(Integer.parseInt(idTipoParametro));
                        String idConciliacion =  rec.get(2).toString();
                        parametrizacionDto.setIdConciliacion(Integer.parseInt(idConciliacion));
                        String idConexion1 =  rec.get(3).toString();
                        parametrizacionDto.setIdConexion1(Integer.parseInt(idConexion1));
                        String idConexion2 =  rec.get(4).toString();
                        parametrizacionDto.setIdConexion2(Integer.parseInt(idConexion2));
                        parametrizacionDto.setDataSolicitudOrigen(rec.get(5).toString());
                        parametrizacionDto.setDataSolicitudDestino(rec.get(6).toString());
                        parametrizacionDto.setKeys(rec.get(7).toString());
                        String fechaCreacion = rec.get(8).toString();
                        String fechaModificacion = rec.get(9).toString();

                        try {
                            dateCreacion = formatter.parse(fechaCreacion);
                            dateModificacion = formatter.parse(fechaModificacion);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        parametrizacionDto.setFechaCreacion(dateCreacion);
                        parametrizacionDto.setFechaModificacion(dateModificacion);
                        break;
                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return parametrizacionDto;
    }

}
