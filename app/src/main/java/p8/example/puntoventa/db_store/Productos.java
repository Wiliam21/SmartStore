package p8.example.puntoventa.db_store;
//Clase Producto con sus atributos, constructor, getters y sets
public class Productos {
    private Integer ID_Producto;
    private String Nombre_Producto;
    private Double Costo_Compra;
    private Double Costo_Venta;
    private Integer Existencia;
    private Integer Veces_Vendido;
    private Integer ID_Proveedor;

    public Productos(Integer ID_Producto, String nombre_Producto, Double costo_Compra, Double costo_Venta, Integer existencia, Integer veces_Vendido, Integer ID_Proveedor) {
        this.ID_Producto = ID_Producto;
        this.Nombre_Producto = nombre_Producto;
        this.Costo_Compra = costo_Compra;
        this.Costo_Venta = costo_Venta;
        this.Existencia = existencia;
        this.Veces_Vendido = veces_Vendido;
        this.ID_Proveedor = ID_Proveedor;
    }

    public Integer getID_Producto() {
        return ID_Producto;
    }

    public String getNombre_Producto() {
        return Nombre_Producto;
    }

    public Double getCosto_Compra() {
        return Costo_Compra;
    }

    public Double getCosto_Venta() {
        return Costo_Venta;
    }

    public Integer getExistencia() {
        return Existencia;
    }

    public Integer getVeces_Vendido() {
        return Veces_Vendido;
    }

    public Integer getID_Proveedor() {
        return ID_Proveedor;
    }

    public void setID_Producto(Integer ID_Producto) {
        this.ID_Producto = ID_Producto;
    }

    public void setNombre_Producto(String nombre_Producto) {
        Nombre_Producto = nombre_Producto;
    }

    public void setCosto_Compra(Double costo_Compra) {
        Costo_Compra = costo_Compra;
    }

    public void setCosto_Venta(Double costo_Venta) {
        Costo_Venta = costo_Venta;
    }

    public void setExistencia(Integer existencia) {
        Existencia = existencia;
    }

    public void setVeces_Vendido(Integer veces_Vendido) {
        Veces_Vendido = veces_Vendido;
    }

    public void setID_Proveedor(Integer ID_Proveedor) {
        this.ID_Proveedor = ID_Proveedor;
    }
}
