package Dominio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ServicioAlmuerzo {
    private JsonNode root;
    private ObjectMapper mapper;
    private Scanner scanner;
    private ServicioUsuarios servicioUsuarios;

    public ServicioAlmuerzo(ServicioUsuarios servicioUsuarios) throws IOException {
        this.servicioUsuarios = servicioUsuarios;
        mapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Datos/menu.json");
        if (inputStream == null) {
            throw new IOException("Archivo JSON no encontrado en el classpath");
        }
        root = mapper.readTree(inputStream);
        scanner = new Scanner(System.in);
    }

    public void iniciar(Usuario usuario) throws IOException {
        boolean continuar = true;
        while (continuar) {
            System.out.println("Opciones: ");
            System.out.println("1. Ver Menú");
            System.out.println("2. Realizar Pedido");
            System.out.println("3. Ver Pedidos");
            System.out.println("4. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (opcion) {
                case 1:
                    verMenu();
                    break;
                case 2:
                    realizarPedido(usuario);
                    break;
                case 3:
                    servicioUsuarios.verPedidos(usuario);
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }

    private void verMenu() {
        System.out.print("Ingrese el día de la semana para ver el menú: ");
        String dia = scanner.nextLine();
        JsonNode menuDelDia = root.path("dias").path(dia.toLowerCase());
        if (!menuDelDia.isMissingNode()) {
            System.out.println("Menú del día " + dia + ":");
            Iterator<Map.Entry<String, JsonNode>> fields = menuDelDia.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                System.out.println(field.getKey() + ": " + field.getValue());
            }
        } else {
            System.out.println("Día no válido.");
        }
    }

    private void realizarPedido(Usuario usuario) {
        System.out.print("Ingrese el día de la semana para ver el menú: ");
        String dia = scanner.nextLine();
        JsonNode menuDelDia = root.path("dias").path(dia.toLowerCase());
        if (!menuDelDia.isMissingNode()) {
            System.out.println("Menú del día " + dia + ":");
            Iterator<Map.Entry<String, JsonNode>> fields = menuDelDia.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                System.out.println(field.getKey() + ": " + field.getValue());
            }

            System.out.print("Seleccione el tipo de menú: ");
            String tipoMenu = scanner.nextLine();
            if (menuDelDia.has(tipoMenu)) {
                usuario.addAlmuerzoComprado(tipoMenu);
                System.out.println("Pedido registrado exitosamente.");
            } else {
                System.out.println("Tipo de menú no válido.");
            }
        } else {
            System.out.println("Día no válido.");
        }
    }
}
