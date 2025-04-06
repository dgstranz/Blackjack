import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> cards;
    private double credit;

    public Player(String name, double credit) {
        this.name = name;
        this.credit = credit;

        ArrayList<Card> cards = new ArrayList<Card>();
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public double getCredit() {
        return credit;
    }

    public void gain(int change) {
        credit += change;
    }

    public void lose(int change) {
        credit -= change;
    }

    /* Con estas reglas no debería acabarse el mazo.
     * Igualmente, está contemplado el caso en que se acaba y un
     * jugador trata de robar.
     * 
     * La mano máxima es de 11 cartas (cuatro ases, cuatro doses y
     * tres treses, que suman 21 puntos), por lo que en cualquier
     * mano mayor solo habrá que contar los puntos y determinar
     * que ha perdido.
     */
    public void drawCard(Deck deck) {
        try {
            Card card = deck.removeCard();
            cards.add(card);
        } catch (IllegalStateException e) {
            System.err.println("Error al tratar de robar una carta: " + e.getMessage());
            throw e;
        }
    }

    public void drawCards(Deck deck, int num) {
        for (int i = 0; i < num; i++) {
            try {
                drawCard(deck);
            } catch (IllegalStateException e) {
                System.err.println("Error al tratar de robar cartas del mazo: " + e.getMessage());
                throw e;
            }
        }
    }

    public void showCards(boolean visible) {
        String output = new String();

        for (int i = 0; i < cards.size(); i++) {
            output += cards.get(i).toString(!visible && i > 0) + " ";
        }

        System.out.println(output);
    }

    public void showScore() {
        int score = 0;
        int aces = 0;
        Card card;

        for (int i = 0; i < cards.size(); i++) {
            card = cards.get(i);
            score += card.getScore();
            if (card.getRank() == "A") {
                aces++;
            }
        }

        /* Si el jugador tiene más de 21 puntos y tiene ases,
         * probamos a ver uno por uno si esos ases pueden contar
         * 1 punto en lugar de 11
         */
        while (score > 21 && aces > 0) {
            aces--;
            score -= 10;
        }

        System.out.println(score);
    }
}