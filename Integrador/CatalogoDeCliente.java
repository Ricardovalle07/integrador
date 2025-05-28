import java.io.*;
import java.util.*;
public class CatalogoDeCliente {
    public static void main(String[] args) throws IOException {
        File filetxt = new File("clientes.txt");
        File fileD = new File("clientes.dat");

        PrintWriter salidaT = new PrintWriter(new FileOutputStream(filetxt));
        ObjectOutputStream salidaD = new ObjectOutputStream(new FileOutputStream(fileD));

        Scanner entrada = new Scanner(System.in);

        System.out.print("Numero del cliente:");
        int numero = entrada.nextInt();
        entrada.nextLine(); 

        while (numero > 0) {
            System.out.print("Nombre del cliente:");
            String nombre = entrada.nextLine();

            Cliente cliente = new Cliente(numero, nombre);

            salidaT.println(cliente.toString());
            salidaD.writeObject(cliente);

            System.out.print("Numero del cliente:");
            numero = entrada.nextInt();
            entrada.nextLine();
        }
        salidaT.close();
        salidaD.close();

        System.out.println("Clientes guardados en clientes.txt y clientes.dat");
        entrada.close();
    }
}
