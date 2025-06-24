package Views.Recursos.Asistentes;

import Controllers.AsistentesController;
import Models.Asistentes;
import Views.BaseView;
import java.awt.*;
import javax.swing.*;

public class EditarAsistente extends BaseView {
    private final AsistentesController asistentesController = AsistentesController.getInstance();
    private JComboBox<Asistentes> comboAsistentes;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtTelefono;

    public EditarAsistente() {
        configurarVentanaBase("Modificar Datos de Asistente", 650, 700);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Edición de Participante",
            "Seleccione un asistente y modifique su información personal"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelFormulario = crearPanelOpciones("Datos del Participante");
        
        panelFormulario.add(crearCampoSeleccion());
        panelFormulario.add(crearEspaciado(20));
        panelFormulario.add(crearCampoNombre());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearCampoApellido());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearCampoEmail());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearCampoTelefono());
        
        panelPrincipal.add(panelFormulario);
        panelPrincipal.add(crearEspaciado(30));
        
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(crearEspaciado(20));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        agregarBarraEstado("Edición de Asistente", "Seleccione un participante para modificar sus datos");
        
        cargarAsistentesEnCombo();
    }
    
    private JPanel crearCampoSeleccion() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Seleccionar Asistente:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        comboAsistentes = new JComboBox<>();
        comboAsistentes.setPreferredSize(new Dimension(300, 35));
        comboAsistentes.setFont(new Font("Arial", Font.PLAIN, 13));
        comboAsistentes.addActionListener(e -> cargarDetalles());
        
        JLabel ayuda = new JLabel("Elija el participante cuyos datos desea modificar");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(comboAsistentes, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearCampoNombre() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Nombre:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(300, 35));
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel ayuda = new JLabel("Nombre completo del participante");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(txtNombre, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearCampoApellido() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Apellido:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        txtApellido = new JTextField();
        txtApellido.setPreferredSize(new Dimension(300, 35));
        txtApellido.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel ayuda = new JLabel("Apellidos del participante");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(txtApellido, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearCampoEmail() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Correo Electrónico:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(300, 35));
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel ayuda = new JLabel("Dirección de email válida para notificaciones");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(txtEmail, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearCampoTelefono() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Teléfono:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        txtTelefono = new JTextField();
        txtTelefono.setPreferredSize(new Dimension(300, 35));
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel ayuda = new JLabel("Número de teléfono de contacto");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(txtTelefono, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(COLOR_FONDO);
        
        JButton btnGuardar = crearBotonSimple("Actualizar Datos", 
            "Guarda los cambios realizados", COLOR_PRINCIPAL, e -> editarAsistente());
        
        JButton btnVolver = crearBotonSimple("Volver al Menú", 
            "Regresa al menú de participantes", COLOR_SECUNDARIO, e -> dispose());
        
        JButton btnCancelar = crearBotonSimple("Cancelar", 
            "Cancela la operación sin guardar cambios", COLOR_SECUNDARIO, e -> dispose());
        
        panel.add(btnGuardar);
        panel.add(btnVolver);
        panel.add(btnCancelar);
        
        return panel;
    }

    private void editarAsistente() {
        try {
            Asistentes asistenteSeleccionado = obtenerAsistenteSeleccionado();
            if (asistenteSeleccionado == null) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor seleccione un asistente para editar.", 
                    "Asistente No Seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String email = txtEmail.getText().trim();
            String telefono = txtTelefono.getText().trim();

            if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor complete todos los campos obligatorios.", 
                    "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validación básica de email
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor ingrese una dirección de email válida.", 
                    "Email Inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            asistentesController.actualizarInformacionParticipante(asistenteSeleccionado, nombre, apellido, email, telefono);
            
            JOptionPane.showMessageDialog(this, 
                "¡Datos actualizados exitosamente!\n\n" +
                "Participante: " + nombre + " " + apellido + "\n" +
                "Email: " + email + "\n" +
                "Los cambios han sido guardados en el sistema.", 
                "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
            
            // Volver automáticamente al menú anterior
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar los datos:\n" + ex.getMessage(), 
                "Error de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarAsistentesEnCombo() {
        comboAsistentes.removeAllItems();
        comboAsistentes.addItem(null);
        for (Asistentes asistente : asistentesController.obtenerTodosLosParticipantes()) {
            comboAsistentes.addItem(asistente);
        }
    }

    private void cargarDetalles() {
        Asistentes asistente = obtenerAsistenteSeleccionado();
        if (asistente != null) {
            txtNombre.setText(asistente.getNombre());
            txtApellido.setText(asistente.getApellido());
            txtEmail.setText(asistente.getEmail());
            txtTelefono.setText(asistente.getTelefono());
            
            agregarBarraEstado("Editando: " + asistente.obtenerNombreCompleto(), 
                "Modifique los campos y presione 'Actualizar Datos' para guardar");
        } else {
            txtNombre.setText("");
            txtApellido.setText("");
            txtEmail.setText("");
            txtTelefono.setText("");
            
            agregarBarraEstado("Edición de Asistente", "Seleccione un participante para modificar sus datos");
        }
    }

    private Asistentes obtenerAsistenteSeleccionado() {
        return (Asistentes) comboAsistentes.getSelectedItem();
    }
}
