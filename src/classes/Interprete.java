package classes;

/**
 * Définit le contrat minimal d'un interprète musical.
 * Un interprète peut être un artiste solo ou un groupe.
 */
public interface Interprete {
    /**
     * Retourne le nom d'affichage de l'interprète.
     *
     * @return nom de l'interprète
     */
    String getNom();
}

