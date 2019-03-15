/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public int y = 0;
    
    
        public Disparos(){
        try {
            imagen = ImageIO.read(getClass().getResource("/imagenes/disparo.png"));
        } catch (IOException ex) {
            
        }
    }
        
        public void mueve(){
            y--;
        }
        
        public void posicionaDisparo(Nave _nave){
            //colocamos el disparo en la izq de la nave , le sumamos la 
            //mitad de la nave y le restamos la mitad del disparo para centrarlo
            x = _nave.x + _nave.imagen.getWidth(null)/2 - imagen.getWidth(null)/2;
            //para que salga desde la altura de la nave 
            y = _nave.y;
            
        }
}
//hay que crear una estructura de varios disparos con un array con x elementos
//dependiendo del numero de disparos que queremos permitir 

//hay que hacer que los disparos choquen 
