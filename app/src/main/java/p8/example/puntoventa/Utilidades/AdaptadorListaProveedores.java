package p8.example.puntoventa.Utilidades;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import p8.example.puntoventa.EditarProveedor;
import p8.example.puntoventa.R;
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
        View vista=convertView;
        ViewHolder holder=new ViewHolder();
        if (vista==null){
            try{
                vista=inflater.inflate(R.layout.elemento_proveedor,null);
                holder=new ViewHolder();
                holder.txtNombre=(TextView)vista.findViewById(R.id.txtNombre_Proveedor);
                holder.txtTelefono=(TextView)vista.findViewById(R.id.txtNumeroProveedor);
                vista.setTag(holder);
            }catch (Exception e){
                e.printStackTrace();
            }
        } else holder=(ViewHolder)vista.getTag();

        holder.txtNombre.setText(ListaProveedores.get(position).getNombre_Proveedor());
        holder.txtTelefono.setText(ListaProveedores.get(position).getTelefono());
        return vista;
        /*
        final View vista=inflater.inflate(R.layout.elemento_proveedor,null);
        final TextView txtNombre_Proveedor=(TextView)vista.findViewById(R.id.txtNombre_Proveedor);
        TextView txtNumeroProveedor=(TextView)vista.findViewById(R.id.txtNumeroProveedor);
        ImageButton imgbEditarProveedor=(ImageButton)vista.findViewById(R.id.imgbEditarProveedor);

        txtNombre_Proveedor.setText(ListaProveedores.get(position).getNombre_Proveedor());
        txtNumeroProveedor.setText(ListaProveedores.get(position).getTelefono());
        imgbEditarProveedor.setTag(ListaProveedores.get(position).getID_Proveedor());

        imgbEditarProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID_PROVEEDOR=String.valueOf((Integer) v.getTag());
                Log.e("ID", "onClick: "+ID_PROVEEDOR );
                context.startActivity(new Intent(context, EditarProveedor.class).putExtra("ID_PROVEEDOR",ID_PROVEEDOR));
            }
        });
        return vista;*/
    }
    class  ViewHolder{
        TextView txtNombre,txtTelefono;
    }
    public void setData(ArrayList<ProveedorObjeto> listaProveedores){
        this.ListaProveedores=ListaProveedores;

    }
}
