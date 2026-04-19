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
     * @param persistenceService service de persistance
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

    /**
     * Crée un compte abonné puis sauvegarde les comptes.
     *
     * @param login login souhaité
     * @param motDePasse mot de passe souhaité
     * @return abonné créé, ou {@code null} en cas d'échec
     */
    public Abonne creerCompte(String login, String motDePasse) {
        Abonne abonne = authService.creerCompte(login, motDePasse, abonnes);
        if (abonne != null) {
            persistenceService.saveAccounts(abonnes);
        }
        return abonne;
    }

    /**
     * Recherche des morceaux par titre.
     *
     * @param titre titre à rechercher
     * @return morceaux correspondant au titre recherché
     */
    public List<Morceau> rechercherMorceauxParTitre(String titre) {
        return catalogue.rechercherMorceauxParTitre(titre);
    }

    /**
     * Recherche des morceaux par style.
     *
     * @param style style à rechercher
     * @return morceaux correspondant au style recherché
     */
    public List<Morceau> rechercherMorceauxParStyle(String style) {
        return catalogue.rechercherMorceauxParStyle(style);
    }

    /**
     * Recherche des albums par titre.
     *
     * @param titre titre à rechercher
     * @return albums correspondant au titre recherché
     */
    public List<Album> rechercherAlbumsParTitre(String titre) {
        return catalogue.rechercherAlbumsParTitre(titre);
    }

    /**
     * Recherche des artistes par nom.
     *
     * @param nom nom à rechercher
     * @return artistes correspondant au nom recherché
     */
    public List<Artiste> rechercherArtistesParNom(String nom) {
        return catalogue.rechercherArtistesParNom(nom);
    }

    /**
     * Recherche des groupes par nom.
     *
     * @param nom nom à rechercher
     * @return groupes correspondant au nom recherché
     */
    public List<Groupe> rechercherGroupesParNom(String nom) {
        return catalogue.rechercherGroupesParNom(nom);
    }

    /**
     * Retourne les morceaux d'un artiste.
     *
     * @param nomArtiste nom de l'artiste
     * @return morceaux de l'artiste fourni
     */
    public List<Morceau> getMorceauxParArtiste(String nomArtiste) {
        return catalogue.getMorceauxParArtiste(nomArtiste);
    }

    /**
     * Retourne les albums d'un artiste.
     *
     * @param nomArtiste nom de l'artiste
     * @return albums de l'artiste fourni
     */
    public List<Album> getAlbumsParArtiste(String nomArtiste) {
        return catalogue.getAlbumsParArtiste(nomArtiste);
    }

    /**
     * Retourne les morceaux d'un groupe.
     *
     * @param nomGroupe nom du groupe
     * @return morceaux du groupe fourni
     */
    public List<Morceau> getMorceauxParGroupe(String nomGroupe) {
        return catalogue.getMorceauxParGroupe(nomGroupe);
    }

    /**
     * Retourne les albums d'un groupe.
     *
     * @param nomGroupe nom du groupe
     * @return albums du groupe fourni
     */
    public List<Album> getAlbumsParGroupe(String nomGroupe) {
        return catalogue.getAlbumsParGroupe(nomGroupe);
    }

    /**
     * Retourne les morceaux triés par titre.
     *
     * @return morceaux triés par titre
     */
    public List<Morceau> morceauxTriesParTitre() {
        return catalogue.getMorceauxTriesParTitre();
    }

    /**
     * Retourne les morceaux triés par durée.
     *
     * @return morceaux triés par durée
     */
    public List<Morceau> morceauxTriesParDuree() {
        return catalogue.getMorceauxTriesParDuree();
    }

    /**
     * Retourne les morceaux triés par popularité.
     *
     * @return morceaux triés par nombre d'écoutes
     */
    public List<Morceau> morceauxTriesParEcoutes() {
        return catalogue.getMorceauxTriesParEcoutes();
    }

    /**
     * Retourne tous les morceaux du catalogue.
     *
     * @return tous les morceaux du catalogue
     */
    public List<Morceau> getMorceaux() {
        return catalogue.getMorceaux();
    }

    /**
     * Retourne tous les albums du catalogue.
     *
     * @return tous les albums du catalogue
     */
    public List<Album> getAlbums() {
        return catalogue.getAlbums();
    }

    /**
     * Retourne tous les artistes du catalogue.
     *
     * @return tous les artistes du catalogue
     */
    public List<Artiste> getArtistes() {
        return catalogue.getArtistes();
    }

    /**
     * Retourne tous les groupes du catalogue.
     *
     * @return tous les groupes du catalogue
     */
    public List<Groupe> getGroupes() {
        return catalogue.getGroupes();
    }

    /**
     * Retourne la liste complète des abonnés.
     *
     * @return liste complète des abonnés
     */
    public List<Abonne> getAbonnes() {
        return abonnes;
    }

    /**
     * Ajoute un morceau au catalogue via les actions administrateur.
     *
     * @param titre titre du morceau
     * @param duree durée en secondes
     * @param style style musical
     * @param typeInterprete type de l'interprète
     * @param nomInterprete nom de l'interprète
     * @return {@code true} si le morceau a été ajouté
     */
    public boolean adminAjouterMorceau(String titre, int duree, String style, String typeInterprete, String nomInterprete) {
        boolean ok = SharedService.adminAjouterMorceau(catalogue, titre, duree, style, typeInterprete, nomInterprete);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    /**
     * Supprime un morceau du catalogue via les actions administrateur.
     *
     * @param morceau morceau à supprimer
     * @return {@code true} si le morceau a été supprimé
     */
    public boolean adminSupprimerMorceau(Morceau morceau) {
        boolean ok = SharedService.adminSupprimerMorceau(catalogue, morceau);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    /**
     * Ajoute un album au catalogue via les actions administrateur.
     *
     * @param titre titre de l'album
     * @param annee année de sortie
     * @param typeInterprete type de l'interprète
     * @param nomInterprete nom de l'interprète
     * @return {@code true} si l'album a été ajouté
     */
    public boolean adminAjouterAlbum(String titre, int annee, String typeInterprete, String nomInterprete) {
        boolean ok = SharedService.adminAjouterAlbum(catalogue, titre, annee, typeInterprete, nomInterprete);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    /**
     * Supprime un album du catalogue via les actions administrateur.
     *
     * @param album album à supprimer
     * @return {@code true} si l'album a été supprimé
     */
    public boolean adminSupprimerAlbum(Album album) {
        boolean ok = SharedService.adminSupprimerAlbum(catalogue, album);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    /**
     * Ajoute un artiste au catalogue via les actions administrateur.
     *
     * @param nom nom de l'artiste
     * @return {@code true} si l'artiste a été ajouté
     */
    public boolean adminAjouterArtiste(String nom) {
        boolean ok = SharedService.adminAjouterArtiste(catalogue, nom);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    /**
     * Supprime un artiste du catalogue via les actions administrateur.
     *
     * @param artiste artiste à supprimer
     * @return {@code true} si l'artiste a été supprimé
     */
    public boolean adminSupprimerArtiste(Artiste artiste) {
        boolean ok = SharedService.adminSupprimerArtiste(catalogue, artiste);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    /**
     * Ajoute un groupe au catalogue via les actions administrateur.
     *
     * @param nom nom du groupe
     * @return {@code true} si le groupe a été ajouté
     */
    public boolean adminAjouterGroupe(String nom) {
        boolean ok = SharedService.adminAjouterGroupe(catalogue, nom);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    /**
     * Supprime un groupe du catalogue via les actions administrateur.
     *
     * @param groupe groupe à supprimer
     * @return {@code true} si le groupe a été supprimé
     */
    public boolean adminSupprimerGroupe(Groupe groupe) {
        boolean ok = SharedService.adminSupprimerGroupe(catalogue, groupe);
        if (ok) persistenceService.saveCatalogue(catalogue);
        return ok;
    }

    /**
     * Suspend un abonné.
     *
     * @param abonne abonné cible
     * @return {@code true} si l'abonné a été suspendu
     */
    public boolean adminSuspendreAbonne(Abonne abonne) {
        boolean ok = SharedService.adminSuspendreAbonne(abonne);
        if (ok) persistenceService.saveAccounts(abonnes);
        return ok;
    }

    /**
     * Réactive un abonné suspendu.
     *
     * @param abonne abonné cible
     * @return {@code true} si l'abonné a été réactivé
     */
    public boolean adminReactiverAbonne(Abonne abonne) {
        boolean ok = SharedService.adminReactiverAbonne(abonne);
        if (ok) persistenceService.saveAccounts(abonnes);
        return ok;
    }

    /**
     * Supprime un abonné.
     *
     * @param abonne abonné à supprimer
     * @return {@code true} si l'abonné a été supprimé
     */
    public boolean adminSupprimerAbonne(Abonne abonne) {
        boolean ok = SharedService.adminSupprimerAbonne(abonnes, abonne);
        if (ok) {
            persistenceService.saveAccounts(abonnes);
            persistenceService.savePlaylists(abonnes);
        }
        return ok;
    }

    /**
     * Retourne un résumé des statistiques administrateur.
     *
     * @return texte multi-ligne de synthèse
     */
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

    /**
     * Supprime la note de l'abonné sur un morceau.
     *
     * @param abonne abonné auteur
     * @param morceau morceau cible
     * @return {@code true} si une note existait et a été supprimée
     */
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

    /**
     * Crée une playlist pour un abonné.
     *
     * @param abonne abonné propriétaire
     * @param nom nom de playlist
     * @return {@code true} si la création est valide
     */
    public boolean creerPlaylist(Abonne abonne, String nom) {
        boolean ok = SharedService.creerPlaylist(abonne, nom);
        if (ok) persistenceService.savePlaylists(abonnes);
        return ok;
    }

    /**
     * Retourne les playlists d'un abonné.
     *
     * @param abonne abonné cible
     * @return playlists de l'abonné, ou liste vide si abonné nul
     */
    public List<Playlist> getPlaylists(Abonne abonne) {
        if (abonne == null) {
            return new ArrayList<>();
        }
        return abonne.getPlaylists();
    }

    /**
     * Ajoute un morceau dans une playlist.
     *
     * @param playlist playlist cible
     * @param morceau morceau à ajouter
     * @return {@code true} si l'opération est valide
     */
    public boolean ajouterMorceauAPlaylist(Playlist playlist, Morceau morceau) {
        boolean ok = SharedService.ajouterMorceauAPlaylist(playlist, morceau);
        if (ok) persistenceService.savePlaylists(abonnes);
        return ok;
    }

    /**
     * Retourne les morceaux d'une playlist.
     *
     * @param playlist playlist cible
     * @return morceaux de la playlist, ou liste vide si playlist nulle
     */
    public List<Morceau> getMorceauxPlaylist(Playlist playlist) {
        if (playlist == null) {
            return new ArrayList<>();
        }
        return playlist.getMorceaux();
    }

    /**
     * Retire un morceau d'une playlist.
     *
     * @param playlist playlist cible
     * @param morceau morceau à retirer
     * @return {@code true} si l'opération est valide
     */
    public boolean retirerMorceauDePlaylist(Playlist playlist, Morceau morceau) {
        boolean ok = SharedService.retirerMorceauDePlaylist(playlist, morceau);
        if (ok) persistenceService.savePlaylists(abonnes);
        return ok;
    }

    /**
     * Renomme une playlist.
     *
     * @param playlist playlist à renommer
     * @param nouveauNom nouveau nom
     * @return {@code true} si le nouveau nom est valide
     */
    public boolean renommerPlaylist(Playlist playlist, String nouveauNom) {
        boolean ok = SharedService.renommerPlaylist(playlist, nouveauNom);
        if (ok) persistenceService.savePlaylists(abonnes);
        return ok;
    }

    /**
     * Supprime une playlist appartenant à un abonné.
     *
     * @param abonne propriétaire
     * @param playlist playlist à supprimer
     * @return {@code true} si l'opération est valide
     */
    public boolean supprimerPlaylist(Abonne abonne, Playlist playlist) {
        boolean ok = SharedService.supprimerPlaylist(abonne, playlist);
        if (ok) persistenceService.savePlaylists(abonnes);
        return ok;
    }

    /**
     * Lance l'écoute d'un morceau en mode invité ou abonné.
     *
     * @param abonne abonné courant, ou {@code null} en mode invité
     * @param morceau morceau à écouter
     * @return {@code true} si l'écoute a été enregistrée
     */
    public boolean ecouterMorceau(Abonne abonne, Morceau morceau) {
        boolean ok = (abonne == null)
                ? SharedService.ecouterMorceauInvite(morceau)
                : SharedService.ecouterMorceauAbonne(abonne, morceau);
        if (ok) {
            persistenceService.saveCatalogue(catalogue);
        }
        return ok;
    }

    /**
     * Sauvegarde l'ensemble des données persistantes.
     */
    public void saveAll() {
        persistenceService.saveAll(catalogue, abonnes);
    }
}
