package classes;

import java.util.Scanner;

public class Morceau {
    int id;
    String titre;
    int duree;
    String style;
    int nbEcoutes;
    String interprete;

    public Morceau(int id, String titre, int duree, String style, int nbEcoutes, String interprete) {
        this.id = id;
        this.titre = titre;
        this.style = style;
        this.duree = duree;
        this.nbEcoutes = 0;
        this.style = style;
        this.interprete = interprete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getNbEcoutes() {
        return nbEcoutes;
    }

    public void setNbEcoutes(int nbEcoutes) {
        this.nbEcoutes = nbEcoutes;
    }



    public String toString(){
        return "Morceau: " + titre +
                " | Genre: " + style +
                " | Durée: " + duree +
                " | Interprete: " + interprete;
    }

    public void incrementerEcoutes(){
        this.nbEcoutes++;
    }
}
