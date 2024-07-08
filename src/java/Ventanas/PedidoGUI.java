import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.json.JSONObject;
import org.json.JSONArray;

public class PedidoGUI {
    private JTextField numeroPedidoField;
    private JButton buscarButton;
    private JTextArea detallesPedidoArea;
    private JButton volverButton;
    private JPanel panel1;
    private JButton entregarButton;
    private Runnable onVolver;

    public PedidoGUI() {
        buscarButton.addActionListener(e -> buscarPedido());
        entregarButton.addActionListener(e -> entregarPedido());
        volverButton.addActionListener(e -> {
            if (onVolver != null) {
                onVolver.run();
            }
        });
    }

    private void buscarPedido() {
        String numeroPedido = numeroPedidoField.getText();
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/java/Datos/dia.json")));
            JSONObject jsonObject = new JSONObject(content);

            boolean pedidoEncontrado = false;
            JSONObject dias = jsonObject.getJSONObject("dia");
            for (String dia : dias.keySet()) {
                JSONObject almuerzosComprados = dias.getJSONObject(dia).getJSONObject("almuerzosComprados");
                if (almuerzosComprados.has(numeroPedido)) {
                    JSONObject pedido = almuerzosComprados.getJSONObject(numeroPedido);
                    StringBuilder detalles = new StringBuilder();
                    detalles.append("Detalles del pedido para: ").append(numeroPedido).append("\n");
                    detalles.append("Correo: ").append(pedido.getString("correoElectronico")).append("\n");
                    detalles.append("Pedido: ");
                    JSONArray detallesArray = pedido.getJSONArray("detalles");
                    for (int i = 0; i < detallesArray.length(); i++) {
                        detalles.append(detallesArray.getString(i));
                        if (i < detallesArray.length() - 1) {
                            detalles.append(", ");
                        }
                    }
                    detallesPedidoArea.setText(detalles.toString());
                    pedidoEncontrado = true;
                    break;
                }
            }
            if (!pedidoEncontrado) {
                detallesPedidoArea.setText("Pedido no encontrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            detallesPedidoArea.setText("Error al leer el archivo JSON.");
        }
    }

    private void entregarPedido() {
        String numeroPedido = numeroPedidoField.getText();
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/java/Datos/dia.json")));
            JSONObject jsonObject = new JSONObject(content);

            boolean pedidoEncontrado = false;
            JSONObject dias = jsonObject.getJSONObject("dia");
            for (String dia : dias.keySet()) {
                JSONObject almuerzosComprados = dias.getJSONObject(dia).getJSONObject("almuerzosComprados");
                if (almuerzosComprados.has(numeroPedido)) {
                    almuerzosComprados.remove(numeroPedido);
                    pedidoEncontrado = true;
                    break;
                }
            }
            if (pedidoEncontrado) {
                // Escribir los cambios en el archivo JSON
                Files.write(Paths.get("src/java/Datos/dia.json"), jsonObject.toString(4).getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                detallesPedidoArea.setText("Pedido entregado y eliminado.");
            } else {
                detallesPedidoArea.setText("Pedido no encontrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            detallesPedidoArea.setText("Error al actualizar el archivo JSON.");
        }
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setOnVolver(Runnable onVolver) {
        this.onVolver = onVolver;
    }
}
