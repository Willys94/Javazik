package classes;

/**
 * Classe de base des utilisateurs authentifiables de l'application.
 */
public abstract class Utilisateurs {
    protected int id;
    protected String login;
    protected String pw;

    /**
     * Construit un utilisateur.
     *
     * @param id identifiant unique
     * @param login identifiant de connexion
     * @param pw mot de passe
     */
    public Utilisateurs(int id, String login, String pw) {
        this.id = id;
        this.login = login;
        this.pw = pw;
    }

    /**
     * Vérifie une paire login/mot de passe.
     *
     * @param login login saisi
     * @param pw mot de passe saisi
     * @return {@code true} si les identifiants correspondent
     */
    public boolean verifierIdentifiants(String login, String pw) {
        return this.login.equals(login) && this.pw.equals(pw);
    }

    /**
     * Retourne l'identifiant utilisateur.
     *
     * @return identifiant
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne le login.
     *
     * @return login utilisateur
     */
    public String getLogin() {
        return login;
    }

    /**
     * Retourne le mot de passe.
     *
     * @return mot de passe
     */
    public String getPw() {
        return pw;
    }

    /**
     * Modifie le login.
     *
     * @param login nouveau login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Modifie le mot de passe.
     *
     * @param pw nouveau mot de passe
     */
    public void setPw(String pw) {
        this.pw = pw;
    }

    @Override
    public String toString() {
        return "Utilisateur{id=" + id + ", login='" + login + "'}";
    }
}