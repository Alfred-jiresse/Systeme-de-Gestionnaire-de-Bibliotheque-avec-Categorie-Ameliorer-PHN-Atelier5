package org.bibliotheque.modeles;

public class Livre extends Document {
    private int nombrePages;

    public Livre(int id, String titre, String auteur, int anneePublication, int nombreExemplaires, int nombrePages) {
        super(id, titre, auteur, anneePublication, nombreExemplaires);
        this.nombrePages = Math.max(0, nombrePages);
    }

    public int getNombrePages() {
        return nombrePages;
    }

    public void setNombrePages(int nombrePages) {
        if (nombrePages >= 0) {
            this.nombrePages = nombrePages;
        }
    }

    @Override
    public String afficherInfos() {
        return "Livre{" +
                "id=" + getId() +
                ", titre='" + getTitre() + '\'' +
                ", auteur='" + getAuteur() + '\'' +
                ", annee=" + getAnneePublication() +
                ", exemplaires=" + getNombreExemplaires() +
                ", pages=" + nombrePages +
                '}';
    }
}
