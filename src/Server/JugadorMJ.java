package Server;

import java.net.InetAddress;

import Juego.ComponenteGrafico;
import Juego.Jugador;
import Juego.Tablero;

public class JugadorMJ extends Jugador{
    public InetAddress direccionIP;
    public int puerto;

    public JugadorMJ(ComponenteGrafico bombermanComponent, Tablero tablero, int[] posicion, int id,
        InetAddress direccionIP ,int puerto){
            super(bombermanComponent,tablero,posicion,id);
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
