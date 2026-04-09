package classes;

public abstract class Utilisateurs {
    protected int id;
    protected String login;
    protected String pw;

    public Utilisateurs(int id, String login, String pw) {
        this.id = id;
        this.login = login;
        this.pw = pw;
    }

    public boolean verifierIdentifiants(String login, String pw) {
        return this.login.equals(login) && this.pw.equals(pw);
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPw() {
        return pw;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @Override
    public String toString() {
        return "Utilisateur{id=" + id + ", login='" + login + "'}";
    }
}