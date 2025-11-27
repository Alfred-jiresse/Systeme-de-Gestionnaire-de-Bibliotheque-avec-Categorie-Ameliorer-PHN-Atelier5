package org.bibliotheque;

import java.util.Scanner;
import org.bibliotheque.ui.InterfaceBibliotheque;

public class Main {
    public static void main(String[] args) {
        InterfaceBibliotheque ui = new InterfaceBibliotheque(new Scanner(System.in));
        ui.demarrer();
    }
}
