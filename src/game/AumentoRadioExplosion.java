package game;

/**
 * This class extends PowerUp and receives fundamental methods such as getters for its coordinates and size. This class
 * has an agregarMejora-method which adjusts the bombRadius of the jugador.
 */
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
