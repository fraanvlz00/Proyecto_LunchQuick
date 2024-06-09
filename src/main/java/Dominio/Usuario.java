package Dominio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

public class Usuario {
    private String correoElectronico;
    private String contraseña;
    private String almuerzoComprado;
    private JsonNode root;
    private ObjectMapper mapper;

    // Constructor para inicializar el objeto Usuario
    public Usuario(String correoElectronico, String contraseña, String almuerzoComprado) {
        this.correoElectronico = correoElectronico;
        this.contraseña = contraseña;
        this.almuerzoComprado = almuerzoComprado;
    }

    // Constructor para inicializar el JSON y el ObjectMapper
    public Usuario() throws IOException {
        mapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("Datos/usuarios.json");
        if (inputStream == null) {
            throw new IOException("Archivo JSON no encontrado en el classpath");
        }
        root = mapper.readTree(inputStream);
    }

    // Getters y setters
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getAlmuerzoComprado() {
        return almuerzoComprado;
    }

    public void setAlmuerzoComprado(String almuerzoComprado) {
        this.almuerzoComprado = almuerzoComprado;
    }

    // Método para iniciar sesión
    public Usuario iniciarSesion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.println("Ingrese su contraseña: ");
        String contraseña = scanner.nextLine();

        try {
            Iterator<JsonNode> usuarios = root.get("usuarios").elements();
            while (usuarios.hasNext()) {
                JsonNode usuarioNode = usuarios.next();
                if (usuarioNode.get("correoElectronico").asText().equals(correo) &&
                        usuarioNode.get("contraseña").asText().equals(contraseña)) {
                    Usuario usuario = new Usuario(correo, contraseña, "");
                    System.out.println("Inicio de sesión exitoso.");
                    return usuario;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al procesar el inicio de sesión: " + e.getMessage());
        }

        System.out.println("Correo electrónico o contraseña incorrectos.");
        return null;
    }
}

