package classes;

/**
 * Représente un groupe musical.
 */
public class Groupe implements Interprete {
    private String nom;

    /**
     * Crée un groupe.
     *
     * @param nom nom du groupe
     */
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