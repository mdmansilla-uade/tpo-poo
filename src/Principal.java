import Controllers.AsistentesController;
import Controllers.EventosController;
import Controllers.SalonController;
import Models.Asistentes;
import Models.Eventos;
import Models.Salon;
import Views.PrincipalView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import services.GestorErrores;

public class Principal {

    private static final String RUTA_ARCHIVO_PARTICIPANTES = "src/resources/asistentes.txt";
    private static final String RUTA_ARCHIVO_ESPACIOS = "src/resources/salones.txt";
    private static final String RUTA_ARCHIVO_EVENTOS = "src/resources/eventos.txt";
    
    private static final GestorErrores manejadorErrores = GestorErrores.obtenerInstancia();

    public static void main(String[] args) {
        try {
            List<Salon> listaEspaciosReunion = cargarEspaciosDesdeArchivo();
            List<Asistentes> listaParticipantes = cargarParticipantesDesdeArchivo();
            List<Eventos> listaEventosActividades = cargarEventosDesdeArchivo(listaEspaciosReunion, listaParticipantes);

            EventosController gestorEventos = EventosController.getInstance();
            gestorEventos.initialize(listaEventosActividades);

            SalonController gestorEspacios = SalonController.getInstance();
            gestorEspacios.initialize(listaEspaciosReunion, gestorEventos);

            AsistentesController gestorParticipantes = AsistentesController.getInstance();
            gestorParticipantes.initialize(listaParticipantes, gestorEventos);
            
            SwingUtilities.invokeLater(() -> new PrincipalView());
            
        } catch (Exception excepcionGeneral) {
            manejadorErrores.registrarError("Error crítico al inicializar la aplicación", excepcionGeneral);
            JOptionPane.showMessageDialog(null, 
                "Error crítico al inicializar la aplicación. Revise el archivo de errores para más detalles.", 
                "Error de Inicio", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private static List<Asistentes> cargarParticipantesDesdeArchivo() {
        List<Asistentes> coleccionParticipantes = new ArrayList<>();
        
        try (BufferedReader lectorArchivo = new BufferedReader(new FileReader(RUTA_ARCHIVO_PARTICIPANTES))) {
            String lineaActual;
            int numeroLinea = 0;
            
            while ((lineaActual = lectorArchivo.readLine()) != null) {
                numeroLinea++;
                try {
                    String[] datosParticipante = lineaActual.split(";");
                    
                    if (datosParticipante.length == 4) {
                        String nombrePersona = datosParticipante[0].trim();
                        String apellidoPersona = datosParticipante[1].trim();
                        String correoElectronico = datosParticipante[2].trim();
                        String numeroTelefono = datosParticipante[3].trim();

                        // Validaciones básicas
                        if (nombrePersona.isEmpty() || apellidoPersona.isEmpty()) {
                            manejadorErrores.registrarError("Datos incompletos en participante línea " + numeroLinea + ": " + lineaActual);
                            continue;
                        }

                        Asistentes nuevoParticipante = new Asistentes(nombrePersona, apellidoPersona, correoElectronico, numeroTelefono);
                        coleccionParticipantes.add(nuevoParticipante);
                    } else {
                        manejadorErrores.registrarError("Formato incorrecto en línea " + numeroLinea + " del archivo de participantes: " + lineaActual);
                    }
                } catch (Exception excepcionLinea) {
                    manejadorErrores.registrarError("Error al procesar línea " + numeroLinea + " de participantes: " + lineaActual, excepcionLinea);
                }
            }
        } catch (IOException excepcionArchivo) {
            manejadorErrores.registrarError("Error al leer archivo de participantes", excepcionArchivo);
            throw new RuntimeException("Fallo crítico al cargar participantes", excepcionArchivo);
        }
        
        return coleccionParticipantes;
    }

    private static List<Salon> cargarEspaciosDesdeArchivo() {
        List<Salon> coleccionEspacios = new ArrayList<>();
        
        try (BufferedReader lectorArchivo = new BufferedReader(new FileReader(RUTA_ARCHIVO_ESPACIOS))) {
            String lineaActual;
            int numeroLinea = 0;
            
            while ((lineaActual = lectorArchivo.readLine()) != null) {
                numeroLinea++;
                try {
                    String[] datosEspacio = lineaActual.split(";");
                    
                    if (datosEspacio.length == 3) {
                        String denominacionEspacio = datosEspacio[0].trim();
                        int capacidadMaxima = Integer.parseInt(datosEspacio[1].trim());
                        String ubicacionFisica = datosEspacio[2].trim();

                        // Validaciones básicas
                        if (denominacionEspacio.isEmpty() || capacidadMaxima <= 0) {
                            manejadorErrores.registrarError("Datos inválidos en espacio línea " + numeroLinea + ": " + lineaActual);
                            continue;
                        }

                        Salon nuevoEspacio = new Salon(denominacionEspacio, capacidadMaxima, ubicacionFisica);
                        coleccionEspacios.add(nuevoEspacio);
                    } else {
                        manejadorErrores.registrarError("Formato incorrecto en línea " + numeroLinea + " del archivo de espacios: " + lineaActual);
                    }
                } catch (NumberFormatException excepcionNumero) {
                    manejadorErrores.registrarError("Error de formato numérico en línea " + numeroLinea + " de espacios: " + lineaActual, excepcionNumero);
                } catch (Exception excepcionLinea) {
                    manejadorErrores.registrarError("Error al procesar línea " + numeroLinea + " de espacios: " + lineaActual, excepcionLinea);
                }
            }
        } catch (IOException excepcionArchivo) {
            manejadorErrores.registrarError("Error al leer archivo de espacios", excepcionArchivo);
            throw new RuntimeException("Fallo crítico al cargar espacios", excepcionArchivo);
        }
        
        return coleccionEspacios;
    }

    private static List<Eventos> cargarEventosDesdeArchivo(List<Salon> espaciosDisponibles, List<Asistentes> participantesDisponibles) {
        List<Eventos> coleccionEventos = new ArrayList<>();
        SimpleDateFormat formateadorFecha = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        formateadorFecha.setTimeZone(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));

        try (BufferedReader lectorArchivo = new BufferedReader(new FileReader(RUTA_ARCHIVO_EVENTOS))) {
            String lineaActual;
            int numeroLinea = 0;
            
            while ((lineaActual = lectorArchivo.readLine()) != null) {
                numeroLinea++;
                try {
                    String[] datosEvento = lineaActual.split(";");
                    
                    if (datosEvento.length == 5) {
                        String denominacionEvento = datosEvento[0].trim();
                        Date fechaYHoraEvento = formateadorFecha.parse(datosEvento[1]);
                        String nombreEspacio = datosEvento[2].trim();
                        String listaParticipantesTexto = datosEvento[3].replaceAll("[\\[\\]]", "");
                        String descripcionDetallada = datosEvento[4].trim();

                        // Buscar el espacio correspondiente
                        Salon espacioAsignado = espaciosDisponibles.stream()
                                .filter(espacio -> espacio.getNombre().equals(nombreEspacio))
                                .findFirst()
                                .orElse(null);

                        if (espacioAsignado == null) {
                            manejadorErrores.registrarError("Espacio no encontrado en línea " + numeroLinea + ": " + nombreEspacio);
                            continue;
                        }

                        // Procesar lista de participantes
                        List<Asistentes> participantesEvento = new ArrayList<>();
                        if (!listaParticipantesTexto.trim().isEmpty()) {
                            List<String> nombresParticipantes = Arrays.asList(listaParticipantesTexto.split(", "));
                            
                            for (String nombreParticipante : nombresParticipantes) {
                                Asistentes participanteEncontrado = participantesDisponibles.stream()
                                        .filter(participante -> participante.toString().equals(nombreParticipante.trim()))
                                        .findFirst()
                                        .orElse(null);
                                
                                if (participanteEncontrado != null) {
                                    participantesEvento.add(participanteEncontrado);
                                } else {
                                    manejadorErrores.registrarError("Participante no encontrado en evento línea " + numeroLinea + ": " + nombreParticipante);
                                }
                            }
                        }

                        // Crear evento
                        Eventos nuevoEvento = new Eventos(fechaYHoraEvento, denominacionEvento, espacioAsignado, descripcionDetallada);
                        nuevoEvento.setAsistentes(participantesEvento);
                        coleccionEventos.add(nuevoEvento);
                        
                    } else {
                        manejadorErrores.registrarError("Formato incorrecto en línea " + numeroLinea + " del archivo de eventos: " + lineaActual);
                    }
                } catch (ParseException excepcionFecha) {
                    manejadorErrores.registrarError("Error al parsear fecha en línea " + numeroLinea + " de eventos: " + lineaActual, excepcionFecha);
                } catch (Exception excepcionLinea) {
                    manejadorErrores.registrarError("Error al procesar línea " + numeroLinea + " de eventos: " + lineaActual, excepcionLinea);
                }
            }
        } catch (IOException excepcionArchivo) {
            manejadorErrores.registrarError("Error al leer archivo de eventos", excepcionArchivo);
            throw new RuntimeException("Fallo crítico al cargar eventos", excepcionArchivo);
        }
        
        return coleccionEventos;
    }
}
