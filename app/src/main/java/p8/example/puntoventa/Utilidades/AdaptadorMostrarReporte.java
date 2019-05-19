package p8.example.puntoventa.Utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import p8.example.puntoventa.R;
import p8.example.puntoventa.db_store.Productos;

public class AdaptadorMostrarReporte extends BaseAdapter {
    private static LayoutInflater inflater=null;
    Context context;
    String[] Cantidades;
    ArrayList<Productos> ListaReportes;

    public AdaptadorMostrarReporte(Context context,ArrayList<Productos> listaReportes,String[] cantidades){
        this.context=context;
        this.Cantidades=cantidades;
        this.ListaReportes=listaReportes;
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ListaReportes.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaReportes.get(position);
    }

    @Override
    public long getItemId(int position) {
       // return Integer.parseInt(ListaReportes.get(position).getID_Producto());
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View vista=inflater.inflate(R.layout.elemento_ver_reporte,null);
        Productos productos=(Productos)getItem(position);

        TextView txtCantidad,txtProducto,txtCosto;
        txtCantidad=(TextView)vista.findViewById(R.id.txtCantidadVerReporte);
        txtProducto=(TextView)vista.findViewById(R.id.txtProductoVerReporte);
        txtCosto=(TextView)vista.findViewById(R.id.txtPrecioVerProducto);

        txtCantidad.setText(Cantidades[position]);
        txtProducto.setText(productos.getNombre_Producto());
        txtCosto.setText("$"+productos.getCosto_Venta().toString());

        return vista;
    }
    public void SetData(ArrayList<Productos> ProductosVendidos) {
        this.ListaReportes = ProductosVendidos;
    }
}
