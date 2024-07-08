import javax.swing.*;

public class DiaPedidoAdmin {
    private JComboBox<String> seleccioneOpcionCheckBox;
    private JPanel panel1;
    private JButton siguienteButton;
    private JButton volverButton;
    private Runnable onVolver;

    public DiaPedidoAdmin() {
        volverButton.addActionListener(e -> {
            if (onVolver != null) {
                onVolver.run();
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setOnVolver(Runnable onVolver) {
        this.onVolver = onVolver;
    }
}
