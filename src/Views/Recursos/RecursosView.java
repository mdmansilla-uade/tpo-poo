package Views.Recursos;

import Views.Recursos.Asistentes.AsistentesView;
import Views.Recursos.Salones.SalonesView;
import Views.BaseView;
import java.awt.*;
import javax.swing.*;

public class RecursosView extends BaseView {

    public RecursosView() {
        configurarVentanaBase("Gestión de Recursos", 700, 600);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Administración de Recursos",
            "Gestiona espacios físicos y participantes del sistema"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelOpciones = crearPanelOpciones("Recursos Disponibles");
        
        JButton botonParticipantes = crearBotonTarjeta(
            "Gestión de Asistentes",
            "Administrar base de datos de participantes",
            "Registra, edita y consulta información de asistentes a eventos",
            COLOR_PRINCIPAL,
            e -> abrirGestionAsistentes()
        );

        JButton botonEspacios = crearBotonTarjeta(
            "Gestión de Salones",
            "Administrar espacios físicos para eventos",
            "Controla capacidades, ubicaciones y disponibilidad de salones",
            COLOR_PRINCIPAL,
            e -> abrirGestionSalones()
        );

        panelOpciones.add(botonParticipantes);
        panelOpciones.add(crearEspaciado(20));
        panelOpciones.add(botonEspacios);

        panelPrincipal.add(panelOpciones);
        panelPrincipal.add(Box.createVerticalGlue());
        
        JPanel panelInferior = crearPanelInferior();
        JButton botonRegresar = crearBotonSimple("Regresar al Menú Principal", 
            "Volver a la pantalla principal", COLOR_PELIGRO, e -> dispose());
        
        panelInferior.add(botonRegresar);
        panelPrincipal.add(panelInferior);

        add(panelPrincipal, BorderLayout.CENTER);
        
        agregarBarraEstado("Módulo de Recursos Activo", "Sistema operativo");
    }

    private void abrirGestionAsistentes() {
        new AsistentesView();
    }

    private void abrirGestionSalones() {
        new SalonesView();
    }
}
