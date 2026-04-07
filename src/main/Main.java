package main;

import classes.*;

public class Main {
    public static void main(String[] args) {
        Interprete interprete = new Artiste("noichi");
        Interprete interprete2 = new Groupe("La mifa");
        Abonne abonne = new Abonne(1,"willys","0000");
        Album album = new Album(1,"ding dong",1990);
        Morceau morceau = new Morceau(1,"connard",2,"rock",0,interprete.getNom());
        Morceau morceau2 = new Morceau(2,"connard 2 clem",3,"rock",0,interprete2.getNom());
        Playlist playlist = new Playlist("ching",abonne);
        Catalogue catalogue = new Catalogue();

        album.ajouterMorceau(morceau);
        album.ajouterMorceau(morceau2);
        catalogue.ajouterAlbum(album);
        catalogue.ajouterMorceau(morceau2);
        System.out.println(album);
        System.out.println("\n-------------------------\n");
        System.out.println(catalogue);


    }
}