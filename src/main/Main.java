package main;

import classes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);

        Catalogue catalogue = new Catalogue();



        List<Artiste> artistes = GestionFichier.chargerArtistes("src/txt/artistes.txt");
        List<Groupe>  groupes  = GestionFichier.chargerGroupes("src/txt/groupes.txt");
        List<Morceau> morceauxInitiaux = GestionFichier.chargerMorceaux("src/txt/morceaux.txt", artistes, groupes);
        List<Abonne>  abonnes  = GestionFichier.chargerAbonnes("src/txt/abonnes.txt");
        List<Administrateur> administrateurs = GestionFichier.chargerAdministrateurs("src/txt/administrateurs.txt");

        for (Artiste a : artistes)        catalogue.ajouterArtiste(a);
        for (Groupe  g : groupes)         catalogue.ajouterGroupe(g);
        for (Morceau m : morceauxInitiaux) catalogue.ajouterMorceau(m);
        GestionFichier.chargerAlbums("src/txt/albums.txt", catalogue, artistes, groupes, morceauxInitiaux);
        GestionFichier.chargerPlaylists("src/txt/playlists.txt", abonnes, morceauxInitiaux);

        AuthentificationService authService = new AuthentificationService();

        boolean lancement = true;

        while (lancement) {
            System.out.println("\n===== BIENVENUE SUR JAVAZIK =====");
            System.out.println("1. Se connecter");
            System.out.println("2. Creer un compte abonne");
            System.out.println("3. Entrer en tant qu'invite");
            System.out.println("4. Quitter");
            System.out.print("Votre choix : ");

            int choixAccueil = clavier.nextInt();
            clavier.nextLine();

            switch (choixAccueil) {
                case 1:
                    System.out.print("Login : ");
                    String login = clavier.nextLine();
                    System.out.print("Mot de passe : ");
                    String pw = clavier.nextLine();

                    Utilisateurs utilisateurConnecte = authService.connecter(login, pw, abonnes, administrateurs);

                    if (utilisateurConnecte == null) {
                        System.out.println("Connexion echouee ou compte suspendu.");
                    } else {
                        System.out.println("Connexion reussie : " + utilisateurConnecte.getLogin());

                        if (utilisateurConnecte instanceof Abonne) {
                            menuAbonne(clavier, catalogue, (Abonne) utilisateurConnecte, abonnes, "src/txt/playlists.txt");
                        } else if (utilisateurConnecte instanceof Administrateur) {
                            menuAdministrateur(clavier, catalogue, abonnes, (Administrateur) utilisateurConnecte, artistes, groupes,"src/txt/abonnes.txt","src/txt/artistes.txt", "src/txt/groupes.txt", "src/txt/morceaux.txt", "src/txt/albums.txt");
                        }
                    }
                    break;

                case 2:
                    System.out.print("Choisissez un login : ");
                    String nouveauLogin = clavier.nextLine();
                    System.out.print("Choisissez un mot de passe : ");
                    String nouveauPw = clavier.nextLine();

                    Abonne nouvelAbonne = authService.creerCompte(nouveauLogin, nouveauPw, abonnes);
                    GestionFichier.sauvegarderAbonnes("src/txt/abonnes.txt", abonnes);

                    if (nouvelAbonne == null) {
                        System.out.println("Creation du compte impossible. Login deja utilise ou champs invalides.");
                    } else {
                        System.out.println("Compte cree avec succes.");
                        System.out.println("Bienvenue " + nouvelAbonne.getLogin());
                        menuAbonne(clavier, catalogue, nouvelAbonne, abonnes, "src/txt/playlists.txt");
                        GestionFichier.sauvegarderAbonnes("src/txt/abonnes.txt", abonnes);
                    }
                    break;

                case 3:
                    menuInvite(clavier, catalogue);
                    break;

                case 4:
                    lancement = false;
                    System.out.println("Au revoir.");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }

        clavier.close();
    }

    public static void menuInvite(Scanner clavier, Catalogue catalogue) {
        boolean continuer = true;
        int nbEcoutesInvite = 0;
        int limiteEcoutes = 3;

        while (continuer) {
            System.out.println("\n===== MENU INVITE =====");
            System.out.println("1. Afficher les morceaux");
            System.out.println("2. Afficher les albums");
            System.out.println("3. Rechercher un morceau");
            System.out.println("4. Rechercher un album");
            System.out.println("5. Rechercher un artiste");
            System.out.println("6. Rechercher un groupe");
            System.out.println("7. Ecouter un morceau");
            System.out.println("8. Retour");
            System.out.print("Votre choix : ");

            int choix = clavier.nextInt();
            clavier.nextLine();

            switch (choix) {
                case 1:
                    for (Morceau m : catalogue.getMorceaux()) {
                        System.out.println(m);
                    }
                    break;

                case 2:
                    for (Album a : catalogue.getAlbums()) {
                        System.out.println(a);
                    }
                    break;

                case 3:
                    System.out.print("Entrez le titre du morceau : ");
                    String titreRecherche = clavier.nextLine();
                    List<Morceau> resultatsMorceaux = catalogue.rechercherMorceauxParTitre(titreRecherche);

                    if (resultatsMorceaux.isEmpty()) {
                        System.out.println("Aucun morceau trouve.");
                    } else {
                        for (Morceau m : resultatsMorceaux) {
                            System.out.println(m);
                        }
                    }
                    break;

                case 4:
                    System.out.print("Entrez le titre de l'album : ");
                    String titreAlbumRecherche = clavier.nextLine();
                    List<Album> resultatsAlbums = catalogue.rechercherAlbumsParTitre(titreAlbumRecherche);

                    if (resultatsAlbums.isEmpty()) {
                        System.out.println("Aucun album trouve.");
                    } else {
                        for (Album a : resultatsAlbums) {
                            System.out.println(a);
                        }
                    }
                    break;

                case 5:
                    System.out.print("Entrez le nom de l'artiste : ");
                    String nomArtisteRecherche = clavier.nextLine();
                    List<Artiste> resultatsArtistes = catalogue.rechercherArtistesParNom(nomArtisteRecherche);

                    if (resultatsArtistes.isEmpty()) {
                        System.out.println("Aucun artiste trouve.");
                    } else {
                        for (Artiste artiste : resultatsArtistes) {
                            System.out.println("\nArtiste : " + artiste.getNom());

                            List<Morceau> morceauxArtiste = catalogue.getMorceauxParArtiste(artiste.getNom());
                            List<Album> albumsArtiste = catalogue.getAlbumsParArtiste(artiste.getNom());

                            System.out.println("--- Morceaux ---");
                            if (morceauxArtiste.isEmpty()) {
                                System.out.println("Aucun morceau.");
                            } else {
                                for (Morceau morceau : morceauxArtiste) {
                                    System.out.println(morceau);
                                }
                            }

                            System.out.println("--- Albums ---");
                            if (albumsArtiste.isEmpty()) {
                                System.out.println("Aucun album.");
                            } else {
                                for (Album album : albumsArtiste) {
                                    System.out.println(album);
                                }
                            }
                        }
                    }
                    break;

                case 6:
                    System.out.print("Entrez le nom du groupe : ");
                    String nomGroupeRecherche = clavier.nextLine();
                    List<Groupe> resultatsGroupes = catalogue.rechercherGroupesParNom(nomGroupeRecherche);

                    if (resultatsGroupes.isEmpty()) {
                        System.out.println("Aucun groupe trouve.");
                    } else {
                        for (Groupe groupe : resultatsGroupes) {
                            System.out.println("\nGroupe : " + groupe.getNom());

                            List<Morceau> morceauxGroupe = catalogue.getMorceauxParGroupe(groupe.getNom());
                            List<Album> albumsGroupe = catalogue.getAlbumsParGroupe(groupe.getNom());

                            System.out.println("--- Morceaux ---");
                            if (morceauxGroupe.isEmpty()) {
                                System.out.println("Aucun morceau.");
                            } else {
                                for (Morceau morceau : morceauxGroupe) {
                                    System.out.println(morceau);
                                }
                            }

                            System.out.println("--- Albums ---");
                            if (albumsGroupe.isEmpty()) {
                                System.out.println("Aucun album.");
                            } else {
                                for (Album album : albumsGroupe) {
                                    System.out.println(album);
                                }
                            }
                        }
                    }
                    break;

                case 7:
                    if (nbEcoutesInvite >= limiteEcoutes) {
                        System.out.println("Limite d'ecoutes atteinte pour l'invite.");
                        break;
                    }

                    System.out.println("\nChoisissez un morceau a ecouter :");
                    for (int i = 0; i < catalogue.getMorceaux().size(); i++) {
                        System.out.println((i + 1) + ". " + catalogue.getMorceaux().get(i).getTitre());
                    }

                    int choixEcoute = clavier.nextInt();
                    clavier.nextLine();

                    if (choixEcoute < 1 || choixEcoute > catalogue.getMorceaux().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Morceau morceauEcoute = catalogue.getMorceaux().get(choixEcoute - 1);
                    morceauEcoute.incrementerEcoutes();
                    nbEcoutesInvite++;

                    System.out.println("Vous ecoutez : " + morceauEcoute.getTitre());
                    System.out.println("Ecoutes invite : " + nbEcoutesInvite + "/" + limiteEcoutes);
                    break;

                case 8:
                    continuer = false;
                    System.out.println("Retour au menu principal.");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    public static void menuAbonne(Scanner clavier, Catalogue catalogue, Abonne abonne, List<Abonne> abonnes, String cheminPlaylists) {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n===== MENU ABONNE =====");
            System.out.println("1. Afficher les morceaux");
            System.out.println("2. Afficher les albums");
            System.out.println("3. Rechercher un morceau");
            System.out.println("4. Rechercher un album");
            System.out.println("5. Rechercher un artiste");
            System.out.println("6. Rechercher un groupe");
            System.out.println("7. Creer une playlist");
            System.out.println("8. Ajouter un morceau a une playlist");
            System.out.println("9. Afficher les playlists");
            System.out.println("10. Afficher le contenu d'une playlist");
            System.out.println("11. Retirer un morceau d'une playlist");
            System.out.println("12. Renommer une playlist");
            System.out.println("13. Supprimer une playlist");
            System.out.println("14. Ecouter un morceau");
            System.out.println("15. Afficher l'historique");
            System.out.println("16. Deconnexion");
            System.out.print("Votre choix : ");

            int choix = clavier.nextInt();
            clavier.nextLine();

            switch (choix) {
                case 1:
                    System.out.println("\n--- Liste des morceaux ---");
                    for (Morceau m : catalogue.getMorceaux()) {
                        System.out.println(m);
                    }
                    break;

                case 2:
                    System.out.println("\n--- Liste des albums ---");
                    for (Album a : catalogue.getAlbums()) {
                        System.out.println(a);
                    }
                    break;

                case 3:
                    System.out.print("Entrez le titre du morceau : ");
                    String titreRecherche = clavier.nextLine();

                    List<Morceau> resultatsMorceaux = catalogue.rechercherMorceauxParTitre(titreRecherche);

                    if (resultatsMorceaux.isEmpty()) {
                        System.out.println("Aucun morceau trouve.");
                    } else {
                        for (Morceau m : resultatsMorceaux) {
                            System.out.println(m);
                        }
                    }
                    break;

                case 4:
                    System.out.print("Entrez le titre de l'album : ");
                    String titreAlbumRecherche = clavier.nextLine();

                    List<Album> resultatsAlbums = catalogue.rechercherAlbumsParTitre(titreAlbumRecherche);

                    if (resultatsAlbums.isEmpty()) {
                        System.out.println("Aucun album trouve.");
                    } else {
                        for (Album a : resultatsAlbums) {
                            System.out.println(a);
                        }
                    }
                    break;

                case 5:
                    System.out.print("Entrez le nom de l'artiste : ");
                    String nomArtisteRecherche = clavier.nextLine();

                    List<Artiste> resultatsArtistes = catalogue.rechercherArtistesParNom(nomArtisteRecherche);

                    if (resultatsArtistes.isEmpty()) {
                        System.out.println("Aucun artiste trouve.");
                    } else {
                        for (Artiste artiste : resultatsArtistes) {
                            System.out.println("\nArtiste : " + artiste.getNom());

                            List<Morceau> morceauxArtiste = catalogue.getMorceauxParArtiste(artiste.getNom());
                            List<Album> albumsArtiste = catalogue.getAlbumsParArtiste(artiste.getNom());

                            System.out.println("--- Morceaux ---");
                            if (morceauxArtiste.isEmpty()) {
                                System.out.println("Aucun morceau.");
                            } else {
                                for (Morceau morceau : morceauxArtiste) {
                                    System.out.println(morceau);
                                }
                            }

                            System.out.println("--- Albums ---");
                            if (albumsArtiste.isEmpty()) {
                                System.out.println("Aucun album.");
                            } else {
                                for (Album album : albumsArtiste) {
                                    System.out.println(album);
                                }
                            }
                        }
                    }
                    break;

                case 6:
                    System.out.print("Entrez le nom du groupe : ");
                    String nomGroupeRecherche = clavier.nextLine();

                    List<Groupe> resultatsGroupes = catalogue.rechercherGroupesParNom(nomGroupeRecherche);

                    if (resultatsGroupes.isEmpty()) {
                        System.out.println("Aucun groupe trouve.");
                    } else {
                        for (Groupe groupe : resultatsGroupes) {
                            System.out.println("\nGroupe : " + groupe.getNom());

                            List<Morceau> morceauxGroupe = catalogue.getMorceauxParGroupe(groupe.getNom());
                            List<Album> albumsGroupe = catalogue.getAlbumsParGroupe(groupe.getNom());

                            System.out.println("--- Morceaux ---");
                            if (morceauxGroupe.isEmpty()) {
                                System.out.println("Aucun morceau.");
                            } else {
                                for (Morceau morceau : morceauxGroupe) {
                                    System.out.println(morceau);
                                }
                            }

                            System.out.println("--- Albums ---");
                            if (albumsGroupe.isEmpty()) {
                                System.out.println("Aucun album.");
                            } else {
                                for (Album album : albumsGroupe) {
                                    System.out.println(album);
                                }
                            }
                        }
                    }
                    break;

                case 7:
                    if (abonne.estSuspendu()) {
                        System.out.println("Compte suspendu. Action impossible.");
                        break;
                    }

                    System.out.print("Nom de la playlist : ");
                    String nomPlaylist = clavier.nextLine();

                    Playlist nouvellePlaylist = new Playlist(nomPlaylist, abonne);
                    abonne.ajouterPlaylist(nouvellePlaylist);
                    GestionFichier.sauvegarderPlaylists(cheminPlaylists, abonnes);

                    System.out.println("Playlist creee avec succes.");
                    break;

                case 8:
                    if (abonne.estSuspendu()) {
                        System.out.println("Compte suspendu. Action impossible.");
                        break;
                    }

                    if (abonne.getPlaylists().isEmpty()) {
                        System.out.println("Aucune playlist disponible.");
                        break;
                    }

                    System.out.println("\nChoisissez une playlist :");
                    for (int i = 0; i < abonne.getPlaylists().size(); i++) {
                        System.out.println((i + 1) + ". " + abonne.getPlaylists().get(i).getNom());
                    }

                    int choixPlaylist = clavier.nextInt();
                    clavier.nextLine();

                    if (choixPlaylist < 1 || choixPlaylist > abonne.getPlaylists().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Playlist playlistChoisie = abonne.getPlaylists().get(choixPlaylist - 1);

                    System.out.println("\nChoisissez un morceau :");
                    for (int i = 0; i < catalogue.getMorceaux().size(); i++) {
                        System.out.println((i + 1) + ". " + catalogue.getMorceaux().get(i).getTitre());
                    }

                    int choixMorceau = clavier.nextInt();
                    clavier.nextLine();

                    if (choixMorceau < 1 || choixMorceau > catalogue.getMorceaux().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Morceau morceauChoisi = catalogue.getMorceaux().get(choixMorceau - 1);
                    playlistChoisie.ajouterMorceau(morceauChoisi);
                    GestionFichier.sauvegarderPlaylists(cheminPlaylists, abonnes);

                    System.out.println("Morceau ajoute a la playlist.");
                    break;

                case 9:
                    System.out.println("\n--- Playlists ---");
                    if (abonne.getPlaylists().isEmpty()) {
                        System.out.println("Aucune playlist.");
                    } else {
                        for (Playlist p : abonne.getPlaylists()) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 10:
                    if (abonne.getPlaylists().isEmpty()) {
                        System.out.println("Aucune playlist disponible.");
                        break;
                    }

                    System.out.println("\nChoisissez une playlist :");
                    for (int i = 0; i < abonne.getPlaylists().size(); i++) {
                        System.out.println((i + 1) + ". " + abonne.getPlaylists().get(i).getNom());
                    }

                    int indexPlaylist = clavier.nextInt();
                    clavier.nextLine();

                    if (indexPlaylist < 1 || indexPlaylist > abonne.getPlaylists().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Playlist playlistSelectionnee = abonne.getPlaylists().get(indexPlaylist - 1);

                    System.out.println("\n--- Contenu de la playlist : " + playlistSelectionnee.getNom() + " ---");
                    if (playlistSelectionnee.getMorceaux().isEmpty()) {
                        System.out.println("Cette playlist est vide.");
                    } else {
                        for (Morceau m : playlistSelectionnee.getMorceaux()) {
                            System.out.println(m);
                        }
                    }
                    break;

                case 11:
                    if (abonne.getPlaylists().isEmpty()) {
                        System.out.println("Aucune playlist disponible.");
                        break;
                    }

                    System.out.println("\nChoisissez une playlist :");
                    for (int i = 0; i < abonne.getPlaylists().size(); i++) {
                        System.out.println((i + 1) + ". " + abonne.getPlaylists().get(i).getNom());
                    }

                    int indexPlaylistSupp = clavier.nextInt();
                    clavier.nextLine();

                    if (indexPlaylistSupp < 1 || indexPlaylistSupp > abonne.getPlaylists().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Playlist playlistASupprimerMorceau = abonne.getPlaylists().get(indexPlaylistSupp - 1);

                    if (playlistASupprimerMorceau.getMorceaux().isEmpty()) {
                        System.out.println("Cette playlist est vide.");
                        break;
                    }

                    System.out.println("\nChoisissez un morceau a retirer :");
                    for (int i = 0; i < playlistASupprimerMorceau.getMorceaux().size(); i++) {
                        System.out.println((i + 1) + ". " + playlistASupprimerMorceau.getMorceaux().get(i).getTitre());
                    }

                    int indexMorceauSupp = clavier.nextInt();
                    clavier.nextLine();

                    if (indexMorceauSupp < 1 || indexMorceauSupp > playlistASupprimerMorceau.getMorceaux().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Morceau morceauARetirer = playlistASupprimerMorceau.getMorceaux().get(indexMorceauSupp - 1);
                    playlistASupprimerMorceau.retirerMorceau(morceauARetirer);
                    GestionFichier.sauvegarderPlaylists(cheminPlaylists, abonnes);

                    System.out.println("Morceau retire de la playlist.");
                    break;

                case 12:
                    if (abonne.getPlaylists().isEmpty()) {
                        System.out.println("Aucune playlist disponible.");
                        break;
                    }

                    System.out.println("\nChoisissez une playlist a renommer :");
                    for (int i = 0; i < abonne.getPlaylists().size(); i++) {
                        System.out.println((i + 1) + ". " + abonne.getPlaylists().get(i).getNom());
                    }

                    int indexPlaylistRenommer = clavier.nextInt();
                    clavier.nextLine();

                    if (indexPlaylistRenommer < 1 || indexPlaylistRenommer > abonne.getPlaylists().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Playlist playlistARenommer = abonne.getPlaylists().get(indexPlaylistRenommer - 1);

                    System.out.print("Entrez le nouveau nom : ");
                    String nouveauNom = clavier.nextLine();

                    if (nouveauNom == null || nouveauNom.trim().isEmpty()) {
                        System.out.println("Nom invalide.");
                        break;
                    }

                    playlistARenommer.renommer(nouveauNom);
                    GestionFichier.sauvegarderPlaylists(cheminPlaylists, abonnes);
                    System.out.println("Playlist renommee avec succes.");
                    break;

                case 13:
                    if (abonne.getPlaylists().isEmpty()) {
                        System.out.println("Aucune playlist disponible.");
                        break;
                    }

                    System.out.println("\nChoisissez une playlist a supprimer :");
                    for (int i = 0; i < abonne.getPlaylists().size(); i++) {
                        System.out.println((i + 1) + ". " + abonne.getPlaylists().get(i).getNom());
                    }

                    int indexPlaylistSupprimer = clavier.nextInt();
                    clavier.nextLine();

                    if (indexPlaylistSupprimer < 1 || indexPlaylistSupprimer > abonne.getPlaylists().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Playlist playlistASupprimer = abonne.getPlaylists().get(indexPlaylistSupprimer - 1);
                    abonne.supprimerPlaylist(playlistASupprimer);
                    GestionFichier.sauvegarderPlaylists(cheminPlaylists, abonnes);

                    System.out.println("Playlist supprimee avec succes.");
                    break;

                case 14:
                    if (abonne.estSuspendu()) {
                        System.out.println("Compte suspendu. Action impossible.");
                        break;
                    }

                    System.out.println("\n--- Choisissez un morceau a ecouter ---");
                    for (int i = 0; i < catalogue.getMorceaux().size(); i++) {
                        System.out.println((i + 1) + ". " + catalogue.getMorceaux().get(i).getTitre());
                    }

                    int choixEcoute = clavier.nextInt();
                    clavier.nextLine();

                    if (choixEcoute < 1 || choixEcoute > catalogue.getMorceaux().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Morceau morceauEcoute = catalogue.getMorceaux().get(choixEcoute - 1);
                    abonne.ecouterMorceau(morceauEcoute);

                    System.out.println("Vous ecoutez : " + morceauEcoute.getTitre());
                    System.out.println("Nombre d'ecoutes de cet abonne pour ce morceau : " + abonne.getNbEcoutesMorceau(morceauEcoute));
                    break;

                case 15:
                    System.out.println("\n--- Historique d'ecoute ---");
                    if (abonne.getHistorique().isEmpty()) {
                        System.out.println("Aucun morceau ecoute.");
                    } else {
                        for (Morceau m : abonne.getHistorique()) {
                            System.out.println(m);
                        }
                    }
                    break;

                case 16:
                    continuer = false;
                    System.out.println("Deconnexion.");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    public static void menuAdministrateur(Scanner clavier, Catalogue catalogue,List<Abonne> abonnes, Administrateur admin, List<Artiste> artistes, List<Groupe> groupes, String cheminAbonnes, String cheminArtistes, String cheminGroupes, String cheminMorceaux, String cheminAlbums) {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n===== MENU ADMINISTRATEUR =====");
            System.out.println("1. Ajouter un morceau au catalogue");
            System.out.println("2. Supprimer un morceau du catalogue");
            System.out.println("3. Ajouter un album au catalogue");
            System.out.println("4. Supprimer un album du catalogue");
            System.out.println("5. Ajouter un artiste au catalogue");
            System.out.println("6. Supprimer un artiste du catalogue");
            System.out.println("7. Ajouter un groupe au catalogue");
            System.out.println("8. Supprimer un groupe du catalogue");
            System.out.println("9. Afficher les abonnes");
            System.out.println("10. Suspendre un abonne");
            System.out.println("11. Reactiver un abonne");
            System.out.println("12. Supprimer un abonne");
            System.out.println("13. Deconnexion");
            System.out.print("Votre choix : ");

            int choix = clavier.nextInt();
            clavier.nextLine();

            switch (choix) {
                case 1:
                    System.out.print("Titre du morceau : ");
                    String titreMorceau = clavier.nextLine();

                    System.out.print("Duree du morceau : ");
                    int dureeMorceau = clavier.nextInt();
                    clavier.nextLine();

                    System.out.print("Style du morceau : ");
                    String styleMorceau = clavier.nextLine();

                    System.out.print("Type d'interprete (1 = Artiste, 2 = Groupe) : ");
                    int typeInterprete = clavier.nextInt();
                    clavier.nextLine();

                    System.out.print("Nom de l'interprete : ");
                    String nomInterprete = clavier.nextLine();

                    Interprete interpreteMorceau;
                    if (typeInterprete == 1) {
                        interpreteMorceau = new Artiste(nomInterprete);
                        admin.ajouterArtisteCatalogue(catalogue, (Artiste) interpreteMorceau);
                    } else {
                        interpreteMorceau = new Groupe(nomInterprete);
                        admin.ajouterGroupeCatalogue(catalogue, (Groupe) interpreteMorceau);
                    }

                    int nouvelIdMorceau = catalogue.getMorceaux().size() + 1;
                    Morceau nouveauMorceau = new Morceau(nouvelIdMorceau, titreMorceau, dureeMorceau, styleMorceau, 0, interpreteMorceau);

                    admin.ajouterMorceauCatalogue(catalogue, nouveauMorceau);
                    GestionFichier.sauvegarderArtistes(cheminArtistes, catalogue.getArtistes());
                    GestionFichier.sauvegarderGroupes(cheminGroupes, catalogue.getGroupes());
                    GestionFichier.sauvegarderMorceaux(cheminMorceaux, catalogue.getMorceaux());
                    System.out.println("Morceau ajoute au catalogue.");
                    break;

                case 2:
                    if (catalogue.getMorceaux().isEmpty()) {
                        System.out.println("Aucun morceau dans le catalogue.");
                        break;
                    }

                    System.out.println("\nChoisissez un morceau a supprimer :");
                    for (int i = 0; i < catalogue.getMorceaux().size(); i++) {
                        System.out.println((i + 1) + ". " + catalogue.getMorceaux().get(i).getTitre());
                    }

                    int indexSuppMorceau = clavier.nextInt();
                    clavier.nextLine();

                    if (indexSuppMorceau < 1 || indexSuppMorceau > catalogue.getMorceaux().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Morceau morceauASupprimer = catalogue.getMorceaux().get(indexSuppMorceau - 1);
                    admin.supprimerMorceauCatalogue(catalogue, morceauASupprimer);
                    GestionFichier.sauvegarderMorceaux(cheminMorceaux, catalogue.getMorceaux());

                    System.out.println("Morceau supprime du catalogue.");
                    break;

                case 3:
                    System.out.print("Titre de l'album : ");
                    String titreAlbum = clavier.nextLine();

                    System.out.print("Annee de l'album : ");
                    int anneeAlbum = clavier.nextInt();
                    clavier.nextLine();

                    System.out.print("Type d'interprete (1 = Artiste, 2 = Groupe) : ");
                    int typeInterpreteAlbum = clavier.nextInt();
                    clavier.nextLine();

                    System.out.print("Nom de l'interprete : ");
                    String nomInterpreteAlbum = clavier.nextLine();

                    Interprete interpreteAlbum;
                    if (typeInterpreteAlbum == 1) {
                        interpreteAlbum = new Artiste(nomInterpreteAlbum);
                        admin.ajouterArtisteCatalogue(catalogue, (Artiste) interpreteAlbum);
                    } else {
                        interpreteAlbum = new Groupe(nomInterpreteAlbum);
                        admin.ajouterGroupeCatalogue(catalogue, (Groupe) interpreteAlbum);
                    }

                    int nouvelIdAlbum = catalogue.getAlbums().size() + 1;
                    Album nouvelAlbum = new Album(nouvelIdAlbum, titreAlbum, anneeAlbum, interpreteAlbum);

                    admin.ajouterAlbumCatalogue(catalogue, nouvelAlbum);
                    GestionFichier.sauvegarderAlbums(cheminAlbums, catalogue.getAlbums());
                    GestionFichier.sauvegarderArtistes(cheminArtistes, catalogue.getArtistes());
                    System.out.println("Album ajoute au catalogue.");
                    break;

                case 4:
                    if (catalogue.getAlbums().isEmpty()) {
                        System.out.println("Aucun album dans le catalogue.");
                        break;
                    }

                    System.out.println("\nChoisissez un album a supprimer :");
                    for (int i = 0; i < catalogue.getAlbums().size(); i++) {
                        System.out.println((i + 1) + ". " + catalogue.getAlbums().get(i).getTitre());
                    }

                    int indexSuppAlbum = clavier.nextInt();
                    clavier.nextLine();

                    if (indexSuppAlbum < 1 || indexSuppAlbum > catalogue.getAlbums().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Album albumASupprimer = catalogue.getAlbums().get(indexSuppAlbum - 1);
                    admin.supprimerAlbumCatalogue(catalogue, albumASupprimer);
                    GestionFichier.sauvegarderAlbums(cheminAlbums, catalogue.getAlbums());

                    System.out.println("Album supprime du catalogue.");
                    break;

                case 5:
                    System.out.print("Nom de l'artiste : ");
                    String nomNouvelArtiste = clavier.nextLine();

                    Artiste nouvelArtiste = new Artiste(nomNouvelArtiste);
                    admin.ajouterArtisteCatalogue(catalogue, nouvelArtiste);
                    GestionFichier.sauvegarderArtistes(cheminArtistes, catalogue.getArtistes());
                    System.out.println("Artiste ajoute au catalogue.");
                    break;

                case 6:
                    if (catalogue.getArtistes().isEmpty()) {
                        System.out.println("Aucun artiste dans le catalogue.");
                        break;
                    }

                    System.out.println("\nChoisissez un artiste a supprimer :");
                    for (int i = 0; i < catalogue.getArtistes().size(); i++) {
                        System.out.println((i + 1) + ". " + catalogue.getArtistes().get(i).getNom());
                    }

                    int indexArtiste = clavier.nextInt();
                    clavier.nextLine();

                    if (indexArtiste < 1 || indexArtiste > catalogue.getArtistes().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Artiste artisteASupprimer = catalogue.getArtistes().get(indexArtiste - 1);
                    admin.supprimerArtisteCatalogue(catalogue, artisteASupprimer);
                    GestionFichier.sauvegarderArtistes(cheminArtistes, catalogue.getArtistes());

                    System.out.println("Artiste supprime du catalogue.");
                    break;

                case 7:
                    System.out.print("Nom du groupe : ");
                    String nomNouveauGroupe = clavier.nextLine();

                    Groupe nouveauGroupe = new Groupe(nomNouveauGroupe);
                    admin.ajouterGroupeCatalogue(catalogue, nouveauGroupe);
                    GestionFichier.sauvegarderGroupes(cheminGroupes, catalogue.getGroupes());

                    System.out.println("Groupe ajoute au catalogue.");
                    break;

                case 8:
                    if (catalogue.getGroupes().isEmpty()) {
                        System.out.println("Aucun groupe dans le catalogue.");
                        break;
                    }

                    System.out.println("\nChoisissez un groupe a supprimer :");
                    for (int i = 0; i < catalogue.getGroupes().size(); i++) {
                        System.out.println((i + 1) + ". " + catalogue.getGroupes().get(i).getNom());
                    }

                    int indexGroupe = clavier.nextInt();
                    clavier.nextLine();

                    if (indexGroupe < 1 || indexGroupe > catalogue.getGroupes().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Groupe groupeASupprimer = catalogue.getGroupes().get(indexGroupe - 1);
                    admin.supprimerGroupeCatalogue(catalogue, groupeASupprimer);
                    GestionFichier.sauvegarderGroupes(cheminGroupes, catalogue.getGroupes());

                    System.out.println("Groupe supprime du catalogue.");
                    break;

                case 9:
                    System.out.println("\n--- Liste des abonnes ---");
                    if (abonnes.isEmpty()) {
                        System.out.println("Aucun abonne.");
                    } else {
                        for (int i = 0; i < abonnes.size(); i++) {
                            System.out.println((i + 1) + ". " + abonnes.get(i));
                        }
                    }
                    break;

                case 10:
                    if (abonnes.isEmpty()) {
                        System.out.println("Aucun abonne a suspendre.");
                        break;
                    }

                    System.out.println("\nChoisissez un abonne a suspendre :");
                    for (int i = 0; i < abonnes.size(); i++) {
                        System.out.println((i + 1) + ". " + abonnes.get(i));
                    }

                    int indexAbonneSuspendre = clavier.nextInt();
                    clavier.nextLine();

                    if (indexAbonneSuspendre < 1 || indexAbonneSuspendre > abonnes.size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Abonne abonneASuspendre = abonnes.get(indexAbonneSuspendre - 1);
                    admin.suspendreAbonne(abonneASuspendre);
                    GestionFichier.sauvegarderAbonnes("src/txt/abonnes.txt", abonnes);

                    System.out.println("Abonne suspendu avec succes.");
                    break;

                case 11:
                    if (abonnes.isEmpty()) {
                        System.out.println("Aucun abonne a reactiver.");
                        break;
                    }

                    System.out.println("\nChoisissez un abonne a reactiver :");
                    for (int i = 0; i < abonnes.size(); i++) {
                        System.out.println((i + 1) + ". " + abonnes.get(i));
                    }

                    int indexAbonneReactiver = clavier.nextInt();
                    clavier.nextLine();

                    if (indexAbonneReactiver < 1 || indexAbonneReactiver > abonnes.size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Abonne abonneAReactiver = abonnes.get(indexAbonneReactiver - 1);
                    admin.reactiverAbonne(abonneAReactiver);
                    GestionFichier.sauvegarderAbonnes("src/txt/abonnes.txt", abonnes);

                    System.out.println("Abonne reactive avec succes.");
                    break;

                case 12:
                    if (abonnes.isEmpty()) {
                        System.out.println("Aucun abonne a supprimer.");
                        break;
                    }

                    System.out.println("\nChoisissez un abonne a supprimer :");
                    for (int i = 0; i < abonnes.size(); i++) {
                        System.out.println((i + 1) + ". " + abonnes.get(i));
                    }

                    int indexSuppAbonne = clavier.nextInt();
                    clavier.nextLine();

                    if (indexSuppAbonne < 1 || indexSuppAbonne > abonnes.size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }

                    Abonne abonneASupprimer = abonnes.get(indexSuppAbonne - 1);
                    admin.supprimerAbonne(abonnes, abonneASupprimer);
                    GestionFichier.sauvegarderAbonnes("src/txt/abonnes.txt", abonnes);

                    System.out.println("Abonne supprime avec succes.");
                    break;

                case 13:
                    continuer = false;
                    System.out.println("Deconnexion.");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }
}