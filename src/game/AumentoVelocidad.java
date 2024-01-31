package game;

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
