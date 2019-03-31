package p8.example.puntoventa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import p8.example.puntoventa.Utilidades.AdaptadorListaProveedores;
import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.ProveedorObjeto;

import static p8.example.puntoventa.Utilidades.Utilidades.TABLA_PROVEEDOR;

public class Proveedor extends AppCompatActivity {

    //Lista de proveedores

    ListView lstProveedores;
    ArrayList<ProveedorObjeto> arrayProveerdor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor);
        consultarProveedores();
        lstProveedores=(ListView)findViewById(R.id.lstProveedores);
        FloatingActionButton fab = findViewById(R.id.fab);
        lstProveedores.setAdapter(new AdaptadorListaProveedores(this,arrayProveerdor));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Proveedor.this,Proveedores.class);
                startActivity(i);
            }
        });
    }

    private void consultarProveedores() {
        Conexion conn=new Conexion(this,"db_SmartStore",null,1);
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
