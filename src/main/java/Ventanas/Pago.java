package Ventanas;

import Dominio.Pagos;
import javax.swing.*;

public class Pago extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton btnPagar;
    private JLabel lbRut;
    private JLabel lbCodigoBaes;
    private Pagos pagos;

    public Pago() {
        // Inicializar los pagos
        pagos = new Pagos();

        // Configuración de la ventana
        setSize(500, 500);
        setTitle("Ventana de Pago");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(panel1);
        setVisible(true);

        // Agregar acción al botón pagar
        btnPagar.addActionListener(e -> {
            String rut = textField1.getText();
            String codigoBaes = new String(passwordField1.getPassword());

            if (pagos.verificarPago(rut, codigoBaes)) {
                JOptionPane.showMessageDialog(this, "Pago verificado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error: RUT o código incorrecto.");
            }
        });
    }


}
