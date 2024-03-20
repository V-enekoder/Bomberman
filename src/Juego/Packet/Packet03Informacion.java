package Juego.Packet;

import Server.UDP.Cliente;

/**
 * La clase Packet03Informacion representa un paquete de información enviado desde un cliente al servidor en el juego Bomberman.
 */
public class Packet03Informacion extends Packet{

    private String colores; // Colores de los jugadores

    /**
     * Crea una instancia de Packet03Informacion con el ID del paquete de información establecido como 03.
     */
    public Packet03Informacion(){
        super(03); // Establece el ID del paquete de información como 03
    }

    /**
     * Crea una instancia de Packet03Informacion con los colores de los jugadores especificados.
     * 
     * @param colores Los colores de los jugadores.
     */
    public Packet03Informacion(String colores){
        super(03); // Establece el ID del paquete de información como 03
        this.colores = colores; // Establece los colores de los jugadores
    }

    /**
     * Crea una instancia de Packet03Informacion a partir de los datos recibidos.
     * 
     * @param datos Los datos recibidos.
     */
    public Packet03Informacion(byte[] datos){
        super(03); // Establece el ID del paquete de información como 03
        this.colores = leerInformacion(datos); // Lee los colores de los jugadores del paquete de información
    }
    
    /**
     * Envía el paquete de información al servidor desde un cliente.
     * 
     * @param cliente El cliente que envía el paquete.
     */
    public void enviar(Cliente cliente){ // Desde un cliente al servidor
        cliente.enviarServidor(getInformacion());
    }

    /**
     * Obtiene los datos del paquete de información.
     * 
     * @return Los datos del paquete de información.
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
     * Lee la información del paquete de información.
     * 
     * @param datos Los datos recibidos.
     * @return La información del paquete de información.
     */
    public String leerInformacion(byte[] datos){
        String mensaje = new String(datos).trim();
        return mensaje.substring(2); // Ignora los primeros dos caracteres (ID)
    }
}
