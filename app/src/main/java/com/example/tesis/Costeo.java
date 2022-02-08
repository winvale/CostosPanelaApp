package com.example.tesis;

public class Costeo {
    int id, idUsuario, ManoObra, Jornales, Insumos, Transporte, OtrosGastos, Total;
    String Tipo;


    public Costeo(int idUsuario, int manoObra, int jornales, int insumos, int transporte, int otrosGastos, int total, String tipo) {
        this.idUsuario = idUsuario;
        ManoObra = manoObra;
        Jornales = jornales;
        Insumos = insumos;
        Transporte = transporte;
        OtrosGastos = otrosGastos;
        Total = total;
        Tipo = tipo;
    }

    @Override
    public String toString() {
        return "Costeo{" +
                "Tipo='" + Tipo + '\'' +
                '}';
    }
    /*
    //validar
    public boolean isNull(){
        if (ManoObra. && Jornales.equals(0) && Insumos.equals("") && Transporte.equals("") && OtrosGastos){
            return false;
        }else { return true; }
    }

     */

    public Costeo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getManoObra() {
        return ManoObra;
    }

    public void setManoObra(int manoObra) {
        ManoObra = manoObra;
    }

    public int getJornales() {
        return Jornales;
    }

    public void setJornales(int jornales) {
        Jornales = jornales;
    }

    public int getInsumos() {
        return Insumos;
    }

    public void setInsumos(int insumos) {
        Insumos = insumos;
    }

    public int getTransporte() {
        return Transporte;
    }

    public void setTransporte(int transporte) {
        Transporte = transporte;
    }

    public int getOtrosGastos() {
        return OtrosGastos;
    }

    public void setOtrosGastos(int otrosGastos) {
        OtrosGastos = otrosGastos;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
}
