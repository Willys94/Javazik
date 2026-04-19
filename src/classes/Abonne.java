package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represente un utilisateur abonne.
 * Un abonne dispose de playlists, d'un historique et d'un statut de suspension.
 */
public class Abonne extends Utilisateurs {
    private List<Playlist> playlists;
    private List<Morceau> historique;
    private Map<Morceau, Integer> nbEcoutesParMorceau;
    private boolean suspendu;

    /**
     * Cree un nouvel abonne actif.
     *
     * @param id identifiant unique
     * @param login login de connexion
     * @param pw mot de passe
     */
    public Abonne(int id, String login, String pw) {
        super(id, login, pw);
        this.playlists = new ArrayList<>();
        this.historique = new ArrayList<>();
        this.nbEcoutesParMorceau = new HashMap<>();
        this.suspendu = false;
    }

    /**
     * Ajoute une playlist à l'abonné si elle n'existe pas déjà.
     *
     * @param playlist playlist à ajouter
     */
    public void ajouterPlaylist(Playlist playlist) {
        if (playlist != null && !playlists.contains(playlist)) {
            playlists.add(playlist);
        }
    }

    /**
     * Supprime une playlist de l'abonné.
     *
     * @param playlist playlist à retirer
     */
    public void supprimerPlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }

    /**
     * Retourne les playlists de l'abonné.
     *
     * @return liste des playlists
     */
    public List<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * Enregistre l'ecoute d'un morceau dans l'historique et met a jour les compteurs.
     *
     * @param morceau morceau ecoute
     */
    public void ecouterMorceau(Morceau morceau) {
        if (morceau != null) {
            historique.add(morceau);
            nbEcoutesParMorceau.put(morceau, nbEcoutesParMorceau.getOrDefault(morceau, 0) + 1);
            morceau.incrementerEcoutes();
        }
    }

    /**
     * Retourne l'historique d'écoute de l'abonné.
     *
     * @return liste chronologique des morceaux écoutés
     */
    public List<Morceau> getHistorique() {
        return historique;
    }

    /**
     * Retourne le nombre d'écoutes d'un morceau par cet abonné.
     *
     * @param morceau morceau à consulter
     * @return nombre d'écoutes enregistrées
     */
    public int getNbEcoutesMorceau(Morceau morceau) {
        return nbEcoutesParMorceau.getOrDefault(morceau, 0);
    }

    /**
     * Indique si l'abonné est suspendu.
     *
     * @return {@code true} si suspendu, sinon {@code false}
     */
    public boolean estSuspendu() {
        return suspendu;
    }

    /**
     * Met l'abonné en état suspendu.
     */
    public void suspendre() {
        this.suspendu = true;
    }

    /**
     * Retire l'état suspendu de l'abonné.
     */
    public void reactiver() {
        this.suspendu = false;
    }

    @Override
    public String toString() {
        return "Abonne{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", suspendu=" + suspendu +
                ", nbPlaylists=" + playlists.size() +
                '}';
    }
}