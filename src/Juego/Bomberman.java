package Juego;

import javax.swing.*;

import Juego.Packet.Packet00Ingreso;
import Juego.Packet.Packet.packet;
import Juego.Personaje.Jugador;
import Juego.Personaje.JugadorMJ;
import Server.*;
import Server.UDP.Cliente;
import Server.UDP.Servidor;
import graficos.MenuLogueo;
import graficos.MenuPrincipal;

import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unused")
public class Bomberman{ //Menu con modo ataque, defensa y equilibrado

    private final int TIME_STEP = 30;
    private Timer clockTimer;
    private Servidor socketServidor;
    private int contador_mejoras = 1000*20;
    private  boolean inicio = false;

    public Bomberman(){
    }

    public static void main(String[] args) {
        Bomberman juego = new Bomberman();
        //juego.startServer();
        MenuPrincipal v1 = new MenuPrincipal();
    }

    public void startServer(){
        this.socketServidor = new Servidor(5000);
        Thread servidorThread = new Thread(socketServidor);
        servidorThread.start();

    }

    public void startClient() {
        Cliente cliente = new Cliente();
        Thread clienteThread = new Thread(cliente);
        clienteThread.start();
    }
 
    public void startGame(Tablero tablero/*, InterfazGrafica GUI*/) {

        Action doOneStep = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                tick(tablero/*,GUI*/);
            }
        };

        clockTimer = new Timer(TIME_STEP, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }

    private void tick(Tablero tablero/*, InterfazGrafica GUI*/) {
        //if (tablero.isGameOver())
        //    SwingUtilities.invokeLater(() -> gameOver(GUI));    
        //gameOver(GUI);
        contador_mejoras -= 30;
        if(contador_mejoras <= 0){
            tablero.generarMejoraAleatoria();
            contador_mejoras = 1000 * 20;
        }

        tablero.moverEnemigos();
        tablero.avanzarCuentaRegresiva();
        tablero.generarExplosion();
        tablero.aplicarExplosion();
        tablero.informarSensores();
        tablero.choqueconEnemigos();
        for(JugadorMJ jugador: tablero.getJugadores())
            if(jugador.isInmune())
                tablero.reducirTiempoInmunidad();
    }

    private void gameOver(InterfazGrafica GUI){
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

    /*public static boolean getInicio() {
        return inicio;
    }

    public static void setInicio(boolean inicio) {
        Bomberman.inicio = inicio;
    }*/

}