package Ventanas;

import Dominio.ServicioPedidos;
import Dominio.Cliente;
import Dominio.Pagos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pago extends JFrame implements ActionListener {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton btnPagar;
    private JLabel lbRut;
    private JLabel lbCodigoBaes;
    private JButton volverButton;

    private ServicioPedidos servicioPedidos;
    private Cliente cliente;
    private Pagos pagos;

    public Pago(ServicioPedidos servicioPedidos, Cliente cliente, Pagos pagos) {
        this.servicioPedidos = servicioPedidos;
        this.cliente = cliente;
        this.pagos = pagos;

        // Inicializa los componentes
        panel1 = new JPanel();
        textField1 = new JTextField(20);
        passwordField1 = new JPasswordField(20);
        btnPagar = new JButton("Pagar");
        volverButton = new JButton("Volver");
        lbRut = new JLabel("RUT:");
        lbCodigoBaes = new JLabel("Código BAES:");

        // Configura el layout del panel
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.add(lbRut);
        panel1.add(textField1);
        panel1.add(lbCodigoBaes);
        panel1.add(passwordField1);
        panel1.add(btnPagar);
        panel1.add(volverButton);

        // Añade listeners
        btnPagar.addActionListener(this);
        volverButton.addActionListener(this);

        // Configura el JFrame
        setContentPane(panel1);
        setSize(400, 300);
        setTitle("Pago");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPagar) {
            procesarPago();
        } else if (e.getSource() == volverButton) {
            // Volver a la ventana anterior o cerrar la ventana de pago
            this.dispose();
        }
    }

    private void procesarPago() {
        String rut = textField1.getText();
        String codigoBaes = new String(passwordField1.getPassword());

        if (pagos.verificarPago(rut, codigoBaes)) {
            JOptionPane.showMessageDialog(this, "Pago verificado.");

            // Realizar la compra del almuerzo y obtener los detalles
            servicioPedidos.comprarAlmuerzo(cliente, pagos);

            // Obtener los detalles del almuerzo comprado y el número de retiro
            String almuerzoComprado = servicioPedidos.getDetallesAlmuerzoComprado();
            int numeroRetiro = servicioPedidos.getNumeroRetiro();

            // Abrir la ventana de Ticket
            Ticket ticketVentana = new Ticket(cliente, almuerzoComprado, numeroRetiro);
            ticketVentana.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Pago no verificado o no disponible, no se puede agregar el pedido. Inténtelo nuevamente.");
        }
    }
}
