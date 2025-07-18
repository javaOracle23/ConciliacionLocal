package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.entity.AchivoExtraccionEntity;
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
import java.util.List;

public class ConstantesArchivoExtraccion {



    public static final String ARCHIVO_ORIGEN = "archivo_origen.parquet";

    public static final String ARCHIVO_DESTINO = "archivo_destino.parquet";

    public static AchivoExtraccionEntity guardar(AchivoExtraccionEntity achivoExtraccionEntity){

        boolean guardadoExitoso = false;
        String nombreArchivo = "TBArchivoExtraccion.parquet";
        List<GenericRecord> records = null;
        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();
        Integer id = 0;
        File outputFiles = null;

        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = AchivoExtraccionEntity.class;
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
        achivoExtraccionEntity.setId_extraccion(++id);

        Object objAchivoExtraccion = achivoExtraccionEntity;
        Class<?> clase = objAchivoExtraccion.getClass();
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
                    Object valor = campo.get(objAchivoExtraccion);
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

        return (guardadoExitoso ? achivoExtraccionEntity : null);
    }

    private static int getId() {
        return 0;
    }


    public static boolean eliminarPorId(Long id){

        boolean eliminarExitoso = false;
        String nombreArchivo = "TBArchivoExtraccion.parquet";
        List<GenericRecord> records = null;
        org.apache.hadoop.fs.Path path= null;
        Schema schema = null;
        List<Schema.Field> listField = new ArrayList<>();

        File outputFiles = null;


        schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);

        Class<?> miClase = AchivoExtraccionEntity.class;
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
                    String id_extraccion = id.toString();
                    if(!idRegistro.equalsIgnoreCase(id_extraccion)){
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


}
