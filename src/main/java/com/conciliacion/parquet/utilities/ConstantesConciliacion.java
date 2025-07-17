package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.dto.ConciliacionDto;
import com.conciliacion.parquet.dto.ConexionDto;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Clase donde se definen las constantes que se van a utilizar en la lógica y peticiones del
 * aplicativo web.
 *
 * @since 1.0.0
 */
public final class ConstantesConciliacion {

    /** Constantes de la aplicación */
    private ConstantesConciliacion() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final String NOMBRE_SCHEMA_DATABASE_CONCILIACION =
            "dbo";

    public static final String VALOR_N =
            "N";

    public static final String MENSAJE_INSERT_OK = "Registro guardado con exito";

    public static final String MENSAJE_UPDATE_OK = "Registro actualizado con exito";

    public static final String MENSAJE_INSERT_ERROR_CONCILIACION = "La conciliacion no se registro";

    public static final String MENSAJE_UPDATE_ERROR_CONCILIACION  = "La concilacion no se actualizo";

    public static final String LISTA_CONCILIACION = "ListaConciliacion";

    public static final String MENSAJE_LISTA_CONCILIACION = "No hay conciliaciones registradas";

    public static final String MENSAJE_CONCILIACION = "No existe la conciliacion a consultar";

    public static final String MENSAJE_DELETE_CONCILIACION = "La conciliacion se elimino";

    public static final String CONCILIACION = "conciliacion";

    public static final String NOMBRE_ARCHIVO = "TBConciliacion.parquet";

