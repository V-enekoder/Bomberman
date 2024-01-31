package game;

import javax.swing.*;
import java.awt.event.ActionEvent;
/*import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;*/

public class Bomberman{

    private static final int TIME_STEP = 30;
    private static int ancho = 15;
    private static int alto = 15;
    private static int enemigos = 10;
    private static Timer clockTimer;
	public static int contador;

    public static void main(String[] args){
		startGame();
    }

    public static void startGame(){
		Tablero tablero = new Tablero(ancho, alto, enemigos);
	 	InterfazGrafica frame = new InterfazGrafica("Bomberman", tablero);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tablero.agregarSensor(frame.getBombermanComponent());

		Action doOneStep = new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				tick(frame, tablero);
			}
		};

		clockTimer = new Timer(TIME_STEP, doOneStep);
		clockTimer.setCoalesce(true);
		clockTimer.start();
    }

    private static void tick(InterfazGrafica frame, Tablero tablero){
	
		if (tablero.isGameOver())
		   	gameOver(frame, tablero);
	
		tablero.moverEnemigos();
		tablero.avanzarCuentaRegresiva();
		tablero.generarExplosion();
		tablero.aplicarExplosion();
		tablero.informarSensores();
    }

    private static void gameOver(InterfazGrafica frame, Tablero tablero){
		clockTimer.stop();
		frame.dispose();
    }
}
