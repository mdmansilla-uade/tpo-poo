package Views.Recursos.Asistentes;

import Controllers.AsistentesController;
import Models.Asistentes;
import Views.BaseView;
import java.awt.*;
import javax.swing.*;

public class EliminarAsistente extends BaseView {
    private final AsistentesController asistentesController = AsistentesController.getInstance();
    private JComboBox<Asistentes> cmbAsistentes;
    private JTextArea areaDetalles;

    public EliminarAsistente() {
        configurarVentanaBase("Eliminar Participante", 650, 700);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Eliminación de Participante",
            "Esta acción removerá permanentemente al asistente del sistema"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelFormulario = crearPanelOpciones("Selección de Participante");
        
        panelFormulario.add(crearCampoSeleccion());
        panelFormulario.add(crearEspaciado(20));
        panelFormulario.add(crearPanelDetalles());
        
        panelPrincipal.add(panelFormulario);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelAdvertencia = crearPanelAdvertencia();
        panelPrincipal.add(panelAdvertencia);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(crearEspaciado(20));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        agregarBarraEstado("Eliminación de Asistente", "Seleccione el participante a eliminar");
        
        cargarAsistentes();
    }
    
    private JPanel crearCampoSeleccion() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Seleccionar Asistente a Eliminar:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        cmbAsistentes = new JComboBox<>();
        cmbAsistentes.setPreferredSize(new Dimension(400, 35));
        cmbAsistentes.setFont(new Font("Arial", Font.PLAIN, 13));
        cmbAsistentes.addActionListener(e -> actualizarDetalles());
        
        JLabel ayuda = new JLabel("El participante será eliminado de todos los eventos registrados");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_PELIGRO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(cmbAsistentes, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelDetalles() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Información del Participante:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        areaDetalles = new JTextArea(6, 30);
        areaDetalles.setFont(new Font("Arial", Font.PLAIN, 12));
        areaDetalles.setEditable(false);
        areaDetalles.setBackground(COLOR_FONDO);
        areaDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaDetalles.setText("Seleccione un asistente para ver sus detalles...");
        
        JScrollPane scrollDetalles = new JScrollPane(areaDetalles);
        scrollDetalles.setBorder(BorderFactory.createEtchedBorder());
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(scrollDetalles, BorderLayout.CENTER);
        
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
            "ADVERTENCIA IMPORTANTE:\n\n" +
            "• Esta acción eliminará permanentemente al participante del sistema\n" +
            "• Se removerá de todos los eventos en los que esté inscrito\n" +
            "• Los datos no podrán ser recuperados una vez confirmada la eliminación\n" +
            "• Se recomienda verificar la información antes de proceder"
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
        
        JButton btnEliminar = crearBotonSimple("Eliminar Participante", 
            "CUIDADO: Esta acción es irreversible", COLOR_PELIGRO, e -> eliminarAsistente());
        
        JButton btnVolver = crearBotonSimple("Volver al Menú", 
            "Regresa al menú de participantes", COLOR_SECUNDARIO, e -> dispose());
        
        JButton btnCancelar = crearBotonSimple("Cancelar", 
            "Cancela la operación sin eliminar", COLOR_SECUNDARIO, e -> dispose());
        
        panel.add(btnEliminar);
        panel.add(btnVolver);
        panel.add(btnCancelar);
        
        return panel;
    }

    private void eliminarAsistente() {
        try {
            Asistentes asistenteSeleccionado = obtenerAsistenteSeleccionado();
            if (asistenteSeleccionado == null) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor seleccione un asistente para eliminar.", 
                    "Asistente No Seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String mensaje = "CONFIRMACIÓN DE ELIMINACIÓN\n\n" +
                           "Está a punto de eliminar permanentemente a:\n\n" +
                           "Participante: " + asistenteSeleccionado.obtenerNombreCompleto() + "\n" +
                           "Email: " + asistenteSeleccionado.getEmail() + "\n" +
                           "Teléfono: " + asistenteSeleccionado.getTelefono() + "\n\n" +
                           "Esta acción:\n" +
                           "• Eliminará al participante del sistema\n" +
                           "• Lo removerá de todos los eventos\n" +
                           "• NO se puede deshacer\n\n" +
                           "¿Está completamente seguro de continuar?";
            
            int opcion = JOptionPane.showConfirmDialog(this, 
                mensaje, 
                "Confirmar Eliminación Permanente", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
                
            if (opcion == JOptionPane.YES_OPTION) {
                asistentesController.eliminarParticipante(asistenteSeleccionado);
                
                JOptionPane.showMessageDialog(this, 
                    "Participante eliminado exitosamente\n\n" +
                    "El asistente " + asistenteSeleccionado.obtenerNombreCompleto() + 
                    " ha sido removido permanentemente del sistema.", 
                    "Eliminación Completada", JOptionPane.INFORMATION_MESSAGE);
                
                // Volver automáticamente al menú anterior
                dispose();
            }
            
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, 
                "No se puede eliminar este participante:\n\n" + ex.getMessage() + 
                "\n\nPrimero desinscríbalo de todos los eventos.", 
                "Eliminación Bloqueada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al eliminar el asistente:\n" + ex.getMessage(), 
                "Error de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarAsistentes() {
        cmbAsistentes.removeAllItems();
        cmbAsistentes.addItem(null);
        for (Asistentes asistente : asistentesController.obtenerTodosLosParticipantes()) {
            cmbAsistentes.addItem(asistente);
        }
    }
    
    private void actualizarDetalles() {
        Asistentes asistente = obtenerAsistenteSeleccionado();
        if (asistente != null) {
            StringBuilder detalles = new StringBuilder();
            detalles.append("INFORMACIÓN PERSONAL\n");
            detalles.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
            detalles.append("Nombre completo: ").append(asistente.obtenerNombreCompleto()).append("\n");
            detalles.append("Correo electrónico: ").append(asistente.getEmail()).append("\n");
            detalles.append("Teléfono: ").append(asistente.getTelefono()).append("\n\n");
            detalles.append("ADVERTENCIA: Esta información será eliminada permanentemente\n");
            detalles.append("y el participante será removido de todos los eventos.");
            
            areaDetalles.setText(detalles.toString());
            
            agregarBarraEstado("Seleccionado: " + asistente.obtenerNombreCompleto(), 
                "Verifique los datos antes de eliminar");
        } else {
            areaDetalles.setText("Seleccione un asistente para ver sus detalles...");
            agregarBarraEstado("Eliminación de Asistente", "Seleccione el participante a eliminar");
        }
    }
    
    private Asistentes obtenerAsistenteSeleccionado() {
        return (Asistentes) cmbAsistentes.getSelectedItem();
    }
}
