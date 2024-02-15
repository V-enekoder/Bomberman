package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import Server.Packet.TiposPacket;
import game.Bomberman;

public class Servidor implements Runnable{
    private final int PUERTO;
    private byte[] datos;
    private DatagramSocket socket;
    private volatile boolean running = true;
    private List<JugadorMJ> jugadoresConectados = new ArrayList<JugadorMJ>();
    private Bomberman juego;

    public Servidor(Bomberman juego){
        this.PUERTO = 5000;
        this.datos = new byte[1024];
        this.juego = juego;
        try {
            this.socket = new DatagramSocket(PUERTO);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while(running){

            DatagramPacket recibido = recibir(datos);

            analizarPacket(recibido.getData(),recibido.getAddress(),recibido.getPort());

            /*String mensaje = new String(recibido.getData(), 0, recibido.getLength());
            System.out.println("Cliente > " + mensaje);

            datos = "Funcionando".getBytes();
    
            enviar(datos,recibido.getAddress(),recibido.getPort());*/
        }
        socket.close();
    }

    private void analizarPacket(byte[] data, InetAddress address, int port) {
        String mensaje = new String(data).trim();
        int id = Integer.parseInt(mensaje.substring(0, 2));
        TiposPacket tipo = Packet.identificarTipo(id);//TiposPacket.buscarPacket(Integer.parseInt(mensaje.substring(0,2)));
        switch(tipo){
            default:
            case INVALIDO:
                break;
            case INGRESO:
                Packet00Ingreso ingreso = new Packet00Ingreso(data);
                System.out.println("[Se ha conectado "+address.getHostAddress()+" ; "+port+" ] "+ingreso.getNombre() 
                    +" se ha conectado exitosamente");
                juego.agregarJugador();
                break;
            case DESCONEXION:
                break;
        }
    }

    public DatagramPacket recibir(byte[] datos){
        DatagramPacket paquete = new DatagramPacket(datos,datos.length);
        try {
            socket.receive(paquete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paquete;
    }

    public void enviar(byte[] datos, InetAddress direccionCliente, int puertoCliente){
        DatagramPacket respuesta = new DatagramPacket(datos, datos.length,direccionCliente,puertoCliente);
        try {
            socket.send(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stop() {
        running = false;
    }
    public void enviarTodosLosClientes(byte[] datos) {
        for(JugadorMJ jugador: jugadoresConectados){
            enviar(datos,jugador.getDireccionIP(), jugador.getPuerto());
        }

    }
}