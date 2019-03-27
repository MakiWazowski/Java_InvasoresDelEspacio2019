/*
 * Pagina principal
 */
package codigo;

import java.applet.AudioClip;
import java.awt.Color;
import static java.awt.Color.black;
import static java.awt.Color.green;
import static java.awt.Color.white;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 *
 * @author Irene Benito
 */
public class VentanaJuego extends javax.swing.JFrame {
    
    //para el titulo
   public static Label label2 = new Label(); 

    //para la puntuacion
   public static Label label1 = new Label(); 
   int puntuacion = 0;

    //private boolean gameEnded=false;

  
    
    static int ANCHOPANTALLA = 700;
    static int ALTOPANTALLA = 550;
    
    //numero de marcianos que van a aparecer 
    int filas =5;
    int columnas =8;
   
    BufferedImage buffer = null;
    
    //declaro la ventana nave
    Nave miNave = new Nave();
    
    //declaro la ventana disparo 
    Disparos miDisparo = new Disparos();
    
    //declaro la ventana marciano 
 //   Marciano miMarciano = new Marciano();
    Marciano [][] listaMarcianos = new Marciano[filas][columnas];
    
    //boolean para que si uno se mueva , todos se muevan
    boolean direccionMarcianos = false;
    
    //el contador sirve para cambiar el tipo de marciano 
    int contador = 0;
    
    //imagen para cargar el spritesheet con todos los sprites del juego 
    BufferedImage plantilla = null;
    Image [][] imagenes;
    
    //fondo
     Image fondo;
     
    //para la velocidad en la que se mueve todo 
    Timer temporizador = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            bucleDelJuego();
        }
    });
    
