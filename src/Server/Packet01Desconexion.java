package Server;

public class Packet01Desconexion extends Packet{

    private String nombre;
    public Packet01Desconexion(byte[] datos) { //Recuperando info
        super(01);
        this.nombre = leerInformacion(datos);
    }

    public Packet01Desconexion(String nombre) { //Desde el usuario
        super(01);
        this.nombre = nombre;
    }

    @Override
    public void escribirInformacion(Cliente cliente){
        cliente.enviar(getDatos());
    }

    @Override
    public void escribirInformacion(Servidor servidor){
        servidor.enviarTodosLosClientes(getDatos());
    }

    @Override
    public byte[] getDatos() {
        return ("01" + this.nombre).getBytes();
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
