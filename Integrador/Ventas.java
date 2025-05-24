public class Ventas extends Cliente{
    private int cantidad;
    private Articulo articulo; 
    private Cliente cliente;
    public Ventas(int cant, int num, String nom, Articulo art, Cliente cli){
        super(num, nom);
        cantidad = cant;
        articulo = art;
        cliente = cli;
    }
    public void setCantidad(int cant){
        cantidad=cant;
    }
    public void setArticulo(Articulo art){
        articulo=art;
    }
    public void setCliente(Cliente cli){
        cliente=cli;
    }
    public int getCantidad(){
        return cantidad;
    }
    public Articulo getArticulo(){
        return articulo;
    }
    public Cliente getCliente(){
        return cliente;
    }
    public String toString(){
        return "Cliente: "+cliente+" Articulo: "+articulo+" Cantidad: "+cantidad;
    }

}
