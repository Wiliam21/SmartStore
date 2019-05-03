package p8.example.puntoventa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ReporteProductos extends AppCompatActivity {

    LinearLayout Lyt_Diario,Lyt_Semanal,Lyt_Mensual;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_productos);
        Lyt_Diario=(LinearLayout) findViewById(R.id.Lyt_Diario);
        Lyt_Semanal=(LinearLayout) findViewById(R.id.Lyt_Semanal);
        Lyt_Mensual=(LinearLayout) findViewById(R.id.Lyt_Mensual);

    }
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.Lyt_Diario: i = new Intent(this, ReporteDiario.class);startActivity(i);break;
            case R.id.Lyt_Semanal: i = new Intent(this, ReporteSemanal.class);startActivity(i);break;
            case R.id.Lyt_Mensual: i = new Intent(this, ReporteMensual.class);startActivity(i);break;
            default:break;
        }
    }
}
