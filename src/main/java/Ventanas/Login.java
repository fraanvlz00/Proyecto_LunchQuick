package Ventanas;

import Dominio.ControladorLogin;
import Dominio.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Login extends JFrame implements ActionListener, FocusListener {
    private JPanel JPLogin;
    private JLabel lblLunch;
    private JLabel lblEmail;
    private JLabel lblPass;
    private JLabel lblUfro;
    private JPasswordField pfPass;
    private JTextField txfEmail;
    private JButton btnLogin;
    private JButton btnRegister;

    public Login() {

    }
    public void Pantalla(){
        setSize(500,500);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(JPLogin);
        setVisible(true);
        JPLogin.setFocusable(true);
        JPLogin.requestFocusInWindow();
        btnLogin.addActionListener(this);
        btnRegister.addActionListener(this);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == btnRegister){
           // INICIAR PANTALLA Register
           Register register = new Register();
           register.PantallaRegi();
           setVisible(false);
        }
       if (e.getSource() == btnLogin){
           // VALIDAR LOGIN
           // SI CORRECTO, INICIAR PANTALLA PRINCIPAL
           // SI INCORRECTO, MOSTRAR MENSAJE DE ERROR
           String correo = txfEmail.getText();
           String contraseña = String.valueOf(pfPass.getPassword());
           ControladorLogin cl = new ControladorLogin();
           Usuario result = cl.verificarCredenciales(correo,contraseña);
           if(result != null){
               JOptionPane.showMessageDialog(this,"Login exitoso!");
               Comprar comprar = new Comprar();
               comprar.PantallaCompra();
               setVisible(false);
           }
           else{
               JOptionPane.showMessageDialog(this,"Error al Logear usuario!");
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

