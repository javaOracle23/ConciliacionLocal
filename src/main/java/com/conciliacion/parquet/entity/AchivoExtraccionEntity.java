package com.conciliacion.parquet.entity;

import java.io.Serializable;

//@Entity
//@Table(schema = ConstantesConexion.NOMBRE_SCHEMA_DATABASE_CONCILIACION, name = "archivo_extraccion")
public class AchivoExtraccionEntity implements Serializable {

    private static final long serialVersionUID = 1l;

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_extraccion;

    //@NotNull
    //@Column(name = "id_conciliacion")
    private Integer idConciliacion;

    //@Column(name = "nombre_origen")
    private String nombreOrigen;

    //@Column(name = "nombre_destino")
    private String nombreDestino;

    public Integer getId_extraccion() {
        return id_extraccion;
    }

    public void setId_extraccion(Integer id_extraccion) {
        this.id_extraccion = id_extraccion;
    }

    public Integer getIdConciliacion() {
        return idConciliacion;
    }

    public void setIdConciliacion(Integer idConciliacion) {
        this.idConciliacion = idConciliacion;
    }

    public String getNombreOrigen() {
        return nombreOrigen;
    }

    public void setNombreOrigen(String nombreOrigen) {
        this.nombreOrigen = nombreOrigen;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
    }
}
