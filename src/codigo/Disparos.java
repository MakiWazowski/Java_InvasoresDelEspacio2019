/*
 * disparos del juego 
 */
package codigo;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Irene Benito
 */
public class Disparos {
    
    //Copiado de la nave :
    public Image imagen = null;
    public int x = 0;
    public int y = 2000;
    //cuando diga disparo mueve , chequea esta variable
    public boolean disparado = false ;
    
        public Disparos(){
        try {
            imagen = ImageIO.read(getClass().getResource("/imagenes/disparo.png"));
        } catch (IOException ex) {
            
        }
    }
        //para el movimiento y la velocidad del disparo
        public void mueve(){
            if(disparado){
            y -= 10;
            }
        }
        public void posicionaDisparo(Nave _nave){
            //colocamos el disparo en la izq de la nave , le sumamos la 
            //mitad de la nave y le restamos la mitad del disparo para centrarlo
            x = _nave.x + _nave.imagen.getWidth(null)/2 - imagen.getWidth(null)/2;
            //para que salga desde la altura de la nave 
            y = _nave.y - _nave.imagen.getWidth(null)/2;
          //  disparado = true;
        }
}
//hay que crear una estructura de varios disparos con un array con x elementos
//dependiendo del numero de disparos que queremos permitir 

//hay que hacer que los disparos choquen 
