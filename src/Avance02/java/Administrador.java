import java.util.ArrayList;

public class Administrador {
    private String correoElectronico;
    private String contrasena;
    private ArrayList<Compra> comprasPendientes;

    // Constructor
    public Administrador(String correoElectronico, String contrasena) {
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.comprasPendientes = new ArrayList<>();
    }

    // Métodos getters y setters
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public ArrayList<Compra> getComprasPendientes() {
        return comprasPendientes;
    }

    public void setComprasPendientes(ArrayList<Compra> comprasPendientes) {
        this.comprasPendientes = comprasPendientes;
    }

    // Método para marcar una compra como aceptada
    public void marcarCompraAceptada(Compra compra) {
        comprasPendientes.remove(compra);

    }

    // Método para marcar una compra como rechazada
    public void marcarCompraRechazada(Compra compra) {
        comprasPendientes.remove(compra);

    }
}
