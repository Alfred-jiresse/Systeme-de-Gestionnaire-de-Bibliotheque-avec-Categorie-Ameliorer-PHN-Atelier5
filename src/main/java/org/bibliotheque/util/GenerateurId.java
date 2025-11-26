package org.bibliotheque.util;

import java.util.concurrent.atomic.AtomicInteger;


public class GenerateurId {
    private static final AtomicInteger COMPTEUR = new AtomicInteger(0);

    public static int suivant() {
        return COMPTEUR.incrementAndGet();
    }
}
