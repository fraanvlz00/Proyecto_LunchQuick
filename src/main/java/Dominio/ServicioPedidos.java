package Dominio;

import Launcher.Main;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class ServicioPedidos {
	private JsonNode root;
	private ObjectMapper mapper;
	private Scanner scanner;

	public ServicioPedidos() throws IOException {
		mapper = new ObjectMapper();
		File jsonFile = new File("src/main/java/Datos/menu.json");
		if (!jsonFile.exists()) {
			throw new IOException("Archivo JSON no encontrado en la ruta especificada: " + jsonFile.getAbsolutePath());
		}
		root = mapper.readTree(jsonFile);
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

		// Actualizar JSON
		try {
			actualizarJson(usuario);
		} catch (IOException e) {
			System.out.println("Error al actualizar el archivo JSON: " + e.getMessage());
		}
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

	private void actualizarJson(Usuario usuario) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		File jsonFile = new File("src/main/java/Datos/usuarios.json");
		if (!jsonFile.exists()) {
			throw new IOException("Archivo JSON no encontrado en la ruta especificada: " + jsonFile.getAbsolutePath());
		}
		JsonNode root = mapper.readTree(jsonFile);

		JsonNode usuariosNode = root.get("usuarios");
		for (JsonNode usuarioNode : usuariosNode) {
			if (usuarioNode.get("correoElectronico").asText().equals(usuario.getCorreoElectronico())) {
				ObjectNode almuerzosCompradosNode = (ObjectNode) usuarioNode.get("almuerzosComprados");
				if (almuerzosCompradosNode == null) {
					almuerzosCompradosNode = mapper.createObjectNode();
					((ObjectNode) usuarioNode).set("almuerzosComprados", almuerzosCompradosNode);
				}

				// Contar el número de almuerzos existentes
				int numAlmuerzos = 0;
				Iterator<Map.Entry<String, JsonNode>> fields = almuerzosCompradosNode.fields();
				while (fields.hasNext()) {
					Map.Entry<String, JsonNode> field = fields.next();
					int currentNum = Integer.parseInt(field.getKey());
					if (currentNum > numAlmuerzos) {
						numAlmuerzos = currentNum;
					}
				}

				// Crear un nuevo array para el almuerzo con un número de almuerzo
				ArrayNode nuevoAlmuerzo = mapper.createArrayNode();
				String[] detallesAlmuerzo = usuario.getAlmuerzoComprado().split(", ");
				for (String detalle : detallesAlmuerzo) {
					nuevoAlmuerzo.add(detalle);
				}

				// Añadir el nuevo almuerzo al nodo de almuerzos comprados
				almuerzosCompradosNode.set(String.valueOf(numAlmuerzos + 1), nuevoAlmuerzo);
				break;
			}
		}
		mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, root);
	}
}

