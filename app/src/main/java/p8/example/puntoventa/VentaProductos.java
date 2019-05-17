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
    Double Total,Ganancia,Total_Compra;
    String Id_Producto;
    IntentIntegrator intent =new IntentIntegrator(this);
    TextView txtNombreProducto;
    Button btnVenta;
    ArrayList<Productos>ProductosVendidos=new ArrayList<Productos>();
    ArrayList<Integer>CantidadProductos=new ArrayList<Integer>();
    ListView lstVenta;
    AdaptadorVenta adaptadorVenta;
    Conexion conexion=new Conexion(this, Utilidades.DATABASE,null,2);
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
                if (id==0){
                    int Cantidad=CantidadProductos.get(position);
                    switch (view.getId()){
                        case R.id.imgbResta:
                            CantidadProductos.set(position,Cantidad-1);
                            if (CantidadProductos.get(position)<=0){
                                ProductosVendidos.remove(position);
                                CantidadProductos.remove(position);
                                contador--;
                                ActualizarVenta();
                            }
                            break;
                        case R.id.imgbSuma:
                            if (CantidadProductos.get(position)<ProductosVendidos.get(position).getExistencia())
                                CantidadProductos.set(position,Cantidad+1);
                            ActualizarVenta();
                            break;
                    }
                }
                else if (id==1){
                    ProductosVendidos.remove(position);
                    CantidadProductos.remove(position);
                    contador--;
                    ActualizarVenta();
                }
                adaptadorVenta.setData(ProductosVendidos,CantidadProductos);
            }
        });
        lstVenta.setLongClickable(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Escanea codigo de barras y lo regresa en un Intent
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
            Toast.makeText(this,"Producto no encontrado",Toast.LENGTH_LONG).show();
        }
    }

    public void Escanear(View view){
        intent.initiateScan();
    }

    public void GenerarVenta(View view){
        try {
            Ganancia=0.0;
            Total_Compra=0.0;
            SQLiteDatabase bd=conexion.getWritableDatabase();
            Calendar fecha = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String fechaformato = df.format(fecha.getTime());
            String Codigo_Productos="",Cantidad_Productos="";
            for (int i=0; i<ProductosVendidos.size();i++){
                Codigo_Productos+=ProductosVendidos.get(i).getID_Producto()+",";
                Cantidad_Productos+=CantidadProductos.get(i).toString()+",";
                Total_Compra+=CantidadProductos.get(i).doubleValue()*ProductosVendidos.get(i).getCosto_Compra();
                ContentValues DatosProducto=new ContentValues();
                DatosProducto.put(Utilidades.CAMPO_EXISTENCIA_PRODUCTO,ProductosVendidos.get(i).getExistencia()-CantidadProductos.get(i));
                DatosProducto.put(Utilidades.CAMPO_VECES_VENDIDO,ProductosVendidos.get(i).getVeces_Vendido()+CantidadProductos.get(i));
                bd.update(Utilidades.TABLA_PRODUCTO,DatosProducto,Utilidades.CAMPO_ID_PRODUCTO+" = ?",new String[]{ProductosVendidos.get(i).getID_Producto()});
            }
            Ganancia=Total-Total_Compra;
            ContentValues valores = new ContentValues();
            valores.put(Utilidades.CAMPO_FECHA_REPORTE,fechaformato);
            valores.put(Utilidades.CAMPO_PRODUCTOS_VENDIDOS,Codigo_Productos);
            valores.put(Utilidades.CAMPO_CANTIDAD_PRODUCTOS,Cantidad_Productos);
            valores.put(Utilidades.CAMPO_GANANCIA_REPORTE,Ganancia);
            valores.put(Utilidades.CAMPO_TOTAL_REPORTE,Total);
            bd.insert(Utilidades.TABLA_REPORTE,Utilidades.CAMPO_ID_REPORTE,valores);
            Toast.makeText(this,"Venta realizada",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.e("GenerarVenta: ",e.getMessage());
        }
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
        if (producto.getExistencia().intValue()==0) cantidad=0;
        Log.w("PRODUCTO", "Nombre: " +producto.getNombre_Producto() );
        ProductosVendidos.add(producto);
        CantidadProductos.add(cantidad);
        adaptadorVenta.setData(ProductosVendidos,CantidadProductos);
        lstVenta.setAdapter(adaptadorVenta);
        contador++;
        Log.i("CONTADOR",contador.toString());
        ActualizarVenta();
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
            txteId_Producto.setText(null);
        }catch (Exception e){
            Log.e("BUSCAR",e.getMessage());
            Toast.makeText(this,"Producto no encontrado",Toast.LENGTH_LONG).show();
        }
    }

    public void ActualizarVenta(){
        Total=0.0;
        for (int i=0;i<ProductosVendidos.size();i++){
            Total+=(ProductosVendidos.get(i).getCosto_Venta()*CantidadProductos.get(i));
        }
        btnVenta.setText("TOTAL: $"+Total);
    }



}