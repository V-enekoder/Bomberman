package Juego;

import javax.swing.*;

import Juego.Mejora.Mejora;
import Juego.Personaje.Enemigo;
import Juego.Personaje.Jugador;
import Server.JugadorMJ;

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

	private void pintarPiso(int fila, int columna, Graphics g2d){
		g2d.setColor(Color.white);
		g2d.fillRect(columna * DIMENSION_CELDA, fila * DIMENSION_CELDA, DIMENSION_CELDA, DIMENSION_CELDA);
		g2d.setColor(Color.CYAN);
		g2d.drawLine(columna* DIMENSION_CELDA+5, fila*DIMENSION_CELDA+10,
			columna * DIMENSION_CELDA + 10, fila * DIMENSION_CELDA + 5);
		g2d.drawLine(columna* DIMENSION_CELDA+5, fila*DIMENSION_CELDA+MITAD_CELDA,
			columna * DIMENSION_CELDA + MITAD_CELDA, fila * DIMENSION_CELDA + 5);
		g2d.drawLine(columna* DIMENSION_CELDA+5, fila*DIMENSION_CELDA + MITAD_CELDA+10,
			columna * DIMENSION_CELDA + MITAD_CELDA + 10, fila * DIMENSION_CELDA + 5);
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

    private void pintarPared(int fila, int columna, Graphics g2d){
	g2d.fillRect(columna * DIMENSION_CELDA, fila * DIMENSION_CELDA,
		DIMENSION_CELDA, DIMENSION_CELDA);
	g2d.setColor(Color.DARK_GRAY);
	g2d.drawLine(columna* DIMENSION_CELDA, fila*DIMENSION_CELDA,
		columna*DIMENSION_CELDA+DIMENSION_CELDA, fila*DIMENSION_CELDA);
	g2d.drawLine(columna* DIMENSION_CELDA, fila*DIMENSION_CELDA+DIMENSION_CELDA,
		columna*DIMENSION_CELDA+DIMENSION_CELDA, fila*DIMENSION_CELDA+DIMENSION_CELDA);
	g2d.drawLine(columna* DIMENSION_CELDA, fila*DIMENSION_CELDA, columna*DIMENSION_CELDA,
		fila*DIMENSION_CELDA+DIMENSION_CELDA);
	g2d.drawLine(columna* DIMENSION_CELDA+DIMENSION_CELDA, fila*DIMENSION_CELDA, 
		columna*DIMENSION_CELDA+DIMENSION_CELDA, fila*DIMENSION_CELDA+DIMENSION_CELDA);
    }

	private void pintarJugador(ArrayList<Jugador> jugadores, Graphics g2d){
		for(Jugador jugador: jugadores){
			// Paint hat
			g2d.setColor(Color.BLUE);
			g2d.fillOval(jugador.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_15,
				jugador.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT-2, PAINT_PARAMETER_15, PAINT_PARAMETER_15);
			
			// Paint body
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillOval(jugador.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT,
				jugador.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, jugador.getSize(), jugador.getSize());
			
			// Paint face
			g2d.setColor(Color.PINK);
			g2d.fillOval(jugador.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+3,
				jugador.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+3, jugador.getSize()-6, jugador.getSize()-6);
			
			// Paint eyes
			g2d.setColor(Color.BLACK);
			
			g2d.drawLine(jugador.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10,
				jugador.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10,
					jugador.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+10,
						jugador.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_18);
			
						g2d.drawLine(jugador.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20,
				jugador.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+10,
					jugador.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_20,
						jugador.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT+PAINT_PARAMETER_18);
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

	private void pintarMejora(Mejora mejora,Graphics g2d){
		switch (mejora.getNombre()){
			case "Aumento de Bombas Disponibles":
				g2d.setColor(Color.BLACK);
				break;
			case "Aumento de Radio de Explosion":
				g2d.setColor(Color.RED);
				break;
			case "Aumento de Velocidad":
				g2d.setColor(Color.BLUE);
				break;
			case "Aumento de Vidas":
				g2d.setColor(Color.PINK);
				break;	
		}
		g2d.fillOval(mejora.getColumna()-CHARACTER_ADJUSTMENT_FOR_PAINT,
			mejora.getFila()-CHARACTER_ADJUSTMENT_FOR_PAINT, mejora.getPowerupSize(), mejora.getPowerupSize());
	}

	private void pintarBomba(Bomba bomba, Graphics g2d){
		g2d.setColor(Color.RED);
		int bombX = tablero.transfromarAPixel(bomba.getColumna());
		int bombY = tablero.transfromarAPixel(bomba.getFila());
		g2d.fillOval(bombX + BOMB_ADJUSTMENT_1, bombY + BOMB_ADJUSTMENT_1, Bomba.getSIZE(), Bomba.getSIZE());
		g2d.setColor(Color.ORANGE);
		g2d.fillOval(bombX + BOMB_ADJUSTMENT_2, bombY + BOMB_ADJUSTMENT_1, BOMB_ADJUSTMENT_1, BOMB_ADJUSTMENT_2);
	}

	private void pintarExplosion(Explosion explosion, Graphics2D g2d){
		g2d.fillOval(tablero.transfromarAPixel(explosion.getColumna()) + BOMB_ADJUSTMENT_1,
			tablero.transfromarAPixel(explosion.getFila())
				+ BOMB_ADJUSTMENT_1, Bomba.getSIZE(), Bomba.getSIZE());
	}
}
