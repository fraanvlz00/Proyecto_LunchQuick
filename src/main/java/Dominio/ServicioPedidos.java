package Dominio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ServicioPedidos {
	private JsonNode rootUsuarios;
	private JsonNode rootDias;
	private ObjectMapper mapper;
	private Scanner scanner;
	private Pagos pagos;

	public ServicioPedidos() {
		mapper = new ObjectMapper();
		pagos = new Pagos(); // Inicializar la clase de pagos

		try {
			// Cargar JSON de usuarios
			File jsonFileUsuarios = new File("src/main/java/Datos/usuarios.json");
			if (!jsonFileUsuarios.exists()) {
				throw new IOException("Archivo JSON de usuarios no encontrado en la ruta especificada: " + jsonFileUsuarios.getAbsolutePath());
			}
			rootUsuarios = mapper.readTree(jsonFileUsuarios);

			// Cargar JSON de días
			File jsonFileDias = new File("src/main/java/Datos/dia.json");
			if (!jsonFileDias.exists()) {
				throw new IOException("Archivo JSON de días no encontrado en la ruta especificada: " + jsonFileDias.getAbsolutePath());
			}
			rootDias = mapper.readTree(jsonFileDias);
		} catch (IOException e) {
			System.out.println("Error al cargar los archivos JSON: " + e.getMessage());
		}

		scanner = new Scanner(System.in);
	}

	public static void comprarAlmuerzo(Usuario usuario, Pagos pagos) {
		Scanner scanner = new Scanner(System.in);

		String dia;
		while (true) {
			System.out.println("Seleccione el día para comprar almuerzo (lunes, martes, miércoles, jueves, viernes):");
			dia = scanner.nextLine().toLowerCase();
			if (dia.equals("lunes") || dia.equals("martes") || dia.equals("miércoles") || dia.equals("jueves") || dia.equals("viernes")) {
				break;
			} else {
				System.out.println("Día no válido. Por favor, intente de nuevo.");
			}
		}

		String tipoMenu;
		while (true) {
			System.out.println("Seleccione el tipo de menú (vegetariano, economico, ejecutivo, baes):");
			tipoMenu = scanner.nextLine().toLowerCase();
			if (tipoMenu.equals("vegetariano") || tipoMenu.equals("economico") || tipoMenu.equals("ejecutivo") || tipoMenu.equals("baes")) {
				break;
			} else {
				System.out.println("Tipo de menú no válido. Por favor, intente de nuevo.");
			}
		}

		// Cargar JSON de menús
		JsonNode menusNode = null;
		try {
			File jsonFileMenus = new File("src/main/java/Datos/menu.json");
			if (!jsonFileMenus.exists()) {
				throw new IOException("Archivo JSON de menús no encontrado en la ruta especificada: " + jsonFileMenus.getAbsolutePath());
			}
			JsonNode rootMenus = new ObjectMapper().readTree(jsonFileMenus);
			menusNode = rootMenus.get("dias").get(dia).get("menus");
			if (menusNode == null) {
				System.out.println("El nodo 'menus' no existe para el día " + dia + ".");
				return;
			}
		} catch (IOException e) {
			System.out.println("Error al cargar el archivo de menús: " + e.getMessage());
			return;
		}

		Menu menu = Menu.fromJsonNode(menusNode.get(tipoMenu));

		// Seleccionar opciones de menú
		String almuerzoComprado = "";
		if (menu.getBebestibles() != null && menu.getBebestibles().length > 0) {
			almuerzoComprado += selectOption("bebestibles", menu.getBebestibles()) + ", ";
		}
		if (menu.getPlatoDeFondo() != null && menu.getPlatoDeFondo().length > 0) {
			almuerzoComprado += selectOption("plato de fondo", menu.getPlatoDeFondo()) + ", ";
		}
		if (menu.getEnsalada() != null && menu.getEnsalada().length > 0) {
			almuerzoComprado += selectOption("ensalada", menu.getEnsalada()) + ", ";
		}
		if (menu.getPostre() != null && menu.getPostre().length > 0) {
			almuerzoComprado += selectOption("postre", menu.getPostre()) + ", ";
		}
		if (menu.getSopa() != null && menu.getSopa().length > 0) {
			almuerzoComprado += selectOption("sopa", menu.getSopa()) + ", ";
		}
		if (menu.getAcompañamiento() != null && menu.getAcompañamiento().length > 0) {
			almuerzoComprado += selectOption("acompañamiento", menu.getAcompañamiento()) + ", ";
		}

		// Eliminar la última coma y espacio
		if (almuerzoComprado.endsWith(", ")) {
			almuerzoComprado = almuerzoComprado.substring(0, almuerzoComprado.length() - 2);
		}

		// Mostrar precio antes de solicitar el RUT y el código de pago
		System.out.println("Cliente: " + usuario.getCorreoElectronico());
		System.out.println("Almuerzo en carrito de compras: " + almuerzoComprado);
		System.out.println("Precio total a pagar: $" + menu.getPrecio());

		// Solicitar el RUT y el código de pago
		System.out.println("Ingrese el RUT: ");
		String rut = scanner.nextLine();
		System.out.println("Ingrese el código de pago: ");
		String codigoPago = scanner.nextLine();

		// Verificar pago antes de agregar al JSON
		if (pagos.verificarPago(rut, codigoPago)) {
			System.out.println("Pago verificado.");

			// Actualizar JSON de usuarios y días
			usuario.agregarAlmuerzoComprado(dia, almuerzoComprado);

			try {
				int numeroAsignado = new ServicioPedidos().actualizarJsonDia(dia, usuario);
				new ServicioPedidos().actualizarJsonUsuario(usuario, dia); // Aquí pasamos el día
				System.out.println("El numero de retiro de su almuerzo es: " + numeroAsignado);
			} catch (IOException e) {
				System.out.println("Error al actualizar los archivos JSON: " + e.getMessage());
			}
		} else {
			System.out.println("Pago no verificado o no disponible, no se puede agregar el pedido. (intentelo nuevamente)");
		}
	}

	public static void verAlmuerzosComprados(Usuario usuario) {
		try {
			File jsonFileUsuarios = new File("src/main/java/Datos/usuarios.json");
			if (!jsonFileUsuarios.exists()) {
				throw new IOException("Archivo JSON de usuarios no encontrado en la ruta especificada: " + jsonFileUsuarios.getAbsolutePath());
			}

			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootUsuarios = mapper.readTree(jsonFileUsuarios);
			JsonNode usuariosNode = rootUsuarios.get("usuarios");

			for (JsonNode usuarioNode : usuariosNode) {
				if (usuarioNode.get("correoElectronico").asText().equals(usuario.getCorreoElectronico())) {
					JsonNode almuerzosCompradosNode = usuarioNode.get("almuerzosComprados");
					if (almuerzosCompradosNode != null) {
						Iterator<Map.Entry<String, JsonNode>> fields = almuerzosCompradosNode.fields();
						while (fields.hasNext()) {
							Map.Entry<String, JsonNode> field = fields.next();
							System.out.println("Día: " + field.getKey());
							System.out.println("Menú: " + field.getValue().get("detalles"));
							System.out.println("-----------");
						}
					} else {
						System.out.println("No hay almuerzos comprados.");
					}
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo JSON de usuarios: " + e.getMessage());
		}
	}

	// Métodos existentes...

	private static String selectOption(String category, String[] options) {
		Scanner scanner = new Scanner(System.in);
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

	private void actualizarJsonUsuario(Usuario usuario, String dia) throws IOException {
		File jsonFileUsuarios = new File("src/main/java/Datos/usuarios.json");
		JsonNode usuariosNode = rootUsuarios.get("usuarios");
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

				// Crear un nuevo objeto para el almuerzo con el número de almuerzo
				ObjectNode nuevoAlmuerzo = mapper.createObjectNode();
				String[] detallesAlmuerzo = usuario.getAlmuerzosComprados().get(dia).split(", ");
				ArrayNode detallesArray = nuevoAlmuerzo.putArray("detalles");
				for (String detalle : detallesAlmuerzo) {
					detallesArray.add(detalle);
				}
				nuevoAlmuerzo.put("correoElectronico", usuario.getCorreoElectronico());

				// Añadir el nuevo almuerzo al nodo de almuerzos comprados
				almuerzosCompradosNode.set(String.valueOf(numAlmuerzos + 1), nuevoAlmuerzo);
				break;
			}
		}
		mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFileUsuarios, rootUsuarios);
	}

	private int actualizarJsonDia(String dia, Usuario usuario) throws IOException {
		File jsonFileDias = new File("src/main/java/Datos/dia.json");
		JsonNode diaNode = rootDias.get("dia").get(dia);
		if (diaNode == null) {
			System.out.println("El nodo para el día " + dia + " no existe.");
			return -1;
		}
		ObjectNode almuerzosCompradosNode = (ObjectNode) diaNode.get("almuerzosComprados");
		if (almuerzosCompradosNode == null) {
			almuerzosCompradosNode = mapper.createObjectNode();
			((ObjectNode) diaNode).set("almuerzosComprados", almuerzosCompradosNode);
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

		// Crear un nuevo objeto para el almuerzo con el número de almuerzo
		ObjectNode nuevoAlmuerzo = mapper.createObjectNode();
		String[] detallesAlmuerzo = usuario.getAlmuerzosComprados().get(dia).split(", ");
		ArrayNode detallesArray = nuevoAlmuerzo.putArray("detalles");
		for (String detalle : detallesAlmuerzo) {
			detallesArray.add(detalle);
		}
		nuevoAlmuerzo.put("correoElectronico", usuario.getCorreoElectronico());

		// Añadir el nuevo almuerzo al nodo de almuerzos comprados
		int numeroAsignado = numAlmuerzos + 1;
		almuerzosCompradosNode.set(String.valueOf(numeroAsignado), nuevoAlmuerzo);

		mapper.writerWithDefaultPrettyPrinter().writeValue(jsonFileDias, rootDias);

		return numeroAsignado;
	}
}