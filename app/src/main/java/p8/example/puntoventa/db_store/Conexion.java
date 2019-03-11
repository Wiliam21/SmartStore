package p8.example.puntoventa.db_store;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexion extends SQLiteOpenHelper {

    //Creo las tablas de la base de datos

    final String CREAR_TABLA_PROVEEDORES="create table Proveedor(" +
            "ID_Proveedor int primary key autoincrement ," +
            "Nombre_Proveedor text," +
            "telefono text" +
            ");";
    final String CREAR_TABLA_PRODUCTOS="create table Productos(" +
            "ID_Producto int primary key," +
            "Nombre_Producto text," +
            "Costo_Venta double," +
            "Costo_Compra double," +
            "Existencia int," +
            "Veces_Vendido int," +
            "ID_Proveedor int," +
            "foreign key (ID_Proveedor) references Proveedor(ID_Proveedor)" +
            ");";
    final String CREAR_TABLA_REPORTES="create table Reporte(" +
            "ID_Reporte int primary key," +
            "Total double," +
            "Ganancia double," +
            "Fecha date" +
            ");";


    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Ejecuta las sentencias para crear las tablas
        db.execSQL(CREAR_TABLA_PROVEEDORES);
        db.execSQL(CREAR_TABLA_PRODUCTOS);
        db.execSQL(CREAR_TABLA_REPORTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //En caso de que haya una nueva version o ya existan las tablas, eliminara estas
        db.execSQL("DROP TABLE IF EXISTS Proveedores");
        db.execSQL("DROP TABLE IF EXISTS Productos");
        db.execSQL("DROP TABLE IF EXISTS Reportes");
        onCreate(db);
    }
}
