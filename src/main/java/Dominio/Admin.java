package Dominio;

import org.json.JSONArray;
import org.json.JSONObject;

public class Admin extends Usuario {

    public Admin(String correoElectronico, String contraseña) {
        super(correoElectronico, contraseña, TipoUsuario.ADMIN);
    }

    public JSONArray verAlmuerzosPorDia(String dia) {
        JSONObject jsonObject = ManejarJSON.leerJSON();
        return jsonObject.optJSONArray(dia);
    }

    public void eliminarAlmuerzoPorDia(String dia, String correoCliente) {
        JSONObject jsonObject = ManejarJSON.leerJSON();
        JSONArray almuerzosDia = jsonObject.optJSONArray(dia);

        if (almuerzosDia != null) {
            for (int i = 0; i < almuerzosDia.length(); i++) {
                JSONObject pedido = almuerzosDia.getJSONObject(i);
                if (pedido.getString("correoElectronico").equals(correoCliente)) {
                    almuerzosDia.remove(i);
                    break;
                }
            }
            jsonObject.put(dia, almuerzosDia);
            ManejarJSON.escribirJSON(jsonObject);
        }
    }
}

