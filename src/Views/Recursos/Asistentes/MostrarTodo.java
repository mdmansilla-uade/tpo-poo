package Views.Recursos.Asistentes;

import Models.Asistentes;
import Views.BaseView;
import Controllers.AsistentesController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MostrarTodo extends BaseView {
    private List<Asistentes> asistentes;
    private JTable tabla;
    private final AsistentesController asistentesController = AsistentesController.getInstance();

    public MostrarTodo(List<Asistentes> asistentes) {
        this.asistentes = asistentes;
        configurarVentanaBase("Listado de Participantes", 950, 800);
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
            "Base de Datos de Participantes",
            "Consulte información completa de todos los asistentes registrados"
        );
        panelPrincipal.add(encabezado);
        
        // Panel de tabla usando método de la clase base
        JPanel panelTabla = crearPanelOpciones("Participantes Registrados");
        
        // Crear tabla con datos
        crearTablaAsistentes();
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(850, 300));
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        
        panelTabla.add(scrollPane);
        panelPrincipal.add(panelTabla);
        panelPrincipal.add(crearEspaciado(20));
        
        // Panel de estadísticas
        JPanel panelEstadisticas = crearPanelEstadisticas();
        panelPrincipal.add(panelEstadisticas);
        panelPrincipal.add(crearEspaciado(20));
        
        // Panel de botones usando método de la clase base
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(crearEspaciado(20));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Barra de estado usando método de la clase base
        agregarBarraEstado("Listado de Participantes", 
            "Total: " + asistentes.size() + " participantes registrados");
    }
    
    private void crearTablaAsistentes() {
        String[] columnas = {"#", "Nombre", "Apellido", "Email", "Teléfono"};
        
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (int i = 0; i < asistentes.size(); i++) {
            Asistentes asistente = asistentes.get(i);
            Object[] fila = {
                i + 1,
                asistente.getNombre(),
                asistente.getApellido(),
                asistente.getEmail(),
                asistente.getTelefono()
            };
            model.addRow(fila);
        }
        
        tabla = new JTable(model);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        tabla.setGridColor(COLOR_BORDE);
        tabla.setSelectionBackground(COLOR_PRINCIPAL.brighter());
        tabla.setSelectionForeground(COLOR_TEXTO_BLANCO);
        
        // Configurar anchos de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);  // #
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150); // Apellido
        tabla.getColumnModel().getColumn(3).setPreferredWidth(200); // Email
        tabla.getColumnModel().getColumn(4).setPreferredWidth(120); // Teléfono
        
        // Centrar algunas columnas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // #
        tabla.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Teléfono
        
        // ✅ CORRECCIÓN: Configurar encabezados con COLOR_PRINCIPAL
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(COLOR_PRINCIPAL);
        tabla.getTableHeader().setForeground(COLOR_TEXTO_BLANCO);
        tabla.getTableHeader().setReorderingAllowed(false);
    }
    
    private JPanel crearPanelEstadisticas() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(COLOR_FONDO);
        
        // Título de estadísticas
        JLabel tituloEstadisticas = new JLabel("Estadísticas de Participación");
        tituloEstadisticas.setFont(new Font("Arial", Font.BOLD, 16));
        tituloEstadisticas.setForeground(COLOR_TEXTO_TITULO);
        tituloEstadisticas.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelPrincipal.add(tituloEstadisticas);
        panelPrincipal.add(crearEspaciado(10));
        
        // Panel de métricas
        JPanel panelMetricas = new JPanel(new GridLayout(1, 3, 15, 0));
        panelMetricas.setBackground(COLOR_FONDO);
        
        // Estadísticas básicas
        int totalParticipantes = asistentes.size();
        
        // Análisis de dominios de email
        Map<String, Long> dominiosEmail = asistentes.stream()
            .collect(Collectors.groupingBy(
                asistente -> asistente.getEmail().substring(asistente.getEmail().indexOf("@") + 1),
                Collectors.counting()
            ));
        
        String dominioMasComun = dominiosEmail.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("N/A");
        
        // Crear tarjetas de estadísticas
        panelMetricas.add(crearTarjetaEstadistica("Total Participantes", 
            String.valueOf(totalParticipantes), "👥", COLOR_PRINCIPAL));
        
        panelMetricas.add(crearTarjetaEstadistica("Dominio Email Común", 
            dominioMasComun, "📧", COLOR_SECUNDARIO));
        
        panelMetricas.add(crearTarjetaEstadistica("Dominios Únicos", 
            String.valueOf(dominiosEmail.size()), "🌐", COLOR_PRINCIPAL));
        
        panelPrincipal.add(panelMetricas);
        
        return panelPrincipal;
    }
    
    private JPanel crearTarjetaEstadistica(String titulo, String valor, String icono, Color color) {
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
        lblValor.setFont(new Font("Arial", Font.BOLD, 18));
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
        
        JButton btnExportar = crearBotonSimple("Mostrar Análisis Detallado", 
            "Muestra estadísticas completas de participación", COLOR_SECUNDARIO, e -> mostrarAnalisisDetallado());
        
        JButton btnVolver = crearBotonSimple("Volver al Menú", 
            "Regresa al menú de participantes", COLOR_SECUNDARIO, e -> dispose());
        
        JButton btnCerrar = crearBotonSimple("Cerrar", 
            "Cierra esta ventana", COLOR_SECUNDARIO, e -> dispose());
        
        panel.add(btnActualizar);
        panel.add(btnExportar);
        panel.add(btnVolver);
        panel.add(btnCerrar);
        
        return panel;
    }
    
    private void actualizarTabla() {
        // Actualizar datos desde el controlador
        asistentes = asistentesController.obtenerTodosLosParticipantes();
        
        // Recrear la tabla con datos actualizados
        Container parent = tabla.getParent().getParent().getParent();
        parent.remove(tabla.getParent().getParent());
        
        crearTablaAsistentes();
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(850, 300));
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        
        parent.add(scrollPane);
        parent.revalidate();
        parent.repaint();
        
        // Actualizar barra de estado
        agregarBarraEstado("Listado Actualizado", 
            "Total: " + asistentes.size() + " participantes | Datos actualizados correctamente");
    }
    
    private void mostrarAnalisisDetallado() {
        StringBuilder analisis = new StringBuilder();
        analisis.append("📊 ANÁLISIS DETALLADO DE PARTICIPACIÓN\n");
        analisis.append("═══════════════════════════════════════════════\n\n");
        
        // Estadísticas generales
        analisis.append("📈 RESUMEN GENERAL:\n");
        analisis.append("• Total de participantes registrados: ").append(asistentes.size()).append("\n");
        
        // Análisis de dominios de email
        Map<String, Long> dominiosEmail = asistentes.stream()
            .collect(Collectors.groupingBy(
                asistente -> asistente.getEmail().substring(asistente.getEmail().indexOf("@") + 1),
                Collectors.counting()
            ));
        
        analisis.append("\n📧 ANÁLISIS DE DOMINIOS DE EMAIL:\n");
        dominiosEmail.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> 
                analisis.append("• ").append(entry.getKey()).append(": ")
                        .append(entry.getValue()).append(" participantes\n"));
        
        // Patrones de nombre
        analisis.append("\n👤 PATRONES DE NOMBRES:\n");
        Map<String, Long> nombresComunes = asistentes.stream()
            .collect(Collectors.groupingBy(
                Asistentes::getNombre,
                Collectors.counting()
            ));
        
        long nombresUnicos = nombresComunes.entrySet().stream()
            .filter(entry -> entry.getValue() == 1)
            .count();
        
        analisis.append("• Nombres únicos: ").append(nombresUnicos).append("\n");
        analisis.append("• Nombres repetidos: ").append(nombresComunes.size() - nombresUnicos).append("\n");
        
        // Análisis de teléfonos
        analisis.append("\n📞 ANÁLISIS DE CONTACTO:\n");
        long telefonosValidos = asistentes.stream()
            .filter(asistente -> asistente.getTelefono() != null && !asistente.getTelefono().trim().isEmpty())
            .count();
        
        analisis.append("• Participantes con teléfono registrado: ").append(telefonosValidos).append("\n");
        analisis.append("• Participantes sin teléfono: ").append(asistentes.size() - telefonosValidos).append("\n");
        
        // Crear ventana de análisis
        JDialog dialogo = new JDialog(this, "Análisis Detallado de Participación", true);
        dialogo.setSize(600, 500);
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
