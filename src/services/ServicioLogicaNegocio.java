package services;

/**
 * Servicio especializado para lógica de negocio genérica
 * Evita dependencias circulares y mantiene el principio de responsabilidad única
 */
public class ServicioLogicaNegocio {
    
    private static ServicioLogicaNegocio instanciaUnica;
    
    private ServicioLogicaNegocio() {}
    
    public static ServicioLogicaNegocio obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ServicioLogicaNegocio();
        }
        return instanciaUnica;
    }
    

    public boolean puedeAlbergar(int capacidadMaxima, int cantidadPersonas) {
        if (capacidadMaxima <= 0 || cantidadPersonas < 0) {
            return false;
        }
        
        return cantidadPersonas <= capacidadMaxima;
    }
    

    public boolean esCapacidadSuficiente(int capacidadMaxima, int personasRequeridas) {
        if (capacidadMaxima <= 0 || personasRequeridas < 0) {
            return false;
        }
        
        return capacidadMaxima >= personasRequeridas;
    }
    

    public double calcularPorcentajeOcupacion(int capacidadMaxima, int personasActuales) {
        if (capacidadMaxima <= 0 || personasActuales < 0) {
            return 0.0;
        }
        
        return ((double) personasActuales / capacidadMaxima) * 100.0;
    }
    

    public boolean estaCercaDeCapacidadMaxima(int capacidadMaxima, int personasActuales, double porcentajeLimite) {
        double porcentajeActual = calcularPorcentajeOcupacion(capacidadMaxima, personasActuales);
        return porcentajeActual >= porcentajeLimite;
    }
    

    public int calcularEspacioDisponible(int capacidadMaxima, int personasActuales) {
        if (capacidadMaxima <= 0 || personasActuales < 0) {
            return 0;
        }
        
        int disponible = capacidadMaxima - personasActuales;
        return Math.max(0, disponible);
    }
    

    public boolean esApropiado(int capacidadMaxima, String tipoEvento, int personasEsperadas) {
        if (capacidadMaxima <= 0 || tipoEvento == null || personasEsperadas <= 0) {
            return false;
        }
        

        if (!puedeAlbergar(capacidadMaxima, personasEsperadas)) {
            return false;
        }
        
        return switch (tipoEvento.toLowerCase()) {
            case "conferencia" -> personasEsperadas <= (capacidadMaxima * 0.9);
            case "reunión" -> personasEsperadas <= (capacidadMaxima * 0.8);
            case "celebración" -> personasEsperadas <= (capacidadMaxima * 0.95);
            default -> personasEsperadas <= (capacidadMaxima * 0.85);
        };
    }
} 