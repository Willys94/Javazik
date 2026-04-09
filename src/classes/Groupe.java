package classes;

public class Groupe implements Interprete {
    private String nom;

    public Groupe(String nom) {
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