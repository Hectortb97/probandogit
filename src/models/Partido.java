package models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Partido implements Serializable, Comparable<Partido> {

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    private String local;
    private String visitante;
    private String division;
    private int golesLocal;
    private int golesVisitante;
    private String resultado;
    private Date fecha;

    public Partido(String eLocal, String eVisitante, String division, int golesLocal, int golesVisitante, String fecha) {

        this.local = eLocal;
        this.visitante = eVisitante;
        this.division = division;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.resultado = String.valueOf(golesLocal) + "-"+ String.valueOf(golesVisitante);
        try {
            this.fecha = sdf.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getVisitante() {
        return visitante;
    }

    public void setVisitante(String visitante) {
        this.visitante = visitante;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(int golesLocal, int golesVisitante) {
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.resultado = String.valueOf(golesLocal) + "-" + String.valueOf(golesVisitante);
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "Equipo Local='" + local + '\'' +
                ", Equipo Visitante='" + visitante + '\'' +
                ", Division='" + division + '\'' +
                ", Resultado=" + resultado +
                ", Fecha='" + sdf.format(fecha) + '\'' +
                '}';
    }

    @Override
    public int compareTo(Partido o) {
        return getFecha().compareTo(o.getFecha());
    }
}