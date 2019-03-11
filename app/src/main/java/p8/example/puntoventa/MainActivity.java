package p8.example.puntoventa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView altaCard, preciosCard, ventaCard, reporteCard, proveedoresCard, inventarioCard, ajustesCard;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        altaCard = (CardView) findViewById(R.id.alta);
        preciosCard = (CardView) findViewById(R.id.precios);
        ventaCard = (CardView) findViewById(R.id.venta);
        reporteCard = (CardView) findViewById(R.id.reporte);
        proveedoresCard = (CardView) findViewById(R.id.proveedores);
        inventarioCard = (CardView) findViewById(R.id.inventario);
        ajustesCard = (CardView) findViewById(R.id.ajustes);

        altaCard.setOnClickListener(this);
        preciosCard.setOnClickListener(this);
        ventaCard.setOnClickListener(this);
        reporteCard.setOnClickListener(this);
        proveedoresCard.setOnClickListener(this);
        inventarioCard.setOnClickListener(this);
        ajustesCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){

            case R.id.alta: i = new Intent(this,AltaProductos.class);startActivity(i); break;
            case R.id.precios: i = new Intent(this, PreciosProductos.class);startActivity(i); break;
            case R.id.venta: i = new Intent(this, VentaProductos.class);startActivity(i);break;
            case R.id.reporte: i = new Intent(this, ReporteProductos.class);startActivity(i);break;
            case R.id.proveedores: i = new Intent(this, Proveedores.class);startActivity(i);break;
            case R.id.inventario: i = new Intent(this, Inventario.class);startActivity(i);break;
            case R.id.ajustes: i = new Intent(this, Ajustes.class);startActivity(i);break;
            default:break;

        }
    }
}
