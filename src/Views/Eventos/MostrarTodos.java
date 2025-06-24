package Views.Eventos;

import Models.Asistentes;
import Models.Eventos;
import Views.BaseView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class MostrarTodos extends BaseView {
    private List<Eventos> eventos;
    private JTable tabla;

    public MostrarTodos(List<Eventos> eventos) {
        this.eventos = eventos;
        configurarVentanaBase("Listado de Eventos", 1000, 700);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Lista de Eventos Registrados",
            "Consulte información detallada de todos los eventos del sistema"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelTabla = crearPanelOpciones("Eventos Disponibles");
        
        crearTablaEventos();
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        
        panelTabla.add(scrollPane);
        panelPrincipal.add(panelTabla);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelEstadisticas = crearPanelEstadisticas();
        panelPrincipal.add(panelEstadisticas);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(crearEspaciado(20));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        agregarBarraEstado("Listado de Eventos", 
            "Total: " + eventos.size() + " eventos | Click en 'Ver Asistentes' para detalles");
    }
    
    private void crearTablaEventos() {
        String[] columnas = {"Evento", "Salón", "Fecha", "Descripción", "Asistentes", "Acciones"};
        
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Eventos evento : eventos) {
            Object[] fila = {
                evento.getNombre(),
                evento.getSalon().getNombre(),
                dateFormat.format(evento.getFecha()),
                evento.getDescripcion(),
                evento.getAsistentes() != null ? evento.getAsistentes().size() : 0,
                "Ver Detalles"
            };
            model.addRow(fila);
        }
        
        tabla = new JTable(model);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        tabla.setGridColor(COLOR_BORDE);
        tabla.setSelectionBackground(COLOR_PRINCIPAL.brighter());
        tabla.setSelectionForeground(Color.WHITE);
        
        tabla.getColumnModel().getColumn(0).setPreferredWidth(150); // Evento
        tabla.getColumnModel().getColumn(1).setPreferredWidth(120); // Salón
        tabla.getColumnModel().getColumn(2).setPreferredWidth(80);  // Fecha
        tabla.getColumnModel().getColumn(3).setPreferredWidth(200); // Descripción
        tabla.getColumnModel().getColumn(4).setPreferredWidth(80);  // Asistentes
        tabla.getColumnModel().getColumn(5).setPreferredWidth(100); // Acciones
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabla.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Fecha
        tabla.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Asistentes
        tabla.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Acciones
        
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(COLOR_PRINCIPAL);
        tabla.getTableHeader().setForeground(COLOR_TEXTO_BLANCO);
        tabla.getTableHeader().setReorderingAllowed(false);
        
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = tabla.columnAtPoint(e.getPoint());
                int row = tabla.rowAtPoint(e.getPoint());
                
                if (column == 5 && row >= 0 && row < eventos.size()) {
                    Eventos evento = eventos.get(row);
                    mostrarDetallesEvento(evento);
                }
            }
        });
    }
    
    private JPanel crearPanelEstadisticas() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(COLOR_FONDO);
        
        int totalEventos = eventos.size();
        int totalAsistentes = eventos.stream()
            .mapToInt(evento -> evento.getAsistentes() != null ? evento.getAsistentes().size() : 0)
            .sum();
        
        JLabel estadisticas = new JLabel("Estadísticas: " + totalEventos + " eventos | " + 
                                       totalAsistentes + " participantes registrados");
        estadisticas.setFont(new Font("Arial", Font.BOLD, 14));
        estadisticas.setForeground(COLOR_TEXTO_TITULO);
        
        panel.add(estadisticas);
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(COLOR_FONDO);
        
        JButton btnActualizar = crearBotonSimple("Actualizar Lista", 
            "Refresca los datos de la tabla", COLOR_PRINCIPAL, e -> actualizarTabla());
        
        JButton btnVolver = crearBotonSimple("Volver al Menú", 
            "Regresa al menú de eventos", COLOR_SECUNDARIO, e -> dispose());
        
        JButton btnCerrar = crearBotonSimple("Cerrar", 
            "Cierra esta ventana", COLOR_SECUNDARIO, e -> dispose());
        
        panel.add(btnActualizar);
        panel.add(btnVolver);
        panel.add(btnCerrar);
        
        return panel;
    }
    
    private void actualizarTabla() {        
        Container parent = tabla.getParent().getParent().getParent();
        parent.remove(tabla.getParent().getParent());
        
        crearTablaEventos();
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        
        parent.add(scrollPane);
        parent.revalidate();
        parent.repaint();
        
        agregarBarraEstado("Listado Actualizado", 
            "Total: " + eventos.size() + " eventos | Datos actualizados correctamente");
    }

    private void mostrarDetallesEvento(Eventos evento) {
        StringBuilder detalles = new StringBuilder();
        detalles.append("INFORMACIÓN DEL EVENTO\n\n");
        detalles.append("Nombre: ").append(evento.getNombre()).append("\n");
        detalles.append("Salón: ").append(evento.getSalon().getNombre()).append("\n");
        detalles.append("Fecha: ").append(new SimpleDateFormat("dd/MM/yyyy").format(evento.getFecha())).append("\n");
        detalles.append("Descripción: ").append(evento.getDescripcion()).append("\n\n");
        
        List<Asistentes> asistentes = evento.getAsistentes();
        if (asistentes == null || asistentes.isEmpty()) {
            detalles.append("PARTICIPANTES: Ninguno registrado");
        } else {
            detalles.append("PARTICIPANTES (").append(asistentes.size()).append("):\n\n");
            for (int i = 0; i < asistentes.size(); i++) {
                Asistentes asistente = asistentes.get(i);
                detalles.append(String.format("%d. %s %s\n   %s | %s\n\n", 
                    i + 1,
                    asistente.getNombre(), 
                    asistente.getApellido(),
                    asistente.getEmail(),
                    asistente.getTelefono()));
            }
        }
        
        JDialog dialogo = new JDialog(this, "Detalles del Evento: " + evento.getNombre(), true);
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(this);
        
        JTextArea areaTexto = new JTextArea(detalles.toString());
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 12));
        areaTexto.setEditable(false);
        areaTexto.setBackground(COLOR_PANEL);
        areaTexto.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JScrollPane scroll = new JScrollPane(areaTexto);
        
        JButton btnCerrarDialog = new JButton("Cerrar");
        btnCerrarDialog.addActionListener(e -> dialogo.dispose());
        
        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.add(btnCerrarDialog);
        
        dialogo.add(scroll, BorderLayout.CENTER);
        dialogo.add(panelBoton, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
}
