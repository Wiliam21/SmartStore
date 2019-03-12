package p8.example.puntoventa;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class VentaProductos extends AppCompatActivity {
    private EditText txteId_Producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_productos);
        txteId_Producto=(EditText)findViewById(R.id.txtId_Producto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result =IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            txteId_Producto.setText(result.getContents());
        }
        else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    public void Escanear(View view){
        IntentIntegrator intent =new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        intent.setPrompt("Escanea el codigo de barras");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.initiateScan();
        intent.setBarcodeImageEnabled(false);
    }
}
