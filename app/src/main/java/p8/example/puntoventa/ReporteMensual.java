package p8.example.puntoventa;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import p8.example.puntoventa.Utilidades.AdaptadorReporte;
import p8.example.puntoventa.Utilidades.DatePickerFragment;
import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.Reportes;

public class ReporteMensual extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView txtMesFechaI,txtMesFechaF,txtTotal,txtGanancias;
    ListView lstMesReporte;
    Button btnSelFechaM;
    AdaptadorReporte adaptadorReporte;
    ArrayList<Reportes> ListaReportesMes;
    String dbDateString1,dbDateString2;
    Conexion conexion=new Conexion(this, Utilidades.DATABASE,null,2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_mensual);

        txtMesFechaI=(TextView)findViewById(R.id.txtMesFechaI);
        txtMesFechaF=(TextView)findViewById(R.id.txtMesFechaF);
        txtTotal=(TextView)findViewById(R.id.txtTotalVendidoMensual);
        txtGanancias=(TextView)findViewById(R.id.txtGananciasMensuales);
        btnSelFechaM=(Button)findViewById(R.id.btnSelFechaM);
        lstMesReporte=(ListView)findViewById(R.id.lstReporteMensual);

        adaptadorReporte = new AdaptadorReporte(this,ListaReportesMes);
        lstMesReporte.setAdapter(null);

        btnSelFechaM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"Date Picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar c=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c2.set(year,month+1,dayOfMonth);
        String UserDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        String UserLastDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c2.getTime());

        dbDateString1=df.format(c.getTime());
        dbDateString2=df.format(c2.getTime());
        txtMesFechaI.setText(UserDateString);
        txtMesFechaF.setText(UserLastDateString);
        PonerReporte();
    }
    public void PonerReporte(){
        SQLiteDatabase db=conexion.getReadableDatabase();
        Double Total=0.0,Ganancias=0.0;

        ListaReportesMes=new ArrayList<Reportes>();
        Reportes reportes=null;
        Cursor cursor=db.rawQuery("SELECT*FROM "+Utilidades.TABLA_REPORTE+" where "+Utilidades.CAMPO_FECHA_REPORTE+" between ? and ?",new String[] {dbDateString1,dbDateString2});
        while (cursor.moveToNext()){
            reportes=new Reportes();
            reportes.setID_Reporte(cursor.getInt(0));
            reportes.setTotal(cursor.getDouble(3));
            reportes.setGanancia(cursor.getDouble(4));
            Total+=reportes.getTotal();
            Ganancias+=reportes.getGanancia();
            String fecha=cursor.getString(5);
            reportes.setFecha(fecha);
            ListaReportesMes.add(reportes);
        }
        adaptadorReporte.setData(ListaReportesMes);
        if(cursor.getCount()==0){
            lstMesReporte.setAdapter(null);
            Toast.makeText(this,"No se han encontrado registros",Toast.LENGTH_LONG).show();
            txtGanancias.setText("");
            txtTotal.setText("");
        }
        else {
            txtTotal.setText("Total Vendido: $"+Total.toString());
            txtGanancias.setText("Ganancias: $"+Ganancias.toString());
            lstMesReporte.setAdapter(adaptadorReporte);
        }
        db.close();
        cursor.close();
    }
}
