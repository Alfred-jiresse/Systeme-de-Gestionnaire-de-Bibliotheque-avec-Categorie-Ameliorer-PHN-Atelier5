package org.bibliotheque.modeles;

public class Magazine extends Document {
    private String numeroEdition;

    public Magazine(int id, String titre, String auteur, int anneePublication, int nombreExemplaires, String numeroEdition) {
        super(id, titre, auteur, anneePublication, nombreExemplaires);
        this.numeroEdition = (numeroEdition == null) ? "" : numeroEdition;
    }

    public String getNumeroEdition() {
        return numeroEdition;
    }

    public void setNumeroEdition(String numeroEdition) {
        if (numeroEdition != null) {
            this.numeroEdition = numeroEdition;
        }
    }

    @Override
    public String afficherInfos() {
        return "Magazine{" +
                "id=" + getId() +
                ", titre='" + getTitre() + '\'' +
                ", auteur='" + getAuteur() + '\'' +
                ", annee=" + getAnneePublication() +
                ", exemplaires=" + getNombreExemplaires() +
                ", edition='" + numeroEdition + '\'' +
                '}';
    }
}
