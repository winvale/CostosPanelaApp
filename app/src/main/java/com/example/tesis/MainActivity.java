package com.example.tesis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RadioButton r1,r2,r3;
    EditText var1,var2,var3,var4,var5;
    Button btnForm, btnHist;
    TextView text_clk;
    daoCosteo daoc;
    int idUsuario;
    Usuario u;
    daoUsuario daou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        r1=findViewById(R.id.radioButton);
        r2=findViewById(R.id.radioButton2);
        r3=findViewById(R.id.radioButton3);
        var1=findViewById(R.id.mano_obra);
        var2=findViewById(R.id.num_trabaj);
        var3=findViewById(R.id.insumos);
        var4=findViewById(R.id.transporte);
        var5=findViewById(R.id.otros_gastos);

        btnForm=findViewById(R.id.btnForm);
        text_clk=findViewById(R.id.text_click);
        btnHist=findViewById(R.id.btnHis);

        btnForm.setOnClickListener(this);
        text_clk.setOnClickListener(this);
        btnHist.setOnClickListener(this);

        daoc= new daoCosteo(this);

        Bundle b= getIntent().getExtras();
        idUsuario = b.getInt("idU");
        daou= new daoUsuario(this);
        u= daou.getUsuarioById(idUsuario);

    }

    public int tipo_proceso(){

        int tipo=0;
        if(r1.isChecked()) {
            tipo = 1;
        }if (r2.isChecked()){
            tipo=2;
        }if(r3.isChecked()){
            tipo=3;
        }

        return tipo;
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnForm:
                //boton que se presiona al terminar de llenar los datos del formulario

                int tp=tipo_proceso();
                int v1=Integer.parseInt(var1.getText().toString());
                int v2=Integer.parseInt(var2.getText().toString());
                int v3=Integer.parseInt(var3.getText().toString());
                int v4=Integer.parseInt(var4.getText().toString());
                int v5=Integer.parseInt(var5.getText().toString());
                int v6=(v1 * v2) + v3 + v4 + v5;
                Costeo cos = new Costeo();
                String tpo="";

                if(tp==1){
                    tpo="Cultivo";
                }
                else if(tp==2){
                    tpo="Proceso";
                }
                else if(tp==3){
                    tpo="Comercializacion";
                }

                cos.setTipo(tpo);
                cos.setManoObra(v1);
                cos.setJornales(v2);
                cos.setInsumos(v3);
                cos.setTransporte(v4);
                cos.setOtrosGastos(v5);
                cos.setTotal(v6);
                cos.setIdUsuario(u.getId());

                if(daoc.insertarCosteo(cos)){
                    Toast.makeText(this,"Datos registrados",Toast.LENGTH_SHORT).show();
                    Costeo cs=daoc.getCosteo(v1,v2,v3,v4,v5,v6,u.getId());
                    Intent main2 = new Intent(this, AnalisisGeneral.class);

                    main2.putExtra("idC",cs.getId());
                    main2.putExtra("idU",u.getId());
/*
                    main2.putExtra("tp",tp);
                    main2.putExtra("v1",v1);
                    main2.putExtra("v2",v2);
                    main2.putExtra("v3",v3);
                    main2.putExtra("v4",v4);
                    main2.putExtra("v5",v5);

 */
                    startActivity(main2);


                }else{
                    Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.text_click:
                //boton que lleva a la especificacion del sistema
                Intent main = new Intent(this, Descripcion.class);
                main.putExtra("idU",u.getId());
                startActivity(main);
                finish();
                break;

            case R.id.btnHis:
                //boton que lleva a el historial de costeos
                Intent h = new Intent(this, Historial.class);
                h.putExtra("idU",u.getId());
                startActivity(h);
                finish();

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    /*
    public void btnmenu(View vista) {
        int tp=tipo_proceso();
        int v1=Integer.parseInt(var1.getText().toString());
        int v2=Integer.parseInt(var2.getText().toString());
        int v3=Integer.parseInt(var3.getText().toString());
        int v4=Integer.parseInt(var4.getText().toString());
        int v5=Integer.parseInt(var5.getText().toString());
        Intent main2 = new Intent(this, AnalisisGeneral.class);
        main2.putExtra("tp",tp);
        main2.putExtra("v1",v1);
        main2.putExtra("v2",v2);
        main2.putExtra("v3",v3);
        main2.putExtra("v4",v4);
        main2.putExtra("v5",v5);
        startActivity(main2);


    }

     */


    public void historialCosteos(View vista) {
        Costeo cos = new Costeo();
        int tp =tipo_proceso();
        String tpo="";
        if(tp==1){
            tpo="Cultivo";
        }
        else if(tp==2){
            tpo="Proceso";
        }
        else if(tp==3){
            tpo="Comercializacion";
        }

        cos.setTipo(tpo);
        cos.setManoObra(Integer.parseInt(var1.getText().toString()));
        cos.setJornales(Integer.parseInt(var2.getText().toString()));
        cos.setInsumos(Integer.parseInt(var3.getText().toString()));
        cos.setTransporte(Integer.parseInt(var4.getText().toString()));
        cos.setOtrosGastos(Integer.parseInt(var5.getText().toString()));

        Intent main = new Intent(this, Descripcion.class);
        startActivity(main);
    }
    /*
    public void btnAcercaModelo(View vista) {

    }
     */
}