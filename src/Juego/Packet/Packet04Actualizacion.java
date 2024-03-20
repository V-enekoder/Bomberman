package Juego.Packet;

import Server.UDP.Cliente;

/**
 * La clase Packet04Actualizacion representa un paquete de actualización enviado desde un cliente al servidor en el juego Bomberman.
 */
public class Packet04Actualizacion extends Packet{

    private String colores; // Colores de los jugadores

    /**
     * Crea una instancia de Packet04Actualizacion con el ID del paquete de actualización establecido como 04.
     */
    public Packet04Actualizacion(){
        super(04); // Establece el ID del paquete de actualización como 04
    }

    /**
     * Crea una instancia de Packet04Actualizacion con los colores de los jugadores especificados.
     * 
     * @param colores Los colores de los jugadores.
     */
    public Packet04Actualizacion(String colores){
        super(04); // Establece el ID del paquete de actualización como 04
        this.colores = colores; // Establece los colores de los jugadores
    }

    /**
     * Crea una instancia de Packet04Actualizacion a partir de los datos recibidos.
     * 
     * @param datos Los datos recibidos.
     */
    public Packet04Actualizacion(byte[] datos){
        super(04); // Establece el ID del paquete de actualización como 04
        this.colores = leerInformacion(datos); // Lee los colores de los jugadores del paquete de actualización
    }
    
    /**
     * Envía el paquete de actualización al servidor desde un cliente.
     * 
     * @param cliente El cliente que envía el paquete.
     */
    public void enviar(Cliente cliente){ // Desde un cliente al servidor
        cliente.enviarServidor(getInformacion());
    }

    /**
     * Obtiene los datos del paquete de actualización.
     * 
     * @return Los datos del paquete de actualización.
     */
    public byte[] getInformacion() {
        String datos = String.format("%02d%s", id, this.colores); // Formato: ID + colores
        return datos.getBytes();
    }

    /**
     * Obtiene los colores de los jugadores.
     * 
     * @return Los colores de los jugadores.
     */
    public String getColores() {
        return colores;
    }

    /**
     * Establece los colores de los jugadores.
     * 
     * @param colores Los colores de los jugadores.
     */
    public void setColores(String colores) {
        this.colores = colores;
    }

    /**
     * Lee la información del paquete de actualización.
     * 
     * @param datos Los datos recibidos.
     * @return La información del paquete de actualización.
     */
    public String leerInformacion(byte[] datos){
        String mensaje = new String(datos).trim();
        return mensaje.substring(2); // Ignora los primeros dos caracteres (ID)
    }
}
