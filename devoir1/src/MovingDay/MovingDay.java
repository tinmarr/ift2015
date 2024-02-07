package MovingDay;

import Stack.ArrayStack;

public class MovingDay {
    private ArrayStack<String> Nam = new ArrayStack<>();
    private ArrayStack<String> Pam = new ArrayStack<>();
    private ArrayStack<String> Sam = new ArrayStack<>();
    private int days = 0;

    public MovingDay() {
        Nam.push("N5: Le Roi");
        Nam.push("N4: Gouvernement");
        Nam.push("N3: Académie");
        Nam.push("N2: Manufacture");
        Nam.push("N1: Agriculture");
    }

    public void move() {
        while (!Nam.isEmpty() || !Pam.isEmpty()) {
            days++;
            if (!Nam.isEmpty()) {
                Pam.push(Nam.pop());
            } else if (!Pam.isEmpty()) {
                Sam.push(Pam.pop());
            }
            printStatus();
            if (days > 10) {
                System.out.println(
                        "Il est NON POSSIBLE de déplacer la ville dans les 10 jours, car plus de 10 jours sont nécessaires.");
                return;
            }
        }
        System.out.println(
                "Il est POSSIBLE de déplacer la ville dans les 10 jours, car " + days + " jours sont nécessaires.");
    }

    private void printStatus() {
        System.out.println("Jour " + days + ": Nam (" + Nam.toString() + "), Pam (" + Pam.toString() + ") et Sam ("
                + Sam.toString() + ")");
    }
}
