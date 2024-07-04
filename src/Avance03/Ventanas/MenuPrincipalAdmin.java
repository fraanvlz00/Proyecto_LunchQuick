import javax.swing.*;

public class MenuPrincipalAdmin {
    private JPanel panel1;
    private JButton pedidosPendientesButton;
    private JButton salirButton;
    private Runnable onPedidosPendientes;

    public MenuPrincipalAdmin() {
        pedidosPendientesButton.addActionListener(e -> {
            if (onPedidosPendientes != null) {
                onPedidosPendientes.run();
            }
        });

        salirButton.addActionListener(e -> {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
            currentFrame.dispose();
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setOnPedidosPendientes(Runnable onPedidosPendientes) {
        this.onPedidosPendientes = onPedidosPendientes;
    }
}
