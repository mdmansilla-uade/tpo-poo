package Views;

import Views.Eventos.EventosView;
import Views.Recursos.RecursosView;
import java.awt.*;
import javax.swing.*;

public class PrincipalView extends BaseView {

    public PrincipalView() {
        configurarVentanaBase("Sistema de Gestión de Eventos", 700, 800);
        inicializarComponentes();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Sistema de Gestión de Eventos",
            "Administra eventos, recursos y participantes de manera eficiente"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelOpciones = crearPanelOpciones("Opciones Principales");
        
        JButton botonEventos = crearBotonTarjeta(
            "Gestión de Eventos", 
            "Crear, modificar y administrar eventos corporativos",
            "Controla todo el ciclo de vida de tus eventos empresariales",
            COLOR_PRINCIPAL,
            e -> abrirVistaEventos()
        );
        
        JButton botonRecursos = crearBotonTarjeta(
            "Gestión de Recursos", 
            "Administrar salones y participantes del sistema",
            "Gestiona espacios físicos y base de datos de asistentes",
            COLOR_PRINCIPAL,
            e -> abrirVistaRecursos()
        );
        
        panelOpciones.add(botonEventos);
        panelOpciones.add(crearEspaciado(20));
        panelOpciones.add(botonRecursos);
        
        panelPrincipal.add(panelOpciones);
        
        panelPrincipal.add(crearEspaciado(20));
        
        JPanel panelInferior = crearPanelInferior();
        JButton botonSalir = crearBotonSimple("Salir de la Aplicación", 
            "Finaliza la sesión y cierra la aplicación de forma segura", 
            COLOR_PELIGRO, e -> cerrarAplicacion());
        
        panelInferior.add(botonSalir);
        panelPrincipal.add(panelInferior);
        
        panelPrincipal.add(crearEspaciado(20));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        agregarBarraEstado("Sistema de Gestión de Eventos", "v1.0");
    }

    private void abrirVistaEventos() {
        new EventosView();
    }

    private void abrirVistaRecursos() {
        new RecursosView();
    }

    private void cerrarAplicacion() {
        int opcionSeleccionada = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar la aplicación?\n\nTodos los datos han sido guardados automáticamente.",
            "Confirmar Salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (opcionSeleccionada == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }
}
