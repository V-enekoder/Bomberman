package Juego;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class Estadisticas implements Serializable {
    private int partidasJugadas;
    private int partidasAbandonadas;
    private int partidasPerdidas;
    private int partidasGanadas;
    private File archivo;
   
    // Enum para identificar las estadi­sticas
    public enum TipoEstadistica { //Probablemente se vaya este enum
        JUGADAS,
        GANADAS,
        PERDIDAS,
        ABANDONADAS
    }
    
    public Estadisticas(String nombre) {
    this.partidasJugadas = 0;
    this.partidasAbandonadas = 0;
    this.partidasPerdidas = 0;
    this.partidasGanadas = 0;
    archivo = new File("src/DatosJugadores/"+nombre +".txt");
    }

    public void aumentarPartidasJugadas() {
        this.partidasJugadas++;
    }

    public void aumentarPartidasAbandonadas() {
        this.partidasAbandonadas++;
    }

    public void aumentarPartidasPerdidas() {
        this.partidasPerdidas++;
    }

    public void aumentarPartidasGanadas() {
        this.partidasGanadas++;
    }

    // MÃ©todos para obtener las estadÃ­sticas

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public int getPartidasAbandonadas() {
        return partidasAbandonadas;
    }

    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    public void setPartidasAbandonadas(int partidasAbandonadas) {
        this.partidasAbandonadas = partidasAbandonadas;
    }

    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    public void setPartidasPerdidas(int partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }

    public void guardar() {
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
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String statType = parts[0].trim();
                    int statValue = Integer.parseInt(parts[1].trim());
    
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
    
            // Escribir el contenido actualizado en el archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                writer.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}