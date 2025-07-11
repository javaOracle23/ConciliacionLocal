package com.conciliacion.parquet.entity;


import java.util.Date;

//@Entity
//@Table(schema = ConstantesConciliacion.NOMBRE_SCHEMA_DATABASE_CONCILIACION, name = "conciliacion")
public class ConciliacionEntity {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conciliacion")*/
    private Integer idConciliacion;

    //@Column(name = "nombre_conciliacion")
    private String nombreConciliacion;

    //@Column(name = "fecha_creacion")
    private Date fechaCreacion;

    //@Column(name = "fecha_modificacion")
    private Date fechaModificacion;


    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreConciliacion() {
        return nombreConciliacion;
    }

    public void setNombreConciliacion(String nombreConciliacion) {
        this.nombreConciliacion = nombreConciliacion;
    }

    public Integer getIdConciliacion() {
        return idConciliacion;
    }

    public void setIdConciliacion(Integer idConciliacion) {
        this.idConciliacion = idConciliacion;
    }
}
