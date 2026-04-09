package classes;

public class Morceau {
    private int id;
    private String titre;
    private int duree;
    private String style;
    private int nbEcoutes;
    private Interprete interprete;

    public Morceau(int id, String titre, int duree, String style, int nbEcoutes, Interprete interprete) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.style = style;
        this.nbEcoutes = nbEcoutes;
        this.interprete = interprete;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public int getDuree() {
        return duree;
    }

    public String getStyle() {
        return style;
    }

    public int getNbEcoutes() {
        return nbEcoutes;
    }

    public Interprete getInterprete() {
        return interprete;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setInterprete(Interprete interprete) {
        this.interprete = interprete;
    }

    public void incrementerEcoutes() {
        this.nbEcoutes++;
    }

    @Override
    public String toString() {
        return "Morceau{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", duree=" + duree +
                ", style='" + style + '\'' +
                ", nbEcoutes=" + nbEcoutes +
                ", interprete=" + interprete.getNom() +
                '}';
    }
}