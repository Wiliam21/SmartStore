package p8.example.puntoventa.Utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import p8.example.puntoventa.R;
import p8.example.puntoventa.db_store.Productos;

public class AdaptadorVenta extends BaseAdapter {
    private static LayoutInflater inflater=null;
    Context context;
    ArrayList<Productos> ListaProductos;

    public AdaptadorVenta(Context context, ArrayList<Productos> listaProductos) {
        this.context = context;
        ListaProductos = listaProductos;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View vista=inflater.inflate(R.layout.elemento_venta,null);
        Productos producto=(Productos)getItem(position);
        TextView txtNombre,txtPrecio;
        txtNombre=(TextView)vista.findViewById(R.id.txtNombreProducto);
        txtPrecio=(TextView)vista.findViewById(R.id.txtPrecio);
        txtNombre.setText(producto.getNombre_Producto());
        txtPrecio.setText(producto.getCosto_Venta().toString());
        return vista;
    }
}
