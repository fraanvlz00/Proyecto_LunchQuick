package Ventanas;

import Dominio.Cliente;
import Dominio.Pagos;
import Dominio.ServicioPedidos;

import javax.swing.*;

public class Pago extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton btnPagar;
    private JLabel lbRut;
    private JLabel lbCodigoBaes;
    private JButton volverButton;
    private ServicioPedidos servicioPedidos;
    private Cliente cliente;
    private String diaSeleccionado;
    private String tipoMenuSeleccionado;

    public Pago(ServicioPedidos servicioPedidos, Cliente cliente, String diaSeleccionado, String tipoMenuSeleccionado) {
        this.servicioPedidos = servicioPedidos;
        this.cliente = cliente;
        this.diaSeleccionado = diaSeleccionado;
        this.tipoMenuSeleccionado = tipoMenuSeleccionado;

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

            if (servicioPedidos.getPagos().verificarPago(rut, codigoBaes)) {
                JOptionPane.showMessageDialog(this, "Pago verificado correctamente.");
                int numeroRetiro = servicioPedidos.procesarCompra(cliente, diaSeleccionado, tipoMenuSeleccionado);
                String detalles = servicioPedidos.obtenerDetallesCompra(cliente);

                // Mostrar la ventana Ticket
                Ticket ticket = new Ticket(numeroRetiro, detalles);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error: RUT o código incorrecto.");
            }
        });

        // Agregar acción al botón volver
        volverButton.addActionListener(e -> {
            Comprar comprar = new Comprar(servicioPedidos, cliente);
            comprar.PantallaCompra();
            setVisible(false);
        });
    }
}
