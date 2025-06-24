package Controllers;

import Models.Salon;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import services.GestorErrores;

public class SalonController {
    private static SalonController instanciaUnica;
    private List<Salon> coleccionEspacios;
    private EventosController gestorEventos;
    private static final String rutaArchivoEspacios = "src/resources/salones.txt";
    private final GestorErrores manejadorErrores;

    private SalonController() {
        this.manejadorErrores = GestorErrores.obtenerInstancia();
    }

    public static SalonController getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new SalonController();
        }
        return instanciaUnica;
    }

    public void initialize(List<Salon> listaEspacios, EventosController controladorEventos) {
        this.coleccionEspacios = new ArrayList<>(listaEspacios);
        this.gestorEventos = controladorEventos;
    }

    public void persistirEspacios() {
        try (BufferedWriter escritorArchivo = new BufferedWriter(new FileWriter(rutaArchivoEspacios))) {
            for (Salon espacioActual : coleccionEspacios) {
                String lineaFormateada = String.format("%s;%s;%s",
                        espacioActual.getNombre(),
                        espacioActual.getCapacidad(),
                        espacioActual.getUbicacion());
                escritorArchivo.write(lineaFormateada);
                escritorArchivo.newLine();
            }
        } catch (IOException excepcion) {
            manejadorErrores.registrarError("Error al persistir espacios en archivo", excepcion);
            throw new RuntimeException("Fallo en la persistencia de datos de espacios", excepcion);
        }
    }

    public void registrarNuevoEspacio(String denominacionEspacio, int capacidadMaxima, String ubicacionFisica) {
        try {
            boolean nombreExistente = coleccionEspacios.stream()
                    .anyMatch(espacio -> espacio.getNombre().equalsIgnoreCase(denominacionEspacio != null ? denominacionEspacio.trim() : ""));
            
            if (nombreExistente) {
                throw new IllegalArgumentException("Ya existe un espacio con esta denominación");
            }

            Salon nuevoEspacio = new Salon(denominacionEspacio, capacidadMaxima, ubicacionFisica);
            coleccionEspacios.add(nuevoEspacio);
            persistirEspacios();
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al registrar nuevo espacio: " + denominacionEspacio, excepcion);
            throw excepcion;
        }
    }

    public void actualizarInformacionEspacio(Salon espacioAModificar, String nuevaDenominacion, 
                                           int nuevaCapacidad, String nuevaUbicacion) {
        try {
            if (verificarEspacioEnUso(espacioAModificar)) {
                throw new IllegalStateException("No se puede modificar un espacio que está asignado a eventos");
            }

            boolean nombreConflicto = coleccionEspacios.stream()
                    .anyMatch(e -> !e.equals(espacioAModificar) && 
                             e.getNombre().equalsIgnoreCase(nuevaDenominacion != null ? nuevaDenominacion.trim() : ""));
            
            if (nombreConflicto) {
                throw new IllegalArgumentException("Ya existe otro espacio con esta denominación");
            }

            espacioAModificar.setNombre(nuevaDenominacion);
            espacioAModificar.setCapacidad(nuevaCapacidad);
            espacioAModificar.setUbicacion(nuevaUbicacion);
            persistirEspacios();
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al actualizar espacio: " + espacioAModificar.toString(), excepcion);
            throw excepcion;
        }
    }

    public void eliminarEspacio(Salon espacioAEliminar) {
        try {
            if (verificarEspacioEnUso(espacioAEliminar)) {
                throw new IllegalStateException("No se puede eliminar un espacio que está asignado a eventos");
            }

            boolean espacioEliminado = coleccionEspacios.remove(espacioAEliminar);
            if (!espacioEliminado) {
                throw new IllegalArgumentException("El espacio no existe en la colección");
            }
            
            persistirEspacios();
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al eliminar espacio: " + espacioAEliminar.toString(), excepcion);
            throw excepcion;
        }
    }

    public List<Salon> obtenerEspaciosDisponibles(Date fechaConsulta) {
        try {
            return coleccionEspacios.stream()
                    .filter(espacio -> gestorEventos.obtenerTodosLosEventos().stream()
                            .noneMatch(evento -> evento.esMismaFecha(fechaConsulta) && 
                                       evento.getSalon().equals(espacio)))
                    .collect(Collectors.toList());
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al obtener espacios disponibles para fecha", excepcion);
            return new ArrayList<>();
        }
    }


    public List<Salon> obtenerTodosLosEspacios() {
        return new ArrayList<>(coleccionEspacios);
    }


    public boolean verificarEspacioEnUso(Salon espacioAVerificar) {
        try {
            return gestorEventos.obtenerTodosLosEventos().stream()
                    .anyMatch(evento -> evento.getSalon().equals(espacioAVerificar));
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al verificar uso del espacio", excepcion);
            return false;
        }
    }


    public List<Salon> buscarEspaciosPorCapacidad(int capacidadMinima) {
        try {
            return coleccionEspacios.stream()
                    .filter(espacio -> espacio.puedeAlbergar(capacidadMinima))
                    .collect(Collectors.toList());
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al buscar espacios por capacidad", excepcion);
            return new ArrayList<>();
        }
    }


    public List<Salon> buscarEspaciosPorUbicacion(String criterioBusqueda) {
        try {
            if (criterioBusqueda == null || criterioBusqueda.trim().isEmpty()) {
                return obtenerTodosLosEspacios();
            }
            
            String criterioLimpio = criterioBusqueda.toLowerCase().trim();
            return coleccionEspacios.stream()
                    .filter(espacio -> espacio.getUbicacion().toLowerCase().contains(criterioLimpio))
                    .collect(Collectors.toList());
        } catch (Exception excepcion) {
            manejadorErrores.registrarError("Error al buscar espacios por ubicación: " + criterioBusqueda, excepcion);
            return new ArrayList<>();
        }
    }
}
