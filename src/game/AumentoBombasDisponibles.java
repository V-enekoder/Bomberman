package game;

/**
 * This class extends PowerUp and receives fundamental methods such as getters for its coordinates and size. This class
 * has an agregarMejora-method which adjusts the bombCount of the jugador.
 */

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
