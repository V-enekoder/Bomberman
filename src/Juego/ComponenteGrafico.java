package Juego;

import javax.swing.*;

import Juego.Mejora.Mejora;
import Juego.Personaje.Enemigo;
import Juego.Personaje.Jugador;
import Juego.Personaje.JugadorMJ;

import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;


@SuppressWarnings("unused")
public class ComponenteGrafico extends JComponent implements Sensor{
    // Constants are static by definition.
    private final static int DIMENSION_CELDA = 40;
    private final static int CHARACTER_ADJUSTMENT_FOR_PAINT = 15;
    private final static int MITAD_CELDA = DIMENSION_CELDA/2;
    private final static int BOMB_ADJUSTMENT_1 =5;
    private final static int BOMB_ADJUSTMENT_2 =10;
    // Defining painting parameters
    //private final static int PAINT_PARAMETER_13 = 13;
    private final static int PAINT_PARAMETER_15 = 15;
    //private final static int PAINT_PARAMETER_17 = 17;
    private final static int PAINT_PARAMETER_18 = 18;
    private final static int PAINT_PARAMETER_19 = 19;
    private final static int PAINT_PARAMETER_20 = 20;
    //private final static int PAINT_PARAMETER_24 = 24;
    private final Tablero tablero;
    private final AbstractMap<Celda, Color> mapaColores;

    public ComponenteGrafico(Tablero tablero) {
		this.tablero = tablero;
		mapaColores = new EnumMap<>(Celda.class);
		mapaColores.put(Celda.PISO, Color.GREEN);
		mapaColores.put(Celda.PARED, Color.BLACK);
		mapaColores.put(Celda.BLOQUE, Color.RED);
    }

    // This method is static since each square has the same size.
    public static int getSquareSize() {
		return DIMENSION_CELDA;
    }

    // This method is static since each square has the same size.
    public static int getSquareMiddle() {
		return MITAD_CELDA;
    }

    public Dimension getPreferredSize(){
		super.getPreferredSize();
		return new Dimension(this.tablero.getAncho() * DIMENSION_CELDA, this.tablero.getAlto() * DIMENSION_CELDA);
    }

    public void actualizarTablero() {
		repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		final Graphics2D g2d = (Graphics2D) g;

		pintarCeldas(g2d);

		pintarJugador(tablero.getJugadores(), g2d);

		for (Enemigo e: tablero.getEnemigos())
			pintarEnemigo(e, g2d);

		for (Mejora mejora: tablero.getMejoras())
			pintarMejora(mejora, g2d);

		for (Bomba bomba: tablero.getBombas())
			pintarBomba(bomba, g2d);

		g2d.setColor(Color.ORANGE);
		for (Explosion explosion: tablero.getUbicacionExplosiones())
			pintarExplosion(explosion, g2d);
    }

	private void pintarCeldas(Graphics2D g2d ){
	
		for (int fila = 0; fila < tablero.getAlto(); fila++){
			for (int columna = 0; columna < tablero.getAncho(); columna++){
				g2d.setColor(mapaColores.get(this.tablero.getTipoCelda(fila, columna)));
				switch (tablero.getTipoCelda(fila, columna)) {
					case PISO:
						pintarPiso(fila, columna, g2d);
						break;
					case BLOQUE:
						pintarBloque(fila, columna, g2d);
						break;
					case PARED:
						pintarPared(fila, columna, g2d);
						break;
				}
			}
		}
	}

	private void pintarPiso(int fila, int columna, Graphics g2d) {
		Image piso1 = new ImageIcon("res_piso.png").getImage();
		Image piso2 = new ImageIcon("res_piso2.png").getImage();
	
		// Calcular el índice de la celda de piso
		int indicePiso = (fila / DIMENSION_CELDA + columna / DIMENSION_CELDA) % 2;
	
		// Alternar entre piso1 y piso2 basado en el índice de la celda
		if (indicePiso == 0) {
			// Pintar piso1
			g2d.drawImage(piso1, columna * DIMENSION_CELDA, fila * DIMENSION_CELDA, null);
		} else {
			// Pintar piso2
			g2d.drawImage(piso2, columna * DIMENSION_CELDA, fila * DIMENSION_CELDA, null);
		}
	}

