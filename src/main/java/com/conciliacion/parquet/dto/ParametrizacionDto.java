package com.conciliacion.parquet.dto;


import java.util.Date;

public class ParametrizacionDto {

    private Integer idParameto;

    private Integer idTipoParametro;

    private Integer idConciliacion;

    private Integer idConexion1;

    private Integer idConexion2;

    private String dataSolicitudOrigen;

    private String dataSolicitudDestino;

    private Date fechaCreacion;

    private Date fechaModificacion;

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
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
