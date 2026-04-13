package controller;

import classes.Abonne;
import classes.Administrateur;
import classes.AuthentificationService;
import classes.Catalogue;
import classes.Utilisateurs;
import view.ConsoleView;

import java.util.List;
import java.util.Scanner;

/**
 * Controleur principal du mode console.
 * Gere l'accueil, l'authentification et la redirection vers les menus de role.
 */
public class JavazikController {
    private final ConsoleView vue;
    private final Scanner clavier;
    private final Catalogue catalogue;
    private final List<Abonne> abonnes;
    private final List<Administrateur> administrateurs;
    private final AuthentificationService authService;

    /**
     * Construit le controleur console.
     *
     * @param vue vue console utilisee pour les interactions
     * @param clavier scanner utilise pour lire les saisies
     * @param catalogue catalogue musical courant
     * @param abonnes liste des abonnes
     * @param administrateurs liste des administrateurs
     * @param authService service d'authentification
     */
    public JavazikController(
            ConsoleView vue,
            Scanner clavier,
            Catalogue catalogue,
            List<Abonne> abonnes,
            List<Administrateur> administrateurs,
            AuthentificationService authService
    ) {
        this.vue = vue;
        this.clavier = clavier;
        this.catalogue = catalogue;
        this.abonnes = abonnes;
        this.administrateurs = administrateurs;
        this.authService = authService;
    }

    /**
     * Lance la boucle du menu d'accueil et traite les actions utilisateur.
     */
    public void lancer() {
        boolean lancement = true;

        while (lancement) {
            vue.afficherMenuAccueil();
            int choixAccueil = vue.lireEntier();

            switch (choixAccueil) {
                case 1:
                    vue.afficherPrompt("Login : ");
                    String login = vue.lireLigne();
                    vue.afficherPrompt("Mot de passe : ");
                    String pw = vue.lireLigne();

                    Utilisateurs utilisateurConnecte = authService.connecter(login, pw, abonnes, administrateurs);

                    if (utilisateurConnecte == null) {
                        vue.afficherMessage("Connexion echouee ou compte suspendu.");
                    } else {
                        vue.afficherMessage("Connexion reussie : " + utilisateurConnecte.getLogin());

                        if (utilisateurConnecte instanceof Abonne) {
                            MenuController.menuAbonne(clavier, catalogue, (Abonne) utilisateurConnecte);
                        } else if (utilisateurConnecte instanceof Administrateur) {
                            MenuController.menuAdministrateur(clavier, catalogue, abonnes, (Administrateur) utilisateurConnecte);
                        }
                    }
                    break;

                case 2:
                    vue.afficherPrompt("Choisissez un login : ");
                    String nouveauLogin = vue.lireLigne();
                    vue.afficherPrompt("Choisissez un mot de passe : ");
                    String nouveauPw = vue.lireLigne();

                    Abonne nouvelAbonne = authService.creerCompte(nouveauLogin, nouveauPw, abonnes);

                    if (nouvelAbonne == null) {
                        vue.afficherMessage("Creation du compte impossible. Login deja utilise ou champs invalides.");
                    } else {
                        vue.afficherMessage("Compte cree avec succes.");
                        vue.afficherMessage("Bienvenue " + nouvelAbonne.getLogin());
                        MenuController.menuAbonne(clavier, catalogue, nouvelAbonne);
                    }
                    break;

                case 3:
                    MenuController.menuInvite(clavier, catalogue);
                    break;

                case 4:
                    lancement = false;
                    vue.afficherMessage("Au revoir.");
                    break;

                default:
                    vue.afficherMessage("Choix invalide.");
            }
        }
    }
}
