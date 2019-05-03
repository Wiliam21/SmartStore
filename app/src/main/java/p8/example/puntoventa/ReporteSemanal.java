package p8.example.puntoventa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ReporteSemanal extends AppCompatActivity {

    EditText txtFirstDate, txtLastDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_semanal);
        txtFirstDate=(EditText)findViewById(R.id.txtFirstDate);
        txtLastDate=(EditText)findViewById(R.id.txtLastDate);

    }
}
