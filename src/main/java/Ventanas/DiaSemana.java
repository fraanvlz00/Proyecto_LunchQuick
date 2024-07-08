package Ventanas;

import Dominio.ServicioPedidos;
import Dominio.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class DiaSemana extends JFrame implements ActionListener, FocusListener {
    private JPanel jpDiaSemana;
    private JButton btnDiaSigiente;
    private JComboBox cbDia;
    private JButton btnDiaVolver;
    private JLabel lblDiaElgido;
    private JLabel lblLunchDia;
    private Usuario usuario;
    private ServicioPedidos servicioPedidos;

    public DiaSemana() {
        servicioPedidos = new ServicioPedidos();

    }


    public void PantallaDia(){
        setSize(500,500);
        setTitle("ElegirDia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(jpDiaSemana);
        setVisible(true);
        jpDiaSemana.setFocusable(true);
        jpDiaSemana.requestFocusInWindow();
        btnDiaSigiente.addActionListener(this);
        btnDiaVolver.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDiaSigiente){
            String diaElegido = (String) cbDia.getSelectedItem();
            lblDiaElgido.setText("Día elegido: " + diaElegido);
            lblLunchDia.setText("Lunch del día: " + diaElegido);
            ElegirTipoMenu elegirTipoMenu = new ElegirTipoMenu();
            elegirTipoMenu.PantallaMenu();
            setVisible(false);

        }
        if (e.getSource() == btnDiaVolver){
           Comprar comprar = new Comprar(usuario);
           comprar.PantallaCompra();
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
