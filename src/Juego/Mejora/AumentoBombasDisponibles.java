package Juego.Mejora;

import Juego.Personaje.Jugador;

/**
 * La clase AumentoBombasDisponibles representa una mejora que aumenta el número de bombas disponibles para un jugador en el juego Bomberman.
 * Cuando un jugador recoge esta mejora, se incrementa su contador de bombas disponibles.
 */
public class AumentoBombasDisponibles extends Mejora{

    /**
     * Crea una nueva instancia de AumentoBombasDisponibles en la posición especificada en el tablero.
     * 
     * @param fila La fila en la que se encuentra la mejora.
     * @param columna La columna en la que se encuentra la mejora.
     */
    public AumentoBombasDisponibles(int fila, int columna){
        super(fila, columna);
        nombre = "Aumento de Bombas Disponibles";
    }
    
    /**
     * Agrega la mejora al jugador, aumentando el número de bombas disponibles para el jugador.
     * 
     * @param jugador El jugador que recoge la mejora.
     */
    @Override
    public void agregarMejora(Jugador jugador){
        jugador.aumentarBombasDisponibles();
        //reproducirSonido("mejora.wav");
    }

    /**
     * Obtiene el nombre de la mejora.
     * 
     * @return El nombre de la mejora.
     */
    @Override
    public String getNombre(){
        return nombre;
    }
}
