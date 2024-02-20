package Juego.Packet;

public class Packet00Ingreso extends Packet{

    public Packet00Ingreso(byte[] datos){
        super(00);
        this.nombre = leerInformacion(datos);
    }

    public Packet00Ingreso(String nombre){
        super(00);
        this.nombre = nombre;
    }
}
