package org.example;
import java.util.*;

public class MenuDelDia {
    private final Map<Integer, String> menu;
    private final Map<String, Double> precios;
    private final List<String> carrito;

    public MenuDelDia() {
        this.menu = Map.of(
                1, "Menú BAES básico",
                2, "Menú BAES mejorado",
                3, "Menú vegano"
        );

        this.precios = Map.of(
                "Menú BAES básico", 1600.0,
                "Menú BAES mejorado", 1800.0,
                "Menú vegano", 2800.0
        );

        this.carrito = new ArrayList<>();
    }

    public void mostrarMenuDia() {
        for (Map.Entry<Integer, String> entry : menu.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue() + " - Valor: $" + precios.get(entry.getValue()));
        }
    }

    public void agregarAlimentoCarrito(int opcion) {
        String nombreAlimento = obtenerNombreAlimento(opcion);
        if (nombreAlimento != null) {
            carrito.add(nombreAlimento);
            System.out.println(nombreAlimento + " agregado al carrito.");
        } else {
            System.out.println("La opción seleccionada no está en el menú.");
        }
    }

    public void eliminarAlimentoCarrito(int opcion) {
        if (carrito.isEmpty()) {
            System.out.println("El carrito está vacío. No hay nada que eliminar.");
            return;
        }

        if (opcion >= 1 && opcion <= carrito.size()) {
            String nombreAlimentoEliminado = carrito.remove(opcion - 1);
            System.out.println(nombreAlimentoEliminado + " eliminado del carrito.");
        } else {
            System.out.println("La opción seleccionada no está en el carrito.");
        }
    }

    public void comprarCarrito() {
        if (carrito.isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }

        mostrarAlimentoCarritoConCantidad();
        if (confirmarCompra()) {
            realizarCompra();
        } else {
            System.out.println("Compra cancelada.");
        }
    }

    public String obtenerNombreAlimento(int opcion) {
        return menu.getOrDefault(opcion, null);
    }

    public void mostrarAlimentoCarritoConCantidad() {
        Map<String, Integer> cantidadAlimentos = contarCantidadAlimentos();
        int index = 1;

        for (Map.Entry<String, Integer> entry : cantidadAlimentos.entrySet()) {
            String nombreAlimento = entry.getKey();
            int cantidad = entry.getValue();
            double precioAlimento = precios.get(nombreAlimento);

            System.out.println(index + ". " + nombreAlimento + " - Cantidad: " + cantidad + " - Valor: $" + precioAlimento * cantidad);
            index++;
        }
    }

    public Map<String, Integer> contarCantidadAlimentos() {
        Map<String, Integer> cantidadAlimentos = new HashMap<>();

        for (String nombreAlimento : carrito) {
            cantidadAlimentos.put(nombreAlimento, cantidadAlimentos.getOrDefault(nombreAlimento, 0) + 1);
        }

        return cantidadAlimentos;
    }

    public boolean confirmarCompra() {
        System.out.println("Estás seguro de comprar estos alimentos del carrito? (S/N)");
        Scanner scanner = new Scanner(System.in);
        String respuesta = scanner.nextLine().toUpperCase();
        return respuesta.equals("S");
    }

    public void realizarCompra() {
        double total = 0.0;
        for (String nombreAlimento : carrito) {
            double precioAlimento = precios.get(nombreAlimento);
            total += precioAlimento;
            System.out.println("Compra realizada: " + nombreAlimento + " por $" + precioAlimento);
        }
        System.out.println("Total de la compra: $" + total);
        carrito.clear();
    }

    public void iniciarMenuInteractivo() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarOpcionesMenu();
            opcion = obtenerOpcion(scanner);

            switch (opcion) {
                case 1 -> mostrarMenuDia();
                case 2 -> agregarAlimento(scanner);
                case 3 -> eliminarAlimento(scanner);
                case 4 -> comprarCarrito();
                case 5 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 5);
        scanner.close();
    }

    public void mostrarOpcionesMenu() {
        System.out.println("\n=== MENÚ ===");
        System.out.println("1. Mostrar menú del día");
        System.out.println("2. Agregar alimento al carrito");
        System.out.println("3. Eliminar alimento del carrito");
        System.out.println("4. Comprar del carrito");
        System.out.println("5. Salir");
        System.out.print("Ingrese su opción: ");
    }

    public int obtenerOpcion(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: Por favor, ingrese un número válido.");
            scanner.next(); // Limpiar el buffer del scanner
            return -1; // Asignar un valor inválido para repetir el bucle
        }
    }

    public void agregarAlimento(Scanner scanner) {
        System.out.print("Ingrese el número del alimento a agregar al carrito: ");
        int opcionAgregar = obtenerOpcion(scanner);
        agregarAlimentoCarrito(opcionAgregar);
    }

    public void eliminarAlimento(Scanner scanner) {
        if (!carrito.isEmpty()) {
            mostrarAlimentoCarritoConCantidad();
            System.out.print("Ingrese el número del alimento a eliminar del carrito: ");
            try {
                int opcionEliminar = scanner.nextInt();
                eliminarAlimentoCarrito(opcionEliminar);
            } catch (InputMismatchException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
                scanner.next(); // Limpiar el buffer del scanner
            }
        } else {
            System.out.println("El carrito está vacío. No hay nada que eliminar.");
        }
    }

    public static void main(String[] args) {
        MenuDelDia menu = new MenuDelDia();
        menu.iniciarMenuInteractivo();
    }
}