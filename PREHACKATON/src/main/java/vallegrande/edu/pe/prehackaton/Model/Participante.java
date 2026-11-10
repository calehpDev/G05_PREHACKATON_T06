package vallegrande.edu.pe.prehackaton.Model;

public class Participante {
    private int idParticipante;
    private String nombres;
    private String apellidos;
    private String dni;
    private String correo;
    private String telefono;
    private String institucion;

    // Constructor vacío
    public Participante() {
    }

    // Constructor completo (para listar / leer datos de la BD)
    public Participante(int idParticipante, String nombres, String apellidos, String dni, String correo, String telefono, String institucion) {
        this.idParticipante = idParticipante;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.institucion = institucion;
    }

    // Constructor sin ID (para registrar nuevos participantes)
    public Participante(String nombres, String apellidos, String dni, String correo, String telefono, String institucion) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.institucion = institucion;
    }

    // Getters y Setters
    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }
}
