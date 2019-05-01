package p8.example.puntoventa;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import p8.example.puntoventa.Utilidades.Utilidades;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.ProveedorObjeto;

public class EditarProveedor extends AppCompatActivity {
    EditText txtnEditarNumeroProveedor,txteEditarNombreProveedor;
    Button btnEliminarProveedor,btnGuardarProveedor;
    Conexion conexion=new Conexion(this, Utilidades.DATABASE,null,1);
    Integer ID_PROVEEDOR;
    ProveedorObjeto proveedorObjeto=new ProveedorObjeto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_proveedor);
        txteEditarNombreProveedor=(EditText)findViewById(R.id.txteEditarNombreProveedor);
        txtnEditarNumeroProveedor=(EditText)findViewById(R.id.txtnEditarNumeroProveedor);
        btnEliminarProveedor=(Button)findViewById(R.id.btnEliminarProveedor);
        btnGuardarProveedor=(Button)findViewById(R.id.btnGuardarProveedor);
        Intent Recibir=getIntent();
        ID_PROVEEDOR=Integer.parseInt(Recibir.getStringExtra("ID_PROVEEDOR"));
        PonerDatos();

    }

    public void PonerDatos(){
        SQLiteDatabase db=conexion.getReadableDatabase();
        Cursor cursor=db.query(Utilidades.TABLA_PROVEEDOR,null,Utilidades.CAMPO_ID_PROVEEDOR+"= ?",new String[]{String.valueOf(ID_PROVEEDOR)},null,null,null);
        cursor.moveToFirst();
        proveedorObjeto.setID_Proveedor(ID_PROVEEDOR);
        proveedorObjeto.setNombre_Proveedor(cursor.getString(1));
        proveedorObjeto.setTelefono(cursor.getString(2));
        txteEditarNombreProveedor.setText(proveedorObjeto.getNombre_Proveedor());
        txtnEditarNumeroProveedor.setText(proveedorObjeto.getTelefono());
    }

    public void EliminarProveedor(View view){
        SQLiteDatabase db=conexion.getWritableDatabase();
        db.delete(Utilidades.TABLA_PROVEEDOR,Utilidades.CAMPO_ID_PROVEEDOR+"= ?",new String[]{String.valueOf(ID_PROVEEDOR)});
        db.close();
        startActivity(new Intent(this,Proveedor.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    public void GuardarProveedor(View view){
        SQLiteDatabase db=conexion.getWritableDatabase();
        proveedorObjeto.setNombre_Proveedor(txteEditarNombreProveedor.getText().toString());
        proveedorObjeto.setTelefono(txtnEditarNumeroProveedor.getText().toString());
        ContentValues contentValues=new ContentValues();
        contentValues.put(Utilidades.CAMPO_ID_PROVEEDOR,ID_PROVEEDOR);
        contentValues.put(Utilidades.CAMPO_NOMBRE_PROVEEDOR,proveedorObjeto.getNombre_Proveedor());
        contentValues.put(Utilidades.CAMPO_TELEFONO_PROVEEDOR,proveedorObjeto.getTelefono());
        db.update(Utilidades.TABLA_PROVEEDOR,contentValues,Utilidades.CAMPO_ID_PROVEEDOR+"= ?",new  String[]{String.valueOf(ID_PROVEEDOR)});
        db.close();
        startActivity(new Intent(this,Proveedor.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
