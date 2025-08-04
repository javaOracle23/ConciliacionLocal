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

import java.io.File;
import java.nio.file.Path;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GestionBDMySQLImpl2 implements IGestionFabricaBD {

    private static Logger LOGGER = LoggerFactory.getLogger(GestionBDMySQLImpl2.class);


    @Autowired(required=false)
    private AchivoExtraccionRepository achivoExtraccionRepository;

    @Override
    public Object probarConexion(ConexionDto conexionDto) {

        Map<String, Object> mapResponse = new HashMap<String, Object>();
        String connectionUrl = ConstantesMySQL.obtenerConexionMySQL(conexionDto);
        Connection con = null;

        try {
            Class.forName(ConstantesMySQL.driverMySQL);
            con = DriverManager.getConnection(connectionUrl,conexionDto.getUsuario(),conexionDto.getClave());
            if (con != null) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CONEXION_OK );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }
        } catch (ClassNotFoundException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CARGAR_CONTROLADOR );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
        }catch (SQLException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CONECTAR_BD );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CERRAR_CONEXION );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
                LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            }
        }
        return mapResponse;
    }

    @Override
    public Object consultaTablas(ConexionDto conexionDto,String consultaTablas) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        String connectionUrl = ConstantesMySQL.obtenerConexionMySQL(conexionDto);
        Connection con = null;
        ResultSet rs = null;
        ArrayList<String> listTablas = new ArrayList<>();
        try {
            Class.forName(ConstantesMySQL.driverMySQL);
            con = DriverManager.getConnection(connectionUrl,conexionDto.getUsuario(),conexionDto.getClave());
            if (con != null) {
                rs = con.createStatement().executeQuery(consultaTablas);
                while (rs.next()) {
                    String esquema = rs.getString(1);
                    String nombreTablaBD = esquema != null && !esquema.isEmpty() ? esquema + "." + rs.getString(2) : rs.getString(2);
                    listTablas.add(nombreTablaBD);
                }
                if(listTablas != null && listTablas.size() != 0){
                    mapResponse.put(ConstantesSQLServer.LISTA_TABLAS, listTablas );
                    mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
                }
            }
        } catch (ClassNotFoundException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CARGAR_CONTROLADOR );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
        }catch (SQLException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CONECTAR_BD );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if(rs != null){
                    rs.close();
                }
            } catch (SQLException e) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CERRAR_CONEXION );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
                LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            }
        }
        return mapResponse;
    }

    @Override
    public Object consultaColumnas(ConexionDto conexionDto,String consultaColumnas,String nombreTablaBD) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        String connectionUrl = ConstantesMySQL.obtenerConexionMySQL(conexionDto);
        Connection con = null;
        ResultSet resultSet = null;
        ArrayList<String> listColumnas = new ArrayList<>();
        try {
            Class.forName(ConstantesMySQL.driverMySQL);
            con = DriverManager.getConnection(connectionUrl,conexionDto.getUsuario(),conexionDto.getClave());
            if (con != null) {
                if(nombreTablaBD.contains(".")){
                    String cadenaTabla[] = nombreTablaBD.split("\\.");
                    consultaColumnas = consultaColumnas + " WHERE TABLE_SCHEMA='" + cadenaTabla[0] + "' AND " + " TABLE_NAME='" + cadenaTabla[1] + "'";
                }else{
                    consultaColumnas = consultaColumnas + " WHERE TABLE_NAME='" + nombreTablaBD + "'";
                }
                resultSet = con.createStatement().executeQuery(consultaColumnas);

                while (resultSet.next()) {
                    listColumnas.add(resultSet.getString(1));
                }

                if(listColumnas != null && listColumnas.size() != 0){
                    mapResponse.put(ConstantesSQLServer.LISTA_COLUMNAS_TABLA, listColumnas );
                    mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
                }
            }
        } catch (ClassNotFoundException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CARGAR_CONTROLADOR );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
        }catch (SQLException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CONECTAR_BD );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if(resultSet != null){
                    resultSet.close();
                }
            } catch (SQLException e) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CERRAR_CONEXION );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
                LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            }
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
        ArrayList<String> listDatos = new ArrayList<>();

        String nombreArchivo = null;
        String query = null;
        List<String> keys = null;
        try {
            if(parametrizacionEntity.getIdParameto().intValue() == 1){
                parametrosSQLDto = objectMapper.readValue(jsonSolicitudOrigen, ParametrosSQLDto.class);
                query = parametrosSQLDto.getQuery();
                keys = parametrosSQLDto.getKeys();
            }else if(parametrizacionEntity.getIdParameto().intValue() == 2){
                parametrosTXTDto = objectMapper.readValue(jsonSolicitudOrigen, ParametrosTXTDto.class);
                query = Util.construirQuery(parametrosTXTDto);
                keys = parametrosTXTDto.getKeys();

            }

            String cadenaKeys = String.join(", ", keys);
            achivoExtraccionEntity.setKeys(cadenaKeys);
            achivoExtraccionEntity = ConstantesArchivoExtraccion.guardar(achivoExtraccionEntity);

        } catch (JsonProcessingException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_JSON_MAP );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_DATOS_INVALIDOS );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            return mapResponse;
        }

        String connectionUrl = ConstantesMySQL.obtenerConexionMySQL(conexionDto);
        Connection con = null;
        ResultSet resultSet = null;
        try {
            Class.forName(ConstantesMySQL.driverMySQL);
            con = DriverManager.getConnection(connectionUrl,conexionDto.getUsuario(),conexionDto.getClave());
            if (con != null) {
                resultSet = con.createStatement().executeQuery(query);
                ResultSetMetaData metaDatos = resultSet.getMetaData();
                int numeroColumnas = metaDatos.getColumnCount();
                List<Field> listField = new ArrayList<>();
                for (int i = 1; i <= numeroColumnas; i++) {
                    String nombreColumna = metaDatos.getColumnName(i);
                    listField.add(new Field(nombreColumna, Schema.create(Schema.Type.STRING), null, null) );
                }

                Schema schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);
                schema.setFields(listField);

                nombreArchivo = achivoExtraccionEntity.getId_extraccion() + "_" + ConstantesArchivoExtraccion.ARCHIVO_ORIGEN;

                File outputFiles = new File(nombreArchivo);

                if (outputFiles.exists()) {
                    outputFiles.delete(); // true for recursive delete
                }

                LocalOutputFile outputFile = new LocalOutputFile(Path.of(nombreArchivo));

                try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(outputFile)
                        .withCompressionCodec(CompressionCodecName.GZIP)
                        .withSchema(schema)
                        .withPageSize(1 << 20)
                        .build())
                {
                    GenericData.Record record = null;
                    String nombreColumna = "";
                    String valor = "";
                    while (resultSet.next()) {
                        record = new GenericData.Record(schema);
                        for (int i = 1; i <= numeroColumnas; i++) {
                            nombreColumna = metaDatos.getColumnName(i);
                            valor = resultSet.getObject(i).toString();
                            record.put(nombreColumna, valor);
                        }
                        writer.write(record);
                    }
                }
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_ARCHIVO_EXTRACCION_OK);
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );

            }
        }catch (Exception e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_ARCHIVO_EXTRACCION);
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_REGISTRO_EXISTENTE );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if(resultSet != null){
                    resultSet.close();
                }
            } catch (SQLException e) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CERRAR_CONEXION );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
                LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            }
        }

        return mapResponse;
    }

    @Override
    public Object consultaExtraccionDestino(ConexionDto conexionDto,ParametrizacionEntity parametrizacionEntity,Integer id_extraccion) {
        Map<String, Object> mapResponse = new HashMap<String, Object>();
        String jsonSolicitudDestino = parametrizacionEntity.getDataSolicitudDestino();
        ObjectMapper objectMapper = new ObjectMapper();
        ParametrosSQLDto parametrosSQLDto = null;
        ParametrosTXTDto parametrosTXTDto = null;
        ArrayList<String> listDatos = new ArrayList<>();
        AchivoExtraccionEntity achivoExtraccionEntity = null;
        String nombreArchivo = null;
        String query = null;
        try {
            if(parametrizacionEntity.getIdParameto().intValue() == 1){
                parametrosSQLDto = objectMapper.readValue(jsonSolicitudDestino, ParametrosSQLDto.class);
                query = parametrosSQLDto.getQuery();
            }else if(parametrizacionEntity.getIdParameto().intValue() == 2){
                parametrosTXTDto = objectMapper.readValue(jsonSolicitudDestino, ParametrosTXTDto.class);
                query = Util.construirQuery(parametrosTXTDto);
            }
        } catch (JsonProcessingException e) {
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_JSON_MAP );
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_DATOS_INVALIDOS );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            return mapResponse;
        }

        String connectionUrl = ConstantesMySQL.obtenerConexionMySQL(conexionDto);
        Connection con = null;
        ResultSet resultSet = null;
        try {
            Class.forName(ConstantesMySQL.driverMySQL);
            con = DriverManager.getConnection(connectionUrl,conexionDto.getUsuario(),conexionDto.getClave());
            if (con != null) {
                resultSet = con.createStatement().executeQuery(query);
                ResultSetMetaData metaDatos = resultSet.getMetaData();
                int numeroColumnas = metaDatos.getColumnCount();
                List<Field> listField = new ArrayList<>();
                for (int i = 1; i <= numeroColumnas; i++) {
                    String nombreColumna = metaDatos.getColumnName(i);
                    listField.add(new Field(nombreColumna, Schema.create(Schema.Type.STRING), null, null) );
                }

                Schema schema = Schema.createRecord("recordName", "myrecordname", "org.myorganization.mynamespace", false);
                schema.setFields(listField);


                nombreArchivo = id_extraccion + "_" + ConstantesArchivoExtraccion.ARCHIVO_DESTINO;

                File outputFiles = new File(nombreArchivo);

                if (outputFiles.exists()) {
                    outputFiles.delete(); // true for recursive delete
                }

                LocalOutputFile outputFile = new LocalOutputFile(Path.of(nombreArchivo));

                try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(outputFile)
                        .withCompressionCodec(CompressionCodecName.GZIP)
                        .withSchema(schema)
                        .withPageSize(1 << 20)
                        .build())
                {
                    GenericData.Record record = null;
                    String nombreColumna = "";
                    String valor = "";
                    while (resultSet.next()) {
                        record = new GenericData.Record(schema);
                        for (int i = 1; i <= numeroColumnas; i++) {
                            nombreColumna = metaDatos.getColumnName(i);
                            valor = resultSet.getObject(i).toString();
                            record.put(nombreColumna, valor);
                        }
                        writer.write(record);
                    }
                }
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_ARCHIVO_EXTRACCION_OK);
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_OK );
            }
        }catch (Exception e) {
            if(id_extraccion != null){
                ConstantesArchivoExtraccion.eliminarPorId(Long.valueOf(id_extraccion));
            }
            mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_ARCHIVO_EXTRACCION);
            mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_REGISTRO_EXISTENTE );
            LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if(resultSet != null){
                    resultSet.close();
                }
            } catch (SQLException e) {
                mapResponse.put(ConstantesGenericas.MENSAJE, ConstantesGenericas.MENSAJE_CERRAR_CONEXION );
                mapResponse.put(ConstantesGenericas.CODIGO, ConstantesGenericas.CODIGO_ERROR_BD );
                LOGGER.error(ConstantesGenericas.MENSAJE , e.getMessage());
            }
        }

        return mapResponse;
    }

}
