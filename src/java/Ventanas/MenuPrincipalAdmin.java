import javax.swing.*;

public class MenuPrincipalAdmin {
    private JPanel panel1;
    private JButton pedidosPendientesButton;
    private JButton salirButton;
    private Runnable onPedidosPendientes;
    private Runnable onSalir;

    public MenuPrincipalAdmin() {
        pedidosPendientesButton.addActionListener(e -> {
            if (onPedidosPendientes != null) {
                onPedidosPendientes.run();
            }
        });

        salirButton.addActionListener(e -> {
            if (onSalir != null) {
                onSalir.run();
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setOnPedidosPendientes(Runnable onPedidosPendientes) {
        this.onPedidosPendientes = onPedidosPendientes;
    }

    public void setOnSalir(Runnable onSalir) {
        this.onSalir = onSalir;
    }
}
