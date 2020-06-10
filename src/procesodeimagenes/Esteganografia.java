/*
 * UNAM, Facultad de Ciencias
 * Proceso Digital de Imágenes
 * Alumno: Rodríguez Romero Sergio Alfonso
 * No. de Cuenta: 314187785
 * Correo: sergiorodriguez@ciencias.unam.mx
 */
package procesodeimagenes;

import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.embed.swing.SwingFXUtils;

/**
 *
 * @author Rodríguez Romero Sergio Alfonso
 */
public class Esteganografia {
    
    BufferedImage imagen;
    Image img;
    
    //Constructor de la clase.
    public Esteganografia(BufferedImage imagen){
        this.imagen = imagen;
        this.img = SwingFXUtils.toFXImage(imagen, null);
    }
    
    public Image encripta(String mensaje){
        int w = this.imagen.getWidth();
        int h = this.imagen.getHeight();
        int marca = 0;
        int grisImagenR,grisImagenG,grisImagenB,grisImagenR2,grisImagenG2,grisImagenB2,grisImagenR3,grisImagenG3,grisImagenB3,
                newR,newG,newB,newR2,newG2,newB2,newR3,newG3;
        WritableImage imagenF = new WritableImage(w, h);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h-2; j++) { 
                grisImagenR = (int)(this.img.getPixelReader().getColor(i, j).getRed()*255);
                grisImagenG = (int)(this.img.getPixelReader().getColor(i, j).getGreen()*255);
                grisImagenB = (int)(this.img.getPixelReader().getColor(i, j).getBlue()*255);                
                if(marca < mensaje.length()){
                    grisImagenR2 = (int)(this.img.getPixelReader().getColor(i, j+1).getRed()*255);
                    grisImagenG2 = (int)(this.img.getPixelReader().getColor(i, j+1).getGreen()*255);
                    grisImagenB2 = (int)(this.img.getPixelReader().getColor(i, j+1).getBlue()*255);
                    grisImagenR3 = (int)(this.img.getPixelReader().getColor(i, j+2).getRed()*255);
                    grisImagenG3 = (int)(this.img.getPixelReader().getColor(i, j+2).getGreen()*255);
                    grisImagenB3 = (int)(this.img.getPixelReader().getColor(i, j+2).getBlue()*255);
                    int letra = (int)mensaje.charAt(marca++);
                    newR = (grisImagenR & ~1) | (1 & (letra >> 7));
                    newG = (grisImagenG & ~1) | (1 & (letra >> 6));
                    newB = (grisImagenB & ~1) | (1 & (letra >> 5));
                    newR2 = (grisImagenR2 & ~1) | (1 & (letra >> 4));
                    newG2 = (grisImagenG2 & ~1) | (1 & (letra >> 3));
                    newB2 = (grisImagenB2 & ~1) | (1 & (letra >> 2));
                    newR3 = (grisImagenR3 & ~1) | (1 & (letra >> 1));
                    newG3 = (grisImagenG3 & ~1) | (1 & (letra));
                    imagenF.getPixelWriter().setColor(i, j, Color.rgb(newR, newG, newB));
                    imagenF.getPixelWriter().setColor(i, j+1, Color.rgb(newR2, newG2, newB2));
                    imagenF.getPixelWriter().setColor(i, j+2, Color.rgb(newR3, newG3, grisImagenB3));
                    j += 2;
                }else{
                    grisImagenR &= ~1;
                    grisImagenG &= ~1;
                    grisImagenB &= ~1;
                    imagenF.getPixelWriter().setColor(i, j, Color.rgb(grisImagenR, grisImagenG, grisImagenB));
                }
            }
        }
        return imagenF;
    }
    
    public String descifra(){
        int w = this.imagen.getWidth();
        int h = this.imagen.getHeight();
        String mensaje = "";
        int letra;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h-2; j += 3) {
                int letra1 = (int)(this.img.getPixelReader().getColor(i, j).getRed()*255);
                int letra2 = (int)(this.img.getPixelReader().getColor(i, j).getGreen()*255);
                int letra3 = (int)(this.img.getPixelReader().getColor(i, j).getBlue()*255);
                int letra4 = (int)(this.img.getPixelReader().getColor(i, j+1).getRed()*255);
                int letra5 = (int)(this.img.getPixelReader().getColor(i, j+1).getGreen()*255);
                int letra6 = (int)(this.img.getPixelReader().getColor(i, j+1).getBlue()*255);
                int letra7 = (int)(this.img.getPixelReader().getColor(i, j+2).getRed()*255);
                int letra8 = (int)(this.img.getPixelReader().getColor(i, j+2).getGreen()*255);
                letra = ((letra1 << 7) & 128) + ((letra2 << 6) & 64) + ((letra3 << 5) & 32) +
                        ((letra4 << 4) & 16) + ((letra5 << 3) & 8) + ((letra6 << 2) & 4)+((letra7 << 1) & 2) + (letra8 & 1);
                mensaje = mensaje.concat(""+(char)letra); 
            }
            mensaje = mensaje.trim();
        }
        return mensaje.replace(""+(char)0,"").trim();
    }
}
