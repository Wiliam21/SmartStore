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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import p8.example.puntoventa.Utilidades.AdaptadorInventario;
import p8.example.puntoventa.Utilidades.AdaptadorVenta;
import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.Productos;

public class VentaProductos extends AppCompatActivity {
    private EditText txteId_Producto;
    Double Total;
    String Id_Producto;
    IntentIntegrator intent =new IntentIntegrator(this);
    TextView txtNombreProducto;
    ImageButton imgbScan, imgbBuscar;
    Button btnVenta;
    ArrayList<Productos>ProductosVendidos=new ArrayList<Productos>();
    ArrayList<Integer>CantidadProductos=new ArrayList<Integer>();
    ListView lstVenta;
    AdaptadorVenta adaptadorVenta;
    Conexion conexion=new Conexion(this, Utilidades.DATABASE,null,1);
    Integer contador=0;
    Productos producto=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_productos);
        txteId_Producto=(EditText)findViewById(R.id.txtId_Producto);
        intent.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        intent.setPrompt("Escanea el codigo de barras");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setOrientationLocked(false);
        btnVenta=(Button)findViewById(R.id.btnVenta);
        lstVenta=(ListView)findViewById(R.id.lstVenta);
        adaptadorVenta=new AdaptadorVenta(this,ProductosVendidos,CantidadProductos);
        lstVenta.setAdapter(null);

        lstVenta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int Cantidad=CantidadProductos.get(position);
                Log.i("CANTIDAD",""+Cantidad);
                switch (view.getId()){
                    case R.id.imgbResta:
                        CantidadProductos.set(position,Cantidad-1);
                        if (CantidadProductos.get(position)<=0){
                            ProductosVendidos.remove(position);
                            CantidadProductos.remove(position);
                            contador--;
                        }
                        break;
                    case R.id.imgbSuma:
                        CantidadProductos.set(position,Cantidad+1);
                        break;
                }
                adaptadorVenta.setData(ProductosVendidos,CantidadProductos);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result =IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        String id=result.getContents();
        try {
            Log.w("LECTURA", "Code: "+result.getContents() );
            boolean Existe=false;
            if (contador>0){
                for (int i=0;i<ProductosVendidos.size();i++){
                    if (id.equals(ProductosVendidos.get(i).getID_Producto())){
                        Existe=true;
                        CantidadProductos.set(i,CantidadProductos.get(i)+1);
                        adaptadorVenta.setData(ProductosVendidos,CantidadProductos);
                    }
                }
            }
            if (!Existe) PonerProducto(id);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }

    public void Escanear(View view){
        intent.initiateScan();
    }

    public void GenerarVenta(View view){
        Conexion conexion=new Conexion(this,Utilidades.DATABASE,null,1);
        SQLiteDatabase bd=conexion.getWritableDatabase();
        Calendar fecha = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String fechaformato = df.format(fecha.getTime());

        ContentValues valores = new ContentValues();
        valores.put(Utilidades.CAMPO_FECHA_REPORTE,fechaformato);
    }

    public void PonerProducto(String ID){
        SQLiteDatabase db=conexion.getReadableDatabase();
        String[] selectionArgs={ID};
        Cursor cursor=db.rawQuery("SELECT*FROM "+Utilidades.TABLA_PRODUCTO+" where "+Utilidades.CAMPO_ID_PRODUCTO+"= ?",selectionArgs);
        cursor.moveToFirst();
        producto=new Productos();
        Integer cantidad=1;
        producto.setID_Producto(cursor.getString(0));
        producto.setNombre_Producto(cursor.getString(1));
        producto.setCosto_Venta(cursor.getDouble(2));
        producto.setCosto_Compra(cursor.getDouble(3));
        producto.setExistencia(cursor.getInt(4));
        producto.setVeces_Vendido(cursor.getInt(5));
        producto.setID_Proveedor(cursor.getInt(6));
        Log.w("PRODUCTO", "Nombre: " +producto.getNombre_Producto() );
        ProductosVendidos.add(producto);
        CantidadProductos.add(cantidad);
        adaptadorVenta.setData(ProductosVendidos,CantidadProductos);
        lstVenta.setAdapter(adaptadorVenta);
        contador++;
        Log.i("CONTADOR",contador.toString());
    }

    public void Buscar(View view){
        try {
            String id=txteId_Producto.getText().toString();
            boolean Existe=false;
            if (contador>0){
                for (int i=0;i<ProductosVendidos.size();i++){
                    if (id.equals(ProductosVendidos.get(i).getID_Producto())){
                        Existe=true;
                        CantidadProductos.set(i,CantidadProductos.get(i)+1);
                        adaptadorVenta.setData(ProductosVendidos,CantidadProductos);
                    }
                }
            }
            if (!Existe) PonerProducto(id);
        }catch (Exception e){
            Log.e("BUSCAR",e.getMessage());
            Toast.makeText(this,"Producto no encontrado",Toast.LENGTH_LONG).show();
        }
    }

}
