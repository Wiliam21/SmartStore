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

public class AdaptadorInventario extends BaseAdapter {

    private static LayoutInflater inflater=null;
    Context context;
    ArrayList<Productos> ListaProductos;

    public AdaptadorInventario(Context context, ArrayList<Productos> ListaProductos){
        this.context=context;
        this.ListaProductos=ListaProductos;
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ListaProductos.size();
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
        final View vista=inflater.inflate(R.layout.elementro_producto,null);

        TextView txtNombre;
        ImageButton imgbDelete,imgbInfo,imgbEditar;
        txtNombre=(TextView)vista.findViewById(R.id.txtNombreProducto);
        imgbDelete=(ImageButton) vista.findViewById(R.id.imgbDelete);
        imgbInfo=(ImageButton) vista.findViewById(R.id.imgbInfo);
        imgbInfo=(ImageButton) vista.findViewById(R.id.imgbEditar);

        txtNombre.setText(ListaProductos.get(position).getNombre_Producto());
        return vista;
    }
}
