package game;

import javax.swing.*;

import Server.Cliente;
import Server.Servidor;

import java.awt.event.ActionEvent;
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
    private Servidor socketServidor;
    private Thread gameThread;
	private static ArrayList<int[]> inicio = new ArrayList<>();

	static {
        inicio.add(new int[]{60, 60});
        inicio.add(new int[]{60, 540});
        inicio.add(new int[]{540, 60});
        inicio.add(new int[]{540, 540});
    }

    public Bomberman(int ancho, int alto, int enemigos) {
        this.tablero = new Tablero(ancho, alto, enemigos);
        this.GUI = new InterfazGrafica("Bomberman", tablero);

        Random random = new Random();
        int numeroAleatorio = random.nextInt(inicio.size());
        int[] casillas = inicio.get(numeroAleatorio);
        tablero.crearJugador(GUI.getBombermanComponent(), tablero, casillas,0);
        inicio.remove(numeroAleatorio);

        GUI.setLocationRelativeTo(null);
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tablero.agregarSensor(GUI.getBombermanComponent());
        this.socketCliente = new Cliente();
        this.socketServidor = new Servidor();
        //this.running = false;
    }

    public static void main(String[] args){
        //boolean servidor = true;
        Bomberman bomberman = new Bomberman(15, 15, 10);
        bomberman.startGame();
        //bomberman.startGameThread(!servidor);
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

        }
    }

    public void run() {
        startGame();
    }

    public void startGame() {
        //socketCliente.enviar("hola".getBytes());
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
    }

    private void gameOver(){
        clockTimer.stop();
        GUI.dispose();
        /*if (gameThread != null)
            gameThread.interrupt();    
        if (socketServidor != null)
            socketServidor.stop();
            //socketServidor.stop();
        if (socketCliente != null){
            socketCliente.stop();
        }*/
    }
}