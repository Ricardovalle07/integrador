import java.io.Serializable;

public class Ventas implements Serializable {
    private int cantidad;
    private Articulo articulo; 
    private Cliente cliente;
    public Ventas(int cant, Cliente cliente, Articulo art){
        cantidad = cant;
        articulo = art;
        this.cliente=cliente;
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
