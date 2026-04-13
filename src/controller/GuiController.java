package controller;

import classes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controleur de l'interface graphique Swing.
 * Expose les operations metier utilisees par la vue.
 */
public class GuiController {
    private final Catalogue catalogue;
    private final List<Abonne> abonnes;
    private final List<Administrateur> administrateurs;
    private final AuthentificationService authService;

    /**
     * Cree un controleur graphique.
     *
     * @param catalogue catalogue musical
     * @param abonnes liste des abonnes
     * @param administrateurs liste des administrateurs
     * @param authService service d'authentification
     */
    public GuiController(Catalogue catalogue, List<Abonne> abonnes, List<Administrateur> administrateurs, AuthentificationService authService) {
        this.catalogue = catalogue;
        this.abonnes = abonnes;
        this.administrateurs = administrateurs;
        this.authService = authService;
    }

    /**
     * Tente de connecter un utilisateur.
     *
     * @param login identifiant saisi
     * @param motDePasse mot de passe saisi
     * @return utilisateur connecte ou {@code null} en cas d'echec
     */
    public Utilisateurs connecter(String login, String motDePasse) {
        return authService.connecter(login, motDePasse, abonnes, administrateurs);
    }

    public Abonne creerCompte(String login, String motDePasse) {
        return authService.creerCompte(login, motDePasse, abonnes);
    }

    public List<Morceau> rechercherMorceauxParTitre(String titre) {
        return catalogue.rechercherMorceauxParTitre(titre);
    }

    public List<Morceau> rechercherMorceauxParStyle(String style) {
        return catalogue.rechercherMorceauxParStyle(style);
    }

    public List<Album> rechercherAlbumsParTitre(String titre) {
        return catalogue.rechercherAlbumsParTitre(titre);
    }

    public List<Artiste> rechercherArtistesParNom(String nom) {
        return catalogue.rechercherArtistesParNom(nom);
    }

    public List<Groupe> rechercherGroupesParNom(String nom) {
        return catalogue.rechercherGroupesParNom(nom);
    }

    public List<Morceau> getMorceauxParArtiste(String nomArtiste) {
        return catalogue.getMorceauxParArtiste(nomArtiste);
    }

    public List<Album> getAlbumsParArtiste(String nomArtiste) {
        return catalogue.getAlbumsParArtiste(nomArtiste);
    }

    public List<Morceau> getMorceauxParGroupe(String nomGroupe) {
        return catalogue.getMorceauxParGroupe(nomGroupe);
    }

    public List<Album> getAlbumsParGroupe(String nomGroupe) {
        return catalogue.getAlbumsParGroupe(nomGroupe);
    }

    public List<Morceau> morceauxTriesParTitre() {
        return catalogue.getMorceauxTriesParTitre();
    }

    public List<Morceau> morceauxTriesParDuree() {
        return catalogue.getMorceauxTriesParDuree();
    }

    public List<Morceau> morceauxTriesParEcoutes() {
        return catalogue.getMorceauxTriesParEcoutes();
    }

    public List<Morceau> getMorceaux() {
        return catalogue.getMorceaux();
    }

    public List<Album> getAlbums() {
        return catalogue.getAlbums();
    }

    public List<Artiste> getArtistes() {
        return catalogue.getArtistes();
    }

    public List<Groupe> getGroupes() {
        return catalogue.getGroupes();
    }

    public List<Abonne> getAbonnes() {
        return abonnes;
    }

    public boolean adminAjouterMorceau(String titre, int duree, String style, String typeInterprete, String nomInterprete) {
        if (titre == null || titre.trim().isEmpty() || style == null || style.trim().isEmpty()
                || nomInterprete == null || nomInterprete.trim().isEmpty() || duree <= 0) {
            return false;
        }
        Interprete interprete;
        if ("Artiste".equalsIgnoreCase(typeInterprete)) {
            Artiste a = new Artiste(nomInterprete.trim());
            catalogue.ajouterArtiste(a);
            interprete = a;
        } else {
            Groupe g = new Groupe(nomInterprete.trim());
            catalogue.ajouterGroupe(g);
            interprete = g;
        }
        Morceau m = new Morceau(catalogue.getMorceaux().size() + 1, titre.trim(), duree, style.trim(), 0, interprete);
        catalogue.ajouterMorceau(m);
        return true;
    }

    public boolean adminSupprimerMorceau(Morceau morceau) {
        if (morceau == null) return false;
        catalogue.supprimerMorceau(morceau);
        return true;
    }

    public boolean adminAjouterAlbum(String titre, int annee, String typeInterprete, String nomInterprete) {
        if (titre == null || titre.trim().isEmpty() || nomInterprete == null || nomInterprete.trim().isEmpty()) return false;
        Interprete interprete;
        if ("Artiste".equalsIgnoreCase(typeInterprete)) {
            Artiste a = new Artiste(nomInterprete.trim());
            catalogue.ajouterArtiste(a);
            interprete = a;
        } else {
            Groupe g = new Groupe(nomInterprete.trim());
            catalogue.ajouterGroupe(g);
            interprete = g;
        }
        catalogue.ajouterAlbum(new Album(catalogue.getAlbums().size() + 1, titre.trim(), annee, interprete));
        return true;
    }

    public boolean adminSupprimerAlbum(Album album) {
        if (album == null) return false;
        catalogue.supprimerAlbum(album);
        return true;
    }

