package services;

import java.util.Date;
import java.util.List;

public class ValidadorEventos {
    
    private static ValidadorEventos instanciaUnica;
    
    private ValidadorEventos() {}
    
    public static ValidadorEventos obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ValidadorEventos();
        }
        return instanciaUnica;
    }
    
    public String validarDenominacion(String denominacion) {
        if (denominacion == null || denominacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La denominación del evento no puede estar vacía");
        }
        
        String denominacionLimpia = denominacion.trim();
        
        if (denominacionLimpia.length() < 3) {
            throw new IllegalArgumentException("La denominación debe tener al menos 3 caracteres");
        }
        
        if (denominacionLimpia.length() > 100) {
            throw new IllegalArgumentException("La denominación no puede exceder los 100 caracteres");
        }
        if (!denominacionLimpia.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s\\-\\.\\,\\:]+$")) {
            throw new IllegalArgumentException("La denominación contiene caracteres no válidos");
        }
        
        return denominacionLimpia;
    }
    

    public Date validarFecha(Date fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha del evento no puede estar vacía");
        }
        
        Date fechaActual = new Date();
        if (fecha.before(fechaActual)) {
            throw new IllegalArgumentException("La fecha del evento no puede ser anterior a la fecha actual");
        }
        
        long cincoAniosEnMs = 5L * 365 * 24 * 60 * 60 * 1000;
        Date fechaLimite = new Date(fechaActual.getTime() + cincoAniosEnMs);
        if (fecha.after(fechaLimite)) {
            throw new IllegalArgumentException("La fecha del evento no puede ser más de 5 años en el futuro");
        }
        
        return fecha;
    }
    

    public String validarDescripcion(String descripcion) {
        if (descripcion == null) {
            return "";
        }
        
        String descripcionLimpia = descripcion.trim();
        
        if (descripcionLimpia.length() > 500) {
            throw new IllegalArgumentException("La descripción no puede exceder los 500 caracteres");
        }
        
        return descripcionLimpia;
    }
    

    public <T> List<T> validarListaParticipantes(List<T> lista) {
        if (lista == null) {
            return new java.util.ArrayList<>();
        }
        return new java.util.ArrayList<>(lista);
    }
    

    public void validarNuevoParticipante(Object participante, List<?> participantesExistentes) {
        if (participante == null) {
            throw new IllegalArgumentException("El participante no puede ser nulo");
        }
        
        if (participantesExistentes != null && participantesExistentes.contains(participante)) {
            throw new IllegalArgumentException("El participante ya está registrado en este evento");
        }
    }
    

    public void validarDatosEvento(String denominacion, Date fecha, String descripcion) {
        validarDenominacion(denominacion);
        validarFecha(fecha);
        validarDescripcion(descripcion);
    }
} 