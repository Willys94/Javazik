package classes;

import java.util.Collections;
import java.util.Locale;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represente un morceau du catalogue avec ses metadonnees et les avis associes.
 */
public class Morceau {
    private int id;
    private String titre;
    private int duree;
    private String style;
    private int nbEcoutes;
    private Interprete interprete;
    private Map<String, Avis> avisParAbonne;

    public Morceau(int id, String titre, int duree, String style, int nbEcoutes, Interprete interprete) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.style = style;
        this.nbEcoutes = nbEcoutes;
        this.interprete = interprete;
        this.avisParAbonne = new LinkedHashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public int getDuree() {
        return duree;
    }

    public String getStyle() {
        return style;
    }

    public int getNbEcoutes() {
        return nbEcoutes;
    }

    public Interprete getInterprete() {
        return interprete;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setInterprete(Interprete interprete) {
        this.interprete = interprete;
    }

    public void incrementerEcoutes() {
        this.nbEcoutes++;
    }

    /**
     * Ajoute un avis ou remplace l'avis existant d'un abonne.
     *
     * @param loginAbonne login de l'abonne
     * @param note note de 1 a 5
     * @param commentaire commentaire optionnel
     * @return {@code true} si l'avis est valide et enregistre
     */
    public boolean ajouterOuModifierAvis(String loginAbonne, int note, String commentaire) {
        if (loginAbonne == null || loginAbonne.trim().isEmpty() || note < 1 || note > 5) {
            return false;
        }
        String commentaireSafe = commentaire == null ? "" : commentaire.trim();
        avisParAbonne.put(loginAbonne.toLowerCase(), new Avis(note, commentaireSafe));
        return true;
    }

    /**
     * Supprime l'avis d'un abonne pour ce morceau.
     *
     * @param loginAbonne login de l'abonne
     * @return {@code true} si un avis a ete supprime
     */
    public boolean supprimerAvis(String loginAbonne) {
        if (loginAbonne == null || loginAbonne.trim().isEmpty()) {
            return false;
        }
        return avisParAbonne.remove(loginAbonne.toLowerCase()) != null;
    }

    /**
     * Calcule la note moyenne du morceau.
     *
     * @return note moyenne, ou 0.0 s'il n'existe aucun avis
     */
    public double getNoteMoyenne() {
        if (avisParAbonne.isEmpty()) {
            return 0.0;
        }
        int total = 0;
        for (Avis avis : avisParAbonne.values()) {
            total += avis.getNote();
        }
        return (double) total / avisParAbonne.size();
    }

    public Map<String, Avis> getAvisParAbonne() {
        return Collections.unmodifiableMap(avisParAbonne);
    }

    /** Duree en secondes au format minutes:secondes (ex. 3:05). */
    public static String formaterDuree(int secondes) {
        if (secondes < 0) {
            secondes = 0;
        }
        int m = secondes / 60;
        int s = secondes % 60;
        return String.format("%d:%02d", m, s);
    }

    @Override
    public String toString() {
        String dureeFmt = formaterDuree(duree);
        String resumeAvis;
        if (avisParAbonne.isEmpty()) {
            resumeAvis = "pas encore note";
        } else {
            resumeAvis = String.format(Locale.FRANCE, "note moy. %.1f/5 (%d avis)", getNoteMoyenne(), avisParAbonne.size());
        }
        return String.format(Locale.FRANCE, "\"%s\" · %s · %s · %s · %d ecoute(s) · %s",
                titre, interprete.getNom(), dureeFmt, style, nbEcoutes, resumeAvis);
    }
}