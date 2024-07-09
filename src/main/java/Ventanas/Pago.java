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
import java.io.IOException;
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

    private String pedido;

    private ServicioPedidos servicioPedidos;
    private Cliente cliente;
    private Pagos pagos;
    private Usuario usuario;

    private String dia;

    public Pago() {
        this.usuario = usuario;
        this.pagos = new Pagos();
        this.pedido = "";
    }

    public Pago(Usuario usu, Pagos pag, String pedido, ServicioPedidos servicioPedido, String dia, Cliente cli) {
        this.pagos = new Pagos();
        this.pedido = pedido;
        this.servicioPedidos = servicioPedido;
        this.dia = dia;
        this.cliente = new Cliente(usu.getCorreoElectronico(), usu.getContraseña());
        this.usuario = usu;
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
            String pagosRealizados = procesarPagoDos();

            try{
               pagosRealizados = "" + servicioPedidos.actualizarJsonDia(dia, cliente, pedido);
            }catch (Exception e1) {
                System.out.println("Error al leer los almuerzos comprados: " + e1.getMessage());
            }


            if (!"".equals(pagosRealizados)){
                 Ticket ticket = new Ticket(usuario,"TIckets",pagosRealizados, pedido);
                 ticket.Pantalla();
                 setVisible(false);
            }
            this.dispose();
        }
        else if (e.getSource() == btnVolver) {

            this.dispose();
        }
    }

    public ArrayList procesarPago() {
        String rut = textField1.getText();
        String codigoBaes = new String(passwordField1.getPassword());
        ArrayList pagosRealizados = new ArrayList<>();

        if (pagos.verificarPago(rut, codigoBaes)) {
            JOptionPane.showMessageDialog(this, "Pago verificado.");

            String almuerzoComprado = servicioPedidos.getDetallesAlmuerzoComprado();
            String numeroRetiro = Integer.toString(servicioPedidos.getNumeroRetiro()) ;

            pagosRealizados.add(almuerzoComprado);
            pagosRealizados.add(numeroRetiro);

            return pagosRealizados;

        } else {
            JOptionPane.showMessageDialog(this, "Pago no verificado o no disponible, no se puede agregar el pedido. Inténtelo nuevamente.");
            return pagosRealizados;
        }
    }

    public String procesarPagoDos() {
        String rut = textField1.getText();
        String codigoBaes = new String(passwordField1.getPassword());
        String pagosRealizados = "";

        if (pagos.verificarPago(rut, codigoBaes)) {
            JOptionPane.showMessageDialog(this, "Pago verificado.");

            pagosRealizados = Integer.toString(servicioPedidos.getNumeroRetiro()) ;

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
