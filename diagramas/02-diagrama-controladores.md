classDiagram
    class AsistentesController {
        -AsistentesController instancia
        -List~Asistentes~ participantes
        -RepositorioArchivos repositorio
        -ValidadorAsistentes validador
        -AsistentesController()
        +getInstance() AsistentesController
        +registrarNuevoParticipante(String, String, String, String) void
        +obtenerTodosLosParticipantes() List~Asistentes~
        +actualizarInformacionParticipante(Asistentes, String, String, String, String) void
        +eliminarParticipante(Asistentes) void
        +buscarParticipantePorEmail(String) Asistentes
        +verificarExistenciaParticipante(Asistentes) boolean
        -cargarDatos() void
        -guardarDatos() void
    }
    
    class EventosController {
        -EventosController instancia
        -List~Eventos~ eventos
        -RepositorioArchivos repositorio
        -ValidadorEventos validador
        -ServicioLogicaEventos servicioLogica
        -EventosController()
        +getInstance() EventosController
        +crearNuevoEvento(Date, String, Salon, String) void
        +obtenerTodosLosEventos() List~Eventos~
        +actualizarEvento(Eventos, Date, String, Salon, String) void
        +eliminarEvento(Eventos) void
        +inscribirParticipanteEnEvento(Eventos, Asistentes) void
        +desinscribirParticipanteDeEvento(Eventos, Asistentes) void
        +buscarEventosPorFecha(Date) List~Eventos~
        +buscarEventosPorSalon(Salon) List~Eventos~
        -cargarDatos() void
        -guardarDatos() void
    }
    
    class SalonController {
        -SalonController instancia
        -List~Salon~ espacios
        -RepositorioArchivos repositorio
        -ValidadorSalon validador
        -ServicioLogicaNegocio servicioLogica
        -SalonController()
        +getInstance() SalonController
        +registrarNuevoEspacio(String, int, String) void
        +obtenerTodosLosEspacios() List~Salon~
        +actualizarInformacionEspacio(Salon, String, int, String) void
        +eliminarEspacio(Salon) void
        +buscarEspacioPorNombre(String) Salon
        +verificarDisponibilidadEspacio(Salon, Date) boolean
        +obtenerEspaciosDisponibles(Date) List~Salon~
        -cargarDatos() void
        -guardarDatos() void
    }
    
    AsistentesController --> "repositorio" RepositorioArchivos
    EventosController --> "repositorio" RepositorioArchivos
    SalonController --> "repositorio" RepositorioArchivos
    
    AsistentesController --> "*" Asistentes : gestiona
    EventosController --> "*" Eventos : gestiona
    SalonController --> "*" Salon : gestiona
```