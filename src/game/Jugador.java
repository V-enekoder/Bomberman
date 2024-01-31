package game;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class Jugador extends Personaje{

    private static int CASILLA_INICIAL_X = 60;
    private static int CASILLA_INICIAL_Y = 60;
    private int radioExplosion;
    private int bombasDisponibles;
	private int vidas;
 
    public Jugador(ComponenteGrafico bombermanComponent, Tablero tablero){
		super(CASILLA_INICIAL_X, CASILLA_INICIAL_Y);
		radioExplosion = 1;
		bombasDisponibles = 1;
		vidas = 1;
		configurarControles(bombermanComponent, tablero);
    }

	private void configurarControles(ComponenteGrafico bombermanComponent, Tablero tablero){
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "Derecha");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "Izquierda");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("UP"), "Subir");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "Bajar");
		bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "dropBomb");
		bombermanComponent.getActionMap().put("Derecha", mover(Movimiento.DERECHA, tablero));
		bombermanComponent.getActionMap().put("Izquierda", mover(Movimiento.IZQUIERDA, tablero));
        bombermanComponent.getActionMap().put("Subir", mover(Movimiento.ARRIBA, tablero));
        bombermanComponent.getActionMap().put("Bajar", mover(Movimiento.ABAJO, tablero));
		bombermanComponent.getActionMap().put("dropBomb", ponerBomba(tablero));
		//bombermanComponent.getInputMap().put("ESC", "q");
		//bombermanComponent.getActionMap().put("q", quit);
    }
    
	/*private final Action quit = new AbstractAction(){
		public void actionPerformed(ActionEvent e) {
			dispose();    
		}
    };*/

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
                        tablero.getCantidadBombas() < getbombasDisponibles())
                    tablero.agregarBomba(new Bomba(getUbicacionFila(), getUbicacionColumna(), getRadioExplosion()));
                tablero.informarSensores();
            }
        };
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
		
		if(tablero.choqueconEnemigos()){
			//reducirVidas();
			//if(vidas == 0)
				tablero.setGameOver(true);
		}

		tablero.verificarSalidaBomba();
		tablero.choqueconMejora();
		tablero.informarSensores();
    }
}
