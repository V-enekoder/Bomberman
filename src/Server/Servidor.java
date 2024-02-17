package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import Juego.Bomberman;
import Server.Packet.packet;

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
        packet tipo = Packet.identificarTipo(id);
        Packet packet = null;
        switch(tipo){
            default:
            case INVALIDO:
                break;
            case INGRESO:
                packet = new Packet00Ingreso(data);
                
                System.out.println("[Se ha conectado "+address.getHostAddress()+" ; "+port+" ] "
                    +((Packet00Ingreso)packet).getNombre() +" exitosamente");
                
                //int[] pos = {60,60};
                //JugadorMJ j = new JugadorMJ(juego.getGUI().getBombermanComponent(),juego.getTablero(),pos
                //,0,((Packet00Ingreso)packet).getNombre(),null,-1/*,address,port*/);
                
                JugadorMJ j = juego.agregarJugador(address,port,((Packet00Ingreso)packet).getNombre());
                //Hay que cambiar lo de arriba por un jugador normal, sin agregarlo para evitar multiplicidades
                //
                /*JugadorMJ j = new JugadorMJ(juego.getGUI().getBombermanComponent(),juego.getTablero(),pos
                    ,0,((Packet00Ingreso)packet).getNombre(),null,-1/*,address,port*///);
                //jugadoresConectados.add(j);
                conectarJugador(j,(Packet00Ingreso)packet);
                break;
            case DESCONEXION:
                break;
        }
    }

    public void conectarJugador(JugadorMJ jugador, Packet00Ingreso ingreso){
        boolean yaConectado = false;
        for(JugadorMJ j: jugadoresConectados){
            if(j.getNombre().equalsIgnoreCase(ingreso.getNombre())){
                /*if(j.direccionIP == null)
                    j.direccionIP = jugador.direccionIP;
                if(j.puerto == -1)
                    j.puerto = jugador.puerto;*/
                yaConectado = true;
           }
           else
                enviar(ingreso.getDatos(),j.direccionIP,j.puerto);
        }
        if(!yaConectado)
            this.jugadoresConectados.add(jugador);
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