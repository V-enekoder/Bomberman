package Juego.Personaje;

import javax.swing.*;

import Juego.Bomba;
import Juego.Bomberman;
import Juego.ComponenteGrafico;
import Juego.InterfazGrafica;
import Juego.Tablero;
import Juego.Packet.Packet01Desconexion;
import Server.UDP.Cliente;
import Juego.Packet.Packet.packet;

import java.awt.event.ActionEvent;

@SuppressWarnings("unused")
public class Jugador extends Personaje{
	protected int id;
    protected int radioExplosion;
    protected int bombasDisponibles;
	protected int vidas;
	protected int duracionInmunidad;
	protected int tiempoInmunidad;
	protected boolean inmune; // Pintar cuando inmmune
	protected String nombre;
	protected boolean fantasma;

	public boolean isFantasma() {
		return fantasma;
	}

	public void setFantasma(boolean fantasma) {
		this.fantasma = fantasma;
	}

	public Jugador(InterfazGrafica GUI, Tablero tablero, int[] posicion, int id, String nombre){
		super(posicion[0],posicion[1]);
		radioExplosion = 1;
		bombasDisponibles = 1;
		vidas = 1;
		this.id = id;
		duracionInmunidad = 3000; //1 segundo = 1000 milisegundos
		tiempoInmunidad = duracionInmunidad;
		inmune = false; 
		this.nombre = nombre;
		fantasma = false;
		configurarControles(GUI.getBombermanComponent(), tablero);
    }

	protected void configurarControles(ComponenteGrafico bombermanComponent, Tablero tablero) {
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "Derecha");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "Izquierda");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("UP"), "Subir");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "Bajar");
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
				Packet01Desconexion desconexion = new Packet01Desconexion(nombre);
				desconexion.enviar(socket);
			}
		};
	}

    protected Action mover(Movimiento Movimiento, Tablero tablero) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                moverJugador(Movimiento, tablero);
            }
        };
    }

    protected Action ponerBomba(Tablero tablero) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!tablero.tieneBomba(getUbicacionFila(), getUbicacionColumna()) &&
                        tablero.getCantidadBombas() < getbombasDisponibles() && !fantasma)
                    tablero.agregarBomba(new Bomba(getUbicacionFila(), getUbicacionColumna(), getRadioExplosion()));
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

	public int getID(){
		return id;
	}

    protected void moverJugador(Movimiento movimiento,Tablero tablero) {
		Movimiento(movimiento);

		if(tablero.chocaconBloque(this) || tablero.chocaconBomba(this))
			regresar(movimiento);

		tablero.verificarSalidaBomba();
		tablero.choqueconMejora();
		tablero.informarSensores();
    }
}