package classes;

import java.util.ArrayList;
import java.util.List;

public class Abonne extends Utilisateurs {

    List<Playlist> playlist;
    List<Morceau> historique;

    public Abonne(int id,String login,String pw ) {
        this.playlist = new ArrayList<>();
        this.historique = new ArrayList<>();
        super(id,login,pw);
    }

    public void creerPlaylist(){

    }

    @Override
    public String toString() {
        return "Abonne{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", pw='" + pw + '\'' +
                '}';
    }
}
