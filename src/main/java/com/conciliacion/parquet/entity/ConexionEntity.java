package com.conciliacion.parquet.entity;

import java.io.Serializable;
import java.util.Date;


//@Entity
//@Table(schema = ConstantesConexion.NOMBRE_SCHEMA_DATABASE_CONCILIACION, name = "Conexion")
public class ConexionEntity implements Serializable {

    private static final long serialVersionUID = 1l;

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID_Conexion;

    //@NotNull
    //@Column(name = "ID_Tipo_Conexion")
    private Integer idTipoConexion;

//    @NotNull
//    @Column(name = "nombre")
    private String nombre;

//    @NotNull
//    @Column(name = "host")
    private String host;

//    @NotNull
//    @Column(name = "puerto")
    private String puerto;

//    @NotNull
//    @Column(name = "nombre_Base_De_Datos")
    private String nombreBaseDeDatos;

//    @NotNull
//    @Column(name = "usuario")
    private String usuario;

//    @NotNull
//    @Column(name = "clave")
    private String clave;

//    @Column(name = "fecha_Creacion")
    private Date fechaCreacion;

//    @Column(name = "fecha_Modificacion")
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
