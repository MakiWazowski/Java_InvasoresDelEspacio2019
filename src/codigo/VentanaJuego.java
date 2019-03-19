/*
 * Pagina principal
 */
package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

/**
 *
 * @author Irene Benito
 */
public class VentanaJuego extends javax.swing.JFrame {

    static int ANCHOPANTALLA = 600;
    static int ALTOPANTALLA = 450;
    
    //numero de marcianos que van a aparecer 
    int filas =5;
    int columnas =10;
    
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
    
    //el contador sit¡rve para cambiar el tipo de marciano 
    int contador = 0;
    
    Timer temporizador = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            bucleDelJuego();
        }
    });
    /**
     * Creates new form VentanaJuego
     */
    public VentanaJuego() {
        initComponents();
        setSize(ANCHOPANTALLA, ALTOPANTALLA);
        buffer = (BufferedImage) jPanel1.createImage(ANCHOPANTALLA, ALTOPANTALLA);
        buffer.createGraphics();
        
        temporizador.start();
        
        //inicializo la posición inicial de la nave
        miNave.x = ANCHOPANTALLA /2 - miNave.imagen.getWidth(this) / 2;
        miNave.y = ALTOPANTALLA - miNave.imagen.getHeight(this)  - 40; 
        
        //inicio el array de los marcianos
         for(int i=0; i<filas; i++){
            for(int j=0; j<columnas; j++){
                
                listaMarcianos[i][j] = new Marciano();
                listaMarcianos[i][j].x = j*(8 + listaMarcianos[i][j].imagen1.getWidth(null));
                listaMarcianos[i][j].y = i*(8 + listaMarcianos[i][j].imagen1.getHeight(null));
            }
         }
         miDisparo.posicionaDisparo(miNave);
    }
    
    private void bucleDelJuego(){
        //se encarga del redibujado de los objetos en el jPanel1
        //primero borro todo lo que hay en el buffer
        contador++;
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, ANCHOPANTALLA, ALTOPANTALLA);
        
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
                
                rectanguloMarciano.setFrame(listaMarcianos[i][j].x,
                                            listaMarcianos[i][j].y,
                                            listaMarcianos[i][j].imagen1.getHeight(null),
                                            listaMarcianos[i][j].imagen1.getWidth(null));
            //ahora hay que comprobar si interseccionan
            if(rectanguloDisparo.intersects(rectanguloMarciano)){
//para mandar al marciano a una posicion muy alta y que no salga en la pantalla 
                listaMarcianos[i][j].y = 2000;
                miDisparo.posicionaDisparo(miNave);
                
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
         for(int i=0; i<filas; i++){
            for(int j=0; j<columnas; j++){
                
                listaMarcianos[i][j].mueve(); 
                
                //chequeo si el marciano se ha chocado con la pared para cambiar 
                //la direccion de todos los marcianos 
                if(listaMarcianos[i][j].x + anchoMarciano == ANCHOPANTALLA || listaMarcianos[i][j].x == 0){
                    cambiaDireccionMarcianos();
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
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
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
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
