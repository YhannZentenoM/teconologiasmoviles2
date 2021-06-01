package com.example.nomasfilas;

import java.io.Serializable;

public class Citas {

    private Integer idusuario;
    private String consultorio, medico, fecha, hora;

    public Citas (Integer idusuario, String consultorio, String medico, String fecha, String hora){
        this.idusuario = idusuario;
        this.consultorio = consultorio;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Citas() {

    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
