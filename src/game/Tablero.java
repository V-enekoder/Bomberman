package game;

import game.Personaje.Movimiento;
import java.util.*;


public class Tablero {
    // Constants are static by definition.
    private final static double PROB_PARED = 0.4;
    /*private final static double PROB_AUMENTO_RADIO = 0.25;
    private final static double PROB_AUMENTO_BOMBAS = 0.25;
	private final static double PROB_AUMENTO_VELOCIDAD = 0.25;
	private final static double PROB_AUMENTO_VIDAS = 0.25;*/
    private final Celda[][] celdas;
    private int ancho;
    private int alto;
    private Collection<Sensor> sensores = new ArrayList<>();
    private Jugador jugador;
    private Collection<Enemigo> enemigos = new ArrayList<>();
    private List<Bomba> bombas = new ArrayList<>();
    private Collection<Mejora> mejoras = new ArrayList<>();
    private Collection<Bomba> explosiones = new ArrayList<>();
    private Collection<Explosion> ubicacionExplosiones= new ArrayList<>();
    private boolean GameOver = false;
	private int[][] reservado = {{1, 1}, {1, 2}, {2, 1}};


    public Tablero(int ancho, int alto, int enemigos){
		this.ancho = ancho;
		this.alto = alto;
		this.celdas = new Celda[alto][ancho];
		definirCasillas();
		crearEnemigos(enemigos);
	}

	private void definirCasillas(){
		int i,j;

		celdas[1][1] = Celda.PISO;
		celdas[1][2] = Celda.PISO;
		celdas[2][1] = Celda.PISO;

		for (i = 0; i < alto; i++) {
			for (j = 0; j < ancho; j++) {
				if (esBorde(i, j) || (i % 2 == 0 && j % 2 == 0))
					celdas[i][j] = Celda.PARED;
			
				double prob = Math.random();

				if (prob <= PROB_PARED && estaDisponible(celdas[i][j]))
					celdas[i][j] = Celda.BLOQUE;

				if (estaDisponible(celdas[i][j]))
					celdas[i][j] = Celda.PISO;
	  	  	}
		}
	}

	private boolean esBorde(int fila, int columna){
		if ((fila == 0) || (columna == 0) || (fila == alto - 1) || (columna == ancho - 1))
			return true;
		return false;
	}

	private boolean estaDisponible (Celda casilla){
		if(casilla == Celda.PISO || casilla == Celda.BLOQUE || casilla == Celda.PARED)
			return false;
		return true;
	}

    private void crearEnemigos (int cantidadEnemigos){
		int enemigos_generados = 0;
		do{
			int fila = generarNumeroAleatorio(1, alto - 1);
			int columna = generarNumeroAleatorio(1, ancho - 1);

			if(getTipoCelda(fila, columna) == Celda.PISO && !estaReservado(fila, columna)){	
				boolean	vertical = generarNumeroAleatorio(1,100) % 2 == 0 ? true : false;
				enemigos.add(new Enemigo(transfromarAPixel(columna) + ComponenteGrafico.getSquareMiddle(), 
					transfromarAPixel(fila) + ComponenteGrafico.getSquareMiddle(), vertical));
				enemigos_generados++;
			}
		}while(enemigos_generados != cantidadEnemigos);
    }

	public static int generarNumeroAleatorio(int minimo, int maximo){
        int rango = maximo - minimo + 1;
        return (int) (Math.random() * rango) + minimo;
    }