    public boolean adminAjouterArtiste(String nom) {
        if (nom == null || nom.trim().isEmpty()) return false;
        catalogue.ajouterArtiste(new Artiste(nom.trim()));
        return true;
    }

    public boolean adminSupprimerArtiste(Artiste artiste) {
        if (artiste == null) return false;
        catalogue.supprimerArtiste(artiste);
        return true;
    }

    public boolean adminAjouterGroupe(String nom) {
        if (nom == null || nom.trim().isEmpty()) return false;
        catalogue.ajouterGroupe(new Groupe(nom.trim()));
        return true;
    }

    public boolean adminSupprimerGroupe(Groupe groupe) {
        if (groupe == null) return false;
        catalogue.supprimerGroupe(groupe);
        return true;
    }

    public boolean adminSuspendreAbonne(Abonne abonne) {
        if (abonne == null) return false;
        abonne.suspendre();
        return true;
    }

    public boolean adminReactiverAbonne(Abonne abonne) {
        if (abonne == null) return false;
        abonne.reactiver();
        return true;
    }

    public boolean adminSupprimerAbonne(Abonne abonne) {
        if (abonne == null) return false;
        abonnes.remove(abonne);
        return true;
    }

    public String getResumeStatsAdmin() {
        StringBuilder sb = new StringBuilder();
        sb.append("Utilisateurs: ").append(abonnes.size() + administrateurs.size()).append("\n");
        sb.append("Abonnes: ").append(abonnes.size()).append("\n");
        sb.append("Morceaux: ").append(catalogue.getMorceaux().size()).append("\n");
        sb.append("Albums: ").append(catalogue.getAlbums().size()).append("\n");
        sb.append("Artistes: ").append(catalogue.getArtistes().size()).append("\n");
        sb.append("Groupes: ").append(catalogue.getGroupes().size()).append("\n");
        sb.append("Total ecoutes: ").append(catalogue.getTotalEcoutesCatalogue()).append("\n");
        Morceau topM = catalogue.getMorceauLePlusEcoute();
        sb.append("Top morceau: ").append(topM == null ? "N/A" : topM.getTitre()).append("\n");
        Album topA = catalogue.getAlbumLePlusEcoute();
        sb.append("Top album: ").append(topA == null ? "N/A" : topA.getTitre()).append("\n");
        Interprete topI = catalogue.getInterpreteLePlusEcoute();
        sb.append("Top interprete: ").append(topI == null ? "N/A" : topI.getNom());
        return sb.toString();
    }

    /**
     * Ajoute ou modifie l'avis d'un abonne sur un morceau.
     *
     * @param abonne abonne auteur de l'avis
     * @param morceau morceau cible
     * @param note note entre 1 et 5
     * @param commentaire commentaire optionnel
     * @return {@code true} si l'operation est valide
     */
    public boolean noterMorceau(Abonne abonne, Morceau morceau, int note, String commentaire) {
        if (abonne == null || morceau == null) {
            return false;
        }
        return morceau.ajouterOuModifierAvis(abonne.getLogin(), note, commentaire);
    }

    public boolean supprimerMaNote(Abonne abonne, Morceau morceau) {
        if (abonne == null || morceau == null) {
            return false;
        }
        return morceau.supprimerAvis(abonne.getLogin());
    }

    /**
     * Construit un resume lisible des avis d'un morceau.
     *
     * @param morceau morceau cible
     * @return texte de synthese des avis
     */
    public String getResumeAvis(Morceau morceau) {
        if (morceau == null) {
            return "Morceau invalide.";
        }
        Map<String, Avis> avis = morceau.getAvisParAbonne();
        if (avis.isEmpty()) {
            return "Aucun avis pour ce morceau.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Note moyenne: ").append(String.format("%.2f", morceau.getNoteMoyenne())).append("/5\n");
        for (String login : avis.keySet()) {
            sb.append("- ").append(login).append(" -> ").append(avis.get(login)).append("\n");
        }
        return sb.toString();
    }

    public boolean creerPlaylist(Abonne abonne, String nom) {
        if (abonne == null || nom == null || nom.trim().isEmpty()) {
            return false;
        }
        abonne.ajouterPlaylist(new Playlist(nom.trim(), abonne));
        return true;
    }

    public List<Playlist> getPlaylists(Abonne abonne) {
        if (abonne == null) {
            return new ArrayList<>();
        }
        return abonne.getPlaylists();
    }

    public boolean ajouterMorceauAPlaylist(Playlist playlist, Morceau morceau) {
        if (playlist == null || morceau == null) {
            return false;
        }
        playlist.ajouterMorceau(morceau);
        return true;
    }

    public List<Morceau> getMorceauxPlaylist(Playlist playlist) {
        if (playlist == null) {
            return new ArrayList<>();
        }
        return playlist.getMorceaux();
    }

    public boolean retirerMorceauDePlaylist(Playlist playlist, Morceau morceau) {
        if (playlist == null || morceau == null) {
            return false;
        }
        playlist.retirerMorceau(morceau);
        return true;
    }

    public boolean renommerPlaylist(Playlist playlist, String nouveauNom) {
        if (playlist == null || nouveauNom == null || nouveauNom.trim().isEmpty()) {
            return false;
        }
        playlist.renommer(nouveauNom.trim());
        return true;
    }

    public boolean supprimerPlaylist(Abonne abonne, Playlist playlist) {
        if (abonne == null || playlist == null) {
            return false;
        }
        abonne.supprimerPlaylist(playlist);
        return true;
    }
}
