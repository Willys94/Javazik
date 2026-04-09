package classes;

public class Artiste implements Interprete {
    private String nom;

    public Artiste(String nom) {
        this.nom = nom;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}