package Views.Recursos.Asistentes;

import Controllers.AsistentesController;
import Views.BaseView;
import java.awt.*;
import javax.swing.*;

public class AgregarAsistente extends BaseView {
    private final AsistentesController asistentesController = AsistentesController.getInstance();
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtMail;
    private JTextField txtTelefono;

    public AgregarAsistente() {
        configurarVentanaBase("Registrar Nuevo Asistente", 600, 650);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Registro de Nuevo Participante",
            "Complete la información personal del asistente a registrar"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelFormulario = crearPanelOpciones("Datos Personales");
        
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
        
        agregarBarraEstado("Registro de Asistente", "Complete todos los campos para crear el perfil");
    }
    
    private JPanel crearCampoNombre() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Nombre:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(200, 35));
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
        txtApellido.setPreferredSize(new Dimension(200, 35));
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
        
        txtMail = new JTextField();
        txtMail.setPreferredSize(new Dimension(200, 35));
        txtMail.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel ayuda = new JLabel("Dirección de email válida para notificaciones");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(txtMail, BorderLayout.CENTER);
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
        txtTelefono.setPreferredSize(new Dimension(200, 35));
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
        
        JButton btnGuardar = crearBotonSimple("Registrar Asistente", 
            "Guarda el nuevo participante en el sistema", COLOR_PRINCIPAL, e -> agregarAsistente());
        
        JButton btnVolver = crearBotonSimple("Volver al Menú", 
            "Regresa al menú de participantes", COLOR_SECUNDARIO, e -> dispose());
        
        JButton btnCancelar = crearBotonSimple("Cancelar", 
            "Cancela la operación y cierra la ventana", COLOR_SECUNDARIO, e -> dispose());
        
        panel.add(btnGuardar);
        panel.add(btnVolver);
        panel.add(btnCancelar);
        
        return panel;
    }

    private void agregarAsistente() {
        try {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String mail = txtMail.getText().trim();
            String telefono = txtTelefono.getText().trim();

            if (nombre.isEmpty() || apellido.isEmpty() || mail.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor complete todos los campos obligatorios.", 
                    "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validación básica de email
            if (!mail.contains("@") || !mail.contains(".")) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor ingrese una dirección de email válida.", 
                    "Email Inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            asistentesController.registrarNuevoParticipante(nombre, apellido, mail, telefono);
            
            JOptionPane.showMessageDialog(this, 
                "¡Asistente registrado exitosamente!\n\n" +
                "Nombre: " + nombre + " " + apellido + "\n" +
                "Email: " + mail + "\n" +
                "El participante ha sido agregado al sistema.", 
                "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            // Volver automáticamente al menú anterior
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al registrar el asistente:\n" + ex.getMessage(), 
                "Error de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }
}
