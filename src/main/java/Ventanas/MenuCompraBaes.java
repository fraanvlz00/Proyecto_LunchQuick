package Ventanas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MenuCompraBaes extends JFrame implements ActionListener, FocusListener {
    private JPanel panel1;
    private JComboBox cbxEnsalada;
    private JComboBox cbxBBstible;
    private JComboBox cbxPoste;
    private JComboBox cbPdfondo;
    private JButton btnPagar;
    private JPanel jpMenuBaes;
    private JButton btnVolverMcompra;


    public void PantallaMenBaesu() {
        setSize(500, 500);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(jpMenuBaes);
        setVisible(true);
        jpMenuBaes.setFocusable(true);
        jpMenuBaes.requestFocusInWindow();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnVolverMcompra) {
            DiaSemana elegirDiaSemana = new DiaSemana();
            elegirDiaSemana.PantallaDia();
            setVisible(false);
        }
        if (e.getSource() == btnPagar) {

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
