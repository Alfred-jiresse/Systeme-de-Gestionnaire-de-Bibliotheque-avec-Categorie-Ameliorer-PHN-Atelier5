package org.bibliotheque.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.bibliotheque.modeles.Document;
import org.bibliotheque.modeles.Livre;
import org.bibliotheque.modeles.Magazine;
import org.bibliotheque.modeles.Journal;
import org.bibliotheque.modeles.Disque;
import org.bibliotheque.services.ServiceBibliotheque;
import org.bibliotheque.util.ConstantesTypes;

public class InterfaceBibliotheque {
    private final Scanner scanner;
    private final ServiceBibliotheque service;

    public InterfaceBibliotheque(Scanner scanner) {
        this.scanner = scanner;
        this.service = new ServiceBibliotheque();
    }

    public void demarrer() {
        boolean enCours = true;
        while (enCours) {
            afficherMenu();
            int choix = lireEntier();
            switch (choix) {
                case 1 -> ajouterDocument();
                case 2 -> listerDocuments();
                case 3 -> modifierDocument();
                case 4 -> supprimerDocument();
                case 5 -> emprunterDocument();
                case 6 -> retournerDocument();
                case 7 -> rechercherParTitre();
                case 8 -> filtrerParType();
                case 0 -> {
                    System.out.println("Au revoir !");
                    enCours = false;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void afficherMenu() {
        System.out.println("\n GESTION BIBLIOTHÈQUE ");
        System.out.println("1. Ajouter un document");
        System.out.println("2. Lister tous les documents");
        System.out.println("3. Modifier un document");
        System.out.println("4. Supprimer un document");
        System.out.println("5. Emprunter un document");
        System.out.println("6. Retourner un document");
        System.out.println("7. Rechercher par titre");
        System.out.println("8. Filtrer par type");
        System.out.println("0. Quitter");
        System.out.print("Choix : ");
    }

    private void ajouterDocument() {
        System.out.println("Choisir le type : 1.Livre  2.Magazine  3.Journal  4.Disque");
        int t = lireEntier();
        String type = switch (t) {
            case 1 -> ConstantesTypes.LIVRE;
            case 2 -> ConstantesTypes.MAGAZINE;
            case 3 -> ConstantesTypes.JOURNAL;
            case 4 -> ConstantesTypes.DISQUE;
            default -> {
                System.out.println("Type invalide.");
                yield null;
            }
        };
        if (type == null) return;

        System.out.print("Titre : ");
        String titre = scanner.nextLine().trim();
        System.out.print("Auteur / Rédaction : ");
        String auteur = scanner.nextLine().trim();
        System.out.print("Année de publication (ex: 2020) : ");
        int annee = lireEntier();
        System.out.print("Nombre d'exemplaires : ");
        int exemplaires = lireEntier();

        String infoSpec = null;
        if (ConstantesTypes.LIVRE.equals(type)) {
            System.out.print("Nombre de pages : ");
            infoSpec = String.valueOf(lireEntier());
        } else if (ConstantesTypes.MAGAZINE.equals(type)) {
            System.out.print("Numéro d'édition : ");
            infoSpec = scanner.nextLine().trim();
        } else if (ConstantesTypes.JOURNAL.equals(type)) {
            System.out.print("Date de parution (YYYY-MM-DD) : ");
            infoSpec = scanner.nextLine().trim();
        } else if (ConstantesTypes.DISQUE.equals(type)) {
            System.out.print("Durée en minutes : ");
            infoSpec = String.valueOf(lireEntier());
        }

        Document d = service.creerDocument(type, titre, auteur, annee, exemplaires, infoSpec);
        System.out.println("Document ajouté : " + d.afficherInfos());
    }

    private void listerDocuments() {
        List<Document> liste = service.listerTous();
        if (liste.isEmpty()) {
            System.out.println("Aucun document.");
        } else {
            System.out.println("Documents :");
            liste.forEach(d -> System.out.println(d.afficherInfos()));
        }
    }

    private void modifierDocument() {
        System.out.print("ID du document à modifier : ");
        int id = lireEntier();
        Document d = service.trouverParId(id);
        if (d == null) {
            System.out.println("Document introuvable.");
            return;
        }
        System.out.println("Document actuel : " + d.afficherInfos());
        System.out.print("Nouveau titre (laisser vide pour conserver) : ");
        String titre = scanner.nextLine().trim();
        if (!titre.isEmpty()) d.setTitre(titre);
        System.out.print("Nouvel auteur (laisser vide pour conserver) : ");
        String auteur = scanner.nextLine().trim();
        if (!auteur.isEmpty()) d.setAuteur(auteur);
        System.out.print("Nouvelle année (0 pour conserver) : ");
        int annee = lireEntier();
        if (annee > 0) d.setAnneePublication(annee);
        System.out.print("Nouveau nombre d'exemplaires (-1 pour conserver) : ");
        int ex = lireEntierAllowNegativeOne();
        if (ex >= 0) d.setNombreExemplaires(ex);

        if (d instanceof Livre) {
            Livre l = (Livre) d;
            System.out.print("Nombre de pages ( -1 pour conserver ) : ");
            int pages = lireEntierAllowNegativeOne();
            if (pages >= 0) l.setNombrePages(pages);
        } else if (d instanceof Magazine) {
            Magazine m = (Magazine) d;
            System.out.print("Numéro d'édition (laisser vide pour conserver) : ");
            String ed = scanner.nextLine().trim();
            if (!ed.isBlank()) m.setNumeroEdition(ed);
        } else if (d instanceof Journal) {
            Journal j = (Journal) d;
            System.out.print("Date de parution (YYYY-MM-DD) (laisser vide pour conserver) : ");
            String date = scanner.nextLine().trim();
            if (!date.isBlank()) {
                try {
                    j.setDateParution(LocalDate.parse(date));
                } catch (Exception e) {
                    System.out.println("Format de date invalide. Conservation de la date actuelle.");
                }
            }
        } else if (d instanceof Disque) {
            Disque disco = (Disque) d;
            System.out.print("Durée en minutes ( -1 pour conserver ) : ");
            int dur = lireEntierAllowNegativeOne();
            if (dur >= 0) disco.setDureeMinutes(dur);
        }

        boolean ok = service.mettreAJour(d);
        System.out.println(ok ? "Document mis à jour." : "Mise à jour échouée.");
    }

    private void supprimerDocument() {
        System.out.print("ID du document à supprimer : ");
        int id = lireEntier();
        boolean ok = service.supprimer(id);
        System.out.println(ok ? "Document supprimé." : "Document introuvable.");
    }

    private void emprunterDocument() {
        System.out.print("ID du document à emprunter : ");
        int id = lireEntier();
        boolean ok = service.emprunter(id);
        System.out.println(ok ? "Emprunt réussi." : "Emprunt impossible (introuvable ou plus d'exemplaires).");
    }

    private void retournerDocument() {
        System.out.print("ID du document à retourner : ");
        int id = lireEntier();
        boolean ok = service.retourner(id);
        System.out.println(ok ? "Retour enregistré." : "Document introuvable.");
    }

    private void rechercherParTitre() {
        System.out.print("Mot ou phrase dans le titre : ");
        String mot = scanner.nextLine().trim();
        List<Document> res = service.rechercherParTitre(mot);
        if (res.isEmpty()) {
            System.out.println("Aucun résultat.");
        } else {
            res.forEach(d -> System.out.println(d.afficherInfos()));
        }
    }

    private void filtrerParType() {
        System.out.println("Choisir le type : 1.Livre  2.Magazine  3.Journal  4.Disque");
        int t = lireEntier();
        String type = switch (t) {
            case 1 -> ConstantesTypes.LIVRE;
            case 2 -> ConstantesTypes.MAGAZINE;
            case 3 -> ConstantesTypes.JOURNAL;
            case 4 -> ConstantesTypes.DISQUE;
            default -> null;
        };
        if (type == null) {
            System.out.println("Type invalide.");
            return;
        }
        List<Document> res = service.filtrerParType(type);
        if (res.isEmpty()) {
            System.out.println("Aucun document de ce type.");
        } else {
            res.forEach(d -> System.out.println(d.afficherInfos()));
        }
    }

    private int lireEntier() {
        while (true) {
            String ligne = scanner.nextLine().trim();
            try {
                return Integer.parseInt(ligne);
            } catch (NumberFormatException e) {
                System.out.print("Entrée invalide, recommencez : ");
            }
        }
    }

    private int lireEntierAllowNegativeOne() {
        while (true) {
            String ligne = scanner.nextLine().trim();
            try {
                return Integer.parseInt(ligne);
            } catch (NumberFormatException e) {
                System.out.print("Entrée invalide, recommencez (ou -1 pour conserver) : ");
            }
        }
    }
}
