package Dominio;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ServicioUsuarios {
    private List<Usuario> usuarios;
    private ObjectMapper mapper;
    private Scanner scanner;

    public ServicioUsuarios() throws IOException {
        mapper = new ObjectMapper();
        File file = new File("src/Datos/usuarios.json");
        usuarios = List.of(mapper.readValue(file, Usuario[].class));
        scanner = new Scanner(System.in);
    }

    public Usuario iniciarSesion() {
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        for (Usuario usuario : usuarios) {
            if (usuario.getCorreoElectronico().equals(correo) &&
                    usuario.getContrasena().equals(contrasena)) {
                System.out.println("Inicio de sesión exitoso.");
                return usuario;
            }
        }
        System.out.println("Correo electrónico o contraseña incorrectos.");
        return null;
    }

    public void verPedidos(Usuario usuario) {
        List<String> pedidos = usuario.getAlmuerzosComprados();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            System.out.println("Pedidos registrados:");
            for (String pedido : pedidos) {
                System.out.println(pedido);
            }
        }
    }
}
