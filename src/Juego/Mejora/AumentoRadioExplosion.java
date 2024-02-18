package Juego.Mejora;

import Juego.Personaje.Jugador;

public class AumentoRadioExplosion extends Mejora{

    public AumentoRadioExplosion(int fila, int columna){
	    super(fila,columna);
        nombre = "Aumento de Radio de Explosion";
    }
    
    @Override
    public void agregarMejora(Jugador jugador){
        jugador.aumentarRadioExplosion();
    }

    @Override
    public String getNombre(){
        return nombre;
    }
}
