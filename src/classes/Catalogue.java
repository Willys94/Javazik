package classes;

import java.util.ArrayList;
import java.util.List;

public class Catalogue {
    private List<Morceau> morceaux;
    private List<Album> albums;
    private List<Artiste> artistes;
    private List<Groupe> groupes;

    public Catalogue() {
        this.morceaux = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.artistes = new ArrayList<>();
        this.groupes = new ArrayList<>();
    }

    public void ajouterMorceau(Morceau morceau) {
        if (morceau != null && !morceaux.contains(morceau)) {
            morceaux.add(morceau);
        }
    }

    public void supprimerMorceau(Morceau morceau) {
        morceaux.remove(morceau);
    }

    public void ajouterAlbum(Album album) {
        if (album != null && !albums.contains(album)) {
            albums.add(album);
        }
    }

    public void supprimerAlbum(Album album) {
        albums.remove(album);
    }

    public void ajouterArtiste(Artiste artiste) {
        if (artiste != null && !artistes.contains(artiste)) {
            artistes.add(artiste);
        }
    }

    public void supprimerArtiste(Artiste artiste) {
        artistes.remove(artiste);
    }

    public void ajouterGroupe(Groupe groupe) {
        if (groupe != null && !groupes.contains(groupe)) {
            groupes.add(groupe);
        }
    }

    public void supprimerGroupe(Groupe groupe) {
        groupes.remove(groupe);
    }

    public List<Morceau> getMorceaux() {
        return morceaux;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public List<Artiste> getArtistes() {
        return artistes;
    }

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public List<Morceau> rechercherMorceauxParTitre(String titre) {
        List<Morceau> resultats = new ArrayList<>();

        if (titre == null || titre.trim().isEmpty()) {
            return resultats;
        }

        for (Morceau morceau : morceaux) {
            if (morceau.getTitre() != null &&
                    morceau.getTitre().toLowerCase().contains(titre.toLowerCase())) {
                resultats.add(morceau);
            }
        }

        return resultats;
    }

    public List<Album> rechercherAlbumsParTitre(String titre) {
        List<Album> resultats = new ArrayList<>();

        if (titre == null || titre.trim().isEmpty()) {
            return resultats;
        }

        for (Album album : albums) {
            if (album.getTitre() != null &&
                    album.getTitre().toLowerCase().contains(titre.toLowerCase())) {
                resultats.add(album);
            }
        }

        return resultats;
    }

    public List<Artiste> rechercherArtistesParNom(String nom) {
        List<Artiste> resultats = new ArrayList<>();

        if (nom == null || nom.trim().isEmpty()) {
            return resultats;
        }

        for (Artiste artiste : artistes) {
            if (artiste.getNom() != null &&
                    artiste.getNom().toLowerCase().contains(nom.toLowerCase())) {
                resultats.add(artiste);
            }
        }

        return resultats;
    }

    public List<Groupe> rechercherGroupesParNom(String nom) {
        List<Groupe> resultats = new ArrayList<>();

        if (nom == null || nom.trim().isEmpty()) {
            return resultats;
        }

        for (Groupe groupe : groupes) {
            if (groupe.getNom() != null &&
                    groupe.getNom().toLowerCase().contains(nom.toLowerCase())) {
                resultats.add(groupe);
            }
        }

        return resultats;
    }

    public List<Morceau> getMorceauxParArtiste(String nomArtiste) {
        List<Morceau> resultats = new ArrayList<>();

        for (Morceau morceau : morceaux) {
            if (morceau.getInterprete() instanceof Artiste &&
                    morceau.getInterprete().getNom().equalsIgnoreCase(nomArtiste)) {
                resultats.add(morceau);
            }
        }

        return resultats;
    }

    public List<Album> getAlbumsParArtiste(String nomArtiste) {
        List<Album> resultats = new ArrayList<>();

        for (Album album : albums) {
            if (album.getInterprete() instanceof Artiste &&
                    album.getInterprete().getNom().equalsIgnoreCase(nomArtiste)) {
                resultats.add(album);
            }
        }

        return resultats;
    }

    public List<Morceau> getMorceauxParGroupe(String nomGroupe) {
        List<Morceau> resultats = new ArrayList<>();

        for (Morceau morceau : morceaux) {
            if (morceau.getInterprete() instanceof Groupe &&
                    morceau.getInterprete().getNom().equalsIgnoreCase(nomGroupe)) {
                resultats.add(morceau);
            }
        }

        return resultats;
    }

    public List<Album> getAlbumsParGroupe(String nomGroupe) {
        List<Album> resultats = new ArrayList<>();

        for (Album album : albums) {
            if (album.getInterprete() instanceof Groupe &&
                    album.getInterprete().getNom().equalsIgnoreCase(nomGroupe)) {
                resultats.add(album);
            }
        }

        return resultats;
    }
}