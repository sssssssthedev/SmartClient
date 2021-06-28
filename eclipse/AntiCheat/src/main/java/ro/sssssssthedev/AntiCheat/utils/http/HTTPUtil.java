package ro.sssssssthedev.AntiCheat.utils.http;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;

public class HTTPUtil {
    public static String getResponse(String URL) {
        String out = null;
        try {
            URLConnection connection = new URL(URL).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();
            BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            String value = sb.toString();
            out = value;
        } catch (IOException ex) {
            out = "[ERROR] - " + ex.getMessage();
        }
        return out;
    }

    public static String getResponse(String URL, HashMap<String, String> header) {
        String out = null;
        try {
            URLConnection connection = new URL(URL).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            header.forEach(connection::setRequestProperty);
            connection.connect();
            BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            String value = sb.toString();
            out = value;
        } catch (IOException ex) {
            out = "[ERROR] - " + ex.getMessage();
        }
        return out;
    }

    public static String getUUID(String mcName) {
        String sURL = "https://minecraft-techworld.com/admin/api/uuid?action=uuid&username=" + mcName;
        URL url;
        try {
            url = new URL(sURL);
            URLConnection request = url.openConnection();
            request.setRequestProperty("User-Agent", "Mozilla/5.0");
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject JSONObject = root.getAsJsonObject();
            return JSONObject.get("output").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
