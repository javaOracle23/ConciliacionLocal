package com.conciliacion.parquet.utilities;

/**
 * Clase donde se definen las constantes que se van a utilizar en la lógica y peticiones del
 * aplicativo web.
 *
 * @since 1.0.0
 */
public final class ConstantesTipoParametro {

    /** Constantes de la aplicación */
    private ConstantesTipoParametro() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final String NOMBRE_SCHEMA_DATABASE_CONCILIACION =
            "dbo";

    public static final String VALOR_N =
            "N";


    public static final String LISTA_TIPO_PARAMETRO = "ListaTipoParametro";

    public static final String MENSAJE_LISTA_TIPO_PARAMETRO = "No hay parametros registrados";


}
