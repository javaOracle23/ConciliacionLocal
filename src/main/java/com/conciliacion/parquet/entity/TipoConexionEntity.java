package com.conciliacion.parquet.entity;

import java.io.Serializable;



//@Entity
//@Table(schema = ConstantesConexion.NOMBRE_SCHEMA_DATABASE_CONCILIACION, name = "Tipo_conexion")
public class TipoConexionEntity implements Serializable {

    private static final long serialVersionUID = 1l;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID_Tipo_Conexion;

//    @NotNull
//    @Column(name = "nombre")
    private String nombre;

//    @Column(name = "consulta_Tablas")
    private String consultaTablas;

//    @Column(name = "consulta_Columnas")
    private String consultaColumnas;

    public Integer getID_Tipo_Conexion() {
        return ID_Tipo_Conexion;
    }

    public void setID_Tipo_Conexion(Integer ID_Tipo_Conexion) {
        this.ID_Tipo_Conexion = ID_Tipo_Conexion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getConsultaTablas() {
        return consultaTablas;
    }

    public void setConsultaTablas(String consultaTablas) {
        this.consultaTablas = consultaTablas;
    }

    public String getConsultaColumnas() {
        return consultaColumnas;
    }

    public void setConsultaColumnas(String consultaColumnas) {
        this.consultaColumnas = consultaColumnas;
    }
}
