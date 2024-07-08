package Ventanas;

import Dominio.ServicioPedidos;
import Dominio.Menu;
import Dominio.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MenuCompra extends JFrame implements ActionListener, FocusListener {
    private JPanel panel1;
    private JComboBox cbxEnsalada;
    private JComboBox cbxBebestible;
    private JComboBox cbxPostre;
    private JComboBox cbxPfondo;
    private JButton btnPagarEco;
    private JButton btnVolverEco;
    private JPanel jpEconomico;
    private JComboBox cbxDia;
    private JComboBox cbxTipoMenu;
    private JButton btnCambiar;
    private JComboBox cbxPan;
    private JComboBox cbxSopa;
    private JLabel lblSopa;
    private JLabel lblAcompañamiento;
    private  ServicioPedidos servicioPedidos = new ServicioPedidos();
    private Usuario usuario;

    public void PantallaMenuCompra() {
        setSize(700, 700);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(jpEconomico);
        setVisible(true);
        jpEconomico.setFocusable(true);
        jpEconomico.requestFocusInWindow();
        btnVolverEco.addActionListener(this);
        btnPagarEco.addActionListener(this);
        btnCambiar.addActionListener(this);
        llamarSupremo();

    }
    public void llamarSupremo() {
        Menu menu = servicioPedidos.obtenerMenu((String)cbxDia.getSelectedItem(),(String)cbxTipoMenu.getSelectedItem());
        llamarbebestible(menu);
        llamarpan(menu);
        llamarSopa(menu);
        llamarPostre(menu);
        llamarPlatoFondo(menu);
        llamarEnsalada(menu);
    }

    public void llamarbebestible(Menu menu) {
        cbxBebestible.removeAllItems();
        String[] bebidasMenu = menu.getBebestibles();
        if (bebidasMenu != null) {
            for (String bebida : bebidasMenu) {
                cbxBebestible.addItem(bebida);
            }
        }
    }

    public void llamarpan(Menu menu) {
        cbxPan.removeAllItems();
        String[] bebidasMenu = menu.getAcompañamiento();
        if (bebidasMenu != null) {
            for (String pan : bebidasMenu) {
                cbxPan.addItem(pan);
            }
        }
    }

    public void llamarSopa(Menu menu) {
        cbxSopa.removeAllItems();
        String[] bebidasMenu = menu.getSopa();
        if (bebidasMenu != null) {
            for (String sopa : bebidasMenu) {
                cbxSopa.addItem(sopa);
            }
        }
    }

    public void llamarPostre(Menu menu) {
        cbxPostre.removeAllItems();
        String[] bebidasMenu = menu.getPostre();
        if (bebidasMenu != null) {
            for (String postre : bebidasMenu) {
                cbxPostre.addItem(postre);
            }
        }
    }

    public void llamarPlatoFondo(Menu menu) {
        cbxPfondo.removeAllItems();
        String[] bebidasMenu = menu.getPlatoDeFondo();
        if (bebidasMenu != null) {
            for (String platoFondo : bebidasMenu) {
                cbxPfondo.addItem(platoFondo);
            }
        }
    }

    public void llamarEnsalada(Menu menu) {
        cbxEnsalada.removeAllItems();
        String[] bebidasMenu = menu.getEnsalada();
        if (bebidasMenu != null) {
            for (String ensalada : bebidasMenu) {
                cbxEnsalada.addItem(ensalada);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnVolverEco) {
            Comprar elegirDiaSemana = new Comprar(usuario);
            elegirDiaSemana.PantallaCompra();
            setVisible(false);
        }
        else if (e.getSource() == btnCambiar) {
            System.out.println("xd");
            llamarSupremo();
            JOptionPane.showMessageDialog(null, "Cambios realizados");

        }
        else if (e.getSource() ==btnPagarEco ) {

            setVisible(false);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
