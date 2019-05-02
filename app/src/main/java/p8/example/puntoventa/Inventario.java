package p8.example.puntoventa;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.sql.Array;
import java.util.ArrayList;

import p8.example.puntoventa.Utilidades.AdaptadorInventario;
import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.Productos;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

public class Inventario extends AppCompatActivity {
    Conexion conexion=new Conexion(this, Utilidades.DATABASE,null,2);
    ArrayList<Productos>ListaProductos;
    FloatingActionButton fab;
    SwipeMenuListView Lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        fab = (FloatingActionButton) findViewById(R.id.fbtnAdd);
        Lista=(SwipeMenuListView) findViewById(R.id.listLista);
        CargarProductos();
        Lista.setAdapter(new AdaptadorInventario(this,ListaProductos));

        SwipeMenuCreator creator =new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(100);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth((90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_edit);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        Lista.setMenuCreator(creator);

    }

    public void CargarProductos(){
        SQLiteDatabase db=conexion.getReadableDatabase();
        ListaProductos=new ArrayList<Productos>();
        Productos producto=null;
        Cursor cursor= db.query(Utilidades.TABLA_PRODUCTO,null,null,null,null,null,Utilidades.CAMPO_NOMBRE_PRODUCTO+" asc");
        while(cursor.moveToNext()){
            producto=new Productos();
            producto.setID_Producto(cursor.getString(0));
            producto.setNombre_Producto(cursor.getString(1));
            producto.setCosto_Venta(cursor.getDouble(2));
            producto.setCosto_Compra(cursor.getDouble(3));
            producto.setExistencia(cursor.getInt(4));
            producto.setVeces_Vendido(cursor.getInt(5));
            producto.setID_Proveedor(cursor.getInt(6));
            ListaProductos.add(producto);
        }
        db.close();
    }
}
