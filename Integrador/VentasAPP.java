import java.io.*;
import java.util.*;

public class VentasAPP {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner entrada = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú de Ventas ---");
            System.out.println("1. Registrar una venta");        //Inciso b
            System.out.println("2. Reporte por cliente");        //Inciso c
            System.out.println("3. Reporte por artículo");       //Inciso d
            System.out.println("4. Artículos con baja existencia"); //Inciso e
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {
                case 1:
                    registrarVenta(entrada);                   //Inciso b
                    break;
                case 2:
                    reportePorCliente(entrada);                //Inciso c
                    break;
                case 3:
                    reportePorArticulo(entrada);               //Inciso d
                    break;
                case 4:
                    bajaExistencia();                          //Inciso e
                    break;
                case 0:
                    System.out.println("Fin del programa.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        entrada.close();
    }

    //Inciso b Registrar ventas
    public static void registrarVenta(Scanner entrada) throws IOException {
        ObjectOutputStream salidaVentas = new ObjectOutputStream(new FileOutputStream("ventas.dat", true)) {
            protected void writeStreamHeader() throws IOException {}
        };

        System.out.print("Número del cliente (0 para terminar): ");
        int numCliente = entrada.nextInt();

        while (numCliente > 0) {
            Cliente cliente = buscarCliente(numCliente); //Inciso a Buscar en clientes.dat
            if (cliente == null) {
                System.out.println("Cliente no encontrado");
            } else {
                System.out.print("Código del artículo: ");
                int codigo = entrada.nextInt();
                Articulo articulo = buscarArticulo(codigo); //Inciso a Buscar en articulos.dat

                if (articulo == null) {
                    System.out.println("Artículo no encontrado");
                } else {
                    System.out.print("Cantidad: ");
                    int cantidad = entrada.nextInt();

                    Ventas venta = new Ventas(cantidad, cliente.getNumero(), cliente.getNombre(), articulo, cliente);
                    salidaVentas.writeObject(venta);
                    System.out.println("Venta registrada: " + venta);
                }
            }
            System.out.print("Número del cliente (0 para terminar): ");
            numCliente = entrada.nextInt();
        }

        salidaVentas.close();
        System.out.println("Ventas guardadas en ventas.dat");
    }

    //Inciso c Reporte por cliente
    public static void reportePorCliente(Scanner entrada) throws IOException, ClassNotFoundException {
        System.out.print("Nombre del cliente: ");
        String nombreCliente = entrada.nextLine();
        double total = 0;
        boolean encontrado = false;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ventas.dat"))) {
            System.out.println("\nCliente: " + nombreCliente);
            System.out.println("Descripción\tPrecio\tCantidad\tMonto");

            while (true) {
                try {
                    Ventas venta = (Ventas) ois.readObject();
                    if (venta.getCliente().getNombre().equalsIgnoreCase(nombreCliente)) {
                        Articulo art = venta.getArticulo();
                        int cant = venta.getCantidad();
                        double monto = art.getPrecio() * cant;
                        System.out.printf("%s\t%.2f\t%d\t\t%.2f\n", art.getDesc(), art.getPrecio(), cant, monto);
                        total += monto;
                        encontrado = true;
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        }

        if (encontrado) {
            System.out.printf("\nTotal comprado: %.2f\n", total);
        } else {
            System.out.println("No se encontraron ventas para este cliente.");
        }
    }

    //Inciso d Reporte por artículo
    public static void reportePorArticulo(Scanner entrada) throws IOException, ClassNotFoundException {
        System.out.print("Código del artículo: ");
        int codigo = entrada.nextInt();

        int totalUnidades = 0;
        boolean encontrado = false;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ventas.dat"))) {
            System.out.println("\nArtículo: " + codigo);
            System.out.println("Cliente\tCantidad");

            while (true) {
                try {
                    Ventas venta = (Ventas) ois.readObject();
                    if (venta.getArticulo().getCodigo() == codigo) {
                        System.out.println(venta.getCliente().getNombre() + "\t" + venta.getCantidad());
                        totalUnidades += venta.getCantidad();
                        encontrado = true;
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        }

        if (encontrado) {
            System.out.println("Total de unidades vendidas: " + totalUnidades);
        } else {
            System.out.println("No se encontraron ventas para este artículo.");
        }
    }

    //Inciso e Reporte de baja existencia
    public static void bajaExistencia() throws IOException, ClassNotFoundException {
    PrintWriter salida = new PrintWriter(new FileWriter("BajaExistencia.txt"));
    boolean encontrado = false;

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("articulos.dat"))) {
        System.out.println("Artículos con existencia menor a 10:");
        while (true) {
            try {
                Articulo art = (Articulo) ois.readObject();
                if (art.getExistencia() < 10) {
                    System.out.println(art); //
                    salida.println(art.toString());  
                    salida.println("---------------------------");
                    encontrado = true;
                }
            } catch (EOFException e) {
                break;
            }
        }
    }

    salida.close();
    if (!encontrado) {
        System.out.println("No hay artículos con baja existencia.");
    } else {
        System.out.println("Guardado en BajaExistencia.txt");
    }
}

    //Inciso a Lectura secuencial de Cliente desde archivo binario
    public static Cliente buscarCliente(int numero) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("clientes.dat"))) {
            while (true) {
                Cliente c = (Cliente) ois.readObject();
                if (c.getNumero() == numero) {
                    return c;
                }
            }
        } catch (EOFException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Error al leer clientes.dat");
            return null;
        }
    }

    //Inciso a: Lectura secuencial de Articulo desde archivo binario
    public static Articulo buscarArticulo(int codigo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("articulos.dat"))) {
            while (true) {
                Articulo a = (Articulo) ois.readObject();
                if (a.getCodigo() == codigo) {
                    return a;
                }
            }
        } catch (EOFException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Error al leer articulos.dat");
            return null;
        }
    }
}
