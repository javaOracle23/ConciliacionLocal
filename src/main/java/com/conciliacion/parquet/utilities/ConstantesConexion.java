package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.dto.ConexionDto;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Clase donde se definen las constantes que se van a utilizar en la lógica y peticiones del
 * aplicativo web.
 *
 * @since 1.0.0
 */
public final class ConstantesConexion {

    /** Constantes de la aplicación */
    private ConstantesConexion() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final String NOMBRE_SCHEMA_DATABASE_CONCILIACION =
            "dbo";

    public static final String VALOR_N =
            "N";


    public static final String MENSAJE_INSERT_CONEXION = "Ya existe una conexión con el mismo nombre,host y puerto";

    public static final String MENSAJE_INSERT_OK = "Registro guardado con exito";

    public static final String MENSAJE_UPDATE_OK = "Registro actualizado con exito";

    public static final String MENSAJE_INSERT_ERROR_CONEXION = "La conexión no se registro";

    public static final String MENSAJE_UPDATE_ERROR_CONEXION = "La conexión no se actualizo";

    public static final String LISTA_CONEXIONES = "listaconexiones";

    public static final String MENSAJE_LISTA_CONEXIONES = "No hay conexiones registradas";

    public static final String MENSAJE_DELETE_CONEXION = "La conexión se elimino";

    public static final String MENSAJE_DELETE_ERROR_CONEXION = "No existe el registro a eliminar";

    public static final String DATOS_CONEXION = "datosconexion";

    public static final String MENSAJE_DATOS_CONEXION = "No existen la conexion a consultar";

    public static boolean guardar(ConexionDto conexionDto){

        boolean guardadoExitoso = false;
        String nombreArchivo = "TBConexion.parquet";
        List<GenericRecord> records = null;
        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        Integer id = 0;
        File outputFiles = null;

        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ConexionDto.class;
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
                    records.add(rec);
                    id = Integer.parseInt(rec.get(0).toString());

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        conexionDto.setID_Conexion(++id);
        conexionDto.setFechaCreacion(new Date());
        conexionDto.setFechaModificacion(new Date());

        Object objConexion = conexionDto;
        Class<?> clase = objConexion.getClass();
        java.lang.reflect.Field[] campos = clase.getDeclaredFields();

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
            GenericData.Record record = new GenericData.Record(schema);
            Object fieldValue = null;
            for (java.lang.reflect.Field campo : campos) {
                try {
                    campo.setAccessible(true); // Permite acceder a campos privados
                    Object valor = campo.get(objConexion);
                    record.put(campo.getName(), valor != null ? valor.toString(): "");
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


    public static boolean actualizarPorId(ConexionDto conexionDto){

        boolean actualizarExitoso = false;
        String nombreArchivo = "TBConexion.parquet";
        List<GenericRecord> records = null;
        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();

        File outputFiles = null;
        conexionDto.setFechaCreacion(new Date());
        conexionDto.setFechaModificacion(new Date());
        Object objConexion = conexionDto;
        Class<?> clase = objConexion.getClass();
        java.lang.reflect.Field[] campos = clase.getDeclaredFields();

        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ConexionDto.class;
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
                    String idRegistro = rec.get(0).toString();
                    String idConexion = conexionDto.getID_Conexion().toString();
                    if(idRegistro.equalsIgnoreCase(idConexion)){
                        GenericData.Record record = new GenericData.Record(schema);
                        Object fieldValue = null;
                        for (java.lang.reflect.Field campo : campos) {
                            try {
                                campo.setAccessible(true); // Permite acceder a campos privados
                                Object valor = campo.get(objConexion);
                                rec.put(campo.getName(), valor != null ? valor.toString(): "");
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    records.add(rec);

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
        String nombreArchivo = "TBConexion.parquet";
        List<GenericRecord> records = null;
        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();

        File outputFiles = null;


        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ConexionDto.class;
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
                    String idRegistro = rec.get(0).toString();
                    String idConexion = id.toString();
                    if(!idRegistro.equalsIgnoreCase(idConexion)){
                        records.add(rec);
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


    public static  List<ConexionDto> consultarConexiones(){

        String nombreArchivo = "TBConexion.parquet";

        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;
        List<ConexionDto> listConexiones = null;
        ConexionDto conexionDto = null;

        Date dateCreacion = null;
        Date dateModificacion = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("EST")); // Especifica la zona horaria


        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ConexionDto.class;
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
                listConexiones = new ArrayList<>();
                while ((rec = parquetReader.read()) != null) {

                    conexionDto = new ConexionDto();
                    String idConexion =  rec.get(0).toString();
                    conexionDto.setID_Conexion( Integer.parseInt(idConexion) );
                    String idTipoConexion = rec.get(1).toString();
                    conexionDto.setIdTipoConexion(Integer.parseInt(idTipoConexion));
                    conexionDto.setNombre( rec.get(2).toString());
                    conexionDto.setHost(rec.get(3).toString());
                    conexionDto.setPuerto(rec.get(4).toString());
                    conexionDto.setNombreBaseDeDatos(rec.get(5).toString());
                    conexionDto.setUsuario(rec.get(6).toString());
                    conexionDto.setClave(rec.get(7).toString());
                    String fechaCreacion = rec.get(8).toString();
                    String fechaModificacion = rec.get(9).toString();

                    try {
                        dateCreacion = formatter.parse(fechaCreacion);
                        dateModificacion= formatter.parse(fechaModificacion);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    conexionDto.setFechaCreacion(dateCreacion);
                    conexionDto.setFechaModificacion (dateModificacion);

                    listConexiones.add(conexionDto);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return listConexiones;
    }



    public static  ConexionDto consultarConexionesPorId(Long id){

        String nombreArchivo = "TBConexion.parquet";

        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;
        ConexionDto conexionDto = null;

        Date dateCreacion = null;
        Date dateModificacion = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("EST")); // Especifica la zona horaria


        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ConexionDto.class;
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


                    String idConexion =  rec.get(0).toString();
                    String idBusqueda = id.toString();

                    if(idConexion.equalsIgnoreCase(idBusqueda)) {
                        conexionDto = new ConexionDto();
                        conexionDto.setID_Conexion(Integer.parseInt(idConexion));
                        String idTipoConexion = rec.get(1).toString();
                        conexionDto.setIdTipoConexion(Integer.parseInt(idTipoConexion));
                        conexionDto.setNombre(rec.get(2).toString());
                        conexionDto.setHost(rec.get(3).toString());
                        conexionDto.setPuerto(rec.get(4).toString());
                        conexionDto.setNombreBaseDeDatos(rec.get(5).toString());
                        conexionDto.setUsuario(rec.get(6).toString());
                        conexionDto.setClave(rec.get(7).toString());
                        String fechaCreacion = rec.get(8).toString();
                        String fechaModificacion = rec.get(9).toString();

                        try {
                            dateCreacion = formatter.parse(fechaCreacion);
                            dateModificacion = formatter.parse(fechaModificacion);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        conexionDto.setFechaCreacion(dateCreacion);
                        conexionDto.setFechaModificacion(dateModificacion);
                        break;
                    }


                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return conexionDto;
    }

}
