package classes;

abstract public class Utilisateurs {
    int id;
    String login;
    String pw;//mdp

    public Utilisateurs(){
        // constructeur vide
    }
    public Utilisateurs(int id,String login,String pw){
        this.id = id;
        this.login = login;
        this.pw = pw;
    }
    abstract public void seConnecter();
    abstract public void consulterCatalogue();
    //*Getter et Setter *//

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
