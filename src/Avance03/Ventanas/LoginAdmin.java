import javax.swing.*;

public class LoginAdmin {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton iniciarSesionButton;
    private JLabel imagenLabel;
    private JPanel mainPanel;
    private Runnable onLoginSuccess;

    public LoginAdmin() {
        iniciarSesionButton.addActionListener(e -> {
            String usuario = textField1.getText();
            String contrasena = new String(passwordField1.getPassword());

            // Aquí iría la lógica para verificar usuario y contraseña

            if (onLoginSuccess != null) {
                onLoginSuccess.run();
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }
}
