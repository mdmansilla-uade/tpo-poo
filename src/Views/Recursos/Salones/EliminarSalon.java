package Views.Recursos.Salones;

import Controllers.SalonController;
import Controllers.EventosController;
import Models.Salon;
import Models.Eventos;
import Views.BaseView;
import java.awt.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class EliminarSalon extends BaseView {
    private final SalonController salonController = SalonController.getInstance();
    private final EventosController eventosController = EventosController.getInstance();
    private JComboBox<Salon> cmbSalones;
    private JTextArea areaDetalles;
    private JTextArea areaImpacto;

    public EliminarSalon() {
        configurarVentanaBase("Eliminar Salón", 700, 750);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());

        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Eliminación de Espacio",
            "⚠️ Esta acción removerá permanentemente el salón del sistema"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelFormulario = crearPanelOpciones("Selección de Espacio");
        
        panelFormulario.add(crearCampoSeleccion());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearPanelDetalles());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearPanelAnalisisImpacto());
        
        panelPrincipal.add(panelFormulario);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelAdvertencia = crearPanelAdvertencia();
        panelPrincipal.add(panelAdvertencia);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(crearEspaciado(20));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        agregarBarraEstado("Eliminación de Salón", "Seleccione el espacio a eliminar");
        
        cargarSalones();
    }
    
    private JPanel crearCampoSeleccion() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Seleccionar Salón a Eliminar:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        cmbSalones = new JComboBox<>();
        cmbSalones.setPreferredSize(new Dimension(400, 35));
        cmbSalones.setFont(new Font("Arial", Font.PLAIN, 13));
        cmbSalones.addActionListener(e -> actualizarInformacion());
        
        JLabel ayuda = new JLabel("El salón será eliminado y todos sus eventos futuros cancelados");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_PELIGRO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(cmbSalones, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelDetalles() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Información del Salón:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        areaDetalles = new JTextArea(4, 30);
        areaDetalles.setFont(new Font("Arial", Font.PLAIN, 12));
        areaDetalles.setEditable(false);
        areaDetalles.setBackground(COLOR_FONDO);
        areaDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaDetalles.setText("Seleccione un salón para ver sus detalles...");
        
        JScrollPane scrollDetalles = new JScrollPane(areaDetalles);
        scrollDetalles.setBorder(BorderFactory.createEtchedBorder());
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(scrollDetalles, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelAnalisisImpacto() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Análisis de Impacto:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_PELIGRO);
        
        areaImpacto = new JTextArea(4, 30);
        areaImpacto.setFont(new Font("Arial", Font.PLAIN, 12));
        areaImpacto.setEditable(false);
        areaImpacto.setBackground(COLOR_PELIGRO.brighter().brighter().brighter());
        areaImpacto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaImpacto.setText("Seleccione un salón para analizar el impacto de su eliminación...");
        
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
        
        JLabel iconoAdvertencia = new JLabel("🏢⚠️");
        iconoAdvertencia.setFont(new Font("Arial", Font.BOLD, 24));
        iconoAdvertencia.setForeground(COLOR_PELIGRO);
        
        JTextArea textoAdvertencia = new JTextArea(
            "ADVERTENCIA CRÍTICA - ELIMINACIÓN DE ESPACIO:\n\n" +
            "• Esta acción eliminará permanentemente el salón del sistema\n" +
            "• Todos los eventos programados en este espacio serán CANCELADOS\n" +
            "• Los participantes perderán sus reservas automáticamente\n" +
            "• La capacidad total del sistema se reducirá\n" +
            "• Los datos del salón no podrán ser recuperados\n" +
            "• Se recomienda revisar el análisis de impacto antes de proceder"
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
        
        JButton btnEliminar = crearBotonSimple("Eliminar Salón", 
            "PELIGRO: Esta acción cancelará eventos", COLOR_PELIGRO, e -> eliminarSalon());
        
        JButton btnVolver = crearBotonSimple("Volver al Menú", 
            "Regresa al menú de espacios", COLOR_SECUNDARIO, e -> dispose());
        
        JButton btnCancelar = crearBotonSimple("Cancelar", 
            "Cancela la operación sin eliminar", COLOR_SECUNDARIO, e -> dispose());
        
        panel.add(btnEliminar);
        panel.add(btnVolver);
        panel.add(btnCancelar);
        
        return panel;
    }

    private void cargarSalones() {
        cmbSalones.removeAllItems();
        cmbSalones.addItem(null);
        for (Salon salon : salonController.obtenerTodosLosEspacios()) {
            cmbSalones.addItem(salon);
        }
    }
    
    private void actualizarInformacion() {
        Salon salon = obtenerSalonSeleccionado();
        if (salon != null) {
            StringBuilder detalles = new StringBuilder();
            detalles.append("INFORMACIÓN DEL ESPACIO\n");
            detalles.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
            detalles.append("Nombre: ").append(salon.getNombre()).append("\n");
            detalles.append("Capacidad: ").append(salon.getCapacidad()).append(" personas\n");
            detalles.append("Ubicación: ").append(salon.getUbicacion()).append("\n\n");
            detalles.append("ADVERTENCIA: Esta información será eliminada permanentemente");
            
            areaDetalles.setText(detalles.toString());
            
            analizarImpactoEliminacion(salon);
            
            agregarBarraEstado(" Seleccionado: " + salon.getNombre(), 
                "Capacidad: " + salon.getCapacidad() + " | Revise el análisis de impacto");
        } else {
            areaDetalles.setText("Seleccione un salón para ver sus detalles...");
            areaImpacto.setText("Seleccione un salón para analizar el impacto de su eliminación...");
            agregarBarraEstado("Eliminación de Salón", "Seleccione el espacio a eliminar");
        }
    }
    
    private void analizarImpactoEliminacion(Salon salon) {
        StringBuilder analisis = new StringBuilder();
        
        // Buscar eventos que usan este salón
        List<Eventos> eventosAfectados = eventosController.obtenerTodosLosEventos().stream()
            .filter(evento -> evento.getSalon().equals(salon))
            .collect(Collectors.toList());
        
        analisis.append("ANÁLISIS DE IMPACTO - ELIMINACIÓN\n");
        analisis.append("═══════════════════════════════════════════════\n\n");
        
        if (eventosAfectados.isEmpty()) {
            analisis.append("CTO BAJO:\n");
            analisis.append("• No hay eventos programados en este salón\n");
            analisis.append("• La eliminación no afectará eventos existentes\n");
            analisis.append("• Es seguro eliminar este espacio\n\n");
            analisis.append("RECOMENDACIÓN: Eliminación segura - Sin impacto en eventos");
        } else {
            analisis.append("IMPACTO ALTO:\n");
            analisis.append("• ").append(eventosAfectados.size()).append(" eventos serán CANCELADOS\n");
            
            // Contar participantes afectados
            int totalParticipantesAfectados = eventosAfectados.stream()
                .mapToInt(evento -> evento.getAsistentes() != null ? evento.getAsistentes().size() : 0)
                .sum();
            
            analisis.append("• ").append(totalParticipantesAfectados).append(" participantes perderán sus reservas\n\n");
            
            analisis.append("EVENTOS AFECTADOS:\n");
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            for (int i = 0; i < eventosAfectados.size() && i < 5; i++) {
                Eventos evento = eventosAfectados.get(i);
                analisis.append("• ").append(evento.getNombre())
                        .append(" (").append(formato.format(evento.getFecha())).append(")\n");
            }
            
            if (eventosAfectados.size() > 5) {
                analisis.append("• ... y ").append(eventosAfectados.size() - 5).append(" eventos más\n");
            }
            
            analisis.append("\nRECOMENDACIÓN: NO eliminar - Impacto severo en eventos activos");
        }
        
        areaImpacto.setText(analisis.toString());
    }

    private void eliminarSalon() {
        try {
            Salon salonSeleccionado = obtenerSalonSeleccionado();
            if (salonSeleccionado == null) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor seleccione un salón para eliminar.", 
                    "Salón No Seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            List<Eventos> eventosAfectados = eventosController.obtenerTodosLosEventos().stream()
                .filter(evento -> evento.getSalon().equals(salonSeleccionado))
                .collect(Collectors.toList());
            
            String mensajeConfirmacion;
            if (eventosAfectados.isEmpty()) {
                mensajeConfirmacion = "CONFIRMACIÓN DE ELIMINACIÓN\n\n" +
                                    "Está a punto de eliminar permanentemente:\n\n" +
                                    "Salón: " + salonSeleccionado.getNombre() + "\n" +
                                    "Capacidad: " + salonSeleccionado.getCapacidad() + " personas\n" +
                                    "Ubicación: " + salonSeleccionado.getUbicacion() + "\n\n" +
                                    "IMPACTO: Ningún evento será afectado\n\n" +
                                    "¿Está seguro de eliminar este salón?";
            } else {
                int totalParticipantes = eventosAfectados.stream()
                    .mapToInt(evento -> evento.getAsistentes() != null ? evento.getAsistentes().size() : 0)
                    .sum();
                    
                mensajeConfirmacion = "CONFIRMACIÓN DE ELIMINACIÓN CRÍTICA\n\n" +
                                    "PELIGRO: Esta acción tendrá ALTO IMPACTO\n\n" +
                                    "Salón: " + salonSeleccionado.getNombre() + "\n" +
                                    "Capacidad: " + salonSeleccionado.getCapacidad() + " personas\n" +
                                    "Ubicación: " + salonSeleccionado.getUbicacion() + "\n\n" +
                                    "IMPACTO SEVERO:\n" +
                                    "• " + eventosAfectados.size() + " eventos serán CANCELADOS\n" +
                                    "• " + totalParticipantes + " participantes perderán reservas\n" +
                                    "• Pérdida de capacidad del sistema\n\n" +
                                    "¿Está COMPLETAMENTE SEGURO de continuar?\n" +
                                    "Esta acción NO se puede deshacer.";
            }
            
            int tipoMensaje = eventosAfectados.isEmpty() ? JOptionPane.QUESTION_MESSAGE : JOptionPane.ERROR_MESSAGE;
            int opcion = JOptionPane.showConfirmDialog(this, 
                mensajeConfirmacion, 
                "Confirmar Eliminación de Salón", 
                JOptionPane.YES_NO_OPTION,
                tipoMensaje);
                
            if (opcion == JOptionPane.YES_OPTION) {
                salonController.eliminarEspacio(salonSeleccionado);
                
                String mensajeExito = eventosAfectados.isEmpty() ? 
                    "Salón eliminado exitosamente\n\n" +
                    "El espacio " + salonSeleccionado.getNombre() + 
                    " ha sido removido del sistema sin afectar eventos." :
                    "Salón eliminado con impacto\n\n" +
                    "El espacio " + salonSeleccionado.getNombre() + 
                    " ha sido eliminado.\n" + eventosAfectados.size() + 
                    " eventos han sido cancelados automáticamente.";
                
                JOptionPane.showMessageDialog(this, 
                    mensajeExito, 
                    "Eliminación Completada", JOptionPane.INFORMATION_MESSAGE);
                
                // Volver automáticamente al menú anterior
                dispose();
            }
            
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, 
                "No se puede eliminar este salón:\n\n" + ex.getMessage() + 
                "\n\nEl salón está siendo utilizado en eventos activos.", 
                "Eliminación Bloqueada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al eliminar el salón:\n" + ex.getMessage(), 
                "Error de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Salon obtenerSalonSeleccionado() {
        return (Salon) cmbSalones.getSelectedItem();
    }
}
