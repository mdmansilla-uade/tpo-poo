package Views.Eventos;

import Controllers.AsistentesController;
import Controllers.EventosController;
import Models.Asistentes;
import Models.Eventos;
import Views.BaseView;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import java.text.SimpleDateFormat;

public class RegistrarAsistente extends BaseView {
    private final EventosController eventosController = EventosController.getInstance();
    private final AsistentesController asistentesController = AsistentesController.getInstance();
    private JComboBox<Eventos> comboEvento;
    private JComboBox<Asistentes> comboAsistente;
    private JTextArea areaInfoEvento;
    private JTextArea areaInfoAsistente;
    private JLabel lblCapacidad;
    private JProgressBar progressCapacidad;

    public RegistrarAsistente() {
        configurarVentanaBase("Registrar Participante", 1000, 800);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Registro de Participante",
            "Inscriba participantes en eventos disponibles del sistema"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelFormulario = crearPanelOpciones("Selección de Evento y Participante");
        
        panelFormulario.add(crearCampoSeleccionEvento());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearPanelCapacidad());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearCampoSeleccionAsistente());
        
        panelPrincipal.add(panelFormulario);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelInformacion = crearPanelInformacion();
        panelPrincipal.add(panelInformacion);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(crearEspaciado(20));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        agregarBarraEstado("Registro de Participante", "Seleccione un evento para comenzar");
        
        cargarEventos();
    }
    
    private JPanel crearCampoSeleccionEvento() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Seleccionar Evento:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        comboEvento = new JComboBox<>();
        comboEvento.setPreferredSize(new Dimension(500, 35));
        comboEvento.setFont(new Font("Arial", Font.PLAIN, 13));
        comboEvento.addActionListener(e -> actualizarEventoSeleccionado());
        
        JLabel ayuda = new JLabel("Seleccione el evento al cual desea registrar un participante");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(comboEvento, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelCapacidad() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Estado de Capacidad",
            0, 0,
            new Font("Arial", Font.BOLD, 12),
            COLOR_TEXTO_TITULO
        ));
        
        lblCapacidad = new JLabel("Capacidad: Sin evento seleccionado");
        lblCapacidad.setFont(new Font("Arial", Font.BOLD, 13));
        lblCapacidad.setForeground(COLOR_TEXTO_TITULO);
        
        progressCapacidad = new JProgressBar(0, 100);
        progressCapacidad.setStringPainted(true);
        progressCapacidad.setString("0%");
        progressCapacidad.setPreferredSize(new Dimension(400, 25));
        progressCapacidad.setForeground(COLOR_PRINCIPAL);
        
        JPanel panelProgress = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelProgress.setBackground(COLOR_PANEL);
        panelProgress.add(progressCapacidad);
        
        panel.add(lblCapacidad, BorderLayout.NORTH);
        panel.add(panelProgress, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearCampoSeleccionAsistente() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Seleccionar Participante Disponible:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        comboAsistente = new JComboBox<>();
        comboAsistente.setPreferredSize(new Dimension(500, 35));
        comboAsistente.setFont(new Font("Arial", Font.PLAIN, 13));
        comboAsistente.addActionListener(e -> actualizarAsistenteSeleccionado());
        
        JLabel ayuda = new JLabel("Solo se muestran participantes que NO están registrados en este evento");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(comboAsistente, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelInformacion() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));
        panel.setBackground(COLOR_FONDO);
        
        JPanel panelEvento = new JPanel(new BorderLayout(10, 5));
        panelEvento.setBackground(COLOR_PANEL);
        panelEvento.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Información del Evento",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            COLOR_TEXTO_TITULO
        ));
        
        areaInfoEvento = new JTextArea(8, 25);
        areaInfoEvento.setFont(new Font("Arial", Font.PLAIN, 12));
        areaInfoEvento.setEditable(false);
        areaInfoEvento.setBackground(COLOR_FONDO);
        areaInfoEvento.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaInfoEvento.setText("Seleccione un evento para ver su información...");
        
        JScrollPane scrollEvento = new JScrollPane(areaInfoEvento);
        scrollEvento.setBorder(BorderFactory.createEtchedBorder());
        
        panelEvento.add(scrollEvento, BorderLayout.CENTER);
        
        JPanel panelAsistente = new JPanel(new BorderLayout(10, 5));
        panelAsistente.setBackground(COLOR_PANEL);
        panelAsistente.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Información del Participante",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            COLOR_TEXTO_TITULO
        ));
        
        areaInfoAsistente = new JTextArea(8, 25);
        areaInfoAsistente.setFont(new Font("Arial", Font.PLAIN, 12));
        areaInfoAsistente.setEditable(false);
        areaInfoAsistente.setBackground(COLOR_FONDO);
        areaInfoAsistente.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaInfoAsistente.setText("Seleccione un participante para ver su información...");
        
        JScrollPane scrollAsistente = new JScrollPane(areaInfoAsistente);
        scrollAsistente.setBorder(BorderFactory.createEtchedBorder());
        
        panelAsistente.add(scrollAsistente, BorderLayout.CENTER);
        
        panel.add(panelEvento);
        panel.add(panelAsistente);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(COLOR_FONDO);
        
        JButton btnRegistrar = crearBotonSimple("Registrar Participante", 
            "Inscribe al participante en el evento", COLOR_PRINCIPAL, e -> registrarAsistente());
        
        JButton btnLimpiar = crearBotonSimple("Limpiar Selección", 
            "Reinicia las selecciones", COLOR_SECUNDARIO, e -> limpiarSelecciones());
        
        JButton btnVolver = crearBotonSimple("Volver al Menú", 
            "Regresa al menú de eventos", COLOR_SECUNDARIO, e -> dispose());
        
        JButton btnCancelar = crearBotonSimple("Cancelar", 
            "Cierra sin registrar", COLOR_SECUNDARIO, e -> dispose());
        
        panel.add(btnRegistrar);
        panel.add(btnLimpiar);
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
    
    private void actualizarEventoSeleccionado() {
        Eventos evento = (Eventos) comboEvento.getSelectedItem();
        if (evento != null) {
            actualizarInformacionEvento(evento);
            
            actualizarCapacidad(evento);
            
            cargarAsistentes(evento);
            
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            int registrados = evento.getAsistentes() != null ? evento.getAsistentes().size() : 0;
            int capacidad = evento.getSalon().getCapacidad();
            agregarBarraEstado("Evento: " + evento.getNombre(), 
                "Fecha: " + formato.format(evento.getFecha()) + " | Ocupación: " + registrados + "/" + capacidad);
        } else {
            limpiarInformacion();
        }
    }
    
    private void actualizarAsistenteSeleccionado() {
        Asistentes asistente = (Asistentes) comboAsistente.getSelectedItem();
        if (asistente != null) {
            actualizarInformacionAsistente(asistente);
        } else {
            areaInfoAsistente.setText("Seleccione un participante para ver su información...");
        }
    }
    
    private void actualizarInformacionEvento(Eventos evento) {
        StringBuilder info = new StringBuilder();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        info.append("INFORMACIÓN DEL EVENTO\n");
        info.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        info.append("Nombre: ").append(evento.getNombre()).append("\n");
        info.append("Fecha: ").append(formato.format(evento.getFecha())).append("\n");
        info.append("Salón: ").append(evento.getSalon().getNombre()).append("\n");
        info.append("Ubicación: ").append(evento.getSalon().getUbicacion()).append("\n");
        info.append("Capacidad: ").append(evento.getSalon().getCapacidad()).append(" personas\n");
        info.append("Descripción: ").append(evento.getDescripcion()).append("\n\n");
        
        int registrados = evento.getAsistentes() != null ? evento.getAsistentes().size() : 0;
        int disponibles = evento.getSalon().getCapacidad() - registrados;
        
        info.append("ESTADO DE INSCRIPCIONES:\n");
        info.append("• Registrados: ").append(registrados).append(" participantes\n");
        info.append("• Disponibles: ").append(disponibles).append(" cupos\n");
        
        if (disponibles == 0) {
            info.append("• EVENTO COMPLETO - No se pueden registrar más participantes\n");
        } else if (disponibles <= 5) {
            info.append("• ÚLTIMOS CUPOS - Solo quedan ").append(disponibles).append(" lugares\n");
        } else {
            info.append("• CUPOS DISPONIBLES - ").append(disponibles).append(" lugares libres\n");
        }
        
        if (evento.getAsistentes() != null && !evento.getAsistentes().isEmpty()) {
            info.append("\nPARTICIPANTES REGISTRADOS:\n");
            for (int i = 0; i < evento.getAsistentes().size() && i < 3; i++) {
                Asistentes asistente = evento.getAsistentes().get(i);
                info.append("• ").append(asistente.getNombre()).append(" ").append(asistente.getApellido()).append("\n");
            }
            if (evento.getAsistentes().size() > 3) {
                info.append("• ... y ").append(evento.getAsistentes().size() - 3).append(" participantes más\n");
            }
        }
        
        areaInfoEvento.setText(info.toString());
    }
    
    private void actualizarInformacionAsistente(Asistentes asistente) {
        StringBuilder info = new StringBuilder();
        
        info.append("INFORMACIÓN DEL PARTICIPANTE\n");
        info.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        info.append("Nombre: ").append(asistente.getNombre()).append(" ").append(asistente.getApellido()).append("\n");
        info.append("Email: ").append(asistente.getEmail() != null ? asistente.getEmail() : "No registrado").append("\n");
        info.append("Teléfono: ").append(asistente.getTelefono() != null ? asistente.getTelefono() : "No registrado").append("\n\n");
        
        List<Eventos> eventosDelAsistente = eventosController.obtenerTodosLosEventos().stream()
            .filter(evento -> evento.getAsistentes() != null && evento.getAsistentes().contains(asistente))
            .collect(Collectors.toList());
        
        info.append("HISTORIAL DE PARTICIPACIÓN:\n");
        if (eventosDelAsistente.isEmpty()) {
            info.append("• Este será su primer evento en el sistema\n");
            info.append("• Participante nuevo\n");
        } else {
            info.append("• Registrado en ").append(eventosDelAsistente.size()).append(" evento(s)\n");
            info.append("• Participante activo\n\n");
            
            info.append("EVENTOS PARTICIPANDO:\n");
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            for (int i = 0; i < eventosDelAsistente.size() && i < 3; i++) {
                Eventos evento = eventosDelAsistente.get(i);
                info.append("• ").append(evento.getNombre())
                    .append(" (").append(formato.format(evento.getFecha())).append(")\n");
            }
            if (eventosDelAsistente.size() > 3) {
                info.append("• ... y ").append(eventosDelAsistente.size() - 3).append(" eventos más\n");
            }
        }
        
        info.append("\nESTADO DE CONTACTO:\n");
        if (asistente.getEmail() != null && !asistente.getEmail().isEmpty()) {
            info.append("• Email disponible para notificaciones\n");
        } else {
            info.append("• Sin email - Contacto limitado\n");
        }
        
        if (asistente.getTelefono() != null && !asistente.getTelefono().isEmpty()) {
            info.append("• Teléfono disponible\n");
        } else {
            info.append("• Sin teléfono registrado\n");
        }
        
        areaInfoAsistente.setText(info.toString());
    }
    
    private void actualizarCapacidad(Eventos evento) {
        int capacidadTotal = evento.getSalon().getCapacidad();
        int registrados = evento.getAsistentes() != null ? evento.getAsistentes().size() : 0;
        int porcentaje = capacidadTotal > 0 ? (registrados * 100) / capacidadTotal : 0;
        
        lblCapacidad.setText("Capacidad: " + registrados + "/" + capacidadTotal + " participantes");
        
        progressCapacidad.setMaximum(capacidadTotal);
        progressCapacidad.setValue(registrados);
        progressCapacidad.setString(porcentaje + "% ocupado");
        
        if (porcentaje >= 100) {
            progressCapacidad.setForeground(COLOR_PELIGRO);
            lblCapacidad.setForeground(COLOR_PELIGRO);
        } else if (porcentaje >= 80) {
            progressCapacidad.setForeground(Color.ORANGE);
            lblCapacidad.setForeground(Color.ORANGE.darker());
        } else {
            progressCapacidad.setForeground(COLOR_PRINCIPAL);
            lblCapacidad.setForeground(COLOR_TEXTO_TITULO);
        }
    }
    
    private void cargarAsistentes(Eventos evento) {
        comboAsistente.removeAllItems();
        comboAsistente.addItem(null);
        
        List<Asistentes> asistentesEvento = evento.getAsistentes();
        
        List<Asistentes> asistentesDisponibles;
        if (asistentesEvento == null || asistentesEvento.isEmpty()) {
            asistentesDisponibles = asistentesController.obtenerTodosLosParticipantes();
        } else {
            asistentesDisponibles = asistentesController.obtenerTodosLosParticipantes()
                    .stream()
                    .filter(asistente -> !asistentesEvento.contains(asistente))
                    .collect(Collectors.toList());
        }
        
        asistentesDisponibles.forEach(comboAsistente::addItem);
        
        agregarBarraEstado("Participantes disponibles: " + asistentesDisponibles.size(), 
            "Seleccione un participante para registrar en el evento");
    }
    
    private void limpiarInformacion() {
        areaInfoEvento.setText("Seleccione un evento para ver su información...");
        areaInfoAsistente.setText("Seleccione un participante para ver su información...");
        lblCapacidad.setText("Capacidad: Sin evento seleccionado");
        lblCapacidad.setForeground(COLOR_TEXTO_TITULO);
        progressCapacidad.setValue(0);
        progressCapacidad.setString("0%");
        progressCapacidad.setForeground(COLOR_PRINCIPAL);
        comboAsistente.removeAllItems();
        agregarBarraEstado("Registro de Participante", "Seleccione un evento para comenzar");
    }
    
    private void limpiarSelecciones() {
        comboEvento.setSelectedIndex(0);
        comboAsistente.removeAllItems();
        limpiarInformacion();
    }

    private void registrarAsistente() {
        try {
            Eventos evento = (Eventos) comboEvento.getSelectedItem();
            Asistentes asistente = (Asistentes) comboAsistente.getSelectedItem();

            if (evento == null) {
                JOptionPane.showMessageDialog(this, 
                    "Debe seleccionar un evento para el registro.", 
                    "Evento Requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (asistente == null) {
                JOptionPane.showMessageDialog(this, 
                    "Debe seleccionar un participante para registrar.", 
                    "Participante Requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (eventosController.verificarCapacidadCompleta(evento)) {
                JOptionPane.showMessageDialog(this, 
                    "Capacidad Completa\n\n" +
                    "La capacidad máxima del salón ha sido alcanzada.\n" +
                    "No se pueden registrar más participantes en este evento.\n\n" +
                    "Capacidad: " + evento.getSalon().getCapacidad() + " personas\n" +
                    "Registrados: " + (evento.getAsistentes() != null ? evento.getAsistentes().size() : 0), 
                    "Capacidad Agotada", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (evento.getAsistentes() != null && evento.getAsistentes().contains(asistente)) {
                JOptionPane.showMessageDialog(this, 
                    "Participante ya registrado\n\n" +
                    "El participante " + asistente.getNombre() + " " + asistente.getApellido() + 
                    " ya está registrado en este evento.", 
                    "Registro Duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("CONFIRMACIÓN DE REGISTRO\n\n");
            mensaje.append("¿Confirma el registro del siguiente participante?\n\n");
            mensaje.append("Participante: ").append(asistente.getNombre()).append(" ").append(asistente.getApellido()).append("\n");
            mensaje.append("Email: ").append(asistente.getEmail() != null ? asistente.getEmail() : "No registrado").append("\n");
            mensaje.append("Evento: ").append(evento.getNombre()).append("\n");
            mensaje.append("Fecha: ").append(new SimpleDateFormat("dd/MM/yyyy").format(evento.getFecha())).append("\n");
            mensaje.append("Salón: ").append(evento.getSalon().getNombre()).append("\n\n");
            
            int registradosActuales = evento.getAsistentes() != null ? evento.getAsistentes().size() : 0;
            int capacidadTotal = evento.getSalon().getCapacidad();
            int disponiblesDespues = capacidadTotal - registradosActuales - 1;
            
            mensaje.append("Capacidad después del registro:\n");
            mensaje.append("• Registrados: ").append(registradosActuales + 1).append("/").append(capacidadTotal).append("\n");
            mensaje.append("• Disponibles: ").append(disponiblesDespues).append(" cupos\n");
            
            if (disponiblesDespues == 0) {
                mensaje.append("• Este registro COMPLETARÁ la capacidad del evento");
            }
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                mensaje.toString(), 
                "Confirmar Registro de Participante", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
                
            if (confirmacion == JOptionPane.YES_OPTION) {
                eventosController.inscribirParticipanteEnEvento(evento, asistente);
                
                String mensajeExito = "Participante registrado exitosamente\n\n" +
                    "Participante: " + asistente.getNombre() + " " + asistente.getApellido() + "\n" +
                    "Evento: " + evento.getNombre() + "\n" +
                    "Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(evento.getFecha()) + "\n\n";
                
                if (disponiblesDespues == 0) {
                    mensajeExito += "EVENTO COMPLETO: Se ha alcanzado la capacidad máxima";
                } else if (disponiblesDespues <= 3) {
                    mensajeExito += "ÚLTIMOS CUPOS: Solo quedan " + disponiblesDespues + " lugares disponibles";
                } else {
                    mensajeExito += "Quedan " + disponiblesDespues + " cupos disponibles";
                }
                
                JOptionPane.showMessageDialog(this, 
                    mensajeExito, 
                    "Registro Completado", JOptionPane.INFORMATION_MESSAGE);
                
                // Volver automáticamente al menú anterior
                dispose();
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al registrar el participante:\n" + ex.getMessage(), 
                "Error de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }
}
