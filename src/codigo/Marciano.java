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
    
    
        public Marciano(){
        try {
            imagen1 = ImageIO.read(getClass().getResource("/imagenes/marcianito1.png"));
            imagen2 = ImageIO.read(getClass().getResource("/imagenes/marcianito2.png"));
        } catch (IOException ex) {
            
        }
        }
        //va sumandole la x a la posicion para su movimiento horizontal 
        public void mueve(){
        x += velocidadX;
        }
}

