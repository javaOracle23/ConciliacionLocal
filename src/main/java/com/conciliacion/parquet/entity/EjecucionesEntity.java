package com.conciliacion.parquet.entity;

import java.io.Serializable;
import java.util.Date;


//@Entity
//@Table(schema = ConstantesConexion.NOMBRE_SCHEMA_DATABASE_CONCILIACION, name = "ejecuciones")
public class EjecucionesEntity implements Serializable {

    private static final long serialVersionUID = 1l;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ejecuciones;

//    @NotNull
//    @Column(name = "id_conciliacion")
    private Integer idConciliacion;

//    @Column(name = "resultado_comparacion")
    private String resultadoComparacion;

//    @Column(name = "estado_ejecucion")
    private String estadoEjecucion;

//    @Column(name = "fecha_ejecucion")
    private Date fechaEjecucion;

    public Integer getId_ejecuciones() {
        return id_ejecuciones;
    }

    public void setId_ejecuciones(Integer id_ejecuciones) {
        this.id_ejecuciones = id_ejecuciones;
    }

    public Integer getIdConciliacion() {
        return idConciliacion;
    }

    public void setIdConciliacion(Integer idConciliacion) {
        this.idConciliacion = idConciliacion;
    }

    public String getResultadoComparacion() {
        return resultadoComparacion;
    }

    public void setResultadoComparacion(String resultadoComparacion) {
        this.resultadoComparacion = resultadoComparacion;
    }

    public String getEstadoEjecucion() {
        return estadoEjecucion;
    }

    public void setEstadoEjecucion(String estadoEjecucion) {
        this.estadoEjecucion = estadoEjecucion;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }
}
