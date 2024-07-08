package Ventanas;

import Dominio.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Ticket extends JFrame implements ActionListener {
    private JPanel panel1;
    private JLabel lblNumeroRetiro;
    private JTextArea txtDetalles;
    private JButton btnvolverTicket;
    private JPanel jpTicket;
    private JLabel lbRetiro;
    private JLabel lblDetalles;
    private JLabel lblAlmuerzo;
    private Usuario usuario;

    public void Pantalla() {
        setSize(500, 500);
        setTitle("Pago");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(jpTicket);
        setVisible(true);
        jpTicket.setFocusable(true);
        jpTicket.requestFocusInWindow();
        btnvolverTicket.addActionListener(this);
    }

    public Ticket(ArrayList pagosRealizados) {
        lblAlmuerzo.setText((String) pagosRealizados.get(0));
        lblNumeroRetiro.setText((String) pagosRealizados.get(1));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnvolverTicket) {
            Comprar comprar = new Comprar(usuario);
            comprar.PantallaCompra();
            this.dispose();
        }
    }
}
