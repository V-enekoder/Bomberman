package Juego.Mejoras;

import Juego.Personaje.Jugador;

public class AumentoVelocidad extends Mejora {
    
    public AumentoVelocidad(int fila, int columna) {
    	super(fila,columna);
        nombre = "Aumento de Velocidad";
    }
    
    @Override
    public void agregarMejora(Jugador jugador){
        jugador.aumentarVelocidad();
    }

    @Override
    public String getNombre(){
	    return nombre;
    }
}
