package Juego.Packet;

import Server.UDP.Cliente;

public class Packet00Ingreso extends Packet{

    private int color;

    public Packet00Ingreso(byte[] datos){
        super(00);
        this.nombre = leerInformacion(datos);
    }

    public Packet00Ingreso(String nombre, int color){
        super(00);
        this.nombre = nombre;
        this.color = color;
    }

    public void enviar(Cliente cliente){ // Desde un cliente al servidor
        cliente.enviarServidor(getDatos());
    } 

    public byte[] getDatos() {
        String datos = String.format("%02d%d%s", id,getColor(),getNombre()); // Formato: ID + color + Nombre
        return datos.getBytes();
    }
    
    public int getColor() {
        return color;
    }
}
