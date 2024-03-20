package Juego.Mejora;

import Juego.Personaje.Jugador;

/**
 * La clase AumentoVidas representa una mejora que aumenta el número de vidas de un jugador en el juego Bomberman.
 * Cuando un jugador recoge esta mejora, se le añade una vida adicional, lo que le permite sobrevivir más tiempo en el juego.
 */
public class AumentoVidas extends Mejora {
    
    /**
     * Crea una nueva instancia de AumentoVidas en la posición especificada en el tablero.
     * 
     * @param fila La fila en la que se encuentra la mejora.
     * @param columna La columna en la que se encuentra la mejora.
     */
    public AumentoVidas(int fila, int columna){
    	super(fila,columna);
        nombre = "Aumento de Vidas";
    }
    
    /**
     * Agrega la mejora al jugador, aumentando el número de vidas.
     * 
     * @param jugador El jugador que recoge la mejora.
     */
    @Override
    public void agregarMejora(Jugador jugador){
        jugador.aumentarVidas();
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
