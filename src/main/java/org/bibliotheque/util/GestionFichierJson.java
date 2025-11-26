package org.bibliotheque.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bibliotheque.util.RuntimeTypeAdapterFactory;
import org.bibliotheque.modeles.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GestionFichierJson {

    private static final String CHEMIN = "data/documents.json";

    private static Gson creerGson() {
        RuntimeTypeAdapterFactory<Document> typeFactory =
                RuntimeTypeAdapterFactory
                        .of(Document.class, "type")   // nom du champ dans le json
                        .registerSubtype(Livre.class, "LIVRE")
                        .registerSubtype(Magazine.class, "MAGAZINE")
                        .registerSubtype(Journal.class, "JOURNAL")
                        .registerSubtype(Disque.class, "DISQUE");

        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(typeFactory)
                .create();
    }

    public static void sauvegarder(List<Document> documents) {
        try {
            java.io.File dossier = new java.io.File("data");
            if (!dossier.exists()) dossier.mkdir();

            Gson gson = creerGson();
            FileWriter writer = new FileWriter(CHEMIN);
            gson.toJson(documents, writer);
            writer.close();
        } catch (IOException e) {
            System.out.println("Erreur de sauvegarde JSON : " + e.getMessage());
        }
    }

    public static List<Document> charger() {
        try {
            FileReader reader = new FileReader(CHEMIN);
            Gson gson = creerGson();
            Document[] tableau = gson.fromJson(reader, Document[].class);
            reader.close();

            if (tableau == null) return new ArrayList<>();

            List<Document> liste = new ArrayList<>();
            for (Document d : tableau) liste.add(d);

            return liste;

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
