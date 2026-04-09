package classes;

import java.util.ArrayList;
import java.util.List;

public class Album {
    int id;
    String titre;
    int annee;
    List<Morceau> albums = new ArrayList<>();
    String interprete;

    public Album(int id, String titre, int annee, String interprete) {
        this.id = id;
        this.titre = titre;
        this.annee = annee;
        this.albums = new ArrayList<>();
        this.interprete = interprete;
    }

    public void ajouterMorceau(Morceau morceau) {
        this.albums.add(morceau);
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

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public List<Morceau> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Morceau> albums) {
        this.albums = albums;
    }

    public String getInterprete() {
        return interprete;
    }

    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", annee=" + annee +
                ", albums=" + albums +
                '}';
    }
}
