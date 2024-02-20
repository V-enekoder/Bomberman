package Server.UDP;

import Juego.Packet.Packet00Ingreso;
import Juego.Packet.Packet01Desconexion;
import Juego.Packet.Packet02Derrota;
import Juego.Packet.Packet.packet;

public class Prueba {
    
    public static void main(String[] args) {
        Packet02Derrota prueba = new Packet02Derrota("Victor");
        String mensaje = new String(prueba.getDatos());
        System.out.println(mensaje);
    }
}
