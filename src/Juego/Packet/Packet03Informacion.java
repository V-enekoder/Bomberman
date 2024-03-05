package Juego.Packet;

import Server.UDP.Cliente;

public class Packet03Informacion extends Packet{

    String colores;

    public Packet03Informacion(){
        super(03);
    }

    public Packet03Informacion(String colores){
        super(03);
        this.colores = colores;
    }

    public Packet03Informacion(byte[] datos){
        super(03);
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
