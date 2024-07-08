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

}
