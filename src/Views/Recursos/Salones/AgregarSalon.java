package Views.Recursos.Salones;

import Controllers.SalonController;
import Views.BaseView;
import java.awt.*;
import javax.swing.*;

public class AgregarSalon extends BaseView {
    private final SalonController salonController = SalonController.getInstance();
    private JTextField txtNombre;
    private JTextField txtCapacidad;
    private JTextField txtUbicacion;

    public AgregarSalon() {
        configurarVentanaBase("Registrar Nuevo Salón", 600, 550);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Registro de Nuevo Espacio",
            "Configure las características del salón a agregar al sistema"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelFormulario = crearPanelOpciones("Datos del Salón");
        
        panelFormulario.add(crearCampoNombre());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearCampoCapacidad());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearCampoUbicacion());
        
        panelPrincipal.add(panelFormulario);
        panelPrincipal.add(crearEspaciado(30));
        
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(crearEspaciado(20));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        agregarBarraEstado("Registro de Salón", "Configure nombre, capacidad y ubicación");
    }
    
    private JPanel crearCampoNombre() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Nombre del Salón:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(200, 35));
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel ayuda = new JLabel("Nombre descriptivo del espacio (ej: Auditorio Principal)");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(txtNombre, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearCampoCapacidad() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Capacidad Máxima:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        txtCapacidad = new JTextField();
        txtCapacidad.setPreferredSize(new Dimension(200, 35));
        txtCapacidad.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel ayuda = new JLabel("Número máximo de personas que puede albergar el salón");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(txtCapacidad, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearCampoUbicacion() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Ubicación:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        txtUbicacion = new JTextField();
        txtUbicacion.setPreferredSize(new Dimension(200, 35));
        txtUbicacion.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel ayuda = new JLabel("Dirección o ubicación física del salón");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(txtUbicacion, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(COLOR_FONDO);
        
        JButton btnGuardar = crearBotonSimple("Registrar Salón", 
            "Guarda el nuevo espacio en el sistema", COLOR_PRINCIPAL, e -> agregarSalon());
        
        JButton btnVolver = crearBotonSimple("Volver al Menú", 
            "Regresa al menú de espacios", COLOR_SECUNDARIO, e -> dispose());
        
        JButton btnCancelar = crearBotonSimple("Cancelar", 
            "Cancela la operación y cierra la ventana", COLOR_SECUNDARIO, e -> dispose());
        
        panel.add(btnGuardar);
        panel.add(btnVolver);
        panel.add(btnCancelar);
        
        return panel;
    }

    private void agregarSalon() {
        try {
            String nombre = txtNombre.getText().trim();
            String ubicacion = txtUbicacion.getText().trim();
            String capacidadTexto = txtCapacidad.getText().trim();

            if (nombre.isEmpty() || ubicacion.isEmpty() || capacidadTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor complete todos los campos obligatorios.", 
                    "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int capacidad;
            try {
                capacidad = Integer.parseInt(capacidadTexto);
                if (capacidad <= 0) {
                    JOptionPane.showMessageDialog(this, 
                        "La capacidad debe ser un número mayor a cero.", 
                        "Capacidad Inválida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "La capacidad debe ser un número entero válido.", 
                    "Formato Incorrecto", JOptionPane.WARNING_MESSAGE);
                return;
            }

            salonController.registrarNuevoEspacio(nombre, capacidad, ubicacion);
            
            JOptionPane.showMessageDialog(this, 
                "¡Salón registrado exitosamente!\n\n" +
                "Nombre: " + nombre + "\n" +
                "Capacidad: " + capacidad + " personas\n" +
                "Ubicación: " + ubicacion + "\n" +
                "El espacio está disponible para eventos.", 
                "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            // Volver automáticamente al menú anterior
            dispose();
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                "Error de validación:\n" + e.getMessage(), 
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al registrar el salón:\n" + e.getMessage(), 
                "Error de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }
}
