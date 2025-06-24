classDiagram
    class ValidadorAsistentes {
        -ValidadorAsistentes instancia
        -ValidadorAsistentes()
        +obtenerInstancia() ValidadorAsistentes
        +validarNombre(String) String
        +validarApellido(String) String
        +validarEmail(String) String
        +validarTelefono(String) String
        +validarNuevoParticipante(Asistentes, List~Asistentes~) void
    }
    
    class ValidadorEventos {
        -ValidadorEventos instancia
        -ValidadorEventos()
        +obtenerInstancia() ValidadorEventos
        +validarFecha(Date) Date
        +validarDenominacion(String) String
        +validarDescripcion(String) String
        +validarListaParticipantes(List~Asistentes~) List~Asistentes~
        +validarNuevoParticipante(Asistentes, List~Asistentes~) void
    }
    
    class ValidadorSalon {
        -ValidadorSalon instancia
        -ValidadorSalon()
        +obtenerInstancia() ValidadorSalon
        +validarDenominacion(String) String
        +validarCapacidad(int) int
        +validarUbicacion(String) String
    }
    
    class ServicioLogicaEventos {
        -ServicioLogicaEventos instancia
        -ServicioLogicaEventos()
        +obtenerInstancia() ServicioLogicaEventos
        +esMismaFecha(Date, Date) boolean
        +participanteYaExiste(Asistentes, List~Asistentes~) boolean
        +estaLleno(int, int) boolean
        +puedeAgregarParticipantes(int, int, int) boolean
        +calcularPorcentajeOcupacion(int, int) double
        +estaCercaDeCapacidadMaxima(int, int, double) boolean
        +calcularEspacioDisponible(int, int) int
        +esApropiado(int, int, String) boolean
        +calcularDiasRestantes(Date) long
        +esEventoProximo(Date, int) boolean
    }
    
    class ServicioLogicaNegocio {
        -ServicioLogicaNegocio instancia
        -ServicioLogicaNegocio()
        +obtenerInstancia() ServicioLogicaNegocio
        +puedeAlbergar(int, int) boolean
        +esCapacidadSuficiente(int, int) boolean
        +calcularPorcentajeOcupacion(int, int) double
        +estaCercaDeCapacidadMaxima(int, int, double) boolean
        +calcularEspacioDisponible(int, int) int
        +esApropiado(int, String, int) boolean
    }
    
    class IRepositorioDatos {
        <<interface>>
        +guardar(String, List~String~) void
        +cargar(String) List~String~
    }
    
    class RepositorioArchivos {
        +guardar(String, List~String~) void
        +cargar(String) List~String~
        -escribirArchivo(String, List~String~) void
        -leerArchivo(String) List~String~
    }
    
    class GestorErrores {
        -List~String~ errores
        +registrarError(String) void
        +obtenerErrores() List~String~
        +limpiarErrores() void
        +tieneErrores() boolean
        +guardarEnArchivo() void
    }
    
    RepositorioArchivos ..|> IRepositorioDatos
    
    Asistentes --> ValidadorAsistentes : usa
    Eventos --> ValidadorEventos : usa
    Eventos --> ServicioLogicaEventos : usa
    Salon --> ValidadorSalon : usa
    Salon --> ServicioLogicaNegocio : usa
```