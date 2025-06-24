package services;

public class ValidadorSalon {
    
    private static ValidadorSalon instanciaUnica;
    
    private ValidadorSalon() {}
    
    public static ValidadorSalon obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ValidadorSalon();
        }
        return instanciaUnica;
    }
    

    public String validarDenominacion(String denominacion) {
        if (denominacion == null || denominacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La denominación del espacio no puede estar vacía");
        }
        
        String denominacionLimpia = denominacion.trim();
        
        if (denominacionLimpia.length() < 2) {
            throw new IllegalArgumentException("La denominación debe tener al menos 2 caracteres");
        }
        
        if (denominacionLimpia.length() > 50) {
            throw new IllegalArgumentException("La denominación no puede exceder los 50 caracteres");
        }
        

        if (!denominacionLimpia.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s\\-\\.]+$")) {
            throw new IllegalArgumentException("La denominación contiene caracteres no válidos");
        }
        
        return denominacionLimpia;
    }
    

    public int validarCapacidad(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        
        if (capacidad > 10000) {
            throw new IllegalArgumentException("La capacidad no puede exceder las 10,000 personas");
        }
        
        return capacidad;
    }
    

    public String validarUbicacion(String ubicacion) {
        if (ubicacion == null) {
            return "";
        }
        
        String ubicacionLimpia = ubicacion.trim();
        
        if (ubicacionLimpia.length() > 100) {
            throw new IllegalArgumentException("La ubicación no puede exceder los 100 caracteres");
        }
        
        return ubicacionLimpia;
    }
    

    public void validarDatosSalon(String denominacion, int capacidad, String ubicacion) {
        validarDenominacion(denominacion);
        validarCapacidad(capacidad);
        validarUbicacion(ubicacion);
    }
} 