package p8.example.puntoventa.Utilidades;

public class Utilidades {
    public static final String TABLA_PROVEEDOR="Proveedor";
    public static final String CAMPO_ID_PROVEEDOR="ID_Proveedor";
    public static final String CAMPO_NOMBRE_PROVEEDOR="Nombre_Proveedor";
    public static final String CAMPO_TELEFONO_PROVEEDOR="Telefono_Proveedor";

    public static final String CREAR_TABLA_PROVEEDOR=
            "CREATE TABLE IF NOT EXISTS "+TABLA_PROVEEDOR+"("
            +CAMPO_ID_PROVEEDOR+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +CAMPO_NOMBRE_PROVEEDOR+" TEXT,"
            +CAMPO_TELEFONO_PROVEEDOR+" TEXT)";

    public static final String TABLA_PRODUCTO="Proveedor";
    public static final String CAMPO_ID_PRODUCTO="ID_Producto";
    public static final String CAMPO_NOMBRE_PRODUCTO="Nombre_Producto";
    public static final String CAMPO_COSTO_VENTA="Costo_Venta";
    public static final String CAMPO_COSTO_COMPRA="Costo_Compra";
    public static final String CAMPO_EXISTENCIA_PRODUCTO="Existencia_Producto";
    public static final String CAMPO_VECES_VENDIDO="Veces_Vendido";
    public static final String CAMPO_ID_PROVEEDOR_PRODUCTO="ID_Proveedor";

    public static final String CREAR_TABLA_PRODUCTO=
            "CREATE TABLE IF NOT EXISTS "+TABLA_PRODUCTO+"(" +CAMPO_ID_PRODUCTO+" varchar(25) primary key," +
            CAMPO_NOMBRE_PRODUCTO+" TEXT," +
            CAMPO_COSTO_VENTA+" double," +
            CAMPO_COSTO_COMPRA+" double," +
            CAMPO_EXISTENCIA_PRODUCTO+" INTEGER," +
            CAMPO_VECES_VENDIDO+" integer," +
            CAMPO_ID_PROVEEDOR_PRODUCTO+" INTEGER" +
                    "," +
            "foreign key ("+CAMPO_ID_PROVEEDOR_PRODUCTO+") references "+TABLA_PROVEEDOR+"("+CAMPO_ID_PROVEEDOR+"))";

    public static final String TABLA_REPORTE="Reporte";
    public static final String CAMPO_ID_REPORTE="ID_Reporte";
    public static final String CAMPO_TOTAL_REPORTE="Total";
    public static final String CAMPO_GANANCIA_REPORTE="Ganancia";
    public static final String CAMPO_FECHA_REPORTE="Fecha";

    public static final String CREAR_TABLA_REPORTE= "CREATE TABLE IF NOT EXISTS "+TABLA_REPORTE+"("
            +CAMPO_ID_REPORTE+" integer primary key,"
            +CAMPO_TOTAL_REPORTE+" double,"
            +CAMPO_GANANCIA_REPORTE+" Ganancia double,"
            +CAMPO_FECHA_REPORTE+" date)";
}
