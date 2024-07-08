package Dominio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




public class ServicioPedidosTest {

    private Usuario usuario;
    private Pagos pagos;
    private ServicioPedidos servicioPedidos;

    @BeforeEach
    public void setUp() throws IOException {
        usuario = new Usuario("test@example.com", "password123", "");
        usuario.setRut("12345678-9");
        pagos = mock(Pagos.class);
        when(pagos.verificarPago(anyString(), anyString())).thenReturn(true);

        servicioPedidos = new ServicioPedidos();
    }

    @Test
    public void testActualizarJsonUsuario() throws IOException {
        File jsonFileUsuarios = new File("src/main/java/Datos/usuarios.json");
        assertTrue(jsonFileUsuarios.exists(), "El archivo JSON de usuarios debería existir");

        servicioPedidos.actualizarJsonUsuario(usuario, "lunes");
        // Verificar que el usuario ha sido actualizado en el archivo JSON (puedes leer el archivo y verificar)
    }



    @Test
    void testVerAlmuerzosComprados() {
        ServicioPedidos servicioPedidos = new ServicioPedidos();
        Usuario usuario = new Usuario("test@example.com");

        assertDoesNotThrow(() -> {
            servicioPedidos.verAlmuerzosComprados(usuario);
        });
    }
}

