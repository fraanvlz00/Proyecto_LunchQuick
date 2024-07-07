package Ventanas;

import Dominio.Cliente;
import Dominio.ServicioPedidos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Historial extends JFrame implements ActionListener, FocusListener {
    private JPanel panel1;
    private JTextArea textArea2;
    private JTextArea txaMHistorial;
    private JButton btnVolverH;
    private JPanel jpHistorial;
    private JButton btnMHistorial;
    private Cliente cliente;

    public Historial(Cliente cliente) {
        this.cliente = cliente;
        PantallaHistorial();
    }

    public void PantallaHistorial(){
        setSize(500,500);
        setTitle("ElegirDia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(jpHistorial);
        setVisible(true);
        jpHistorial.setFocusable(true);
        jpHistorial.requestFocusInWindow();
        btnVolverH.addActionListener(this);
        btnMHistorial.addActionListener(this);
    }
    public void setHistorialCompras(String historialCompras) {
        txaMHistorial.setText(historialCompras);
        txaMHistorial.setEditable(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnVolverH) {
            Comprar comprar = new Comprar();
            comprar.PantallaCompra();
            setVisible(false);
        }
        if (e.getSource() == btnMHistorial) {
            ServicioPedidos servicioPedidos = new ServicioPedidos();
            String historialCompras = servicioPedidos.verAlmuerzosComprados(cliente);
            setHistorialCompras(historialCompras);
        }
        }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}


