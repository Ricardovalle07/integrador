import java.io.*;
import java.util.*;

public class VentasAPP {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner entrada = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n Menú de Ventas ");
            System.out.println("1. Registrar una venta");          
            System.out.println("2. Reporte por cliente");          
            System.out.println("3. Reporte por artículo");         
            System.out.println("4. Artículos con baja existencia"); 
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {
                case 1: 
                    class MiObjectOutputStream extends ObjectOutputStream {
                        public MiObjectOutputStream(OutputStream out) throws IOException { super(out); }
                        protected void writeStreamHeader() throws IOException {}
                    }

                    File ventasFile = new File("ventas.dat");
                    ObjectOutputStream salidaVentas;
                    if (ventasFile.exists() && ventasFile.length() > 0) {
                        salidaVentas = new MiObjectOutputStream(new FileOutputStream(ventasFile, true));
                    } else {
                        salidaVentas = new ObjectOutputStream(new FileOutputStream(ventasFile));
                    }

                    System.out.print("Número del cliente (0 para terminar): ");
                    int numCliente = entrada.nextInt();

                    while (numCliente > 0) {
                        Cliente cliente = buscarCliente(numCliente); 
                        if (cliente == null) {
                            System.out.println("Cliente no encontrado");
                        } else {
                            System.out.print("Código del artículo: ");
                            int codigo = entrada.nextInt();
                            Articulo articulo = buscarArticulo(codigo);

                            if (articulo == null) {
                                System.out.println("Artículo no encontrado");
                            } else {
                                System.out.print("Cantidad: ");
                                int cantidad = entrada.nextInt();

                                Ventas venta = new Ventas(cantidad, cliente, articulo);
                                salidaVentas.writeObject(venta);
                                System.out.println("Venta registrada: " + venta);
                            }
                        }
                        System.out.print("Número del cliente (0 para terminar): ");
                        numCliente = entrada.nextInt();
                    }

                    salidaVentas.close();
                    System.out.println("Ventas guardadas en ventas.dat");
                    break;

                case 2: 
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
                    break;

                case 3: //REPORTE DE CLIENTE 
                    System.out.print("Código del artículo: ");
                    int codigo = entrada.nextInt();

                    int totalUnidades = 0;
                    boolean ventaEncontrada = false;

                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ventas.dat"))) {
                        System.out.println("\nArtículo: " + codigo);
                        System.out.println("Cliente\tCantidad");

                        while (true) {
                            try {
                                Ventas venta = (Ventas) ois.readObject();
                                if (venta.getArticulo().getCodigo() == codigo) {
                                    System.out.println(venta.getCliente().getNombre() + "\t" + venta.getCantidad());
                                    totalUnidades += venta.getCantidad();
                                    ventaEncontrada = true;
                                }
                            } catch (EOFException e) {
                                break;
                            }
                        }
                    }

                    if (ventaEncontrada) {
                        System.out.println("Total de unidades vendidas: " + totalUnidades);
                    } else {
                        System.out.println("No se encontraron ventas para este artículo.");
                    }
                    break;

                case 4: //BAJA EXISTENCIA 
                    PrintWriter salida = new PrintWriter(new FileWriter("BajaExistencia.txt"));
                    boolean articuloEncontrado = false;

                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("articulos.dat"))) {
                        System.out.println("Artículos con existencia menor a 10:");
                        while (true) {
                            try {
                                Articulo art = (Articulo) ois.readObject();
                                if (art.getExistencia() < 10) {
                                    System.out.println(art); // usa toString() implícito
                                    salida.println(art.toString()); //uso explícito
                                    salida.println("---------------------------");
                                    articuloEncontrado = true;
                                }
                            } catch (EOFException e) {
                                break;
                            }
                        }
                    }

                    salida.close();
                    if (!articuloEncontrado) {
                        System.out.println("No hay artículos con baja existencia.");
                    } else {
                        System.out.println("Guardado en BajaExistencia.txt");
                    }
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

    //NO JALA 
    // Ya jala, era error en catalago de clientes lol
    public static Cliente buscarCliente(int numero) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("clientes.dat"))) {
            while (true) {
                Cliente c = (Cliente) ois.readObject();
                if (c.getNumero() == numero) return c;
            }
        } catch (EOFException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Error al leer clientes.dat");
            return null;
        }
    }

    public static Articulo buscarArticulo(int codigo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("articulos.dat"))) {
            while (true) {
                Articulo a = (Articulo) ois.readObject();
                if (a.getCodigo() == codigo) return a;
            }
        } catch (EOFException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Error al leer articulos.dat");
            return null;
        }
    }
}