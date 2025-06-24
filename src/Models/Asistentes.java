package Models;

import services.ValidadorAsistentes;

public class Asistentes {
    private String nombrePersona;
    private String apellidoPersona;
    private String correoElectronico;
    private String numeroTelefono;
    
    private final ValidadorAsistentes validador = ValidadorAsistentes.obtenerInstancia();

    public Asistentes(String nombrePersona, String apellidoPersona, String correoElectronico, String numeroTelefono) {
        this.nombrePersona = validador.validarNombre(nombrePersona);
        this.apellidoPersona = validador.validarApellido(apellidoPersona);
        this.correoElectronico = validador.validarEmail(correoElectronico);
        this.numeroTelefono = validador.validarTelefono(numeroTelefono);
    }

    public String getNombre() {
        return nombrePersona;
    }

    public void setNombre(String nombrePersona) {
        this.nombrePersona = validador.validarNombre(nombrePersona);
    }

    public String getApellido() {
        return apellidoPersona;
    }

    public void setApellido(String apellidoPersona) {
        this.apellidoPersona = validador.validarApellido(apellidoPersona);
    }

    public String getEmail() {
        return correoElectronico;
    }

    public void setEmail(String correoElectronico) {
        this.correoElectronico = validador.validarEmail(correoElectronico);
    }

    public String getTelefono() {
        return numeroTelefono;
    }

    public void setTelefono(String numeroTelefono) {
        this.numeroTelefono = validador.validarTelefono(numeroTelefono);
    }

    public String obtenerNombreCompleto() {
        return nombrePersona + " " + apellidoPersona;
    }

    public boolean tieneEmailValido() {
        return correoElectronico != null && !correoElectronico.trim().isEmpty();
    }

    @Override
    public String toString() {
        return obtenerNombreCompleto();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Asistentes that = (Asistentes) obj;
        return correoElectronico.equals(that.correoElectronico);
    }

    @Override
    public int hashCode() {
        return correoElectronico.hashCode();
    }
}
