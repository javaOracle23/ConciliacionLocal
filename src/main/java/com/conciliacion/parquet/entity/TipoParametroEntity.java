package com.conciliacion.parquet.entity;

//@Entity
//@Table(schema = ConstantesTipoParametro.NOMBRE_SCHEMA_DATABASE_CONCILIACION, name = "tipoparametro")
public class TipoParametroEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipo_parametro;

//    @NotNull
//    @Column(name = "tipo")
    private String tipo;

    public Integer getId_tipo_parametro() {
        return id_tipo_parametro;
    }

    public void setId_tipo_parametro(Integer id_tipo_parametro) {
        this.id_tipo_parametro = id_tipo_parametro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
