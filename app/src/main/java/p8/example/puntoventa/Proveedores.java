package p8.example.puntoventa;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;

public class Proveedores extends AppCompatActivity {
    EditText txteNombreProveedor,txtnNumeroProveedor;
    Button btnAltaProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedores);
        txteNombreProveedor=(EditText)findViewById(R.id.txteNombreProveedor);
        txtnNumeroProveedor=(EditText)findViewById(R.id.txtnNumeroProveedor);
        btnAltaProveedor=(Button)findViewById(R.id.btnAltaProveedor);

        View.OnClickListener Alta=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AltaProveedor();
            }
        };
        btnAltaProveedor.setOnClickListener(Alta);
    }
    public void AltaProveedor(){
        Conexion conexion=new Conexion(this,"db_SmartStore",null,1);
        SQLiteDatabase db=conexion.getWritableDatabase();

        String NombreProveedor=txteNombreProveedor.getText().toString();
        String TelefonoProveedor=txtnNumeroProveedor.getText().toString();

        ContentValues values =new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE_PROVEEDOR,NombreProveedor);
        values.put(Utilidades.CAMPO_TELEFONO_PROVEEDOR,TelefonoProveedor);

        Long idProveedor=db.insert(Utilidades.TABLA_PROVEEDOR,Utilidades.CAMPO_ID_PROVEEDOR,values);
        db.close();
        Toast.makeText(this,"Id proveedor: "+idProveedor,Toast.LENGTH_LONG).show();
    }
}
