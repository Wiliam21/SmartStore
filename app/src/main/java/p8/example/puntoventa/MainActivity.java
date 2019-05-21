package p8.example.puntoventa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView ventaCard, reporteCard, proveedoresCard, inventarioCard;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ventaCard = (CardView) findViewById(R.id.venta);
        reporteCard = (CardView) findViewById(R.id.reporte);
        proveedoresCard = (CardView) findViewById(R.id.proveedores);
        inventarioCard = (CardView) findViewById(R.id.inventario);

        ventaCard.setOnClickListener(this);
        reporteCard.setOnClickListener(this);
        proveedoresCard.setOnClickListener(this);
        inventarioCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.venta: i = new Intent(this, VentaProductos.class);startActivity(i);break;
            case R.id.reporte: i = new Intent(this, ReporteProductos.class);startActivity(i);break;
            case R.id.proveedores: i = new Intent(this, Proveedor.class);startActivity(i);break;
            case R.id.inventario: i = new Intent(this, Inventario.class);startActivity(i);break;
            default:break;
        }
    }
}
