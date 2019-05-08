package p8.example.puntoventa;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import p8.example.puntoventa.Utilidades.DatePickerFragment;
import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;

public class ReporteDiario extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Conexion conexion=new Conexion(this,Utilidades.DATABASE,null,2);
    Button btnSelectdate;
    TextView txtfecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_diario);

        btnSelectdate=(Button)findViewById(R.id.btnSelectdate);
        txtfecha=(TextView)findViewById(R.id.txtfecha);
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
        String dbDateString = df.format(c.getTime());
        txtfecha.setText(UserDateString);

    }
}

