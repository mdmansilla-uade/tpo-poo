package Controllers;

import Models.Asistentes;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import services.GestorErrores;

public class AsistentesController {
    private static AsistentesController instanciaUnica;
    private List<Asistentes> coleccionParticipantes;
    private EventosController controladorEventos;
    private static final String rutaArchivoParticipantes = "src/resources/asistentes.txt";
    private final GestorErrores manejadorErrores;

    private AsistentesController() {
        this.manejadorErrores = GestorErrores.obtenerInstancia();
    }

    public static AsistentesController getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new AsistentesController();
        }
        return instanciaUnica;
    }

    public void initialize(List<Asistentes> listaParticipantes, EventosController gestorEventos) {
        this.coleccionParticipantes = new ArrayList<>(listaParticipantes);
        this.controladorEventos = gestorEventos;
    }

    public void persistirParticipantes() {
        try (BufferedWriter escritorArchivo = new BufferedWriter(new FileWriter(rutaArchivoParticipantes))) {
            for (Asistentes participanteActual : coleccionParticipantes) {
                String lineaFormateada = String.format("%s;%s;%s;%s",
                        participanteActual.getNombre(),
                        participanteActual.getApellido(),
                        participanteActual.getEmail(),
                        participanteActual.getTelefono());
                escritorArchivo.write(lineaFormateada);
                escritorArchivo.newLine();
            }
        } catch (IOException excepcion) {
            manejadorErrores.registrarError("Error al persistir participantes en archivo", excepcion);
            throw new RuntimeException("Fallo en la persistencia de datos de participantes", excepcion);
        }
    }

    public void registrarNuevoParticipante(String nombrePersona, String apellidoPersona, 
                                         String correoElectronico, String numeroTelefono) {
        try {
            boolean emailExistente = coleccionParticipantes.stream()
                    .anyMatch(p -> p.getEmail().equalsIgnoreCase(correoElectronico != null ? correoElectronico.trim() : ""));
            
            if (emailExistente) {
                throw new IllegalArgumentException("Ya existe un participante con este email");
            }
            Asistentes nuevoParticipante = new Asistentes(nombrePersona, apellidoPersona, correoElectronico, numeroTelefono);
            coleccionParticipantes.add(nuevoParticipante);
            persistirParticipantes();
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al registrar nuevo participante: " + nombrePersona + " " + apellidoPersona, excepcion);
            throw excepcion;
        }
    }

    public void actualizarInformacionParticipante(Asistentes participanteAModificar, String nuevoNombre, 
                                                String nuevoApellido, String nuevoEmail, String nuevoTelefono) {
        try {
            boolean emailConflicto = coleccionParticipantes.stream()
                    .anyMatch(p -> !p.equals(participanteAModificar) && 
                             p.getEmail().equalsIgnoreCase(nuevoEmail != null ? nuevoEmail.trim() : ""));
            
            if (emailConflicto) {
                throw new IllegalArgumentException("Ya existe otro participante con este email");
            }

            participanteAModificar.setNombre(nuevoNombre);
            participanteAModificar.setApellido(nuevoApellido);
            participanteAModificar.setEmail(nuevoEmail);
            participanteAModificar.setTelefono(nuevoTelefono);
            
            persistirParticipantes();
            
            if (controladorEventos != null) {
                controladorEventos.persistirEventos();
            }
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al actualizar participante: " + participanteAModificar.obtenerNombreCompleto(), excepcion);
            throw excepcion;
        }
    }

    public void eliminarParticipante(Asistentes participanteAEliminar) {
        try {
            boolean participanteEnEventos = controladorEventos.obtenerTodosLosEventos().stream()
                    .anyMatch(evento -> evento.getAsistentes() != null && 
                             evento.getAsistentes().contains(participanteAEliminar));

            if (participanteEnEventos) {
                throw new IllegalStateException("No se puede eliminar un participante que está inscrito en eventos. " +
                                              "Desinscríbalo de todos los eventos primero.");
            }

            boolean participanteEliminado = coleccionParticipantes.remove(participanteAEliminar);
            if (!participanteEliminado) {
                throw new IllegalArgumentException("El participante no existe en la colección");
            }
            
            persistirParticipantes();
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al eliminar participante: " + participanteAEliminar.obtenerNombreCompleto(), excepcion);
            throw excepcion;
        }
    }

    public List<Asistentes> obtenerTodosLosParticipantes() {
        return new ArrayList<>(coleccionParticipantes);
    }

    public List<Asistentes> buscarParticipantesPorNombre(String criterioBusqueda) {
        try {
            if (criterioBusqueda == null || criterioBusqueda.trim().isEmpty()) {
                return obtenerTodosLosParticipantes();
            }
            
            String criterioLimpio = criterioBusqueda.toLowerCase().trim();
            return coleccionParticipantes.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(criterioLimpio) ||
                               p.getApellido().toLowerCase().contains(criterioLimpio))
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al buscar participantes por nombre: " + criterioBusqueda, excepcion);
            return new ArrayList<>();
        }
    }

    public boolean puedeEliminarParticipante(Asistentes participante) {
        try {
            return controladorEventos.obtenerTodosLosEventos().stream()
                    .noneMatch(evento -> evento.getAsistentes() != null && 
                              evento.getAsistentes().contains(participante));
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al validar eliminación de participante", excepcion);
            return false;
        }
    }
}
