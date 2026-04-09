package main;

import classes.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner clavier = new Scanner(System.in);
        Catalogue catalogue = new Catalogue();
        Abonne abonne = new Abonne(1,"f","pw");
        Interprete interprete1 = new Artiste("noichi");
        Morceau morceau = new Morceau(1,"connard",2,"rock",0,interprete1.getNom());
        Album album = new Album(1,"ding dong",1990,"tintin");

        System.out.println("Que voulez vous faire: \n1. Ajouter un morceau au catalogue\n2. Ajouter un album au catalogue\n3. Creer une playlist\n4. Ajouter un morceau à une playlist\n5. Ajouter un morceau à un album");
        switch (clavier.nextInt()){
            case 1:
                catalogue.ajouterMorceauCatalogue(catalogue);
                break;
            case 2:
               catalogue.ajouterAlbumCatalogue(catalogue);
                break;
            case 3:
        }





//        Interprete interprete2 = new Groupe("La mifa");
//        Abonne abonne = new Abonne(1,"willys","0000");
//
//        Morceau morceau2 = new Morceau(2,"connard 2 clem",3,"rock",0,interprete2.getNom());
//        Playlist playlist = new Playlist("ching",abonne);
//
//
//        album.ajouterMorceau(morceau);
//        album.ajouterMorceau(morceau2);
//        catalogue.ajouterAlbum(album);

//        System.out.println(album);
//        System.out.println("\n-------------------------\n");
//        System.out.println(catalogue);


    }


}