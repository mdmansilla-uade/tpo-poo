# tpo-poo

## **1. DESCRIPCIÓN GENERAL**

El **Sistema de Gestión de Eventos** es una aplicación de escritorio desarrollada en Java que permite administrar eventos, participantes y espacios físicos de manera integral. El sistema ofrece una interfaz gráfica moderna y funcional que facilita todas las operaciones necesarias para la gestión completa de eventos.

### **Objetivo Principal**
Proporcionar una herramienta completa para la administración de eventos que permita gestionar participantes, espacios y actividades de forma eficiente y profesional.

---

## **2. FUNCIONALIDADES PRINCIPALES**

### **2.1. Gestión de Eventos**
- **Crear eventos** con fecha, nombre, salón asignado y descripción
- **Editar eventos** existentes con validación de disponibilidad
- **Eliminar eventos** con análisis de impacto en participantes
- **Visualizar todos los eventos** en tabla profesional
- **Registrar participantes** en eventos específicos
- **Vista de calendario** para visualización temporal

### **2.2. Administración de Participantes**
- **Registrar nuevos participantes** con datos completos
- **Editar información** de participantes existentes
- **Eliminar participantes** con validaciones de seguridad
- **Consultar listado completo** con estadísticas avanzadas
- **Análisis de participación** con métricas en tiempo real

### **2.3. Manejo de Espacios**
- **Registrar nuevos salones** con capacidad y ubicación
- **Modificar características** de espacios existentes
- **Eliminar salones** con evaluación de impacto
- **Consultar inventario** de espacios disponibles
- **Análisis de capacidades** y utilización

---

## **3. ARQUITECTURA DEL SISTEMA**

### **3.1. Estructura General**
El sistema implementa el patrón **MVC (Model-View-Controller)** organizando el código en capas claramente definidas:

- **Modelos:** Entidades de negocio (Asistentes, Eventos, Salon)
- **Vistas:** Interfaces gráficas profesionales con BaseView
- **Controladores:** Coordinación de operaciones y lógica de aplicación

### **3.2. Componentes Principales**

**Entidades de Dominio:**
- `Asistentes`: Gestión de datos de participantes
- `Eventos`: Administración de actividades y sus participantes
- `Salon`: Manejo de espacios físicos y capacidades

**Controladores:**
- `AsistentesController`: Operaciones CRUD de participantes
- `EventosController`: Gestión completa de eventos
- `SalonController`: Administración de espacios

**Servicios:**
- Validadores especializados para cada entidad
- Servicios de lógica de negocio
- Sistema de persistencia en archivos

---

## **4. TECNOLOGÍAS Y HERRAMIENTAS**

### **4.1. Tecnologías Core**
- **Lenguaje:** Java 8+
- **Interfaz Gráfica:** Swing
- **Manejo de Fechas:** JCalendar (jcalendar-1.4.jar)
- **Persistencia:** Archivos de texto plano
- **Arquitectura:** Programación Orientada a Objetos

### **4.2. Librerías Externas**
- **JCalendar:** Para selección de fechas en formularios
- **Javax Mail:** Para funcionalidades futuras de notificación
