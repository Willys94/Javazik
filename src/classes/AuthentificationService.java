package classes;

import java.util.List;

/**
 * Service metier dedie a l'authentification et a la creation de comptes abonnes.
 */
public class AuthentificationService {

    /**
     * Verifie des identifiants et retourne l'utilisateur correspondant.
     * Les administrateurs sont testes en priorite, puis les abonnes non suspendus.
     *
     * @param login identifiant saisi
     * @param pw mot de passe saisi
     * @param abonnes liste des abonnes disponibles
     * @param administrateurs liste des administrateurs disponibles
     * @return utilisateur authentifie, ou {@code null} en cas d'echec
     */
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

    /**
     * Cree un nouveau compte abonne si le login est valide et unique.
     *
     * @param login login souhaite
     * @param pw mot de passe souhaite
     * @param abonnes liste dans laquelle ajouter le nouvel abonne
     * @return l'abonne cree, ou {@code null} si creation impossible
     */
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