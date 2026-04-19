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

    /**
     * Crée une playlist pour un abonné.
     *
     * @param abonne abonné propriétaire
     * @param nom nom de la playlist
     * @return {@code true} si les paramètres sont valides
     */
    public static boolean creerPlaylist(Abonne abonne, String nom) {
        if (abonne == null || nom == null || nom.trim().isEmpty()) return false;
        abonne.ajouterPlaylist(new Playlist(nom.trim(), abonne));
        return true;
    }

    /**
     * Ajoute un morceau à une playlist.
     *
     * @param playlist playlist cible
     * @param morceau morceau à ajouter
     * @return {@code true} si les paramètres sont valides
     */
    public static boolean ajouterMorceauAPlaylist(Playlist playlist, Morceau morceau) {
        if (playlist == null || morceau == null) return false;
        playlist.ajouterMorceau(morceau);
        return true;
    }

    /**
     * Retire un morceau d'une playlist.
     *
     * @param playlist playlist cible
     * @param morceau morceau à retirer
     * @return {@code true} si les paramètres sont valides
     */
    public static boolean retirerMorceauDePlaylist(Playlist playlist, Morceau morceau) {
        if (playlist == null || morceau == null) return false;
        playlist.retirerMorceau(morceau);
        return true;
    }

    /**
     * Renomme une playlist existante.
     *
     * @param playlist playlist à renommer
     * @param nouveauNom nouveau nom
     * @return {@code true} si les paramètres sont valides
     */
    public static boolean renommerPlaylist(Playlist playlist, String nouveauNom) {
        if (playlist == null || nouveauNom == null || nouveauNom.trim().isEmpty()) return false;
        playlist.renommer(nouveauNom.trim());
        return true;
    }

    /**
     * Supprime une playlist d'un abonné.
     *
     * @param abonne propriétaire de la playlist
     * @param playlist playlist à supprimer
     * @return {@code true} si les paramètres sont valides
     */
    public static boolean supprimerPlaylist(Abonne abonne, Playlist playlist) {
        if (abonne == null || playlist == null) return false;
        abonne.supprimerPlaylist(playlist);
        return true;
    }

    /**
     * Ajoute un morceau au catalogue en tant qu'administrateur.
     *
     * @param catalogue catalogue cible
     * @param titre titre du morceau
     * @param duree durée en secondes
     * @param style style musical
     * @param typeInterprete type d'interprète attendu
     * @param nomInterprete nom de l'interprète
     * @return {@code true} si le morceau a été ajouté
     */
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

    /**
     * Supprime un morceau du catalogue.
     *
     * @param catalogue catalogue cible
     * @param morceau morceau à supprimer
     * @return {@code true} si les paramètres sont valides
     */
    public static boolean adminSupprimerMorceau(Catalogue catalogue, Morceau morceau) {
        if (catalogue == null || morceau == null) return false;
        catalogue.supprimerMorceau(morceau);
        return true;
    }

    /**
     * Ajoute un album au catalogue.
     *
     * @param catalogue catalogue cible
     * @param titre titre de l'album
     * @param annee année de sortie
     * @param typeInterprete type d'interprète attendu
     * @param nomInterprete nom de l'interprète
     * @return {@code true} si l'album a été ajouté
     */
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

    /**
     * Supprime un album du catalogue.
     *
     * @param catalogue catalogue cible
     * @param album album à supprimer
     * @return {@code true} si les paramètres sont valides
     */
    public static boolean adminSupprimerAlbum(Catalogue catalogue, Album album) {
        if (catalogue == null || album == null) return false;
        catalogue.supprimerAlbum(album);
        return true;
    }

    /**
     * Ajoute un artiste au catalogue.
     *
     * @param catalogue catalogue cible
     * @param nom nom de l'artiste
     * @return {@code true} si les paramètres sont valides
     */
    public static boolean adminAjouterArtiste(Catalogue catalogue, String nom) {
        if (catalogue == null || nom == null || nom.trim().isEmpty()) return false;
        catalogue.ajouterArtiste(new Artiste(nom.trim()));
        return true;
    }

    /**
     * Supprime un artiste du catalogue.
     *
     * @param catalogue catalogue cible
     * @param artiste artiste à supprimer
     * @return {@code true} si les paramètres sont valides
     */
    public static boolean adminSupprimerArtiste(Catalogue catalogue, Artiste artiste) {
        if (catalogue == null || artiste == null) return false;
        catalogue.supprimerArtiste(artiste);
        return true;
    }

    /**
     * Ajoute un groupe au catalogue.
     *
     * @param catalogue catalogue cible
     * @param nom nom du groupe
     * @return {@code true} si les paramètres sont valides
     */
    public static boolean adminAjouterGroupe(Catalogue catalogue, String nom) {
        if (catalogue == null || nom == null || nom.trim().isEmpty()) return false;
        catalogue.ajouterGroupe(new Groupe(nom.trim()));
        return true;
    }

    /**
     * Supprime un groupe du catalogue.
     *
     * @param catalogue catalogue cible
     * @param groupe groupe à supprimer
     * @return {@code true} si les paramètres sont valides
     */
    public static boolean adminSupprimerGroupe(Catalogue catalogue, Groupe groupe) {
        if (catalogue == null || groupe == null) return false;
        catalogue.supprimerGroupe(groupe);
        return true;
    }

    /**
     * Suspend un abonné.
     *
     * @param abonne abonné cible
     * @return {@code true} si l'abonné est valide
     */
    public static boolean adminSuspendreAbonne(Abonne abonne) {
        if (abonne == null) return false;
        abonne.suspendre();
        return true;
    }

    /**
     * Réactive un abonné.
     *
     * @param abonne abonné cible
     * @return {@code true} si l'abonné est valide
     */
    public static boolean adminReactiverAbonne(Abonne abonne) {
        if (abonne == null) return false;
        abonne.reactiver();
        return true;
    }

    /**
     * Supprime un abonné d'une collection.
     *
     * @param abonnes collection d'abonnés
     * @param abonne abonné à retirer
     * @return {@code true} si les paramètres sont valides
     */
    public static boolean adminSupprimerAbonne(List<Abonne> abonnes, Abonne abonne) {
        if (abonnes == null || abonne == null) return false;
        abonnes.remove(abonne);
        return true;
    }

    /**
     * Enregistre une écoute en mode invité.
     *
     * @param morceau morceau écouté
     * @return {@code true} si le morceau est valide
     */
    public static boolean ecouterMorceauInvite(Morceau morceau) {
        if (morceau == null) return false;
        morceau.incrementerEcoutes();
        return true;
    }

    /**
     * Enregistre une écoute pour un abonné actif.
     *
     * @param abonne abonné courant
     * @param morceau morceau écouté
     * @return {@code true} si l'écoute peut être enregistrée
     */
    public static boolean ecouterMorceauAbonne(Abonne abonne, Morceau morceau) {
        if (abonne == null || morceau == null || abonne.estSuspendu()) return false;
        abonne.ecouterMorceau(morceau);
        return true;
    }

    /**
     * Ajoute ou modifie la note d'un abonné sur un morceau.
     *
     * @param abonne abonné auteur
     * @param morceau morceau noté
     * @param note note entre 1 et 5
     * @param commentaire commentaire optionnel
     * @return {@code true} si la note est valide
     */
    public static boolean noterMorceau(Abonne abonne, Morceau morceau, int note, String commentaire) {
        if (abonne == null || morceau == null) return false;
        return morceau.ajouterOuModifierAvis(abonne.getLogin(), note, commentaire);
    }

    /**
     * Supprime la note d'un abonné sur un morceau.
     *
     * @param abonne abonné auteur
     * @param morceau morceau noté
     * @return {@code true} si une note existait
     */
    public static boolean supprimerNote(Abonne abonne, Morceau morceau) {
        if (abonne == null || morceau == null) return false;
        return morceau.supprimerAvis(abonne.getLogin());
    }
}
