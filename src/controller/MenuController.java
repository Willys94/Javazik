package controller;

import classes.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Regroupe les menus console selon le role de l'utilisateur.
 * Cette classe centralise la navigation en mode texte.
 */
public class MenuController {

    /**
     * Affiche et gere le menu invite.
     * L'invite peut consulter le catalogue et ecouter un nombre limite de morceaux.
     *
     * @param clavier scanner de saisie utilisateur
     * @param catalogue catalogue musical
     */
    public static void menuInvite(Scanner clavier, Catalogue catalogue) {
        boolean continuer = true;
        int nbEcoutesInvite = 0;
        int limiteEcoutes = 3;

        while (continuer) {
            try {
                System.out.println("\n===== MENU INVITE =====");
                System.out.println("1. Afficher les morceaux");
                System.out.println("2. Afficher les albums");
                System.out.println("3. Rechercher un morceau");
                System.out.println("4. Rechercher un album");
                System.out.println("5. Rechercher un artiste");
                System.out.println("6. Rechercher un groupe");
                System.out.println("7. Rechercher un style musical");
                System.out.println("8. Ecouter un morceau");
                System.out.println("9. Retour");
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
                    System.out.print("Entrez le style musical : ");
                    String styleRechercheInvite = clavier.nextLine();
                    List<Morceau> resultatsStyleInvite = catalogue.rechercherMorceauxParStyle(styleRechercheInvite);
                    if (resultatsStyleInvite.isEmpty()) {
                        System.out.println("Aucun morceau trouve pour ce style.");
                    } else {
                        for (Morceau m : resultatsStyleInvite) {
                            System.out.println(m);
                        }
                    }
                    break;
                case 8:
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
                    if (SharedService.ecouterMorceauInvite(morceauEcoute)) {
                        nbEcoutesInvite++;
                        System.out.println("Vous ecoutez : " + morceauEcoute.getTitre());
                        System.out.println("Ecoutes invite : " + nbEcoutesInvite + "/" + limiteEcoutes);
                    } else {
                        System.out.println("Lecture impossible.");
                    }
                    break;
                case 9:
                    continuer = false;
                    System.out.println("Retour au menu principal.");
                    break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Saisie invalide. Merci d'entrer un nombre entier.");
                clavier.nextLine();
            }
        }
    }

