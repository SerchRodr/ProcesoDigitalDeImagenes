/*
 * UNAM, Facultad de Ciencias
 * Proceso Digital de Imágenes
 * Alumno: Rodríguez Romero Sergio Alfonso
 * No. de Cuenta: 314187785
 * Correo: sergiorodriguez@ciencias.unam.mx
 */
package procesodeimagenes;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelReader;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Rodríguez Romero Sergio Alfonso
 */
public class Fotomosaico extends abrirImagen {
    
    File bibliotecaImagenes;
    BufferedImage imagen;
    Image img;
    
    //Constructor de la clase.
    public Fotomosaico(File dir, boolean bibliotecaExistente, BufferedImage imagen) throws IOException{
        if(bibliotecaExistente == false){
            this.bibliotecaImagenes = calculaBiblioteca(dir);
        }
        else{
            this.bibliotecaImagenes = dir;
        }
        this.imagen = imagen;
        this.img = SwingFXUtils.toFXImage(imagen, null);
    }
    
    //Método que obtiene el fotomosaico de una imagen.
    public void sacaMosaico(String metodo){
        int anchoX, largoY;
        try{
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre para guardar la imagen en formato html");
            nombre += ".html";
            anchoX = largoY = numMosaicos();
            LinkedList<LinkedList<String>> imagenes = new LinkedList<>();
            int terminoX, terminoY;
            double rojoRGB, verdeRGB, azulRGB, r, g, b;
            int promedio = 0;
            PixelReader pixelI = this.img.getPixelReader();
            rojoRGB = verdeRGB = azulRGB = 0;
            File salida = new File(nombre);
            salida.createNewFile();
            FileWriter html = new FileWriter(salida);
            BufferedWriter escritor = new BufferedWriter(html);
            String texto = "<table border = \"0\" cellspacing=\"0\" cellpadding=\"0\" \n"
                    + "<tr>";
            String imagenTemp;
            escritor.write(texto);
            escritor.flush();
            for(int i = 0; i < img.getWidth(); i+= anchoX){
                terminoX = i+anchoX;
                LinkedList<String> lTemp = new LinkedList<>();
                for(int j = 0; j < img.getHeight(); j += largoY){
                    terminoY = j+largoY;
                    for(int k = i; k < terminoX; k++){
                        if(k >= img.getWidth())
                            break;
                        for(int l = j; l < terminoY; l++){
                            if(l >= img.getHeight())
                                break;
                            Color original = pixelI.getColor(k, l);
                            rojoRGB += original.getRed();
                            verdeRGB += original.getGreen();
                            azulRGB += original.getBlue();
                            promedio++;
                        }
                    }
                    r = (rojoRGB/promedio);
                    g = (verdeRGB/promedio);
                    b = (azulRGB/promedio);
                    rojoRGB = verdeRGB = azulRGB = promedio = 0;
                    imagenTemp = buscarImagen(this.bibliotecaImagenes, r, g, b, metodo);
                    texto = "<td><img src=\""+imagenTemp+"\" width=\"10\", height=\"10\"></td>\n";
                    lTemp.add(texto);                    
                }
                imagenes.add(lTemp);
            }
            for(int i = 0; i < imagenes.getFirst().size(); i++){
                for(LinkedList<String> lTemp : imagenes){
                    if(i >= lTemp.size())
                        break;
                    escritor.write(lTemp.get(i));
                    escritor.flush();
                }
                texto = "</tr><tr> \n";
                escritor.write(texto);
                escritor.flush();
            }
            texto = "</tr>\n"
                    + "</table></center>";
            escritor.write(texto);
            escritor.flush();
            JOptionPane.showMessageDialog(this, "Se está creando su mosaico, en cuanto termine se abrirá automáticamente en su navegador.", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
            Desktop.getDesktop().browse(salida.toURI());
        } catch(IOException e){
            JOptionPane.showMessageDialog(this, "No se pudo crear el archivo.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    //Método auxiliar para calcular el promedio RGB de una imagen.
    public double[] calculaPromedio(File img) throws FileNotFoundException{
        Image imagen = new Image(new FileInputStream(img));
        double w = imagen.getWidth();
        double h = imagen.getHeight();
        double[] promedio = new double[3];
        double promR = 0;
        double promG = 0;
        double promB = 0;
        for(int i = 0; i < w; i++){
            for(int j = 0; j < h; j++){
                promR += imagen.getPixelReader().getColor(i, j).getRed();
                promG += imagen.getPixelReader().getColor(i, j).getGreen();
                promB += imagen.getPixelReader().getColor(i, j).getBlue();
            }
        }
        promedio[0] = promR/(w*h);
        promedio[1] = promG/(w*h);
        promedio[2] = promB/(w*h);
        return promedio;
    }
    
    /* Método auxiliar para crear el archivo donde tendremos los promedios
     * de RGB de cada imagen y su respectiva dirección.
     */
    public File calculaBiblioteca(File dir) throws IOException{
        File[] files = dir.listFiles();
        File biblioteca = new File("valoresImagenes.txt");
        biblioteca.createNewFile();
        FileWriter txt = new FileWriter(biblioteca);
        try( BufferedWriter escritor = new BufferedWriter(txt)){
            String texto;
            for(File img : files){
                double[] prom = calculaPromedio(img);
                texto = prom[0] + "#" + prom[1] + "#" + prom[2] + "#" + img.getAbsolutePath() + "\n";
                escritor.write(texto);
                escritor.flush();
            }
        }
        JOptionPane.showMessageDialog(null, "Se ha terminado de crear la biblioteca.", "COMPLETADO", JOptionPane.INFORMATION_MESSAGE);
        return biblioteca;
    }
    
    // Método auxiliar para buscar la imagen correspondiente.
    public String buscarImagen(File archivo, double r, double g, double b, String metodo) throws FileNotFoundException, IOException{
        String ruta = null;
        String linea;
        FileReader reader = new FileReader(archivo);
        BufferedReader buffer = new BufferedReader(reader);
        double dif = Double.POSITIVE_INFINITY;
        String[] parString;
        String mejorOp = "";
        while(ruta == null){
            linea = buffer.readLine();
            if(linea == null){
                ruta = mejorOp;
                continue;
            }
            parString = linea.split("#");
            double promR = Double.parseDouble(parString[0]);
            double promG = Double.parseDouble(parString[1]);
            double promB = Double.parseDouble(parString[2]);
            double raiz = (metodo.equals("Euclidiana")) ? distEu(r,promR, g,promG, b,promB) : distRi(r,promR, g,promG, b,promB);
            if(raiz < dif){
                dif = raiz;
                mejorOp = parString[parString.length-1];
            }
        }
        return ruta;
    }
    
    //Método auxiliar para calcular la distancia Euclidiana.
    public Double distEu(double r1, double r2, double g1, double g2, double b1, double b2){
        double delta = Math.sqrt(Math.pow(r1-r2, 2) + Math.pow(g1-g2, 2) + Math.pow(b1-b2, 2));
        return delta;
    }
    
    //Método auxiliar para calcular la distancia Riemersma.
    public Double distRi(double r1, double r2, double g1, double g2, double b1, double b2){
        double f = (r1+r2)/2;
        double dR = r1-r2;
        double dG = g1-g2;
        double dB = b1-b2;
        double delta = Math.sqrt((2+(f/256))*(Math.pow(dR, 2)) + (4*Math.pow(dG, 2)) + (2+((255-f)/256))*(Math.pow(dB, 2)));
        return delta;
    }
    
    //Método auxiliar para pedir la cantidad de pixeles.
    public int numMosaicos(){
        int num;
        String pixeles = JOptionPane.showInputDialog("Ingrese el tamaño de mosaico en pixeles:\n"
                    + "Mínimo: 10\n"
                    + "Máximo: " + this.imagen.getWidth());
            num = Integer.parseInt(pixeles);
            while(num < 10 || num > this.imagen.getWidth()/10){
                JOptionPane.showMessageDialog(this, "Por favor seleccione una opción valida", "ERROR", JOptionPane.ERROR_MESSAGE);
                num = numMosaicos();
                break;
            }
            return num;
    }
}
