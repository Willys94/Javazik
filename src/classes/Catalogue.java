package classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represente le catalogue musical central de l'application.
 * Il stocke morceaux, albums, artistes et groupes, et expose des
 * operations de recherche, tri et statistiques.
 */
public class Catalogue {
    private List<Morceau> morceaux;
    private List<Album> albums;
    private List<Artiste> artistes;
    private List<Groupe> groupes;

    /**
     * Crée un catalogue vide.
     */
    public Catalogue() {
        this.morceaux = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.artistes = new ArrayList<>();
        this.groupes = new ArrayList<>();
    }

    /**
     * Ajoute un morceau au catalogue.
     *
     * @param morceau morceau à ajouter
     */
    public void ajouterMorceau(Morceau morceau) {
        if (morceau != null && !morceaux.contains(morceau)) {
            morceaux.add(morceau);
        }
    }

    /**
     * Retire un morceau du catalogue.
     *
     * @param morceau morceau à retirer
     */
    public void supprimerMorceau(Morceau morceau) {
        morceaux.remove(morceau);
    }

    /**
     * Ajoute un album au catalogue.
     *
     * @param album album à ajouter
     */
    public void ajouterAlbum(Album album) {
        if (album != null && !albums.contains(album)) {
            albums.add(album);
        }
    }

    /**
     * Retire un album du catalogue.
     *
     * @param album album à retirer
     */
    public void supprimerAlbum(Album album) {
        albums.remove(album);
    }

    /**
     * Ajoute un artiste au catalogue.
     *
     * @param artiste artiste à ajouter
     */
    public void ajouterArtiste(Artiste artiste) {
        if (artiste != null && !artistes.contains(artiste)) {
            artistes.add(artiste);
        }
    }

    /**
     * Retire un artiste du catalogue.
     *
     * @param artiste artiste à retirer
     */
    public void supprimerArtiste(Artiste artiste) {
        artistes.remove(artiste);
    }

    /**
     * Ajoute un groupe au catalogue.
     *
     * @param groupe groupe à ajouter
     */
    public void ajouterGroupe(Groupe groupe) {
        if (groupe != null && !groupes.contains(groupe)) {
            groupes.add(groupe);
        }
    }

    /**
     * Retire un groupe du catalogue.
     *
     * @param groupe groupe à retirer
     */
    public void supprimerGroupe(Groupe groupe) {
        groupes.remove(groupe);
    }

    /**
     * Retourne tous les morceaux du catalogue.
     *
     * @return liste des morceaux
     */
    public List<Morceau> getMorceaux() {
        return morceaux;
    }

    /**
     * Retourne tous les albums du catalogue.
     *
     * @return liste des albums
     */
    public List<Album> getAlbums() {
        return albums;
    }

    /**
     * Retourne tous les artistes du catalogue.
     *
     * @return liste des artistes
     */
    public List<Artiste> getArtistes() {
        return artistes;
    }

    /**
     * Retourne tous les groupes du catalogue.
     *
     * @return liste des groupes
     */
    public List<Groupe> getGroupes() {
        return groupes;
    }

    /**
     * Recherche des morceaux par correspondance partielle sur le titre.
     *
     * @param titre texte recherche
     * @return liste des morceaux correspondants
     */
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

    /**
     * Recherche des albums par correspondance partielle de titre.
     *
     * @param titre texte recherché
     * @return liste d'albums correspondants
     */
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

    /**
     * Recherche des artistes par correspondance partielle de nom.
     *
     * @param nom nom recherché
     * @return liste d'artistes correspondants
     */
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

    /**
     * Recherche des groupes par correspondance partielle de nom.
     *
     * @param nom nom recherché
     * @return liste de groupes correspondants
     */
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

    /**
     * Retourne les morceaux interprétés par un artiste donné.
     *
     * @param nomArtiste nom de l'artiste
     * @return morceaux de cet artiste
     */
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

    /**
     * Retourne les albums d'un artiste donné.
     *
     * @param nomArtiste nom de l'artiste
     * @return albums de cet artiste
     */
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

    /**
     * Retourne les morceaux interprétés par un groupe donné.
     *
     * @param nomGroupe nom du groupe
     * @return morceaux de ce groupe
     */
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

    /**
     * Retourne les albums d'un groupe donné.
     *
     * @param nomGroupe nom du groupe
     * @return albums de ce groupe
     */
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

