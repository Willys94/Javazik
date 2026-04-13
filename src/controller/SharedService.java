package controller;

import classes.*;

import java.util.List;

/**
 * Service metier partage entre les interfaces console et graphique.
 * Centralise les operations d'ecriture pour garantir un comportement coherent.
 */
public final class SharedService {
    private SharedService() {
    }

    public static boolean creerPlaylist(Abonne abonne, String nom) {
        if (abonne == null || nom == null || nom.trim().isEmpty()) return false;
        abonne.ajouterPlaylist(new Playlist(nom.trim(), abonne));
        return true;
    }

    public static boolean ajouterMorceauAPlaylist(Playlist playlist, Morceau morceau) {
        if (playlist == null || morceau == null) return false;
        playlist.ajouterMorceau(morceau);
        return true;
    }

    public static boolean retirerMorceauDePlaylist(Playlist playlist, Morceau morceau) {
        if (playlist == null || morceau == null) return false;
        playlist.retirerMorceau(morceau);
        return true;
    }

    public static boolean renommerPlaylist(Playlist playlist, String nouveauNom) {
        if (playlist == null || nouveauNom == null || nouveauNom.trim().isEmpty()) return false;
        playlist.renommer(nouveauNom.trim());
        return true;
    }

    public static boolean supprimerPlaylist(Abonne abonne, Playlist playlist) {
        if (abonne == null || playlist == null) return false;
        abonne.supprimerPlaylist(playlist);
        return true;
    }

    public static boolean adminAjouterMorceau(Catalogue catalogue, String titre, int duree, String style, String typeInterprete, String nomInterprete) {
        if (catalogue == null || titre == null || style == null || nomInterprete == null) return false;
        if (titre.trim().isEmpty() || style.trim().isEmpty() || nomInterprete.trim().isEmpty() || duree <= 0) return false;
        Interprete interprete;
        if ("Artiste".equalsIgnoreCase(typeInterprete) || "ARTISTE".equalsIgnoreCase(typeInterprete)) {
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

    public static boolean adminSupprimerMorceau(Catalogue catalogue, Morceau morceau) {
        if (catalogue == null || morceau == null) return false;
        catalogue.supprimerMorceau(morceau);
        return true;
    }

    public static boolean adminAjouterAlbum(Catalogue catalogue, String titre, int annee, String typeInterprete, String nomInterprete) {
        if (catalogue == null || titre == null || nomInterprete == null) return false;
        if (titre.trim().isEmpty() || nomInterprete.trim().isEmpty()) return false;
        Interprete interprete;
        if ("Artiste".equalsIgnoreCase(typeInterprete) || "ARTISTE".equalsIgnoreCase(typeInterprete)) {
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

    public static boolean adminSupprimerAlbum(Catalogue catalogue, Album album) {
        if (catalogue == null || album == null) return false;
        catalogue.supprimerAlbum(album);
        return true;
    }

    public static boolean adminAjouterArtiste(Catalogue catalogue, String nom) {
        if (catalogue == null || nom == null || nom.trim().isEmpty()) return false;
        catalogue.ajouterArtiste(new Artiste(nom.trim()));
        return true;
    }

    public static boolean adminSupprimerArtiste(Catalogue catalogue, Artiste artiste) {
        if (catalogue == null || artiste == null) return false;
        catalogue.supprimerArtiste(artiste);
        return true;
    }

    public static boolean adminAjouterGroupe(Catalogue catalogue, String nom) {
        if (catalogue == null || nom == null || nom.trim().isEmpty()) return false;
        catalogue.ajouterGroupe(new Groupe(nom.trim()));
        return true;
    }

    public static boolean adminSupprimerGroupe(Catalogue catalogue, Groupe groupe) {
        if (catalogue == null || groupe == null) return false;
        catalogue.supprimerGroupe(groupe);
        return true;
    }

    public static boolean adminSuspendreAbonne(Abonne abonne) {
        if (abonne == null) return false;
        abonne.suspendre();
        return true;
    }

    public static boolean adminReactiverAbonne(Abonne abonne) {
        if (abonne == null) return false;
        abonne.reactiver();
        return true;
    }

    public static boolean adminSupprimerAbonne(List<Abonne> abonnes, Abonne abonne) {
        if (abonnes == null || abonne == null) return false;
        abonnes.remove(abonne);
        return true;
    }

    public static boolean ecouterMorceauInvite(Morceau morceau) {
        if (morceau == null) return false;
        morceau.incrementerEcoutes();
        return true;
    }

    public static boolean ecouterMorceauAbonne(Abonne abonne, Morceau morceau) {
        if (abonne == null || morceau == null || abonne.estSuspendu()) return false;
        abonne.ecouterMorceau(morceau);
        return true;
    }

    public static boolean noterMorceau(Abonne abonne, Morceau morceau, int note, String commentaire) {
        if (abonne == null || morceau == null) return false;
        return morceau.ajouterOuModifierAvis(abonne.getLogin(), note, commentaire);
    }

    public static boolean supprimerNote(Abonne abonne, Morceau morceau) {
        if (abonne == null || morceau == null) return false;
        return morceau.supprimerAvis(abonne.getLogin());
    }
}
