package Dominio;
import Launcher.Main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

public class Usuario {
    private String nombre;
    private String correoElectronico;
    private String contrasena;
    private String rol;
    private String almuerzoComprado;
    private JsonNode root;
    private ObjectMapper mapper;

    public Usuario(String nombre, String correoElectronico, String contrasena, String rol){
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.rol = rol;
    }
    public Usuario() throws IOException {
        mapper = new ObjectMapper();
        ClassLoader classLoader = Main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("datos/usuarios.json");
        if (inputStream == null) {
            throw new IOException("Archivo JSON no encontrado en el classpath");
        }
        root = mapper.readTree(inputStream);
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    public String getAlmuerzoComprado() {
        return almuerzoComprado;
    }

    public void setAlmuerzoComprado(String almuerzoComprado) {
        this.almuerzoComprado = almuerzoComprado;
    }
    public Usuario iniciarSesion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.println("Ingrese su contraseña: ");
        String clave = scanner.nextLine();

        Iterator<JsonNode> usuarios = root.get("usuarios").elements();
        while (usuarios.hasNext()) {
            JsonNode usuarioNode = usuarios.next();
            if (usuarioNode.get("correoElectronico").asText().equals(correo) &&
                    usuarioNode.get("contraseña").asText().equals(clave)) {
                try {
                    Usuario usuario = new Usuario();
                    usuario.setCorreoElectronico(correo);
                    usuario.setContrasena(clave);
                    System.out.println("Inicio de sesión exitoso.");
                    return usuario;
                } catch (IOException e) {
                    System.out.println("Error al leer el archivo JSON: " + e.getMessage());
                    return null;
                }
            }
        }

        System.out.println("Correo electrónico o contraseña incorrectos.");
        return null;
    }


}