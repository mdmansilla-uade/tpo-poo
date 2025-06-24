package Views.Eventos;

import Controllers.SalonController;
import Controllers.AsistentesController;
import Models.Eventos;
import Controllers.EventosController;
import Models.Salon;
import Models.Asistentes;
import Views.BaseView;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;

public class EditarEvento extends BaseView {
    private final EventosController eventosController = EventosController.getInstance();
    private final SalonController salonController = SalonController.getInstance();
    private final AsistentesController asistentesController = AsistentesController.getInstance();
    
    private JComboBox<Eventos> comboEventos;
    private JComboBox<Salon> comboSalon;
    private JDateChooser dateChooser;
    private JTextField txtDescripcion;
    private JTextArea areaDetalles;
    private JLabel lblEstadoFecha;
    private JLabel lblParticipantes;

    public EditarEvento() {
        configurarVentanaBase("Editar Evento", 1000, 750);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Modificación de Evento",
            "Edite los detalles de eventos existentes en el sistema"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelFormulario = crearPanelOpciones("Detalles del Evento");
        
        panelFormulario.add(crearCampoSeleccionEvento());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearCampoFecha());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearCampoSalon());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearCampoDescripcion());
        
        panelPrincipal.add(panelFormulario);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelDetalles = crearPanelDetalles();
        panelPrincipal.add(panelDetalles);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(crearEspaciado(20));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        agregarBarraEstado("Edición de Evento", "Seleccione un evento para modificar");
        
        cargarEventos();
    }
    
    private JPanel crearCampoSeleccionEvento() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Seleccionar Evento a Editar:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        comboEventos = new JComboBox<>();
        comboEventos.setPreferredSize(new Dimension(400, 35));
        comboEventos.setFont(new Font("Arial", Font.PLAIN, 13));
        comboEventos.addActionListener(e -> cargarDetalles());
        
        JLabel ayuda = new JLabel("Seleccione el evento que desea modificar");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(comboEventos, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearCampoFecha() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Nueva Fecha del Evento:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelFecha.setBackground(COLOR_PANEL);
        
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(200, 35));
        dateChooser.setFont(new Font("Arial", Font.PLAIN, 13));
        dateChooser.setMinSelectableDate(new Date());
        dateChooser.addPropertyChangeListener("date", evt -> validarCambioFecha());
        
        lblEstadoFecha = new JLabel("Sin fecha seleccionada");
        lblEstadoFecha.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEstadoFecha.setForeground(COLOR_SECUNDARIO);
        
        panelFecha.add(dateChooser);
        panelFecha.add(Box.createRigidArea(new Dimension(20, 0)));
        panelFecha.add(lblEstadoFecha);
        
        JLabel ayuda = new JLabel("Al cambiar fecha se actualizarán los salones disponibles");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(panelFecha, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearCampoSalon() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Seleccionar Nuevo Salón:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        comboSalon = new JComboBox<>();
        comboSalon.setPreferredSize(new Dimension(400, 35));
        comboSalon.setFont(new Font("Arial", Font.PLAIN, 13));
        comboSalon.addActionListener(e -> actualizarInfoSalon());
        
        JLabel ayuda = new JLabel("Solo se muestran salones disponibles para la fecha seleccionada");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(comboSalon, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearCampoDescripcion() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Descripción del Evento:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        txtDescripcion = new JTextField();
        txtDescripcion.setPreferredSize(new Dimension(400, 35));
        txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 13));
        txtDescripcion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEtchedBorder(),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        JLabel ayuda = new JLabel("Ingrese una descripción clara y concisa del evento");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(txtDescripcion, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelDetalles() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_PANEL);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Información del Evento",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            COLOR_TEXTO_TITULO
        ));
        
        areaDetalles = new JTextArea(8, 50);
        areaDetalles.setFont(new Font("Arial", Font.PLAIN, 12));
        areaDetalles.setEditable(false);
        areaDetalles.setBackground(COLOR_FONDO);
        areaDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaDetalles.setText("Seleccione un evento para ver sus detalles...");
        
        JScrollPane scrollDetalles = new JScrollPane(areaDetalles);
        scrollDetalles.setBorder(BorderFactory.createEtchedBorder());
        scrollDetalles.setPreferredSize(new Dimension(700, 120));
        
        JPanel panelParticipantes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelParticipantes.setBackground(COLOR_PANEL);
        
        lblParticipantes = new JLabel("Participantes: 0");
        lblParticipantes.setFont(new Font("Arial", Font.BOLD, 12));
        lblParticipantes.setForeground(COLOR_TEXTO_TITULO);
        
        JButton btnGestionarAsistentes = crearBotonSimple("Gestionar Asistentes", 
            "Agregar o eliminar participantes", COLOR_SECUNDARIO, e -> gestionarAsistentes());
        
        panelParticipantes.add(lblParticipantes);
        panelParticipantes.add(Box.createRigidArea(new Dimension(20, 0)));
        panelParticipantes.add(btnGestionarAsistentes);
        
        panel.add(scrollDetalles, BorderLayout.CENTER);
        panel.add(panelParticipantes, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(COLOR_FONDO);
        
        JButton btnGuardar = crearBotonSimple("Guardar Cambios", 
            "Aplica las modificaciones al evento", COLOR_PRINCIPAL, e -> editarEvento());
        
        JButton btnLimpiar = crearBotonSimple("Limpiar Formulario", 
            "Reinicia todos los campos", COLOR_SECUNDARIO, e -> limpiarFormulario());
        
        JButton btnVolver = crearBotonSimple("Volver al Menú", 
            "Regresa al menú de eventos", COLOR_SECUNDARIO, e -> dispose());
        
        JButton btnCancelar = crearBotonSimple("Cancelar", 
            "Cierra sin guardar cambios", COLOR_SECUNDARIO, e -> dispose());
        
        panel.add(btnGuardar);
        panel.add(btnLimpiar);
        panel.add(btnVolver);
        panel.add(btnCancelar);
        
        return panel;
    }
    
    private void cargarEventos() {
        comboEventos.removeAllItems();
        comboEventos.addItem(null);
        for (Eventos evento : eventosController.obtenerTodosLosEventos()) {
            comboEventos.addItem(evento);
        }
    }
    
    private void cargarDetalles() {
        Eventos evento = obtenerEventoSeleccionado();
        if (evento != null) {
            dateChooser.setDate(evento.getFecha());
            txtDescripcion.setText(evento.getDescripcion());
            
            actualizarInformacionEvento(evento);
            
            validarCambioFecha();
            
            comboSalon.setSelectedItem(evento.getSalon());
            
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            agregarBarraEstado("Editando: " + evento.getNombre(), 
                "Fecha: " + formato.format(evento.getFecha()) + " | Participantes: " + 
                (evento.getAsistentes() != null ? evento.getAsistentes().size() : 0));
        } else {
            limpiarFormulario();
        }
    }
    
    private void actualizarInformacionEvento(Eventos evento) {
        StringBuilder detalles = new StringBuilder();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        detalles.append("INFORMACIÓN DEL EVENTO\n");
        detalles.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        detalles.append("Nombre: ").append(evento.getNombre()).append("\n");
        detalles.append("Fecha: ").append(formato.format(evento.getFecha())).append("\n");
        detalles.append("Salón: ").append(evento.getSalon().getNombre()).append("\n");
        detalles.append("Capacidad: ").append(evento.getSalon().getCapacidad()).append(" personas\n");
        detalles.append("Ubicación: ").append(evento.getSalon().getUbicacion()).append("\n");
        detalles.append("Descripción: ").append(evento.getDescripcion()).append("\n\n");
        
        // Información de participantes
        if (evento.getAsistentes() != null && !evento.getAsistentes().isEmpty()) {
            detalles.append("PARTICIPANTES REGISTRADOS:\n");
            for (int i = 0; i < evento.getAsistentes().size() && i < 3; i++) {
                Asistentes asistente = evento.getAsistentes().get(i);
                detalles.append("• ").append(asistente.getNombre()).append(" ").append(asistente.getApellido()).append("\n");
            }
            if (evento.getAsistentes().size() > 3) {
                detalles.append("• ... y ").append(evento.getAsistentes().size() - 3).append(" participantes más\n");
            }
        } else {
            detalles.append("PARTICIPANTES: Ningún participante registrado");
        }
        
        areaDetalles.setText(detalles.toString());
        
        int numParticipantes = evento.getAsistentes() != null ? evento.getAsistentes().size() : 0;
        lblParticipantes.setText("Participantes: " + numParticipantes);
    }
    
    private void validarCambioFecha() {
        Date fecha = dateChooser.getDate();
        if (fecha != null) {
            Date hoy = new Date();
            if (fecha.before(hoy)) {
                lblEstadoFecha.setText("Fecha pasada - No válida");
                lblEstadoFecha.setForeground(COLOR_PELIGRO);
                comboSalon.removeAllItems();
                return;
            }

            LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int dayOfWeek = localDate.getDayOfWeek().getValue();
            if (dayOfWeek == 6 || dayOfWeek == 7) {
                lblEstadoFecha.setText("Fin de semana - Verificar disponibilidad");
                lblEstadoFecha.setForeground(COLOR_PELIGRO);
            } else {
                lblEstadoFecha.setText("Fecha válida");
                lblEstadoFecha.setForeground(COLOR_PRINCIPAL);
            }
            
            cargarSalonesDisponibles(fecha);
        } else {
            lblEstadoFecha.setText("Sin fecha seleccionada");
            lblEstadoFecha.setForeground(COLOR_SECUNDARIO);
            comboSalon.removeAllItems();
        }
    }
    
    private void cargarSalonesDisponibles(Date fecha) {
        Eventos eventoActual = obtenerEventoSeleccionado();
        comboSalon.removeAllItems();
        
        if (eventoActual != null && fecha.equals(eventoActual.getFecha())) {
            comboSalon.addItem(eventoActual.getSalon());
        }
        
        List<Salon> salonesDisponibles = salonController.obtenerEspaciosDisponibles(fecha);
        for (Salon salon : salonesDisponibles) {
            boolean yaExiste = false;
            for (int i = 0; i < comboSalon.getItemCount(); i++) {
                if (comboSalon.getItemAt(i).equals(salon)) {
                    yaExiste = true;
                    break;
                }
            }
            if (!yaExiste) {
                comboSalon.addItem(salon);
            }
        }
        
        if (comboSalon.getItemCount() == 0) {
            agregarBarraEstado("Sin salones disponibles", 
                "No hay espacios libres para la fecha seleccionada");
        } else {
            agregarBarraEstado("Salones disponibles: " + comboSalon.getItemCount(), 
                "Seleccione el nuevo espacio para el evento");
        }
    }
    
    private void actualizarInfoSalon() {
        Salon salon = (Salon) comboSalon.getSelectedItem();
        if (salon != null) {
            agregarBarraEstado("Salón: " + salon.getNombre(), 
                "Capacidad: " + salon.getCapacidad() + " personas | " + salon.getUbicacion());
        }
    }
    
    private void editarEvento() {
        try {
            Eventos evento = obtenerEventoSeleccionado();
            if (evento == null) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor seleccione un evento para editar.", 
                    "Evento No Seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Date fecha = dateChooser.getDate();
            Salon salon = (Salon) comboSalon.getSelectedItem();
            String descripcion = txtDescripcion.getText().trim();
            
            if (fecha == null) {
                JOptionPane.showMessageDialog(this, 
                    "Debe seleccionar una fecha para el evento.", 
                    "Fecha Requerida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (salon == null) {
                JOptionPane.showMessageDialog(this, 
                    "Debe seleccionar un salón para el evento.", 
                    "Salón Requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Debe ingresar una descripción para el evento.", 
                    "Descripción Requerida", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (fecha.before(new Date())) {
                JOptionPane.showMessageDialog(this, 
                    "No se puede programar un evento en fecha pasada.", 
                    "Fecha Inválida", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String nuevoNombre = generarNombreEvento(fecha, salon.getNombre());
            
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("¿Confirma los siguientes cambios?\n\n");
            mensaje.append("Evento: ").append(evento.getNombre()).append("\n");
            mensaje.append("Nueva fecha: ").append(new SimpleDateFormat("dd/MM/yyyy").format(fecha)).append("\n");
            mensaje.append("Nuevo salón: ").append(salon.getNombre()).append("\n");
            mensaje.append("Nueva descripción: ").append(descripcion).append("\n");
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                mensaje.toString(), 
                "Confirmar Modificación", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
                
            if (confirmacion == JOptionPane.YES_OPTION) {
                eventosController.modificarEventoExistente(evento, nuevoNombre, fecha, salon, descripcion);
                
                JOptionPane.showMessageDialog(this, 
                    "Evento modificado exitosamente\n\n" +
                    "Los cambios han sido aplicados al evento:\n" + nuevoNombre, 
                    "Modificación Completada", JOptionPane.INFORMATION_MESSAGE);
                
                dispose();
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al modificar el evento:\n" + ex.getMessage(), 
                "Error de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void gestionarAsistentes() {
        Eventos eventoSeleccionado = obtenerEventoSeleccionado();
        if (eventoSeleccionado != null) {
            if (eventoSeleccionado.getAsistentes() == null || eventoSeleccionado.getAsistentes().isEmpty()) {
                int opcion = JOptionPane.showConfirmDialog(this,
                    "Este evento no tiene participantes registrados.\n\n" +
                    "¿Desea abrir la ventana de gestión de asistentes?",
                    "Sin Participantes",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);
                    
                if (opcion != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            
            EliminarAsistente dialog = new EliminarAsistente(this, eventoSeleccionado);
            dialog.setVisible(true);
            
            actualizarInformacionEvento(eventoSeleccionado);
            eventosController.persistirEventos();
        } else {
            JOptionPane.showMessageDialog(this,
                "Primero debe seleccionar un evento.",
                "Evento No Seleccionado",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        comboEventos.setSelectedIndex(0);
        dateChooser.setDate(null);
        comboSalon.removeAllItems();
        txtDescripcion.setText("");
        areaDetalles.setText("Seleccione un evento para ver sus detalles...");
        lblParticipantes.setText("Participantes: 0");
        lblEstadoFecha.setText("Sin fecha seleccionada");
        lblEstadoFecha.setForeground(COLOR_SECUNDARIO);
        agregarBarraEstado("Edición de Evento", "Seleccione un evento para modificar");
    }

    private Eventos obtenerEventoSeleccionado() {
        return (Eventos) comboEventos.getSelectedItem();
    }

    private String generarNombreEvento(Date fecha, String salon) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String formattedDate = dateFormat.format(fecha);
        return formattedDate + "-" + salon;
    }
}
