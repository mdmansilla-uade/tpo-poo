package Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import services.ServicioLogicaEventos;
import services.ValidadorEventos;

public class Eventos {
    private Date fechaYHora;
    private String denominacionEvento;
    private Salon espacioAsignado;
    private List<Asistentes> listaParticipantes;
    private String descripcionDetallada;
    
    private final ValidadorEventos validador = ValidadorEventos.obtenerInstancia();
    private final ServicioLogicaEventos servicioLogica = ServicioLogicaEventos.obtenerInstancia();

    public Eventos(Date fechaYHora, String denominacionEvento, String descripcionDetallada) {
        this.fechaYHora = validador.validarFecha(fechaYHora);
        this.denominacionEvento = validador.validarDenominacion(denominacionEvento);
        this.descripcionDetallada = validador.validarDescripcion(descripcionDetallada);
        this.listaParticipantes = new ArrayList<>();
    }

    public Eventos(Date fechaYHora, String denominacionEvento, Salon espacioAsignado, String descripcionDetallada) {
        this(fechaYHora, denominacionEvento, descripcionDetallada);
        this.espacioAsignado = espacioAsignado;
    }

    public Date getFecha() {
        return fechaYHora;
    }

    public void setFecha(Date fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public String getNombre() {
        return denominacionEvento;
    }

    public void setNombre(String denominacionEvento) {
        this.denominacionEvento = validador.validarDenominacion(denominacionEvento);
    }

    public Salon getSalon() {
        return espacioAsignado;
    }

    public void setSalon(Salon espacioAsignado) {
        this.espacioAsignado = espacioAsignado;
    }

    public List<Asistentes> getAsistentes() {
        return new ArrayList<>(listaParticipantes);
    }

    public void setAsistentes(List<Asistentes> listaParticipantes) {
        this.listaParticipantes = validador.validarListaParticipantes(listaParticipantes);
    }

    public String getDescripcion() {
        return descripcionDetallada;
    }

    public void setDescripcion(String descripcionDetallada) {
        this.descripcionDetallada = validador.validarDescripcion(descripcionDetallada);
    }

    public boolean isSameDate(Date otraFecha) {
        return servicioLogica.esMismaFecha(this.fechaYHora, otraFecha);
    }

    public boolean esMismaFecha(Date otraFecha) {
        return isSameDate(otraFecha);
    }

    public void agregarParticipante(Asistentes participante) {
        validador.validarNuevoParticipante(participante, listaParticipantes);
        if (espacioAsignado != null && !puedeAgregarParticipantes(1)) {
            throw new IllegalStateException("No hay capacidad disponible en el espacio asignado");
        }
        listaParticipantes.add(participante);
    }

    public void removerParticipante(Asistentes participante) {
        if (participante == null) {
            throw new IllegalArgumentException("El participante no puede ser nulo");
        }
        if (!servicioLogica.participanteYaExiste(participante, listaParticipantes)) {
            throw new IllegalArgumentException("El participante no está registrado en este evento");
        }
        listaParticipantes.remove(participante);
    }

    public int obtenerCantidadParticipantes() {
        return listaParticipantes.size();
    }

    public boolean estaLleno() {
        return servicioLogica.estaLleno(obtenerCantidadParticipantes(), espacioAsignado.getCapacidad());
    }

    public boolean puedeAgregarParticipantes(int cantidad) {
        return servicioLogica.puedeAgregarParticipantes(
            obtenerCantidadParticipantes(), 
            cantidad, 
            espacioAsignado.getCapacidad()
        );
    }

    public boolean tieneEspacioAsignado() {
        return espacioAsignado != null;
    }
    
    public double calcularPorcentajeOcupacion() {
        return servicioLogica.calcularPorcentajeOcupacion(obtenerCantidadParticipantes(), espacioAsignado.getCapacidad());
    }
    
    public boolean estaCercaDeCapacidadMaxima(double porcentajeLimite) {
        return servicioLogica.estaCercaDeCapacidadMaxima(
            obtenerCantidadParticipantes(), 
            espacioAsignado.getCapacidad(), 
            porcentajeLimite
        );
    }
    
    public int calcularEspacioDisponible() {
        return servicioLogica.calcularEspacioDisponible(obtenerCantidadParticipantes(), espacioAsignado.getCapacidad());
    }
    
    public boolean esApropiado(String tipoEvento) {
        return servicioLogica.esApropiado(obtenerCantidadParticipantes(), espacioAsignado.getCapacidad(), tipoEvento);
    }
    
    public long calcularDiasRestantes() {
        return servicioLogica.calcularDiasRestantes(fechaYHora);
    }
    
    public boolean esEventoProximo(int diasLimite) {
        return servicioLogica.esEventoProximo(fechaYHora, diasLimite);
    }

    @Override
    public String toString() {
        return denominacionEvento;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Eventos evento = (Eventos) obj;
        return denominacionEvento.equals(evento.denominacionEvento) && fechaYHora.equals(evento.fechaYHora);
    }

    @Override
    public int hashCode() {
        int resultado = denominacionEvento.hashCode();
        resultado = 31 * resultado + fechaYHora.hashCode();
        return resultado;
    }
}
