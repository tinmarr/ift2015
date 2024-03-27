import java.util.ArrayList;
import java.util.Collections;

/**
 * Représente un jeu de société où les joueurs jouent leur tour en fonction de la priorité de leurs cartes.
 */
public class Jeu {
    /**
     * Représente un joueur dans le jeu.
     */
    static class Joueur {
        int id;
        int score;
        ArrayList<Integer> priorites; // Liste des priorités pour chaque tour

        /**
         * Construit un nouveau joueur avec un identifiant et une liste initiale de priorités.
         * @param id L'identifiant du joueur.
         * @param priorites La liste des priorités du joueur.
         */
        public Joueur(int id, ArrayList<Integer> priorites) {
            this.id = id;
            this.score = 0;
            this.priorites = priorites;
        }

        /**
         * Renvoie la priorité actuelle la plus élevée du joueur.
         * @return La priorité la plus élevée.
         */
        public int getPrioriteActuelle() {
            return Collections.max(priorites);
        }

        /**
         * Met à jour le score du joueur avec le résultat du lancer de dé.
         * @param resultatDe Le résultat du lancer de dé.
         */
        public void jouer(int resultatDe) {
            this.score += resultatDe;
        }
    }

    ArrayList<Joueur> tas; // Utilise une ArrayList pour simuler un tas

    public Jeu() {
        this.tas = new ArrayList<>();
    }

    /**
     * Ajoute un joueur au jeu en maintenant la propriété du tas max basée sur la priorité des cartes.
     * @param joueur Le joueur à ajouter.
     */
    public void ajout(Joueur joueur) {
        tas.add(joueur);
        int i = tas.size() - 1;
        while (i > 0 && tas.get((i - 1) / 2).getPrioriteActuelle() < tas.get(i).getPrioriteActuelle()) {
            Collections.swap(tas, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    /**
     * Exécute un tour de jeu, où chaque joueur lance le dé et son score est mis à jour.
     * Le tas est ajusté pour refléter les nouvelles priorités après le tour.
     */
    public void tour() {
        // Simuler un lancer de dé pour chaque joueur et mettre à jour le score
        for (Joueur joueur : tas) {
            int resultatDe = (int)(Math.random() * 6) - 2; // Génère un nombre entre -2 et 2
            joueur.jouer(resultatDe);
        }
        // Réorganiser le tas en fonction des nouvelles priorités pourrait être nécessaire
        // selon la logique de jeu spécifique (non illustrée ici pour la simplicité).
    }

    /**
     * Fusionne ce jeu avec un autre jeu en combinant leurs tas de joueurs.
     * @param autreJeu Le jeu à fusionner avec le jeu actuel.
     */
    public void fusion(Jeu autreJeu) {
        for (Joueur joueur : autreJeu.tas) {
            this.ajout(joueur);
        }
    }
}
