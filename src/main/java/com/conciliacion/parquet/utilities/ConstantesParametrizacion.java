package com.conciliacion.parquet.utilities;

import com.conciliacion.parquet.entity.ParametrizacionEntity;
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
                        break;
                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return parametrizacionEntity;
    }

}
