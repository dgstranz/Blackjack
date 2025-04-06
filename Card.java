import java.nio.charset.Charset;

public class Card {
    private String rankName;
    private String rankSymbol;
    private String suitName;
    private String suitSymbol;
    private int score;
    
    /* Si el conjunto de caracteres por defecto no es UTF-8,
     * se asume que no se van a poder sacar correctamente los símbolos
     * correctos para los palos
     * 
     * Para cambiar la configuración en Windows, se pueden seguir los
     * pasos indicados en https://stackoverflow.com/questions/57131654/using-utf-8-encoding-chcp-65001-in-command-prompt-windows-powershell-window
    */
    private static boolean useShortFormat = (Charset.defaultCharset().name().equalsIgnoreCase("UTF-8"));

    public Card(int rankIndex, int suitIndex) {
        this.rankName = Deck.getRankName(rankIndex);
        this.rankSymbol = Deck.getRankSymbol(rankIndex);
        this.suitName = Deck.getSuitName(suitIndex);
        this.suitSymbol = Deck.getSuitSymbol(suitIndex);

        this.score = switch (this.rankSymbol) {
            case "A" -> 11;
            case "J", "Q", "K" -> 10;
            default -> Integer.parseInt(this.rankSymbol);
        };
    }

    public int getScore() {
        return score;
    }

    public String getRank() {
        return rankName;
    }

    @Override
    public String toString() {
        if (useShortFormat) {
            return rankSymbol + suitSymbol;
        } else {
            return "[" + rankName.substring(0,1).toUpperCase() + rankName.substring(1) + " de " + suitName + "]";
        }
    }
    
    public String toString(boolean isHidden) {
        if (isHidden) {
            if (useShortFormat) {
                return "??";
            } else {
                return "[Carta desconocida]";
            }
        } else {
            return toString();
        }
    }
}