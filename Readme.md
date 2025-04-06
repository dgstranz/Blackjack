/*
 * =================
 * =   BLACKJACK   =
 * =================
 * 
 * Utiliza como estructuras básicas:
 * arrays, bucles, condicionales y métodos.
 * 
 * Puedes apoyarte con otras clases, que deberás importar.
 * 
 * 1. Mazo de cartas:
 *      - Crea un array de 52 cartas combinando los siguientes valores:
 *          - Palos: ♠, ♥, ♦, ♣
 *          - Rangos: A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K
 *      - Implementa un método para barajar el mazo aleatoriamente 
 *        (puedes usar el algoritmo de Fisher-Yates).
 * 
 * 2. Reparto inicial:
 *      - Al iniciar la partida, se deben repartir dos cartas al jugador 
 *        y dos cartas al dealer (la banca).
 *      - Una de las cartas del dealer debe mostrarse y la otra debe 
 *        permanecer oculta durante el turno del jugador.
 *      - El mazo de un jugador puede contener 12 cartas como máximo.
 * 
 * 3. Turno del jugador:
 *      - Muestra al jugador sus cartas y el valor total de su mano.
 *      - Pregunta si desea "pedir carta" (hit) o "plantarse" (stand).
 *          - Si pide carta, añade una nueva carta a su mano.
 *          - Si el valor total supera 21, el jugador pierde automáticamente.
 *          - El jugador puede seguir pidiendo cartas mientras no se plante ni se pase de 21.
 * 
 * 4. Turno del dealer:
 *      - Cuando el jugador se planta, se revelan las cartas del dealer.
 *      - El dealer debe seguir robando cartas hasta que su mano tenga al menos 17 puntos.
 *      - Si se pasa de 21, pierde automáticamente.
 * 
 * 5. Cálculo del valor de la mano:
 *      - Las cartas J, Q, K valen 10 puntos.
 *      - Los ases (A) valen 11 puntos, pero si la suma total supera 21, el valor del As se ajusta a 1.
 *      - Implementa un método que calcule correctamente el valor total de una mano, 
 *        teniendo en cuenta este comportamiento especial de los Ases.
 * 
 * 6. Resultado de la partida:
 *      - Al finalizar el turno del dealer, muestra el valor de ambas manos y determina el resultado:
 *          - Si el jugador tiene más puntos sin pasarse, gana.
 *          - Si el dealer tiene más puntos sin pasarse, gana el dealer.
 *          - Si ambos tienen el mismo valor, es un empate.
 *          - Si uno de los dos se pasa de 21, pierde automáticamente.
 * 
 * === AYUDA ===
 * crearBaraja(): genera las 52 cartas.
 * barajarMazo(String[] mazo): baraja el mazo.
 * repartirCarta(): devuelve la siguiente carta disponible en el mazo.
 * calcularValorMano(String[] mano, int numCartas): calcula el valor de una mano completa.
 * printMano(String[] mano, int numCartas): imprime las cartas de una mano por consola.
 */
