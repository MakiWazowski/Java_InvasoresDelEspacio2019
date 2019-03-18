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
    //limitar la nave con la pantalla
        //hacia la derecha, la nave llega hasta el final de la pantalla menos 
        //la nave misma
    public void mueve(){
        if(pulsadoDerecha && x < VentanaJuego.ANCHOPANTALLA - imagen.getWidth(null) - imagen.getWidth(null)/5){
           x += 3;  
        }
        // con x -= 3; va mas rapido que con x--;
        if(pulsadoIzquierda && x > 0){
           x -= 3;
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

