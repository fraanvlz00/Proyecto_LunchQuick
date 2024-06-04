package Launcher;

import Dominio.ServicioAlmuerzo;
import Dominio.ServicioUsuarios;
import Dominio.Usuario;

public class Main {
    public static void main(String[] args) {
        try {
            ServicioUsuarios servicioUsuarios = new ServicioUsuarios();
            Usuario usuario = servicioUsuarios.iniciarSesion();
            if (usuario != null) {
                ServicioAlmuerzo servicioAlmuerzo = new ServicioAlmuerzo(servicioUsuarios);
                servicioAlmuerzo.iniciar(usuario);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
