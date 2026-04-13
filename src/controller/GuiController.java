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
    private final PersistenceService persistenceService;

    /**
     * Cree un controleur graphique.
     *
     * @param catalogue catalogue musical
     * @param abonnes liste des abonnes
     * @param administrateurs liste des administrateurs
     * @param authService service d'authentification
     */
    public GuiController(Catalogue catalogue, List<Abonne> abonnes, List<Administrateur> administrateurs,
                         AuthentificationService authService, PersistenceService persistenceService) {
        this.catalogue = catalogue;
        this.abonnes = abonnes;
        this.administrateurs = administrateurs;
        this.authService = authService;
        this.persistenceService = persistenceService;
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
        Abonne abonne = authService.creerCompte(login, motDePasse, abonnes);
        if (abonne != null) {
            persistenceService.saveAccounts(abonnes);
        }
        return abonne;
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
        boolean ok = SharedService.adminAjouterMorceau(catalogue, titre, duree, style, typeInterprete, nomInterprete);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    public boolean adminSupprimerMorceau(Morceau morceau) {
        boolean ok = SharedService.adminSupprimerMorceau(catalogue, morceau);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    public boolean adminAjouterAlbum(String titre, int annee, String typeInterprete, String nomInterprete) {
        boolean ok = SharedService.adminAjouterAlbum(catalogue, titre, annee, typeInterprete, nomInterprete);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    public boolean adminSupprimerAlbum(Album album) {
        boolean ok = SharedService.adminSupprimerAlbum(catalogue, album);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    public boolean adminAjouterArtiste(String nom) {
        boolean ok = SharedService.adminAjouterArtiste(catalogue, nom);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    public boolean adminSupprimerArtiste(Artiste artiste) {
        boolean ok = SharedService.adminSupprimerArtiste(catalogue, artiste);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    public boolean adminAjouterGroupe(String nom) {
        boolean ok = SharedService.adminAjouterGroupe(catalogue, nom);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    public boolean adminSupprimerGroupe(Groupe groupe) {
        boolean ok = SharedService.adminSupprimerGroupe(catalogue, groupe);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    public boolean adminSuspendreAbonne(Abonne abonne) {
        boolean ok = SharedService.adminSuspendreAbonne(abonne);
        if (ok) persistenceService.saveAccounts(abonnes);
        return ok;
    }

    public boolean adminReactiverAbonne(Abonne abonne) {
        boolean ok = SharedService.adminReactiverAbonne(abonne);
        if (ok) persistenceService.saveAccounts(abonnes);
        return ok;
    }

    public boolean adminSupprimerAbonne(Abonne abonne) {
        boolean ok = SharedService.adminSupprimerAbonne(abonnes, abonne);
        if (ok) {
            persistenceService.saveAccounts(abonnes);
            persistenceService.savePlaylists(abonnes);
        }
        return ok;
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
        return SharedService.noterMorceau(abonne, morceau, note, commentaire);
    }

    public boolean supprimerMaNote(Abonne abonne, Morceau morceau) {
        return SharedService.supprimerNote(abonne, morceau);
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
        boolean ok = SharedService.creerPlaylist(abonne, nom);
        if (ok) persistenceService.savePlaylists(abonnes);
        return ok;
    }

    public List<Playlist> getPlaylists(Abonne abonne) {
        if (abonne == null) {
            return new ArrayList<>();
        }
        return abonne.getPlaylists();
    }

    public boolean ajouterMorceauAPlaylist(Playlist playlist, Morceau morceau) {
        boolean ok = SharedService.ajouterMorceauAPlaylist(playlist, morceau);
        if (ok) persistenceService.savePlaylists(abonnes);
        return ok;
    }

    public List<Morceau> getMorceauxPlaylist(Playlist playlist) {
        if (playlist == null) {
            return new ArrayList<>();
        }
        return playlist.getMorceaux();
    }

    public boolean retirerMorceauDePlaylist(Playlist playlist, Morceau morceau) {
        boolean ok = SharedService.retirerMorceauDePlaylist(playlist, morceau);
        if (ok) persistenceService.savePlaylists(abonnes);
        return ok;
    }

    public boolean renommerPlaylist(Playlist playlist, String nouveauNom) {
        boolean ok = SharedService.renommerPlaylist(playlist, nouveauNom);
        if (ok) persistenceService.savePlaylists(abonnes);
        return ok;
    }

    public boolean supprimerPlaylist(Abonne abonne, Playlist playlist) {
        boolean ok = SharedService.supprimerPlaylist(abonne, playlist);
        if (ok) persistenceService.savePlaylists(abonnes);
        return ok;
    }

    public boolean ecouterMorceau(Abonne abonne, Morceau morceau) {
        boolean ok = (abonne == null)
                ? SharedService.ecouterMorceauInvite(morceau)
                : SharedService.ecouterMorceauAbonne(abonne, morceau);
        if (ok) {
            persistenceService.saveCatalogue(catalogue);
        }
        return ok;
    }

    public void saveAll() {
        persistenceService.saveAll(catalogue, abonnes);
    }
}
