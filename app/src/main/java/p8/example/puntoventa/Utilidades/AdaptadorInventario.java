package p8.example.puntoventa.Utilidades;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Activity;


import java.util.ArrayList;

import p8.example.puntoventa.EditarProducto;
import p8.example.puntoventa.Inventario;
import p8.example.puntoventa.R;
import p8.example.puntoventa.db_store.Conexion;
import p8.example.puntoventa.db_store.Productos;


public class AdaptadorInventario extends BaseAdapter {

    private static LayoutInflater inflater=null;
    Context context;
    ArrayList<Productos> ListaProductos;
    Conexion conexion =new Conexion(context,Utilidades.DATABASE,null,1);

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
        return ListaProductos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View vista=inflater.inflate(R.layout.elementro_producto,null);
        Productos producto=(Productos)getItem(position);
        TextView txtNombre;
        ImageButton imgbInfo,imgbEditar;
        txtNombre=(TextView)vista.findViewById(R.id.txtNombreProducto);
        imgbInfo=(ImageButton) vista.findViewById(R.id.imgbInfo);
        imgbEditar=(ImageButton) vista.findViewById(R.id.imgbEditar);
        String id=producto.getID_Producto();
        txtNombre.setText(producto.getNombre_Producto());
        imgbEditar.setTag(id);
        return vista;
    }

}
