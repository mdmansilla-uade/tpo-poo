package Controllers;

import Models.Asistentes;
import Models.Eventos;
import Models.Salon;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import services.GestorErrores;

public class EventosController {
    private static EventosController instanciaUnica;
    private List<Eventos> coleccionEventos;
    private final String rutaArchivoEventos = "src/resources/eventos.txt";
    private final GestorErrores manejadorErrores;

    private EventosController() {
        this.manejadorErrores = GestorErrores.obtenerInstancia();
    }

    public static EventosController getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new EventosController();
        }
        return instanciaUnica;
    }

    public void initialize(List<Eventos> listaEventos) {
        this.coleccionEventos = new ArrayList<>(listaEventos);
    }

    public void persistirEventos() {
        try (BufferedWriter escritorArchivo = new BufferedWriter(new FileWriter(rutaArchivoEventos))) {
            for (Eventos eventoActual : coleccionEventos) {
                String lineaFormateada = String.format("%s;%s;%s;%s;%s",
                        eventoActual.getNombre(),
                        eventoActual.getFecha(),
                        eventoActual.getSalon(),
                        eventoActual.getAsistentes(),
                        eventoActual.getDescripcion());
                escritorArchivo.write(lineaFormateada);
                escritorArchivo.newLine();
            }
        } catch (IOException excepcion) {
            manejadorErrores.registrarError("Error al persistir eventos en archivo", excepcion);
            throw new RuntimeException("Fallo en la persistencia de datos", excepcion);
        }
    }

    public void crearNuevoEvento(Date fechaYHora, String denominacionEvento, Salon espacioReunion, String detallesEvento) {
        try {
            Eventos nuevoEvento = new Eventos(fechaYHora, denominacionEvento, espacioReunion, detallesEvento);
            coleccionEventos.add(nuevoEvento);
            persistirEventos();
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al crear nuevo evento: " + denominacionEvento, excepcion);
            throw excepcion;
        }
    }

    public void modificarEventoExistente(Eventos eventoAModificar, String nuevaDenominacion, Date nuevaFecha, 
                                       Salon nuevoEspacio, String nuevaDescripcion) {
        try {
            eventoAModificar.setNombre(nuevaDenominacion);
            eventoAModificar.setFecha(nuevaFecha);
            eventoAModificar.setSalon(nuevoEspacio);
            eventoAModificar.setDescripcion(nuevaDescripcion);
            persistirEventos();
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al modificar evento: " + eventoAModificar.getNombre(), excepcion);
            throw excepcion;
        }
    }

    public void cancelarEvento(Eventos eventoACancelar) {
        try {
            boolean eventoEliminado = coleccionEventos.remove(eventoACancelar);
            if (!eventoEliminado) {
                throw new IllegalArgumentException("El evento no existe en la colección");
            }
            persistirEventos();
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al cancelar evento: " + eventoACancelar.getNombre(), excepcion);
            throw excepcion;
        }
    }

    public List<Eventos> obtenerTodosLosEventos() {
        return new ArrayList<>(coleccionEventos);
    }

    public void inscribirParticipanteEnEvento(Eventos eventoDestino, Asistentes participante) {
        try {
            if (verificarCapacidadCompleta(eventoDestino)) {
                throw new IllegalStateException("El evento ha alcanzado su capacidad máxima");
            }
            
            if (eventoDestino.getAsistentes().contains(participante)) {
                throw new IllegalArgumentException("El participante ya está inscrito en este evento");
            }
            
            eventoDestino.agregarParticipante(participante);
            persistirEventos();
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al inscribir participante: " + participante.obtenerNombreCompleto(), excepcion);
            throw excepcion;
        }
    }

    public Boolean verificarCapacidadCompleta(Eventos eventoAVerificar) {
        try {
            return eventoAVerificar.estaLleno();
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al verificar capacidad del evento", excepcion);
            return false;
        }
    }

    public List<Eventos> buscarEventosPorFecha(Date fechaBusqueda) {
        try {
            return coleccionEventos.stream()
                    .filter(evento -> evento.esMismaFecha(fechaBusqueda))
                    .collect(Collectors.toList());
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al buscar eventos por fecha", excepcion);
            return new ArrayList<>();
        }
    }

    public void desinscribirParticipanteDelEvento(Eventos eventoOrigen, Asistentes participante) {
        try {
            if (!eventoOrigen.getAsistentes().contains(participante)) {
                manejadorErrores.registrarError("Intento de desinscribir participante que no estaba inscrito: " + participante.obtenerNombreCompleto());
                throw new IllegalArgumentException("El participante no está inscrito en este evento");
            }
            eventoOrigen.removerParticipante(participante);
            persistirEventos();
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al desinscribir participante: " + participante.obtenerNombreCompleto(), excepcion);
            throw excepcion;
        }
    }
}
