package Dominio;

import Launcher.Main;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ServicioPedidos {
	private JsonNode root;
	private ObjectMapper mapper;
	private Scanner scanner;

	public ServicioPedidos() throws IOException {
		mapper = new ObjectMapper();
		ClassLoader classLoader = Main.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("datos/menu.json");
		if (inputStream == null) {
			throw new IOException("Archivo JSON no encontrado en el classpath");
		}
		root = mapper.readTree(inputStream);
		scanner = new Scanner(System.in);
	}

	public void iniciar(Usuario usuario) {
		System.out.println("Bienvenido, " + usuario.getCorreoElectronico());
		System.out.println("Ingrese el día (lunes, martes, miércoles, jueves, viernes): ");
		String dia = scanner.nextLine().toLowerCase();

		if (root.has("dias") && root.get("dias").has(dia)) {
			JsonNode diaNode = root.get("dias").get(dia);

			System.out.println("Ingrese el tipo de menú (vegetariano, economico, ejecutivo, baes): ");
			String tipoMenu = scanner.nextLine().toLowerCase();

			Menu menu = null;
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
					System.out.println("Tipo de menú no válido.");
					return;
			}

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
		} else {
			System.out.println("El día no es válido.");
		}
	}

	private String selectOption(String category, String[] options) {
		if (options != null && options.length > 0) {
			System.out.println("Seleccione " + category + ": ");
			for (int i = 0; i < options.length; i++) {
				System.out.println((i + 1) + ". " + options[i]);
			}
			int choice = scanner.nextInt();
			scanner.nextLine();  // Consumir nueva línea
			if (choice > 0 && choice <= options.length) {
				System.out.println("Usted ha seleccionado: " + options[choice - 1] + " para " + category);
				return options[choice - 1];
			} else {
				System.out.println("Selección no válida para " + category);
			}
		}
		return "";
	}
}