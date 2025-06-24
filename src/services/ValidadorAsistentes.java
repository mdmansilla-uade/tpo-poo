package services;

public class ValidadorAsistentes {
    
    private static ValidadorAsistentes instanciaUnica;
    
    private ValidadorAsistentes() {}
    
    public static ValidadorAsistentes obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ValidadorAsistentes();
        }
        return instanciaUnica;
    }
    
    
    public String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        
        String nombreLimpio = nombre.trim();
        
        if (nombreLimpio.length() < 2) {
            throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres");
        }
        
        if (nombreLimpio.length() > 30) {
            throw new IllegalArgumentException("El nombre no puede exceder los 30 caracteres");
        }
        
        if (!nombreLimpio.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("El nombre solo puede contener letras y espacios");
        }
        
        return nombreLimpio;
    }
    
    
    public String validarApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        
        String apellidoLimpio = apellido.trim();
        
        if (apellidoLimpio.length() < 2) {
            throw new IllegalArgumentException("El apellido debe tener al menos 2 caracteres");
        }
        
        if (apellidoLimpio.length() > 30) {
            throw new IllegalArgumentException("El apellido no puede exceder los 30 caracteres");
        }
        
        if (!apellidoLimpio.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            throw new IllegalArgumentException("El apellido solo puede contener letras y espacios");
        }
        
        return apellidoLimpio;
    }
    
    
    public String validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        
        String emailLimpio = email.trim().toLowerCase();
        
        String patronEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!emailLimpio.matches(patronEmail)) {
            throw new IllegalArgumentException("El formato del email no es válido");
        }
        
        if (emailLimpio.length() > 100) {
            throw new IllegalArgumentException("El email no puede exceder los 100 caracteres");
        }
        
        return emailLimpio;
    }
    
    
    public String validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        
        String telefonoLimpio = telefono.trim().replaceAll("\\s+", "");
        
        if (!telefonoLimpio.matches("^[+]?[0-9()\\-\\s]{8,20}$")) {
            throw new IllegalArgumentException("El formato del teléfono no es válido");
        }
        
        return telefonoLimpio;
    }
    
    public void validarDatosAsistente(String nombre, String apellido, String email, String telefono) {
        validarNombre(nombre);
        validarApellido(apellido);
        validarEmail(email);
        validarTelefono(telefono);
    }
} 