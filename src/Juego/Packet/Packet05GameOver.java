package Juego.Packet;

/**
 * La clase Packet05GameOver representa un paquete de fin de juego en el juego Bomberman.
 */
public class Packet05GameOver extends Packet{

    /**
     * Crea una instancia de Packet05GameOver con el ID del paquete de fin de juego establecido como 05.
     */
    public Packet05GameOver() {
        super(05); // Establece el ID del paquete de fin de juego como 05
    }

    /**
     * Crea una instancia de Packet05GameOver a partir de los datos recibidos.
     * 
     * @param datos Los datos recibidos.
     */
    public Packet05GameOver(byte[] datos){
        super(05); // Establece el ID del paquete de fin de juego como 05
    }
}
