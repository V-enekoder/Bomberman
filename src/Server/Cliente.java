package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import Juego.Bomberman;
import Server.Packet.packet;

public class Cliente implements Runnable {

    private final int PUERTO_SERVIDOR;
    private byte[] datos;
    private DatagramSocket socket;
    private InetAddress direccionServidor;
    private volatile boolean running = true;
    @SuppressWarnings("unused")
    private Bomberman juego;

    public Cliente(Bomberman juego){
        this.PUERTO_SERVIDOR = 5000;
        this.datos = new byte[1024];
        this.juego = juego;
        try {
            this.socket = new DatagramSocket();
            this.direccionServidor = InetAddress.getByName("localhost");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(running){
            DatagramPacket recibido = recibir();
            analizarPacket(recibido.getData(),recibido.getAddress(),recibido.getPort());
        }
        socket.close();
    }

    
    private void analizarPacket(byte[] data, InetAddress address, int port) {
        String mensaje = new String(data).trim();
        int id = Integer.parseInt(mensaje.substring(0, 2));
        packet tipo = Packet.identificarTipo(id);
        Packet packet = null;
        
        switch(tipo){
            default:
            case INVALIDO:
                break;
            case INGRESO:
                packet = new Packet00Ingreso(data);
                System.out.println(((Packet00Ingreso)packet).getNombre() +" ha ingresado");
                //juego.agregarJugador(/*address,port*/null,-1,((Packet00Ingreso)packet).getNombre());
                break;
            case DESCONEXION:
            packet = new Packet01Desconexion(data);
                    
            System.out.println("["+address.getHostAddress()+" ; "+port+" ] "
                +((Packet01Desconexion)packet).getNombre() + " se ha ido");
            //tablero.removerJugador(((Packet01Desconexion)packet).getNombre())
            //Probablemente agarre por argumento al que va a agregar y listo
                break;
        }
    }

    public DatagramPacket recibir(){
        DatagramPacket paquete = new DatagramPacket(datos,datos.length);
        try {
            socket.receive(paquete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paquete;
    }

    public void enviar(byte[] datos){
        DatagramPacket respuesta = new DatagramPacket(datos, datos.length,direccionServidor,PUERTO_SERVIDOR);
        try {
            socket.send(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stop() {
        running = false;
    }
}

