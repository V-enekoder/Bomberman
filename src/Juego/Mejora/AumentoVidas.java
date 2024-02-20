package Juego.Mejora;

import Juego.Personaje.Jugador;

public class AumentoVidas extends Mejora{ //Mostrar aumento de vida. Una mini animación
    
    public AumentoVidas(int fila, int columna){
    	super(fila,columna);
        nombre = "Aumento de Vidas";
    }
    
    @Override
    public void agregarMejora(Jugador jugador){
        jugador.aumentarVidas();
    }
    
    @Override
    public String getNombre(){
	    return nombre;
    }
}
