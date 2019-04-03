package p8.example.puntoventa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import java.util.ArrayList;

import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.Productos;
import p8.example.puntoventa.db_store.ProveedorObjeto;

public class EditarProducto extends AppCompatActivity {
    private EditText txteNombre,txtnCantidad,txtnCompra,txtnVenta;
    private Button btnGuardar,btnEliminar;
    Spinner spnProveedores;
    Conexion conn=new Conexion(this,"db_SmartStore",null,1);

    ArrayList<ProveedorObjeto> arrayProveerdor;
    ArrayList<String> ListaProveedor;
    String ID_PRODUCTO;
    Productos producto=new Productos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);
        txteNombre=(EditText)findViewById(R.id.txteNombreEdit);
        txtnCantidad=(EditText)findViewById(R.id.txtnCantidadEdit);
        txtnVenta=(EditText)findViewById(R.id.txtnVentaEdit);
        txtnCompra=(EditText)findViewById(R.id.txtnCompraEdit);
        btnGuardar=(Button)findViewById(R.id.btnGuardar);
        spnProveedores =(Spinner)findViewById(R.id.spnProveedroresEdit);

        Intent intent=getIntent();
        ID_PRODUCTO=intent.getStringExtra("ID_PRODUCTOS");
        Log.w("ID", "Id recibido: "+ID_PRODUCTO);

        consultarProveedores();
        ArrayAdapter<CharSequence> adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,ListaProveedor);
        spnProveedores.setAdapter(adapter);
        spnProveedores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ObtenerValores();
    }

    public void ObtenerValores(){
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor=db.query(Utilidades.TABLA_PRODUCTO, null, Utilidades.CAMPO_ID_PRODUCTO+"= ?", new String[]{ID_PRODUCTO}, null, null, null);
        cursor.moveToFirst();
        producto.setID_Producto(ID_PRODUCTO);
        producto.setNombre_Producto(cursor.getString(1));
        producto.setCosto_Venta(cursor.getDouble(2));
        producto.setCosto_Compra(cursor.getDouble(3));
        producto.setExistencia(cursor.getInt(4));
        producto.setID_Proveedor(cursor.getInt(6));
        txtnCantidad.setText(producto.getExistencia().toString());
        txtnCompra.setText(producto.getCosto_Compra().toString());
        txteNombre.setText(producto.getNombre_Producto());
        txtnVenta.setText(producto.getCosto_Venta().toString());

        for (int i=0;i<arrayProveerdor.size();i++){
            if (arrayProveerdor.get(i).getID_Proveedor().equals(producto.getID_Proveedor())){
                spnProveedores.setSelection(i+1);
            }
        }
    }

    private void consultarProveedores() {
        SQLiteDatabase db=conn.getReadableDatabase();

        ProveedorObjeto proveedor=null;
        arrayProveerdor=new ArrayList<ProveedorObjeto>();

        Cursor cursor=db.rawQuery("SELECT*FROM "+ Utilidades.TABLA_PROVEEDOR+"",null);

        while(cursor.moveToNext()){
            proveedor = new ProveedorObjeto();
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
