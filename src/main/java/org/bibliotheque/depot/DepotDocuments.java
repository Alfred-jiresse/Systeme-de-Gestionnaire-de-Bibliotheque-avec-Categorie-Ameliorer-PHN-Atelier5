package org.bibliotheque.depot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bibliotheque.modeles.Document;

public class DepotDocuments {
    private final List<Document> documents = new ArrayList<>();

    public List<Document> trouverTous() {
        return new ArrayList<>(documents);
    }

    public Optional<Document> trouverParId(int id) {
        return documents.stream().filter(d -> d.getId() == id).findFirst();
    }

    public void ajouter(Document document) {
        documents.add(document);
    }

    public boolean supprimerParId(int id) {
        return documents.removeIf(d -> d.getId() == id);
    }

    public boolean remplacer(Document nouveau) {
        int id = nouveau.getId();
        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).getId() == id) {
                documents.set(i, nouveau);
                return true;
            }
        }
        return false;
    }
}