	private boolean estaReservado(int fila, int columna){
		for (int[] position : reservado) {
			if (position[0] == fila && position[1] == columna)
				return true;
		}
		return false;
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

    public Jugador getJugador(){
		return jugador;
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

    public void crearJugador(ComponenteGrafico bombermanComponent, Tablero tablero){
		jugador = new Jugador(bombermanComponent, tablero);
    }

    public void moverEnemigos(){
		if (enemigos.isEmpty())
			GameOver = true;

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
	
	public boolean chocaconBloque(Personaje personaje){
		int fila = personaje.getUbicacionFila();  
		int columna = personaje.getUbicacionColumna();

		for (int i = fila - 1; i <= fila + 1; i++) {
			for (int j = columna - 1; j <= columna+ 1; j++){
				if (estaEnTablero(i,j) && getTipoCelda(i, j) != Celda.PISO 
					&& hayInteraccion(personaje,i, j)) {
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


    public boolean chocaconBomba(Personaje personaje) { 
		for (Bomba bomb : bombas) {
			boolean exteriorBomba = personaje instanceof Jugador ? bomb.estaJugadorFuera(): true;
			if(exteriorBomba && hayChoque(personaje, 
				transfromarAPixel(bomb.getColumna()), transfromarAPixel(bomb.getFila())))
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
				if (fila - i <= alto && southOpen)
					southOpen = procesarExplosion(fila+i, columna);
				if (columna - i >= 0 && westOpen)
					westOpen = procesarExplosion(fila, columna-i);
				if (columna + i <= ancho && eastOpen)
					eastOpen = procesarExplosion(fila, columna+i);
			}
		}
    }

	private boolean procesarExplosion(int fila, int columna){
		boolean open = true;
		if(celdas[fila][columna] != Celda.PISO)
			open = false;

		if(celdas[fila][columna] == Celda.BLOQUE){
			celdas[fila][columna] = Celda.PISO;
			generarMejora(fila, columna);
		}
		if(celdas[fila][columna] != Celda.PARED)
			ubicacionExplosiones.add(new Explosion(fila, columna));
		return open;
    }
    
	private void generarMejora(int fila, int columna) {
		int mejora = generarNumeroAleatorio(1, 8);
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
		aplicarExplosionJugador();
		aplicarExplosionEnemigo();
    }

    public void aplicarExplosionJugador(){
		for (Explosion explosion:ubicacionExplosiones) {
			if(hayChoque(jugador, transfromarAPixel(explosion.getColumna()), transfromarAPixel(explosion.getFila())))
				//jugador.reducirVidas();
				//if(jugador.getVidas()==0)
					GameOver = true;
		}
    }
	
    public void aplicarExplosionEnemigo(){
		for (Explosion exp:ubicacionExplosiones) {
			Collection<Enemigo>enemigosporEliminar = new ArrayList<>();
				for (Enemigo e : enemigos) {
					if(hayChoque(e, transfromarAPixel(exp.getColumna()), transfromarAPixel(exp.getFila())))
						enemigosporEliminar.add(e);
				}
			for (Enemigo e:enemigosporEliminar ) 
				enemigos.remove(e);
		}
    }

    public void informarSensores(){
		for (Sensor b : sensores){
			b.actualizarTablero();
		}
    }	

    public void agregarSensor(Sensor sensor) {
		sensores.add(sensor);
    }

    public boolean choqueconEnemigos(){
		for (Enemigo enemy : enemigos) {
			if(hayChoque(jugador, enemy.getX()-ComponenteGrafico.getSquareMiddle(),
				enemy.getY()-ComponenteGrafico.getSquareMiddle()))
					return true;
		}
		return false;
    }

    public void choqueconMejora(){
		for (Mejora powerup : mejoras) {
			if(hayChoque(jugador, powerup.getColumna()-ComponenteGrafico.getSquareMiddle(),
				powerup.getFila()-ComponenteGrafico.getSquareMiddle())){
					powerup.agregarMejora(jugador);
					mejoras.remove(powerup);
					break;
			}
		}
    }

    public boolean tieneBomba(int fila, int columna){
		for (Bomba bomba: bombas) {
			if(bomba.getFila() == fila && bomba.getColumna() == columna && true)
			return true;
		}
		return false;
    }

	public void verificarSalidaBomba(){
		for (Bomba bomb: bombas) {
			if(!bomb.estaJugadorFuera() && !hayChoque(jugador,
				transfromarAPixel(bomb.getColumna()), transfromarAPixel(bomb.getFila())))
					bomb.setJugadorFuera(true);
		}
	}
}