package com.conciliacion.parquet.dto;


import java.io.Serializable;
import java.util.Date;

public class ConexionDto implements Serializable {

    //private static final long serialVersionUID = 1L;

    private Integer ID_Conexion;


    private Integer idTipoConexion;


    private String nombre;


    private String host;


    private String puerto;


    private String nombreBaseDeDatos;


    private String usuario;


    private String clave;

    private String jsonBigQuery;

    private Date fechaCreacion;


    private Date fechaModificacion;

    public Integer getID_Conexion() {
        return ID_Conexion;
    }

    public void setID_Conexion(Integer ID_Conexion) {
        this.ID_Conexion = ID_Conexion;
    }

    public Integer getIdTipoConexion() {
        return idTipoConexion;
    }

    public void setIdTipoConexion(Integer idTipoConexion) {
        this.idTipoConexion = idTipoConexion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getNombreBaseDeDatos() {
        return nombreBaseDeDatos;
    }

    public void setNombreBaseDeDatos(String nombreBaseDeDatos) {
        this.nombreBaseDeDatos = nombreBaseDeDatos;
    }

    public String getJsonBigQuery() {
        return jsonBigQuery;
    }

    public void setJsonBigQuery(String jsonBigQuery) {
        this.jsonBigQuery = jsonBigQuery;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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
