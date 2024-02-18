package Juego.Packet;

import Server.Cliente;
import Server.Servidor;

public class Packet00Ingreso extends Packet{

    private String nombre;
    public Packet00Ingreso(byte[] datos) { //Recuperando info
        super(00);
        this.nombre = leerInformacion(datos);
    }

    public Packet00Ingreso(String nombre) { //Desde el usuario
        super(00);
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
        return ("00" + this.nombre).getBytes();
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}