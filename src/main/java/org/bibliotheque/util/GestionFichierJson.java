package org.bibliotheque.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bibliotheque.modeles.*;
import org.bibliotheque.util.RuntimeTypeAdapterFactory;
import org.bibliotheque.util.LocalDateAdapter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionFichierJson {

    private static final String CHEMIN = "data/documents.json";

    /**
     * Création d'une instance Gson configurée pour :
     * - gérer le polymorphisme (Document → Livre, Journal, Magazine, Disque)
     * - gérer LocalDate via un adaptateur spécial
     * - produire un JSON lisible (pretty printing)
     */
    public static Gson creerGson() {

        RuntimeTypeAdapterFactory<Document> typeFactory =
                RuntimeTypeAdapterFactory
                        .of(Document.class, "type") // champ ajouté au JSON
                        .registerSubtype(Livre.class, "LIVRE")
                        .registerSubtype(Magazine.class, "MAGAZINE")
                        .registerSubtype(Journal.class, "JOURNAL")
                        .registerSubtype(Disque.class, "DISQUE");

        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapterFactory(typeFactory)
                .registerTypeAdapter(java.time.LocalDate.class, new LocalDateAdapter()) // 🔥 Support LocalDate
                .create();
    }

    /**
     * Sauvegarde la liste des documents dans un fichier JSON.
     */
    public static void sauvegarder(List<Document> documents) {
        try {
            // Crée le dossier data/ s'il n'existe pas
            java.io.File dossier = new java.io.File("data");
            if (!dossier.exists()) dossier.mkdir();

            Gson gson = creerGson();

            FileWriter writer = new FileWriter(CHEMIN);
            gson.toJson(documents, writer);
            writer.close();

            System.out.println("✔ Données sauvegardées dans : " + CHEMIN);

        } catch (IOException e) {
            System.out.println("❌ Erreur sauvegarde JSON : " + e.getMessage());
        }
    }

    /**
     * Charge la liste des documents depuis un fichier JSON.
     */
    public static List<Document> charger() {
        try {
            FileReader reader = new FileReader(CHEMIN);

            Gson gson = creerGson();
            Document[] tab = gson.fromJson(reader, Document[].class);
            reader.close();

            List<Document> liste = new ArrayList<>();

            if (tab != null) {
                for (Document d : tab) {
                    liste.add(d);
                }
            }

            System.out.println("✔ Données chargées depuis : " + CHEMIN);
            return liste;

        } catch (Exception e) {
            // Si le fichier n'existe pas → renvoyer une liste vide
            return new ArrayList<>();
        }
    }
}
