package Launcher;

import Dominio.Usuario;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Usuario usuario = new Usuario();
            Usuario usuarioLogueado = usuario.iniciarSesion();
            if (usuarioLogueado != null) {
                // Lógica adicional después del inicio de sesión exitoso
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
        }
    }
}

