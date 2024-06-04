package Dominio;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String correoElectronico;
    private String contrasena;
    private List<String> almuerzosComprados;

    public Usuario() {
        this.almuerzosComprados = new ArrayList<>();
    }

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

    public List<String> getAlmuerzosComprados() {
        return almuerzosComprados;
    }

    public void setAlmuerzosComprados(List<String> almuerzosComprados) {
        this.almuerzosComprados = almuerzosComprados;
    }

    public void addAlmuerzoComprado(String almuerzo) {
        this.almuerzosComprados.add(almuerzo);
    }
}
