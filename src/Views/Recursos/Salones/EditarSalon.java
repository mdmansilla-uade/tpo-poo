package Views.Recursos.Salones;

import Controllers.SalonController;
import Models.Salon;
import Views.BaseView;
import java.awt.*;
import javax.swing.*;

public class EditarSalon extends BaseView {
    private final SalonController salonController = SalonController.getInstance();
    private JComboBox<Salon> comboSalones;
    private JTextField txtNombre;
    private JTextField txtCapacidad;
    private JTextField txtUbicacion;

    public EditarSalon() {
        configurarVentanaBase("Modificar Datos de Salón", 650, 700);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Edición de Espacio",
            "Seleccione un salón y modifique sus características"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelFormulario = crearPanelOpciones("Configuración del Salón");
        
        panelFormulario.add(crearCampoSeleccion());
        panelFormulario.add(crearEspaciado(20));
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
        
        agregarBarraEstado("Edición de Salón", "Seleccione un espacio para modificar sus datos");
        
        cargarSalonesEnCombo();
    }
    
    private JPanel crearCampoSeleccion() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Seleccionar Salón:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        comboSalones = new JComboBox<>();
        comboSalones.setPreferredSize(new Dimension(300, 35));
        comboSalones.setFont(new Font("Arial", Font.PLAIN, 13));
        comboSalones.addActionListener(e -> cargarDetalles());
        
        JLabel ayuda = new JLabel("Elija el salón cuyas características desea modificar");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(comboSalones, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearCampoNombre() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Nombre del Salón:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(300, 35));
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel ayuda = new JLabel("Nombre descriptivo del espacio");
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
        txtCapacidad.setPreferredSize(new Dimension(300, 35));
        txtCapacidad.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel ayuda = new JLabel("Número máximo de personas que puede albergar");
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
        txtUbicacion.setPreferredSize(new Dimension(300, 35));
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
        
        JButton btnGuardar = crearBotonSimple("Actualizar Salón", 
            "Guarda los cambios realizados", COLOR_PRINCIPAL, e -> editarSalon());
        
        JButton btnVolver = crearBotonSimple("Volver al Menú", 
            "Regresa al menú de espacios", COLOR_SECUNDARIO, e -> dispose());
        
        JButton btnCancelar = crearBotonSimple("Cancelar", 
            "Cancela la operación sin guardar cambios", COLOR_SECUNDARIO, e -> dispose());
        
        panel.add(btnGuardar);
        panel.add(btnVolver);
        panel.add(btnCancelar);
        
        return panel;
    }

    private void cargarSalonesEnCombo() {
        comboSalones.removeAllItems();
        comboSalones.addItem(null);
        for (Salon salon : salonController.obtenerTodosLosEspacios()) {
            comboSalones.addItem(salon);
        }
    }

    private void cargarDetalles() {
        Salon salon = obtenerSalonSeleccionado();
        if (salon != null) {
            txtNombre.setText(salon.getNombre());
            txtCapacidad.setText(String.valueOf(salon.getCapacidad()));
            txtUbicacion.setText(salon.getUbicacion());
            
            agregarBarraEstado("Editando: " + salon.getNombre(), 
                "Capacidad actual: " + salon.getCapacidad() + " personas | Modifique los campos necesarios");
        } else {
            txtNombre.setText("");
            txtCapacidad.setText("");
            txtUbicacion.setText("");
            
            agregarBarraEstado("Edición de Salón", "Seleccione un espacio para modificar sus datos");
        }
    }

    private void editarSalon() {
        try {
            Salon salonSeleccionado = obtenerSalonSeleccionado();
            if (salonSeleccionado == null) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor seleccione un salón para editar.", 
                    "Salón No Seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String nombre = txtNombre.getText().trim();
            String capacidadTexto = txtCapacidad.getText().trim();
            String ubicacion = txtUbicacion.getText().trim();

            if (nombre.isEmpty() || capacidadTexto.isEmpty() || ubicacion.isEmpty()) {
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
            
            salonController.actualizarInformacionEspacio(salonSeleccionado, nombre, capacidad, ubicacion);
            
            JOptionPane.showMessageDialog(this, 
                "¡Salón actualizado exitosamente!\n\n" +
                "Nombre: " + nombre + "\n" +
                "Capacidad: " + capacidad + " personas\n" +
                "Ubicación: " + ubicacion + "\n" +
                "Los cambios han sido guardados en el sistema.", 
                "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
            
            // Volver automáticamente al menú anterior
            dispose();
            
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, 
                "No se puede modificar este salón:\n\n" + ex.getMessage() + 
                "\n\nEl salón está siendo utilizado en eventos activos.", 
                "Modificación Bloqueada", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error de validación:\n" + ex.getMessage(), 
                "Error de Validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar el salón:\n" + ex.getMessage(), 
                "Error de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Salon obtenerSalonSeleccionado() {
        return (Salon) comboSalones.getSelectedItem();
    }
}
