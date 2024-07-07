package Ventanas;

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
    private JButton btnMostrarHistorial;

    public Historial() {
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
        btnMostrarHistorial.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnVolverH) {
            Comprar comprar = new Comprar();
            comprar.PantallaCompra();
            setVisible(false);
        }
        if (e.getSource() == btnMostrarHistorial) {
            ServicioPedidos servicioPedidos = new ServicioPedidos();
            txaMHistorial.setText(servicioPedidos.obtenerHistorialCompras());
            txaMHistorial.setEditable(false);

        }
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
