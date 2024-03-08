package Juego;

import javax.swing.*;

import Juego.Mejora.Mejora;
import Juego.Personaje.Enemigo;
import Juego.Personaje.Jugador;
import Juego.Personaje.Jugador;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("unused")
public class ComponenteGrafico extends JComponent implements Sensor{
    // Constants are static by definition.
    private final static int DIMENSION_CELDA = 40;
    private final static int CHARACTER_ADJUSTMENT_FOR_PAINT = 20;
    private final static int MITAD_CELDA = DIMENSION_CELDA/2;
    private final static int BOMB_ADJUSTMENT_1 =5;
    private final static int BOMB_ADJUSTMENT_2 =10;
    // Defining painting parameters
    //private final static int PAINT_PARAMETER_13 = 13;
    private final static int PAINT_PARAMETER_15 = 20;
    //private final static int PAINT_PARAMETER_17 = 17;
    private final static int PAINT_PARAMETER_18 = 18;
    private final static int PAINT_PARAMETER_19 = 19;
    private final static int PAINT_PARAMETER_20 = 20;
    //private final static int PAINT_PARAMETER_24 = 24;
    private final Tablero tablero;
    private final AbstractMap<Celda, Color> mapaColores;
	private final Map<Jugador, JLabel> vidasLabels = new HashMap<>();

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

		for (Jugador jugador : tablero.getJugadores()) {
			pintarVidasJugador(jugador, g2d);
		}

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
		Image piso1 = new ImageIcon("zres_piso.png").getImage();
		Image piso2 = new ImageIcon("zres_piso2.png").getImage();
	
		// Calcular el índice de la celda de piso
		int indicePiso = (fila + columna) % 2;
	
		if (indicePiso == 0) {
			// Pintar piso1
			g2d.drawImage(piso1, columna * DIMENSION_CELDA, fila * DIMENSION_CELDA, null);
		} else {
			// Pintar piso2
			g2d.drawImage(piso2, columna * DIMENSION_CELDA, fila * DIMENSION_CELDA, null);
		}
	}

	private void pintarBloque(int fila, int columna, Graphics2D g2d) {
		try {
			Image imagen = new ImageIcon("bloqueExtra.png").getImage();
			g2d.drawImage(imagen, columna * DIMENSION_CELDA, fila * DIMENSION_CELDA, DIMENSION_CELDA, DIMENSION_CELDA, null);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error en el archivo o direccion");
		}
	}

	private void pintarPared(int fila, int columna, Graphics g2d) {
		// Verificar si es una pared del borde
		if (esParedDelBorde(fila, columna)) {
			Image imagenBorde = new ImageIcon("extraBloq.png").getImage();
			g2d.drawImage(imagenBorde, columna * DIMENSION_CELDA, fila * DIMENSION_CELDA, null);
		} else {
			// Es una pared dentro del juego
			Image imagenColision = new ImageIcon("res_muroColision2.png").getImage();
			g2d.drawImage(imagenColision, columna * DIMENSION_CELDA, fila * DIMENSION_CELDA, null);
		}
	}
	
	// Método auxiliar para verificar si es una pared del borde
	private boolean esParedDelBorde(int fila, int columna) {
		return fila == 0 || fila == tablero.getAlto() - 1 || columna == 0 || columna == tablero.getAncho() - 1;
	}

	private void pintarJugador(ArrayList<Jugador> jugadores, Graphics g2d) {

		for (Jugador jugador : jugadores) {
			Image imagen = null;
			
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
	
			// Definir el nuevo tamaño con valores decimales
			double factorEscala = 1.5;  // Puedes ajustar el factor de escala según sea necesario
			double nuevoAnchoDouble = imagen.getWidth(null) * factorEscala;
			double nuevoAltoDouble = imagen.getHeight(null) * factorEscala;
	
			// Convertir los valores decimales a enteros
			int nuevoAncho = (int) nuevoAnchoDouble;
			int nuevoAlto = (int) nuevoAltoDouble;
	
			// Crear una imagen compatible con transparencia
			BufferedImage imagenCompatible = new BufferedImage(nuevoAncho, nuevoAlto, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2dImagen = imagenCompatible.createGraphics();
	
			// Redimensionar la imagen y dibujarla en la imagen compatible
			g2dImagen.drawImage(imagen, 0, 0, nuevoAncho, nuevoAlto, null);
			g2dImagen.dispose();
	
			// Dibujar la imagen del jugador en la posición específica
			g2d.drawImage(imagenCompatible, jugador.getX() - CHARACTER_ADJUSTMENT_FOR_PAINT+8,
					jugador.getY() - CHARACTER_ADJUSTMENT_FOR_PAINT-8, null);

		}
	}

	private void pintarEnemigo(Enemigo e, Graphics g2d) {
		// Cargar el GIF del enemigo
		ImageIcon gifIcon = new ImageIcon("spritegif.gif");
		Image gifImage = gifIcon.getImage();
		
		// Dibujar el GIF en la posición del enemigo
		g2d.drawImage(gifImage, e.getX() - CHARACTER_ADJUSTMENT_FOR_PAINT, e.getY() - CHARACTER_ADJUSTMENT_FOR_PAINT, null);
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

	private void pintarVidasJugador(Jugador jugador, Graphics2D g2d) {
		// Remove the specific vidasLabel associated with the current player
		JLabel vidasLabel = vidasLabels.remove(jugador);
		if (vidasLabel != null) {
			this.remove(vidasLabel);
		}
	
		// Configurar el JLabel con la cantidad de vidas
		vidasLabel = new JLabel("Vidas: " + jugador.getVidas());
		vidasLabel.setForeground(Color.WHITE);
		vidasLabel.setFont(new Font("Arial", Font.BOLD, 12));
	
		int jugadorX = jugador.getX() - CHARACTER_ADJUSTMENT_FOR_PAINT + 2;
		int jugadorY = jugador.getY() - CHARACTER_ADJUSTMENT_FOR_PAINT - 20;
	
		// Establecer la posición del JLabel en el componente gráfico
		vidasLabel.setBounds(jugadorX, jugadorY, 60, 20);
	
		// Agregar el JLabel al componente gráfico
		this.add(vidasLabel);
	
		// Actualizar el mapeo de vidasLabels
		vidasLabels.put(jugador, vidasLabel);
	}


	private void actualizarVidasLabel(Jugador jugador) {
		JLabel vidasLabel = vidasLabels.get(jugador);
	
		if (vidasLabel == null) {
			// Si el JLabel no existe, crear uno nuevo
			vidasLabel = new JLabel();
			vidasLabels.put(jugador, vidasLabel);
			this.add(vidasLabel);
		}
	
		// Configurar el JLabel con la cantidad de vidas
		vidasLabel.setText("Vidas: " + jugador.getVidas());
		vidasLabel.setForeground(Color.WHITE); // Configura el color del texto
		vidasLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Configura la fuente del texto
	
		int jugadorX = jugador.getX() - CHARACTER_ADJUSTMENT_FOR_PAINT + 2;
		int jugadorY = jugador.getY() - CHARACTER_ADJUSTMENT_FOR_PAINT - 20;
	
		// Establecer la posición del JLabel en el componente gráfico
		vidasLabel.setLocation(jugadorX, jugadorY); // Actualiza la posición directamente
		vidasLabel.setSize(60, 20); // Ajusta el tamaño según sea necesario
	}
}