    /**
     * Affiche et gere le menu abonne.
     * Permet notamment la gestion des playlists, l'historique et les avis.
     *
     * @param clavier scanner de saisie utilisateur
     * @param catalogue catalogue musical
     * @param abonne abonne connecte
     */
    public static void menuAbonne(Scanner clavier, Catalogue catalogue, Abonne abonne, List<Abonne> abonnes, PersistenceService persistenceService) {
        boolean continuer = true;
        while (continuer) {
            try {
                System.out.println("\n===== MENU ABONNE =====");
                System.out.println("1. Afficher les morceaux");
                System.out.println("2. Afficher les albums");
                System.out.println("3. Rechercher un morceau");
                System.out.println("4. Rechercher un album");
                System.out.println("5. Rechercher un artiste");
                System.out.println("6. Rechercher un groupe");
                System.out.println("7. Rechercher un style musical");
                System.out.println("8. Creer une playlist");
                System.out.println("9. Ajouter un morceau a une playlist");
                System.out.println("10. Afficher les playlists");
                System.out.println("11. Afficher le contenu d'une playlist");
                System.out.println("12. Retirer un morceau d'une playlist");
                System.out.println("13. Renommer une playlist");
                System.out.println("14. Supprimer une playlist");
                System.out.println("15. Ecouter un morceau");
                System.out.println("16. Afficher l'historique");
                System.out.println("17. Noter ou modifier la note d'un morceau");
                System.out.println("18. Supprimer ma note d'un morceau");
                System.out.println("19. Afficher les avis d'un morceau");
                System.out.println("20. Afficher les morceaux tries");
                System.out.println("21. Deconnexion");
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
                    System.out.print("Entrez le style musical : ");
                    String styleRechercheAbonne = clavier.nextLine();
                    List<Morceau> resultatsStyleAbonne = catalogue.rechercherMorceauxParStyle(styleRechercheAbonne);
                    if (resultatsStyleAbonne.isEmpty()) {
                        System.out.println("Aucun morceau trouve pour ce style.");
                    } else {
                        for (Morceau m : resultatsStyleAbonne) {
                            System.out.println(m);
                        }
                    }
                    break;
                case 8:
                    if (abonne.estSuspendu()) {
                        System.out.println("Compte suspendu. Action impossible.");
                        break;
                    }
                    System.out.print("Nom de la playlist : ");
                    String nomPlaylist = clavier.nextLine();
                    if (SharedService.creerPlaylist(abonne, nomPlaylist)) {
                        persistenceService.savePlaylists(abonnes);
                        System.out.println("Playlist creee avec succes.");
                    } else {
                        System.out.println("Nom invalide.");
                    }
                    break;
                case 9:
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
                    if (SharedService.ajouterMorceauAPlaylist(playlistChoisie, morceauChoisi)) {
                        persistenceService.savePlaylists(abonnes);
                        System.out.println("Morceau ajoute a la playlist.");
                    }
                    break;
                case 10:
                    System.out.println("\n--- Playlists ---");
                    if (abonne.getPlaylists().isEmpty()) {
                        System.out.println("Aucune playlist.");
                    } else {
                        for (Playlist p : abonne.getPlaylists()) {
                            System.out.println(p);
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
                case 12:
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
                    if (SharedService.retirerMorceauDePlaylist(playlistASupprimerMorceau, morceauARetirer)) {
                        persistenceService.savePlaylists(abonnes);
                        System.out.println("Morceau retire de la playlist.");
                    }
                    break;
                case 13:
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
                    if (SharedService.renommerPlaylist(playlistARenommer, nouveauNom)) {
                        persistenceService.savePlaylists(abonnes);
                        System.out.println("Playlist renommee avec succes.");
                    }
                    break;
                case 14:
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
                    if (SharedService.supprimerPlaylist(abonne, playlistASupprimer)) {
                        persistenceService.savePlaylists(abonnes);
                        System.out.println("Playlist supprimee avec succes.");
                    }
                    break;
                case 15:
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
                    if (SharedService.ecouterMorceauAbonne(abonne, morceauEcoute)) {
                        persistenceService.saveCatalogue(catalogue);
                        System.out.println("Vous ecoutez : " + morceauEcoute.getTitre());
                        System.out.println("Nombre d'ecoutes de cet abonne pour ce morceau : " + abonne.getNbEcoutesMorceau(morceauEcoute));
                    } else {
                        System.out.println("Lecture impossible.");
                    }
                    break;
                case 16:
                    System.out.println("\n--- Historique d'ecoute ---");
                    if (abonne.getHistorique().isEmpty()) {
                        System.out.println("Aucun morceau ecoute.");
                    } else {
                        for (Morceau m : abonne.getHistorique()) {
                            System.out.println(m);
                        }
                    }
                    break;
                case 17:
                    if (abonne.estSuspendu()) {
                        System.out.println("Compte suspendu. Action impossible.");
                        break;
                    }
                    if (catalogue.getMorceaux().isEmpty()) {
                        System.out.println("Aucun morceau disponible.");
                        break;
                    }
                    System.out.println("\nChoisissez un morceau a noter :");
                    for (int i = 0; i < catalogue.getMorceaux().size(); i++) {
                        System.out.println((i + 1) + ". " + catalogue.getMorceaux().get(i).getTitre());
                    }
                    int indexMorceauNote = clavier.nextInt();
                    clavier.nextLine();
                    if (indexMorceauNote < 1 || indexMorceauNote > catalogue.getMorceaux().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }
                    Morceau morceauANoter = catalogue.getMorceaux().get(indexMorceauNote - 1);
                    System.out.print("Note (1 a 5) : ");
                    int note = clavier.nextInt();
                    clavier.nextLine();
                    System.out.print("Commentaire (optionnel) : ");
                    String commentaire = clavier.nextLine();
                    if (SharedService.noterMorceau(abonne, morceauANoter, note, commentaire)) {
                        System.out.println("Avis enregistre.");
                    } else {
                        System.out.println("Note invalide (elle doit etre comprise entre 1 et 5).");
                    }
                    break;
                case 18:
                    if (catalogue.getMorceaux().isEmpty()) {
                        System.out.println("Aucun morceau disponible.");
                        break;
                    }
                    System.out.println("\nChoisissez un morceau :");
                    for (int i = 0; i < catalogue.getMorceaux().size(); i++) {
                        System.out.println((i + 1) + ". " + catalogue.getMorceaux().get(i).getTitre());
                    }
                    int indexMorceauSupprAvis = clavier.nextInt();
                    clavier.nextLine();
                    if (indexMorceauSupprAvis < 1 || indexMorceauSupprAvis > catalogue.getMorceaux().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }
                    Morceau morceauSuppAvis = catalogue.getMorceaux().get(indexMorceauSupprAvis - 1);
                    if (SharedService.supprimerNote(abonne, morceauSuppAvis)) {
                        System.out.println("Avis supprime.");
                    } else {
                        System.out.println("Vous n'avez pas d'avis sur ce morceau.");
                    }
                    break;
                case 19:
                    if (catalogue.getMorceaux().isEmpty()) {
                        System.out.println("Aucun morceau disponible.");
                        break;
                    }
                    System.out.println("\nChoisissez un morceau :");
                    for (int i = 0; i < catalogue.getMorceaux().size(); i++) {
                        System.out.println((i + 1) + ". " + catalogue.getMorceaux().get(i).getTitre());
                    }
                    int indexMorceauAvis = clavier.nextInt();
                    clavier.nextLine();
                    if (indexMorceauAvis < 1 || indexMorceauAvis > catalogue.getMorceaux().size()) {
                        System.out.println("Choix invalide.");
                        break;
                    }
                    Morceau morceauAvis = catalogue.getMorceaux().get(indexMorceauAvis - 1);
                    if (morceauAvis.getAvisParAbonne().isEmpty()) {
                        System.out.println("Aucun avis pour ce morceau.");
                    } else {
                        System.out.printf("Note moyenne : %.2f/5%n", morceauAvis.getNoteMoyenne());
                        for (String login : morceauAvis.getAvisParAbonne().keySet()) {
                            Avis avis = morceauAvis.getAvisParAbonne().get(login);
                            System.out.println("- " + login + " -> " + avis);
                        }
                    }
                    break;
                case 20:
                    if (catalogue.getMorceaux().isEmpty()) {
                        System.out.println("Aucun morceau disponible.");
                        break;
                    }
                    System.out.println("\nTrier les morceaux par :");
                    System.out.println("1. Titre");
                    System.out.println("2. Duree");
                    System.out.println("3. Nombre d'ecoutes");
                    System.out.print("Votre choix : ");
                    int choixTri = clavier.nextInt();
                    clavier.nextLine();
                    List<Morceau> morceauxTries;
                    if (choixTri == 1) {
                        morceauxTries = catalogue.getMorceauxTriesParTitre();
                    } else if (choixTri == 2) {
                        morceauxTries = catalogue.getMorceauxTriesParDuree();
                    } else if (choixTri == 3) {
                        morceauxTries = catalogue.getMorceauxTriesParEcoutes();
                    } else {
                        System.out.println("Choix invalide.");
                        break;
                    }
                    for (Morceau m : morceauxTries) {
                        System.out.println(m);
                    }
                    break;
                case 21:
                    continuer = false;
                    System.out.println("Deconnexion.");
                    break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Saisie invalide. Merci d'entrer un nombre entier.");
                clavier.nextLine();
            }
        }
    }

    /**
     * Affiche et gere le menu administrateur.
     * Permet la gestion du catalogue, des abonnes et l'acces aux statistiques.
     *
     * @param clavier scanner de saisie utilisateur
     * @param catalogue catalogue musical
     * @param abonnes liste des abonnes
     * @param admin administrateur connecte
     */
    public static void menuAdministrateur(Scanner clavier, Catalogue catalogue, List<Abonne> abonnes, Administrateur admin, PersistenceService persistenceService) {
        boolean continuer = true;
        while (continuer) {
            try {
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
                System.out.println("13. Afficher les statistiques");
                System.out.println("14. Deconnexion");
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
                    String typeNom = typeInterprete == 1 ? "Artiste" : "Groupe";
                    if (SharedService.adminAjouterMorceau(catalogue, titreMorceau, dureeMorceau, styleMorceau, typeNom, nomInterprete)) {
                        persistenceService.saveCatalogue(catalogue);
                        System.out.println("Morceau ajoute au catalogue.");
                    }
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
                    if (SharedService.adminSupprimerMorceau(catalogue, morceauASupprimer)) {
                        persistenceService.saveCatalogue(catalogue);
                        System.out.println("Morceau supprime du catalogue.");
                    }
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
                    String typeNomAlbum = typeInterpreteAlbum == 1 ? "Artiste" : "Groupe";
                    if (SharedService.adminAjouterAlbum(catalogue, titreAlbum, anneeAlbum, typeNomAlbum, nomInterpreteAlbum)) {
                        persistenceService.saveCatalogue(catalogue);
                        System.out.println("Album ajoute au catalogue.");
                    }
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
                    if (SharedService.adminSupprimerAlbum(catalogue, albumASupprimer)) {
                        persistenceService.saveCatalogue(catalogue);
                        System.out.println("Album supprime du catalogue.");
                    }
                    break;
                case 5:
                    System.out.print("Nom de l'artiste : ");
                    String nomNouvelArtiste = clavier.nextLine();
                    if (SharedService.adminAjouterArtiste(catalogue, nomNouvelArtiste)) {
                        persistenceService.saveCatalogue(catalogue);
                        System.out.println("Artiste ajoute au catalogue.");
                    }
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
                    if (SharedService.adminSupprimerArtiste(catalogue, artisteASupprimer)) {
                        persistenceService.saveCatalogue(catalogue);
                        System.out.println("Artiste supprime du catalogue.");
                    }
                    break;
                case 7:
                    System.out.print("Nom du groupe : ");
                    String nomNouveauGroupe = clavier.nextLine();
                    if (SharedService.adminAjouterGroupe(catalogue, nomNouveauGroupe)) {
                        persistenceService.saveCatalogue(catalogue);
                        System.out.println("Groupe ajoute au catalogue.");
                    }
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
                    if (SharedService.adminSupprimerGroupe(catalogue, groupeASupprimer)) {
                        persistenceService.saveCatalogue(catalogue);
                        System.out.println("Groupe supprime du catalogue.");
                    }
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
                    if (SharedService.adminSuspendreAbonne(abonneASuspendre)) {
                        persistenceService.saveAccounts(abonnes);
                        System.out.println("Abonne suspendu avec succes.");
                    }
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
                    if (SharedService.adminReactiverAbonne(abonneAReactiver)) {
                        persistenceService.saveAccounts(abonnes);
                        System.out.println("Abonne reactive avec succes.");
                    }
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
                    if (SharedService.adminSupprimerAbonne(abonnes, abonneASupprimer)) {
                        persistenceService.saveAccounts(abonnes);
                        persistenceService.savePlaylists(abonnes);
                        System.out.println("Abonne supprime avec succes.");
                    }
                    break;
                case 13:
                    System.out.println("\n--- Statistiques globales ---");
                    System.out.println("Nombre d'utilisateurs : " + (abonnes.size() + 1));
                    System.out.println("Nombre d'abonnes : " + abonnes.size());
                    System.out.println("Nombre de morceaux : " + catalogue.getMorceaux().size());
                    System.out.println("Nombre d'albums : " + catalogue.getAlbums().size());
                    System.out.println("Nombre d'artistes : " + catalogue.getArtistes().size());
                    System.out.println("Nombre de groupes : " + catalogue.getGroupes().size());
                    System.out.println("Nombre total d'ecoutes : " + catalogue.getTotalEcoutesCatalogue());
                    System.out.println("\n--- Statistiques avancees ---");
                    Morceau topMorceau = catalogue.getMorceauLePlusEcoute();
                    Album topAlbum = catalogue.getAlbumLePlusEcoute();
                    Interprete topInterprete = catalogue.getInterpreteLePlusEcoute();
                    if (topMorceau != null) {
                        System.out.println("Morceau le plus ecoute : " + topMorceau.getTitre() + " (" + topMorceau.getNbEcoutes() + " ecoutes)");
                    } else {
                        System.out.println("Morceau le plus ecoute : N/A");
                    }
                    if (topAlbum != null) {
                        int ecoutesAlbum = 0;
                        for (Morceau morceau : topAlbum.getMorceaux()) {
                            ecoutesAlbum += morceau.getNbEcoutes();
                        }
                        System.out.println("Album le plus ecoute : " + topAlbum.getTitre() + " (" + ecoutesAlbum + " ecoutes)");
                    } else {
                        System.out.println("Album le plus ecoute : N/A");
                    }
                    if (topInterprete != null) {
                        int ecoutesInterprete = 0;
                        for (Morceau morceau : catalogue.getMorceaux()) {
                            if (morceau.getInterprete().getNom().equalsIgnoreCase(topInterprete.getNom())) {
                                ecoutesInterprete += morceau.getNbEcoutes();
                            }
                        }
                        System.out.println("Interprete le plus ecoute : " + topInterprete.getNom() + " (" + ecoutesInterprete + " ecoutes)");
                    } else {
                        System.out.println("Interprete le plus ecoute : N/A");
                    }
                    break;
                case 14:
                    continuer = false;
                    System.out.println("Deconnexion.");
                    break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Saisie invalide. Merci d'entrer un nombre entier.");
                clavier.nextLine();
            }
        }
    }
}
