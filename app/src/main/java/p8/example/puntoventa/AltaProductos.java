package p8.example.puntoventa;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.ProveedorObjeto;

public class AltaProductos extends AppCompatActivity {
    private EditText txtnId_Producto,txteNombre,txtnCantidad,txtnCompra,txtnVenta;
    private Button btnAlta,btnOtro;
    private ImageButton imgbScan;
    Spinner comboProveedores;


    ArrayList<ProveedorObjeto> arrayProveerdor;
    ArrayList<String> ListaProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_producto);

        txtnId_Producto=(EditText)findViewById(R.id.txtnId_Producto);
        txteNombre=(EditText)findViewById(R.id.txteNombre);
        txtnCantidad=(EditText)findViewById(R.id.txtnCantidad);
        txtnVenta=(EditText)findViewById(R.id.txtnVenta);
        txtnCompra=(EditText)findViewById(R.id.txtnCompra);
        btnAlta=(Button)findViewById(R.id.btnAlta);
        imgbScan=(ImageButton)findViewById(R.id.imgbScan);
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
        Conexion conn=new Conexion(this,"db_SmartStore",null,2);
        SQLiteDatabase db=conn.getReadableDatabase();

        ProveedorObjeto proveedor=null;
        arrayProveerdor=new ArrayList<ProveedorObjeto>();

        Cursor cursor=db.rawQuery("SELECT*FROM "+Utilidades.TABLA_PROVEEDOR+"",null);

        while(cursor.moveToNext()){
            proveedor=new ProveedorObjeto();
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

    public void AltaProducto(View view){
        try {
            Alta();
        }catch (Exception e){
            Log.e("ALTA PROVEEDOR",e.getMessage());
        }
    }

    private void Alta() {
        Conexion conn=new Conexion(this,"db_SmartStore",null,2);
        SQLiteDatabase db=conn.getWritableDatabase();
        int ID_Proveedor;
        String Nombre=txteNombre.getText().toString();
        String Id_Producto=txtnId_Producto.getText().toString();
        double CostoCompra=Double.parseDouble(txtnCompra.getText().toString());
        double CostoVenta=Double.parseDouble(txtnVenta.getText().toString());
        int Cantidad= Integer.parseInt(txtnCantidad.getText().toString());
        int idSpinner=(int) comboProveedores.getSelectedItemPosition();
        Log.i("Id", "AltaProducto: "+Id_Producto);
        if (idSpinner>0){
            ID_Proveedor=arrayProveerdor.get(idSpinner-1).getID_Proveedor();
            ContentValues Producto=new ContentValues();
            Producto.put(Utilidades.CAMPO_ID_PRODUCTO,Id_Producto);
            Producto.put(Utilidades.CAMPO_NOMBRE_PRODUCTO,Nombre);
            Producto.put(Utilidades.CAMPO_COSTO_VENTA,CostoVenta);
            Producto.put(Utilidades.CAMPO_COSTO_COMPRA,CostoCompra);
            Producto.put(Utilidades.CAMPO_EXISTENCIA_PRODUCTO,Cantidad);
            Producto.put(Utilidades.CAMPO_VECES_VENDIDO,0);
            Producto.put(Utilidades.CAMPO_ID_PROVEEDOR_PRODUCTO,ID_Proveedor);
            long id=db.insert(Utilidades.TABLA_PRODUCTO,null,Producto);
            Log.e("ID",""+id);
            if (id==-1){
                Toast.makeText(this,"El producto ya existe",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"Producto agregado",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getBaseContext(),Inventario.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        }
        else {
            Toast.makeText(this,"No selecciono un proveedor",Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    public void Escanear(View view){
        IntentIntegrator intent =new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setPrompt("Escanea el codigo de barras");
        intent.initiateScan();
        intent.setBarcodeImageEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result =IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            txtnId_Producto.setText(result.getContents());
        }
        else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }


}
