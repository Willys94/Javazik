package classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class GestionFichier {


    /**
     * Charge la liste des abonnés depuis un fichier texte.
     * Format attendu : id;login;motdepasse;suspendu
     * Exemple       : 1;willys;0000;false
     */
    public static List<Abonne> chargerAbonnes(String chemin) {
        List<Abonne> abonnes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith("#")) continue;

                String[] champs = ligne.split(";");
                if (champs.length < 4) continue;

                int     id        = Integer.parseInt(champs[0].trim());
                String  login     = champs[1].trim();
                String  pw        = champs[2].trim();
                boolean suspendu  = Boolean.parseBoolean(champs[3].trim());

                Abonne abonne = new Abonne(id, login, pw);
                if (suspendu) abonne.suspendre();

                abonnes.add(abonne);
            }
        } catch (FileNotFoundException e) {
            System.out.println("[GestionFichier] Fichier introuvable : " + chemin + " (démarrage avec liste vide)");
        } catch (IOException e) {
            System.out.println("[GestionFichier] Erreur lecture abonnés : " + e.getMessage());
        }

        return abonnes;
    }

    /**
     * Charge la liste des administrateurs depuis un fichier texte.
     * Format attendu : id;login;motdepasse
     * Exemple       : 99;admin;1234
     */
    public static List<Administrateur> chargerAdministrateurs(String chemin) {
        List<Administrateur> administrateurs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith("#")) continue;

                String[] champs = ligne.split(";");
                if (champs.length < 3) continue;

                int    id    = Integer.parseInt(champs[0].trim());
                String login = champs[1].trim();
                String pw    = champs[2].trim();

                administrateurs.add(new Administrateur(id, login, pw));
            }
        } catch (FileNotFoundException e) {
            System.out.println("[GestionFichier] Fichier introuvable : " + chemin + " (démarrage avec liste vide)");
        } catch (IOException e) {
            System.out.println("[GestionFichier] Erreur lecture administrateurs : " + e.getMessage());
        }

        return administrateurs;
    }

    /**
     * Charge la liste des artistes depuis un fichier texte.
     * Format attendu : id;nom
     * Exemple       : 1;Noichi
     */
    public static List<Artiste> chargerArtistes(String chemin) {
        List<Artiste> artistes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith("#")) continue;

                String[] champs = ligne.split(";");
                if (champs.length < 2) continue;

                String nom = champs[1].trim();
                artistes.add(new Artiste(nom));
            }
        } catch (FileNotFoundException e) {
            System.out.println("[GestionFichier] Fichier introuvable : " + chemin + " (démarrage avec liste vide)");
        } catch (IOException e) {
            System.out.println("[GestionFichier] Erreur lecture artistes : " + e.getMessage());
        }

        return artistes;
    }

    /**
     * Charge la liste des groupes depuis un fichier texte.
     * Format attendu : id;nom
     * Exemple       : 1;La Mifa
     */
    public static List<Groupe> chargerGroupes(String chemin) {
        List<Groupe> groupes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith("#")) continue;

                String[] champs = ligne.split(";");
                if (champs.length < 2) continue;

                String nom = champs[1].trim();
                groupes.add(new Groupe(nom));
            }
        } catch (FileNotFoundException e) {
            System.out.println("[GestionFichier] Fichier introuvable : " + chemin + " (démarrage avec liste vide)");
        } catch (IOException e) {
            System.out.println("[GestionFichier] Erreur lecture groupes : " + e.getMessage());
        }

        return groupes;
    }

    /**
     * Charge la liste des morceaux depuis un fichier texte.
     * Format attendu : id;titre;duree;style;nbEcoutes;typeInterprete;nomInterprete
     *   typeInterprete vaut "ARTISTE" ou "GROUPE"
     * Exemple       : 1;Premier son;180;Rap;0;ARTISTE;Noichi
     *
     * Si l'interprète existe déjà dans les listes fournies, on le réutilise.
     * Sinon on en crée un nouveau (mais il ne sera pas ajouté au catalogue ici).
     */
    public static List<Morceau> chargerMorceaux(String chemin,
                                                List<Artiste> artistes,
                                                List<Groupe> groupes) {
        List<Morceau> morceaux = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith("#")) continue;

                String[] champs = ligne.split(";");
                if (champs.length < 7) continue;

                int    id             = Integer.parseInt(champs[0].trim());
                String titre          = champs[1].trim();
                int    duree          = Integer.parseInt(champs[2].trim());
                String style          = champs[3].trim();
                int    nbEcoutes      = Integer.parseInt(champs[4].trim());
                String typeInterprete = champs[5].trim().toUpperCase();
                String nomInterprete  = champs[6].trim();

                Interprete interprete = trouverOuCreerInterprete(
                        typeInterprete, nomInterprete, artistes, groupes);

                if (interprete != null) {
                    Morceau m = new Morceau(id, titre, duree, style, nbEcoutes, interprete);
                    morceaux.add(m);
                } else {
                    System.out.println("[GestionFichier] Interprète inconnu ignoré : " + nomInterprete);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("[GestionFichier] Fichier introuvable : " + chemin + " (démarrage avec liste vide)");
        } catch (IOException e) {
            System.out.println("[GestionFichier] Erreur lecture morceaux : " + e.getMessage());
        }

        return morceaux;
    }

    /**
     * Charge les albums depuis un fichier texte et les ajoute directement au catalogue.
     * Format attendu : id;titre;annee;typeInterprete;nomInterprete;idMorceau1,idMorceau2,...
     *   La dernière colonne peut être vide si l'album n'a pas encore de morceaux.
     * Exemple       : 1;Album test;2024;ARTISTE;Noichi;1,2
     *
     * Les morceaux sont retrouvés par ID dans la liste fournie.
     */
    public static void chargerAlbums(String chemin,
                                     Catalogue catalogue,
                                     List<Artiste> artistes,
                                     List<Groupe> groupes,
                                     List<Morceau> morceaux) {
        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith("#")) continue;

                String[] champs = ligne.split(";");
                if (champs.length < 5) continue;

                int    id             = Integer.parseInt(champs[0].trim());
                String titre          = champs[1].trim();
                int    annee          = Integer.parseInt(champs[2].trim());
                String typeInterprete = champs[3].trim().toUpperCase();
                String nomInterprete  = champs[4].trim();

                Interprete interprete = trouverOuCreerInterprete(
                        typeInterprete, nomInterprete, artistes, groupes);

                if (interprete == null) {
                    System.out.println("[GestionFichier] Album ignoré (interprète inconnu) : " + titre);
                    continue;
                }

                Album album = new Album(id, titre, annee, interprete);

                // Ajout des morceaux si la colonne existe et n'est pas vide
                if (champs.length >= 6 && !champs[5].trim().isEmpty()) {
                    String[] idsMorceaux = champs[5].trim().split(",");
                    for (String idStr : idsMorceaux) {
                        idStr = idStr.trim();
                        if (idStr.isEmpty()) continue;
                        int idMorceau = Integer.parseInt(idStr);
                        Morceau m = trouverMorceauParId(morceaux, idMorceau);
                        if (m != null) {
                            album.ajouterMorceau(m);
                        }
                    }
                }

                catalogue.ajouterAlbum(album);
            }
        } catch (FileNotFoundException e) {
            System.out.println("[GestionFichier] Fichier introuvable : " + chemin + " (démarrage avec liste vide)");
        } catch (IOException e) {
            System.out.println("[GestionFichier] Erreur lecture albums : " + e.getMessage());
        }
    }

    /**
     * Charge les playlists depuis un fichier texte et les assigne aux abonnés concernés.
     * Format attendu : loginAbonne;nomPlaylist;idMorceau1,idMorceau2,...
     *   La dernière colonne peut être vide si la playlist est vide.
     * Exemple       : willys;MaPlaylist;1,2
     *
     * Si le login ne correspond à aucun abonné, la ligne est ignorée.
     * Les morceaux sont retrouvés par ID dans la liste fournie.
     */
    public static void chargerPlaylists(String chemin,
                                        List<Abonne> abonnes,
                                        List<Morceau> morceaux) {
        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith("#")) continue;

                String[] champs = ligne.split(";");
                if (champs.length < 2) continue;

                String loginAbonne  = champs[0].trim();
                String nomPlaylist  = champs[1].trim();

                Abonne proprietaire = trouverAbonneParLogin(abonnes, loginAbonne);
                if (proprietaire == null) {
                    System.out.println("[GestionFichier] Abonné introuvable pour la playlist : " + loginAbonne);
                    continue;
                }

                Playlist playlist = new Playlist(nomPlaylist, proprietaire);

                if (champs.length >= 3 && !champs[2].trim().isEmpty()) {
                    String[] idsMorceaux = champs[2].trim().split(",");
                    for (String idStr : idsMorceaux) {
                        idStr = idStr.trim();
                        if (idStr.isEmpty()) continue;
                        int idMorceau = Integer.parseInt(idStr);
                        Morceau m = trouverMorceauParId(morceaux, idMorceau);
                        if (m != null) {
                            playlist.ajouterMorceau(m);
                        }
                    }
                }

                proprietaire.ajouterPlaylist(playlist);
            }
        } catch (FileNotFoundException e) {
            System.out.println("[GestionFichier] Fichier introuvable : " + chemin + " (démarrage avec liste vide)");
        } catch (IOException e) {
            System.out.println("[GestionFichier] Erreur lecture playlists : " + e.getMessage());
        }
    }

    // =========================================================================
    //  SAUVEGARDE
    // =========================================================================

    /**
     * Sauvegarde la liste des abonnés dans un fichier texte.
     * Format écrit : id;login;motdepasse;suspendu
     */
    public static void sauvegarderAbonnes(String chemin, List<Abonne> abonnes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(chemin, false))) {
            for (Abonne a : abonnes) {
                String ligne = a.getId() + ";" + a.getLogin() + ";" + a.getPw() + ";" + a.estSuspendu();
                bw.write(ligne);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[GestionFichier] Erreur sauvegarde abonnés : " + e.getMessage());
        }
    }

    /**
     * Sauvegarde la liste des artistes dans un fichier texte.
     * Format écrit : id;nom  (l'id est généré selon la position dans la liste)
     */
    public static void sauvegarderArtistes(String chemin, List<Artiste> artistes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(chemin, false))) {
            for (int i = 0; i < artistes.size(); i++) {
                String ligne = (i + 1) + ";" + artistes.get(i).getNom();
                bw.write(ligne);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[GestionFichier] Erreur sauvegarde artistes : " + e.getMessage());
        }
    }

    /**
     * Sauvegarde la liste des groupes dans un fichier texte.
     * Format écrit : id;nom
     */
    public static void sauvegarderGroupes(String chemin, List<Groupe> groupes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(chemin, false))) {
            for (int i = 0; i < groupes.size(); i++) {
                String ligne = (i + 1) + ";" + groupes.get(i).getNom();
                bw.write(ligne);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[GestionFichier] Erreur sauvegarde groupes : " + e.getMessage());
        }
    }

    /**
     * Sauvegarde la liste des morceaux dans un fichier texte.
     * Format écrit : id;titre;duree;style;nbEcoutes;typeInterprete;nomInterprete
     */
    public static void sauvegarderMorceaux(String chemin, List<Morceau> morceaux) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(chemin, false))) {
            for (Morceau m : morceaux) {
                String typeInterprete = (m.getInterprete() instanceof Artiste) ? "ARTISTE" : "GROUPE";
                String ligne = m.getId() + ";"
                        + m.getTitre() + ";"
                        + m.getDuree() + ";"
                        + m.getStyle() + ";"
                        + m.getNbEcoutes() + ";"
                        + typeInterprete + ";"
                        + m.getInterprete().getNom();
                bw.write(ligne);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[GestionFichier] Erreur sauvegarde morceaux : " + e.getMessage());
        }
    }

    /**
     * Sauvegarde la liste des albums dans un fichier texte.
     * Format écrit : id;titre;annee;typeInterprete;nomInterprete;idMorceau1,idMorceau2,...
     */
    public static void sauvegarderAlbums(String chemin, List<Album> albums) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(chemin, false))) {
            for (Album a : albums) {
                String typeInterprete = (a.getInterprete() instanceof Artiste) ? "ARTISTE" : "GROUPE";

                StringBuilder idsMorceaux = new StringBuilder();
                List<Morceau> morceaux = a.getMorceaux();
                for (int i = 0; i < morceaux.size(); i++) {
                    idsMorceaux.append(morceaux.get(i).getId());
                    if (i < morceaux.size() - 1) idsMorceaux.append(",");
                }

                String ligne = a.getId() + ";"
                        + a.getTitre() + ";"
                        + a.getAnnee() + ";"
                        + typeInterprete + ";"
                        + a.getInterprete().getNom() + ";"
                        + idsMorceaux;
                bw.write(ligne);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[GestionFichier] Erreur sauvegarde albums : " + e.getMessage());
        }
    }

    /**
     * Sauvegarde toutes les playlists de tous les abonnés dans un fichier texte.
     * Format écrit : loginAbonne;nomPlaylist;idMorceau1,idMorceau2,...
     */
    public static void sauvegarderPlaylists(String chemin, List<Abonne> abonnes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(chemin, false))) {
            for (Abonne abonne : abonnes) {
                for (Playlist p : abonne.getPlaylists()) {
                    StringBuilder idsMorceaux = new StringBuilder();
                    List<Morceau> morceaux = p.getMorceaux();
                    for (int i = 0; i < morceaux.size(); i++) {
                        idsMorceaux.append(morceaux.get(i).getId());
                        if (i < morceaux.size() - 1) idsMorceaux.append(",");
                    }

                    String ligne = abonne.getLogin() + ";"
                            + p.getNom() + ";"
                            + idsMorceaux;
                    bw.write(ligne);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("[GestionFichier] Erreur sauvegarde playlists : " + e.getMessage());
        }
    }

    // =========================================================================
    //  UTILITAIRES PRIVÉS
    // =========================================================================

    /**
     * Cherche un artiste ou groupe existant dans les listes par nom.
     * Si non trouvé, en crée un nouveau sans l'ajouter aux listes.
     */
    private static Interprete trouverOuCreerInterprete(String type,
                                                       String nom,
                                                       List<Artiste> artistes,
                                                       List<Groupe> groupes) {
        if (type.equals("ARTISTE")) {
            for (Artiste a : artistes) {
                if (a.getNom().equalsIgnoreCase(nom)) return a;
            }
            // Pas trouvé -> on crée à la volée
            Artiste nouveau = new Artiste(nom);
            artistes.add(nouveau);
            return nouveau;

        } else if (type.equals("GROUPE")) {
            for (Groupe g : groupes) {
                if (g.getNom().equalsIgnoreCase(nom)) return g;
            }
            Groupe nouveau = new Groupe(nom);
            groupes.add(nouveau);
            return nouveau;
        }

        return null;
    }

    /** Cherche un morceau par son ID dans une liste. Retourne null si non trouvé. */
    private static Morceau trouverMorceauParId(List<Morceau> morceaux, int id) {
        for (Morceau m : morceaux) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    /** Cherche un abonné par login (insensible à la casse). Retourne null si non trouvé. */
    private static Abonne trouverAbonneParLogin(List<Abonne> abonnes, String login) {
        for (Abonne a : abonnes) {
            if (a.getLogin().equalsIgnoreCase(login)) return a;
        }
        return null;
    }
}
