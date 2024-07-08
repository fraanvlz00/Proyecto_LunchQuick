package Dominio;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class MenuLogin {
    private final ControladorLogin controladorLogin;
    private final Scanner scanner;
    private final ServicioPedidos servicioPedidos;

    public MenuLogin() {
        this.controladorLogin = new ControladorLogin();
        this.scanner = new Scanner(System.in);
        this.servicioPedidos = new ServicioPedidos();
    }

    public void mostrarMenu() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("Bienvenido!");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = leerOpcion();
            switch (opcion) {
                case 1:
                    controladorLogin.registrarse();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 3:
                    continuar = false;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
    }

    private void iniciarSesion() {
        Usuario usuarioLogueado = controladorLogin.iniciarSesion();
        if (usuarioLogueado != null && usuarioLogueado instanceof Cliente) {
            boolean continuar = true;
            while (continuar) {
                continuar = ejecutarOpcionMenuCliente((Cliente) usuarioLogueado);
            }
        } else if (usuarioLogueado != null && usuarioLogueado instanceof Admin) {
            boolean continuar = true;
            while (continuar) {
                continuar = ejecutarOpcionMenuAdmin((Admin) usuarioLogueado);
            }
        }
    }

    private boolean ejecutarOpcionMenuCliente(Cliente cliente) {
        System.out.println("Menú de Cliente:");
        System.out.println("1. Comprar Almuerzo");
        System.out.println("2. Ver Almuerzos Comprados");
        System.out.println("3. Cerrar Sesión");
        System.out.print("Seleccione una opción: ");

        int opcion = leerOpcion();

        switch (opcion) {
            case 1:
                servicioPedidos.comprarAlmuerzo(cliente, new Pagos());
                return true;
            case 2:
                servicioPedidos.verAlmuerzosComprados(cliente);
                return true;
            case 3:
                System.out.println("Cerrando sesión...");
                return false;
            default:
                System.out.println("Opción inválida. Intente nuevamente.");
                return true;
        }
    }

    private boolean ejecutarOpcionMenuAdmin(Admin admin) {
        System.out.println("Menú de Admin:");
        System.out.println("1. Ver Almuerzos por Día");
        System.out.println("2. Eliminar Almuerzo por Día");
        System.out.println("3. Cerrar Sesión");
        System.out.print("Seleccione una opción: ");

        int opcion = leerOpcion();

        switch (opcion) {
            case 1:
                verAlmuerzosPorDia(admin);
                return true;
            case 2:
                eliminarAlmuerzoPorDia(admin);
                return true;
            case 3:
                System.out.println("Cerrando sesión...");
                return false;
            default:
                System.out.println("Opción inválida. Intente nuevamente.");
                return true;
        }
    }

    private void verAlmuerzosPorDia(Admin admin) {
        String dia = Admin.obtenerDiaValido();
        JSONArray almuerzos = admin.verAlmuerzosPorDia(dia);
        if (almuerzos != null) {
            for (int i = 0; i < almuerzos.length(); i++) {
                JSONObject almuerzo = almuerzos.getJSONObject(i);
                System.out.println("Código de retiro: " + (i + 1));
                System.out.println("Detalles: " + almuerzo.getJSONArray("detalles"));
                System.out.println("Correo Electrónico: " + almuerzo.getString("correoElectronico"));
                System.out.println("-----------");
            }
        } else {
            System.out.println("No se encontraron almuerzos para el día: " + dia);
        }
    }

    private void eliminarAlmuerzoPorDia(Admin admin) {
        String dia = Admin.obtenerDiaValido();
        JSONArray almuerzos = admin.verAlmuerzosPorDia(dia);
        if (almuerzos != null) {
            for (int i = 0; i < almuerzos.length(); i++) {
                JSONObject almuerzo = almuerzos.getJSONObject(i);
                System.out.println((i + 1) + ". Correo Electrónico: " + almuerzo.getString("correoElectronico") + " - Detalles: " + almuerzo.getJSONArray("detalles"));
            }
            String correo = obtenerEntrada("Ingrese el correo electrónico del cliente para eliminar: ");
            admin.eliminarAlmuerzoPorDia(dia, correo);
        } else {
            System.out.println("No se encontraron almuerzos para el día: " + dia);
        }
    }

    private String obtenerEntrada(String mensaje) {
        System.out.println(mensaje);
        return scanner.nextLine();
    }

    private int leerOpcion() {
        while (true) {
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                return opcion;
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                scanner.nextLine(); // Limpiar el buffer
            }
        }
    }
}
