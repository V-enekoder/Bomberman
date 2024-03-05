package Juego.Packet;

import Server.UDP.Cliente;
import Server.UDP.Servidor;

public abstract class Packet {
    
    public static enum packet{
        INVALIDO(-1), INGRESO(00), DESCONEXION(01),DERROTA(02), 
        INFORMACION(03), ACTUALIZACION(04);
        
        private int id;

        private packet(int id) {
            this.id = id;
        }
        public int getId() {
            return this.id;
        }
    }

    protected byte id;
    protected String nombre;

    public Packet(int id){
        this.id = (byte) id;
        this.nombre = "";
    }

    public void enviar(Cliente cliente){ // Desde un cliente al servidor
        cliente.enviarServidor(getDatos());
    } 

    public void enviar(Servidor servidor){ //Se manda a toods los clientes desde el servidor
        servidor.enviarTodosLosClientes(getDatos());
    }

    public byte[] getDatos() {
        String datos = String.format("%02d%s", id, getNombre()); // Formato: ID + Nombre
        return datos.getBytes();
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String leerInformacion(byte[] datos){
        String mensaje = new String(datos).trim();
        return mensaje.substring(3);
    }

    public static packet identificarTipo(int id){
        for(packet packet: packet.values()){
            if(packet.getId() == id)
                return packet;
        }
        return packet.INVALIDO;
    }
}
