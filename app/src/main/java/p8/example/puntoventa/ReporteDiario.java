package p8.example.puntoventa;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReporteDiario extends AppCompatActivity {

    Button btnSelectdate;
    TextView txtfecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_diario);

        btnSelectdate=(Button)findViewById(R.id.btnSelectdate);
        txtfecha=(TextView)findViewById(R.id.txtfecha);

        /*cv.setOnDateChangeListener(new OnDateChangeListener() {@Override public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            { // TODO Auto-generated method stub initScheduleEvent(); } });
                // new AlertDialog.Builder(MomAppActivity.this) .setTitle("Event Calendar") .setMessage("Click to schedule or view events.") .setView(ll) .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                // public void onClick(DialogInterface dialog, int whichButton) { //do nothing...yet } }).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                // { public void onClick(DialogInterface dialog, int whichButton) { // Do nothing. } } ).show();

    }*/
    }
    public void fecha(View view){
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout Ll = (LinearLayout) inflater.inflate(R.layout.dailycalendar, null, false);
        CalendarView Calendar = (CalendarView) Ll.getChildAt(0);
        Calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, final int year, final int month,final int day) {
                new AlertDialog.Builder(ReporteDiario.this).setTitle("Elige la fecha").setMessage("Click en la fecha deseada").setView(Ll).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String date=day+"/"+month+"/"+year;
                        txtfecha.setText(date);
                    }
                });
            }
        });
    }
}
