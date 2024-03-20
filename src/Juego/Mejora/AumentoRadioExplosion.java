package Juego.Mejora;

import Juego.Personaje.Jugador;

/**
 * La clase AumentoRadioExplosion representa una mejora que aumenta el radio de explosión de las bombas para un jugador en el juego Bomberman.
 * Cuando un jugador recoge esta mejora, se incrementa el radio de explosión de las bombas que coloque.
 */
public class AumentoRadioExplosion extends Mejora{

    /**
     * Crea una nueva instancia de AumentoRadioExplosion en la posición especificada en el tablero.
     * 
     * @param fila La fila en la que se encuentra la mejora.
     * @param columna La columna en la que se encuentra la mejora.
     */
    public AumentoRadioExplosion(int fila, int columna){
        super(fila,columna);
        nombre = "Aumento de Radio de Explosión";
    }
    
    /**
     * Agrega la mejora al jugador, aumentando el radio de explosión de las bombas que coloque.
     * 
     * @param jugador El jugador que recoge la mejora.
     */
    @Override
    public void agregarMejora(Jugador jugador){
        jugador.aumentarRadioExplosion();
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
