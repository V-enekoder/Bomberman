package graficos;

public class BotonConfiguracion {
    private String colorDisponible;
    private String colorNoDisponible;
    private int x;

    public BotonConfiguracion(String colorDisponible, String colorNoDisponible, int x) {
        this.colorDisponible = colorDisponible;
        this.colorNoDisponible = colorNoDisponible;
        this.x = x;
    }

    public String getColorDisponible() {
        return colorDisponible;
    }

    public String getColorNoDisponible() {
        return colorNoDisponible;
    }
    
    public int getX() {
        return x;
    }
}
