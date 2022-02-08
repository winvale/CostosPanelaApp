package com.example.tesis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoCosteo {
    Context con;
    Costeo c;
    ArrayList<Costeo> lista;
    SQLiteDatabase sql;
    String db= "DBSC";
    String tabla= "CREATE TABLE if not exists costeo(id integer primary key autoincrement," +
            "tipo TEXT, manoObra integer, jornales integer, insumos integer, transporte integer, " +
            "otrosGastos integer, total integer, idUsuario integer)";

    public daoCosteo(Context con){
        this.con=con;
        sql = con.openOrCreateDatabase(db, con.MODE_PRIVATE, null);
        sql.execSQL(tabla);
        c = new Costeo();

    }

    public boolean insertarCosteo(Costeo c){
        //if(buscarC(c.getIdUsuario()) == 0){
            ContentValues cv=new ContentValues();
            cv.put("tipo",c.getTipo());
            cv.put("manoObra",c.getManoObra());
            cv.put("jornales",c.getJornales());
            cv.put("insumos",c.getInsumos());
            cv.put("transporte",c.getTransporte());
            cv.put("otrosGastos",c.getOtrosGastos());
            cv.put("total",c.getTotal());
            cv.put("idUsuario",c.getIdUsuario());
            return (sql.insert("costeo",null,cv)>0);
        //}else { return false;}
    }

    /*
    public int buscarC(String cor){
        int x=0;
        lista=selectUsuario();
        for (Usuario co: lista) {
            if(co.getCorreo().equals(cor)){
                x++;
            }
        }

        return x;
    }

     */

    //Lista costeo
    // select con where idusuario = u.getId ??


    public ArrayList<Costeo> selectCosteo(){
        ArrayList<Costeo> list = new ArrayList<>();
        list.clear();
        Cursor cr=sql.rawQuery("select * from costeo",null);
        if(cr != null && cr.moveToFirst()){
            do{
                Costeo c = new Costeo();
                c.setId(cr.getInt(0));
                c.setTipo(cr.getString(1));
                c.setManoObra(cr.getInt(2));
                c.setJornales(cr.getInt(3));
                c.setInsumos(cr.getInt(4));
                c.setTransporte(cr.getInt(5));
                c.setOtrosGastos(cr.getInt(6));
                c.setTotal(cr.getInt(7));
                c.setIdUsuario(cr.getInt(8));

                list.add(c);

            }while (cr.moveToNext());
        }
        return list;
    }


    public ArrayList<Costeo> selectCosteoByUser(int idUs){
        ArrayList<Costeo> list = new ArrayList<>();
        list.clear();
        Cursor cr=sql.rawQuery("select * from costeo where idUsuario="+idUs+";",null);
        if(cr != null && cr.moveToFirst()){
            do{
                Costeo c = new Costeo();
                c.setId(cr.getInt(0));
                c.setTipo(cr.getString(1));
                c.setManoObra(cr.getInt(2));
                c.setJornales(cr.getInt(3));
                c.setInsumos(cr.getInt(4));
                c.setTransporte(cr.getInt(5));
                c.setOtrosGastos(cr.getInt(6));
                c.setTotal(cr.getInt(7));
                c.setIdUsuario(cr.getInt(8));

                list.add(c);

            }while (cr.moveToNext());
        }
        return list;
    }


    public ArrayList<Costeo> selectCosteoByUseryTipo(int idUs,String tp){
        ArrayList<Costeo> list = new ArrayList<>();
        list.clear();
        Cursor cr=sql.rawQuery("select * from costeo where idUsuario="+idUs+" and tipo=\""+tp+"\";",null);
        if(cr != null && cr.moveToFirst()){
            do{
                Costeo c = new Costeo();
                c.setId(cr.getInt(0));
                c.setTipo(cr.getString(1));
                c.setManoObra(cr.getInt(2));
                c.setJornales(cr.getInt(3));
                c.setInsumos(cr.getInt(4));
                c.setTransporte(cr.getInt(5));
                c.setOtrosGastos(cr.getInt(6));
                c.setTotal(cr.getInt(7));
                c.setIdUsuario(cr.getInt(8));

                list.add(c);

            }while (cr.moveToNext());
        }
        return list;
    }
    /*
    public ArrayList<Costeo> selectCosteoByUser(int us){
        ArrayList<Costeo> list = new ArrayList<>();
        list.clear();
        Cursor cr=sql.rawQuery("select * from costeo where idUsuario="+us,null);
        if(cr != null && cr.moveToFirst()){
            do{
                Costeo c = new Costeo();
                c.setId(cr.getInt(0));
                c.setTipo(cr.getString(1));
                c.setManoObra(cr.getInt(2));
                c.setJornales(cr.getInt(3));
                c.setInsumos(cr.getInt(4));
                c.setTransporte(cr.getInt(5));
                c.setOtrosGastos(cr.getInt(6));
                c.setTotal(cr.getInt(7));
                c.setIdUsuario(cr.getInt(8));

                list.add(c);

            }while (cr.moveToNext());
        }
        return list;
    }
*/

    //public Costeo getCosteoById

    public Costeo getCosteoById(int id){
        lista=selectCosteo();
        for (Costeo costeo: lista) {
            if(costeo.getId() == id){
                return costeo;
            }
        }
        return null;
    }

    public Costeo getCosteo(int v1, int v2, int v3, int v4, int v5, int v6, int uid){
        lista=selectCosteo();
        for (Costeo costeo: lista) {
            if(costeo.getManoObra() == v1 && costeo.getJornales() == v2 && costeo.getInsumos() == v3 &&costeo.getTransporte() == v4
             && costeo.getOtrosGastos() == v5 && costeo.getTotal() == v6 && costeo.getIdUsuario() == uid){
                return costeo;
            }
        }
        return null;
    }



}
