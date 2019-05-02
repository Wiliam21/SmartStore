package p8.example.puntoventa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import p8.example.puntoventa.Utilidades.AdaptadorListaProveedores;
import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.ProveedorObjeto;

import static p8.example.puntoventa.Utilidades.Utilidades.TABLA_PROVEEDOR;

public class Proveedor extends AppCompatActivity {

    //Lista de proveedores

    SwipeMenuListView lstProveedores;
    ArrayList<ProveedorObjeto> arrayProveerdor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor);
        consultarProveedores();
        lstProveedores=(SwipeMenuListView) findViewById(R.id.lstProveedores);
        FloatingActionButton fab = findViewById(R.id.fab);
        lstProveedores.setAdapter(new AdaptadorListaProveedores(this,arrayProveerdor));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Proveedor.this, AltaProveedores.class);
                startActivity(i);
            }
        });

        lstProveedores.setMenuCreator(new SwipeMenuCreator() {
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
        });
    }

    private void consultarProveedores() {
        Conexion conn=new Conexion(this,"db_SmartStore",null,2);
        SQLiteDatabase db=conn.getReadableDatabase();

        ProveedorObjeto proveedor=null;
        arrayProveerdor=new ArrayList<ProveedorObjeto>();

        Cursor cursor=db.query(TABLA_PROVEEDOR,null,null,null,null,null,Utilidades.CAMPO_NOMBRE_PROVEEDOR+" asc");

        while(cursor.moveToNext()){
            proveedor=new ProveedorObjeto();
            proveedor.setID_Proveedor(cursor.getInt(0));
            proveedor.setNombre_Proveedor(cursor.getString(1));
            proveedor.setTelefono(cursor.getString(2));

            arrayProveerdor.add(proveedor);
        }
        db.close();
    }
}
