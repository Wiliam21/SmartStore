package p8.example.puntoventa.Utilidades;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import p8.example.puntoventa.R;
import p8.example.puntoventa.VentaProductos;
import p8.example.puntoventa.db_store.Productos;

public class AdaptadorVenta extends BaseAdapter {
    private static LayoutInflater inflater=null;
    Context context;
    ArrayList<Productos> ListaProductos;
    ArrayList<Integer>CantidadPorductos;

    public AdaptadorVenta(Context context, ArrayList<Productos> listaProductos, ArrayList<Integer> cantidadPorductos) {
        this.context = context;
        this.ListaProductos = listaProductos;
        this.CantidadPorductos=cantidadPorductos;
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return ListaProductos.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaProductos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(ListaProductos.get(position).getID_Producto());
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final View vista=inflater.inflate(R.layout.elemento_venta,null);
        Productos producto=(Productos)getItem(position);
        TextView txtNombre,txtPrecio,txtnCantidadVenta,txtSubtotal;
        ImageButton imgbResta,imgbSuma;
        txtnCantidadVenta=(TextView)vista.findViewById(R.id.txtnCantidadVenta);
        txtSubtotal=(TextView)vista.findViewById(R.id.txtSubtotal);
        imgbResta=(ImageButton)vista.findViewById(R.id.imgbResta);
        imgbSuma=(ImageButton)vista.findViewById(R.id.imgbSuma);
        txtnCantidadVenta.setText(CantidadPorductos.get(position).toString());
        txtNombre=(TextView)vista.findViewById(R.id.txtID);
        txtPrecio=(TextView)vista.findViewById(R.id.txtPrecioProducto);
        txtNombre.setText(producto.getNombre_Producto());
        txtPrecio.setText(producto.getCosto_Venta().toString());
        txtSubtotal.setText(""+(producto.getCosto_Venta()*CantidadPorductos.get(position)));
        if (CantidadPorductos.get(position)==0)txtnCantidadVenta.setText("AGOTADO");

        vista.setLongClickable(true);
        imgbResta.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((ListView)parent).performItemClick(v,position,1);
                Log.e("onLongClick: ","Presion larga: "+position);
                return true;
            }
        });

        imgbResta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView)parent).performItemClick(v,position,0);
            }
        });

        imgbSuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView)parent).performItemClick(v,position,0);
            }
        });

        return vista;
    }

    public void setData(ArrayList<Productos> listaProductos,ArrayList<Integer>cantidadPorductos){
        this.ListaProductos=listaProductos;
        this.CantidadPorductos=cantidadPorductos;
        notifyDataSetChanged();
    }
}