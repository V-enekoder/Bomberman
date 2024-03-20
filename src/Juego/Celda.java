package Juego;
/**
 * Enumeración que representa los tipos de celda en el tablero del juego.
 */
public enum Celda{
    PISO,     // Celda que representa un espacio vacío donde los personajes pueden moverse
    PARED,    // Celda que representa una pared, que bloquea el movimiento de los personajes
    BLOQUE    // Celda que representa un bloque que puede ser destruido por una explosión
}