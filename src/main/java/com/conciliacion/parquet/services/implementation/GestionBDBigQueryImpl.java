package com.conciliacion.parquet.services.implementation;

import com.conciliacion.parquet.dto.ConexionDto;
import com.conciliacion.parquet.dto.ParametrosSQLDto;
import com.conciliacion.parquet.dto.ParametrosTXTDto;
import com.conciliacion.parquet.entity.AchivoExtraccionEntity;
import com.conciliacion.parquet.entity.ParametrizacionEntity;
import com.conciliacion.parquet.repository.AchivoExtraccionRepository;
import com.conciliacion.parquet.services.interfaces.IGestionFabricaBD;
import com.conciliacion.parquet.services.interfaces.IRepositorioAzure;
import com.conciliacion.parquet.utilities.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.*;
import net.bytebuddy.asm.Advice;
import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.sql.*;
import java.sql.Connection;
import java.util.*;


@Service
public class GestionBDBigQueryImpl implements IGestionFabricaBD {

    private static Logger LOGGER = LoggerFactory.getLogger(GestionBDBigQueryImpl.class);


    @Autowired(required=false)
    private AchivoExtraccionRepository achivoExtraccionRepository;




    @Override
    public Object probarConexion(ConexionDto conexionDto) {

        String jsonCredenciales = conexionDto.getJsonBigQuery();
        String nombreArchivoCredenciales = "jsonCredencialesBigQuery";
        Map<String, Object> mapResponse = new HashMap<String, Object>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivoCredenciales))) {
            writer.write(jsonCredenciales);
        } catch (IOException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_ARCHIVO_JSON );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_CAMPO_OBLIGATORIO );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            return mapResponse;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(jsonCredenciales, Map.class);
        } catch (JsonProcessingException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_JSON_MAP + ": " + e.getOriginalMessage());
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_DATOS_INVALIDOS );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage(), e);
            return mapResponse;
        }

        String project_id = (String)map.get("project_id");
        try{
                GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(nombreArchivoCredenciales))
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

            BigQuery bigquery = BigQueryOptions.newBuilder()
                    .setProjectId(project_id)
                    .setCredentials(credentials)
                    .build()
                    .getService();

            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CONEXION_OK );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );


        } catch (IOException | BigQueryException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CONECTAR_BD );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
        }
        return mapResponse;
    }

    @Override
    public Object consultaTablas(ConexionDto conexionDto,String consultaTablas) {

        String jsonCredenciales = conexionDto.getJsonBigQuery();
        String dataSet = conexionDto.getNombreBaseDeDatos();
        String nombreArchivoCredenciales = "jsonCredencialesBigQuery";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        ArrayList<String> listTablas = new ArrayList<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivoCredenciales))) {
            writer.write(jsonCredenciales);
        } catch (IOException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_ARCHIVO_JSON );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_CAMPO_OBLIGATORIO );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            return mapResponse;
        }

        String project_id = conexionDto.getHost();
        try{
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(nombreArchivoCredenciales))
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

            BigQuery bigquery = BigQueryOptions.newBuilder()
                    .setProjectId(project_id)
                    .setCredentials(credentials)
                    .build()
                    .getService();

            consultaTablas = consultaTablas + " FROM " +  dataSet + ".INFORMATION_SCHEMA.TABLES";

            //String query = "SELECT * FROM `bdprueba-466214.id_empleado_123.usuarios` LIMIT 1000";
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(consultaTablas).build();

            // Ejecutar la consulta
            Job job = bigquery.create(JobInfo.of(queryConfig));
            job = job.waitFor();

            // Manejar errores
            if (job == null) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesBigQuery.MENSAJE_JOB );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesBigQuery.CODIGO_JOB );
                LOGGER.error(ConstantesGenericas.MENSAJE , ConstantesBigQuery.MENSAJE_JOB);
                return mapResponse;
            }
            if (job.getStatus().getError() != null) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesBigQuery.MENSAJE_JOB );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesBigQuery.CODIGO_JOB );
                LOGGER.error(ConstantesGenericas.MENSAJE , ConstantesBigQuery.MENSAJE_JOB);
                return mapResponse;
            }

            TableResult result = job.getQueryResults();

            for (FieldValueList row : result.iterateAll()) {
                String esquema = row.get(0).getValue().toString();
                String nombreTablaBD = row.get(1).getValue().toString();
                nombreTablaBD = esquema != null && !esquema.isEmpty() ? esquema + "." + nombreTablaBD : nombreTablaBD;
                listTablas.add(nombreTablaBD);
            }

            if(listTablas != null && listTablas.size() != 0){
                mapResponse.put(ConstantesBigQuery.LISTA_TABLAS, listTablas );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }

        } catch (IOException | RuntimeException | InterruptedException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CONECTAR_BD );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
        }
        return mapResponse;
    }

    @Override
    public Object consultaColumnas(ConexionDto conexionDto,String consultaColumnas,String nombreTablaBD) {

        String jsonCredenciales = conexionDto.getJsonBigQuery();
        String dataSet = conexionDto.getNombreBaseDeDatos();
        String nombreArchivoCredenciales = "jsonCredencialesBigQuery";
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        ArrayList<String> listColumnas = new ArrayList<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivoCredenciales))) {
            writer.write(jsonCredenciales);
        } catch (IOException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_ARCHIVO_JSON );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_CAMPO_OBLIGATORIO );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            return mapResponse;
        }

        String project_id = conexionDto.getHost();
        try{
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(nombreArchivoCredenciales))
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

            BigQuery bigquery = BigQueryOptions.newBuilder()
                    .setProjectId(project_id)
                    .setCredentials(credentials)
                    .build()
                    .getService();

            //consultaTablas = consultaTablas + " FROM " + "`" + project_id + "`." + dataSet + ".INFORMATION_SCHEMA.TABLES";
            if(nombreTablaBD.contains(".")){
                String cadenaTabla[] = nombreTablaBD.split("\\.");
                consultaColumnas = consultaColumnas + " FROM "  +  dataSet + ".INFORMATION_SCHEMA.COLUMNS WHERE table_name=" + "'" + cadenaTabla[1] + "'";
            }else{
                consultaColumnas = consultaColumnas + " FROM "  +  dataSet + ".INFORMATION_SCHEMA.COLUMNS WHERE table_name=" + "'" + nombreTablaBD + "'";
            }

            //String query = "SELECT * FROM `bdprueba-466214.id_empleado_123.usuarios` LIMIT 1000";
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(consultaColumnas).build();

            // Ejecutar la consulta
            Job job = bigquery.create(JobInfo.of(queryConfig));
            job = job.waitFor();

            // Manejar errores
            if (job == null) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesBigQuery.MENSAJE_JOB );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesBigQuery.CODIGO_JOB );
                LOGGER.error(ConstantesGenericas.MENSAJE , ConstantesBigQuery.MENSAJE_JOB);
                return mapResponse;
            }
            if (job.getStatus().getError() != null) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesBigQuery.MENSAJE_JOB );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesBigQuery.CODIGO_JOB );
                LOGGER.error(ConstantesGenericas.MENSAJE , ConstantesBigQuery.MENSAJE_JOB);
                return mapResponse;
            }

            TableResult result = job.getQueryResults();

            for (FieldValueList row : result.iterateAll()) {
                listColumnas.add(row.get(0).getValue().toString());
            }

            if(listColumnas != null && listColumnas.size() != 0){
                mapResponse.put(ConstantesBigQuery.LISTA_TABLAS, listColumnas );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }

        } catch (IOException | RuntimeException | InterruptedException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CONECTAR_BD );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
        }
        return mapResponse;
    }

    @Override
    public Object consultaExtraccionOrigen(ConexionDto conexionDto,ParametrizacionEntity parametrizacionEntity, AchivoExtraccionEntity achivoExtraccionEntity) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        String jsonSolicitudOrigen = parametrizacionEntity.getDataSolicitudOrigen();
        ObjectMapper objectMapper = new ObjectMapper();
        ParametrosSQLDto parametrosSQLDto = null;
        ParametrosTXTDto parametrosTXTDto = null;
        String nombreArchivo = null;
        String query = null;
        List<String> columns = null;
        File outputFiles = null;
        List<String> keys = null;
        try {

            if(parametrizacionEntity.getIdParameto().intValue() == 1){
                parametrosSQLDto = objectMapper.readValue(jsonSolicitudOrigen, ParametrosSQLDto.class);
                //columns = parametrosTXTDto.getColumns();
                query = parametrosSQLDto.getQuery();
                if(query.contains("*")){
                    columns = parametrosSQLDto.getColumns();
                }else{
                    String palabraInicio = "SELECT";
                    String palabraFin = "FROM";
                    String columnas = Util.obtenerSubcadena(palabraInicio.toLowerCase(),palabraFin.toLowerCase(),query.toLowerCase());
                    if(columnas.contains(",")) {
                        columns = Arrays.stream(columnas.split(",")).toList();
                    }
                }
                keys = parametrosSQLDto.getKeys();
            }else if(parametrizacionEntity.getIdParameto().intValue() == 2){
                parametrosTXTDto = objectMapper.readValue(jsonSolicitudOrigen, ParametrosTXTDto.class);
                query = Util.construirQuery(parametrosTXTDto);
                columns = parametrosTXTDto.getColumns();
                keys = parametrosTXTDto.getKeys();
            }

            String cadenaKeys = String.join(", ", keys);
            achivoExtraccionEntity.setKeys(cadenaKeys);
            achivoExtraccionEntity = ConstantesArchivoExtraccion.guardar(achivoExtraccionEntity);

        } catch (JsonProcessingException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_JSON_MAP + ": " + e.getOriginalMessage());
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_DATOS_INVALIDOS );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage(),e);
            return mapResponse;
        }
        List<Field> listField = new ArrayList<>();
        for (String columna:columns) {
            listField.add(new Field(columna.trim(), Schema.create(Schema.Type.STRING), null, null) );
        }
        Schema schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);
        schema.setFields(listField);
        nombreArchivo = achivoExtraccionEntity.getId_extraccion() + "_" + ConstantesArchivoExtraccion.ARCHIVO_ORIGEN;

        outputFiles = new File(nombreArchivo);

        if (outputFiles.exists()) {
            outputFiles.delete();
        }

        LocalOutputFile outputFile = new LocalOutputFile(Path.of(nombreArchivo));

        String jsonCredenciales = conexionDto.getJsonBigQuery();
        String nombreArchivoCredenciales = "jsonCredencialesBigQuery";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivoCredenciales))) {
            writer.write(jsonCredenciales);
        } catch (IOException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_ARCHIVO_JSON );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_CAMPO_OBLIGATORIO );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            return mapResponse;
        }

        String project_id = conexionDto.getHost();
        try{
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(nombreArchivoCredenciales))
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

            BigQuery bigquery = BigQueryOptions.newBuilder()
                    .setProjectId(project_id)
                    .setCredentials(credentials)
                    .build()
                    .getService();


            //String query = "SELECT * FROM `bdprueba-466214.id_empleado_123.usuarios` LIMIT 1000";
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

            Job job = bigquery.create(JobInfo.of(queryConfig));
            job = job.waitFor();

            if (job == null) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesBigQuery.MENSAJE_JOB );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesBigQuery.CODIGO_JOB );
                LOGGER.error(ConstantesGenericas.MENSAJE , ConstantesBigQuery.MENSAJE_JOB);
                return mapResponse;
            }
            if (job.getStatus().getError() != null) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesBigQuery.MENSAJE_JOB );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesBigQuery.CODIGO_JOB );
                LOGGER.error(ConstantesGenericas.MENSAJE , ConstantesBigQuery.MENSAJE_JOB);
                return mapResponse;
            }

            TableResult result = job.getQueryResults();

            try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(outputFile)
                    .withCompressionCodec(CompressionCodecName.GZIP)
                    .withSchema(schema)
                    .withPageSize(1 << 20)
                    .build())
            {
                GenericData.Record record = null;
                String nombreColumna = "";
                String valor = "";
                int i = 0;

                for (FieldValueList row : result.iterateAll()) {
                    i = 0;
                    record = new GenericData.Record(schema);
                    for (String columna:columns) {
                        nombreColumna = columns.get(i);
                        valor = row.get(i).getValue().toString();
                        record.put(nombreColumna.trim(), valor);
                        i++;
                    }
                    writer.write(record);
                }
            }
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_ARCHIVO_EXTRACCION_OK);
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );

        } catch (IOException | RuntimeException | InterruptedException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_ARCHIVO_EXTRACCION);
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_REGISTRO_EXISTENTE );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
        }
        return mapResponse;
    }

    @Override
    public Object consultaExtraccionDestino(ConexionDto conexionDto,ParametrizacionEntity parametrizacionEntity, Integer id_extraccion) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        String jsonSolicitudDestino = parametrizacionEntity.getDataSolicitudDestino();
        ObjectMapper objectMapper = new ObjectMapper();
        ParametrosSQLDto parametrosSQLDto = null;
        ParametrosTXTDto parametrosTXTDto = null;
        String nombreArchivo = null;
        String query = null;
        List<String> columns = null;
        File outputFiles = null;
        try {
            columns = parametrosTXTDto.getColumns();
            if(parametrizacionEntity.getIdParameto().intValue() == 1){
                parametrosSQLDto = objectMapper.readValue(jsonSolicitudDestino, ParametrosSQLDto.class);
                query = parametrosSQLDto.getQuery();
                String palabraInicio = "SELECT";
                String palabraFin = "FROM";
                String columnas = Util.obtenerSubcadena(palabraInicio.toLowerCase(),palabraFin.toLowerCase(),query.toLowerCase());
                if(columnas.contains(",")) {
                    columns = Arrays.stream(columnas.split(",")).toList();
                }
            }else if(parametrizacionEntity.getIdParameto().intValue() == 2){
                parametrosTXTDto = objectMapper.readValue(jsonSolicitudDestino, ParametrosTXTDto.class);
                query = Util.construirQuery(parametrosTXTDto);
                columns = parametrosTXTDto.getColumns();
            }
        } catch (JsonProcessingException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_JSON_MAP + ": " + e.getOriginalMessage());
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_DATOS_INVALIDOS );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage(), e);
            return mapResponse;
        }
        List<Field> listField = new ArrayList<>();
        for (String columna:columns) {
            listField.add(new Field(columna, Schema.create(Schema.Type.STRING), null, null) );
        }
        Schema schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);
        schema.setFields(listField);
        nombreArchivo = id_extraccion + "_" + ConstantesArchivoExtraccion.ARCHIVO_DESTINO;

        outputFiles = new File(nombreArchivo);

        if (outputFiles.exists()) {
            outputFiles.delete(); // true for recursive delete
        }

        LocalOutputFile outputFile = new LocalOutputFile(Path.of(nombreArchivo));

        String jsonCredenciales = conexionDto.getJsonBigQuery();
        String nombreArchivoCredenciales = "jsonCredencialesBigQuery";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivoCredenciales))) {
            writer.write(jsonCredenciales);
        } catch (IOException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_ARCHIVO_JSON );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_CAMPO_OBLIGATORIO );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            return mapResponse;
        }

        String project_id = conexionDto.getHost();
        try{
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(nombreArchivoCredenciales))
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

            BigQuery bigquery = BigQueryOptions.newBuilder()
                    .setProjectId(project_id)
                    .setCredentials(credentials)
                    .build()
                    .getService();

            //String query = "SELECT * FROM `bdprueba-466214.id_empleado_123.usuarios` LIMIT 1000";
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

            Job job = bigquery.create(JobInfo.of(queryConfig));
            job = job.waitFor();

            if (job == null) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesBigQuery.MENSAJE_JOB );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesBigQuery.CODIGO_JOB );
                LOGGER.error(ConstantesGenericas.MENSAJE , ConstantesBigQuery.MENSAJE_JOB);
                return mapResponse;
            }
            if (job.getStatus().getError() != null) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesBigQuery.MENSAJE_JOB );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesBigQuery.CODIGO_JOB );
                LOGGER.error(ConstantesGenericas.MENSAJE , ConstantesBigQuery.MENSAJE_JOB);
                return mapResponse;
            }

            TableResult result = job.getQueryResults();

            try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(outputFile)
                    .withCompressionCodec(CompressionCodecName.GZIP)
                    .withSchema(schema)
                    .withPageSize(1 << 20)
                    .build())
            {
                GenericData.Record record = null;
                String nombreColumna = "";
                String valor = "";
                int i = 0;
                record = new GenericData.Record(schema);
                for (FieldValueList row : result.iterateAll()) {
                    i = 0;
                    for (String columna:columns) {
                        nombreColumna = columns.get(i);
                        valor = row.get(i).getValue().toString();
                        record.put(nombreColumna, valor);
                    }
                    writer.write(record);
                }
            }

            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_ARCHIVO_EXTRACCION_OK);
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );

        } catch (IOException | RuntimeException | InterruptedException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_ARCHIVO_EXTRACCION);
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_REGISTRO_EXISTENTE );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
        }
        return mapResponse;
    }

}
