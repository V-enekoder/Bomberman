package Juego.Mejoras;

import Juego.Jugador;

public class AumentoVidas extends Mejora{
    
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
