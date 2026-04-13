package view;

import java.util.Scanner;
import java.util.InputMismatchException;

public class ConsoleView {
    private final Scanner clavier;

    public ConsoleView(Scanner clavier) {
        this.clavier = clavier;
    }

    public void afficherMenuAccueil() {
        System.out.println("\n===== BIENVENUE SUR JAVAZIK =====");
        System.out.println("1. Se connecter");
        System.out.println("2. Creer un compte abonne");
        System.out.println("3. Entrer en tant qu'invite");
        System.out.println("4. Quitter");
        System.out.print("Votre choix : ");
    }

    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public void afficherPrompt(String prompt) {
        System.out.print(prompt);
    }

    public int lireEntier() {
        while (true) {
            try {
                int valeur = clavier.nextInt();
                clavier.nextLine();
                return valeur;
            } catch (InputMismatchException e) {
                System.out.println("Saisie invalide. Merci d'entrer un nombre entier.");
                clavier.nextLine();
            }
        }
    }

    public String lireLigne() {
        return clavier.nextLine();
    }
}
