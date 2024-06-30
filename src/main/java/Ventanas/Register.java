package Ventanas;

import Dominio.ControladorLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Register extends JFrame implements ActionListener, FocusListener {
    private JPanel jpRegister;
    private JPasswordField pfPassRegi;
    private JTextField txfEmailRegi;
    private JLabel lblRegi;
    private JButton btnVolverRegi;
    private JButton btnConfirmarRegi;

    public void PantallaRegi(){
        setSize(500,500);
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(jpRegister);
        setVisible(true);
        jpRegister.setFocusable(true);
        jpRegister.requestFocusInWindow();
        btnVolverRegi.addActionListener(this);
        btnConfirmarRegi.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnVolverRegi){
          Login login = new Login();
          login.Pantalla();
          setVisible(false);
        }
        if(e.getSource() == btnConfirmarRegi){
            //En este caso, se podría conectar con la base de datos y crear el usuario
            String correo = txfEmailRegi.getText();
            String contrasena = String.valueOf(pfPassRegi.getPassword());
            ControladorLogin cl = new ControladorLogin();
            cl.registrarse(correo,contrasena);
            Boolean result = cl.registrarse(correo,contrasena);
            System.out.println("result: " + result);
            if(result){
                JOptionPane.showMessageDialog(this,"Registro exitoso!");
                Login login = new Login();
                login.Pantalla();
                setVisible(false);
            }
            else{
                JOptionPane.showMessageDialog(this,"Error al registrar usuario!");
            }
        }

    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
