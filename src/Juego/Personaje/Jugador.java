package Juego.Personaje;

import javax.swing.*;

import Juego.Bomba;
import Juego.ComponenteGrafico;
import Juego.Tablero;

import java.awt.event.ActionEvent;

public class Jugador extends Personaje{
	private int id;
    private int radioExplosion;
    private int bombasDisponibles;
	private int vidas;
	private int duracionInmunidad;
	private int tiempoInmunidad;
	private boolean inmune;

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
		this.tiempoInmunidad -= 125;
	}

	public boolean isInmune() {
		return inmune;
	}

	public void setInmune(boolean inmune) {
		this.inmune = inmune;
	}

	public Jugador(ComponenteGrafico bombermanComponent, Tablero tablero, int[] posicion, int id){
		super(posicion[0],posicion[1]);
		radioExplosion = 1;
		bombasDisponibles = 1;
		vidas = 3;
		this.id = id;
		duracionInmunidad = 5*1000; //5 segundos por 1000 milisegundos
		tiempoInmunidad = duracionInmunidad;
		inmune = false; 
		configurarControles(bombermanComponent, tablero/*, id*/);
    }

	protected void configurarControles(ComponenteGrafico bombermanComponent, Tablero tablero/* , int id*/) {
		//if (id == 0) {
			bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "Derecha");
			bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "Izquierda");
			bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("UP"), "Subir");
			bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "Bajar");
			bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "dropBomb");
		//} else if (id == 1) {
			bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("D"), "Derecha");
			bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("A"), "Izquierda");
			bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("W"), "Subir");
			bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("S"), "Bajar");
			bombermanComponent.getInputMap().put(KeyStroke.getKeyStroke("Z"), "dropBomb");
		//}
	
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
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // Sale del juego
			}
		};
	}

    /*private*/protected Action mover(Movimiento Movimiento, Tablero tablero) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                moverJugador(Movimiento, tablero);
            }
        };
    }

    /*private*/protected Action ponerBomba(Tablero tablero) {
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

	public void chocaConEnemigo(Tablero tablero){
		if(tablero.choqueconEnemigos() && !inmune){
			reducirVidas();
			inmune = true;
			if(vidas == 0)
				tablero.setGameOver(true);
		}
	}
}
/*
Claro, aquí tienes un ejemplo simplificado de cómo podrías modificar el código para permitir que dos jugadores se muevan simultáneamente. En este ejemplo, cada jugador tiene sus propios controles y su propio `ComponenteGrafico`.

Primero, modificaríamos la clase `ComponenteGrafico` para manejar múltiples jugadores y sus controles:

```java
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ComponenteGrafico extends JComponent {
    private final Tablero tablero;
    private final ArrayList<Jugador> jugadores;

    public ComponenteGrafico(Tablero tablero, ArrayList<Jugador> jugadores) {
        this.tablero = tablero;
        this.jugadores = jugadores;
    }

    public void configurarControles() {
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            ActionMap actionMap = getActionMap();
            
            String rightKey = (i == 0) ? "Derecha" : "D";
            String leftKey = (i == 0) ? "Izquierda" : "A";
            String upKey = (i == 0) ? "Subir" : "W";
            String downKey = (i == 0) ? "Bajar" : "S";
            String bombKey = (i == 0) ? "dropBomb" : "Z";

            inputMap.put(KeyStroke.getKeyStroke("RIGHT"), rightKey);
            inputMap.put(KeyStroke.getKeyStroke("LEFT"), leftKey);
            inputMap.put(KeyStroke.getKeyStroke("UP"), upKey);
            inputMap.put(KeyStroke.getKeyStroke("DOWN"), downKey);
            inputMap.put(KeyStroke.getKeyStroke("SPACE"), bombKey);

            actionMap.put(rightKey, jugador.mover(Movimiento.DERECHA, tablero));
            actionMap.put(leftKey, jugador.mover(Movimiento.IZQUIERDA, tablero));
            actionMap.put(upKey, jugador.mover(Movimiento.ARRIBA, tablero));
            actionMap.put(downKey, jugador.mover(Movimiento.ABAJO, tablero));
            actionMap.put(bombKey, jugador.ponerBomba(tablero));
        }
    }

    // Resto del código...
}
```

Luego, en la clase `Jugador`, haríamos algunas modificaciones para manejar los controles:

```java
import javax.swing.*;
import java.awt.event.ActionEvent;

public class Jugador extends Personaje {
    private int id;
    private int radioExplosion;
    private int bombasDisponibles;
    // Otros atributos...

    public Jugador(ComponenteGrafico bombermanComponent, Tablero tablero, int[] posicion, int id) {
        super(posicion[0], posicion[1]);
        // Inicialización de atributos...
        this.id = id;
        // Otros atributos...
    }

    public Action mover(Movimiento movimiento, Tablero tablero) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                moverJugador(movimiento, tablero);
            }
        };
    }

    public Action ponerBomba(Tablero tablero) {
        return new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para poner la bomba...
            }
        };
    }

    // Otro código de la clase...
}
```

Con estos cambios, ahora deberías ser capaz de controlar a dos jugadores simultáneamente en el juego. Asegúrate de instanciar los jugadores y el componente gráfico correctamente y de llamar a `configurarControles()` después de crear todos los jugadores.
 */