//    public void gameOver() {
//       gameEnded = true;
//    System.exit(ABORT);
//      }
    /**
     * Creates new form VentanaJuego
     */
    public VentanaJuego() {
        initComponents();
        
        //fondo
       try {
         fondo = ImageIO.read(getClass().getResource("/imagenes/fondo.png"));
       } catch (IOException ex) {}
       //letras
        setLocationRelativeTo(null);
        Font font1;
        Font font2 = null;
        font1 = new Font("Courier New", Font.BOLD, 30);
        font2 = new Font("Calibri", Font.BOLD, 30);
        //titulo
        label2.setText("CATCH THEM ALL!!"); 
        label2.setFont(font2);
        label2.setForeground(white);
        label2.setBackground(green);
        label2.setBounds(200, 0, 600, 30);
        //puntuacion
        label1.setFont(font1);
        label1.setForeground(white);
        label1.setBackground(green);
        label1.setBounds(600, 0, 100, 30);
        label1.setText("0");
        jPanel1.add(label1);
        jPanel1.add(label2);
      
        //añadimos el sonido de fondo 2
        AudioClip sonido;
        sonido = java.applet.Applet.newAudioClip(getClass().getResource("/sonido/fondo2.wav"));
        sonido.play();
        
        //para cargar el archivo de imagenes
        //el nombre del archivo ,las filas , las columnas , el ancho del sprite y el alto del sprite y escala para cambiar el tamaño
       imagenes = cargaImagenes("/imagenes/pokemons.png", 1 , 5, 64, 64, 2);
        setSize(ANCHOPANTALLA, ALTOPANTALLA);
        buffer = (BufferedImage) jPanel1.createImage(ANCHOPANTALLA, ALTOPANTALLA);
        buffer.createGraphics();
        
        temporizador.start();
        
        //inicializo la posición inicial de la nave
//para poner una nave de un spritesheet
//        miNave.imagen = imagenes [4][2];
        miNave.x = ANCHOPANTALLA /2 - miNave.imagen.getWidth(this) / 2;
        miNave.y = ALTOPANTALLA - miNave.imagen.getHeight(this)  - 40; 
        //RETO HA HACER ESTO CON MODS (BUCLE FOR ANIDADO)
        //aqui elegimos la fila en la que vamos a poner los marcianos y el marciano segun su fila y columna
        creaFilaDeMarcianos(0, 0, 0);
        
        creaFilaDeMarcianos(1, 0, 1);
        creaFilaDeMarcianos(2, 0, 2);
        creaFilaDeMarcianos(3, 0, 3);
        creaFilaDeMarcianos(4, 0, 0);
        
        
    }
    private void creaFilaDeMarcianos (int numFila, int spriteFila, int spriteColumna){
            //inicio el array de los marcianos
          for(int j=0; j<columnas; j++){
                
                listaMarcianos[numFila][j] = new Marciano();
                //cambiar el marciano 
                listaMarcianos[numFila][j].imagen1 = imagenes[spriteFila][spriteColumna];
                listaMarcianos[numFila][j].imagen2 = imagenes[spriteFila][spriteColumna + 1];
                listaMarcianos[numFila][j].x = j*(15 + listaMarcianos[numFila][j].imagen1.getWidth(null));
                listaMarcianos[numFila][j].y = numFila*(10 + listaMarcianos[numFila][j].imagen1.getHeight(null));
            }
    }
    //Este metodo va a servir para crear el array de imagenes con todas las imagenes del spritesheet.
    //devolvera un array de dos dimensiones con las imagenes del archivo
    private Image[][] cargaImagenes(String nombreArchivoImagenes,int numFilas, int numColumnas, int ancho, int alto, int escala){
            try {
            plantilla = ImageIO.read(getClass().getResource(nombreArchivoImagenes));
        } catch (IOException ex) {}
            Image [][] arrayImagenes = new Image[numFilas][numColumnas];
            
        //cargo las imagenes de forma individual en cada imagen del array de las imagenes 
        for(int i=0; i<numFilas; i++){
            for(int j=0; j<numColumnas; j++){
            arrayImagenes[i][j] = plantilla.getSubimage(j*ancho, i*alto, ancho, alto);
            //tamaño en pantalla de los marcianos, escalar la imagen del sprite
            arrayImagenes[i][j] = arrayImagenes[i][j].getScaledInstance(ancho/escala, ancho/escala, Image.SCALE_SMOOTH);
            
            }
        }
        return arrayImagenes;
//         //la última fila del spritesheet sólo mide 32 de alto, así que hay que hacerla aparte
//        for (int j=0; j<4; j++){
//            imagenes[20 + j] = plantilla.getSubimage(j*64, 5*64, 64, 32);   
//        }
        }
    
    private void bucleDelJuego(){
        //se encarga del redibujado de los objetos en el jPanel1
        //primero borro todo lo que hay en el buffer
        contador++;
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, ANCHOPANTALLA, ALTOPANTALLA);
        
        // pintar fondo
        g2.drawImage(fondo, 0, 0, null);        
        
        ///////////////////////////////////////////////////////
        //redibujaremos aquí cada elemento
        //dibujamos el disparo  
        g2.drawImage(miDisparo.imagen, miDisparo.x, miDisparo.y, null);
        //dibujamos la nave 
        g2.drawImage(miNave.imagen, miNave.x, miNave.y, null);
        //dibujamos el marcianito
