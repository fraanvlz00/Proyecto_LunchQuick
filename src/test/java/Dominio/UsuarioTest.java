package Dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;  // Asegúrate de que esta línea esté presente

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario("test@example.com", "password123", "");
    }

    @Test
    public void testJsonConstructorThrowsIOException() {
        // Crear un mock para el archivo
        File mockFile = mock(File.class);

        // Simular que el archivo no existe
        when(mockFile.exists()).thenReturn(false);
        when(mockFile.getAbsolutePath()).thenReturn("ruta/inexistente/usuarios.json");

        try {
            new Usuario(mockFile);
            fail("Debería lanzar IOException si el archivo JSON no existe");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("Archivo JSON no encontrado"), "El mensaje de excepción debería contener 'Archivo JSON no encontrado'");
        }
    }

    @Test
    public void testUsuarioCreacion() {
        assertNotNull(usuario, "El objeto usuario no debería ser nulo");
        assertEquals("test@example.com", usuario.getCorreoElectronico(), "El correo electrónico debería ser 'test@example.com'");
        assertEquals("password123", usuario.getContraseña(), "La contraseña debería ser 'password123'");
    }

    @Test
    public void testGettersAndSetters() {
        usuario.setCorreoElectronico("nuevo@example.com");
        assertEquals("nuevo@example.com", usuario.getCorreoElectronico(), "El correo electrónico debería ser 'nuevo@example.com'");

        usuario.setContraseña("newpassword123");
        assertEquals("newpassword123", usuario.getContraseña(), "La contraseña debería ser 'newpassword123'");

        usuario.setRut("12345678-9");
        assertEquals("12345678-9", usuario.getRut(), "El RUT debería ser '12345678-9'");
    }

    @Test
    public void testAgregarAlmuerzoComprado() {
        usuario.agregarAlmuerzoComprado("lunes", "vegetariano");
        HashMap<String, String> almuerzos = usuario.getAlmuerzosComprados();
        assertEquals(1, almuerzos.size(), "Debería haber 1 almuerzo comprado");
        assertEquals("vegetariano", almuerzos.get("lunes"), "El almuerzo del lunes debería ser 'vegetariano'");
    }

    @Test
    public void testJsonConstructor() {
        try {
            Usuario usuarioConJson = new Usuario();
            assertNotNull(usuarioConJson, "El objeto usuario no debería ser nulo");
            assertNotNull(usuarioConJson.root, "El objeto root no debería ser nulo");
        } catch (IOException e) {
            fail("No debería lanzar IOException: " + e.getMessage());
        }
    }
}

