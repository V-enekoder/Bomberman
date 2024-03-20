package graficos;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Esta clase representa el menú de estadísticas en el juego Bomberman.
 * Permite a los jugadores ver las estadísticas de juego y buscar usuarios específicos.
 */
public class MenuEstadisticas extends Menu{

    private JLabel viewUser, viewPartidasJugadas, viewPartidasGanadas, viewPartidasPerdidas, viewPartidasAbandonadas;
    private JButton botonVolver, botonBuscar;
    private JTextField nameSearch;

    /**
     * Constructor de la clase MenuEstadisticas.
     * Crea y configura la ventana del menú de estadísticas.
     */
    public MenuEstadisticas(){
        panel = new JPanel();
        panel.removeAll();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        // Configura la ventana del juego
        setSize(616, 639);
        setTitle("Bomberman: Menu de Estadisticas (Ver informacion)");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        iniciarComponentes();
        setVisible(true);
    }

    @Override
    protected void iniciarComponentes(){
        colocarEtiquetas();
        colocarCajaTexto();
        colocarBotones();
    }

    /**
     * Coloca las etiquetas de las estadísticas en el panel.
     */
    void colocarEtiquetas(){
        background=new JLabel();
        background.setIcon(new ImageIcon("menuStats.png"));
        background.setBounds(0, 0, 600, 600);
        background.setOpaque(false);
        panel.add(background);

        viewUser = new JLabel(); 
        viewUser.setBounds(192,172,200,18);
        viewUser.setFont(new Font("Arial",1,18));
        viewUser.setOpaque(false);
        viewUser.setHorizontalAlignment(SwingConstants.CENTER);
        viewUser.setForeground(Color.WHITE);
        panel.add(viewUser);
        panel.setComponentZOrder(viewUser, 0);

        viewPartidasJugadas=new JLabel(""); 
        viewPartidasJugadas.setBounds(284,241,200,18);
        viewPartidasJugadas.setFont(new Font("Arial",1,18));
        viewPartidasJugadas.setOpaque(false);
        viewPartidasJugadas.setForeground(Color.WHITE);
        panel.add(viewPartidasJugadas);
        panel.setComponentZOrder(viewPartidasJugadas, 0);

        viewPartidasGanadas=new JLabel(""); 
        viewPartidasGanadas.setBounds(290,293,200,18);
        viewPartidasGanadas.setFont(new Font("Arial",1,18));
        viewPartidasGanadas.setOpaque(false);
        viewPartidasGanadas.setForeground(Color.WHITE);
        panel.add(viewPartidasGanadas);
        panel.setComponentZOrder(viewPartidasGanadas, 0);


        viewPartidasAbandonadas=new JLabel(""); 
        viewPartidasAbandonadas.setBounds(344,341,200,18);
        viewPartidasAbandonadas.setFont(new Font("Arial",1,18));
        viewPartidasAbandonadas.setOpaque(false);
        viewPartidasAbandonadas.setForeground(Color.WHITE);
        panel.add(viewPartidasAbandonadas);
        panel.setComponentZOrder(viewPartidasAbandonadas, 0);


        viewPartidasPerdidas=new JLabel("");
        viewPartidasPerdidas.setBounds(293,395,200,18);
        viewPartidasPerdidas.setFont(new Font("Arial",1,18));
        viewPartidasPerdidas.setOpaque(false);
        viewPartidasPerdidas.setForeground(Color.WHITE);
        panel.add(viewPartidasPerdidas);
        panel.setComponentZOrder(viewPartidasPerdidas, 0);

    }

    /**
     * Coloca la caja de texto para la búsqueda de usuarios en el panel.
     */
    void colocarCajaTexto(){

        nameSearch = new JTextField();
        nameSearch.setBounds(199, 170, 185, 25);
        panel.add(nameSearch);

    }

    /**
     * Reproduce un sonido desde un archivo de audio.
     * @param ruta La ruta del archivo de audio.
     */
    private void reproducirSonido(String ruta) {
        try {
            File archivoSonido = new File(ruta);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivoSonido);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Coloca los botones en el panel.
     */
    void colocarBotones(){
        botonVolver = new JButton();
        botonVolver.setBounds(228, 450, 147, 28);
        botonVolver.setEnabled(true);
        botonVolver.setIcon(new ImageIcon("botonVolver.png"));
        panel.add(botonVolver);
        botonVolver.setVisible(true);

        botonBuscar = new JButton();
        botonBuscar.setBounds(393, 165, 87, 30);
        botonBuscar.setEnabled(true);
        botonBuscar.setIcon(new ImageIcon("botonBuscar.png"));
        panel.add(botonBuscar);
        botonBuscar.setVisible(true);

        botonVolver.addActionListener(volverMenuPrincipal);
        botonBuscar.addActionListener(buscarUser);
    }

    /**
     * Acción realizada al hacer clic en el botón "Volver".
     */
    ActionListener volverMenuPrincipal = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            background.setVisible(false);
            reproducirSonido("audio2.wav");
            retornar();
        }
    };

    /**
     * Acción realizada al hacer clic en el botón "Buscar".
     */
    ActionListener buscarUser = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String texto = nameSearch.getText();
            reproducirSonido("audio2.wav");
            String jugadas=" ", perdidas= " ", abandonadas=" ", ganadas=" ";
            File archivo = new File("src/DatosJugadores/"+texto+".txt");
        if (!archivo.exists()) {
            System.out.println("el archivo de estadisticas no existe.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");

                if (parts.length == 2) {
                    String statType = parts[0].trim();
                    
                    String valor = parts [1].trim();
                    switch (statType) {
                        case "Partidas jugadas":
                            jugadas=valor;
                            break;
                        case "Partidas ganadas":
                            ganadas=valor;
                            break;
                        case "Partidas perdidas":
                             perdidas=valor;
                            break;
                        case "Partidas abandonadas":
                            abandonadas=valor;
                            break;
                    }
                    
                }

            }
        }catch(IOException exp){
            System.out.println(exp.getMessage());
        }
            viewPartidasJugadas.setText(jugadas);
            viewPartidasAbandonadas.setText(abandonadas);
            viewPartidasGanadas.setText(ganadas);
            viewPartidasPerdidas.setText(perdidas);
            repintar();
        }
    };

    /**
     * Retorna al menú principal del juego.
     */
    void retornar(){
            new MenuPrincipal();
            this.dispose();
        }

    /**
     * Repinta los componentes del panel.
     */
    void repintar(){
        iniciarComponentes();
        panel.repaint();
    }   
}