    public static boolean guardar(ConciliacionDto conciliacionDto){

        boolean guardadoExitoso = false;
        String nombreArchivo = "TBConciliacion.parquet";
        List<GenericRecord> records = null;
        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        Integer id = 0;
        File outputFiles = null;

        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ConciliacionDto.class;
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
        conciliacionDto.setIdConciliacion(++id);
        conciliacionDto.setFechaCreacion(new Date());

        Object objConciliacion = conciliacionDto;
        Class<?> clase = objConciliacion.getClass();
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
                    Object valor = campo.get(objConciliacion);
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

    public static boolean actualizarPorId(ConciliacionDto conciliacionDto) {
//        String nombreArchivo = "TBConciliacion.parquet";
        boolean actualizarExitoso = false;

        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) return false;

        org.apache.hadoop.fs.Path path = new org.apache.hadoop.fs.Path(archivo.getPath());
        List<GenericRecord> registros = new ArrayList<>();
        Schema schema = construirSchema(ConciliacionDto.class);
        String idActualizar = conciliacionDto.getIdConciliacion().toString();

        try (ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(path)
                .withDataModel(GenericData.get())
                .build()) {
            GenericRecord registro;
            while ((registro = reader.read()) != null) {
                String idActual = registro.get("idConciliacion").toString();
                if (idActualizar.equalsIgnoreCase(idActual)) {
                    // Extraemos la fechaCreacion original
                    String fechaCreacionOriginal = registro.get("fechaCreacion").toString();
                    // Creamos un nuevo registro con los datos actualizados
                    GenericRecord actualizado = crearRegistroDesdeDto(schema, conciliacionDto, fechaCreacionOriginal);
                    registros.add(actualizado);
                } else {
                    registros.add(registro);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo Parquet", e);
        }

        archivo.delete();

        LocalOutputFile outputFile = new LocalOutputFile(Path.of(NOMBRE_ARCHIVO));
        try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(outputFile)
                .withSchema(schema)
                .withCompressionCodec(CompressionCodecName.GZIP)
                .withPageSize(1 << 20)
                .build()) {
            for (GenericRecord record : registros) {
                writer.write(record);
            }
            actualizarExitoso = true;
        } catch (IOException e) {
            throw new RuntimeException("Error escribiendo archivo Parquet", e);
        }

        return actualizarExitoso;
    }

    public static List<ConciliacionDto> consultarConciliacion() {
        String nombreArchivo = "TBConciliacion.parquet"; // Asegúrate de que este sea el nombre correcto
        List<ConciliacionDto> lista = new ArrayList<>();

        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe.");
            return lista;
        }

        org.apache.hadoop.fs.Path path = new org.apache.hadoop.fs.Path(archivo.getPath());

        try (ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(path)
                .withDataModel(GenericData.get())
                .build()) {

            GenericRecord registro;
            while ((registro = reader.read()) != null) {
                ConciliacionDto dto = new ConciliacionDto();

                for (java.lang.reflect.Field campo : ConciliacionDto.class.getDeclaredFields()) {
                    campo.setAccessible(true);
                    String nombreCampo = campo.getName();
                    Object valor = registro.get(nombreCampo);
                    System.out.println("Nombre campo: " + nombreCampo + " Nombre valor: " +valor);
                    if (valor != null) {
                        Class<?> tipoCampo = campo.getType();

                        try {
                            if (tipoCampo.equals(String.class)) {
                                campo.set(dto, valor.toString());

                            } else if (tipoCampo.equals(Integer.class)) {
                                campo.set(dto, Integer.parseInt(valor.toString()));

                            } else if (tipoCampo.equals(Long.class)) {
                                campo.set(dto, Long.parseLong(valor.toString()));

                            } else if (tipoCampo.equals(Date.class)) {
                                // Ajusta el formato si guardaste la fecha con otro formato
                                SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                                campo.set(dto, formatter.parse(valor.toString()));

                            } else {
                                campo.set(dto, valor); // fallback para otros tipos
                            }
                        } catch (Exception e) {
                            System.out.println("Error convirtiendo campo " + campo.getName() + ": " + e.getMessage());
                        }
                    }

                }

                lista.add(dto);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo Parquet", e);
        }

        return lista;
    }

    public static ConciliacionDto consultarConciliacionPorId(Long id){

        String nombreArchivo = "TBConciliacion.parquet";

        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        File outputFiles = null;
        ConciliacionDto conciliacionDto = null;

        Date dateCreacion = null;
        Date dateModificacion = null;
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("EST")); // Especifica la zona horaria


        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ConciliacionDto.class;
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


                    String idConciliacion =  rec.get(0).toString();
                    String idBusqueda = id.toString();

                    if(idConciliacion.equalsIgnoreCase(idBusqueda)) {
                        conciliacionDto = new ConciliacionDto();
                        conciliacionDto.setIdConciliacion(Integer.parseInt(idConciliacion));
                        conciliacionDto.setNombreConciliacion(rec.get(1).toString());
                        String fechaCreacion = rec.get(2).toString();
                        String fechaModificacion = rec.get(3).toString();

                        try {
                            dateCreacion = formatter.parse(fechaCreacion);
                            if (!fechaModificacion.isEmpty()){
                                dateModificacion = formatter.parse(fechaModificacion);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        conciliacionDto.setFechaCreacion(dateCreacion);
                        conciliacionDto.setFechaModificacion(dateModificacion);
                        break;
                    }


                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return conciliacionDto;
    }

    public static boolean eliminarPorId(Long id){

        boolean eliminarExitoso = false;
        String nombreArchivo = "TBConciliacion.parquet";
        List<GenericRecord> records = null;
        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();

        File outputFiles = null;


        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = ConciliacionDto.class;
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
                    String idConciliacion = id.toString();
                    if(!idRegistro.equalsIgnoreCase(idConciliacion)){
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

    public static GenericRecord crearRegistroDesdeDto(Schema schema, Object dto, String fechaCreacionOriginal) {
        GenericRecord record = new GenericData.Record(schema);
        Date ahora = new Date();

        for (java.lang.reflect.Field campo : dto.getClass().getDeclaredFields()) {
            campo.setAccessible(true);
            try {
                String nombreCampo = campo.getName();
                Object valor = campo.get(dto);

                if (nombreCampo.equals("fechaCreacion")) {
                    record.put(nombreCampo, fechaCreacionOriginal); // Conserva la original
                } else if (nombreCampo.equals("fechaModificacion")) {
                    record.put(nombreCampo, ahora.toString()); // Fecha actual
                } else {
                    record.put(nombreCampo, valor != null ? valor.toString() : "");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accediendo campo DTO", e);
            }
        }
        return record;
    }

    public static Schema construirSchema(Class<?> claseDto) {
        List<Schema.Field> campos = new ArrayList<>();
        for (java.lang.reflect.Field campo : claseDto.getDeclaredFields()) {
            campo.setAccessible(true);
            Schema fieldSchema = Schema.create(Schema.Type.STRING); // Puedes adaptar esto si sabes el tipo real
            campos.add(new Schema.Field(campo.getName(), fieldSchema, null, (Object) null));
        }
        Schema schema = Schema.createRecord("record", null, null, false);
        schema.setFields(campos);
        return schema;
    }

}
