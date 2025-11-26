package org.bibliotheque.modeles;


public class Disque extends Document {
    private int dureeMinutes;

    public Disque(int id, String titre, String auteur, int anneePublication, int nombreExemplaires, int dureeMinutes) {
        super(id, titre, auteur, anneePublication, nombreExemplaires);
        this.dureeMinutes = Math.max(0, dureeMinutes);
    }

    public int getDureeMinutes() {
        return dureeMinutes;
    }

    public void setDureeMinutes(int dureeMinutes) {
        if (dureeMinutes >= 0) {
            this.dureeMinutes = dureeMinutes;
        }
    }

    @Override
    public String afficherInfos() {
        return "Disque{" +
                "id=" + getId() +
                ", titre='" + getTitre() + '\'' +
                ", auteur='" + getAuteur() + '\'' +
                ", annee=" + getAnneePublication() +
                ", exemplaires=" + getNombreExemplaires() +
                ", dureeMinutes=" + dureeMinutes +
                '}';
    }
}
