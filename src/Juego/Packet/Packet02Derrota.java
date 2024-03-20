package Juego.Packet;

/**
 * La clase Packet02Derrota representa un paquete de derrota enviado desde un cliente al servidor en el juego Bomberman.
 */
public class Packet02Derrota extends Packet{
    private int idJugador; // Identificador del jugador que ha sido derrotado

    /**
     * Crea una instancia de Packet02Derrota a partir de los datos recibidos.
     * 
     * @param datos Los datos recibidos.
     */
    public Packet02Derrota(byte[] datos) {
        super(02); // Establece el ID del paquete de derrota como 02
        this.nombre = leerInformacion(datos); // Lee el nombre de usuario del paquete de derrota
    }

    /**
     * Crea una instancia de Packet02Derrota con el nombre y ID de jugador especificados.
     * 
     * @param nombre El nombre de usuario.
     * @param id El ID del jugador.
     */
    public Packet02Derrota(String nombre, int id) {
        super(02); // Establece el ID del paquete de derrota como 02
        this.nombre = nombre; // Establece el nombre de usuario
        this.idJugador = id; // Establece el ID del jugador
    }
    
    /**
     * Obtiene el ID del jugador que ha sido derrotado.
     * 
     * @return El ID del jugador que ha sido derrotado.
     */
    public int getIdJugador() {
        return idJugador;
    }

    /**
     * Establece el ID del jugador que ha sido derrotado.
     * 
     * @param idJugador El ID del jugador que ha sido derrotado.
     */
    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }
}
