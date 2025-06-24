package Views.Eventos;

import Controllers.EventosController;
import Models.Eventos;
import Models.Asistentes;
import Views.BaseView;
import java.awt.*;
import javax.swing.*;
import java.text.SimpleDateFormat;

public class EliminarEvento extends BaseView {
    private final EventosController eventosController = EventosController.getInstance();
    private JComboBox<Eventos> comboEvento;
    private JTextArea areaDetalles;
    private JTextArea areaImpacto;

    public EliminarEvento() {
        configurarVentanaBase("Eliminar Evento", 1000, 750);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Cancelación de Evento",
            "Esta acción eliminará permanentemente el evento del sistema"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelFormulario = crearPanelOpciones("Selección de Evento");
        
        panelFormulario.add(crearCampoSeleccion());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearPanelDetalles());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearPanelImpactoParticipantes());
        
        panelPrincipal.add(panelFormulario);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelAdvertencia = crearPanelAdvertencia();
        panelPrincipal.add(panelAdvertencia);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(crearEspaciado(20));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        agregarBarraEstado("Eliminación de Evento", "Seleccione el evento a cancelar");
        
        cargarEventos();
    }
    
    private JPanel crearCampoSeleccion() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Seleccionar Evento a Eliminar:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        comboEvento = new JComboBox<>();
        comboEvento.setPreferredSize(new Dimension(400, 35));
        comboEvento.setFont(new Font("Arial", Font.PLAIN, 13));
        comboEvento.addActionListener(e -> actualizarInformacion());
        
        JLabel ayuda = new JLabel("El evento será cancelado y todos los participantes notificados");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_PELIGRO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(comboEvento, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelDetalles() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Información del Evento:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        areaDetalles = new JTextArea(5, 30);
        areaDetalles.setFont(new Font("Arial", Font.PLAIN, 12));
        areaDetalles.setEditable(false);
        areaDetalles.setBackground(COLOR_FONDO);
        areaDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaDetalles.setText("Seleccione un evento para ver sus detalles...");
        
        JScrollPane scrollDetalles = new JScrollPane(areaDetalles);
        scrollDetalles.setBorder(BorderFactory.createEtchedBorder());
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(scrollDetalles, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelImpactoParticipantes() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Impacto en Participantes:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_PELIGRO);
        
        areaImpacto = new JTextArea(4, 30);
        areaImpacto.setFont(new Font("Arial", Font.PLAIN, 12));
        areaImpacto.setEditable(false);
        areaImpacto.setBackground(COLOR_PELIGRO.brighter().brighter().brighter());
        areaImpacto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaImpacto.setText("Seleccione un evento para analizar el impacto en participantes...");
        
        JScrollPane scrollImpacto = new JScrollPane(areaImpacto);
        scrollImpacto.setBorder(BorderFactory.createLineBorder(COLOR_PELIGRO, 2));
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(scrollImpacto, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelAdvertencia() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_PELIGRO.brighter().brighter());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_PELIGRO, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel iconoAdvertencia = new JLabel("");
        iconoAdvertencia.setFont(new Font("Arial", Font.BOLD, 24));
        iconoAdvertencia.setForeground(COLOR_PELIGRO);
        
        JTextArea textoAdvertencia = new JTextArea(
            "ADVERTENCIA CRÍTICA - CANCELACIÓN DE EVENTO:\n\n" +
            "• Esta acción eliminará permanentemente el evento del sistema\n" + 
            "• Esta acción eliminará permanentemente el evento del sistema\n" +
            "• TODOS los participantes registrados perderán sus reservas\n" +
            "• El salón quedará liberado para esa fecha\n" +
            "• Los datos del evento no podrán ser recuperados\n" +
            "• Se enviará notificación automática de cancelación\n" +
            "• Esta operación no se puede deshacer\n" +
            "• Revise cuidadosamente el impacto antes de proceder"
        );
        textoAdvertencia.setFont(new Font("Arial", Font.PLAIN, 12));
        textoAdvertencia.setForeground(COLOR_PELIGRO.darker());
        textoAdvertencia.setBackground(COLOR_PELIGRO.brighter().brighter());
        textoAdvertencia.setEditable(false);
        textoAdvertencia.setWrapStyleWord(true);
        textoAdvertencia.setLineWrap(true);
        
        panel.add(iconoAdvertencia, BorderLayout.WEST);
        panel.add(textoAdvertencia, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(COLOR_FONDO);
        
        JButton btnEliminar = crearBotonSimple("Cancelar Evento", 
            "PELIGRO: Esta acción es irreversible", COLOR_PELIGRO, e -> eliminarEvento());
        
        JButton btnVolver = crearBotonSimple("Volver al Menú", 
            "Regresa al menú de eventos", COLOR_SECUNDARIO, e -> dispose());
        
        JButton btnCancelar = crearBotonSimple("Cancelar", 
            "Cierra sin cancelar evento", COLOR_SECUNDARIO, e -> dispose());
        
        panel.add(btnEliminar);
        panel.add(btnVolver);
        panel.add(btnCancelar);
        
        return panel;
    }

    private void cargarEventos() {
        comboEvento.removeAllItems();
        comboEvento.addItem(null);
        for (Eventos evento : eventosController.obtenerTodosLosEventos()) {
            comboEvento.addItem(evento);
        }
    }
    
    private void actualizarInformacion() {
        Eventos evento = obtenerEventoSeleccionado();
        if (evento != null) {
            actualizarDetallesEvento(evento);
            analizarImpactoParticipantes(evento);
            
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            int numParticipantes = evento.getAsistentes() != null ? evento.getAsistentes().size() : 0;
            agregarBarraEstado("Seleccionado: " + evento.getNombre(), 
                "Fecha: " + formato.format(evento.getFecha()) + " | " + numParticipantes + " participantes afectados");
        } else {
            areaDetalles.setText("Seleccione un evento para ver sus detalles...");
            areaImpacto.setText("Seleccione un evento para analizar el impacto en participantes...");
            agregarBarraEstado("Eliminación de Evento", "Seleccione el evento a cancelar");
        }
    }
    
    private void actualizarDetallesEvento(Eventos evento) {
        StringBuilder detalles = new StringBuilder();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        detalles.append("INFORMACIÓN DEL EVENTO\n");
        detalles.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        detalles.append("Nombre: ").append(evento.getNombre()).append("\n");
        detalles.append("Fecha: ").append(formato.format(evento.getFecha())).append("\n");
        detalles.append("Salón: ").append(evento.getSalon().getNombre()).append("\n");
        detalles.append("Capacidad del salón: ").append(evento.getSalon().getCapacidad()).append(" personas\n");
        detalles.append("Ubicación: ").append(evento.getSalon().getUbicacion()).append("\n");
        detalles.append("Descripción: ").append(evento.getDescripcion()).append("\n\n");
        
        if (evento.getAsistentes() != null && !evento.getAsistentes().isEmpty()) {
            detalles.append("PARTICIPANTES REGISTRADOS (").append(evento.getAsistentes().size()).append("):\n");
            for (int i = 0; i < evento.getAsistentes().size() && i < 5; i++) {
                Asistentes asistente = evento.getAsistentes().get(i);
                detalles.append("• ").append(asistente.getNombre()).append(" ").append(asistente.getApellido());
                if (asistente.getEmail() != null && !asistente.getEmail().isEmpty()) {
                    detalles.append(" (").append(asistente.getEmail()).append(")");
                }
                detalles.append("\n");
            }
            if (evento.getAsistentes().size() > 5) {
                detalles.append("• ... y ").append(evento.getAsistentes().size() - 5).append(" participantes más\n");
            }
        } else {
            detalles.append("PARTICIPANTES: Ningún participante registrado");
        }
        
        areaDetalles.setText(detalles.toString());
    }
    
    private void analizarImpactoParticipantes(Eventos evento) {
        StringBuilder analisis = new StringBuilder();
        
        analisis.append("ANÁLISIS DE IMPACTO - CANCELACIÓN\n");
        analisis.append("═══════════════════════════════════════════════\n\n");
        
        if (evento.getAsistentes() == null || evento.getAsistentes().isEmpty()) {
            analisis.append("IMPACTO BAJO EN PARTICIPANTES:\n");
            analisis.append("• No hay participantes registrados en este evento\n");
            analisis.append("• La cancelación no afectará a ningún usuario\n");
            analisis.append("• Solo se liberará el salón para esa fecha\n\n");
            analisis.append("RECOMENDACIÓN: Cancelación segura - Sin impacto en participantes");
        } else {
            int totalParticipantes = evento.getAsistentes().size();
            
            analisis.append("IMPACTO ALTO EN PARTICIPANTES:\n");
            analisis.append("• ").append(totalParticipantes).append(" participantes perderán sus reservas\n");
            
            // Análisis de contactos para notificación
            long participantesConEmail = evento.getAsistentes().stream()
                .filter(asistente -> asistente.getEmail() != null && !asistente.getEmail().isEmpty())
                .count();
                
            long participantesSinEmail = totalParticipantes - participantesConEmail;
            
            analisis.append("• ").append(participantesConEmail).append(" pueden ser notificados por email\n");
            if (participantesSinEmail > 0) {
                analisis.append("• ").append(participantesSinEmail).append(" SIN email registrado (notificación manual)\n");
            }
            
            analisis.append("\nPARTICIPANTES A NOTIFICAR:\n");
            for (int i = 0; i < evento.getAsistentes().size() && i < 5; i++) {
                Asistentes asistente = evento.getAsistentes().get(i);
                analisis.append("• ").append(asistente.getNombre()).append(" ").append(asistente.getApellido());
                if (asistente.getEmail() != null && !asistente.getEmail().isEmpty()) {
                    analisis.append(" Email disponible");
                } else {
                    analisis.append(" Sin email - Contacto manual");
                }
                analisis.append("\n");
            }
            
            if (evento.getAsistentes().size() > 5) {
                analisis.append("• ... y ").append(evento.getAsistentes().size() - 5).append(" participantes más\n");
            }
            
            analisis.append("\nRECOMENDACIÓN: ");
            if (totalParticipantes > 10) {
                analisis.append("ALTO RIESGO - Considere reprogramar en lugar de cancelar");
            } else if (participantesSinEmail > totalParticipantes / 2) {
                analisis.append("ATENCIÓN - Muchos participantes sin email para notificar");
            } else {
                analisis.append("Proceda con precaución - Notifique a todos los participantes");
            }
        }
        
        areaImpacto.setText(analisis.toString());
    }

    private void eliminarEvento() {
        try {
            Eventos eventoSeleccionado = obtenerEventoSeleccionado();
            if (eventoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor seleccione un evento para eliminar.", 
                    "Evento No Seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int totalParticipantes = eventoSeleccionado.getAsistentes() != null ? 
                eventoSeleccionado.getAsistentes().size() : 0;
            
            StringBuilder mensajeConfirmacion = new StringBuilder();
            
            if (totalParticipantes == 0) {
                mensajeConfirmacion.append("CONFIRMACIÓN DE CANCELACIÓN\n\n")
                    .append("Está a punto de cancelar permanentemente:\n\n")
                    .append("Evento: ").append(eventoSeleccionado.getNombre()).append("\n")
                    .append("Fecha: ").append(new SimpleDateFormat("dd/MM/yyyy").format(eventoSeleccionado.getFecha())).append("\n")
                    .append("Salón: ").append(eventoSeleccionado.getSalon().getNombre()).append("\n\n")
                    .append("IMPACTO: Ningún participante será afectado\n\n")
                    .append("¿Está seguro de cancelar este evento?");
            } else {
                long participantesConEmail = eventoSeleccionado.getAsistentes().stream()
                    .filter(asistente -> asistente.getEmail() != null && !asistente.getEmail().isEmpty())
                    .count();
                
                mensajeConfirmacion.append("CONFIRMACIÓN DE CANCELACIÓN CRÍTICA\n\n")
                    .append("PELIGRO: Esta acción tendrá ALTO IMPACTO\n\n")
                    .append("Evento: ").append(eventoSeleccionado.getNombre()).append("\n")
                    .append("Fecha: ").append(new SimpleDateFormat("dd/MM/yyyy").format(eventoSeleccionado.getFecha())).append("\n")
                    .append("Salón: ").append(eventoSeleccionado.getSalon().getNombre()).append("\n\n")
                    .append("IMPACTO SEVERO:\n")
                    .append("• ").append(totalParticipantes).append(" participantes perderán sus reservas\n")
                    .append("• ").append(participantesConEmail).append(" serán notificados por email\n");
                
                if (participantesConEmail < totalParticipantes) {
                    mensajeConfirmacion.append("• ").append(totalParticipantes - participantesConEmail)
                        .append(" requerirán notificación MANUAL\n");
                }
                
                mensajeConfirmacion.append("• El salón quedará liberado para esa fecha\n\n")
                    .append("¿Está COMPLETAMENTE SEGURO de cancelar?\n")
                    .append("Esta acción NO se puede deshacer.");
            }
            
            int tipoMensaje = totalParticipantes == 0 ? JOptionPane.QUESTION_MESSAGE : JOptionPane.ERROR_MESSAGE;
            int opcion = JOptionPane.showConfirmDialog(this, 
                mensajeConfirmacion.toString(), 
                "Confirmar Cancelación de Evento", 
                JOptionPane.YES_NO_OPTION,
                tipoMensaje);
                
            if (opcion == JOptionPane.YES_OPTION) {
                eventosController.cancelarEvento(eventoSeleccionado);
                
                String mensajeExito;
                if (totalParticipantes == 0) {
                    mensajeExito = "Evento cancelado exitosamente\n\n" +
                        "El evento " + eventoSeleccionado.getNombre() + 
                        " ha sido eliminado del sistema sin afectar participantes.";
                } else {
                    mensajeExito = "Evento cancelado con impacto\n\n" +
                        "El evento " + eventoSeleccionado.getNombre() + 
                        " ha sido cancelado.\n" + totalParticipantes + 
                        " participantes han sido afectados.\n\n" +
                        "ACCIÓN REQUERIDA: Notifique manualmente a los participantes sin email.";
                }
                
                JOptionPane.showMessageDialog(this, 
                    mensajeExito, 
                    "Cancelación Completada", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cancelar el evento:\n" + ex.getMessage(), 
                "Error de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Eventos obtenerEventoSeleccionado() {
        return (Eventos) comboEvento.getSelectedItem();
    }
}
