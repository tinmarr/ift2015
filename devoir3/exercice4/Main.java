import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Jeu jeu1 = new Jeu();
        Jeu jeu2 = new Jeu();

        ArrayList<Integer> prioritesJoueur1 = new ArrayList<>();
        Collections.addAll(prioritesJoueur1, 5, 3, 4);
        Jeu.Joueur joueur1 = new Jeu.Joueur(1, prioritesJoueur1);

        ArrayList<Integer> prioritesJoueur2 = new ArrayList<>();
        Collections.addAll(prioritesJoueur2, 2, 4, 5);
        Jeu.Joueur joueur2 = new Jeu.Joueur(2, prioritesJoueur2);

        ArrayList<Integer> prioritesJoueur3 = new ArrayList<>();
        Collections.addAll(prioritesJoueur3, 1, 2, 3);
        Jeu.Joueur joueur3 = new Jeu.Joueur(3, prioritesJoueur3);

        jeu1.ajout(joueur1);
        jeu1.ajout(joueur2);
        jeu2.ajout(joueur3);

        System.out.println("Début du tour pour le jeu 1.");
        jeu1.tour();
        System.out.println("Début du tour pour le jeu 2.");
        jeu2.tour();

        System.out.println("Fusion des jeux.");
        jeu1.fusion(jeu2);

        System.out.println("Début d'un nouveau tour après fusion.");
        jeu1.tour();

        // Affiche le score de chaque joueur après les tours
        System.out.println("Scores après les tours:");
        for (Jeu.Joueur joueur : jeu1.tas) {
            System.out.println("Joueur " + joueur.id + ": " + joueur.score);
        }
    }
}