    /**
     * Recherche des morceaux par style musical.
     *
     * @param style style recherche
     * @return liste des morceaux correspondants
     */
    public List<Morceau> rechercherMorceauxParStyle(String style) {
        List<Morceau> resultats = new ArrayList<>();

        if (style == null || style.trim().isEmpty()) {
            return resultats;
        }

        for (Morceau morceau : morceaux) {
            if (morceau.getStyle() != null &&
                    morceau.getStyle().toLowerCase().contains(style.toLowerCase())) {
                resultats.add(morceau);
            }
        }

        return resultats;
    }

    /**
     * Calcule le total des ecoutes de tous les morceaux du catalogue.
     *
     * @return nombre total d'ecoutes
     */
    public int getTotalEcoutesCatalogue() {
        int total = 0;
        for (Morceau morceau : morceaux) {
            total += morceau.getNbEcoutes();
        }
        return total;
    }

    /**
     * Retourne le morceau le plus ecoute.
     *
     * @return morceau le plus ecoute, ou {@code null} si vide
     */
    public Morceau getMorceauLePlusEcoute() {
        Morceau meilleur = null;
        for (Morceau morceau : morceaux) {
            if (meilleur == null || morceau.getNbEcoutes() > meilleur.getNbEcoutes()) {
                meilleur = morceau;
            }
        }
        return meilleur;
    }

    /**
     * Calcule l'album le plus écouté (somme des écoutes de ses morceaux).
     *
     * @return album le plus écouté, ou {@code null} si aucun album
     */
    public Album getAlbumLePlusEcoute() {
        Album meilleurAlbum = null;
        int maxEcoutesAlbum = -1;

        for (Album album : albums) {
            int ecoutesAlbum = 0;
            for (Morceau morceau : album.getMorceaux()) {
                ecoutesAlbum += morceau.getNbEcoutes();
            }

            if (ecoutesAlbum > maxEcoutesAlbum) {
                maxEcoutesAlbum = ecoutesAlbum;
                meilleurAlbum = album;
            }
        }

        return meilleurAlbum;
    }

    /**
     * Calcule l'interprète (artiste ou groupe) le plus écouté.
     *
     * @return interprète le plus écouté, ou {@code null} si catalogue vide
     */
    public Interprete getInterpreteLePlusEcoute() {
        Interprete meilleur = null;
        int maxEcoutes = -1;

        for (Artiste artiste : artistes) {
            int ecoutesArtiste = 0;
            for (Morceau morceau : morceaux) {
                if (morceau.getInterprete() instanceof Artiste &&
                        morceau.getInterprete().getNom().equalsIgnoreCase(artiste.getNom())) {
                    ecoutesArtiste += morceau.getNbEcoutes();
                }
            }
            if (ecoutesArtiste > maxEcoutes) {
                maxEcoutes = ecoutesArtiste;
                meilleur = artiste;
            }
        }

        for (Groupe groupe : groupes) {
            int ecoutesGroupe = 0;
            for (Morceau morceau : morceaux) {
                if (morceau.getInterprete() instanceof Groupe &&
                        morceau.getInterprete().getNom().equalsIgnoreCase(groupe.getNom())) {
                    ecoutesGroupe += morceau.getNbEcoutes();
                }
            }
            if (ecoutesGroupe > maxEcoutes) {
                maxEcoutes = ecoutesGroupe;
                meilleur = groupe;
            }
        }

        return meilleur;
    }

    /**
     * Retourne la liste des morceaux tries par titre.
     *
     * @return copie triee par ordre alphabetique de titre
     */
    public List<Morceau> getMorceauxTriesParTitre() {
        List<Morceau> tries = new ArrayList<>(morceaux);
        tries.sort(Comparator.comparing(Morceau::getTitre, String.CASE_INSENSITIVE_ORDER));
        return tries;
    }

    /**
     * Retourne une copie des morceaux triés par durée croissante.
     *
     * @return liste triée par durée
     */
    public List<Morceau> getMorceauxTriesParDuree() {
        List<Morceau> tries = new ArrayList<>(morceaux);
        tries.sort(Comparator.comparingInt(Morceau::getDuree));
        return tries;
    }

    /**
     * Retourne une copie des morceaux triés par nombre d'écoutes décroissant.
     *
     * @return liste triée par popularité
     */
    public List<Morceau> getMorceauxTriesParEcoutes() {
        List<Morceau> tries = new ArrayList<>(morceaux);
        tries.sort(Comparator.comparingInt(Morceau::getNbEcoutes).reversed());
        return tries;
    }
}