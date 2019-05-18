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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import p8.example.puntoventa.Utilidades.AdaptadorReporte;
import p8.example.puntoventa.Utilidades.DatePickerFragment;
import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.Reportes;

public class ReporteSemanal extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Conexion conexion=new Conexion(this,Utilidades.DATABASE,null,2);
    TextView txtFechaInicio, txtFechaFinal;
    ListView lstReporteSemanal;
    Button btnFechaSemanal;
    AdaptadorReporte adaptadorReporte;
    ArrayList <Reportes>ListaReportes;
    String dbFirstDateString,dbLastDateString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_semanal);

        txtFechaInicio=(TextView)findViewById(R.id.txtFechaInicio);
        txtFechaFinal=(TextView)findViewById(R.id.txtFechaFinal);
        btnFechaSemanal=(Button)findViewById(R.id.btnFechaSemanal);
        lstReporteSemanal=(ListView)findViewById(R.id.lstReporteSemanal);

        adaptadorReporte = new AdaptadorReporte(this,ListaReportes);
        lstReporteSemanal.setAdapter(null);

        btnFechaSemanal.setOnClickListener(new View.OnClickListener() {
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
        c2.set(year,month,dayOfMonth+7);
        String UserDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        String UserLastDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c2.getTime());
        dbLastDateString=df.format(c2.getTime());
        dbFirstDateString=df.format(c.getTime());
        txtFechaInicio.setText(UserDateString);
        txtFechaFinal.setText(UserLastDateString);
        PonerReporte();
    }
    public void PonerReporte(){
        SQLiteDatabase db=conexion.getReadableDatabase();

        ListaReportes=new ArrayList<Reportes>();
        Reportes reporte=null;
        Cursor cursor=db.rawQuery("SELECT*FROM "+Utilidades.TABLA_REPORTE+" where "+Utilidades.CAMPO_FECHA_REPORTE+" between ? and ?",new String[] {dbFirstDateString,dbLastDateString});
        while (cursor.moveToNext()){
            reporte=new Reportes();
            reporte.setID_Reporte(cursor.getInt(0));
            reporte.setTotal(cursor.getDouble(3));
            reporte.setGanancia(cursor.getDouble(4));
            ListaReportes.add(reporte);
        }
        adaptadorReporte.setData(ListaReportes);
        if(cursor.getCount()==0){
            lstReporteSemanal.setAdapter(null);
            Toast.makeText(this,"No se han encontrado registros",Toast.LENGTH_LONG).show();
        }
        else lstReporteSemanal.setAdapter(adaptadorReporte);
        db.close();
        cursor.close();
    }
}
