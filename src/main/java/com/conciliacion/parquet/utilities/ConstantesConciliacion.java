package com.conciliacion.parquet.utilities;

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

}
