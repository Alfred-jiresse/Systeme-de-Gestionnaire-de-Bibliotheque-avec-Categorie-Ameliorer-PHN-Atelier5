package org.bibliotheque;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// Main.java
import java.util.List;
import java.util.Scanner;

import org.bibliotheque.modeles.Document;
import org.bibliotheque.modeles.Magazine;
import org.bibliotheque.ui.InterfaceBibliotheque;
import org.bibliotheque.util.GestionFichierJson;

public class Main {
    public static void main(String[] args) {
        InterfaceBibliotheque ui = new InterfaceBibliotheque(new Scanner(System.in));
        ui.demarrer();
        // Charger les documents existants (ou liste vide si le fichier n'existe pas)
        List<Document> documents = GestionFichierJson.charger();

        // Si la liste est vide, on peut créer un fichier avec des données par défaut
        if (documents.isEmpty()) {
            System.out.println("Aucun document trouvé, création d'un fichier par défaut...");
            // Sauvegarder immédiatement pour générer le fichier JSON
            GestionFichierJson.sauvegarder(documents);
        }
        // Afficher les documents chargés
        for (Document d : documents) {
            System.out.println(d);
        }
    }
}
