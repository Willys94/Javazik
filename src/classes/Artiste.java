package classes;

public class Artiste implements Interprete {
    String nom;

    public Artiste(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }
}

