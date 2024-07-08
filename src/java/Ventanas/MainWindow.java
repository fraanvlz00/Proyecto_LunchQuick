import javax.swing.*;

public class MainWindow {
    private JFrame frame;

    public MainWindow() {
        frame = new JFrame();
        showLogin();
    }

    private void showLogin() {
        LoginAdmin loginAdmin = new LoginAdmin();
        loginAdmin.setOnLoginSuccess(this::showMenuPrincipal);

        frame.setContentPane(loginAdmin.getMainPanel());
        configureFrame("LoginAdmin");
    }

    private void showMenuPrincipal() {
        MenuPrincipalAdmin menuPrincipalAdmin = new MenuPrincipalAdmin();
        menuPrincipalAdmin.setOnPedidosPendientes(this::showPedidoGUI); // Actualizamos esto para abrir PedidoGUI
        menuPrincipalAdmin.setOnSalir(this::showLogin);

        frame.setContentPane(menuPrincipalAdmin.getPanel1());
        configureFrame("MenuPrincipalAdmin");
    }

    private void showPedidoGUI() {
        PedidoGUI pedidoGUI = new PedidoGUI();
        pedidoGUI.setOnVolver(this::showMenuPrincipal); // Método para el botón volver

        frame.setContentPane(pedidoGUI.getPanel1());
        configureFrame("PedidoGUI");
    }

    private void configureFrame(String title) {
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null); // Centrar en la pantalla
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
