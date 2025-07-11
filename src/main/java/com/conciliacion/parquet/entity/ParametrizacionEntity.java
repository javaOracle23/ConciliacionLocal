package com.conciliacion.parquet.entity;


//@Entity
//@Table(schema = ConstantesConexion.NOMBRE_SCHEMA_DATABASE_CONCILIACION, name = "parametrizacion")
public class ParametrizacionEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_parametro")
    private Integer idParameto;

//    @Column(name = "id_tipo_parametro")
    private Integer idTipoParametro;

//    @Column(name = "id_conciliacion")
    private Integer idConciliacion;

//    @Column(name = "id_conexion_1")
    private Integer idConexion1;

//    @Column(name = "id_conexion_2")
    private Integer idConexion2;

//    @Column(name = "data_solicitud_origen")
    private String dataSolicitudOrigen;

//    @Column(name = "data_solicitud_destino")
    private String dataSolicitudDestino;


    public Integer getIdParameto() {
        return idParameto;
    }

    public void setIdParameto(Integer idParameto) {
        this.idParameto = idParameto;
    }

    public Integer getIdTipoParametro() {
        return idTipoParametro;
    }

    public void setIdTipoParametro(Integer idTipoParametro) {
        this.idTipoParametro = idTipoParametro;
    }

    public Integer getIdConciliacion() {
        return idConciliacion;
    }

    public void setIdConciliacion(Integer idConciliacion) {
        this.idConciliacion = idConciliacion;
    }

    public Integer getIdConexion1() {
        return idConexion1;
    }

    public void setIdConexion1(Integer idConexion1) {
        this.idConexion1 = idConexion1;
    }

    public Integer getIdConexion2() {
        return idConexion2;
    }

    public void setIdConexion2(Integer idConexion2) {
        this.idConexion2 = idConexion2;
    }

    public String getDataSolicitudOrigen() {
        return dataSolicitudOrigen;
    }

    public void setDataSolicitudOrigen(String dataSolicitudOrigen) {
        this.dataSolicitudOrigen = dataSolicitudOrigen;
    }

    public String getDataSolicitudDestino() {
        return dataSolicitudDestino;
    }

    public void setDataSolicitudDestino(String dataSolicitudDestino) {
        this.dataSolicitudDestino = dataSolicitudDestino;
    }
}
