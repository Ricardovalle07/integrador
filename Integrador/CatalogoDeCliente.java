import java.io.*;
import java.util.*;

public class CatalogoDeCliente {
    public static void main(String[] args) throws IOException {
        File archivoTexto = new File("clientes.txt");
        File archivoBinario = new File("clientes.dat");

        PrintWriter salidaTexto = new PrintWriter(new FileOutputStream(archivoTexto));
        ObjectOutputStream salidaBinario = new ObjectOutputStream(new FileOutputStream(archivoBinario));

        Scanner entrada = new Scanner(System.in);

        System.out.print("Numero del cliente (0 para terminar):");
        int numero = entrada.nextInt();
        entrada.nextLine(); 

        while (numero > 0) {
            System.out.print("Nombre del cliente:");
            String nombre = entrada.nextLine();

            Cliente cliente = new Cliente(numero, nombre);

            salidaTexto.println(cliente.getNumero() + "\t" + cliente.getNombre());
            salidaBinario.writeObject(cliente);

            System.out.print("Numero del cliente (0 para terminar):");
            numero = entrada.nextInt();
            entrada.nextLine();
        }
        salidaTexto.close();
        salidaBinario.close();

        System.out.println("Clientes guardados en clientes.txt y clientes.dat");
        entrada.close();
    }
}
