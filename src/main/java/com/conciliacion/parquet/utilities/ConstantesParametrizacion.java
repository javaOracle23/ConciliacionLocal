package com.conciliacion.parquet.utilities;

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



}