//        g2.drawImage(miMarciano.imagen1, miMarciano.x, miMarciano.y, null);
          pintaMarcianos(g2);
          chequeaColision();

        //llama al movimiento de la nave 
        miNave.mueve();
        //llama al movimiento del disparo 
        miDisparo.mueve();
        
        
        /////////////////////////////////////////////////////////////
        //*****************   fase final, se dibuja ***************//
        //*****************   el buffer de golpe sobre el Jpanel***//
        
        g2 = (Graphics2D) jPanel1.getGraphics();
        g2.drawImage(buffer, 0, 0, null);
        
    }
    //variables tipo rectangulo :calcula si un pixel alrededor de el disparo y el marciano 
    //coinciden 
    private void chequeaColision(){
        Rectangle2D.Double rectanguloMarciano = new Rectangle2D.Double();
        Rectangle2D.Double rectanguloDisparo = new Rectangle2D.Double();
        
        rectanguloDisparo.setFrame( miDisparo.x, 
                                    miDisparo.y, 
                                    miDisparo.imagen.getWidth(null),
                                    miDisparo.imagen.getHeight(null));
        
        //CALCULA EL SEGUNDO RECTANGULO 
        //un for por que son muchos marcianos
        for(int i=0; i<filas; i++){
            for(int j=0; j<columnas; j++){
                if(listaMarcianos[i][j].vivo){
                rectanguloMarciano.setFrame(listaMarcianos[i][j].x,
                                            listaMarcianos[i][j].y,
                                            listaMarcianos[i][j].imagen1.getHeight(null),
                                            listaMarcianos[i][j].imagen1.getWidth(null));
            //ahora hay que comprobar si interseccionan
            if(rectanguloDisparo.intersects(rectanguloMarciano)){
//para mandar al marciano a una posicion muy alta y que no salga en la pantalla 
                listaMarcianos[i][j].vivo = false;
                miDisparo.posicionaDisparo(miNave);
                miDisparo.y = 1000;
                miDisparo.disparado = false;
                puntuacion = puntuacion + 15;
                label1.setText("" + puntuacion);
            }
        }
            }
        }
    }
    
    private void cambiaDireccionMarcianos(){
        for(int i=0; i<filas; i++){
            for(int j=0; j<columnas; j++){
                //necesito multiplicar la velocidad por menos uno para que se mueva 
                //hacia la izquierda 
                listaMarcianos[i][j].setVelocidadX(listaMarcianos[i][j].getVelocidadX()* -1);
            }
        }
    }
    
    private void pintaMarcianos (Graphics2D _g2){
       
        int anchoMarciano = listaMarcianos[0][0].imagen1.getWidth(null);
         for(int i=1; i<filas; i++){
            for(int j=0; j<columnas; j++){
                if(listaMarcianos[i][j].vivo){
                listaMarcianos[i][j].mueve(); 
                //chequeo si el marciano se ha chocado con la pared para cambiar 
                //la direccion de todos los marcianos 
                if(listaMarcianos[i][j].x + anchoMarciano == ANCHOPANTALLA || listaMarcianos[i][j].x == 0){
                    direccionMarcianos = true;
                }
                
                if(contador < 50 ){
                    
                    _g2.drawImage(listaMarcianos[i][j].imagen1,
                            listaMarcianos[i][j].x,
                            listaMarcianos[i][j].y,
                            null);
                }
                else if(contador <100) {
                        _g2.drawImage(listaMarcianos[i][j].imagen2,
                            listaMarcianos[i][j].x,
                            listaMarcianos[i][j].y,
                            null);
                        }
                else contador = 0 ;
                }
                }        
         }
        if (direccionMarcianos){
            cambiaDireccionMarcianos();
            direccionMarcianos = false;
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 556, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 425, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        switch (evt.getKeyCode()){
            //cambio el valor de la variable a traves de un metodo 
            //lo que hago es que cuando uno este en false el otro esta en true 
            //si le doy al boton flecha izquierdo, se mueve a la derecha 
            case KeyEvent.VK_LEFT: miNave.setPulsadoIzquierda(true) ; break ;
            //si le doy al boton flecha derecho, se mueve a la izquierda 
            case KeyEvent.VK_RIGHT: miNave.setPulsadoDerecha(true) ; break ;
            //la x y la y del disparo sera el de la nave , por lo que sale por el lateral de la nave 
            case KeyEvent.VK_SPACE: miDisparo.posicionaDisparo(miNave);
                                       AudioClip sonido;
                                       sonido = java.applet.Applet.newAudioClip(getClass().getResource("/sonido/disparo.wav"));
                                       sonido.play();
//                                    miDisparo.sonidoDisparo.start();
                                    miDisparo.disparado = true;
                                    break;
        }   
               
    }//GEN-LAST:event_formKeyPressed

    
    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
                                         
        switch (evt.getKeyCode()){
            
            case KeyEvent.VK_LEFT: miNave.setPulsadoIzquierda(false) ; break ; 
            case KeyEvent.VK_RIGHT: miNave.setPulsadoDerecha(false) ; break ;
        }
               
         
    }//GEN-LAST:event_formKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaJuego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
