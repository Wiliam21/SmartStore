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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import p8.example.puntoventa.Utilidades.AdaptadorReporte;
import p8.example.puntoventa.Utilidades.DatePickerFragment;
import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.Reportes;

public class ReporteDiario extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Conexion conexion=new Conexion(this,Utilidades.DATABASE,null,2);
    Button btnSelectdate;
    TextView txtfecha;
    ListView lstReportes;
    AdaptadorReporte adaptadorReporte;
    ArrayList <Reportes>ListaReportes;
    String dbDateString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_diario);

        btnSelectdate=(Button)findViewById(R.id.btnSelectdate);
        txtfecha=(TextView)findViewById(R.id.txtfecha);
        lstReportes=(ListView)findViewById(R.id.lstReporte);

        adaptadorReporte=new AdaptadorReporte(this,ListaReportes);
        lstReportes.setAdapter(null);

        btnSelectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker= new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String UserDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        dbDateString = df.format(c.getTime());
        txtfecha.setText(UserDateString);
        PonerReporte();
    }
    public void PonerReporte(){
        SQLiteDatabase db=conexion.getReadableDatabase();

        String[] selectionArgs={dbDateString};

        ListaReportes=new ArrayList<Reportes>();
        Reportes reporte=null;
       Cursor cursor=db.query(Utilidades.TABLA_REPORTE,null,Utilidades.CAMPO_FECHA_REPORTE+"=?",selectionArgs,null,null,null);
        while (cursor.moveToNext()){
            reporte=new Reportes();
            reporte.setID_Reporte(cursor.getInt(0));
            reporte.setTotal(cursor.getDouble(3));
            reporte.setGanancia(cursor.getDouble(4));
            ListaReportes.add(reporte);
            adaptadorReporte.setData(ListaReportes);
        }
        if (cursor.getCount()==0) lstReportes.setAdapter(null);
        else lstReportes.setAdapter(adaptadorReporte);
        db.close();

    }
}

