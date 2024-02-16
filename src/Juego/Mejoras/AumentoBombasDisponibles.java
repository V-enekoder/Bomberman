package Juego.Mejoras;

import Juego.Personaje.Jugador;

public class AumentoBombasDisponibles extends Mejora{

    public AumentoBombasDisponibles(int fila, int columna){
    	super(fila,columna);
        nombre = "Aumento de Bombas Disponibles";
    }
    
    @Override
    public void agregarMejora(Jugador jugador){
        jugador.aumentarBombasDisponibles();
    }

    @Override
    public String getNombre(){
	    return nombre;
    }
}
