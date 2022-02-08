package com.example.tesis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Historial extends AppCompatActivity {

    ArrayList<Costeo> ls;
    ListView lista;
    daoCosteo daoc;
    daoUsuario daou;
    int id;
    Costeo c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        lista=findViewById(R.id.listCosto);

        Bundle e= getIntent().getExtras();
        id= e.getInt("idU");
        daoc= new daoCosteo(this);
        daou= new daoUsuario(this);

        ls= new ArrayList<>();
        ls.clear();
        ls = daoc.selectCosteoByUser(id);
        ArrayList<String> lf=new ArrayList<>();

        for(int i = 0; i < ls.size(); i++ ){
            lf.add("id:"+ls.get(i).getId()+" - Costeo"+(i+1));
        }



        ArrayAdapter adaptador= new ArrayAdapter(this, android.R.layout.simple_list_item_1,lf);

        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(parent.getContext(),"Datos Cargados",Toast.LENGTH_SHORT).show();

                int idc= ls.get(position).getId();
                Intent in = new Intent(parent.getContext(), AnalisisGeneral.class);

                in.putExtra("idC",idc);
                startActivity(in);
                finish();
            }
        });
    }


}