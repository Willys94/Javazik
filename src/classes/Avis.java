package classes;

import java.util.Locale;

public class Avis {
    private int note;
    private String commentaire;

    public Avis(int note, String commentaire) {
        this.note = note;
        this.commentaire = commentaire;
    }

    public int getNote() {
        return note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        String c = commentaire == null || commentaire.isEmpty() ? "sans commentaire" : commentaire;
        return String.format(Locale.FRANCE, "%d/5 — %s", note, c);
    }
}
