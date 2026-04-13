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

    public PersistenceService(String cheminAbonnes, String cheminArtistes, String cheminGroupes,
                              String cheminMorceaux, String cheminAlbums, String cheminPlaylists) {
        this.cheminAbonnes = cheminAbonnes;
        this.cheminArtistes = cheminArtistes;
        this.cheminGroupes = cheminGroupes;
        this.cheminMorceaux = cheminMorceaux;
        this.cheminAlbums = cheminAlbums;
        this.cheminPlaylists = cheminPlaylists;
    }

    public void savePlaylists(List<Abonne> abonnes) {
        GestionFichier.sauvegarderPlaylists(cheminPlaylists, abonnes);
    }

    public void saveCatalogue(Catalogue catalogue) {
        GestionFichier.sauvegarderArtistes(cheminArtistes, catalogue.getArtistes());
        GestionFichier.sauvegarderGroupes(cheminGroupes, catalogue.getGroupes());
        GestionFichier.sauvegarderMorceaux(cheminMorceaux, catalogue.getMorceaux());
        GestionFichier.sauvegarderAlbums(cheminAlbums, catalogue.getAlbums());
    }

    public void saveAccounts(List<Abonne> abonnes) {
        GestionFichier.sauvegarderAbonnes(cheminAbonnes, abonnes);
    }

    public void saveAll(Catalogue catalogue, List<Abonne> abonnes) {
        saveAccounts(abonnes);
        saveCatalogue(catalogue);
        savePlaylists(abonnes);
    }
}
