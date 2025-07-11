package com.conciliacion.parquet.entity;

import java.io.Serializable;


//@Entity
//@Table(schema = ConstantesConexion.NOMBRE_SCHEMA_DATABASE_CONCILIACION, name = "parametros_ejecutados")
public class ParametrosEjecutadosEntity implements Serializable {

    private static final long serialVersionUID = 1l;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_parametros_ejecutados;

//    @NotNull
//    @Column(name = "id_ejecuciones")
    private Integer idEjecuciones;

//    @NotNull
//    @Column(name = "json_parametros_origen")
    private String jsonParametrosOrigen;

//    @NotNull
//    @Column(name = "json_parametros_destino")
    private Integer jsonParametrosDestino;

//    @Column(name = "job_id")
    private Integer jobId;

    public Integer getId_parametros_ejecutados() {
        return id_parametros_ejecutados;
    }

    public void setId_parametros_ejecutados(Integer id_parametros_ejecutados) {
        this.id_parametros_ejecutados = id_parametros_ejecutados;
    }

    public Integer getIdEjecuciones() {
        return idEjecuciones;
    }

    public void setIdEjecuciones(Integer idEjecuciones) {
        this.idEjecuciones = idEjecuciones;
    }

    public String getJsonParametrosOrigen() {
        return jsonParametrosOrigen;
    }

    public void setJsonParametrosOrigen(String jsonParametrosOrigen) {
        this.jsonParametrosOrigen = jsonParametrosOrigen;
    }

    public Integer getJsonParametrosDestino() {
        return jsonParametrosDestino;
    }

    public void setJsonParametrosDestino(Integer jsonParametrosDestino) {
        this.jsonParametrosDestino = jsonParametrosDestino;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
}
