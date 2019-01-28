package com.acerbisgianluca.ganttproject.utilities;

import com.acerbisgianluca.ganttproject.exceptions.CannotCheckVersionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Classe statica che viene utilizzata unicamente per la connessione al server
 * che contiene l'indice delle versioni.
 *
 * @author Gianluca
 */
public final class VersionManager {

    /**
     * Costruttore privato in modo da non essere istanziato.
     */
    private VersionManager() {
    }

    /**
     *
     * @param current La versione attuale.
     * @return Vero se c'è una nuova versione, falso altrimenti.
     * @throws MalformedURLException Viene lanciata se l'url non è scritto
     * correttamente.
     * @throws CannotCheckVersionException Viene lanciata se il server risulta
     * irraggiungibile.
     */
    public static boolean checkVersion(String current) throws MalformedURLException, CannotCheckVersionException {
        try {
            URLConnection connection = new URL("https://www.acerbisgianluca.com/gantt-project-index.json").openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

            BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }

            JSONObject obj = new JSONObject(sb.toString());
            JSONArray versionsArray = obj.getJSONArray("versions");
            return !versionsArray.getJSONObject(0).getString("tag").equals(current);
        } catch (IOException ex) {
            throw new CannotCheckVersionException("Impossibile connettersi al server.");
        }
    }
}
