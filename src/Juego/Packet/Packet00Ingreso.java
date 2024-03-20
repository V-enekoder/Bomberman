package Juego.Packet;

import Server.UDP.Cliente;

/**
 * La clase Packet00Ingreso representa un paquete de ingreso enviado desde un cliente al servidor en el juego Bomberman.
 */
public class Packet00Ingreso extends Packet{

    private int color; // Color asociado al paquete de ingreso

    /**
     * Crea una instancia de Packet00Ingreso a partir de los datos recibidos.
     * 
     * @param datos Los datos recibidos.
     */
    public Packet00Ingreso(byte[] datos){
        super(00); // Establece el ID del paquete de ingreso como 00
        this.nombre = leerInformacion(datos); // Lee el nombre de usuario del paquete de ingreso
    }

    /**
     * Crea una instancia de Packet00Ingreso con el nombre y color especificados.
     * 
     * @param nombre El nombre de usuario.
     * @param color El color asociado al usuario.
     */
    public Packet00Ingreso(String nombre, int color){
        super(00); // Establece el ID del paquete de ingreso como 00
        this.nombre = nombre; // Establece el nombre de usuario
        this.color = color; // Establece el color asociado al usuario
    }

    /**
     * Envía el paquete de ingreso desde un cliente al servidor.
     * 
     * @param cliente El cliente que enviará el paquete de ingreso.
     */
    public void enviar(Cliente cliente){ 
        cliente.enviarServidor(getDatos());
    } 

    /**
     * Obtiene los datos del paquete de ingreso en formato de bytes.
     * 
     * @return Los datos del paquete de ingreso en formato de bytes.
     */
    public byte[] getDatos() {
        String datos = String.format("%02d%d%s", id,getColor(),getNombre()); // Formato: ID + color + Nombre
        return datos.getBytes();
    }
    
    /**
     * Obtiene el color asociado al paquete de ingreso.
     * 
     * @return El color asociado al paquete de ingreso.
     */
    public int getColor() {
        return color;
    }
}

