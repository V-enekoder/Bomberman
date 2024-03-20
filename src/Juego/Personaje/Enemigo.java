package Juego.Personaje;

/**
 * La clase Enemigo representa a un enemigo en el juego Bomberman.
 */
public class Enemigo extends Personaje {
    
    private Movimiento direccion; // La dirección actual del enemigo

    /**
     * Crea una instancia de Enemigo con una posición inicial dada y una dirección asignada aleatoriamente.
     * 
     * @param x        La coordenada x inicial del enemigo.
     * @param y        La coordenada y inicial del enemigo.
     * @param vertical Indica si el movimiento del enemigo será vertical o horizontal.
     */
    public Enemigo(int x, int y, boolean vertical) {
        super(x, y); // Inicializa la posición del enemigo
        direccion = asignarDireccion(vertical); // Asigna una dirección aleatoria
    }

    /**
     * Asigna una dirección aleatoria al enemigo, vertical u horizontal.
     * 
     * @param vertical Indica si el movimiento del enemigo será vertical o horizontal.
     * @return La dirección asignada al enemigo.
     */
    private Movimiento asignarDireccion(boolean vertical) {
        int pick = (int) (Math.random() * (Movimiento.values().length - 2)); // Selecciona un valor aleatorio para la dirección
        if (vertical) 
            return Movimiento.values()[pick]; // Retorna una dirección vertical aleatoria
        else
            return Movimiento.values()[pick + 2]; // Retorna una dirección horizontal aleatoria
    }
    
    /**
     * Obtiene la dirección actual del enemigo.
     * 
     * @return La dirección actual del enemigo.
     */
    public Movimiento getDireccion() {
        return direccion; // Retorna la dirección actual del enemigo
    }

    /**
     * Cambia la dirección del enemigo.
     * El enemigo cambia su dirección vertical por horizontal y viceversa.
     */
    public void cambiarDireccion() {
        switch (direccion) {
            case ABAJO:
                direccion = Movimiento.ARRIBA;
                break;
            case ARRIBA:
                direccion = Movimiento.ABAJO;
                break;
            case IZQUIERDA:
                direccion = Movimiento.DERECHA;
                break;
            case DERECHA:
                direccion = Movimiento.IZQUIERDA;
                break;        
        }
    }
}
