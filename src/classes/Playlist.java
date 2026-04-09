package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Playlist {
    String nom;
    List<Morceau> morceaux;
    Abonne proprietaire;

    public Playlist(String nom, Abonne proprietaire) {
        this.nom = nom;
        this.morceaux = new ArrayList<>();
        this.proprietaire = proprietaire;
    }

    public void ajouterMorceau(Morceau morceau) {
        this.morceaux.add(morceau);
    }

    public void retirerMorceau(Morceau morceau) {
        this.morceaux.remove(morceau);
    }

    public String renommer() {
        System.out.println("Saisissez le nouveau nom");
        Scanner clavier = new Scanner(System.in);
        return this.nom = clavier.nextLine();
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "nom='" + nom + '\'' +
                ", morceaux=" + morceaux +
                ", proprietaire=" + proprietaire +
                '}';
    }

}
