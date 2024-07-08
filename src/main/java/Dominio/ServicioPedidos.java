package Dominio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class ServicioPedidos {
	private JsonNode rootDias;
	private JsonNode rootMenus;
	private final ObjectMapper mapper;
	private final Scanner scanner;
	private String detallesAlmuerzoComprado;
	private int numeroRetiro;

	public ServicioPedidos() {
		mapper = new ObjectMapper();
		scanner = new Scanner(System.in);
		cargarJson();
	}

	private void cargarJson() {
		try {
			rootDias = cargarArchivoJson("src/main/java/Datos/dia.json");
			rootMenus = cargarArchivoJson("src/main/java/Datos/menu.json");
		} catch (IOException e) {
			System.out.println("Error al cargar los archivos JSON: " + e.getMessage());
		}
	}

	private JsonNode cargarArchivoJson(String rutaArchivo) throws IOException {
		File archivo = new File(rutaArchivo);
		if (!archivo.exists()) {
			throw new IOException("Archivo JSON no encontrado en la ruta especificada: " + archivo.getAbsolutePath());
		}
		return mapper.readTree(archivo);
	}
	public String mostrarHistorial(Usuario usuario) {
		StringBuilder sb = new StringBuilder();
		try (FileReader reader = new FileReader("src/main/java/Datos/dia.json")) {
			JSONObject jsonObject = new JSONObject(new JSONTokener(reader));
			JSONObject dia = jsonObject.getJSONObject("dia");

			for (String diaKey : dia.keySet()) {
				JSONObject diaObj = dia.getJSONObject(diaKey);
				JSONObject almuerzosComprados = diaObj.getJSONObject("almuerzosComprados");

				for (String almuerzoKey : almuerzosComprados.keySet()) {
					JSONObject almuerzo = almuerzosComprados.getJSONObject(almuerzoKey);
					String correo = almuerzo.getString("correoElectronico");

					if (correo.equals(usuario.getCorreoElectronico())) {
						JSONArray detalles = almuerzo.getJSONArray("detalles");

						sb.append("Día: ").append(diaKey).append("\n");
						sb.append("Almuerzo ").append(almuerzoKey).append(":\n");
						sb.append("Correo: ").append(correo).append("\n");
						sb.append("Detalles:\n");
						for (int i = 0; i < detalles.length(); i++) {
							sb.append(((JSONArray) detalles).getString(i)).append("\n");
						}
						sb.append("\n");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public void comprarAlmuerzo(Cliente cliente, Pagos pagos) {
		String dia = seleccionarDia();
		String tipoMenu = seleccionarTipoMenu();

		Menu menu = obtenerMenu(dia, tipoMenu);
		if (menu == null) return;

		detallesAlmuerzoComprado = seleccionarDetallesMenu(menu);

		mostrarResumenCompra(cliente, menu, detallesAlmuerzoComprado);
		procesarCompra(cliente, pagos, dia, detallesAlmuerzoComprado, menu.getPrecio());
	}

	private String seleccionarDia() {
		String dia;
		while (true) {
			System.out.println("Seleccione el día para comprar almuerzo (lunes, martes, miércoles, jueves, viernes):");
			dia = scanner.nextLine().toLowerCase();
			if (esDiaValido(dia)) break;
			System.out.println("Día no válido. Por favor, intente de nuevo.");
		}
		return dia;
	}

	private boolean esDiaValido(String dia) {
		return dia.equals("lunes") || dia.equals("martes") || dia.equals("miércoles") || dia.equals("jueves") || dia.equals("viernes");
	}

	private String seleccionarTipoMenu() {
		String tipoMenu;
		while (true) {
			System.out.println("Seleccione el tipo de menú (vegetariano, economico, ejecutivo, baes):");
			tipoMenu = scanner.nextLine().toLowerCase();
			if (esTipoMenuValido(tipoMenu)) break;
			System.out.println("Tipo de menú no válido. Por favor, intente de nuevo.");
		}
		return tipoMenu;
	}

	private boolean esTipoMenuValido(String tipoMenu) {
		return tipoMenu.equals("vegetariano") || tipoMenu.equals("economico") || tipoMenu.equals("ejecutivo") || tipoMenu.equals("baes");
	}

	public Menu obtenerMenu(String dia, String tipoMenu) {
		JsonNode menusNode = cargarMenusDelDia(dia);
		if (menusNode == null) return null;
		return Menu.fromJsonNode(menusNode.get(tipoMenu));
	}

	private JsonNode cargarMenusDelDia(String dia) {
		try {
			return rootMenus.get("dias").get(dia).get("menus");
		} catch (Exception e) {
			System.out.println("Error al cargar los menús para el día: " + e.getMessage());
			return null;
		}
	}

	private String seleccionarDetallesMenu(Menu menu) {
		String almuerzoComprado = "";
		almuerzoComprado += seleccionarOpcionDeMenu("bebestibles", menu.getBebestibles());
		almuerzoComprado += seleccionarOpcionDeMenu("plato de fondo", menu.getPlatoDeFondo());
		almuerzoComprado += seleccionarOpcionDeMenu("ensalada", menu.getEnsalada());
		almuerzoComprado += seleccionarOpcionDeMenu("postre", menu.getPostre());
		almuerzoComprado += seleccionarOpcionDeMenu("sopa", menu.getSopa());
		almuerzoComprado += seleccionarOpcionDeMenu("acompañamiento", menu.getAcompañamiento());

		return almuerzoComprado.endsWith(", ") ? almuerzoComprado.substring(0, almuerzoComprado.length() - 2) : almuerzoComprado;
	}

	private String seleccionarOpcionDeMenu(String categoria, String[] opciones) {
		if (opciones != null && opciones.length > 0) {
			return seleccionarOpcion(categoria, opciones) + ", ";
		}
		return "";
	}

	private String seleccionarOpcion(String categoria, String[] opciones) {
		while (true) {
			mostrarOpciones(categoria, opciones);
			int choice = obtenerOpcion();
			if (esOpcionValida(choice, opciones.length)) {
				System.out.println("Usted ha seleccionado: " + opciones[choice - 1] + " para " + categoria);
				return opciones[choice - 1];
			} else {
				System.out.println("Selección no válida para " + categoria + ". Por favor, inténtelo de nuevo.");
			}
		}
	}

	private void mostrarOpciones(String categoria, String[] opciones) {
		System.out.println("Seleccione " + categoria + ": ");
		for (int i = 0; i < opciones.length; i++) {
			System.out.println((i + 1) + ". " + opciones[i]);
		}
	}

	private int obtenerOpcion() {
		while (true) {
			try {
				int choice = scanner.nextInt();
				scanner.nextLine();
				return choice;
			} catch (InputMismatchException e) {
				System.out.println("Entrada no válida. Por favor, ingrese un número.");
				scanner.nextLine();
			}
		}
	}

	private boolean esOpcionValida(int choice, int numOpciones) {
		return choice > 0 && choice <= numOpciones;
	}

	private void mostrarResumenCompra(Cliente cliente, Menu menu, String almuerzoComprado) {
		System.out.println("Cliente: " + cliente.getCorreoElectronico());
		System.out.println("Almuerzo en carrito de compras: " + almuerzoComprado);
		System.out.println("Precio total a pagar: $" + menu.getPrecio());
	}

	private void procesarCompra(Cliente cliente, Pagos pagos, String dia, String almuerzoComprado, int precio) {
		String rut = obtenerEntrada("Ingrese el RUT: ");
		String codigoPago = obtenerEntrada("Ingrese el código de pago: ");

		if (pagos.verificarPago(rut, codigoPago)) {
			System.out.println("Pago verificado.");
			try {
				numeroRetiro = actualizarJsonDia(dia, cliente, almuerzoComprado);
				System.out.println("El número de retiro de su almuerzo es: " + numeroRetiro);
			} catch (IOException e) {
				System.out.println("Error al actualizar los archivos JSON: " + e.getMessage());
			}
		} else {
			System.out.println("Pago no verificado o no disponible, no se puede agregar el pedido. (inténtelo nuevamente)");
		}
	}

	private String obtenerEntrada(String mensaje) {
		System.out.println(mensaje);
		return scanner.nextLine();
	}

	private int actualizarJsonDia(String dia, Cliente cliente, String almuerzoComprado) throws IOException {
		File jsonFileDias = new File("src/main/java/Datos/dia.json");
		JsonNode rootDias = mapper.readTree(jsonFileDias);
		JsonNode diaNode = rootDias.get("dia").get(dia);
		if (diaNode == null) {
			System.out.println("El nodo para el día " + dia + " no existe.");
			return -1;
		}
		ObjectNode almuerzosCompradosNode = obtenerNodoAlmuerzosCompradosDia(diaNode);
		return agregarAlmuerzoDia(almuerzosCompradosNode, cliente, almuerzoComprado, jsonFileDias, rootDias);
	}

	private ObjectNode obtenerNodoAlmuerzosCompradosDia(JsonNode diaNode) {
		ObjectNode almuerzosCompradosNode = (ObjectNode) diaNode.get("almuerzosComprados");
		if (almuerzosCompradosNode == null) {
			almuerzosCompradosNode = mapper.createObjectNode();
			((ObjectNode) diaNode).set("almuerzosComprados", almuerzosCompradosNode);
		}
		return almuerzosCompradosNode;
	}

	private int agregarAlmuerzoDia(ObjectNode almuerzosCompradosNode, Cliente cliente, String almuerzoComprado, File jsonFileDias, JsonNode rootDias) throws IOException {
		int numAlmuerzos = obtenerNumeroAlmuerzos(almuerzosCompradosNode);
		ObjectNode nuevoAlmuerzo = mapper.createObjectNode();
		ArrayNode detallesArray = nuevoAlmuerzo.putArray("detalles");
		for (String detalle : almuerzoComprado.split(", ")) {
			detallesArray.add(detalle);
		}
		nuevoAlmuerzo.put("correoElectronico", cliente.getCorreoElectronico());
		int numeroAsignado = numAlmuerzos + 1;
		almuerzosCompradosNode.set(String.valueOf(numeroAsignado), nuevoAlmuerzo);
		guardarJson(jsonFileDias, rootDias);
		return numeroAsignado;
	}

	private int obtenerNumeroAlmuerzos(ObjectNode almuerzosCompradosNode) {
		int numAlmuerzos = 0;
		Iterator<Map.Entry<String, JsonNode>> fields = almuerzosCompradosNode.fields();
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> field = fields.next();
			int currentNum = Integer.parseInt(field.getKey());
			if (currentNum > numAlmuerzos) {
				numAlmuerzos = currentNum;
			}
		}
		return numAlmuerzos;
	}

	private void guardarJson(File archivo, JsonNode root) throws IOException {
		mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, root);
	}

	public String verAlmuerzosComprados(Cliente cliente) {
		StringBuilder historial = new StringBuilder();
		try {
			// Recargar el archivo JSON para asegurar que los datos más recientes se lean
			rootDias = cargarArchivoJson("src/main/java/Datos/dia.json");

			JsonNode diasNode = rootDias.get("dia");
			for (Iterator<String> it = diasNode.fieldNames(); it.hasNext(); ) {
				String dia = it.next();
				JsonNode almuerzosNode = diasNode.get(dia).get("almuerzosComprados");
				if (almuerzosNode != null) {
					for (Iterator<Map.Entry<String, JsonNode>> itAlmuerzos = almuerzosNode.fields(); itAlmuerzos.hasNext(); ) {
						Map.Entry<String, JsonNode> almuerzoEntry = itAlmuerzos.next();
						JsonNode almuerzoNode = almuerzoEntry.getValue();
						if (almuerzoNode.get("correoElectronico").asText().equals(cliente.getCorreoElectronico())) {
							historial.append("Día: ").append(dia).append("\n");
							historial.append("Código de retiro: ").append(almuerzoEntry.getKey()).append("\n");
							historial.append("Detalles: ").append(almuerzoNode.get("detalles")).append("\n");
							historial.append("-----------\n");
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error al leer los almuerzos comprados: " + e.getMessage());
		}
		return historial.toString();
	}

	public String getDetallesAlmuerzoComprado() {
		return detallesAlmuerzoComprado;
	}

	public int getNumeroRetiro() {
		return numeroRetiro;
	}
}

