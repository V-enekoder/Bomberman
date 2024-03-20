package Juego;

import java.io.File;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Juego.Mejora.*;
import Juego.Packet.Packet02Derrota;
import Juego.Personaje.*;
import Juego.Personaje.Personaje.Movimiento;
import Server.UDP.Cliente;
/**
 * La clase Tablero representa el tablero de juego donde se desarrolla la partida de Bomberman.
 * Contiene información sobre las celdas, jugadores, enemigos, bombas, mejoras y explosiones.
 */
public class Tablero {
    private final static double PROB_PARED = 0.4;
    private final Celda[][] celdas;
    private int ancho;
    private int alto;
    private Collection<Sensor> sensores = new ArrayList<>();
	private ArrayList<Jugador> jugadores = new ArrayList<>();
    private Collection<Enemigo> enemigos = new ArrayList<>();
    private List<Bomba> bombas = new ArrayList<>();
    private Collection<Mejora> mejoras = new ArrayList<>();
    private Collection<Bomba> explosiones = new ArrayList<>();
    private Collection<Explosion> ubicacionExplosiones= new ArrayList<>();
    private boolean GameOver = false;
	ArrayList<int[]> reserva;

    public Tablero(int ancho, int alto, int enemigos){
		this.ancho = ancho;
		this.alto = alto;
		this.celdas = new Celda[alto][ancho];
		this.reserva = reservarCasillas();
		definirCasillas();
		crearEnemigos(enemigos);
	}
	    /**
     * Método para reservar las casillas especiales del tablero.
     * @return Una lista de arreglos de enteros que representan las coordenadas de las casillas reservadas.
     */
	private ArrayList<int[]> reservarCasillas() {
		ArrayList<int[]> reserva = new ArrayList<>();
	
		int[] coordenadas = {1, 1};
		for (int i = 0; i < 3; i++) {
			reserva.add(coordenadas.clone());
			coordenadas[1]++;
		}
	
		coordenadas = new int[]{1, ancho - 3};
		for (int i = 0; i < 3; i++) {
			reserva.add(coordenadas.clone());
			coordenadas[1]++;
		}
	
		coordenadas = new int[]{alto - 3, 1};
		for (int i = 0; i < 3; i++) {
			reserva.add(coordenadas.clone());
			coordenadas[1]++;
		}
	
		coordenadas = new int[]{alto - 2, ancho - 3};
		for (int i = 0; i < 3; i++) {
			reserva.add(coordenadas.clone());
			coordenadas[1]++;
		}
		return reserva;
	}
    /**
     * Método para definir las casillas del tablero.
     */
	private void definirCasillas() {
		// Marcar todas las casillas como piso
		for (int i = 0; i < alto; i++) {
			for (int j = 0; j < ancho; j++) {
				celdas[i][j] = Celda.PISO;
			}
		}
	
	// Generar paredes y bloques solo en las casillas no reservadas
		for (int i = 0; i < alto; i++) {
			for (int j = 0; j < ancho; j++) {
			// Generar paredes en los bordes y en las casillas pares
				if (esBorde(i, j) || (i % 2 == 0 && j % 2 == 0) && !estaReservado(i, j))
				celdas[i][j] = Celda.PARED;
			else
				// Generar bloques aleatoriamente solo si la casilla no está reservada
				if (!estaReservado(i, j)) {
					double prob = Math.random();
				 	if (prob <= PROB_PARED)
						celdas[i][j] = Celda.BLOQUE;
				}
			}
		}
		celdas[13][1] = Celda.PISO;
		celdas[2][13] = Celda.PISO;
		celdas[13][2] = Celda.PISO;
		celdas[12][13] = Celda.PISO;
	}
	
	private boolean esBorde(int fila, int columna){
		if ((fila == 0) || (columna == 0) || (fila == alto - 1) || (columna == ancho - 1))
			return true;
		return false;
	}

	private boolean estaReservado(int fila, int columna){
		for (int[] position : reserva) {
			if (position[0] == fila && position[1] == columna)
				return true;
		}
		return false;
	}
    /**
     * Método para crear enemigos en el tablero.
     * @param cantidadEnemigos La cantidad de enemigos a crear.
     */

