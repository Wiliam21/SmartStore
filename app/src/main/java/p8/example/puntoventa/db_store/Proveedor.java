package p8.example.puntoventa.db_store;
//Clase Proveedor con sus atributos, constructor, getters y sets
public class Proveedor {
    private Integer ID_Proveedor;
    private String Nombre_Proveedor;
    private String Telefono;

    public Proveedor(Integer ID_Proveedor, String nombre_Proveedor, String telefono) {
        this.ID_Proveedor = ID_Proveedor;
        this.Nombre_Proveedor = nombre_Proveedor;
        this.Telefono = telefono;
    }

    public Proveedor(){

    }

    public Integer getID_Proveedor() {
        return ID_Proveedor;
    }

    public String getNombre_Proveedor() {
        return Nombre_Proveedor;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setID_Proveedor(Integer ID_Proveedor) {
        this.ID_Proveedor = ID_Proveedor;
    }

    public void setNombre_Proveedor(String nombre_Proveedor) {
        Nombre_Proveedor = nombre_Proveedor;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}
