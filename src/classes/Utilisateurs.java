package classes;

abstract public class Utilisateurs {
    protected int id;
    protected String login;
    protected String pw;//mdp

    Utilisateurs(int id, String login, String pw) {
        this.id = id;
        this.login = login;
        this.pw = pw;
    }

    public boolean Login(String login, String pw) {

        return login.equals(this.login) && pw.equals(this.pw); // retourne 1 si les logs sont correct 0 sinon
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
