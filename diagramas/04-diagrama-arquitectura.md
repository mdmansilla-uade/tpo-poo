graph TB
    subgraph "PRESENTATION LAYER"
        PV[PrincipalView]
        BV[BaseView<br/>Clase Abstracta]
        
        subgraph "Views - Eventos"
            EV[EventosView]
            AE[AgregarEvento]
            EE[EditarEvento]
            RE[RegistrarAsistente]
            DE[EliminarEvento]
            ME[MostrarTodos]
            CAL[Calendario]
        end
        
        subgraph "Views - Recursos"
            RV[RecursosView]
            
            subgraph "Asistentes"
                AV[AsistentesView]
                AA[AgregarAsistente]
                EA[EditarAsistente]
                DA[EliminarAsistente]
                MA[MostrarTodo]
            end
            
            subgraph "Salones"
                SV[SalonesView]
                AS[AgregarSalon]
                ES[EditarSalon]
                DS[EliminarSalon]
                MS[MostrarTodo]
            end
        end
    end
    
    subgraph "BUSINESS LAYER"
        AC[AsistentesController]
        EC[EventosController]
        SC[SalonController]
    end
    
    subgraph "DOMAIN LAYER"
        ASI[Asistentes]
        EVE[Eventos]
        SAL[Salon]
    end
    
    subgraph "SERVICE LAYER"
        VA[ValidadorAsistentes]
        VE[ValidadorEventos]
        VS[ValidadorSalon]
        SLE[ServicioLogicaEventos]
        SLN[ServicioLogicaNegocio]
    end
    
    subgraph "DATA ACCESS LAYER"
        IR[IRepositorioDatos]
        RA[RepositorioArchivos]
        GE[GestorErrores]
    end
    
    subgraph "DATA LAYER"
        F1[(asistentes.txt)]
        F2[(eventos.txt)]
        F3[(salones.txt)]
        F4[(errores.txt)]
    end
    
    %% Relaciones de herencia y extensión
    BV --> PV
    BV --> EV
    BV --> RV
    BV --> AV
    BV --> SV
    BV --> AE
    BV --> EE
    BV --> RE
    BV --> DE
    BV --> ME
    BV --> AA
    BV --> EA
    BV --> DA
    BV --> MA
    BV --> AS
    BV --> ES
    BV --> DS
    BV --> MS
    
    %% Relaciones MVC
    EV --> EC
    AV --> AC
    SV --> SC
    
    AC --> ASI
    EC --> EVE
    SC --> SAL
    
    ASI --> VA
    EVE --> VE
    EVE --> SLE
    SAL --> VS
    SAL --> SLN
    
    AC --> RA
    EC --> RA
    SC --> RA
    
    RA --> F1
    RA --> F2
    RA --> F3
    GE --> F4
```