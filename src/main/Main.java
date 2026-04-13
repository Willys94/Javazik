package main;

import classes.*;
import controller.JavazikController;
import view.ConsoleView;
import view.SwingMainFrame;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;


public class Main {

    /**
     * Point d'entree de l'application Javazik.
     * Initialise des donnees de demonstration puis lance l'IHM graphique
     * (argument "gui") ou le mode console par defaut.
     *
     * @param args arguments de lancement
     */
    public static void main(String[] args) {
        Catalogue catalogue = new Catalogue();

        List<Artiste> artistes = GestionFichier.chargerArtistes("src/txt/artistes.txt");
        List<Groupe> groupes = GestionFichier.chargerGroupes("src/txt/groupes.txt");
        List<Morceau> morceaux = GestionFichier.chargerMorceaux("src/txt/morceaux.txt", artistes, groupes);
        List<Abonne> abonnes = GestionFichier.chargerAbonnes("src/txt/abonnes.txt");
        List<Administrateur> administrateurs = GestionFichier.chargerAdministrateurs("src/txt/administrateurs.txt");

        for (Artiste artiste : artistes) {
            catalogue.ajouterArtiste(artiste);
        }
        for (Groupe groupe : groupes) {
            catalogue.ajouterGroupe(groupe);
        }
        for (Morceau morceau : morceaux) {
            catalogue.ajouterMorceau(morceau);
        }
        GestionFichier.chargerAlbums("src/txt/albums.txt", catalogue, artistes, groupes, morceaux);
        GestionFichier.chargerPlaylists("src/txt/playlists.txt", abonnes, morceaux);

        AuthentificationService authService = new AuthentificationService();

        boolean modeGraphique = args.length > 0 && args[0].equalsIgnoreCase("gui");
        if (modeGraphique) {
            SwingUtilities.invokeLater(() -> {
                SwingMainFrame frame = new SwingMainFrame(catalogue, abonnes, administrateurs, authService);
                frame.setVisible(true);
            });
            return;
        }

        Scanner clavier = new Scanner(System.in);
        ConsoleView vue = new ConsoleView(clavier);
        JavazikController controller = new JavazikController(vue, clavier, catalogue, abonnes, administrateurs, authService);
        controller.lancer();

        clavier.close();
    }
}