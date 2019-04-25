package p8.example.puntoventa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;

public class EditarProveedorActivity extends AppCompatActivity {
    EditText txtnEditarNumeroProveedor,txteEditarNombreProveedor;
    Button btnEliminarProveedor,btnGuardarProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_proveedor);
        txteEditarNombreProveedor=(EditText)findViewById(R.id.txteEditarNombreProveedor);
        txtnEditarNumeroProveedor=(EditText)findViewById(R.id.txtnEditarNumeroProveedor);
        btnEliminarProveedor=(Button)findViewById(R.id.btnEliminarProveedor);
        btnGuardarProveedor=(Button)findViewById(R.id.btnGuardarProveedor);
        Intent Recibir=getIntent();
        String ID_PROVEEDOR=Recibir.getStringExtra("ID_PROVEEDOR");
        Conexion conexion=new Conexion(this, Utilidades.DATABASE,null,1);
        SQLiteDatabase db=conexion.getReadableDatabase();
        Cursor cursor=db.query(Utilidades.TABLA_PROVEEDOR,null,Utilidades.CAMPO_ID_PROVEEDOR+"= ?",new String[]{ID_PROVEEDOR},null,null,null);
        cursor.moveToFirst();
        txteEditarNombreProveedor.setText(cursor.getString(1));
        txtnEditarNumeroProveedor.setText(cursor.getString(2));
    }
}
