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

public class ReportePerson extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button btnFechaI,btnFechaF,btnSearch;
    TextView txtFechaI,txtFechaF,txtTotal,txtGanancias;
    ListView lstPReportes;
    AdaptadorReporte adaptadorReporte;
    ArrayList<Reportes> ListaReportesP;
    String dbDateStringI,dbDateStringF;
    Boolean PrimeraFecha;
    int fecha1, fecha2;
    Conexion conexion=new Conexion(this, Utilidades.DATABASE,null,2);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_person);
        btnFechaI=(Button)findViewById(R.id.btnPFechaI);
        btnFechaF=(Button)findViewById(R.id.btnPFechaF);
        txtFechaI=(TextView)findViewById(R.id.txtPersonFechaI);
        txtFechaF=(TextView)findViewById(R.id.txtPersonFechaF);
        txtTotal=(TextView)findViewById(R.id.txtTotalVendidoPerson);
        txtGanancias=(TextView)findViewById(R.id.txtGananciasPerson);
        lstPReportes=(ListView)findViewById(R.id.lstPReportes);

        adaptadorReporte=new AdaptadorReporte(this,ListaReportesP);
        lstPReportes.setAdapter(null);

        btnFechaI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrimeraFecha=true;
                DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"Date Picker");
            }
        });
        btnFechaF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrimeraFecha=false;
                DialogFragment datePicker=new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"Date Picker");
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar c=Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String UserDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        if (PrimeraFecha){
            txtFechaI.setText(UserDateString);
            dbDateStringI=df.format(c.getTime());
        }
        else{
            txtFechaF.setText(UserDateString);
            dbDateStringF=df.format(c.getTime());
        }
    }
    public void PonerReporte(View view){
        fecha1=Integer.parseInt(dbDateStringI);
        fecha2=Integer.parseInt(dbDateStringF);
        Double Total=0.0,Ganancias=0.0;
        if ((!dbDateStringI.isEmpty() & !dbDateStringF.isEmpty())&&(fecha1<fecha2)){
            SQLiteDatabase db=conexion.getReadableDatabase();

            ListaReportesP=new ArrayList<Reportes>();
            Reportes reportes=null;
            Cursor cursor=db.rawQuery("SELECT*FROM "+Utilidades.TABLA_REPORTE+" where "+Utilidades.CAMPO_FECHA_REPORTE+" between ? and ?",new String[] {dbDateStringI,dbDateStringF});
            while (cursor.moveToNext()){
                reportes=new Reportes();
                reportes.setID_Reporte(cursor.getInt(0));
                reportes.setTotal(cursor.getDouble(3));
                reportes.setGanancia(cursor.getDouble(4));
                String fecha=cursor.getString(5);
                Total+=reportes.getTotal();
                Ganancias+=reportes.getGanancia();
                reportes.setFecha(fecha);
                ListaReportesP.add(reportes);
            }
            adaptadorReporte.setData(ListaReportesP);
            if(cursor.getCount()==0){
                lstPReportes.setAdapter(null);
                Toast.makeText(this,"No se han encontrado registros",Toast.LENGTH_LONG).show();
                txtTotal.setText("");
                txtGanancias.setText("");
            }
            else{
                lstPReportes.setAdapter(adaptadorReporte);
                txtTotal.setText("Total Vendido: $"+ Total.toString());
                txtGanancias.setText("Ganancias: $"+ Ganancias.toString());
            }
            db.close();
            cursor.close();
        }
        else{
            lstPReportes.setAdapter(null);
            Toast.makeText(this,"Seleccione correctamente las fechas",Toast.LENGTH_LONG).show();
            txtTotal.setText("");
            txtGanancias.setText("");
        }
    }
}
