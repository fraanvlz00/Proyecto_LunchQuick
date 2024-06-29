package Dominio;

import java.util.Scanner;

public class MenuLogin {
    private ControladorLogin controladorLogin;
    private Scanner scanner;
    private ServicioPedidos servicioPedidos;

    public MenuLogin() {
        this.controladorLogin = new ControladorLogin();
        this.scanner = new Scanner(System.in);
        this.servicioPedidos = new ServicioPedidos();
    }

    public void mostrarMenu() {
        boolean continuar = true;
        while (continuar) {
            int opcion = mostrarOpcionesMenuPrincipal();
            continuar = ejecutarOpcionMenuPrincipal(opcion);
        }
    }

    private int mostrarOpcionesMenuPrincipal() {
        System.out.println("Bienvenido!");
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
        return scanner.nextInt();
    }

    private boolean ejecutarOpcionMenuPrincipal(int opcion) {
        scanner.nextLine(); // Consumir nueva línea
        switch (opcion) {
            case 1:
                controladorLogin.registrarse();
                return true;
            case 2:
                iniciarSesion();
                return true;
            case 3:
                System.out.println("¡Hasta luego!");
                return false;
            default:
                System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                return true;
        }
    }

    private void iniciarSesion() {
        Usuario usuarioLogueado = controladorLogin.iniciarSesion();
        if (usuarioLogueado != null) {
            mostrarMenuUsuario(usuarioLogueado);
        }
    }

    private void mostrarMenuUsuario(Usuario usuario) {
        boolean continuar = true;
        while (continuar) {
            if (usuario.getTipo() == TipoUsuario.ADMIN) {
                continuar = mostrarMenuAdmin((Admin) usuario);
            } else {
                continuar = mostrarMenuCliente((Cliente) usuario);
            }
        }
    }

    private boolean mostrarMenuAdmin(Admin admin) {
        int opcion = mostrarOpcionesMenuAdmin();
        return ejecutarOpcionMenuAdmin(opcion, admin);
    }

    private int mostrarOpcionesMenuAdmin() {
        System.out.println("Menú de Admin:");
        System.out.println("1. Ver Almuerzos por Día");
        System.out.println("2. Eliminar Almuerzo por Día");
        System.out.println("3. Cerrar Sesión");
        System.out.print("Seleccione una opción: ");
        return scanner.nextInt();
    }

    private boolean ejecutarOpcionMenuAdmin(int opcion, Admin admin) {
        scanner.nextLine(); // Consumir nueva línea
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
        String dia = obtenerEntrada("Ingrese el día: ");
        System.out.println(admin.verAlmuerzosPorDia(dia).toString(2));
    }

    private void eliminarAlmuerzoPorDia(Admin admin) {
        String dia = obtenerEntrada("Ingrese el día: ");
        System.out.println(admin.verAlmuerzosPorDia(dia).toString(2));
        String correoCliente = obtenerEntrada("Ingrese el correo del cliente: ");
        admin.eliminarAlmuerzoPorDia(dia, correoCliente);
    }

    private boolean mostrarMenuCliente(Cliente cliente) {
        int opcion = mostrarOpcionesMenuCliente();
        return ejecutarOpcionMenuCliente(opcion, cliente);
    }

    private int mostrarOpcionesMenuCliente() {
        System.out.println("Menú de Cliente:");
        System.out.println("1. Comprar Almuerzo");
        System.out.println("2. Ver Almuerzos Comprados");
        System.out.println("3. Cerrar Sesión");
        System.out.print("Seleccione una opción: ");
        return scanner.nextInt();
    }

    private boolean ejecutarOpcionMenuCliente(int opcion, Cliente cliente) {
        scanner.nextLine(); // Consumir nueva línea
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

    private String obtenerEntrada(String mensaje) {
        System.out.println(mensaje);
        return scanner.nextLine();
    }
}
