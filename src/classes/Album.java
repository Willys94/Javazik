package classes;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private int id;
    private String titre;
    private int annee;
    private List<Morceau> morceaux;
    private Interprete interprete;

    public Album(int id, String titre, int annee, Interprete interprete) {
        this.id = id;
        this.titre = titre;
        this.annee = annee;
        this.interprete = interprete;
        this.morceaux = new ArrayList<>();
    }

    public void ajouterMorceau(Morceau morceau) {
        if (morceau != null && !morceaux.contains(morceau)) {
            morceaux.add(morceau);
        }
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public int getAnnee() {
        return annee;
    }

    public List<Morceau> getMorceaux() {
        return morceaux;
    }

    public Interprete getInterprete() {
        return interprete;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

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