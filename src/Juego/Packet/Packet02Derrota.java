package Juego.Packet;

public class Packet02Derrota extends Packet{

    public Packet02Derrota(byte[] datos) {
        super(02);
        this.nombre = leerInformacion(datos);
    }

    public Packet02Derrota(String nombre) {
        super(02);
        this.nombre = nombre;
    }
}
