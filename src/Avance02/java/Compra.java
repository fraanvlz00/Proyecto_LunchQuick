import java.util.UUID;

public class Compra {
    private String codigoConfirmacion;
    private String correoUsuario;
    private boolean entregada;

    // Constructor
    public Compra(String correoUsuario) {
        this.codigoConfirmacion = generarCodigoConfirmacion();
        this.correoUsuario = correoUsuario;
        this.entregada = false;
    }

    // Métodos getters y setters
    public String getCodigoConfirmacion() {
        return codigoConfirmacion;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public boolean isEntregada() {
        return entregada;
    }

    public void setEntregada(boolean entregada) {
        this.entregada = entregada;
    }

    // Método para generar un código de confirmación aleatorio
    private String generarCodigoConfirmacion() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
