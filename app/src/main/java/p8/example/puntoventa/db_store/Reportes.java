package p8.example.puntoventa.db_store;

import java.util.Date;

//Clase Reportes con sus atributos, constructor, getters y sets
public class Reportes {
    private Integer ID_Reporte;
    private Double Total;
    private Double Ganancia;
    private Date Fecha;

    public Reportes(Integer ID_Reporte, Double total, Double ganancia, Date fecha) {
        this.ID_Reporte = ID_Reporte;
        this.Total = total;
        this.Ganancia = ganancia;
        this.Fecha = fecha;
    }

    public Integer getID_Reporte() {
        return ID_Reporte;
    }

    public void setID_Reporte(Integer ID_Reporte) {
        this.ID_Reporte = ID_Reporte;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public Double getGanancia() {
        return Ganancia;
    }

    public void setGanancia(Double ganancia) {
        Ganancia = ganancia;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }
}
