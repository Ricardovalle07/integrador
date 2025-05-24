public class Cliente {
    private int numero; String nombre;
    public Cliente(int num, String nom, String dir, String mail, String cel){
       numero=num;
       nombre=nom;
    }
    public void setNumero(int num){
        numero=num;
    }
    public void setNombre(String nom){
        nombre=nom;
    }
    public int getNumero(){
        return numero;
    }
    public String getNombre(){
        return nombre;
    }
    public String toString(){
        return "Numero: "+numero+" Nombre: "+nombre;
    }
}
