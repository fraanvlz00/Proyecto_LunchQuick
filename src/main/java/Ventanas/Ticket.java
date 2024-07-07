package Ventanas;

import Dominio.Cliente;
import Dominio.ServicioPedidos;

import javax.swing.*;

public class Ticket extends JFrame {
    private JPanel panel1;
    private JLabel lblNumeroRetiro;
    private JTextArea txtDetalles;
    private JButton volverButton;
    private Cliente cliente;
    private ServicioPedidos servicioPedidos;

    public Ticket(int numeroRetiro, String detalles) {
        // Configuración de la ventana
        setSize(400, 300);
        setTitle("Ticket de Compra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Inicialización de componentes
        panel1 = new JPanel();
        lblNumeroRetiro = new JLabel("Número de Retiro: " + numeroRetiro);
        txtDetalles = new JTextArea(detalles);
        txtDetalles.setEditable(false);
        volverButton = new JButton("Volver");

        // Configuración de layout y adición de componentes
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.add(lblNumeroRetiro);
        panel1.add(new JScrollPane(txtDetalles));
        panel1.add(volverButton);

        // Añadir panel a la ventana
        setContentPane(panel1);
        setVisible(true);

        // Agregar acción al botón volver
        volverButton.addActionListener(e -> {
            Comprar comprar = new Comprar(servicioPedidos, cliente);
            comprar.PantallaCompra();
            setVisible(false);
        });
    }
}
