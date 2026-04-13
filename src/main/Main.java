package main;

import classes.*;
import controller.JavazikController;
import view.ConsoleView;
import view.SwingMainFrame;

import javax.swing.*;
import java.util.ArrayList;
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
        List<Abonne> abonnes = new ArrayList<>();
        List<Administrateur> administrateurs = new ArrayList<>();

        Abonne abonne1 = new Abonne(1, "willys", "0000");
        Abonne abonne2 = new Abonne(2, "alice", "1111");
        Abonne abonne3 = new Abonne(3, "bob", "2222");
        abonnes.add(abonne1);
        abonnes.add(abonne2);
        abonnes.add(abonne3);

        Administrateur admin1 = new Administrateur(99, "admin", "1234");
        administrateurs.add(admin1);

        Interprete artiste1 = new Artiste("Noichi");
        Interprete groupe1 = new Groupe("La Mifa");
        Morceau morceau1 = new Morceau(1, "Premier son", 180, "Rap", 0, artiste1);
        Morceau morceau2 = new Morceau(2, "Deuxieme son", 210, "Rock", 0, groupe1);
        Album album1 = new Album(1, "Album test", 2024, artiste1);
        album1.ajouterMorceau(morceau1);
        album1.ajouterMorceau(morceau2);

        catalogue.ajouterArtiste((Artiste) artiste1);
        catalogue.ajouterGroupe((Groupe) groupe1);
        catalogue.ajouterMorceau(morceau1);
        catalogue.ajouterMorceau(morceau2);
        catalogue.ajouterAlbum(album1);

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