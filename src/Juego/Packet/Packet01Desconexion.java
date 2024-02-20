package Juego.Packet;

public class Packet01Desconexion extends Packet{

    public Packet01Desconexion(byte[] datos) {
        super(01);
        this.nombre = leerInformacion(datos);
    }

    public Packet01Desconexion(String nombre) {
        super(01);
        this.nombre = nombre;
    }
}
