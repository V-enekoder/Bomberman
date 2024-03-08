package Juego;

import java.util.*;

import Juego.Mejora.*;
import Juego.Packet.Packet02Derrota;
import Juego.Personaje.*;
import Juego.Personaje.Personaje.Movimiento;
import Server.UDP.Cliente;

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
	//private Cliente socket;

    public Tablero(int ancho, int alto, int enemigos){
		this.ancho = ancho;
		this.alto = alto;
		this.celdas = new Celda[alto][ancho];
		this.reserva = reservarCasillas();
		//this.socket = new Cliente();
		definirCasillas();
		crearEnemigos(enemigos);
	}
	
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

	public void eliminarJugador(String nombre){
		int posicion = 0;
		for(Jugador j: jugadores){
			if(j.getNombre().equalsIgnoreCase(nombre))
				break;
			posicion++;
		}
		jugadores.remove(posicion);
	}

	public void eliminarJugador(Jugador jugador){
		jugadores.remove(jugador);
	}


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

	private boolean hayChoque(Personaje personaje, int x, int y){
		double distancia_horizontal = personaje.getX() - x - ComponenteGrafico.getSquareMiddle();
		double distancia_vertical = personaje.getY() - y - ComponenteGrafico.getSquareMiddle();
		double distancia_centros = Math.sqrt(Math.pow(distancia_horizontal,2) + Math.pow(distancia_vertical,2));
		if(personaje.getSize() > distancia_centros)
			return true;
		return false;
    }
	
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

	public void choqueconMejora(){
		int x,y;
		Iterator<Mejora> iteradorMejoras = mejoras.iterator();
		while (iteradorMejoras.hasNext()) {
			Mejora powerup = iteradorMejoras.next();
			x = powerup.getColumna() - ComponenteGrafico.getSquareMiddle();
			y = powerup.getFila() - ComponenteGrafico.getSquareMiddle();
			for (Jugador jugador : jugadores) {
				if (hayChoque(jugador, x, y) && !jugador.isFantasma()){
					powerup.agregarMejora(jugador);
					iteradorMejoras.remove();
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
	
	public void reducirTiempoInmunidad() {
		for(Jugador jugador: jugadores){
			jugador.reducirTiempoInmunidad();
			if(jugador.getTiempoInmunidad() <= 0){
				jugador.setInmune(false);
				jugador.restaurarTiempoInmunidad();
			}
		}
	}

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

}