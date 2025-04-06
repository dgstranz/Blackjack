import java.util.Scanner;

public class Blackjack {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== BLACKJACK ===");
        mainMenu();
        System.out.println("¡Hasta pronto!");
        sc.close();
    }
    
    public static void mainMenu() {
        String option = "";

        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("[N]ueva partida   [S]alir");
            System.out.print("Selecciona una opción: ");
            option = sc.nextLine().trim().toUpperCase();

            while (!option.matches("[NS]")) {
                System.out.println("Opción no válida. Inténtalo de nuevo: ");
                option = sc.nextLine().trim().toUpperCase();
            }

            if (option.equals("N")) {
                newGame();
            }
        } while (!option.equals("S"));
    }
    
    public static void newGame() {
        String option = "";

        // Inicializar los jugadores
        Player player = new Player("Jugador", 100.00);
        Player dealer = new Player("Croupier", 10_000.00);

        // Inicializar la partida
        do {
            System.out.println("\n--- NUEVA PARTIDA ---");
            System.out.println("Tienes " + player.getCredit() + " euros. La apuesta son 10 euros.");
            System.out.println("[N]ueva ronda   [S]alir al menú principal");
            System.out.print("Selecciona una opción: ");
            option = sc.nextLine().trim().toUpperCase();

            while (!option.matches("[NS]")) {
                System.out.println("Opción no válida. Inténtalo de nuevo: ");
                option = sc.nextLine().trim().toUpperCase();
            }

            if (option.equals("N")) {
                newRound(player, dealer);
            } else if (option.equals("S")) {
                // Volver al menú anterior
                return;
            }
        } while (!option.equals("S"));
        
        return;
    }

    public static void newRound(Player player, Player dealer) {
        double bet;
        double roundedBet;
        final double DEFAULT_BET = 10.0;
        final double MIN_BET = 1.0;
        final double MAX_BET = 50.0;

        System.out.println("\n--- NUEVA RONDA ---");

        /* Para agilizar la parte de las apuestas, se acepta la entrada
         * muy libremente y luego se ajusta la cantidad apostada en función de unas constantes
         * de apuesta mínima, apuesta máxima y crédito restante del jugador.
         * Además, la sala de juego impone que la apuesta deba ser una cantidad entera
         * de euros (salvo que el jugador decida apostar todo lo que le queda).
         */
        if (player.getCredit() < MIN_BET) {
            System.out.println("Lo sentimos, no tienes crédito suficiente. No puedes seguir jugando.");
            return;
        } else {
            System.out.println("La apuesta por defecto son 10 €. Si no tienes suficiente, será lo que te quede.");
            System.out.println("Generalmente solo se admiten cantidades enteras.");
            System.out.print("¿Cuánto quieres apostar? ");
            try {
                bet = Double.parseDouble(sc.nextLine());
                roundedBet = Math.round(bet);
                if (bet < MIN_BET) {
                    System.out.println("La apuesta mínima es de " + MIN_BET + " €, así que apostarás eso.");
                    bet = MIN_BET;
                } else if (Math.max(bet, roundedBet) > player.getCredit()) {
                    System.out.println("Solo tienes " + player.getCredit() + " €, así que apostarás eso.");
                    bet = player.getCredit();
                } else if (bet > MAX_BET) {
                    System.out.println("La apuesta máxima es de " + MAX_BET + " €, así que apostarás eso.");
                    bet = MAX_BET;
                } else if (bet != roundedBet) {
                    System.out.println("La apuesta debe ser de una cantidad entera de euros, así que apostarás la cantidad que resulte de redondear eso.");
                    bet = roundedBet;
                }
            } catch (Exception e) {
                bet = DEFAULT_BET;
            }
            System.out.println("Tu apuesta es '" + bet + "'.");
        }
        
        // Inicializar y barajar el mazo
        Deck deck = new Deck();
        deck.shuffle();

        System.out.println(deck.size());
        System.out.println(deck.toString());

        // Repartir dos cartas al jugador y dos al croupier
        player.drawCards(deck, 10);
        player.showCards(true);
        player.showScore();

        dealer.drawCards(deck, 2);
        dealer.showCards(false);

        // Turno del jugador: ve mostrando mensajes para
        // poder controlar la dinámica y el avance de la partida
        // por la consola.



       // Muestra la primera carta y oculta la segunda
    


        // Turno del dealer: debe pedir cartas hasta alcanzar al menos 17




        // Mostrar resultados finales

    }
}