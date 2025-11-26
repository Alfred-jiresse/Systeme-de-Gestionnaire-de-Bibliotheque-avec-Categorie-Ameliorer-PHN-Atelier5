package org.bibliotheque.modeles;

public abstract class Document {
    private final int id;
    private String titre;
    private String auteur;
    private int anneePublication;
    private int nombreExemplaires;

    protected Document(int id, String titre, String auteur, int anneePublication, int nombreExemplaires) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.nombreExemplaires = Math.max(0, nombreExemplaires);
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        if (titre != null && !titre.isBlank()) {
            this.titre = titre;
        }
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        if (auteur != null && !auteur.isBlank()) {
            this.auteur = auteur;
        }
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(int anneePublication) {
        if (anneePublication > 0) {
            this.anneePublication = anneePublication;
        }
    }

    public int getNombreExemplaires() {
        return nombreExemplaires;
    }

    public void setNombreExemplaires(int nombreExemplaires) {
        if (nombreExemplaires >= 0) {
            this.nombreExemplaires = nombreExemplaires;
        }
    }


    public boolean emprunter() {
        if (nombreExemplaires > 0) {
            nombreExemplaires--;
            return true;
        }
        return false;
    }


    public void retourner() {
        nombreExemplaires++;
    }

    public abstract String afficherInfos();

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", annee=" + anneePublication +
                ", exemplaires=" + nombreExemplaires +
                '}';
    }
}
