package Juego.Packet;

public class Packet01Desconexion extends Packet{
    private int idJugador;

    public Packet01Desconexion(byte[] datos) {
        super(01);
        this.nombre = leerInformacion(datos);
    }

    public Packet01Desconexion(String nombre,int id) {
        super(01);
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
