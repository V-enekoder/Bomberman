package Juego.Personaje;

import javax.swing.*;

import Juego.Bomba;
import Juego.ComponenteGrafico;
//import Juego.Estadisticas;
import Juego.Estadisticas;
import Juego.InterfazGrafica;
import Juego.Tablero;
import Juego.Packet.Packet01Desconexion;
import Server.UDP.Cliente;

import java.awt.event.ActionEvent;
import java.net.InetAddress;

public class Jugador extends Personaje{

	private int id;
    private int radioExplosion;
    private int bombasDisponibles;
	private int vidas;
	private int duracionInmunidad;
	private int tiempoInmunidad;
	private boolean inmune; // Pintar cuando inmmune
	private String nombre;
	private boolean fantasma;
	private int color;
    private InetAddress direccionIP;
    private int puerto;
	private Estadisticas datos;

	public Estadisticas getDatos() {
		return datos;
	}

	public void setDatos(Estadisticas datos) {
		this.datos = datos;
	}

	public boolean isFantasma() {
		return fantasma;
	}

	public void setFantasma(boolean fantasma) {
		this.fantasma = fantasma;
	}

	public Jugador(InterfazGrafica GUI, Tablero tablero, int[] posicion, int id,
        String nombre,InetAddress direccionIP ,int puerto, int color){
			super(posicion[0],posicion[1]);
			SIZE = 25;
			radioExplosion = 1;
			bombasDisponibles = 1;//2;
			vidas = 1;
			this.id = id;
			duracionInmunidad = 5000; //1 segundo = 1000 milisegundos
			tiempoInmunidad = duracionInmunidad;
			inmune = false; 
			this.nombre = nombre;
			fantasma = false;
			this.color = color;
			this.direccionIP = direccionIP;
            this.puerto = puerto;
			datos = new Estadisticas(nombre);
			configurarControles(GUI.getBombermanComponent(), tablero);
	}
	

	private void configurarControles(ComponenteGrafico bombermanComponent, Tablero tablero) {
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("D"), "Derecha");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "Derecha");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("A"), "Izquierda");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "Izquierda");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("W"), "Subir");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("UP"), "Subir");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("S"), "Bajar");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "Bajar");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("J"), "dropBomb");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "dropBomb");
	
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("Q"), "CerrarJuego");
		
		bombermanComponent.getActionMap().put("Derecha", mover(Movimiento.DERECHA, tablero));
		bombermanComponent.getActionMap().put("Izquierda", mover(Movimiento.IZQUIERDA, tablero));
		bombermanComponent.getActionMap().put("Subir", mover(Movimiento.ARRIBA, tablero));
		bombermanComponent.getActionMap().put("Bajar", mover(Movimiento.ABAJO, tablero));
		bombermanComponent.getActionMap().put("dropBomb", ponerBomba(tablero));
		bombermanComponent.getActionMap().put("CerrarJuego", cerrarJuego());
	}
	
	private Action cerrarJuego() {
		return new AbstractAction() {
			public void actionPerformed(ActionEvent e){
				Cliente socket = new Cliente();
				Packet01Desconexion desconexion = new Packet01Desconexion(nombre,id);
				desconexion.enviar(socket);
			}
		};
	}

    private Action mover(Movimiento Movimiento, Tablero tablero) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                moverJugador(Movimiento, tablero);
            }
        };
    }

    private Action ponerBomba(Tablero tablero) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!tablero.tieneBomba(getUbicacionFila(), getUbicacionColumna()) &&
                        tablero.getCantidadBombas(id) < getbombasDisponibles() && !fantasma)
                    tablero.agregarBomba(new Bomba(getUbicacionFila(), getUbicacionColumna(), getRadioExplosion(),id));
                tablero.informarSensores();
            }
        };
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTiempoInmunidad() {
		return tiempoInmunidad;
	}

	public void restaurarTiempoInmunidad(){
		tiempoInmunidad = duracionInmunidad;
	}

	public void aumentarTiempoInmunidad(){
		tiempoInmunidad += 1000;
	}

	public void reducirTiempoInmunidad(){
		this.tiempoInmunidad -= 30;
	}

	public boolean isInmune() {
		return inmune;
	}

	public void setInmune(boolean inmune) {
		this.inmune = inmune;
	}

    public int getbombasDisponibles(){
		return bombasDisponibles;
    }

    public void setbombasDisponibles(int bombCount) {
		this.bombasDisponibles = bombCount;
    }

    public int getRadioExplosion() {
		return radioExplosion;
    }

    public void setExplosionRadius(int radioExplosion) {
		this.radioExplosion = radioExplosion;
    }

	public void aumentarBombasDisponibles(){
		bombasDisponibles++;
	}

	public void aumentarRadioExplosion(){
		radioExplosion++;
	}

	public void aumentarVelocidad(){
		velocidad++;
	}

	public void aumentarVidas(){
		vidas++;
	}

	public void reducirVidas(){
		vidas--;
	}

	public int getVidas(){
		return vidas;
	}

    private void moverJugador(Movimiento movimiento,Tablero tablero) {
		Movimiento(movimiento);

		if(tablero.chocaconBloque(this) || tablero.chocaconBomba(this))
			regresar(movimiento);

		tablero.verificarSalidaBomba();
		tablero.choqueconMejora();
		tablero.informarSensores();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRadioExplosion(int radioExplosion) {
		this.radioExplosion = radioExplosion;
	}

	public int getBombasDisponibles() {
		return bombasDisponibles;
	}

	public void setBombasDisponibles(int bombasDisponibles) {
		this.bombasDisponibles = bombasDisponibles;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public int getDuracionInmunidad() {
		return duracionInmunidad;
	}

	public void setDuracionInmunidad(int duracionInmunidad) {
		this.duracionInmunidad = duracionInmunidad;
	}

	public void setTiempoInmunidad(int tiempoInmunidad) {
		this.tiempoInmunidad = tiempoInmunidad;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

    public InetAddress getDireccionIP() {
        return this.direccionIP;
    }

    public void setDireccionIP(InetAddress direccionIP) {
        this.direccionIP = direccionIP;
    }

    public int getPuerto() {
        return this.puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
}