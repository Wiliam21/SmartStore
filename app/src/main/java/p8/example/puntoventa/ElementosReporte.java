package p8.example.puntoventa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import p8.example.puntoventa.Utilidades.AdaptadorMostrarReporte;
import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.Productos;

public class ElementosReporte extends AppCompatActivity {

    TextView txtFecha,txtID,txtTotal,txtGanancia;
    ListView lstElementos;
    Conexion conexion=new Conexion(this, Utilidades.DATABASE,null,Utilidades.DB_VERSION);
    String ID,Fecha=null,CodigosP="",CantidadesP="",Productos,Cantidades;
    Double Total=0.0,Ganancias=0.0;
    SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
    ArrayList<Productos> ProductosVendidos;
    AdaptadorMostrarReporte adaptadorMostrarReporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elementos_reporte);

        txtFecha=(TextView)findViewById(R.id.txtFechaVerReporte);
        txtID=(TextView)findViewById(R.id.txtIDVerReporte);
        txtTotal=(TextView)findViewById(R.id.txtTotalVerReporte);
        txtGanancia=(TextView)findViewById(R.id.txtGananciasVerReporte);
        lstElementos=(ListView)findViewById(R.id.lstElementosVerReporte);
        adaptadorMostrarReporte=new AdaptadorMostrarReporte(this,ProductosVendidos);
        //lstElementos.setAdapter(null);

        Intent recibir=getIntent();
        ID=recibir.getStringExtra("ID");
        txtID.setText("ID: "+ID);
 
        SQLiteDatabase db=conexion.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT*FROM "+Utilidades.TABLA_REPORTE+" where "+Utilidades.CAMPO_ID_REPORTE+" = ? ",new String[]{ID});

        cursor.moveToFirst();
        Productos=cursor.getString(1);
        Cantidades=cursor.getString(2);
        Log.e("PRODUCTOS",Productos);
        Total=cursor.getDouble(3);
        Ganancias=cursor.getDouble(4);
        String fecha=cursor.getString(5),FechaFinal="",Year="";
        for (int i=0;i<4;i++) Year+=String.valueOf(fecha.charAt(i));
        String Month=String.valueOf(fecha.charAt(4))+fecha.charAt(5);
        String Day=String.valueOf(fecha.charAt(6))+fecha.charAt(7);
        FechaFinal=Day+"/"+Month+"/"+Year;
        Fecha=cursor.getString(5);
        txtFecha.setText("Fecha: "+FechaFinal);
        txtTotal.setText("Vendido: $"+Total);
        txtGanancia.setText("Ganancia: $"+Ganancias);
        db.close();
        PonerDatos();
    }
    public void PonerDatos(){
        ProductosVendidos=new ArrayList<Productos>();
        SQLiteDatabase db=conexion.getReadableDatabase();
        String[] IDs=Productos.split(","),Cant=Cantidades.split(",");
        for (int i=0;i<IDs.length;i++){
            Productos productos=null;
            Cursor cursor=db.rawQuery("SELECT*FROM "+Utilidades.TABLA_PRODUCTO+" where "+Utilidades.CAMPO_ID_PRODUCTO+" = ?", new String[]{IDs[i]});
            cursor.moveToFirst();
            /**
             * El error son estas líneas, fuera de eso, ya reconoce la cantidad de productos vendidos
             * Y si ejecutas con éstas líneas comentadas, la aplicación abre el activity correcto, con los items necesarios en el listview
             * pero obvio sin la información requerida
             *              productos.setCosto_Venta(cursor.getDouble(2));
             *             productos.setNombre_Producto(cursor.getString(1));
             *             productos.setVeces_Vendido(Integer.parseInt(Cant[i]));
             */

            ProductosVendidos.add(productos);
        }
        adaptadorMostrarReporte.SetData(ProductosVendidos);
        lstElementos.setAdapter(adaptadorMostrarReporte);
        db.close();
    }
}
