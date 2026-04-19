package view;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Gère les entrées/sorties console de l'application.
 */
public class ConsoleView {
    private final Scanner clavier;

    /**
     * Crée une vue console associée à un scanner.
     *
     * @param clavier scanner de lecture utilisateur
     */
    public ConsoleView(Scanner clavier) {
        this.clavier = clavier;
    }

    /**
     * Affiche le menu d'accueil principal.
     */
    public void afficherMenuAccueil() {
        System.out.println("\n===== BIENVENUE SUR JAVAZIK =====");
        System.out.println("1. Se connecter");
        System.out.println("2. Creer un compte abonne");
        System.out.println("3. Entrer en tant qu'invite");
        System.out.println("4. Quitter");
        System.out.print("Votre choix : ");
    }

    /**
     * Affiche une ligne de message.
     *
     * @param message texte à afficher
     */
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    /**
     * Affiche un prompt sans retour à la ligne.
     *
     * @param prompt invite de saisie
     */
    public void afficherPrompt(String prompt) {
        System.out.print(prompt);
    }

    /**
     * Lit un entier et redemande tant que la saisie est invalide.
     *
     * @return entier saisi par l'utilisateur
     */
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

    /**
     * Lit une ligne complète saisie par l'utilisateur.
     *
     * @return texte saisi
     */
    public String lireLigne() {
        return clavier.nextLine();
    }
}
