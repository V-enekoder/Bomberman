package graficos;

/**
 * Esta clase representa un botón de configuración.
 * Permite definir colores disponibles y no disponibles, así como su posición en la pantalla.
 */
public class BotonConfiguracion {
    private String colorDisponible;
    private String colorNoDisponible;
    private int x;

    /**
     * Constructor de la clase BotonConfiguracion.
     * @param colorDisponible El color disponible para el botón.
     * @param colorNoDisponible El color no disponible para el botón.
     * @param x La posición horizontal del botón en la pantalla.
     */
    public BotonConfiguracion(String colorDisponible, String colorNoDisponible, int x) {
        this.colorDisponible = colorDisponible;
        this.colorNoDisponible = colorNoDisponible;
        this.x = x;
    }

    /**
     * Devuelve el color disponible para el botón.
     * @return El color disponible para el botón.
     */
    public String getColorDisponible() {
        return colorDisponible;
    }

    /**
     * Devuelve el color no disponible para el botón.
     * @return El color no disponible para el botón.
     */
    public String getColorNoDisponible() {
        return colorNoDisponible;
    }
    
    /**
     * Devuelve la posición horizontal del botón en la pantalla.
     * @return La posición horizontal del botón.
     */
    public int getX() {
        return x;
    }
}
