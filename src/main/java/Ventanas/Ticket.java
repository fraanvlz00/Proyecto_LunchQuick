package Ventanas;

import Dominio.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ticket extends JFrame implements ActionListener {
    private JPanel panel1;
    private JLabel lblNumeroRetiro;
    private JTextArea txtDetalles;
    private JButton volverButton;

    public Ticket(Cliente cliente, String almuerzoComprado, int numeroRetiro) {
        // Inicializa los componentes
        lblNumeroRetiro = new JLabel("Número de Retiro: " + numeroRetiro);
        txtDetalles = new JTextArea(almuerzoComprado);
        txtDetalles.setEditable(false);
        volverButton = new JButton("Volver");

        // Configura el layout del panel
        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.add(lblNumeroRetiro);
        panel1.add(new JScrollPane(txtDetalles));
        panel1.add(volverButton);

        // Añade listeners
        volverButton.addActionListener(this);

        // Configura el JFrame
        setContentPane(panel1);
        setSize(400, 300);
        setTitle("Ticket");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == volverButton) {
            // Cierra la ventana de Ticket
            this.dispose();
        }
    }
}
