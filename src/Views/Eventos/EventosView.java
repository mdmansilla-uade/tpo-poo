package Views.Eventos;

import Controllers.EventosController;
import Views.BaseView;
import java.awt.*;
import javax.swing.*;

public class EventosView extends BaseView {
    private final EventosController eventosController = EventosController.getInstance();

    public EventosView() {
        configurarVentanaBase("Gestión de Eventos", 700, 1000);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        setLayout(new BorderLayout());
        
        JPanel panelPrincipal = crearPanelPrincipal();
        
        JPanel encabezado = crearEncabezadoSeccion(
            "Administración de Eventos",
            "Gestiona todos los aspectos de tus eventos corporativos"
        );
        panelPrincipal.add(encabezado);
        
        JPanel panelOpciones = crearPanelOpciones("Acciones de Eventos");
        
        JButton botonCrear = crearBotonTarjeta(
            "Crear Nuevo Evento",
            "Agregar un evento al sistema de gestión",
            "Registra eventos con fecha, ubicación y detalles completos",
            COLOR_PRINCIPAL,
            e -> crearEvento(),
            100
        );
        
        JButton botonEditar = crearBotonTarjeta(
            "Editar Evento Existente",
            "Modificar información de eventos registrados",
            "Actualiza fechas, ubicaciones, descripciones y participantes",
            COLOR_PRINCIPAL,
            e -> editarEvento(),
            100
        );
        
        JButton botonEliminar = crearBotonTarjeta(
            "Eliminar Evento",
            "Remover eventos del sistema de forma permanente",
            "Cancela eventos y libera recursos asociados",
            COLOR_PELIGRO,
            e -> eliminarEvento(),
            100
        );
        
        JButton botonRegistrar = crearBotonTarjeta(
            "Registrar Asistente",
            "Inscribir participantes en eventos disponibles",
            "Gestiona inscripciones y controla capacidad de eventos",
            COLOR_SECUNDARIO,
            e -> registrarAsistente(),
            100
        );
        
        JButton botonTodos = crearBotonTarjeta(
            "Ver Todos los Eventos",
            "Mostrar listado completo de eventos registrados",
            "Consulta información detallada de todos los eventos",
            COLOR_SECUNDARIO,
            e -> mostrarEventos(),
            100
        );
        
        JButton botonCalendario = crearBotonTarjeta(
            "Ver Calendario",
            "Visualizar eventos en formato de calendario",
            "Vista cronológica organizada por fechas y horarios",
            COLOR_SECUNDARIO,
            e -> mostrarCalendario(),
            100
        );
        
        panelOpciones.add(botonCrear);
        panelOpciones.add(crearEspaciado(15));
        panelOpciones.add(botonEditar);
        panelOpciones.add(crearEspaciado(15));
        panelOpciones.add(botonEliminar);
        panelOpciones.add(crearEspaciado(15));
        panelOpciones.add(botonRegistrar);
        panelOpciones.add(crearEspaciado(15));
        panelOpciones.add(botonTodos);
        panelOpciones.add(crearEspaciado(15));
        panelOpciones.add(botonCalendario);
        
        panelPrincipal.add(panelOpciones);
        panelPrincipal.add(Box.createVerticalGlue());
        
        JPanel panelInferior = crearPanelInferior();
        JButton botonRegresar = crearBotonSimple("Regresar al Menú Principal", 
            "Volver a la pantalla principal", COLOR_PELIGRO, e -> dispose());
        
        panelInferior.add(botonRegresar);
        panelPrincipal.add(panelInferior);
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        agregarBarraEstado("Módulo de Eventos Activo", 
            "Total eventos: " + eventosController.obtenerTodosLosEventos().size());
    }
    

    private void crearEvento() {
        new AgregarEvento();
    }

    private void editarEvento() { 
        new EditarEvento();
    }

    private void eliminarEvento() {
        new EliminarEvento();
    }

    private void mostrarEventos() {
        new MostrarTodos(eventosController.obtenerTodosLosEventos());
    }

    private void mostrarCalendario() {
        new Calendario();
    }

    private void registrarAsistente() {
        new RegistrarAsistente();
    }
}
