package p8.example.puntoventa.Utilidades;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import p8.example.puntoventa.R;
import p8.example.puntoventa.db_store.Reportes;

public class AdaptadorReporte extends BaseAdapter {
    private static LayoutInflater inflater=null;
    Context context;
    ArrayList<Reportes> ListaReportes;

    public AdaptadorReporte(Context context, ArrayList<Reportes> listaReportes) {
        this.context = context;
        this.ListaReportes=listaReportes;
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return ListaReportes.size();
    }

    @Override
    public Object getItem(int pos) {
        return ListaReportes.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return ListaReportes.get(pos).getID_Reporte();
    }

    @Override
    public View getView(final int position, View ConvertView, ViewGroup parent) {
        final View vista=inflater.inflate(R.layout.elemento_reporte,null);
        Reportes reporte=(Reportes)getItem(position);
        TextView txtFecha,txtTotalvendido,txtGanancias;
        txtFecha=(TextView)vista.findViewById(R.id.txtFecha);
        txtTotalvendido=(TextView)vista.findViewById(R.id.txtTotalVendido);
        txtGanancias=(TextView)vista.findViewById(R.id.txtGanancias);
        txtFecha.setText(reporte.getFecha().toString());
        txtTotalvendido.setText(reporte.getTotal().toString());
        txtGanancias.setText(reporte.getGanancia().toString());
        return vista;
    }
    public void setData(ArrayList<Reportes> listaReportes){
        this.ListaReportes=listaReportes;
        notifyDataSetChanged();
        Log.w("UPDATE","Se acualizó");
    }
}
