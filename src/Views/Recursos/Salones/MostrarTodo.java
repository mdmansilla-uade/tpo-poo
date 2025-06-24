package Views.Recursos.Salones;

import Models.Salon;
import Models.Eventos;
import Views.BaseView;
import Controllers.SalonController;
import Controllers.EventosController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MostrarTodo extends BaseView {
    private List<Salon> salones;
    private JTable tabla;
    private final SalonController salonController = SalonController.getInstance();
    private final EventosController eventosController = EventosController.getInstance();

    public MostrarTodo(List<Salon> salones) {
        this.salones = salones;
        configurarVentanaBase("Listado de Espacios", 950, 700);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Inventario de Espacios",
            "Consulte información completa de todos los salones disponibles"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelTabla = crearPanelOpciones("Espacios Registrados");
        
        crearTablaSalones();
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(850, 300));
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        
        panelTabla.add(scrollPane);
        panelPrincipal.add(panelTabla);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelAnalisis = crearPanelAnalisisCapacidades();
        panelPrincipal.add(panelAnalisis);
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(crearEspaciado(20));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        int capacidadTotal = salones.stream().mapToInt(Salon::getCapacidad).sum();
        agregarBarraEstado("Inventario de Espacios", 
            "Total: " + salones.size() + " salones | Capacidad total: " + capacidadTotal + " personas");
    }
    
    private void crearTablaSalones() {
        String[] columnas = {"#", "Nombre", "Capacidad", "Ubicación", "Estado", "Eventos"};
        
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (int i = 0; i < salones.size(); i++) {
            Salon salon = salones.get(i);
            
            long eventosEnSalon = eventosController.obtenerTodosLosEventos().stream()
                .filter(evento -> evento.getSalon().equals(salon))
                .count();
            
            String estado = eventosEnSalon > 0 ? "En Uso" : "Disponible";
            
            Object[] fila = {
                i + 1,
                salon.getNombre(),
                salon.getCapacidad() + " personas",
                salon.getUbicacion(),
                estado,
                eventosEnSalon + " eventos"
            };
            model.addRow(fila);
        }
        
        tabla = new JTable(model);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        tabla.setGridColor(COLOR_BORDE);
        tabla.setSelectionBackground(COLOR_PRINCIPAL.brighter());
        tabla.setSelectionForeground(COLOR_TEXTO_BLANCO);
        
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);  // #
        tabla.getColumnModel().getColumn(1).setPreferredWidth(180); // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(100); // Capacidad
        tabla.getColumnModel().getColumn(3).setPreferredWidth(200); // Ubicación
        tabla.getColumnModel().getColumn(4).setPreferredWidth(80);  // Estado
        tabla.getColumnModel().getColumn(5).setPreferredWidth(80);  // Eventos
        
        // Centrar algunas columnas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // #
        tabla.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Capacidad
        tabla.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Estado
        tabla.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Eventos
        
        // Colorear estado
        DefaultTableCellRenderer estadoRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                         boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected && column == 4) { // Columna Estado
                    if ("En Uso".equals(value)) {
                        c.setBackground(COLOR_PELIGRO.brighter().brighter());
                        c.setForeground(COLOR_PELIGRO.darker());
                    } else {
                        c.setBackground(COLOR_PRINCIPAL.brighter().brighter());
                        c.setForeground(COLOR_PRINCIPAL.darker());
                    }
                } else if (!isSelected) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        };
        tabla.getColumnModel().getColumn(4).setCellRenderer(estadoRenderer);
        
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(COLOR_PRINCIPAL);
        tabla.getTableHeader().setForeground(COLOR_TEXTO_BLANCO);
        tabla.getTableHeader().setReorderingAllowed(false);
    }
    
    private JPanel crearPanelAnalisisCapacidades() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(COLOR_FONDO);
        
        JLabel tituloAnalisis = new JLabel("Análisis de Capacidades");
        tituloAnalisis.setFont(new Font("Arial", Font.BOLD, 16));
        tituloAnalisis.setForeground(COLOR_TEXTO_TITULO);
        tituloAnalisis.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelPrincipal.add(tituloAnalisis);
        panelPrincipal.add(crearEspaciado(10));
        
        JPanel panelMetricas = new JPanel(new GridLayout(1, 4, 15, 0));
        panelMetricas.setBackground(COLOR_FONDO);
        
        int totalSalones = salones.size();
        int capacidadTotal = salones.stream().mapToInt(Salon::getCapacidad).sum();
        double capacidadPromedio = totalSalones > 0 ? (double) capacidadTotal / totalSalones : 0;
        int capacidadMaxima = salones.stream().mapToInt(Salon::getCapacidad).max().orElse(0);
        
        long salonesEnUso = salones.stream()
            .filter(salon -> eventosController.obtenerTodosLosEventos().stream()
                .anyMatch(evento -> evento.getSalon().equals(salon)))
            .count();
        
        double porcentajeUtilizacion = totalSalones > 0 ? (double) salonesEnUso / totalSalones * 100 : 0;
        
        panelMetricas.add(crearTarjetaMetrica("Total Espacios", 
            String.valueOf(totalSalones), "", COLOR_PRINCIPAL));
        
        panelMetricas.add(crearTarjetaMetrica("Capacidad Total", 
            capacidadTotal + " personas", "", COLOR_SECUNDARIO));
        
        panelMetricas.add(crearTarjetaMetrica("Capacidad Promedio", 
            String.format("%.0f personas", capacidadPromedio), "", COLOR_PRINCIPAL));
        
        panelMetricas.add(crearTarjetaMetrica("Utilización", 
            String.format("%.1f%%", porcentajeUtilizacion), "", 
            porcentajeUtilizacion > 70 ? COLOR_PELIGRO : COLOR_SECUNDARIO));
        
        panelPrincipal.add(panelMetricas);
        
        return panelPrincipal;
    }
    
    private JPanel crearTarjetaMetrica(String titulo, String valor, String icono, Color color) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(color);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblIcono = new JLabel(icono);
        lblIcono.setFont(new Font("Arial", Font.BOLD, 24));
        lblIcono.setForeground(COLOR_TEXTO_BLANCO);
        lblIcono.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
        panelTexto.setBackground(color);
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 12));
        lblTitulo.setForeground(COLOR_TEXTO_BLANCO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.BOLD, 16));
        lblValor.setForeground(COLOR_TEXTO_BLANCO);
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelTexto.add(lblTitulo);
        panelTexto.add(Box.createRigidArea(new Dimension(0, 5)));
        panelTexto.add(lblValor);
        
        tarjeta.add(lblIcono, BorderLayout.NORTH);
        tarjeta.add(panelTexto, BorderLayout.CENTER);
        
        return tarjeta;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(COLOR_FONDO);
        
        JButton btnActualizar = crearBotonSimple("Actualizar Lista", 
            "Refresca los datos de la tabla", COLOR_PRINCIPAL, e -> actualizarTabla());
        
        JButton btnAnalisisCompleto = crearBotonSimple("Análisis Completo", 
            "Muestra análisis detallado de capacidades", COLOR_SECUNDARIO, e -> mostrarAnalisisCompleto());
        
        JButton btnVolver = crearBotonSimple("Volver al Menú", 
            "Regresa al menú de espacios", COLOR_SECUNDARIO, e -> dispose());
        
        JButton btnCerrar = crearBotonSimple("Cerrar", 
            "Cierra esta ventana", COLOR_SECUNDARIO, e -> dispose());
        
        panel.add(btnActualizar);
        panel.add(btnAnalisisCompleto);
        panel.add(btnVolver);
        panel.add(btnCerrar);
        
        return panel;
    }
    
    private void actualizarTabla() {
        salones = salonController.obtenerTodosLosEspacios();
        
        Container parent = tabla.getParent().getParent().getParent();
        parent.remove(tabla.getParent().getParent());
        
        crearTablaSalones();
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(850, 300));
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        
        parent.add(scrollPane);
        parent.revalidate();
        parent.repaint();
        
        int capacidadTotal = salones.stream().mapToInt(Salon::getCapacidad).sum();
        agregarBarraEstado("Listado Actualizado", 
            "Total: " + salones.size() + " salones | Capacidad: " + capacidadTotal + " personas");
    }
    
    private void mostrarAnalisisCompleto() {
        StringBuilder analisis = new StringBuilder();
        analisis.append("ANÁLISIS COMPLETO DE CAPACIDADES\n");
        analisis.append("═══════════════════════════════════════════════\n\n");
        
        int totalSalones = salones.size();
        int capacidadTotal = salones.stream().mapToInt(Salon::getCapacidad).sum();
        double capacidadPromedio = totalSalones > 0 ? (double) capacidadTotal / totalSalones : 0;
        int capacidadMaxima = salones.stream().mapToInt(Salon::getCapacidad).max().orElse(0);
        int capacidadMinima = salones.stream().mapToInt(Salon::getCapacidad).min().orElse(0);
        
        analisis.append("MÉTRICAS GENERALES:\n");
        analisis.append("• Total de espacios: ").append(totalSalones).append("\n");
        analisis.append("• Capacidad total del sistema: ").append(capacidadTotal).append(" personas\n");
        analisis.append("• Capacidad promedio: ").append(String.format("%.1f", capacidadPromedio)).append(" personas\n");
        analisis.append("• Capacidad máxima: ").append(capacidadMaxima).append(" personas\n");
        analisis.append("• Capacidad mínima: ").append(capacidadMinima).append(" personas\n\n");
        
        List<Eventos> todosLosEventos = eventosController.obtenerTodosLosEventos();
        Map<Salon, Long> utilizacionPorSalon = todosLosEventos.stream()
            .collect(Collectors.groupingBy(Eventos::getSalon, Collectors.counting()));
        
        long salonesEnUso = utilizacionPorSalon.size();
        long salonesLibres = totalSalones - salonesEnUso;
        double porcentajeUtilizacion = totalSalones > 0 ? (double) salonesEnUso / totalSalones * 100 : 0;
        
        analisis.append("NÁLISIS DE UTILIZACIÓN:\n");
        analisis.append("• Salones en uso: ").append(salonesEnUso).append(" (").append(String.format("%.1f%%", porcentajeUtilizacion)).append(")\n");
        analisis.append("• Salones disponibles: ").append(salonesLibres).append(" (").append(String.format("%.1f%%", 100 - porcentajeUtilizacion)).append(")\n");
        
        analisis.append("\nRANKING DE DEMANDA:\n");
        utilizacionPorSalon.entrySet().stream()
            .sorted(Map.Entry.<Salon, Long>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> {
                Salon salon = entry.getKey();
                Long eventos = entry.getValue();
                analisis.append("• ").append(salon.getNombre())
                        .append(" (").append(salon.getCapacidad()).append(" pers.): ")
                        .append(eventos).append(" eventos\n");
            });
        
        analisis.append("\nDISTRIBUCIÓN POR CAPACIDAD:\n");
        Map<String, Long> distribucionCapacidad = salones.stream()
            .collect(Collectors.groupingBy(salon -> {
                int cap = salon.getCapacidad();
                if (cap <= 20) return "Pequeño (≤20)";
                else if (cap <= 50) return "Mediano (21-50)";
                else if (cap <= 100) return "Grande (51-100)";
                else return "Muy Grande (>100)";
            }, Collectors.counting()));
        
        distribucionCapacidad.forEach((categoria, cantidad) -> 
            analisis.append("• ").append(categoria).append(": ").append(cantidad).append(" salones\n"));
        
        analisis.append("\nRECOMENDACIONES:\n");
        if (porcentajeUtilizacion > 80) {
            analisis.append("ALTA DEMANDA: Considere agregar más espacios\n");
        } else if (porcentajeUtilizacion > 50) {
            analisis.append("DEMANDA MODERADA: Utilización balanceada\n");
        } else {
            analisis.append("BAJA DEMANDA: Capacidad disponible suficiente\n");
        }
        
        if (capacidadMaxima > capacidadPromedio * 3) {
            analisis.append("• Considere dividir salones muy grandes en espacios menores\n");
        }
        
        if (salonesLibres > totalSalones * 0.3) {
            analisis.append("• Oportunidad de incrementar la programación de eventos\n");
        }
        
        JDialog dialogo = new JDialog(this, "Análisis Completo de Capacidades", true);
        dialogo.setSize(700, 600);
        dialogo.setLocationRelativeTo(this);
        
        JTextArea areaTexto = new JTextArea(analisis.toString());
        areaTexto.setFont(new Font("Consolas", Font.PLAIN, 12));
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
