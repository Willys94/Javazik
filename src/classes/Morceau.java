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

    /**
     * Construit un morceau.
     *
     * @param id identifiant du morceau
     * @param titre titre du morceau
     * @param duree durée en secondes
     * @param style style musical
     * @param nbEcoutes nombre initial d'écoutes
     * @param interprete interprète principal
     */
    public Morceau(int id, String titre, int duree, String style, int nbEcoutes, Interprete interprete) {
        this.id = id;
        this.titre = titre;
        this.duree = duree;
        this.style = style;
        this.nbEcoutes = nbEcoutes;
        this.interprete = interprete;
        this.avisParAbonne = new LinkedHashMap<>();
    }

    /**
     * Retourne l'identifiant du morceau.
     *
     * @return identifiant
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le titre.
     *
     * @return titre du morceau
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Retourne la durée.
     *
     * @return durée en secondes
     */
    public int getDuree() {
        return duree;
    }

    /**
     * Retourne le style musical.
     *
     * @return style
     */
    public String getStyle() {
        return style;
    }

    /**
     * Retourne le nombre d'écoutes.
     *
     * @return compteur d'écoutes
     */
    public int getNbEcoutes() {
        return nbEcoutes;
    }

    /**
     * Retourne l'interprète du morceau.
     *
     * @return interprète
     */
    public Interprete getInterprete() {
        return interprete;
    }

    /**
     * Modifie le titre.
     *
     * @param titre nouveau titre
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Modifie la durée.
     *
     * @param duree nouvelle durée en secondes
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }

    /**
     * Modifie le style.
     *
     * @param style nouveau style
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * Modifie l'interprète.
     *
     * @param interprete nouvel interprète
     */
    public void setInterprete(Interprete interprete) {
        this.interprete = interprete;
    }

    /**
     * Incrémente le compteur d'écoutes.
     */
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

    /**
     * Retourne les avis indexés par login d'abonné.
     *
     * @return vue non modifiable des avis
     */
    public Map<String, Avis> getAvisParAbonne() {
        return Collections.unmodifiableMap(avisParAbonne);
    }

    /**
     * Formate une durée en secondes au format minutes:secondes.
     *
     * @param secondes durée en secondes
     * @return durée formatée (ex. 3:05)
     */
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