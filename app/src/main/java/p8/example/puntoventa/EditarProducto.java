package p8.example.puntoventa;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.Proveedor;

public class EditarProducto extends AppCompatActivity {
    private EditText txteNombre,txtnCantidad,txtnCompra,txtnVenta;
    private Button btnAlta,btnEliminar;
    Spinner comboProveedores;


    ArrayList<Proveedor> arrayProveerdor;
    ArrayList<String> ListaProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);
        txteNombre=(EditText)findViewById(R.id.txteNombre);
        txtnCantidad=(EditText)findViewById(R.id.txtnCantidad);
        txtnVenta=(EditText)findViewById(R.id.txtnVenta);
        txtnCompra=(EditText)findViewById(R.id.txtnCompra);
        btnAlta=(Button)findViewById(R.id.btnAlta);
        comboProveedores =(Spinner)findViewById(R.id.spinner_proveedores);

        consultarProveedores();
        ArrayAdapter<CharSequence> adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,ListaProveedor);
        comboProveedores.setAdapter(adapter);
        comboProveedores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void consultarProveedores() {
        Conexion conn=new Conexion(this,"db_SmartStore",null,1);
        SQLiteDatabase db=conn.getReadableDatabase();

        Proveedor proveedor=null;
        arrayProveerdor=new ArrayList<Proveedor>();

        Cursor cursor=db.rawQuery("SELECT*FROM "+ Utilidades.TABLA_PROVEEDOR+"",null);

        while(cursor.moveToNext()){
            proveedor=new Proveedor();
            proveedor.setID_Proveedor(cursor.getInt(0));
            proveedor.setNombre_Proveedor(cursor.getString(1));
            proveedor.setTelefono(cursor.getString(2));

            arrayProveerdor.add(proveedor);
        }
        ObtenerLista();
        db.close();
    }

    private void ObtenerLista() {
        ListaProveedor=new ArrayList<>();
        ListaProveedor.add("Seleccione un proveedor");

        for (int i=0;i<arrayProveerdor.size();i++){
            ListaProveedor.add(arrayProveerdor.get(i).getNombre_Proveedor());
        }
    }
}
