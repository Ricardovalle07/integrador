import java.io.*;
import javax.swing.*;

public class CatalogoDeArticulos {
    public static void main(String[]args) throws IOException, FileNotFoundException{
        File filetxt = new File("articulos.txt");
        FileOutputStream ft= new FileOutputStream(filetxt);
        PrintWriter salidaT = new PrintWriter(ft);

        File fileD = new File("articulos.dat");
        FileOutputStream fd= new FileOutputStream(fileD);
        ObjectOutputStream salidaD= new ObjectOutputStream(fd);

        Articulo codigo;
        int code, existencia;
        String desc;
        double precio;


        code=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el codigo del articulo:"));     
        codigo=new Articulo(code, 0, "", 0.0);
        while(code>0){
            desc=JOptionPane.showInputDialog("Ingrese la descripcion del articulo:");
            existencia=Integer.parseInt(JOptionPane.showInputDialog("Ingrese la existencia del articulo:"));
            precio=Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del articulo:"));
        
            codigo.setCodigo(code);
            codigo.setDesc(desc);
            codigo.setExistencia(existencia);
            codigo.setPrecio(precio);
            Articulo articulo = new Articulo(code, existencia, desc, precio);
            
            salidaT.println(articulo.toString());
            salidaT.println("--------------------------------");

            salidaD.writeObject(articulo);


            code=Integer.parseInt(JOptionPane.showInputDialog("Ingrese el codigo del articulo:"));
        }
        salidaT.close();
        salidaD.close();
        System.out.println("Articulos guardados en articulos.txt y articulos.dat");
        
    }
}