    private void pintarBloque(int fila, int columna, Graphics g2d){
		g2d.setColor(Color.lightGray);
		g2d.fillRect(columna * DIMENSION_CELDA, fila * DIMENSION_CELDA, DIMENSION_CELDA, DIMENSION_CELDA);
		g2d.setColor(Color.BLUE);
		g2d.drawLine(columna* DIMENSION_CELDA+1, fila*DIMENSION_CELDA+10,
			columna*DIMENSION_CELDA+DIMENSION_CELDA, fila*DIMENSION_CELDA+10);
		g2d.drawLine(columna* DIMENSION_CELDA+1, fila*DIMENSION_CELDA+MITAD_CELDA,
			columna*DIMENSION_CELDA+DIMENSION_CELDA, fila*DIMENSION_CELDA+MITAD_CELDA);
		g2d.drawLine(columna* DIMENSION_CELDA+1, fila*DIMENSION_CELDA+MITAD_CELDA+10,
			columna*DIMENSION_CELDA+DIMENSION_CELDA, fila*DIMENSION_CELDA+MITAD_CELDA+10);
		g2d.drawLine(columna* DIMENSION_CELDA+1, fila*DIMENSION_CELDA+DIMENSION_CELDA,
			columna*DIMENSION_CELDA+DIMENSION_CELDA, fila*DIMENSION_CELDA+DIMENSION_CELDA);

		g2d.drawLine(columna* DIMENSION_CELDA+10, fila*DIMENSION_CELDA+1,
			columna*DIMENSION_CELDA+10, fila*DIMENSION_CELDA+10);
		g2d.drawLine(columna* DIMENSION_CELDA+MITAD_CELDA+10, fila*DIMENSION_CELDA+1,
			columna*DIMENSION_CELDA+MITAD_CELDA+10, fila*DIMENSION_CELDA+10);

		g2d.drawLine(columna* DIMENSION_CELDA+1, fila*DIMENSION_CELDA+10, columna*DIMENSION_CELDA+1, 
			fila*DIMENSION_CELDA+MITAD_CELDA);
		g2d.drawLine(columna* DIMENSION_CELDA+MITAD_CELDA+1, fila*DIMENSION_CELDA+10,
			columna*DIMENSION_CELDA+MITAD_CELDA+1, fila*DIMENSION_CELDA+MITAD_CELDA);

		g2d.drawLine(columna* DIMENSION_CELDA+10, fila*DIMENSION_CELDA+1+MITAD_CELDA,
			columna*DIMENSION_CELDA+10, fila*DIMENSION_CELDA+MITAD_CELDA+10);
		g2d.drawLine(columna* DIMENSION_CELDA+MITAD_CELDA+10, fila*DIMENSION_CELDA+1+MITAD_CELDA,
			columna*DIMENSION_CELDA+MITAD_CELDA+10, fila*DIMENSION_CELDA+MITAD_CELDA+10);

		g2d.drawLine(columna* DIMENSION_CELDA+1, fila*DIMENSION_CELDA+MITAD_CELDA+10, 
			columna*DIMENSION_CELDA+1, fila*DIMENSION_CELDA+DIMENSION_CELDA);
		g2d.drawLine(columna* DIMENSION_CELDA+MITAD_CELDA+1, fila*DIMENSION_CELDA+MITAD_CELDA+10, 
			columna*DIMENSION_CELDA+MITAD_CELDA+1, fila*DIMENSION_CELDA+DIMENSION_CELDA);
    }

	private void pintarPared(int fila, int columna, Graphics g2d) {
		Image imagen = new ImageIcon("res_muroColision.png").getImage();
	
		// Dibujar la imagen de la pared en la posición específica
		g2d.drawImage(imagen, columna * DIMENSION_CELDA, fila * DIMENSION_CELDA, null);
	}

