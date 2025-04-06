import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            System.out.println("[N]ueva partida   [I]nstrucciones   [S]alir");
            System.out.print("Selecciona una opción: ");
            option = sc.nextLine().trim().toUpperCase();

            while (!option.matches("[NIS]")) {
                System.out.println("Opción no válida. Inténtalo de nuevo: ");
                option = sc.nextLine().trim().toUpperCase();
            }

            if (option.equals("N")) {
                newGame();
            } else if (option.equals("I")) {
                try {
                    printInstructions();
                } catch (FileNotFoundException e) {
                    System.err.println("Instrucciones no disponibles: " + e.getMessage());
                } catch (IOException e) {
                    System.err.println("Instrucciones no disponibles: " + e.getMessage());
                }
            }
        } while (!option.equals("S"));
    }

    public static void printInstructions() throws IOException {
        Path path = Paths.get("README.md");
        String line;
        BufferedReader reader = null;

        try {
            reader = Files.newBufferedReader(path);
            while((line = reader.readLine()) != null) {
                System.out.println(line.substring(
                    Integer.min(line.length(), 3)
                    ));
            }

            reader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("No se encontró el archivo especificado");
        } catch (IOException e) {
            throw new IOException("Error al leer el archivo: " + e.getMessage());
        }
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
        String option;
        boolean playerBlackjack = false;
        boolean dealerBlackjack = false;
        boolean playerBust = false;
        boolean dealerBust = false;

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

        // Repartir dos cartas al jugador y dos al croupier
        player.drawCards(deck, 2);
        dealer.drawCards(deck, 2);

        // Turno del jugador: ve mostrando mensajes para
        // poder controlar la dinámica y el avance de la partida
        // por la consola.

        System.out.println("\n¡Es tu turno!\n");

        do {
            System.out.println("Tu mano: " + player.getCards(true) + "(" + player.getScore() + " puntos)");
            System.out.println("Mano del croupier: " + dealer.getCards(false));
            System.out.println();
            System.out.println("[H]it (pedir carta)   [S]tand (plantarse)");
            System.out.print("Selecciona una opción: ");
            option = sc.nextLine().trim().toUpperCase();

            while (!option.matches("[HS]")) {
                System.out.println("Opción no válida. Inténtalo de nuevo: ");
                option = sc.nextLine().trim().toUpperCase();
            }

            if (option.equals("H")) {
                try {
                    player.drawCard(deck);
                    System.out.println("Has robado la carta " + player.getLastCard() + "\n");
                } catch (IllegalStateException e) {
                    System.err.println("Error al tratar de robar una carta: " + e.getMessage());
                    break;
                }
            } else if (option.equals("S")) {
                break;
            }
        } while (!option.equals("S") && player.getScore() <= 21);

        System.out.println("Te has plantado.");

        if (player.getScore() > 21) {
            System.out.println("¡Te has pasado! Tienes " + player.getScore() + " puntos.");
            playerBust = true;
        } else if (player.getScore() == 21 && player.getHandSize() == 2) {
            System.out.println("¡Enhorabuena! ¡Has conseguido un blackjack!");
            playerBlackjack = true;
        }

        /* Aunque el jugador se pase, el croupier sigue jugando.
         * La lógica es que puede haber varios jugadores; el croupier
         * puede seguir jugando contra los demás.
         */
        System.out.println("\n¡Es el turno del croupier!\n");

        // Turno del croupier: debe pedir cartas hasta alcanzar al menos 17
        while(dealer.getScore() <= 16) {
            try {
                dealer.drawCard(deck);
                System.out.println("El croupier ha robado la carta " + dealer.getLastCard());
            } catch (IllegalStateException e) {
                System.err.println("Error al tratar de robar una carta: " + e.getMessage());
                break;
            }
        }

        System.out.println("El croupier se ha plantado.");

        if (dealer.getScore() > 21) {
            System.out.println("¡El croupier se ha pasado! Tiene " + dealer.getScore() + " puntos.");
            dealerBust = true;
        } else if (dealer.getScore() == 21 && dealer.getHandSize() == 2) {
            System.out.println("Vaya, el croupier ha conseguido un blackjack...");
            dealerBlackjack = true;
        }

        // Mostrar resultados finales
        System.out.println("\nFin de la ronda");
        System.out.println("Tu mano: " + player.getCards(true) + "(" + player.getScore() + " puntos)");
        System.out.println("Mano del croupier: " + dealer.getCards(true) + "(" + dealer.getScore() + " puntos)");
        System.out.println("");

        if (playerBust) {
            // Si el croupier también se pasa, es igual: el jugador pierde igualmente.
            System.out.println("HAS PERDIDO: Te has pasado. Pierdes " + bet + " €.");
            player.lose(bet);
        } else if (playerBlackjack) {
            if (dealerBlackjack) {
                System.out.println("EMPATE: Tanto tú como el croupier habéis conseguido un blackjack.");
            } else {
                System.out.println("HAS GANADO: Conseguiste un blackjack, que se paga 3 a 2. Ganas " + (1.5 * bet) + " €.");
                player.gain(1.5 * bet);
            }
        } else if (dealerBust) {
            System.out.println("HAS GANADO: El croupier se ha pasado. Ganas " + bet + " €.");
            player.gain(bet);
        } else if (dealerBlackjack) {
            System.out.println("HAS PERDIDO: El croupier consiguió un blackjack. Pierdes " + bet + " €.");
            player.lose(bet);
        } else if (player.getScore() > dealer.getScore()) {
            System.out.println("HAS GANADO: Tienes " + player.getScore() + " puntos, por " + dealer.getScore() + " del croupier. Ganas " + bet + " €.");
            player.gain(bet);
        } else if (player.getScore() < dealer.getScore()) {
            System.out.println("HAS PERDIDO: Tienes " + player.getScore() + " puntos, por " + dealer.getScore() + " del croupier. Pierdes " + bet + " €.");
            player.lose(bet);
        } else {
            System.out.println("EMPATE: Tienes " + player.getScore() + " puntos, por " + dealer.getScore() + " del croupier.");
        }

        // Reseteamos la mano del jugador y del croupier para la próxima ronda //
        player.resetHand();
        dealer.resetHand();
    }
}