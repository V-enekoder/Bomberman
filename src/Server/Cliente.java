package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Cliente implements Runnable {

    private final int PUERTO_SERVIDOR;
    private byte[] datos;
    private DatagramSocket socket;
    private InetAddress direccionServidor;
    private volatile boolean running = true;
    
    public Cliente(){
        this.PUERTO_SERVIDOR = 5000;
        this.datos = new byte[1024];
        try {
            this.socket = new DatagramSocket();
            this.direccionServidor = InetAddress.getByName("localhost");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        //while(running){
            
            Packet00Ingreso ingreso = new Packet00Ingreso("Eldesbaratamala");
            ingreso.escribirInformacion(this);

            /*
            DatagramPacket recibido = recibir(datos);

            String mensaje = new String(recibido.getData(), 0, recibido.getLength());
            System.out.println("Servidor > " + mensaje);

            datos = "Sigue".getBytes();
    
            enviar(datos);*/
        //}
        socket.close();
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

