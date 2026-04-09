package classes;

import java.util.List;

public class AuthentificationService {

    public Utilisateurs connecter(String login, String pw,
                                  List<Abonne> abonnes,
                                  List<Administrateur> administrateurs) {

        if (login == null || pw == null) {
            return null;
        }

        for (Administrateur admin : administrateurs) {
            if (admin.verifierIdentifiants(login, pw)) {
                return admin;
            }
        }

        for (Abonne abonne : abonnes) {
            if (abonne.verifierIdentifiants(login, pw)) {
                if (abonne.estSuspendu()) {
                    return null;
                }
                return abonne;
            }
        }

        return null;
    }

    public Abonne creerCompte(String login, String pw, List<Abonne> abonnes) {
        if (login == null || pw == null || login.trim().isEmpty() || pw.trim().isEmpty()) {
            return null;
        }

        for (Abonne abonne : abonnes) {
            if (abonne.getLogin().equalsIgnoreCase(login)) {
                return null;
            }
        }

        int nouvelId = abonnes.size() + 1;
        Abonne nouvelAbonne = new Abonne(nouvelId, login, pw);
        abonnes.add(nouvelAbonne);
        return nouvelAbonne;
    }
}