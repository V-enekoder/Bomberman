package Juego.Packet;

import Server.UDP.Cliente;
import Server.UDP.Servidor;

/**
 * La clase Packet representa un paquete de datos que puede ser enviado desde un cliente al servidor o desde el servidor a todos los clientes conectados en el juego Bomberman.
 */
public class Packet {
    
    /**
     * Enumeración que define los tipos de paquete disponibles con sus respectivos IDs.
     */
    public static enum packet{
        INVALIDO(-1), INGRESO(00), DESCONEXION(01), DERROTA(02), 
        INFORMACION(03), ACTUALIZACION(04), GAMEOVER(05);
        
        private int id;

        /**
         * Crea una instancia de packet con el ID especificado.
         * 
         * @param id El ID del paquete.
         */
        private packet(int id) {
            this.id = id;
        }

        /**
         * Obtiene el ID del paquete.
         * 
         * @return El ID del paquete.
         */
        public int getId() {
            return this.id;
        }
    }

    protected byte id; // ID del paquete
    protected String nombre; // Nombre asociado al paquete

    /**
     * Crea una instancia de Packet con el ID especificado.
     * 
     * @param id El ID del paquete.
     */
    public Packet(int id){
        this.id = (byte) id;
        this.nombre = "";
    }

    /**
     * Envía el paquete desde un cliente al servidor.
     * 
     * @param cliente El cliente que enviará el paquete.
     */
    public void enviar(Cliente cliente){
        cliente.enviarServidor(getDatos());
    } 

    /**
     * Envía el paquete desde el servidor a todos los clientes conectados.
     * 
     * @param servidor El servidor que enviará el paquete.
     */
    public void enviar(Servidor servidor){
        servidor.enviarTodosLosClientes(getDatos());
    }

    /**
     * Obtiene los datos del paquete en formato de bytes.
     * 
     * @return Los datos del paquete en formato de bytes.
     */
    public byte[] getDatos() {
        String datos = String.format("%02d%s", id, getNombre()); // Formato: ID + Nombre
        return datos.getBytes();
    }

    /**
     * Obtiene el ID del paquete.
     * 
     * @return El ID del paquete.
     */
    public byte getId() {
        return id;
    }

    /**
     * Establece el ID del paquete.
     * 
     * @param id El ID del paquete.
     */
    public void setId(byte id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre asociado al paquete.
     * 
     * @return El nombre asociado al paquete.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Establece el nombre asociado al paquete.
     * 
     * @param nombre El nombre asociado al paquete.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Lee la información del paquete a partir de los datos recibidos.
     * 
     * @param datos Los datos del paquete.
     * @return La información del paquete.
     */
    public String leerInformacion(byte[] datos){
        String mensaje = new String(datos).trim();
        return mensaje.substring(3); // Elimina los primeros 3 caracteres del mensaje, que representan el ID
    }

    /**
     * Identifica el tipo de paquete a partir del ID especificado.
     * 
     * @param id El ID del paquete.
     * @return El tipo de paquete identificado.
     */
    public static packet identificarTipo(int id){
        for(packet packet: packet.values()){
            if(packet.getId() == id)
                return packet;
        }
        return packet.INVALIDO; // Retorna el tipo de paquete como INVÁLIDO si no se encuentra ningún tipo correspondiente al ID especificado
    }
}
