package com.example.tesis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoUsuario {
    Context c;
    Usuario u;
    ArrayList<Usuario> lista;
    SQLiteDatabase sql;
    String db= "DBSC";
    String tabla= "CREATE TABLE if not exists usuario(id integer primary key autoincrement," +
            "nombre TEXT, correo TEXT, cc TEXT, password TEXT)";

    public daoUsuario(Context c){
        this.c=c;
        sql = c.openOrCreateDatabase(db, c.MODE_PRIVATE, null);
        sql.execSQL(tabla);
        u = new Usuario();

    }

    public boolean insertarUsuario(Usuario u){
        if(buscar(u.getCorreo()) == 0){
            ContentValues cv=new ContentValues();
            cv.put("nombre",u.getNombre());
            cv.put("correo",u.getCorreo());
            cv.put("cc",u.getCc());
            cv.put("password",u.getPassword());
            return (sql.insert("usuario",null,cv)>0);
        }else { return false;}
    }

    public int buscar(String cor){
        int x=0;
        lista=selectUsuario();
        for (Usuario co: lista) {
           if(co.getCorreo().equals(cor)){
            x++;
           }
        }

        return x;
    }

    public ArrayList<Usuario> selectUsuario(){
        ArrayList<Usuario> lista = new ArrayList<>();
        lista.clear();
        Cursor cr=sql.rawQuery("select * from usuario",null);
        if(cr != null && cr.moveToFirst()){
            do{
                Usuario u = new Usuario();
                u.setId(cr.getInt(0));
                u.setNombre(cr.getString(1));
                u.setCorreo(cr.getString(2));
                u.setCc(cr.getString(3));
                u.setPassword(cr.getString(4));

                lista.add(u);

            }while (cr.moveToNext());
        }
        return lista;
    }

    public Usuario getUsuarioById(int id){
        lista=selectUsuario();
        for (Usuario usuario: lista) {
            if(usuario.getId() == id){
                return usuario;
            }
        }
        return null;
    }

    public Usuario getUsuario(String corr, String pas){
        lista=selectUsuario();
        for (Usuario usuario: lista) {
            if(usuario.getCorreo().equals(corr) && usuario.getPassword().equals(pas)){
                return usuario;
            }
        }
        return null;
    }

    public int login(String u, String p){
        int a=0;
        Cursor cr = sql.rawQuery("SELECT * FROM usuario",null);
        if(cr != null && cr.moveToFirst()) {
            do {
                if (cr.getString(2).equals(u) && cr.getString(4).equals(p)) {
                    a++;
                }
            } while (cr.moveToNext());

        }
        return a;
    }

}
