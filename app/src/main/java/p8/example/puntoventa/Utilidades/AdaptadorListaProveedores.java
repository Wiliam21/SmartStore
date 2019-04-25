package p8.example.puntoventa.Utilidades;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import p8.example.puntoventa.EditarProveedorActivity;
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
        final TextView txtNombre_Proveedor=(TextView)vista.findViewById(R.id.txtNombre_Proveedor);
        ImageButton imgbEditarProveedor=(ImageButton)vista.findViewById(R.id.imgbEditarProveedor);
        ImageButton imgbInfoProveedor=(ImageButton)vista.findViewById(R.id.imgbInfoProveedor);
        txtNombre_Proveedor.setText(ListaProveedores.get(position).getNombre_Proveedor());
        imgbEditarProveedor.setTag(ListaProveedores.get(position).getID_Proveedor());

        imgbEditarProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID_PROVEEDOR=String.valueOf((Integer) v.getTag());
                Log.e("ID", "onClick: "+ID_PROVEEDOR );
                context.startActivity(new Intent(context, EditarProveedorActivity.class).putExtra("ID_PROVEEDOR",ID_PROVEEDOR));
            }
        });
        return vista;
    }
}
