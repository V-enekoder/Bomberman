package Juego.Packet;

/**
 * La clase Packet01Desconexion representa un paquete de desconexión enviado desde un cliente al servidor en el juego Bomberman.
 */
public class Packet01Desconexion extends Packet{
    private int idJugador; // Identificador del jugador que se desconecta

    /**
     * Crea una instancia de Packet01Desconexion a partir de los datos recibidos.
     * 
     * @param datos Los datos recibidos.
     */
    public Packet01Desconexion(byte[] datos) {
        super(01); // Establece el ID del paquete de desconexión como 01
        this.nombre = leerInformacion(datos); // Lee el nombre de usuario del paquete de desconexión
    }

    /**
     * Crea una instancia de Packet01Desconexion con el nombre y ID de jugador especificados.
     * 
     * @param nombre El nombre de usuario.
     * @param id El ID del jugador.
     */
    public Packet01Desconexion(String nombre, int id) {
        super(01); // Establece el ID del paquete de desconexión como 01
        this.nombre = nombre; // Establece el nombre de usuario
        this.idJugador = id; // Establece el ID del jugador
    }

    /**
     * Obtiene el ID del jugador que se desconecta.
     * 
     * @return El ID del jugador que se desconecta.
     */
    public int getIdJugador() {
        return idJugador;
    }

    /**
     * Establece el ID del jugador que se desconecta.
     * 
     * @param idJugador El ID del jugador que se desconecta.
     */
    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }
}