    private void crearEnemigos (int cantidadEnemigos){
		int enemigos_generados = 0;
		int x,y;
		do{
			int fila = generarNumeroAleatorio(1, alto - 1);
			int columna = generarNumeroAleatorio(1, ancho - 1);

			if(getTipoCelda(fila, columna) == Celda.PISO && !estaReservado(fila, columna)){	
				boolean	vertical = generarNumeroAleatorio(1,100) % 2 == 0 ? true : false;
				x = transfromarAPixel(columna) + ComponenteGrafico.getSquareMiddle();
				y = transfromarAPixel(fila) + ComponenteGrafico.getSquareMiddle();
				enemigos.add(new Enemigo(x, y, vertical));
				enemigos_generados++;
			}
		}while(enemigos_generados != cantidadEnemigos);
    }

	public static int generarNumeroAleatorio(int minimo, int maximo){
        int rango = maximo - minimo + 1;
        return (int) (Math.random() * rango) + minimo;
    }

    public int transfromarAPixel(int coordenada){
		return coordenada * ComponenteGrafico.getSquareSize();
    }
    
	public static int transfromarACelda(int coordenada){
		return ((coordenada + ComponenteGrafico.getSquareSize()-1) / ComponenteGrafico.getSquareSize()) - 1;
    }

    public Celda getTipoCelda(int fila, int columna){
		return celdas[fila][columna];
    }

    public int getAncho(){
		return ancho;
    }

    public int getAlto(){
		return alto;
    }

	public ArrayList<Jugador> getJugadores(){
		return jugadores;
	}

    public Collection<Enemigo> getEnemigos(){
		return enemigos;
    }

    public Iterable<Bomba> getBombas(){
		return bombas;
    }

    public int getCantidadBombas(){
		return bombas.size();
    }

    public Iterable<Mejora> getMejoras(){
		return mejoras;
    }

    public Iterable<Explosion> getUbicacionExplosiones(){
		return ubicacionExplosiones;
    }

    public boolean isGameOver(){
		return GameOver;
    }

    public void setGameOver(boolean GameOver) {
		this.GameOver = GameOver;
    }

    public void agregarBomba(Bomba bomba) {
		bombas.add(bomba);
    }

	public void agregarJugador(Jugador jugador){
		jugadores.add(jugador);
	}
/**
 * Elimina un jugador del tablero utilizando su nombre como identificador.
 * @param nombre El nombre del jugador a eliminar.
 */
	public void eliminarJugador(String nombre){
		int posicion = 0;
		for(Jugador j: jugadores){
			if(j.getNombre().equalsIgnoreCase(nombre))
				break;
			posicion++;
		}
		jugadores.remove(posicion);
	}
/**
 * Elimina un jugador del tablero.
 * @param jugador El jugador a eliminar.
 */
	public void eliminarJugador(Jugador jugador){
		jugadores.remove(jugador);
	}

	/**
 * Mueve todos los enemigos en el tablero en la dirección actual de cada uno.
 * Si un enemigo choca con un bloque o una bomba, cambia su dirección.
 */

    public void moverEnemigos(){

		for (Enemigo e : enemigos) {
			Movimiento direccion = e.getDireccion();
		
			switch (direccion) {
				case ABAJO:
					e.Movimiento(Movimiento.ABAJO);
					break;
				case ARRIBA:
					e.Movimiento(Movimiento.ARRIBA);
					break;
				case IZQUIERDA:
					e.Movimiento(Movimiento.IZQUIERDA);
					break;
				case DERECHA:
					e.Movimiento(Movimiento.DERECHA);
					break;
			}
			if (chocaconBloque(e) || chocaconBomba(e)) 
				e.cambiarDireccion();
		}
	}
	/**
 * Verifica si un enemigo choca con un bloque en su proxima posición.
 * @param e El enemigo que se está moviendo.
 * @return true si el enemigo choca con un bloque, false de lo contrario.
 */
	public boolean chocaconBloque(Enemigo e){
		int fila = e.getUbicacionFila();  
		int columna = e.getUbicacionColumna();
		for (int i = fila - 1; i <= fila + 1; i++) {
			for (int j = columna - 1; j <= columna+ 1; j++){
				if (estaEnTablero(i,j) && getTipoCelda(i, j) != Celda.PISO 
					&& hayInteraccion(e,i, j)) {
						return true;
				}
			}
		}
		return false;
    }
	
