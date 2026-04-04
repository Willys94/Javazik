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
}
