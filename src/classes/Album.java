package classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un album musical et ses morceaux.
 */
public class Album {
    private int id;
    private String titre;
    private int annee;
    private List<Morceau> morceaux;
    private Interprete interprete;

    /**
     * Construit un album.
     *
     * @param id identifiant de l'album
     * @param titre titre de l'album
     * @param annee année de sortie
     * @param interprete artiste ou groupe principal
     */
    public Album(int id, String titre, int annee, Interprete interprete) {
        this.id = id;
        this.titre = titre;
        this.annee = annee;
        this.interprete = interprete;
        this.morceaux = new ArrayList<>();
    }

    /**
     * Ajoute un morceau à l'album si absent.
     *
     * @param morceau morceau à ajouter
     */
    public void ajouterMorceau(Morceau morceau) {
        if (morceau != null && !morceaux.contains(morceau)) {
            morceaux.add(morceau);
        }
    }

    /**
     * Retourne l'identifiant de l'album.
     *
     * @return identifiant
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le titre de l'album.
     *
     * @return titre de l'album
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Retourne l'année de sortie.
     *
     * @return année de sortie
     */
    public int getAnnee() {
        return annee;
    }

    /**
     * Retourne les morceaux de l'album.
     *
     * @return liste des morceaux
     */
    public List<Morceau> getMorceaux() {
        return morceaux;
    }

    /**
     * Retourne l'interprète de l'album.
     *
     * @return interprète principal
     */
    public Interprete getInterprete() {
        return interprete;
    }

    /**
     * Modifie le titre de l'album.
     *
     * @param titre nouveau titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Modifie l'année de sortie de l'album.
     *
     * @param annee nouvelle année
     */
    public void setAnnee(int annee) {
        this.annee = annee;
    }

    /**
     * Modifie l'interprète principal de l'album.
     *
     * @param interprete nouvel interprète
     */
    public void setInterprete(Interprete interprete) {
        this.interprete = interprete;
    }

    @Override
    public String toString() {
        int n = morceaux.size();
        String mot = n <= 1 ? "morceau" : "morceaux";
        return String.format("\"%s\" (%d) · %s · %d %s", titre, annee, interprete.getNom(), n, mot);
    }
}