package controller;

import classes.*;

import java.util.List;

/**
 * Orchestre tous les points de sauvegarde de l'application.
 */
public class PersistenceService {
    private final String cheminAbonnes;
    private final String cheminArtistes;
    private final String cheminGroupes;
    private final String cheminMorceaux;
    private final String cheminAlbums;
    private final String cheminPlaylists;

    /**
     * Crée le service de persistance avec les chemins de fichiers.
     *
     * @param cheminAbonnes chemin des abonnés
     * @param cheminArtistes chemin des artistes
     * @param cheminGroupes chemin des groupes
     * @param cheminMorceaux chemin des morceaux
     * @param cheminAlbums chemin des albums
     * @param cheminPlaylists chemin des playlists
     */
    public PersistenceService(String cheminAbonnes, String cheminArtistes, String cheminGroupes,
                              String cheminMorceaux, String cheminAlbums, String cheminPlaylists) {
        this.cheminAbonnes = cheminAbonnes;
        this.cheminArtistes = cheminArtistes;
        this.cheminGroupes = cheminGroupes;
        this.cheminMorceaux = cheminMorceaux;
        this.cheminAlbums = cheminAlbums;
        this.cheminPlaylists = cheminPlaylists;
    }

    /**
     * Sauvegarde les playlists.
     *
     * @param abonnes abonnés possédant les playlists
     */
    public void savePlaylists(List<Abonne> abonnes) {
        GestionFichier.sauvegarderPlaylists(cheminPlaylists, abonnes);
    }

    /**
     * Sauvegarde le catalogue.
     *
     * @param catalogue catalogue à persister
     */
    public void saveCatalogue(Catalogue catalogue) {
        GestionFichier.sauvegarderArtistes(cheminArtistes, catalogue.getArtistes());
        GestionFichier.sauvegarderGroupes(cheminGroupes, catalogue.getGroupes());
        GestionFichier.sauvegarderMorceaux(cheminMorceaux, catalogue.getMorceaux());
        GestionFichier.sauvegarderAlbums(cheminAlbums, catalogue.getAlbums());
    }

    /**
     * Sauvegarde les comptes abonnés.
     *
     * @param abonnes comptes abonnés à persister
     */
    public void saveAccounts(List<Abonne> abonnes) {
        GestionFichier.sauvegarderAbonnes(cheminAbonnes, abonnes);
    }

    /**
     * Sauvegarde l'ensemble des données.
     *
     * @param catalogue catalogue courant
     * @param abonnes abonnés courants
     */
    public void saveAll(Catalogue catalogue, List<Abonne> abonnes) {
        saveAccounts(abonnes);
        saveCatalogue(catalogue);
        savePlaylists(abonnes);
    }
}
