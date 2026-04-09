package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Catalogue {
    List<Morceau> morceau;
    List<Album>  album;

    public Catalogue() {
        this.morceau = new ArrayList<>();
        this.album = new ArrayList<>();

    }


    public void ajouterAlbum(Album album){
        this.album.add(album);
    }

    public List<Morceau> getMorceau() {
        return morceau;
    }

    public void setMorceau(List<Morceau> morceau) {
        this.morceau = morceau;
    }

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

    public void ajouterMorceauCatalogue(Catalogue catalogue){
        Scanner clavier = new Scanner(System.in);
        System.out.println("Entrez le titre du morceau");
        String titre = clavier.nextLine();
        System.out.println("Entrez la durée du morceau");
        int duree = clavier.nextInt();
        clavier.nextLine();
        System.out.println("Entrez le style du morceau");
        String style = clavier.nextLine();
        System.out.println("Entrez l'interprete du morceau");
        String interprete = clavier.nextLine();
        catalogue.morceau.add(new Morceau(catalogue.getMorceau().getLast().getId()+1,titre,duree,style,0,interprete));
    }

    public void ajouterAlbumCatalogue(Catalogue catalogue){
        Scanner clavier = new Scanner(System.in);
        System.out.println("Entrez le titre de l'album");
        String titreAlbum = clavier.nextLine();
        System.out.println("Entrez l'année de l'album");
        int annee = clavier.nextInt();
        System.out.println("Entrez le nom de l'interprete");
        String interpreteAlbum = clavier.nextLine();
        catalogue.album.add(new Album(catalogue.getAlbum().getLast().getId()+1,titreAlbum,annee,interpreteAlbum));
    }

    @Override
    public String toString() {
        return "Catalogue{" +
                "morceau=" + morceau +
                ", album=" + album +
                '}';
    }
}
