/*
 * el marcianito enemigo 
 */
package codigo;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Irene Benito
 */
public class Marciano {
    
    //Copiado de la nave :
    public Image imagen1, imagen2 = null;
    public int x = 0;
    public int y = 0;
    //para que se muevan los marcianos 
    private int velocidadX = 1;
    //para hacer que desaparezca el marciano
    public boolean vivo = true;
    
    
        public Marciano(){
        try {
            imagen1 = ImageIO.read(getClass().getResource("/imagenes/marcianito1.png"));
            imagen2 = ImageIO.read(getClass().getResource("/imagenes/marcianito2.png"));
        } catch (IOException ex) {
            
        }
        }
        //va sumandole la x a la posicion para su movimiento horizontal 
        //y bloqueamos su movimiento al final de la pantalla 
        public void mueve(){
            
             x += velocidadX;
           
//       if(x >= VentanaJuego.ANCHOPANTALLA - imagen1.getWidth(null)-imagen1.getWidth(null)/3){
//             y += velocidadX;
//            }
        }

        
        //el set establece un valor , en este caso contrario al que habia 
        //con este set , lo llamamos a la ventabna principal del juego
    public void setVelocidadX(int velocidadX) {
        this.velocidadX = velocidadX;
    }
//get lee el valor 
    public int getVelocidadX() {
        return velocidadX;
    }
}

