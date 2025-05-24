public class Articulo {
    private int codigo, existencia; String desc; double precio;
    public Articulo(int cod, int exis, String des, double pri){
        codigo=cod;
        existencia=exis;
        desc=des;
        precio=pri;
    }
    public void setCodigo(int cod){
        codigo=cod;
    }
    public void setExistencia(int exis){
        existencia=exis;
    }
    public void setDesc(String des){
        desc=des;
    }
    public void setPrecio(double pri){
        precio=pri;
    }
    public int getCodigo(){
        return codigo;
    }
    public int getExistencia(){
        return existencia;
    }
    public String getDesc(){
        return desc;
    }
    public double getPrecio(){
        return precio;
    }
    public double valorInventario(){
        return precio*existencia;
    }
    public String toString(){
        return "Codigo: "+codigo+" Descripcion: "+desc+" Precio: $"+precio+" Existencia: "+existencia;
    }
}
