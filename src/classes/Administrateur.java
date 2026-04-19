package classes;

import java.util.List;

/**
 * Représente un compte administrateur.
 * Un administrateur peut modifier le catalogue et gérer les abonnés.
 */
public class Administrateur extends Utilisateurs {

    /**
     * Crée un administrateur.
     *
     * @param id identifiant unique
     * @param login identifiant de connexion
     * @param pw mot de passe
     */
    public Administrateur(int id, String login, String pw) {
        super(id, login, pw);
    }

    /**
     * Ajoute un morceau au catalogue.
     *
     * @param catalogue catalogue cible
     * @param morceau morceau à ajouter
     */
    public void ajouterMorceauCatalogue(Catalogue catalogue, Morceau morceau) {
        if (catalogue != null && morceau != null) {
            catalogue.ajouterMorceau(morceau);
        }
    }

    /**
     * Supprime un morceau du catalogue.
     *
     * @param catalogue catalogue cible
     * @param morceau morceau à supprimer
     */
    public void supprimerMorceauCatalogue(Catalogue catalogue, Morceau morceau) {
        if (catalogue != null && morceau != null) {
            catalogue.supprimerMorceau(morceau);
        }
    }

    /**
     * Ajoute un album au catalogue.
     *
     * @param catalogue catalogue cible
     * @param album album à ajouter
     */
    public void ajouterAlbumCatalogue(Catalogue catalogue, Album album) {
        if (catalogue != null && album != null) {
            catalogue.ajouterAlbum(album);
        }
    }

    /**
     * Supprime un album du catalogue.
     *
     * @param catalogue catalogue cible
     * @param album album à supprimer
     */
    public void supprimerAlbumCatalogue(Catalogue catalogue, Album album) {
        if (catalogue != null && album != null) {
            catalogue.supprimerAlbum(album);
        }
    }

    /**
     * Ajoute un artiste au catalogue.
     *
     * @param catalogue catalogue cible
     * @param artiste artiste à ajouter
     */
    public void ajouterArtisteCatalogue(Catalogue catalogue, Artiste artiste) {
        if (catalogue != null && artiste != null) {
            catalogue.ajouterArtiste(artiste);
        }
    }

    /**
     * Supprime un artiste du catalogue.
     *
     * @param catalogue catalogue cible
     * @param artiste artiste à supprimer
     */
    public void supprimerArtisteCatalogue(Catalogue catalogue, Artiste artiste) {
        if (catalogue != null && artiste != null) {
            catalogue.supprimerArtiste(artiste);
        }
    }

    /**
     * Ajoute un groupe au catalogue.
     *
     * @param catalogue catalogue cible
     * @param groupe groupe à ajouter
     */
    public void ajouterGroupeCatalogue(Catalogue catalogue, Groupe groupe) {
        if (catalogue != null && groupe != null) {
            catalogue.ajouterGroupe(groupe);
        }
    }

    /**
     * Supprime un groupe du catalogue.
     *
     * @param catalogue catalogue cible
     * @param groupe groupe à supprimer
     */
    public void supprimerGroupeCatalogue(Catalogue catalogue, Groupe groupe) {
        if (catalogue != null && groupe != null) {
            catalogue.supprimerGroupe(groupe);
        }
    }

    /**
     * Suspend temporairement un abonné.
     *
     * @param abonne abonné à suspendre
     */
    public void suspendreAbonne(Abonne abonne) {
        if (abonne != null) {
            abonne.suspendre();
        }
    }

    /**
     * Réactive un abonné suspendu.
     *
     * @param abonne abonné à réactiver
     */
    public void reactiverAbonne(Abonne abonne) {
        if (abonne != null) {
            abonne.reactiver();
        }
    }

    /**
     * Supprime un abonné de la liste gérée.
     *
     * @param abonnes liste d'abonnés
     * @param abonne abonné à supprimer
     */
    public void supprimerAbonne(List<Abonne> abonnes, Abonne abonne) {
        if (abonnes != null && abonne != null) {
            abonnes.remove(abonne);
        }
    }

    @Override
    public String toString() {
        return "Administrateur{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}