import java.util.List;

public class Main {
    public static void main(String[] args) {
        ReseauBST reseau = new ReseauBST();

        // Ajout d'utilisateurs et leurs amis
        reseau.ajouteNouvelUtilisateur(1, "Alice");
        reseau.ajouteNouvelUtilisateur(2, "Bob");
        reseau.ajouteNouvelUtilisateur(3, "Charlie");
        reseau.ajouteNouvelUtilisateur(4, "Diana");
        reseau.ajouteNouvelUtilisateur(5, "Evan");

        // Simuler des amitiés
        reseau.amiToutLeMonde("Alice");
        reseau.amiToutLeMonde("Charlie");

        System.out.println("Amis de Bob: " + reseau.compteAmis(2));
        System.out.println("Liste des noms dans l'ordre: " + reseau.listeNomsDansOrdre());

        // Recommandations pour Bob
        List<String> recommandationsPourBob = reseau.recommender(2);
        System.out.println("Recommandations pour Bob: " + recommandationsPourBob);
    }
}
