package Juego.Packet;

import Server.UDP.Cliente;
import Server.UDP.Servidor;

public abstract class Packet {
    
    public static enum packet{
        INVALIDO(-1), INGRESO(00), DESCONEXION(01);
        
        private int id;

        private packet(int id) {
            this.id = id;
        }
        public int getId() {
            return this.id;
        }
    }

    private byte id;

    public Packet(int id){
        this.id = (byte) id;
    }

    public abstract void escribirInformacion(Cliente cliente); // Se manda a un cliente
    public abstract void escribirInformacion(Servidor servidor); //Se manda a toods los clientes
    public abstract byte[] getDatos();

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String leerInformacion(byte[] datos){
        String mensaje = new String(datos).trim();
        return mensaje.substring(2);
    }

    public static packet identificarTipo(int id){
        for(packet packet: packet.values()){
            if(packet.getId() == id)
                return packet;
        }
        return packet.INVALIDO;
    }

    public static packet buscarPacket(int id){
        try{
            return identificarTipo(id);
        }catch(NumberFormatException e){
            return packet.INVALIDO;
        }
    }

}
