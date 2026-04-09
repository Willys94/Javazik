package classes;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String nom;
    private List<Morceau> morceaux;
    private Abonne proprietaire;

    public Playlist(String nom, Abonne proprietaire) {
        this.nom = nom;
        this.proprietaire = proprietaire;
        this.morceaux = new ArrayList<>();
    }

    public void ajouterMorceau(Morceau morceau) {
        if (morceau != null && !morceaux.contains(morceau)) {
            morceaux.add(morceau);
        }
    }

    public void retirerMorceau(Morceau morceau) {
        morceaux.remove(morceau);
    }

    public void renommer(String nouveauNom) {
        this.nom = nouveauNom;
    }

    public String getNom() {
        return nom;
    }

    public List<Morceau> getMorceaux() {
        return morceaux;
    }

    public Abonne getProprietaire() {
        return proprietaire;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "nom='" + nom + '\'' +
                ", nbMorceaux=" + morceaux.size() +
                '}';
    }
}