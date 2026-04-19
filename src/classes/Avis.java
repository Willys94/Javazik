package classes;

import java.util.Locale;

/**
 * Représente un avis utilisateur sur un morceau.
 */
public class Avis {
    private int note;
    private String commentaire;

    /**
     * Crée un avis.
     *
     * @param note note donnée
     * @param commentaire commentaire associé
     */
    public Avis(int note, String commentaire) {
        this.note = note;
        this.commentaire = commentaire;
    }

    /**
     * Retourne la note attribuée.
     *
     * @return note de l'avis
     */
    public int getNote() {
        return note;
    }

    /**
     * Retourne le commentaire de l'avis.
     *
     * @return commentaire textuel
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * Modifie la note.
     *
     * @param note nouvelle note
     */
    public void setNote(int note) {
        this.note = note;
    }

    /**
     * Modifie le commentaire.
     *
     * @param commentaire nouveau commentaire
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        String c = commentaire == null || commentaire.isEmpty() ? "sans commentaire" : commentaire;
        return String.format(Locale.FRANCE, "%d/5 — %s", note, c);
    }
}
