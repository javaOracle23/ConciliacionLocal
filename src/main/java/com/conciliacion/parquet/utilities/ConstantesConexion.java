package com.conciliacion.parquet.utilities;

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

}
