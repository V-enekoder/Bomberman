package Juego;

import javax.swing.*;

import Juego.Packet.Packet00Ingreso;
import Juego.Packet.Packet03Informacion;
import Juego.Packet.Packet05GameOver;
import Juego.Packet.Packet.packet;
import Juego.Personaje.Jugador;
import Juego.Personaje.Jugador;
import Server.*;
import Server.UDP.Cliente;
import Server.UDP.Servidor;
import graficos.MenuLogueo;
import graficos.MenuPrincipal;
import graficos.MenuWin;

import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unused")
/**
 * Clase que representa el juego Bomberman.
 */
public class Bomberman {

    private final int TIME_STEP = 30; // Paso de tiempo del juego en milisegundos
    private Timer clockTimer; // Temporizador para controlar el paso del tiempo en el juego
    private int contador_mejoras = 1000 * 30; // Contador para generar mejoras en intervalos regulares
    private boolean notificado; // Indica si el juego ya ha notificado el resultado del juego

    /**
     * Constructor de la clase Bomberman.
     */
    public Bomberman() {
        notificado = false;
    }

    /**
     * Método principal que inicia el juego Bomberman.
     * 
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        Bomberman juego = new Bomberman();

        if (true)
            // juego.startServer();
            juego.startClient();

    }

    /**
     * Inicia el servidor del juego.
     */
    public void startServer() {
        Servidor socketServidor = new Servidor(5000);
        Thread servidorThread = new Thread(socketServidor);
        servidorThread.start();
    }

    /**
     * Inicia el cliente del juego.
     */
    public void startClient() {
        Cliente cliente = new Cliente();
        Thread clienteThread = new Thread(cliente);
        clienteThread.start();
    }

    /**
     * Inicia el juego con el tablero especificado.
     * 
     * @param tablero El tablero del juego.
     */
    public void startGame(Tablero tablero) {
        Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tick(tablero);
            }
        };
        clockTimer = new Timer(TIME_STEP, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }

    /**
     * Realiza una iteración del juego (tick).
     * 
     * @param tablero El tablero del juego.
     */
    private void tick(Tablero tablero) {
        if (tablero.isGameOver() && !notificado) {
            Cliente c = new Cliente();
            Packet05GameOver resultado = new Packet05GameOver();
            resultado.enviar(c);
            notificado = true;
        }

        contador_mejoras -= 30;
        if (contador_mejoras <= 0) {
            tablero.generarMejoraAleatoria();
            contador_mejoras = 1000 * 30;
        }

        tablero.moverEnemigos();
        tablero.avanzarCuentaRegresiva();
        tablero.generarExplosion();
        tablero.aplicarExplosion();
        tablero.choqueconEnemigos();
        tablero.comprobarVictoria();
        for (Jugador jugador : tablero.getJugadores())
            if (jugador.isInmune())
                tablero.reducirTiempoInmunidad();
        tablero.informarSensores();
    }

    /**
     * Detiene el juego y cierra la interfaz gráfica.
     * 
     * @param GUI La interfaz gráfica del juego.
     */
    private void gameOver(InterfazGrafica GUI) {
        clockTimer.stop();
        GUI.dispose();
    }

    /**
     * Obtiene el paso de tiempo del juego.
     * 
     * @return El paso de tiempo del juego en milisegundos.
     */
    public int getTIME_STEP() {
        return this.TIME_STEP;
    }

    /**
     * Obtiene el temporizador del juego.
     * 
     * @return El temporizador del juego.
     */
    public Timer getClockTimer() {
        return this.clockTimer;
    }

    /**
     * Establece el temporizador del juego.
     * 
     * @param clockTimer El temporizador del juego.
     */
    public void setClockTimer(Timer clockTimer) {
        this.clockTimer = clockTimer;
    }

    /*
     * public static boolean getInicio() {
     * return inicio;
     * }
     * 
     * public static void setInicio(boolean inicio) {
     * Bomberman.inicio = inicio;
     * }
     */

}