package Juego.Packet;

public class Packet02Derrota extends Packet{
    private int idJugador;
    public Packet02Derrota(byte[] datos) {
        super(02);
        this.nombre = leerInformacion(datos);
    }

    public Packet02Derrota(String nombre,int id) {
        super(02);
        this.nombre = nombre;
        this.idJugador = id;
    }
    
    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

}
