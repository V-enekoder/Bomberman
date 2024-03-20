package Juego.Mejora;

import Juego.Personaje.Jugador;

/**
 * La clase AumentoVelocidad representa una mejora que aumenta la velocidad de movimiento de un jugador en el juego Bomberman.
 * Cuando un jugador recoge esta mejora, su velocidad de movimiento aumenta, permitiéndole moverse más rápido por el tablero.
 */
public class AumentoVelocidad extends Mejora {
    
    /**
     * Crea una nueva instancia de AumentoVelocidad en la posición especificada en el tablero.
     * 
     * @param fila La fila en la que se encuentra la mejora.
     * @param columna La columna en la que se encuentra la mejora.
     */
    public AumentoVelocidad(int fila, int columna) {
    	super(fila,columna);
        nombre = "Aumento de Velocidad";
    }
    
    /**
     * Agrega la mejora al jugador, aumentando su velocidad de movimiento.
     * 
     * @param jugador El jugador que recoge la mejora.
     */
    @Override
    public void agregarMejora(Jugador jugador){
        jugador.aumentarVelocidad();
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
