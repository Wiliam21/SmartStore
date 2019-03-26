package p8.example.puntoventa.Utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import p8.example.puntoventa.R;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.Productos;
import p8.example.puntoventa.db_store.ProveedorObjeto;

public class AdaptadorListaProveedores extends BaseAdapter {

    private static LayoutInflater inflater=null;
    Context context;
    ArrayList<ProveedorObjeto> ListaProveedores;

    public AdaptadorListaProveedores(Context context, ArrayList<ProveedorObjeto> listaProveedores) {
        this.context = context;
        ListaProveedores = listaProveedores;
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ListaProveedores.size();
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
        final View vista=inflater.inflate(R.layout.elemento_proveedor,null);
        TextView txtNombre_Proveedor;
        txtNombre_Proveedor=(TextView)vista.findViewById(R.id.txtNombre_Proveedor);
        txtNombre_Proveedor.setText(ListaProveedores.get(position).getNombre_Proveedor());
        return vista;
    }
}
