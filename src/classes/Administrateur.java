package classes;

import java.util.List;

public class Administrateur extends Utilisateurs {

    public Administrateur(int id, String login, String pw) {
        super(id, login, pw);
    }

    public void ajouterMorceauCatalogue(Catalogue catalogue, Morceau morceau) {
        if (catalogue != null && morceau != null) {
            catalogue.ajouterMorceau(morceau);
        }
    }

    public void supprimerMorceauCatalogue(Catalogue catalogue, Morceau morceau) {
        if (catalogue != null && morceau != null) {
            catalogue.supprimerMorceau(morceau);
        }
    }

    public void ajouterAlbumCatalogue(Catalogue catalogue, Album album) {
        if (catalogue != null && album != null) {
            catalogue.ajouterAlbum(album);
        }
    }

    public void supprimerAlbumCatalogue(Catalogue catalogue, Album album) {
        if (catalogue != null && album != null) {
            catalogue.supprimerAlbum(album);
        }
    }

    public void ajouterArtisteCatalogue(Catalogue catalogue, Artiste artiste) {
        if (catalogue != null && artiste != null) {
            catalogue.ajouterArtiste(artiste);
        }
    }

    public void supprimerArtisteCatalogue(Catalogue catalogue, Artiste artiste) {
        if (catalogue != null && artiste != null) {
            catalogue.supprimerArtiste(artiste);
        }
    }

    public void ajouterGroupeCatalogue(Catalogue catalogue, Groupe groupe) {
        if (catalogue != null && groupe != null) {
            catalogue.ajouterGroupe(groupe);
        }
    }

    public void supprimerGroupeCatalogue(Catalogue catalogue, Groupe groupe) {
        if (catalogue != null && groupe != null) {
            catalogue.supprimerGroupe(groupe);
        }
    }

    public void suspendreAbonne(Abonne abonne) {
        if (abonne != null) {
            abonne.suspendre();
        }
    }

    public void reactiverAbonne(Abonne abonne) {
        if (abonne != null) {
            abonne.reactiver();
        }
    }

    public void supprimerAbonne(List<Abonne> abonnes, Abonne abonne) {
        if (abonnes != null && abonne != null) {
            abonnes.remove(abonne);
        }
    }

    @Override
    public String toString() {
        return "Administrateur{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}