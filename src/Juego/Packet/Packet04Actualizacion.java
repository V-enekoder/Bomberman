package Juego.Packet;

import Server.UDP.Cliente;

public class Packet04Actualizacion extends Packet{

    String colores;

    public Packet04Actualizacion(){
        super(04);
    }

    public Packet04Actualizacion(String colores){
        super(04);
        this.colores = colores;
    }

    public Packet04Actualizacion(byte[] datos){
        super(04);
        this.colores = leerInformacion(datos);
    }
    
    public void enviar(Cliente cliente){ // Desde un cliente al servidor
        cliente.enviarServidor(getInformacion());
    }
    public byte[] getInformacion() {
        String datos = String.format("%02d%s", id,this.colores);
        return datos.getBytes();
    }

    public String getColores() {
        return colores;
    }

    public void setColores(String colores) {
        this.colores = colores;
    }

    public String leerInformacion(byte[] datos){
        String mensaje = new String(datos).trim();
        return mensaje.substring(2);
    }
}
