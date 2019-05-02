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
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                editItem.setBackground(new ColorDrawable(Color.rgb(0, 176,
                        255)));
                // set item width
                editItem.setWidth(150);
                // set a icon
                editItem .setIcon(R.drawable.ic_edit);
                // add to menu
                menu.addMenuItem(editItem);
            }
        };
        Lista.setMenuCreator(creator);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inventario.this,AltaProductos.class));
            }
        });

        Lista.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        String ID_Producto=ListaProductos.get(position).getID_Producto();
                        startActivity(new Intent(Inventario.this,EditarProducto.class).putExtra("ID_PRODUCTO",ID_Producto));
                        break;
                }

                return false;
            }
        });
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
