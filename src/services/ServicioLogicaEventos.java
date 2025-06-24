package services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ServicioLogicaEventos {
    
    private static ServicioLogicaEventos instanciaUnica;
    
    private ServicioLogicaEventos() {}
    
    public static ServicioLogicaEventos obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ServicioLogicaEventos();
        }
        return instanciaUnica;
    }
    
    public boolean esMismaFecha(Date fecha1, Date fecha2) {
        if (fecha1 == null || fecha2 == null) {
            return false;
        }
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        return formatoFecha.format(fecha1).equals(formatoFecha.format(fecha2));
    }
    
    public boolean estaLleno(int cantidadParticipantes, int capacidadEspacio) {
        if (capacidadEspacio <= 0) {
            return false;
        }
        return cantidadParticipantes >= capacidadEspacio;
    }
    
    public boolean puedeAgregarParticipantes(int cantidadActual, int cantidadAAgregar, int capacidadEspacio) {
        if (capacidadEspacio <= 0) {
            return true;
        }
        
        if (cantidadAAgregar < 0) {
            return false;
        }
        
        return (cantidadActual + cantidadAAgregar) <= capacidadEspacio;
    }
    
    public double calcularPorcentajeOcupacion(int cantidadParticipantes, int capacidadEspacio) {
        if (capacidadEspacio <= 0) {
            return 0.0;
        }
        
        return ((double) cantidadParticipantes / capacidadEspacio) * 100.0;
    }
    
    public boolean estaCercaDeCapacidadMaxima(int cantidadParticipantes, int capacidadEspacio, double porcentajeLimite) {
        double porcentajeActual = calcularPorcentajeOcupacion(cantidadParticipantes, capacidadEspacio);
        return porcentajeActual >= porcentajeLimite;
    }
    
    public int calcularEspacioDisponible(int cantidadParticipantes, int capacidadEspacio) {
        if (capacidadEspacio <= 0) {
            return Integer.MAX_VALUE; // Sin límite
        }
        
        int disponible = capacidadEspacio - cantidadParticipantes;
        return Math.max(0, disponible);
    }
    
    public boolean esApropiado(int cantidadParticipantes, int capacidadEspacio, String tipoEvento) {
        if (capacidadEspacio <= 0 || cantidadParticipantes < 0) {
            return false;
        }
        
        if (cantidadParticipantes > capacidadEspacio) {
            return false;
        }
        
        if (tipoEvento == null) {
            return true;
        }
        
        return switch (tipoEvento.toLowerCase()) {
            case "conferencia" -> cantidadParticipantes <= (capacidadEspacio * 0.9);
            case "reunión" -> cantidadParticipantes <= (capacidadEspacio * 0.8);
            case "celebración" -> cantidadParticipantes <= (capacidadEspacio * 0.95);
            default -> cantidadParticipantes <= (capacidadEspacio * 0.85);
        };
    }
    
    public boolean participanteYaExiste(Object participante, List<?> listaParticipantes) {
        if (participante == null || listaParticipantes == null) {
            return false;
        }
        
        return listaParticipantes.contains(participante);
    }
    
    public long calcularDiasRestantes(Date fechaEvento) {
        if (fechaEvento == null) {
            return 0;
        }
        
        Date fechaActual = new Date();
        long diferenciaMilisegundos = fechaEvento.getTime() - fechaActual.getTime();
        return diferenciaMilisegundos / (1000 * 60 * 60 * 24);
    }
    
    public boolean esEventoProximo(Date fechaEvento, int diasLimite) {
        long diasRestantes = calcularDiasRestantes(fechaEvento);
        return diasRestantes >= 0 && diasRestantes <= diasLimite;
    }
    

    public boolean puedeAlbergar(int capacidadMaxima, int cantidadPersonas) {
        if (capacidadMaxima <= 0 || cantidadPersonas < 0) {
            return false;
        }
        
        return cantidadPersonas <= capacidadMaxima;
    }
} 