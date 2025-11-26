package org.bibliotheque.modeles;

import java.time.LocalDate;


public class Journal extends Document {
    private LocalDate dateParution;

    public Journal(int id, String titre, String auteur, int anneePublication, int nombreExemplaires, LocalDate dateParution) {
        super(id, titre, auteur, anneePublication, nombreExemplaires);
        this.dateParution = (dateParution == null) ? LocalDate.now() : dateParution;
    }

    public LocalDate getDateParution() {
        return dateParution;
    }

    public void setDateParution(LocalDate dateParution) {
        if (dateParution != null) {
            this.dateParution = dateParution;
        }
    }

    @Override
    public String afficherInfos() {
        return "Journal{" +
                "id=" + getId() +
                ", titre='" + getTitre() + '\'' +
                ", auteur='" + getAuteur() + '\'' +
                ", annee=" + getAnneePublication() +
                ", exemplaires=" + getNombreExemplaires() +
                ", dateParution=" + dateParution +
                '}';
    }
}
