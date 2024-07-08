package Ventanas;

import Dominio.Cliente;
import Dominio.Pagos;
import Dominio.ServicioPedidos;
import Dominio.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class Pago extends JFrame implements ActionListener, FocusListener {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton btnPagar;
    private JLabel lbRut;
    private JLabel lbCodigoBaes;
    private JButton btnVolver;
    private JPanel jpPagos;

    private ServicioPedidos servicioPedidos;
    private Cliente cliente;
    private Pagos pagos;
    private Usuario usuario;

    public Pago() {
        this.usuario = usuario;
        this.pagos = new Pagos();
        this.servicioPedidos = new ServicioPedidos();
    }

    public void Pantalla() {
        setSize(500, 500);
        setTitle("Pago");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(jpPagos);
        setVisible(true);
        jpPagos.setFocusable(true);
        jpPagos.requestFocusInWindow();
        btnPagar.addActionListener(this);
        btnVolver.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPagar) {
            ArrayList pagosRealizados = procesarPago();
            if (pagosRealizados.size() != 0){
                 Ticket ticket = new Ticket(pagosRealizados);
                 ticket.Pantalla();
            }
            this.dispose();
        }
        else if (e.getSource() == btnVolver) {

            this.dispose();
        }
    }

    private ArrayList procesarPago() {
        String rut = textField1.getText();
        String codigoBaes = new String(passwordField1.getPassword());
        ArrayList pagosRealizados = new ArrayList<>();

        if (pagos.verificarPago(rut, codigoBaes)) {
            JOptionPane.showMessageDialog(this, "Pago verificado.");

            String almuerzoComprado = servicioPedidos.getDetallesAlmuerzoComprado();
            String numeroRetiro = Integer.toString(servicioPedidos.getNumeroRetiro()) ;

            pagosRealizados.add(almuerzoComprado);
            pagosRealizados.add(numeroRetiro);
            System.out.println(almuerzoComprado);
            System.out.println(numeroRetiro);
            return pagosRealizados;

        } else {
            JOptionPane.showMessageDialog(this, "Pago no verificado o no disponible, no se puede agregar el pedido. Inténtelo nuevamente.");
            return pagosRealizados;
        }
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
