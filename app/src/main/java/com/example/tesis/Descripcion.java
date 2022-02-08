package com.example.tesis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Descripcion extends AppCompatActivity implements View.OnClickListener{

    TextView txt_click;
    Button btnReg;
    daoUsuario daou;
    Usuario u;
    int idU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        txt_click=findViewById(R.id.txt_click);
        btnReg=findViewById(R.id.btnReg);

        Bundle e= getIntent().getExtras();
        idU= e.getInt("idU");
        daou= new daoUsuario(this);
        u= daou.getUsuarioById(idU);

        txt_click.setOnClickListener(this);
        btnReg.setOnClickListener(this);
    }



    public void irWeb(String lk) {
        Uri uri= Uri.parse(lk);
        Intent intentN=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intentN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReg:
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("idU", u.getId());
                startActivity(i);
                finish();

                break;
            case R.id.txt_click:
                String direccion= "https://youtu.be/n1P9HoElXAE";
                irWeb(direccion);
                break;
            default:
                break;
        }
    }
}