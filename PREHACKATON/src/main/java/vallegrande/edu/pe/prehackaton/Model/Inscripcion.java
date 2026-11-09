package vallegrande.edu.pe.prehackaton.Model;

import java.sql.Timestamp;

public class Inscripcion {

    private int idInscripcion;
    private int idEvento;
    private int idParticipante;
    private Timestamp fechaInscripcion;
    private String estado;

    public Inscripcion() {
    }

    public Inscripcion(int idInscripcion, int idEvento, int idParticipante, Timestamp fechaInscripcion, String estado) {
        this.idInscripcion = idInscripcion;
        this.idEvento = idEvento;
        this.idParticipante = idParticipante;
        this.fechaInscripcion = fechaInscripcion;
        this.estado = estado;
    }

    public Inscripcion(int idEvento, int idParticipante, String estado) {
        this.idEvento = idEvento;
        this.idParticipante = idParticipante;
        this.estado = estado;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }

    public Timestamp getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Timestamp fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}