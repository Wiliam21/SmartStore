package p8.example.puntoventa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
                Intent i = new Intent(Proveedor.this, AltaProveedores.class).putExtra("RETORNO","1");
                startActivity(i);
            }
        });

        lstProveedores.setMenuCreator(new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                editItem.setBackground(new ColorDrawable(Color.rgb(255, 255,
                        255)));
                // set item width
                editItem.setWidth(150);
                // set a icon
                editItem .setIcon(R.drawable.ic_edit);
                // add to menu
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem callItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                callItem .setBackground(new ColorDrawable(Color.rgb(255,
                        255, 255)));
                // set item width
                callItem .setWidth(150);
                // set a icon
                callItem .setIcon(R.drawable.ic_phone);
                // add to menu
                menu.addMenuItem(callItem );
            }
        });

        lstProveedores.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        String ID_PROVEEDOR=arrayProveerdor.get(position).getID_Proveedor().toString();
                        startActivity(new Intent(Proveedor.this,EditarProveedor.class).putExtra("ID_PROVEEDOR",ID_PROVEEDOR));
                        break;
                    case 1:
                        try {
                            String TELEFONO=arrayProveerdor.get(position).getTelefono();
                            if (ActivityCompat.checkSelfPermission(Proveedor.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(Proveedor.this,new String[]{Manifest.permission.CALL_PHONE},1);
                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+TELEFONO)));
                            }else {
                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+TELEFONO)));
                            }

                        }catch (Exception e){
                            Log.e("LLAMADA",e.getMessage());
                        }
                        break;
                }
                return false;
            }
        });

        lstProveedores.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
        //lstProveedores.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
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