	private void pintarJugador(ArrayList<JugadorMJ> jugadores, Graphics g2d) {
		for (JugadorMJ jugador : jugadores) {
			Image imagen = null;
	
			// Seleccionar la imagen según el color del jugador
			switch (jugador.getColor()) {
				case 0:
					imagen = new ImageIcon("p_white_estatico.png").getImage();
					break;
				case 1:
					imagen = new ImageIcon("p_black_estatico.png").getImage();
					break;
				case 2:
					imagen = new ImageIcon("p_red_estatico.png").getImage();
					break;
				case 3:
					imagen = new ImageIcon("p_blue_estatico.png").getImage();
					break;
				case 4:
					imagen = new ImageIcon("p_green_estatico.png").getImage();
					break;
			}
	
			// Dibujar la imagen del jugador en la posición específica
			g2d.drawImage(imagen, jugador.getX() - CHARACTER_ADJUSTMENT_FOR_PAINT,
					jugador.getY() - CHARACTER_ADJUSTMENT_FOR_PAINT, null);
	
			// Puedes agregar aquí el código adicional para pintar otras partes del jugador si es necesario
		}
	}

    private void pintarEnemigo(Enemigo e, Graphics g2d){
		// Paint body
		g2d.setColor(Color.orange);
		g2d.fillOval(e.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, e.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT,
			 e.getSize(), e.getSize());
		// Paint brows
		g2d.setColor(Color.BLACK);
		// Paint eyes
		g2d.fillOval(e.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+4, 
			e.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+9, 7, 7);
		g2d.fillOval(e.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_19,
			e.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+9, 7, 7);
		// Paint mouth
		g2d.fillOval(e.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+5, 
			e.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20, PAINT_PARAMETER_20, 2);
		// Fill eyes
		g2d.setColor(Color.RED);
		g2d.fillOval(e.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+5,
			e.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, 5, 5);
		g2d.fillOval(e.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20,
			e.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10, 5, 5);
    }

	private void pintarMejora(Mejora mejora, Graphics g2d) {
		Image imagen = null;
	
		// Seleccionar la imagen según el nombre de la mejora
		switch (mejora.getNombre()) {
			case "Aumento de Bombas Disponibles":
				imagen = new ImageIcon("power_bomb.png").getImage();
				break;
			case "Aumento de Radio de Explosion":
				imagen = new ImageIcon("power_fire.png").getImage();
				break;
			case "Aumento de Velocidad":
				imagen = new ImageIcon("power_speed.png").getImage();
				break;
			case "Aumento de Vidas":
				imagen = new ImageIcon("power_life.png").getImage();
				break;
		}
	
		// Obtener las coordenadas de la mejora
		int mejoraX = mejora.getColumna() - CHARACTER_ADJUSTMENT_FOR_PAINT;
		int mejoraY = mejora.getFila() - CHARACTER_ADJUSTMENT_FOR_PAINT;
	
		// Dibujar la imagen de la mejora en la posición específica
		g2d.drawImage(imagen, mejoraX, mejoraY, null);
	}

	private void pintarBomba(Bomba bomba, Graphics g2d) {
		// Cargar la imagen desde el archivo "bomba.png"
		ImageIcon icono = new ImageIcon("icon_bomb.png");
		Image imagen = icono.getImage();
	
		// Obtener las coordenadas de la bomba
		int bombX = tablero.transfromarAPixel(bomba.getColumna());
		int bombY = tablero.transfromarAPixel(bomba.getFila());
	
		// Dibujar la imagen de la bomba en la posición específica
		g2d.drawImage(imagen, bombX, bombY, null);
	}

	private void pintarExplosion(Explosion explosion, Graphics2D g2d) {
		// Cargar la imagen desde el archivo "fuego.png"
		ImageIcon icono = new ImageIcon("icon_firebomb.png");
		Image imagen = icono.getImage();
	
		// Obtener las coordenadas de la explosión
		int explosionX = tablero.transfromarAPixel(explosion.getColumna());
		int explosionY = tablero.transfromarAPixel(explosion.getFila());
	
		// Dibujar la imagen de la explosión en la posición específica
		g2d.drawImage(imagen, explosionX, explosionY, null);
	}
}
