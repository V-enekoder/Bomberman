package Juego;

import javax.swing.*;

import Juego.Personaje.Jugador;
import Server.Cliente;
import Server.JugadorMJ;
import Server.Packet00Ingreso;
import Server.Servidor;

import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

public class Bomberman implements Runnable { //Menu con modo ataque, defensa y equilibrado
//hay que extenderlo a multijugador local
    private final int TIME_STEP = 30;
    private Timer clockTimer;
    private Tablero tablero;
    private InterfazGrafica GUI;
    //private boolean running;
    private Cliente socketCliente;
    private Cliente socketCliente1;
    private Servidor socketServidor;
    private Thread gameThread;
	private static ArrayList<int[]> inicio = new ArrayList<>();
    private static ArrayList<Integer> id = new ArrayList<>();

	static {
        inicio.add(new int[]{60, 60});
        inicio.add(new int[]{60, 540});
        inicio.add(new int[]{540, 60});
        inicio.add(new int[]{540, 540});
        for(int i = 0; i < 4; i++){
            id.add(i);
        }
    }

    public Bomberman(int ancho, int alto, int enemigos) {
        this.tablero = new Tablero(ancho, alto, enemigos);
        this.GUI = new InterfazGrafica("Bomberman", tablero);
        GUI.setLocationRelativeTo(null);
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tablero.agregarSensor(GUI.getBombermanComponent());
        this.socketCliente = new Cliente();
        this.socketCliente1 = new Cliente();
        this.socketServidor = new Servidor(this);
        //this.running = false;
    }

    public static void main(String[] args){
        boolean servidor = true;
        Bomberman bomberman = new Bomberman(15, 15, 10);
        bomberman.startGameThread(servidor);
    }

    public synchronized void startGameThread(boolean servidor) {
        //running = true;
        this.gameThread = new Thread(this);
        gameThread.start();
        if (servidor) {
            Thread servidorThread = new Thread(socketServidor);
            servidorThread.start();
            Thread clienteThread = new Thread(socketCliente);
            clienteThread.start();
            Thread clienteThread1 = new Thread(socketCliente1);
            clienteThread1.start();
        }
    }

    public void agregarJugador(InetAddress direccion, int port){
        Random random = new Random();
        int numeroAleatorio = random.nextInt(inicio.size());
        int[] casillas = inicio.get(numeroAleatorio);
        JugadorMJ jugador = tablero.crearJugador(GUI.getBombermanComponent(), tablero, casillas,id.get(0), direccion, port);
        GUI.getBombermanComponent().agregarJugador(jugador);
        inicio.remove(numeroAleatorio);
        id.remove(0);
    }
    
    public void run() {
        startGame();
    }

    public void startGame() {
        Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        };

        clockTimer = new Timer(TIME_STEP, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }

    private void tick() {
        if (tablero.isGameOver())
            gameOver();

        tablero.moverEnemigos();
        tablero.avanzarCuentaRegresiva();
        tablero.generarExplosion();
        tablero.aplicarExplosion();
        tablero.informarSensores();

        for(Jugador jugador: tablero.getJugadores())
            jugador.chocaConEnemigo(tablero);
        
        tablero.reducirTiempoInmunidad();
    }

    private void gameOver(){
        clockTimer.stop();
        GUI.dispose();
    }
}