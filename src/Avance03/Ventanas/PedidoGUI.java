/*import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.net.URL;

public class PedidoGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel noPedidosLabel;

    private static final Color BACKGROUND_COLOR = new Color(40, 40, 40);
    private static final Color PANEL_COLOR = new Color(10, 10, 20);
    private static final Color BUTTON_COLOR = new Color(10, 40, 100);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final Dimension PANEL_SIZE = new Dimension(450, 30);
    private static final int STRUT_HEIGHT = 5;

    public PedidoGUI() {
        try {
            initializeComponents();
        } catch (Exception e) {
            mostrarError("Error al inicializar la interfaz gráfica: " + e.getMessage());
        }
    }

    private void initializeComponents() {
        frame = createMainFrame();
        mainPanel = createMainPanel();

        JScrollPane scrollPane = createScrollPane(mainPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.SOUTH);

        JPanel logoPanel = createLogoPanel();
        frame.add(logoPanel, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    public void agregarPedido(String numeroPedido) {
        try {
            if (noPedidosLabel.isVisible()) {
                noPedidosLabel.setVisible(false);
            }
            JPanel contenedorPanel = createContenedorPanel(numeroPedido);
            mainPanel.add(contenedorPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
        } catch (Exception e) {
            mostrarError("Error al agregar pedido: " + e.getMessage());
        }
    }

    private JFrame createMainFrame() {
        JFrame frame = new JFrame("Pedidos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        return frame;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);

        noPedidosLabel = new JLabel("No hay pedidos");
        noPedidosLabel.setForeground(Color.WHITE);
        noPedidosLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(noPedidosLabel);

        return mainPanel;
    }

    private JScrollPane createScrollPane(JPanel mainPanel) {
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    private JPanel createButtonPanel() {
        JButton volverButton = new JButton("Volver");
        volverButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        volverButton.setBackground(new Color(240, 240, 240));
        volverButton.setForeground(Color.BLACK);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(volverButton);
        return buttonPanel;
    }

    private JPanel createLogoPanel() {
        JLabel logoLabel = new JLabel();
        try {
            URL logoURL = PedidoGUI.class.getResource("/ufroImagen.png");
            if (logoURL != null) {
                ImageIcon logoIcon = new ImageIcon(logoURL);
                logoLabel.setIcon(logoIcon);
            } else {
                System.out.println("No se encontró la imagen del logo");
            }
        } catch (Exception e) {
            mostrarError("Error al cargar la imagen del logo: " + e.getMessage());
        }

        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.add(logoLabel, BorderLayout.EAST);
        logoPanel.setBackground(BACKGROUND_COLOR);
        return logoPanel;
    }

    private JPanel createContenedorPanel(String numeroPedido) {
        JPanel contenedorPanel = new JPanel();
        contenedorPanel.setLayout(new BoxLayout(contenedorPanel, BoxLayout.Y_AXIS));
        contenedorPanel.setBackground(BACKGROUND_COLOR);

        JPanel pedidoPanel = createPedidoPanel(numeroPedido, contenedorPanel);
        contenedorPanel.add(pedidoPanel);
        contenedorPanel.add(Box.createVerticalStrut(STRUT_HEIGHT));
        return contenedorPanel;
    }

    private JPanel createPedidoPanel(String numeroPedido, JPanel contenedorPanel) {
        JPanel pedidoPanel = new JPanel(new GridBagLayout());
        pedidoPanel.setPreferredSize(PANEL_SIZE);
        pedidoPanel.setMaximumSize(PANEL_SIZE);
        pedidoPanel.setMinimumSize(PANEL_SIZE);
        pedidoPanel.setBorder(new LineBorder(new Color(60, 60, 60), 1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        JLabel pedidoLabel = createPedidoLabel(numeroPedido);
        JButton entregadaButton = createEntregadaButton(contenedorPanel);

        pedidoPanel.setBackground(PANEL_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        pedidoPanel.add(pedidoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        pedidoPanel.add(entregadaButton, gbc);

        return pedidoPanel;
    }

    private JLabel createPedidoLabel(String numeroPedido) {
        JLabel pedidoLabel = new JLabel("Pedido " + numeroPedido);
        pedidoLabel.setForeground(Color.WHITE);
        pedidoLabel.setPreferredSize(new Dimension(80, 20));
        return pedidoLabel;
    }

    private JButton createEntregadaButton(JPanel contenedorPanel) {
        JButton entregadaButton = new JButton("Entregada");
        entregadaButton.setBackground(BUTTON_COLOR);
        entregadaButton.setForeground(BUTTON_TEXT_COLOR);
        entregadaButton.addActionListener(e -> {
            try {
                mainPanel.remove(contenedorPanel);
                mainPanel.revalidate();
                mainPanel.repaint();
                if (mainPanel.getComponentCount() == 1) {
                    noPedidosLabel.setVisible(true);
                }
            } catch (Exception ex) {
                mostrarError("Error al eliminar pedido: " + ex.getMessage());
            }
        });
        return entregadaButton;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}*/
