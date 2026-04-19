package classes;

/**
 * Représente un artiste solo.
 */
public class Artiste implements Interprete {
    private String nom;

    /**
     * Crée un artiste.
     *
     * @param nom nom de l'artiste
     */
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