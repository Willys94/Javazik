package classes;

public class Groupe implements Interprete {
    String nom;

    public Groupe(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }
}
