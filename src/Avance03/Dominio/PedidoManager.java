import javax.swing.*;

public class PedidoManager {
    private PedidoGUI pedidoGUI;

    public PedidoManager() {
        pedidoGUI = new PedidoGUI();
    }

    public void nuevoPedido(String numeroPedido) {
        pedidoGUI.agregarPedido(numeroPedido);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PedidoManager manager = new PedidoManager();

            // Agregar algunos pedidos de ejemplo
            for (int i = 0; i < 10; i++) {
                manager.nuevoPedido(String.valueOf(i));
            }
        });
    }
}
