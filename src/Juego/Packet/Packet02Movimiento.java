package Juego.Packet;

import Server.UDP.Cliente;
import Server.UDP.Servidor;

public class Packet02Movimiento extends Packet{

    private String nombre;
    public Packet02Movimiento(byte[] datos) { //Recuperando info
        super(02);
        this.nombre = leerInformacion(datos);
    }

    public Packet02Movimiento(String nombre) { //Desde el usuario
        super(02);
        this.nombre = nombre;
    }

    @Override
    public void escribirInformacion(Cliente cliente){
        cliente.enviar(getDatos());
    }

    @Override
    public void escribirInformacion(Servidor servidor){
        servidor.enviarTodosLosClientes(getDatos());
    }

    @Override
    public byte[] getDatos() {
        return ("02" + this.nombre).getBytes();
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
