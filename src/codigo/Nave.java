/*
 * La nave del juego
 */
package codigo;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Irene Benito
 */
public class Nave {
    public Image imagen = null;
    public int x = 0;
    public int y = 0;
     //creo dos variables booleanas que me indiquen si me estoy moviendo a la dereacha o a la izquierda 
    //en false para que cuando empieze , que este quieta la nave 
    //click derecho insert code getter and setter combierte el boolean private 
    //significa que a estas clases no puedes acceder desde la otra ventana 
    //pero si puedes desde el getter and setter 
    private boolean pulsadoIzquierda = false;
    private boolean pulsadoDerecha = false;
    
    public Nave(){
        try {
            imagen = ImageIO.read(getClass().getResource("/imagenes/nave.png"));
        } catch (IOException ex) {
            
        }
    }
    //para posibilitar que se mueva en el eje x 
    public void mueve(){
        if(pulsadoDerecha){
           x++;  
        }
        if(pulsadoIzquierda){
           x--;
        }
        
    }
//getter and setter del insert code de los boolean 
    
    public boolean isPulsadoIzquierda() {
        return pulsadoIzquierda;
    }

    public void setPulsadoIzquierda(boolean pulsadoIzquierda) {
        this.pulsadoIzquierda = pulsadoIzquierda;
        //añadimos el false para que cuando uno funcione el otro no 
        this.pulsadoDerecha = false ;
    }

    public boolean isPulsadoDerecha() {
        return pulsadoDerecha;
    }

    public void setPulsadoDerecha(boolean pulsadoDerecha) {
        this.pulsadoDerecha = pulsadoDerecha;
        //añadimos el false para que cuando uno funcione el otro no
         this.pulsadoIzquierda = false ;
    }
}
