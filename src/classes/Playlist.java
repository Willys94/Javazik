package classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Represente une playlist personnelle d'un abonne.
 */
public class Playlist {
    private String nom;
    private List<Morceau> morceaux;
    private Abonne proprietaire;

    /**
     * Cree une playlist vide associee a son proprietaire.
     *
     * @param nom nom de la playlist
     * @param proprietaire abonne proprietaire
     */
    public Playlist(String nom, Abonne proprietaire) {
        this.nom = nom;
        this.proprietaire = proprietaire;
        this.morceaux = new ArrayList<>();
    }

    /**
     * Ajoute un morceau sans doublon.
     *
     * @param morceau morceau a ajouter
     */
    public void ajouterMorceau(Morceau morceau) {
        if (morceau != null && !morceaux.contains(morceau)) {
            morceaux.add(morceau);
        }
    }

    /**
     * Retire un morceau de la playlist.
     *
     * @param morceau morceau à retirer
     */
    public void retirerMorceau(Morceau morceau) {
        morceaux.remove(morceau);
    }

    /**
     * Renomme la playlist.
     *
     * @param nouveauNom nouveau nom
     */
    public void renommer(String nouveauNom) {
        this.nom = nouveauNom;
    }

    /**
     * Retourne le nom de la playlist.
     *
     * @return nom de la playlist
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne les morceaux de la playlist.
     *
     * @return liste des morceaux
     */
    public List<Morceau> getMorceaux() {
        return morceaux;
    }

    /**
     * Retourne le propriétaire de la playlist.
     *
     * @return abonné propriétaire
     */
    public Abonne getProprietaire() {
        return proprietaire;
    }

    @Override
    public String toString() {
        int n = morceaux.size();
        String mot = n <= 1 ? "morceau" : "morceaux";
        return String.format("%s · %d %s", nom, n, mot);
    }
}