package Juego.Personaje;

import java.net.InetAddress;
import Juego.InterfazGrafica;
import Juego.Tablero;

public class JugadorMJ extends Jugador{
    private InetAddress direccionIP;
    private int puerto;

    public JugadorMJ(InterfazGrafica GUI, Tablero tablero, int[] posicion, int id,
        String nombre,InetAddress direccionIP ,int puerto){
            super(GUI,tablero,posicion,id, nombre);
            this.direccionIP = direccionIP;
            this.puerto = puerto;
    }

    public InetAddress getDireccionIP() {
        return this.direccionIP;
    }

    public void setDireccionIP(InetAddress direccionIP) {
        this.direccionIP = direccionIP;
    }

    public int getPuerto() {
        return this.puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
}
