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
