import java.util.ArrayList;

public class Deck {
    // Nombres y símbolos de los valores y palos de las cartas
    private static final String[] RANK_NAMES = {"as", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez", "jota", "reina", "rey"};
    private static final String[] RANK_SYMBOLS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static final String[] SUIT_NAMES = {"tréboles", "diamantes", "corazones", "picas"};
    private static final String[] SUIT_SYMBOLS = {"♣︎", "♦", "♥", "♠"};

    // Número de valores, palos y cartas en total en la baraja
    private static final int NUMBER_OF_RANKS = RANK_NAMES.length;
    private static final int NUMBER_OF_SUITS = SUIT_NAMES.length;

    // La baraja en sí
    private ArrayList<Card> cards;

    // El constructor
    public Deck() {
        ArrayList<Card> cards = new ArrayList<Card>();

        for (int i = 0; i < NUMBER_OF_SUITS; i++) {
            for (int j = 0; j < NUMBER_OF_RANKS; j++) {
                cards.add(new Card(j, i));
            }
        }

        this.cards = cards;
    }

    // Los getters y setters
    public static String getRankName(int index) {
        return RANK_NAMES[index];
    }

    public static String getRankSymbol(int index) {
        return RANK_SYMBOLS[index];
    }

    public static String getSuitName(int index) {
        return SUIT_NAMES[index];
    }

    public static String getSuitSymbol(int index) {
        return SUIT_SYMBOLS[index];
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return (cards.size() == 0);
    }
    
    // Baraja el mazo utilizando el algoritmo de Fisher-Yates
    public void shuffle() {
        ArrayList<Card> shuffledDeck = new ArrayList<Card>();
        int index;
        Card card;

        while (cards.size() > 0) {
            index = (int) (Math.random() * cards.size());
            card = cards.remove(index);
            shuffledDeck.add(card);
        }

        cards = shuffledDeck;
    }

    public Card removeCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("No hay más cartas en el mazo.");
        }

        return cards.removeLast();
    }

    @Override
    public String toString() {
        String output = new String();
        for (int i = 0; i < cards.size(); i++) {
            output += (cards.get(i).toString() + " ");
        }

        return output;
    }
}
