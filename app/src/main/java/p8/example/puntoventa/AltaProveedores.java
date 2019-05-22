package p8.example.puntoventa;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;

//Alta de proveedor

public class AltaProveedores extends AppCompatActivity {
    EditText txteNombreProveedor,txtnNumeroProveedor;
    Button btnAltaProveedor;
    String RETORNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_proveedores);
        txteNombreProveedor=(EditText)findViewById(R.id.txteNombreProveedor);
        txtnNumeroProveedor=(EditText)findViewById(R.id.txtnNumeroProveedor);
        btnAltaProveedor=(Button)findViewById(R.id.btnAltaProveedor);
        Intent intent=getIntent();
        RETORNO=intent.getStringExtra("RETORNO");

        View.OnClickListener Alta=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AltaProveedor();
                }catch (Exception e){
                    Log.e("ALTA PROVEEDOR",e.getMessage());
                }
            }
        };
        btnAltaProveedor.setOnClickListener(Alta);
    }
    public void AltaProveedor(){
        Conexion conexion=new Conexion(this,"db_SmartStore",null,2);
        SQLiteDatabase db=conexion.getWritableDatabase();

        String NombreProveedor=txteNombreProveedor.getText().toString();
        String TelefonoProveedor=txtnNumeroProveedor.getText().toString();

        if (NombreProveedor.isEmpty() || TelefonoProveedor.isEmpty())
            Toast.makeText(this,"Llene los campos",Toast.LENGTH_LONG).show();
        else{
            ContentValues values =new ContentValues();
            values.put(Utilidades.CAMPO_NOMBRE_PROVEEDOR,NombreProveedor);
            values.put(Utilidades.CAMPO_TELEFONO_PROVEEDOR,TelefonoProveedor);

            Long idProveedor=db.insert(Utilidades.TABLA_PROVEEDOR,Utilidades.CAMPO_ID_PROVEEDOR,values);
            db.close();
            Toast.makeText(this,"Id proveedor: "+idProveedor,Toast.LENGTH_LONG).show();
            if (RETORNO.equals("Proveedor")) {
                startActivity(new Intent(this,Proveedor.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }else {
                startActivity(new Intent(this,AltaProductos.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        }

    }
}
