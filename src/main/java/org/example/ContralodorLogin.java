package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContralodorLogin {
    private static final String FILE_PATH = "src/main/java/Datos/usuarios.json";
    private List<Persona> usuarios;
    private ObjectMapper objectMapper;

    public ContralodorLogin() {
        this.usuarios = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
        cargarUsuarios();
    }

    public boolean registrar(String username, String email, String password) {
        if (buscarUsuarioPorEmail(email) == null) {
            usuarios.add(new Persona(username, email, password));
            guardarUsuarios();
            return true;
        }
        return false;
    }

    public Persona login(String email, String password) {
        Persona usuario = buscarUsuarioPorEmail(email);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null; // Usuario no encontrado o contraseña incorrecta
    }

    private Persona buscarUsuarioPorEmail(String email) {
        for (Persona usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }

    private void guardarUsuarios() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), usuarios);
            System.out.println("Usuarios guardados exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarUsuarios() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try {
                usuarios = objectMapper.readValue(file, new TypeReference<List<Persona>>() {});
                System.out.println("Usuarios cargados exitosamente.");
            } catch (IOException e) {
                System.err.println("Error al cargar usuarios: " + e.getMessage());
                e.printStackTrace();
                usuarios = new ArrayList<>();
            }
        } else {
            System.out.println("Archivo no encontrado, iniciando con una lista vacía.");
            usuarios = new ArrayList<>();
        }
    }
}
