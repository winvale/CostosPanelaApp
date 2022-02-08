package com.example.tesis;

public class Usuario {
    int id;
    String Nombre, Correo, cc, Password;

    public Usuario() {
    }

    public boolean isNull(){
        if (Nombre.equals("") && Correo.equals("") && cc.equals("") && Password.equals("")){
            return false;
        }else { return true; }
    }

    public Usuario(String nombre, String correo, String cc, String password) {
        Nombre = nombre;
        Correo = correo;
        this.cc = cc;
        Password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", Nombre='" + Nombre + '\'' +
                ", Correo='" + Correo + '\'' +
                ", cc='" + cc + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
