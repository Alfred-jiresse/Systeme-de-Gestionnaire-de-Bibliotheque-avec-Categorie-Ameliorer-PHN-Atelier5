package org.bibliotheque.services;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.bibliotheque.depot.DepotDocuments;
import org.bibliotheque.modeles.*;
import org.bibliotheque.util.GenerateurId;
import org.bibliotheque.util.ConstantesTypes;
import org.bibliotheque.util.GestionFichierJson;


public class ServiceBibliotheque {
    private final DepotDocuments depot = new DepotDocuments();

    public ServiceBibliotheque() {
        List<Document> documents = GestionFichierJson.charger();
        documents.forEach(depot::ajouter);
        GestionFichierJson.sauvegarder(depot.trouverTous());
    }

    public Document creerDocument(String type, String titre, String auteur, int anneePublication, int nombreExemplaires, String infoSpecifique) {
        int id = GenerateurId.suivant();
        Document d;
        if (ConstantesTypes.LIVRE.equals(type)) {
            int pages = parseEntierSafe(infoSpecifique, 0);
            d = new Livre(id, titre, auteur, anneePublication, nombreExemplaires, pages);
        } else if (ConstantesTypes.MAGAZINE.equals(type)) {
            String edition = (infoSpecifique == null) ? "" : infoSpecifique;
            d = new Magazine(id, titre, auteur, anneePublication, nombreExemplaires, edition);
        } else if (ConstantesTypes.JOURNAL.equals(type)) {
            LocalDate date;
            try {
                date = (infoSpecifique == null || infoSpecifique.isBlank()) ? LocalDate.now()
                        : LocalDate.parse(infoSpecifique);
            } catch (DateTimeParseException e) {
                date = LocalDate.now();
            }
            d = new Journal(id, titre, auteur, anneePublication, nombreExemplaires, date);
        } else if (ConstantesTypes.DISQUE.equals(type)) {
            int duree = parseEntierSafe(infoSpecifique, 0);
            d = new Disque(id, titre, auteur, anneePublication, nombreExemplaires, duree);
        } else {
            throw new IllegalArgumentException("Type inconnu : " + type);
        }
        depot.ajouter(d);
        return d;
    }

    public List<Document> listerTous() {
        return depot.trouverTous();
    }

    public Document trouverParId(int id) {
        return depot.trouverParId(id).orElse(null);
    }

    //remplacer le document complet (ici je peux reconstruire un Document ou modifier champs)
    public boolean mettreAJour(Document d) {
        return depot.remplacer(d);
    }

    public boolean supprimer(int id) {
        return depot.supprimerParId(id);
    }

    public boolean emprunter(int id) {
        Document d = trouverParId(id);
        if (d == null) return false;
        return d.emprunter();
    }

    public boolean retourner(int id) {
        Document d = trouverParId(id);
        if (d == null) return false;
        d.retourner();
        return true;
    }

    public List<Document> rechercherParTitre(String mot) {
        String m = (mot == null) ? "" : mot.toLowerCase();
        return depot.trouverTous().stream()
                .filter(d -> d.getTitre().toLowerCase().contains(m))
                .collect(Collectors.toList());
    }

    public List<Document> filtrerParType(String type) {
        return depot.trouverTous().stream()
                .filter(d -> {
                    switch (type) {
                        case ConstantesTypes.LIVRE:
                            return d instanceof Livre;
                        case ConstantesTypes.MAGAZINE:
                            return d instanceof Magazine;
                        case ConstantesTypes.JOURNAL:
                            return d instanceof Journal;
                        case ConstantesTypes.DISQUE:
                            return d instanceof Disque;
                        default:
                            return false;
                    }
                }).collect(Collectors.toList());
    }

    private int parseEntierSafe(String s, int defaut) {
        if (s == null || s.isBlank()) return defaut;
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return defaut;
        }
    }
}