	/**
 * Verifica si un jugador choca con un bloque en su proxima posición.
 * @param jugador El jugador que se está moviendo.
 * @return true si el jugador choca con un bloque, false de lo contrario.
 */
	public boolean chocaconBloque(Jugador jugador){
		int fila = jugador.getUbicacionFila();  
		int columna = jugador.getUbicacionColumna();

		for (int i = fila - 1; i <= fila + 1; i++) {
			for (int j = columna - 1; j <= columna+ 1; j++){
				if(estaEnTablero(i,j) && hayInteraccion(jugador,i,j)){
					if(!jugador.isFantasma() && getTipoCelda(i,j) != Celda.PISO
						||(jugador.isFantasma() && esBorde(i,j))) 
							return true;	
				}
			}
		}
		return false;
    }
	
	private boolean estaEnTablero(int fila, int columna){
		if (fila >= 0 && fila < alto && columna >= 0 && columna < ancho)
			return true;
		return false;
	}
	
/**
 * Verifica si hay interacción entre un personaje y una casilla en particular en el tablero.
 * @param personaje El personaje que se está moviendo.
 * @param fila La fila de la casilla.
 * @param columna La columna de la casilla.
 * @return true si hay interacción, false de lo contrario.
 */

	private boolean hayInteraccion(Personaje personaje,int fila, int columna) {
		int x = personaje.getX();
		int y = personaje.getY();

		int radioPersonaje = personaje.getSize() / 2;
		int ladoBloque = ComponenteGrafico.getSquareSize();
		int centroComponenteX = columna * ladoBloque + ladoBloque / 2;
		int centroComponenteY = fila * ladoBloque + ladoBloque / 2;

		int distanciaX = Math.abs(x - centroComponenteX);
		int distanciaY = Math.abs(y - centroComponenteY);

		if (distanciaX > (ladoBloque/2 + radioPersonaje) || distanciaY > (ladoBloque/2 + radioPersonaje)) 
			return false;
		else
			return true;
    }
/**
 * Verifica si un jugador choca con alguna bomba en su proxima posición.
 * @param jugador El jugador que se está moviendo.
 * @return true si el jugador choca con alguna bomba, false de lo contrario.
 */
    public boolean chocaconBomba(Jugador jugador){ 
		int x,y;
		for (Bomba bomb : bombas) {
			boolean exteriorBomba = bomb.estaJugadorFuera(jugador.getId());
			x = transfromarAPixel(bomb.getColumna());
			y = transfromarAPixel(bomb.getFila());
			if(exteriorBomba && hayChoque(jugador,x,y) && !jugador.isFantasma())
					return true;
		}
		return false;
    }
/**
 * Verifica si un enemigo choca con alguna bomba en su proxima posición.
 * @param enemigo El enemigo que se está moviendo.
 * @return true si el enemigo choca con alguna bomba, false de lo contrario.
 */
	public boolean chocaconBomba(Enemigo enemigo){
		int x, y;
		for (Bomba bomb : bombas){
			x = transfromarAPixel(bomb.getColumna());
			y = transfromarAPixel(bomb.getFila());
			if(hayChoque(enemigo,x, y))
					return true;
		}
		return false;
    }


/**
 * Verifica si hay choque entre un personaje y una posición en particular.
 * @param personaje El personaje que se está moviendo.
 * @param x La coordenada x de la posición.
 * @param y La coordenada y de la posición.
 * @return true si hay choque, false de lo contrario.
 */
	private boolean hayChoque(Personaje personaje, int x, int y){
		double distancia_horizontal = personaje.getX() - x - ComponenteGrafico.getSquareMiddle();
		double distancia_vertical = personaje.getY() - y - ComponenteGrafico.getSquareMiddle();
		double distancia_centros = Math.sqrt(Math.pow(distancia_horizontal,2) + Math.pow(distancia_vertical,2));
		if(personaje.getSize() > distancia_centros)
			return true;
		return false;
    }
	/**
 * Avanza la cuenta regresiva de todas las bombas en el tablero.
 * Si el tiempo restante de una bomba llega a cero, se agrega a la lista de explosiones y se elimina de la lista de bombas.
 * 
 * @return Una colección de enteros que representa los índices de las bombas que deben ser eliminadas de la lista de bombas.
 */
	public void avanzarCuentaRegresiva(){
		Collection<Integer> bombas_por_Eliminar = new ArrayList<>();
		explosiones.clear();
		int bomba = 0;
		for (Bomba b: bombas) {
			b.reducirTiempoExplosion();
			if(b.getTiempoRestante() == 0){
				bombas_por_Eliminar.add(bomba);
				explosiones.add(b);
			}
			bomba++;
		}
		for (int i: bombas_por_Eliminar)
			bombas.remove(i);
	}
	/**
 * Genera y gestiona las explosiones causadas por las bombas en el tablero.
 * Reduce la duración de las explosiones existentes y elimina aquellas cuya duración llega a cero.
 * Para cada bomba que explota, determina las direcciones en las que se propagará la explosión y procesa cada celda afectada.
 * 
 */
	public void generarExplosion(){
		Collection<Explosion> explosionesporEliminar = new ArrayList<>();
		for (Explosion e:ubicacionExplosiones) {
			e.reducirDuracion();
			if(e.getDuracion() == 0)
				explosionesporEliminar.add(e);
		}
		for (Explosion e: explosionesporEliminar)
			ubicacionExplosiones.remove(e);
	
		for (Bomba e: explosiones) {
			int fila = e.getFila();
			int columna = e.getColumna();
			boolean northOpen = true;
			boolean southOpen = true;
			boolean westOpen = true;
			boolean eastOpen = true;
			ubicacionExplosiones.add(new Explosion(fila, columna));
			for (int i = 1; i < e.getRadioExplosion() + 1; i++){
				if (fila - i >= 0 && northOpen)
					northOpen = procesarExplosion(fila-i, columna);
				if (fila + i < alto && southOpen)
					southOpen = procesarExplosion(fila+i, columna);
				if (columna - i >= 0 && westOpen)
					westOpen = procesarExplosion(fila, columna-i);
				if (columna + i < ancho && eastOpen)
					eastOpen = procesarExplosion(fila, columna+i);
			}
		}
	}
	/**
 * Procesa la explosión en la posición dada por fila y columna.
 * Verifica si hay una mejora en esa posición y la elimina si la encuentra.
 * Actualiza el estado de la celda en la posición dada y agrega una explosión en esa posición si no es una pared.
 * Además, si la celda es un bloque, lo destruye y genera una nueva mejora en esa posición.
 * Finalmente, reduce el tiempo restante de cualquier bomba que esté en esa posición a 1.
 * 
 * @param fila     La fila donde ocurrió la explosión.
 * @param columna  La columna donde ocurrió la explosión.
 * @return true si la celda en la posición dada está abierta (no es una pared), false de lo contrario.
 */
	private boolean procesarExplosion(int fila, int columna){
		boolean open = true;

		Iterator<Mejora> iteradorMejoras = mejoras.iterator();
		while (iteradorMejoras.hasNext()) {
			Mejora m = iteradorMejoras.next();
			int filaMejora = transfromarACelda(m.getFila());
			int columnaMejora = transfromarACelda(m.getColumna());
			if(fila == filaMejora && columna == columnaMejora){
				mejoras.remove(m);
				break;
			}
		}

		if(celdas[fila][columna] != Celda.PISO)
			open = false;
	
		if(celdas[fila][columna] == Celda.BLOQUE){
			celdas[fila][columna] = Celda.PISO;
			generarMejora(fila, columna);
		}

		if(celdas[fila][columna] != Celda.PARED){
			ubicacionExplosiones.add(new Explosion(fila, columna));
		}

		for(Bomba bomba: bombas){
			if(fila == bomba.getFila() && columna == bomba.getColumna())
				bomba.setTiempoRestante(1);
		}
		return open;
	}
/**
 * Genera una mejora en una posición específica del tablero dada por fila y columna.
 * La mejora se elige aleatoriamente entre cuatro opciones y se agrega a la colección de mejoras.
 * 
 * @param fila     La fila donde se generará la mejora.
 * @param columna  La columna donde se generará la mejora.
 */
	private void generarMejora(int fila, int columna) {
		int mejora = generarNumeroAleatorio(1, 10);
		int squareMiddle = ComponenteGrafico.getSquareMiddle();
		int x = transfromarAPixel(fila) + squareMiddle;
		int y = transfromarAPixel(columna) + squareMiddle;
		switch (mejora) {
			case 1:
			mejoras.add(new AumentoRadioExplosion(x, y));
				break;
			case 2:
			mejoras.add(new AumentoBombasDisponibles(x, y));
				break;
			case 3:
			mejoras.add(new AumentoVelocidad(x,y));
				break;
			case 4:
			mejoras.add(new AumentoVidas(x,y));
				break;
		}
	}
/**
 * Genera una mejora aleatoria en una posición del tablero que sea de tipo PISO.
 * La mejora se elige aleatoriamente entre cuatro opciones y se agrega a la colección de mejoras.
 */
	public void generarMejoraAleatoria(){
		int fila,columna;
		do{
			fila = generarNumeroAleatorio(1, 13);
			columna = generarNumeroAleatorio(1, 13);
		}while(getTipoCelda(fila, columna) != Celda.PISO);
		
		int mejora = generarNumeroAleatorio(1, 10);
		int squareMiddle = ComponenteGrafico.getSquareMiddle();
		int x = transfromarAPixel(fila) + squareMiddle;
		int y = transfromarAPixel(columna) + squareMiddle;
		switch (mejora) {
			case 1:
			mejoras.add(new AumentoRadioExplosion(x, y));
				break;
			case 2:
			mejoras.add(new AumentoBombasDisponibles(x, y));
				break;
			case 3:
			mejoras.add(new AumentoVelocidad(x,y));
				break;
			case 4:
			mejoras.add(new AumentoVidas(x,y));
				break;
		}
	}
/**
 * Aplica el efecto de la explosión en el tablero.
 * Reduce las vidas de los jugadores que se encuentren dentro del área de la explosión y que no estén inmunes.
 * Si un jugador se queda sin vidas, aumenta el contador de partidas perdidas, guarda los datos del jugador,
 * lo convierte en un fantasma y envía un paquete de derrota al servidor.
 * Elimina a los enemigos que se encuentren dentro del área de la explosión.
 */
	public void aplicarExplosion(){
		int x,y;
		for (Explosion explosion:ubicacionExplosiones){
			x = transfromarAPixel(explosion.getColumna());
			y =  transfromarAPixel(explosion.getFila());
			for(Jugador jugador: jugadores){
				if(hayChoque(jugador,x,y) && !jugador.isInmune()){
					jugador.reducirVidas();
					jugador.setInmune(true);
					if(jugador.getVidas() == 0){
						jugador.getDatos().aumentarPartidasPerdidas();
						jugador.getDatos().guardar();
						jugador.setFantasma(true);
						Cliente socket = new Cliente();
						Packet02Derrota packet = new Packet02Derrota(jugador.getNombre(),jugador.getId());
						packet.enviar(socket);
					}
				}
			}
			Collection<Enemigo>enemigosporEliminar = new ArrayList<>();
				for (Enemigo e : enemigos) {
					if(hayChoque(e, x, y))
						enemigosporEliminar.add(e);
				}
			for (Enemigo e:enemigosporEliminar ) 
				enemigos.remove(e);
		}
		/*if(enemigos.isEmpty())
			GameOver = true;*/
    }

