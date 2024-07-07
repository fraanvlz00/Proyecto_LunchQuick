package Dominio;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Scanner;

public class Admin extends Usuario {

    public Admin(String correoElectronico, String contraseña) {
        super(correoElectronico, contraseña, TipoUsuario.ADMIN);
    }

    public JSONArray verAlmuerzosPorDia(String dia) {
        JSONObject jsonObject = ManejarJSON.leerJSON();
        JSONObject diaObject = jsonObject.optJSONObject("dia").optJSONObject(dia);
        if (diaObject == null || !diaObject.has("almuerzosComprados")) {
            System.out.println("No se encontraron almuerzos para el día: " + dia);
            return null;
        }
        return diaObject.getJSONObject("almuerzosComprados").toJSONArray(diaObject.getJSONObject("almuerzosComprados").names());
    }

    public void eliminarAlmuerzoPorCodigo(String dia, String codigoRetiro) {
        JSONObject jsonObject = ManejarJSON.leerJSON();
        JSONObject diaObject = jsonObject.optJSONObject("dia").optJSONObject(dia);
        if (diaObject == null || !diaObject.has("almuerzosComprados")) {
            System.out.println("No se encontraron almuerzos para el día: " + dia);
            return;
        }

        JSONObject almuerzosDia = diaObject.getJSONObject("almuerzosComprados");
        if (almuerzosDia.has(codigoRetiro)) {
            almuerzosDia.remove(codigoRetiro);
            diaObject.put("almuerzosComprados", almuerzosDia);
            ManejarJSON.escribirJSON(jsonObject);
            System.out.println("Almuerzo eliminado.");
        } else {
            System.out.println("No se encontró un almuerzo con el código de retiro: " + codigoRetiro);
        }
    }

    public static String obtenerDiaValido() {
        Scanner scanner = new Scanner(System.in);
        String dia;
        while (true) {
            System.out.println("Ingrese el día (opciones válidas: lunes, martes, miércoles, jueves, viernes):");
            dia = scanner.nextLine().toLowerCase();
            if (esDiaValido(dia)) {
                break;
            } else {
                System.out.println("Día no válido. Por favor, intente de nuevo.");
            }
        }
        return dia;
    }

    private static boolean esDiaValido(String dia) {
        return dia.equals("lunes") || dia.equals("martes") || dia.equals("miércoles") || dia.equals("jueves") || dia.equals("viernes");
    }
}
