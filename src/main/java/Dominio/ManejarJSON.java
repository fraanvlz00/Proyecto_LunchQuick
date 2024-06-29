package Dominio;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ManejarJSON {

    private static final String FILE_PATH = "src/main/java/Datos/almuerzos.json";

    public static JSONObject leerJSON() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            return new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public static void escribirJSON(JSONObject jsonObject) {
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(jsonObject.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
