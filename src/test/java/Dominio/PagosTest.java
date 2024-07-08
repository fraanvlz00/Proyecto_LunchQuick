package Dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PagosTest {

    private Pagos pagos;

    @BeforeEach
    public void setUp() {
        pagos = new Pagos();
    }

    @Test
    public void testCargarPagos() {
        assertNotNull(pagos, "El objeto pagos no debería ser nulo");
        assertFalse(pagos.pagos.isEmpty(), "La lista de pagos no debería estar vacía después de cargar");
    }

    @Test
    public void testVerificarPago() {
        // Asumiendo que hay un registro en el archivo JSON de pagos con rut "12345678-9" y código "codigo123"
        assertTrue(pagos.verificarPago("12345678-9", "codigo123"), "El pago debería ser verificado correctamente");
        assertFalse(pagos.verificarPago("12345678-9", "codigoIncorrecto"), "El pago no debería ser verificado con un código incorrecto");
        assertFalse(pagos.verificarPago("rutInexistente", "codigo123"), "El pago no debería ser verificado con un RUT inexistente");
    }
}
