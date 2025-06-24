classDiagram
    class Asistentes {
        -String nombrePersona
        -String apellidoPersona
        -String correoElectronico
        -String numeroTelefono
        -ValidadorAsistentes validador
        +Asistentes(String, String, String, String)
        +getNombre() String
        +setNombre(String) void
        +getApellido() String
        +setApellido(String) void
        +getEmail() String
        +setEmail(String) void
        +getTelefono() String
        +setTelefono(String) void
        +obtenerNombreCompleto() String
        +tieneEmailValido() boolean
        +toString() String
        +equals(Object) boolean
        +hashCode() int
    }
    
    class Eventos {
        -Date fechaYHora
        -String denominacionEvento
        -Salon espacioAsignado
        -List~Asistentes~ listaParticipantes
        -String descripcionDetallada
        -ValidadorEventos validador
        -ServicioLogicaEventos servicioLogica
        +Eventos(Date, String, String)
        +Eventos(Date, String, Salon, String)
        +getFecha() Date
        +setFecha(Date) void
        +getNombre() String
        +setNombre(String) void
        +getSalon() Salon
        +setSalon(Salon) void
        +getAsistentes() List~Asistentes~
        +setAsistentes(List~Asistentes~) void
        +getDescripcion() String
        +setDescripcion(String) void
        +agregarParticipante(Asistentes) void
        +removerParticipante(Asistentes) void
        +obtenerCantidadParticipantes() int
        +estaLleno() boolean
        +puedeAgregarParticipantes(int) boolean
        +calcularPorcentajeOcupacion() double
        +calcularEspacioDisponible() int
        +esEventoProximo(int) boolean
    }
    
    class Salon {
        -String denominacionEspacio
        -int capacidadMaxima
        -String ubicacionFisica
        -ValidadorSalon validador
        -ServicioLogicaNegocio servicioLogica
        +Salon(String, int, String)
        +getNombre() String
        +setNombre(String) void
        +getCapacidad() int
        +setCapacidad(int) void
        +getUbicacion() String
        +setUbicacion(String) void
        +puedeAlbergar(int) boolean
        +esCapacidadSuficiente(int) boolean
        +calcularPorcentajeOcupacion(int) double
        +estaCercaDeCapacidadMaxima(int, double) boolean
        +calcularEspacioDisponible(int) int
        +esApropiado(String, int) boolean
    }
    
    Eventos "1" --> "1" Salon : espacioAsignado
    Eventos "1" --> "*" Asistentes : listaParticipantes
```