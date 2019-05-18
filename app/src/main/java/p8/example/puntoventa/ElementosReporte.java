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

import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.Productos;
import p8.example.puntoventa.db_store.Reportes;

public class ElementosReporte extends AppCompatActivity {

    TextView txtFecha,txtID,txtTotal,txtGanancia;
    ListView lstElementos;
    Conexion conexion=new Conexion(this, Utilidades.DATABASE,null,Utilidades.DB_VERSION);
    String ID,Fecha=null,CodigosP="",CantidadesP="",Productos,Cantidades;
    Double Total=0.0,Ganancias=0.0;
    SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
    ArrayList<Integer> CantidadProducto=new ArrayList<>();
    ArrayList<Productos> ProductosVendidos=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elementos_reporte);

        txtFecha=(TextView)findViewById(R.id.txtFechaVerReporte);
        txtID=(TextView)findViewById(R.id.txtIDVerReporte);
        txtTotal=(TextView)findViewById(R.id.txtTotalVerReporte);
        txtGanancia=(TextView)findViewById(R.id.txtGananciasVerReporte);
        lstElementos=(ListView)findViewById(R.id.lstElementosVerReporte);

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
        Fecha=cursor.getString(5);
        txtFecha.setText("Fecha: "+Fecha);
        txtTotal.setText("Vendido: $"+Total);
        txtGanancia.setText("Ganancia: $"+Ganancias);

    }
}
