package p8.example.puntoventa;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AltaProductos extends AppCompatActivity {
    private EditText txtnId_Producto,txteNombre,txtnCantidad,txtnCompra,txtnVenta;
    private Button btnAlta;
    private ImageButton imgbScan;
    Spinner comboProveedores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_productos);

        txtnId_Producto=(EditText)findViewById(R.id.txtnId_Producto);
        txteNombre=(EditText)findViewById(R.id.txteNombre);
        txtnCantidad=(EditText)findViewById(R.id.txtnCantidad);
        txtnVenta=(EditText)findViewById(R.id.txtnVenta);
        txtnCompra=(EditText)findViewById(R.id.txtnCompra);
        btnAlta=(Button)findViewById(R.id.btnAlta);
        imgbScan=(ImageButton)findViewById(R.id.imgbScan);
        comboProveedores = findViewById(R.id.spinner_proveedores);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.combo_proveedores,android.R.layout.simple_spinner_item);
        comboProveedores.setAdapter(adapter);

    }

    public void AltaProducto(View view){
        String Nombre=txteNombre.getText().toString();
        String Id_Producto=txtnId_Producto.getText().toString();
        double CostoCompra=Double.parseDouble(txtnCompra.getText().toString());
        double CostoVenta=Double.parseDouble(txtnVenta.getText().toString());
        int Cantidad= Integer.parseInt(txtnCantidad.getText().toString());

    }

    public void Escanear(View view){
        IntentIntegrator intent =new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setPrompt("Escanea el codigo de barras");
        intent.initiateScan();
        intent.setBarcodeImageEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result =IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            txtnId_Producto.setText(result.getContents());
        }
        else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }


}
