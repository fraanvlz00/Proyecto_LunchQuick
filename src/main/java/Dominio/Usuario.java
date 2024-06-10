package Dominio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Usuario {
    private String correoElectronico;
    private String contraseña;
    private String almuerzoComprado;
    private JsonNode root;
    private ObjectMapper mapper;
    private String rut; // Agregar atributo rut

    // Constructor para inicializar el objeto Usuario
    public Usuario(String correoElectronico, String contraseña, String almuerzoComprado) {
        this.correoElectronico = correoElectronico;
        this.contraseña = contraseña;
        this.almuerzoComprado = almuerzoComprado;
    }

    // Constructor para inicializar el JSON y el ObjectMapper
    public Usuario() throws IOException {
        mapper = new ObjectMapper();
        File jsonFile = new File("src/main/java/Datos/usuarios.json");
        if (!jsonFile.exists()) {
            throw new IOException("Archivo JSON no encontrado en la ruta especificada: " + jsonFile.getAbsolutePath());
        }
        root = mapper.readTree(jsonFile);
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

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
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

                if (usuarioNode.has("correoElectronico") && usuarioNode.has("contraseña")) {
                    String jsonCorreo = usuarioNode.get("correoElectronico").asText();
                    String jsonContraseña = usuarioNode.get("contraseña").asText();

                    if (jsonCorreo.equals(correo) && jsonContraseña.equals(contraseña)) {
                        Usuario usuario = new Usuario(correo, contraseña, "");
                        if (usuarioNode.has("rut")) {
                            usuario.setRut(usuarioNode.get("rut").asText()); // Obtener el RUT del JSON
                        }
                        System.out.println("Inicio de sesión exitoso.");
                        return usuario;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al procesar el inicio de sesión: " + e.getMessage());
        }

        System.out.println("Correo electrónico o contraseña incorrectos.");
        return null;
    }
}
