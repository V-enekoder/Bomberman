package Server;

public abstract class Packet {
    
    public static enum TiposPacket{
        INVALIDO(-1), INGRESO(00), DESCONEXION(01);
        
        private int id;

        private TiposPacket(int id) {
            this.id = id;
        }
        public int getId() {
            return this.id;
        }
    }

    public byte id;

    public Packet(int id){
        this.id = (byte) id;
    }

    public abstract void escribirInformacion(Cliente cliente); // Se manda a un cliente
    public abstract void escribirInformacion(Servidor servidor); //Se manda a toods los clientes
    public abstract byte[] getDatos();
    public String leerInformacion(byte[] datos){
        String mensaje = new String(datos).trim();
        return mensaje.substring(2);
    }

    public static TiposPacket identificarTipo(int id){
        for(TiposPacket packet: TiposPacket.values()){
            if(packet.getId() == id)
                return packet;
        }
        return TiposPacket.INVALIDO;
    }

    public static TiposPacket buscarPacket(int id){
        try{
            return identificarTipo(id);
        }catch(NumberFormatException e){
            return TiposPacket.INVALIDO;
        }
    }

}
