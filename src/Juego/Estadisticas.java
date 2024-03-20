package Juego;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Esta clase representa las estadísticas de un jugador en el juego.
 * Proporciona métodos para aumentar y obtener las estadísticas, así como para guardarlas en un archivo.
 */
public class Estadisticas{
    private int partidasJugadas;       // Número de partidas jugadas
    private int partidasAbandonadas;   // Número de partidas abandonadas
    private int partidasPerdidas;      // Número de partidas perdidas
    private int partidasGanadas;       // Número de partidas ganadas
    private File archivo;              // Archivo para guardar las estadísticas
    
    /**
     * Constructor de la clase Estadisticas.
     * Inicializa las estadísticas del jugador y establece el archivo para guardarlas.
     * @param nombre El nombre del jugador.
     */
    public Estadisticas(String nombre) {
        this.partidasJugadas = 0;
        this.partidasAbandonadas = 0;
        this.partidasPerdidas = 0;
        this.partidasGanadas = 0;
        archivo = new File("src/DatosJugadores/"+nombre +".txt");
    }

    /**
     * Incrementa el número de partidas jugadas.
     */
    public void aumentarPartidasJugadas() {
        this.partidasJugadas++;
    }

    /**
     * Incrementa el número de partidas abandonadas.
     */
    public void aumentarPartidasAbandonadas() {
        this.partidasAbandonadas++;
    }

    /**
     * Incrementa el número de partidas perdidas.
     */
    public void aumentarPartidasPerdidas() {
        this.partidasPerdidas++;
    }

    /**
     * Incrementa el número de partidas ganadas.
     */
    public void aumentarPartidasGanadas() {
        this.partidasGanadas++;
    }

    /**
     * Obtiene el número de partidas jugadas.
     * @return El número de partidas jugadas.
     */
    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    /**
     * Obtiene el número de partidas abandonadas.
     * @return El número de partidas abandonadas.
     */
    public int getPartidasAbandonadas() {
        return partidasAbandonadas;
    }

    /**
     * Obtiene el número de partidas perdidas.
     * @return El número de partidas perdidas.
     */
    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }

    /**
     * Obtiene el número de partidas ganadas.
     * @return El número de partidas ganadas.
     */
    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    /**
     * Establece el número de partidas jugadas.
     * @param partidasJugadas El número de partidas jugadas.
     */
    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    /**
     * Establece el número de partidas abandonadas.
     * @param partidasAbandonadas El número de partidas abandonadas.
     */
    public void setPartidasAbandonadas(int partidasAbandonadas) {
        this.partidasAbandonadas = partidasAbandonadas;
    }

    /**
     * Establece el número de partidas ganadas.
     * @param partidasGanadas El número de partidas ganadas.
     */
    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    /**
     * Establece el número de partidas perdidas.
     * @param partidasPerdidas El número de partidas perdidas.
     */
    public void setPartidasPerdidas(int partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }

    /**
     * Guarda las estadísticas en un archivo.
     * Si el archivo no existe, se crea. Si existe, se actualiza con las estadísticas actuales.
     */
    public void guardar() {
        // Crear el directorio si no existe
        File directorioPaquete = archivo.getParentFile();
        if (!directorioPaquete.exists()) {
            directorioPaquete.mkdirs();
        }
    
        if (!archivo.exists()) {
            System.out.println("El archivo de estadísticas no existe en la ruta: " + archivo.getAbsolutePath());
            return;
        }
    
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            StringBuilder sb = new StringBuilder();
            String line;
            // Leer las estadísticas actuales del archivo
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String statType = parts[0].trim();
                    int statValue = Integer.parseInt(parts[1].trim());
    
                    // Actualizar las estadísticas según corresponda
                    switch (statType) {
                        case "Partidas jugadas":
                            statValue += partidasJugadas;
                            break;
                        case "Partidas ganadas":
                            statValue += partidasGanadas;
                            break;
                        case "Partidas perdidas":
                            statValue += partidasPerdidas;
                            break;
                        case "Partidas abandonadas":
                            statValue += partidasAbandonadas;
                            break;
                    }
                    sb.append(statType).append(": ").append(statValue).append("\n");
                }
            }
    
            // Escribir las estadísticas actualizadas en el archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}