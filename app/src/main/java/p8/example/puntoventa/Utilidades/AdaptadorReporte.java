package p8.example.puntoventa.Utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import p8.example.puntoventa.db_store.Reportes;

public class AdaptadorReporte extends BaseAdapter {
    private static LayoutInflater inflater=null;
    Context context;
    ArrayList<Reportes> ListaReportes;

    public AdaptadorReporte(Context context, ArrayList<Reportes> listaReportes) {
        this.context = context;
        ListaReportes = listaReportes;
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
