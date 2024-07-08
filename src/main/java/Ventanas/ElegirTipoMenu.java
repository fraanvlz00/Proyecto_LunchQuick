package Ventanas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ElegirTipoMenu  extends JFrame implements ActionListener, FocusListener {
    private JLabel lblTitlemenu;
    private JPanel panel1;
    private JButton btnEconomico;
    private JButton btnBaes;
    private JButton btnEjecutivo;
    private JLabel iconUfro;
    private JPanel jpElegirmenu;
    private JButton btnVolverMenus;
    private JButton btnVegano;


    public void PantallaMenu() {
        setSize(500, 500);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(jpElegirmenu);
        setVisible(true);
        jpElegirmenu.setFocusable(true);
        jpElegirmenu.requestFocusInWindow();
        btnBaes.addActionListener(this);
        btnEconomico.addActionListener(this);
        btnEjecutivo.addActionListener(this);
        btnVolverMenus.addActionListener(this);
        btnVegano.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnVolverMenus) {
            DiaSemana elegirDiaSemana = new DiaSemana();
            elegirDiaSemana.PantallaDia();
            setVisible(false);
        }
        if (e.getSource() == btnEconomico) {
            MenuCompraEconomico economico = new MenuCompraEconomico();
            //economico.PantallaEconomico();
            setVisible(false);
        }
        if (e.getSource() == btnBaes) {
            MenuCompraBaes baes = new MenuCompraBaes();
            //baes.PantallaBaes();
            setVisible(false);
        }
        if (e.getSource() == btnVegano) {
            MenuCompraVegano vegano = new MenuCompraVegano();
           // vegetariano.PantallaVegetariano();//
            setVisible(false);
        }
        if (e.getSource() == btnEjecutivo) {
            MenuCompraEjecutivo ejecutivo = new MenuCompraEjecutivo();
           // ejecutivo.PantallaVegetariano();
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
