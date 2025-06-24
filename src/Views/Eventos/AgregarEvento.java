package Views.Eventos;

import Controllers.EventosController;
import Controllers.SalonController;
import Models.Salon;
import Views.BaseView;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AgregarEvento extends BaseView {
    private final EventosController eventosController = EventosController.getInstance();
    private final SalonController salonController = SalonController.getInstance();
    private JComboBox<Salon> comboSalon;
    private JDateChooser dateChooser;
    private JTextField txtDescripcion;

    public AgregarEvento() {
        configurarVentanaBase("Crear Nuevo Evento", 700, 750);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        // Panel principal usando método de la clase base
        JPanel panelPrincipal = crearPanelPrincipal();
        
        // Encabezado usando método de la clase base
        JPanel encabezado = crearEncabezadoSeccion(
            "Registro de Nuevo Evento",
            "Complete la información para crear un evento en el sistema"
        );
        panelPrincipal.add(encabezado);
        
        // Panel de formulario usando método de la clase base
        JPanel panelFormulario = crearPanelOpciones("Datos del Evento");
        
        // Crear campos del formulario con diseño moderno
        panelFormulario.add(crearCampoFecha());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearCampoSalon());
        panelFormulario.add(crearEspaciado(15));
        panelFormulario.add(crearCampoDescripcion());
        
        panelPrincipal.add(panelFormulario);
        panelPrincipal.add(crearEspaciado(30));
        
        // Panel de botones usando método de la clase base
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(crearEspaciado(20));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Barra de estado usando método de la clase base
        agregarBarraEstado("Creación de Evento", "Complete todos los campos requeridos");
    }
    
    private JPanel crearCampoFecha() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Fecha del Evento:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(200, 35));
        dateChooser.setFont(new Font("Arial", Font.PLAIN, 13));
        dateChooser.addPropertyChangeListener("date", evt -> {
            Date fecha = dateChooser.getDate();
            cargarSalones(fecha);
        });
        
        JLabel ayuda = new JLabel("Seleccione la fecha cuando se realizará el evento");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(dateChooser, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearCampoSalon() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(COLOR_PANEL);
        
        JLabel etiqueta = new JLabel("Salón Disponible:");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(COLOR_TEXTO_TITULO);
        
        comboSalon = new JComboBox<>();
        comboSalon.setPreferredSize(new Dimension(200, 35));
        comboSalon.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel ayuda = new JLabel("Seleccione un salón disponible para la fecha elegida");
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
        txtDescripcion.setPreferredSize(new Dimension(200, 35));
        txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel ayuda = new JLabel("Escriba una descripción detallada del evento");
        ayuda.setFont(new Font("Arial", Font.ITALIC, 11));
        ayuda.setForeground(COLOR_SECUNDARIO);
        
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(txtDescripcion, BorderLayout.CENTER);
        panel.add(ayuda, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(COLOR_FONDO);
        
        JButton btnGuardar = crearBotonSimple("Crear Evento", 
            "Registra el nuevo evento en el sistema", COLOR_PRINCIPAL, e -> agregarEvento());
        
        JButton btnVolver = crearBotonSimple("Volver al Menú", 
            "Regresa al menú de eventos", COLOR_SECUNDARIO, e -> dispose());
        
        JButton btnCancelar = crearBotonSimple("Cancelar", 
            "Cancela la operación y cierra la ventana", COLOR_SECUNDARIO, e -> dispose());
        
        panel.add(btnGuardar);
        panel.add(btnVolver);
        panel.add(btnCancelar);
        
        return panel;
    }
    
    private void agregarEvento() {
        try {
            Date fecha = dateChooser.getDate();
            Salon salon = (Salon) comboSalon.getSelectedItem();
            String descripcion = txtDescripcion.getText().trim();

            if (fecha == null || salon == null || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Debe seleccionar una fecha, un salón y escribir una descripción", 
                    "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            eventosController.crearNuevoEvento(fecha, generarNombreEvento(fecha, salon.getNombre()), salon, descripcion);
            
            JOptionPane.showMessageDialog(this, 
                "¡Evento creado exitosamente!\n\nEl evento ha sido registrado en el sistema.", 
                "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            
            // Volver automáticamente al menú anterior
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear el evento:\n" + ex.getMessage(), 
                "Error de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarSalones(Date fecha) {
        comboSalon.removeAllItems();
        comboSalon.addItem(null);
        if (fecha != null) {
            List<Salon> salonesDisponibles = salonController.obtenerEspaciosDisponibles(fecha);
            for (Salon salon : salonesDisponibles) {
                comboSalon.addItem(salon);
            }
        }
    }

    private String generarNombreEvento(Date fecha, String salon) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String formattedDate = dateFormat.format(fecha);
        return formattedDate + "-" + salon;
    }
}
