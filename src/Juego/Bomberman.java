package Juego;

import javax.swing.*;

import Juego.Packet.Packet00Ingreso;
import Juego.Packet.Packet.packet;
import Juego.Personaje.Jugador;
import Juego.Personaje.JugadorMJ;
import Server.*;

import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unused")
public class Bomberman implements Runnable { //Menu con modo ataque, defensa y equilibrado
//hay que extenderlo a multijugador local
// Agregar INPUT HANDLER
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
        this.socketCliente = new Cliente(this);
        this.socketCliente1 = new Cliente(this);
        //this.running = false;
    }
//Al intentar instanciar 2 veces, no se crea en el 2do servidor, sino que ingresa al primero.
//Ni idea si esto es bueno o mallo
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
            this.socketServidor = new Servidor(this,5000);
            Thread servidorThread = new Thread(socketServidor);
            servidorThread.start();
        }
        Thread clienteThread = new Thread(socketCliente);
        clienteThread.start();
        Thread clienteThread1 = new Thread(socketCliente1);
        clienteThread1.start();
    }

    public JugadorMJ crearJugador(String nombre){ //Se crean sin port ni inetadrrss, se van a agregar en el server
        Random random = new Random();
        int numeroAleatorio = random.nextInt(inicio.size());
        int[] casillas = inicio.get(numeroAleatorio);

        JugadorMJ jugador = new JugadorMJ(GUI.getBombermanComponent(), tablero, casillas,id.get(0),nombre);
        inicio.remove(numeroAleatorio);
        id.remove(0);
        
        return jugador;
    }
    
    public void run() {
        startGame();
    }
 
    public void startGame() {
        
        //tablero.agregarJugador(crearJugador("Eldesbaratamala"));
        
        Packet00Ingreso ingreso = new Packet00Ingreso("Eldesbaratamala");
        ingreso.escribirInformacion(socketCliente);
        ingreso.setNombre("lamaladesbaratá");
        ingreso.escribirInformacion(socketCliente1);

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


    public int getTIME_STEP() {
        return this.TIME_STEP;
    }


    public Timer getClockTimer() {
        return this.clockTimer;
    }

    public void setClockTimer(Timer clockTimer) {
        this.clockTimer = clockTimer;
    }

    public Tablero getTablero() {
        return this.tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public InterfazGrafica getGUI() {
        return this.GUI;
    }

    public void setGUI(InterfazGrafica GUI) {
        this.GUI = GUI;
    }

    public Cliente getSocketCliente() {
        return this.socketCliente;
    }

    public void setSocketCliente(Cliente socketCliente) {
        this.socketCliente = socketCliente;
    }

    public Cliente getSocketCliente1() {
        return this.socketCliente1;
    }

    public void setSocketCliente1(Cliente socketCliente1) {
        this.socketCliente1 = socketCliente1;
    }

    public Servidor getSocketServidor() {
        return this.socketServidor;
    }

    public void setSocketServidor(Servidor socketServidor) {
        this.socketServidor = socketServidor;
    }

    public Thread getGameThread() {
        return this.gameThread;
    }

    public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }
}