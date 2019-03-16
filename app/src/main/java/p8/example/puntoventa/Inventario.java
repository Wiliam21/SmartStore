package p8.example.puntoventa;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.sql.Array;
import java.util.ArrayList;

import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.Productos;

public class Inventario extends AppCompatActivity {
    Conexion conexion=new Conexion(this, Utilidades.DATABASE,null,1);
    ArrayList<Productos>ListaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

    }

    public void CargarProductos(){
        String[] Campos={Utilidades.CAMPO_ID_PRODUCTO,Utilidades.CAMPO_NOMBRE_PRODUCTO};
        SQLiteDatabase db=conexion.getReadableDatabase();
        Cursor cursor= db.query(Utilidades.TABLA_PRODUCTO,Campos,null,null,null,null,Utilidades.CAMPO_NOMBRE_PRODUCTO+" asc");
        while(cursor.moveToNext()){
            Productos producto=new Productos();
            producto.setID_Producto(cursor.getString(0));
            producto.setNombre_Producto(cursor.getString(1));
            ListaProductos.add(producto);
        }
        db.close();
    }
}
