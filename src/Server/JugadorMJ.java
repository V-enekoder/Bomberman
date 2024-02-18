package Server;

import java.net.InetAddress;

import Juego.ComponenteGrafico;
import Juego.Tablero;
import Juego.Personaje.Jugador;

public class JugadorMJ extends Jugador{
    private InetAddress direccionIP; //Encapsular
    private int puerto;

    public JugadorMJ(ComponenteGrafico bombermanComponent, Tablero tablero, int[] posicion, int id,
        String nombre,InetAddress direccionIP ,int puerto){
            super(bombermanComponent,tablero,posicion,id, nombre);
            this.direccionIP = direccionIP;
            this.puerto = puerto;
    }
    public JugadorMJ(ComponenteGrafico bombermanComponent, Tablero tablero, int[] posicion, int id,
        String nombre){
            super(bombermanComponent,tablero,posicion,id, nombre);
            this.direccionIP = null;
            this. puerto = -1;
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