    public void informarSensores(){
		for (Sensor b : sensores){
			b.actualizarTablero();
		}
    }	

    public void agregarSensor(Sensor sensor) {
		sensores.add(sensor);
    }
/**
 * Verifica si hay choque entre los jugadores y los enemigos.
 * Si un jugador colisiona con un enemigo y no está inmune ni es un fantasma, reduce sus vidas.
 * Si el jugador se queda sin vidas, envía un paquete de derrota al servidor, convierte al jugador en un fantasma
 * e imprime un mensaje indicando que el jugador ha muerto.
 */
    public void choqueconEnemigos(){
		int x,y;
		for (Enemigo enemy : enemigos){
			x = enemy.getX() - ComponenteGrafico.getSquareMiddle();
			y = enemy.getY() - ComponenteGrafico.getSquareMiddle();
			for(Jugador jugador: jugadores){
				if(hayChoque(jugador, x,y) && !jugador.isInmune() && !jugador.isFantasma()){
					jugador.reducirVidas();
					jugador.setInmune(true);
					if(jugador.getVidas() == 0){
						Cliente socket = new Cliente();
						Packet02Derrota packet = new Packet02Derrota(jugador.getNombre(),jugador.getId());
						packet.enviar(socket);
						jugador.setFantasma(true);
						System.out.println("Ha muerto el jugador "+ jugador.getId());
					}
				}
			}
		}
    }
/**
 * Reproduce un archivo de sonido ubicado en la ruta especificada.
 * 
 * @param ruta la ruta del archivo de sonido a reproducir
 */
	private void reproducirSonido(String ruta) {
		try {
			File archivoSonido = new File(ruta);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoSonido);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * Comprueba si algún jugador ha chocado con una mejora y aplica la mejora al jugador si corresponde.
 * Reproduce un sonido cuando se aplica una mejora.
 */
public void choqueconMejora() {
    int x, y;
    Iterator<Mejora> iteradorMejoras = mejoras.iterator();
    while (iteradorMejoras.hasNext()) {
        Mejora powerup = iteradorMejoras.next();
        x = powerup.getColumna() - ComponenteGrafico.getSquareMiddle();
        y = powerup.getFila() - ComponenteGrafico.getSquareMiddle();
        for (Jugador jugador : jugadores) {
            if (hayChoque(jugador, x, y) && !jugador.isFantasma()) {
                powerup.agregarMejora(jugador);
                iteradorMejoras.remove();
                reproducirSonido("audio7.wav");
                break;
            }
        }
    }
}
	
    public boolean tieneBomba(int fila, int columna){
		for (Bomba bomba: bombas) {
			if(bomba.getFila() == fila && bomba.getColumna() == columna)
				return true;
		}
		return false;
    }
/**
 * Verifica si algún jugador ha salido del radio de explosión de una bomba y actualiza su estado.
 */
	public void verificarSalidaBomba(){
		int x,y;
		for (Bomba bomb: bombas){
			x = transfromarAPixel(bomb.getColumna());
			y = transfromarAPixel(bomb.getFila());
			for(Jugador jugador: jugadores){
				if(!bomb.estaJugadorFuera(jugador.getId()) &&
					!hayChoque(jugador, x, y))
						bomb.setJugadorFuera(true,jugador.getId());
			}
		}
	}
	/**
 * Reduce el tiempo de inmunidad de los jugadores y los restaura si el tiempo de inmunidad llega a cero.
 */
	public void reducirTiempoInmunidad() {
		for(Jugador jugador: jugadores){
			jugador.reducirTiempoInmunidad();
			if(jugador.getTiempoInmunidad() <= 0){
				jugador.setInmune(false);
				jugador.restaurarTiempoInmunidad();
			}
		}
	}
/**
 * Verifica si algún jugador ha ganado la partida, es decir, si todos los jugadores, excepto uno, son fantasmas.
 * En ese caso, establece el estado de GameOver como true.
 */
	public void comprobarVictoria(){
		int fantasmas = 0;
		for(Jugador jugador: jugadores){
			if(jugador.isFantasma())
				fantasmas++;
		}
		if(fantasmas == jugadores.size() - 1 && jugadores.size() != 1)
			GameOver = true;
	}

    public int getCantidadBombas(int id){
		int bombasPuestas = 0;
		for(Bomba b: bombas){
			if(b.getIdJugador() == id)
				bombasPuestas++;
		}
		return bombasPuestas;
    }

	public static double getProbPared() {
		return PROB_PARED;
	}

	public Celda[][] getCeldas() {
		return celdas;
	}

	public Collection<Sensor> getSensores() {
		return sensores;
	}

	public Collection<Bomba> getExplosiones() {
		return explosiones;
	}

	public ArrayList<int[]> getReserva() {
		return reserva;
	}
}