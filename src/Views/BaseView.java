package Views;

import javax.swing.*;
import java.awt.*;

public abstract class BaseView extends JFrame {
    
    // Colores estándar del sistema
    protected static final Color COLOR_PRINCIPAL = new Color(52, 73, 94);
    protected static final Color COLOR_SECUNDARIO = new Color(108, 117, 125);
    protected static final Color COLOR_PELIGRO = new Color(220, 53, 69);
    protected static final Color COLOR_FONDO = new Color(248, 249, 250);
    protected static final Color COLOR_PANEL = Color.WHITE;
    protected static final Color COLOR_BORDE = new Color(206, 212, 218);
    protected static final Color COLOR_TEXTO_TITULO = new Color(73, 80, 87);
    
    // Colores para texto en botones y barras
    protected static final Color COLOR_TEXTO_BLANCO = Color.WHITE;
    protected static final Color COLOR_TEXTO_CLARO = new Color(220, 220, 220);
    protected static final Color COLOR_TEXTO_MUY_CLARO = new Color(240, 240, 240);
    protected static final Color COLOR_TEXTO_INFO = new Color(189, 195, 199);
    
    protected void configurarVentanaBase(String titulo, int ancho, int alto) {
        setTitle(titulo);
        setSize(ancho, alto);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
    }
    
    protected JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setBackground(COLOR_FONDO);
        return panel;
    }
    
    protected JPanel crearEncabezadoSeccion(String titulo, String descripcion) {
        JPanel panelEncabezado = new JPanel();
        panelEncabezado.setLayout(new BoxLayout(panelEncabezado, BoxLayout.Y_AXIS));
        panelEncabezado.setBackground(COLOR_FONDO);
        
        JLabel tituloSeccion = new JLabel(titulo);
        tituloSeccion.setFont(new Font("Arial", Font.BOLD, 24));
        tituloSeccion.setForeground(COLOR_PRINCIPAL);
        tituloSeccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel etiquetaDescripcion = new JLabel(descripcion);
        etiquetaDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        etiquetaDescripcion.setForeground(COLOR_SECUNDARIO);
        etiquetaDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelEncabezado.add(tituloSeccion);
        panelEncabezado.add(Box.createRigidArea(new Dimension(0, 8)));
        panelEncabezado.add(etiquetaDescripcion);
        panelEncabezado.add(Box.createRigidArea(new Dimension(0, 30)));
        
        return panelEncabezado;
    }
    
    protected JPanel crearPanelOpciones(String tituloSeccion) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_PANEL);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                " " + tituloSeccion + " ",
                0, 0, new Font("Arial", Font.BOLD, 16), COLOR_TEXTO_TITULO
            ),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        return panel;
    }
    
    protected JButton crearBotonTarjeta(String titulo, String descripcionCorta, String descripcionLarga, 
                                       Color colorPrincipal, java.awt.event.ActionListener accion) {
        return crearBotonTarjeta(titulo, descripcionCorta, descripcionLarga, colorPrincipal, accion, 120);
    }
    
    protected JButton crearBotonTarjeta(String titulo, String descripcionCorta, String descripcionLarga, 
                                       Color colorPrincipal, java.awt.event.ActionListener accion, int altura) {
        JButton boton = new JButton();
        boton.setLayout(new BorderLayout());
        boton.setPreferredSize(new Dimension(700, altura));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, altura));
        boton.setMinimumSize(new Dimension(600, altura));
        
        // Panel de contenido del botón
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(colorPrincipal);
        contenido.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        
        // Panel izquierdo con título y descripción
        JPanel panelTexto = new JPanel();
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
        panelTexto.setBackground(colorPrincipal);
        
        // Ajustar tamaños de fuente según la altura
        int tamanoTitulo = altura > 100 ? 18 : 16;
        int tamanoDescripcion = altura > 100 ? 14 : 13;
        int tamanoDetalle = altura > 100 ? 12 : 11;
        
        JLabel etiquetaTitulo = new JLabel(titulo);
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, tamanoTitulo));
        etiquetaTitulo.setForeground(COLOR_TEXTO_BLANCO);
        etiquetaTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel etiquetaDescripcion = new JLabel(descripcionCorta);
        etiquetaDescripcion.setFont(new Font("Arial", Font.PLAIN, tamanoDescripcion));
        etiquetaDescripcion.setForeground(COLOR_TEXTO_CLARO);
        etiquetaDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel etiquetaDetalle = new JLabel(descripcionLarga);
        etiquetaDetalle.setFont(new Font("Arial", Font.ITALIC, tamanoDetalle));
        etiquetaDetalle.setForeground(COLOR_TEXTO_MUY_CLARO);
        etiquetaDetalle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelTexto.add(etiquetaTitulo);
        panelTexto.add(Box.createRigidArea(new Dimension(0, altura > 100 ? 5 : 4)));
        panelTexto.add(etiquetaDescripcion);
        panelTexto.add(Box.createRigidArea(new Dimension(0, altura > 100 ? 8 : 6)));
        panelTexto.add(etiquetaDetalle);
        
        
        contenido.add(panelTexto, BorderLayout.CENTER);
        
        boton.add(contenido);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                contenido.setBackground(colorPrincipal.darker());
                panelTexto.setBackground(colorPrincipal.darker());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                contenido.setBackground(colorPrincipal);
                panelTexto.setBackground(colorPrincipal);
            }
        });
        
        boton.addActionListener(accion);
        return boton;
    }
    
    protected JButton crearBotonSimple(String texto, String tooltip, Color color, 
                                     java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setToolTipText(tooltip);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(400, 50));
        
        boton.setBackground(color);
        boton.setForeground(color);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });
        
        boton.addActionListener(accion);
        return boton;
    }
    
    protected JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(COLOR_FONDO);
        return panel;
    }
    
    protected void agregarBarraEstado(String textoEstado, String infoAdicional) {
        JPanel panelEstado = new JPanel(new BorderLayout());
        panelEstado.setBackground(COLOR_PRINCIPAL);
        
        JLabel etiquetaEstado = new JLabel(" " + textoEstado);
        etiquetaEstado.setForeground(COLOR_TEXTO_BLANCO);
        etiquetaEstado.setFont(new Font("Arial", Font.PLAIN, 12));
        etiquetaEstado.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        
        JLabel etiquetaInfo = new JLabel(infoAdicional + " ");
        etiquetaInfo.setForeground(COLOR_TEXTO_INFO);
        etiquetaInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        etiquetaInfo.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        
        panelEstado.add(etiquetaEstado, BorderLayout.WEST);
        panelEstado.add(etiquetaInfo, BorderLayout.EAST);
        
        add(panelEstado, BorderLayout.SOUTH);
    }
    
    protected Component crearEspaciado(int altura) {
        return Box.createRigidArea(new Dimension(0, altura));
    }
    
    protected abstract void inicializarComponentes();
} 