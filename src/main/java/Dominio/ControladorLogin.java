package Dominio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class ControladorLogin {
    private ObjectMapper mapper;
    private File jsonFile;
    private Pagos pagos;

    public ControladorLogin() {
        try {
            mapper = new ObjectMapper();
            jsonFile = new File("src/main/java/Datos/usuarios.json");
            pagos = new Pagos();
            if (!jsonFile.exists()) {
                throw new IOException("Archivo JSON no encontrado en la ruta especificada: " + jsonFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("Error al inicializar el Gestor de Sesión: " + e.getMessage());
        }
    }

    public Usuario iniciarSesion() {
        Scanner scanner = new Scanner(System.in);
        String correo = obtenerEntrada(scanner, "Ingrese su correo electrónico: ");
        String contraseña = obtenerEntrada(scanner, "Ingrese su contraseña: ");
        return verificarCredenciales(correo, contraseña);
    }

    public boolean registrarse() {
        Scanner scanner = new Scanner(System.in);
        String correo = obtenerEntrada(scanner, "Ingrese su correo electrónico: ");
        String contraseña = obtenerEntrada(scanner, "Ingrese su contraseña: ");
        return registrarNuevoUsuario(correo, contraseña);
    }

    private String obtenerEntrada(Scanner scanner, String mensaje) {
        System.out.println(mensaje);
        return scanner.nextLine();
    }

    private Usuario verificarCredenciales(String correo, String contraseña) {
        try {
            JsonNode rootUsuarios = mapper.readTree(jsonFile);
            Iterator<JsonNode> usuarios = rootUsuarios.get("usuarios").elements();
            while (usuarios.hasNext()) {
                JsonNode usuarioNode = usuarios.next();
                if (credencialesValidas(usuarioNode, correo, contraseña)) {
                    return crearUsuarioDesdeJson(usuarioNode);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al procesar el inicio de sesión: " + e.getMessage());
        }
        System.out.println("Correo electrónico o contraseña incorrectos.");
        return null;
    }

    private boolean credencialesValidas(JsonNode usuarioNode, String correo, String contraseña) {
        return usuarioNode.has("correoElectronico") && usuarioNode.has("contraseña") &&
                usuarioNode.get("correoElectronico").asText().equals(correo) &&
                usuarioNode.get("contraseña").asText().equals(contraseña);
    }

    private Usuario crearUsuarioDesdeJson(JsonNode usuarioNode) {
        String correo = usuarioNode.get("correoElectronico").asText();
        String contraseña = usuarioNode.get("contraseña").asText();
        TipoUsuario tipo = TipoUsuario.valueOf(usuarioNode.get("tipo").asText());
        if (tipo == TipoUsuario.ADMIN) {
            return new Admin(correo, contraseña);
        } else {
            return new Cliente(correo, contraseña);
        }
    }

    private boolean registrarNuevoUsuario(String correo, String contraseña) {
        try {
            JsonNode rootUsuarios = mapper.readTree(jsonFile);
            if (correoYaRegistrado(rootUsuarios, correo)) {
                System.out.println("El correo electrónico ya está registrado.");
                return false;
            }
            agregarUsuarioAlJson(rootUsuarios, correo, contraseña);
            guardarJsonEnArchivo(rootUsuarios);
            System.out.println("Registro exitoso.");
            return true;
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    private boolean correoYaRegistrado(JsonNode rootUsuarios, String correo) {
        Iterator<JsonNode> usuarios = rootUsuarios.get("usuarios").elements();
        while (usuarios.hasNext()) {
            JsonNode usuarioNode = usuarios.next();
            if (usuarioNode.get("correoElectronico").asText().equals(correo)) {
                return true;
            }
        }
        return false;
    }

    private void agregarUsuarioAlJson(JsonNode rootUsuarios, String correo, String contraseña) {
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode newNode = factory.objectNode();
        newNode.put("correoElectronico", correo);
        newNode.put("contraseña", contraseña);
        newNode.put("tipo", TipoUsuario.CLIENTE.name());
        ArrayNode usuariosArray = (ArrayNode) rootUsuarios.get("usuarios");
        usuariosArray.add(newNode);
    }

    private void guardarJsonEnArchivo(JsonNode rootUsuarios) throws IOException {
        try (FileWriter fileWriter = new FileWriter(jsonFile)) {
            mapper.writeValue(fileWriter, rootUsuarios);
        }
    }
}
