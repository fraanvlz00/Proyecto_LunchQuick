package Dominio;

import Launcher.Main;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ServicioPedidos {
	private JsonNode root;
	private ObjectMapper mapper;
	private Scanner scanner;

	public ServicioPedidos() throws IOException {
		mapper = new ObjectMapper();
		ClassLoader classLoader = Main.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("Datos/menu.json");
		if (inputStream == null) {
			throw new IOException("Archivo JSON no encontrado en el classpath");
		}
		root = mapper.readTree(inputStream);
		scanner = new Scanner(System.in);
	}

	public void iniciar(Usuario usuario) {
		System.out.println("Bienvenido, " + usuario.getCorreoElectronico());

		// Validar día
		String dia;
		while (true) {
			System.out.println("Ingrese el día (lunes, martes, miércoles, jueves, viernes): ");
			dia = scanner.nextLine().toLowerCase();
			if (root.has("dias") && root.get("dias").has(dia)) {
				break;
			} else {
				System.out.println("El día no es válido. Por favor, inténtelo de nuevo.");
			}
		}

		JsonNode diaNode = root.get("dias").get(dia);

		// Validar tipo de menú
		String tipoMenu;
		Menu menu = null;
		while (true) {
			System.out.println("Ingrese el tipo de menú (vegetariano, economico, ejecutivo, baes): ");
			tipoMenu = scanner.nextLine().toLowerCase();

			switch (tipoMenu) {
				case "vegetariano":
					menu = Dia.fromJsonNode(diaNode.get("menus")).getVegetariano();
					break;
				case "economico":
					menu = Dia.fromJsonNode(diaNode.get("menus")).getEconomico();
					break;
				case "ejecutivo":
					menu = Dia.fromJsonNode(diaNode.get("menus")).getEjecutivo();
					break;
				case "baes":
					menu = Dia.fromJsonNode(diaNode.get("menus")).getBaes();
					break;
				default:
					System.out.println("Tipo de menú no válido. Por favor, inténtelo de nuevo.");
					continue;
			}
			break;
		}

		// Seleccionar opciones de menú
		String almuerzoComprado = selectOption("bebestibles", menu.getBebestibles())
				+ ", " + selectOption("plato de fondo", menu.getPlatoDeFondo())
				+ ", " + selectOption("ensalada", menu.getEnsalada())
				+ ", " + selectOption("postre", menu.getPostre())
				+ ", " + selectOption("sopa", menu.getSopa())
				+ ", " + selectOption("acompañamiento", menu.getAcompañamiento());

		usuario.setAlmuerzoComprado(almuerzoComprado);
		System.out.println("Cliente: " + usuario.getCorreoElectronico());
		System.out.println("Almuerzo comprado: " + usuario.getAlmuerzoComprado());
		System.out.println("Precio total: $" + menu.getPrecio());
	}

	private String selectOption(String category, String[] options) {
		if (options != null && options.length > 0) {
			while (true) {
				System.out.println("Seleccione " + category + ": ");
				for (int i = 0; i < options.length; i++) {
					System.out.println((i + 1) + ". " + options[i]);
				}
				try {
					int choice = scanner.nextInt();
					scanner.nextLine();  // Consumir nueva línea
					if (choice > 0 && choice <= options.length) {
						System.out.println("Usted ha seleccionado: " + options[choice - 1] + " para " + category);
						return options[choice - 1];
					} else {
						System.out.println("Selección no válida para " + category + ". Por favor, inténtelo de nuevo.");
					}
				} catch (InputMismatchException e) {
					System.out.println("Entrada no válida. Por favor, ingrese un número.");
					scanner.nextLine();  // Consumir la entrada inválida
				}
			}
		}
		return "";
	}
}
