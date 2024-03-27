import java.util.ArrayList;
import java.util.List;

/**
 * Représente un réseau social utilisant un arbre binaire de recherche pour stocker les informations sur les utilisateurs.
 */
public class ReseauBST {
    /**
     * Classe interne représentant un nœud de l'arbre binaire de recherche.
     */
    class Utilisateur {
        int idUtilisateur;
        String nom;
        List<String> amis;
        Utilisateur gauche, droit;

        /**
         * Constructeur de l'utilisateur.
         * @param id L'identifiant unique de l'utilisateur.
         * @param nom Le nom de l'utilisateur.
         */
        public Utilisateur(int id, String nom) {
            this.idUtilisateur = id;
            this.nom = nom;
            this.amis = new ArrayList<>();
            this.gauche = this.droit = null;
        }
    }

    Utilisateur racine;

    public ReseauBST() {
        racine = null;
    }

    /**
     * Ajoute un nouvel utilisateur à l'arbre binaire de recherche.
     * @param id L'identifiant de l'utilisateur.
     * @param nom Le nom de l'utilisateur.
     */
    public void ajouteNouvelUtilisateur(int id, String nom) {
        racine = ajouteRec(racine, id, nom);
    }

    private Utilisateur ajouteRec(Utilisateur courant, int id, String nom) {
        if (courant == null) {
            return new Utilisateur(id, nom);
        }

        if (id < courant.idUtilisateur) {
            courant.gauche = ajouteRec(courant.gauche, id, nom);
        } else if (id > courant.idUtilisateur) {
            courant.droit = ajouteRec(courant.droit, id, nom);
        }
        return courant;
    }

    /**
     * Compte le nombre d'amis associé à un utilisateur.
     * @param id L'identifiant de l'utilisateur.
     * @return Le nombre d'amis de l'utilisateur, ou -1 si l'utilisateur n'existe pas.
     */
    public int compteAmis(int id) {
        Utilisateur utilisateur = rechercheUtilisateur(racine, id);
        return utilisateur != null ? utilisateur.amis.size() : -1;
    }

    private Utilisateur rechercheUtilisateur(Utilisateur courant, int id) {
        if (courant == null || courant.idUtilisateur == id) {
            return courant;
        }

        return id < courant.idUtilisateur ? rechercheUtilisateur(courant.gauche, id) : rechercheUtilisateur(courant.droit, id);
    }

    /**
     * Ajoute le nom d'une personne à la liste d'amis de chaque utilisateur.
     * @param nom Le nom de la personne à ajouter.
     */
    public void amiToutLeMonde(String nom) {
        ajouteAmiATous(racine, nom);
    }

    private void ajouteAmiATous(Utilisateur courant, String nom) {
        if (courant != null) {
            if (!courant.nom.equals(nom)) { // Éviter de s'ajouter soi-même
                courant.amis.add(nom);
            }
            ajouteAmiATous(courant.gauche, nom);
            ajouteAmiATous(courant.droit, nom);
        }
    }

    /**
     * Génère une liste des noms d'utilisateurs dans l'ordre numérique ascendant de leur ID.
     * @return La liste des noms d'utilisateurs.
     */
    public List<String> listeNomsDansOrdre() {
        List<String> noms = new ArrayList<>();
        parcoursInfixe(racine, noms);
        return noms;
    }

    private void parcoursInfixe(Utilisateur courant, List<String> noms) {
        if (courant != null) {
            parcoursInfixe(courant.gauche, noms);
            noms.add(courant.nom);
            parcoursInfixe(courant.droit, noms);
        }
    }

    /**
     * Recommande des amis à un utilisateur en fonction des amis communs dans le réseau.
     * @param id L'identifiant de l'utilisateur pour lequel les recommandations sont faites.
     * @return Une liste de recommandations d'amis (noms d'utilisateurs).
     */
    public List<String> recommender(int id) {
        Utilisateur utilisateur = rechercheUtilisateur(racine, id);
        if (utilisateur == null) {
            return new ArrayList<>(); // Aucune recommandation si l'utilisateur n'existe pas.
        }
        List<String> recommandations = new ArrayList<>();
        rechercheRecommandations(racine, utilisateur, recommandations);
        return recommandations;
    }

    private void rechercheRecommandations(Utilisateur courant, Utilisateur cible, List<String> recommandations) {
        if (courant != null) {
            if (!courant.amis.contains(cible.nom) && courant.idUtilisateur != cible.idUtilisateur) {
                // Vérifier si courant a des amis communs avec cible et n'est pas déjà ami avec cible
                for (String amiPotentiel : courant.amis) {
                    if (cible.amis.contains(amiPotentiel) && !recommandations.contains(courant.nom)) {
                        recommandations.add(courant.nom);
                        break;
                    }
                }
            }
            rechercheRecommandations(courant.gauche, cible, recommandations);
            rechercheRecommandations(courant.droit, cible, recommandations);
        }
    }
}
