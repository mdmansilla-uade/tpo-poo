package Models;

import services.ServicioLogicaNegocio;
import services.ValidadorSalon;

public class Salon {
    private String denominacionEspacio;
    private int capacidadMaxima;
    private String ubicacionFisica;
    
    private final ValidadorSalon validador = ValidadorSalon.obtenerInstancia();
    private final ServicioLogicaNegocio servicioLogica = ServicioLogicaNegocio.obtenerInstancia();

    public Salon(String denominacionEspacio, int capacidadMaxima, String ubicacionFisica) {
        this.denominacionEspacio = validador.validarDenominacion(denominacionEspacio);
        this.capacidadMaxima = validador.validarCapacidad(capacidadMaxima);
        this.ubicacionFisica = validador.validarUbicacion(ubicacionFisica);
    }

    public String getNombre() {
        return denominacionEspacio;
    }

    public void setNombre(String denominacionEspacio) {
        this.denominacionEspacio = validador.validarDenominacion(denominacionEspacio);
    }

    public int getCapacidad() {
        return capacidadMaxima;
    }

    public void setCapacidad(int capacidadMaxima) {
        this.capacidadMaxima = validador.validarCapacidad(capacidadMaxima);
    }

    public String getUbicacion() {
        return ubicacionFisica;
    }

    public void setUbicacion(String ubicacionFisica) {
        this.ubicacionFisica = validador.validarUbicacion(ubicacionFisica);
    }

    public boolean puedeAlbergar(int cantidadPersonas) {
        return servicioLogica.puedeAlbergar(capacidadMaxima, cantidadPersonas);
    }

    public boolean esCapacidadSuficiente(int personasRequeridas) {
        return servicioLogica.esCapacidadSuficiente(capacidadMaxima, personasRequeridas);
    }
    
    public double calcularPorcentajeOcupacion(int personasActuales) {
        return servicioLogica.calcularPorcentajeOcupacion(capacidadMaxima, personasActuales);
    }
    
    public boolean estaCercaDeCapacidadMaxima(int personasActuales, double porcentajeLimite) {
        return servicioLogica.estaCercaDeCapacidadMaxima(capacidadMaxima, personasActuales, porcentajeLimite);
    }
    
    public int calcularEspacioDisponible(int personasActuales) {
        return servicioLogica.calcularEspacioDisponible(capacidadMaxima, personasActuales);
    }
    
    public boolean esApropiado(String tipoEvento, int personasEsperadas) {
        return servicioLogica.esApropiado(capacidadMaxima, tipoEvento, personasEsperadas);
    }

    @Override
    public String toString() {
        return denominacionEspacio;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Salon salon = (Salon) obj;
        return denominacionEspacio.equals(salon.denominacionEspacio);
    }

    @Override
    public int hashCode() {
        return denominacionEspacio.hashCode();
    }
}
