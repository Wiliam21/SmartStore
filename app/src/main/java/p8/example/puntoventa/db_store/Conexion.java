package p8.example.puntoventa.db_store;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import p8.example.puntoventa.Utilidades.Utilidades;

public class Conexion extends SQLiteOpenHelper {

    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Ejecuta las sentencias para crear las tablas
        db.execSQL(Utilidades.CREAR_TABLA_PROVEEDOR);
        db.execSQL(Utilidades.CREAR_TABLA_PRODUCTO);
        db.execSQL(Utilidades.CREAR_TABLA_REPORTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //En caso de que haya una nueva version o ya existan las tablas, eliminara estas
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_PROVEEDOR+";");
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_PRODUCTO+";");
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_REPORTE +";");
        onCreate(db);
    }
}
