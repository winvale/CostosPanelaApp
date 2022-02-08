package com.example.tesis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AnalisisEspecifico extends AppCompatActivity {
    public java.text.DecimalFormat df = new java.text.DecimalFormat("#,###.##");

    int id;
    daoCosteo daoc;
    Costeo costeoE;


    TextView SalDif, SalAnPrecio, SalCompPrecio;
    ImageView iae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analisis_especifico);

        Bundle e= getIntent().getExtras();
        id= e.getInt("idC");
        daoc= new daoCosteo(this);
        costeoE= daoc.getCosteoById(id);

        SalDif=findViewById(R.id.SalidaDifCost);
        SalAnPrecio=findViewById(R.id.SalidaAnPrecio);
        SalCompPrecio=findViewById(R.id.SalidaCompPrecio);
        iae=findViewById(R.id.Imageae);

        SalDif.setText(diferencia());
        SalAnPrecio.setText(precio());
        SalCompPrecio.setText(precioComp());

        //

    }


    public float precio_pctr_SW(){

        String tp = costeoE.getTipo();
        float CTSW = costeoE.getTotal();
        float PSW=0;

        if(tp.equals("Cultivo")) {
            float PFDP=1200;
            PSW = CTSW/11400;
        }if (tp.equals("Proceso")){
            double PFDP=558.48;
            PSW = CTSW/11400;
        }if(tp.equals("Comercializacion")){
            double PFDP=30.45;
            PSW = CTSW/11400;
        }

        return PSW;
    }
    public float precio_pctr_FDP(){

        String tp = costeoE.getTipo();
        float PFDP= (float) 0.0;

        if(tp.equals("Cultivo")) {
            PFDP= (float) 1200.00;
        }if (tp.equals("Proceso")){
            PFDP= (float) 558.48;
        }if(tp.equals("Comercializacion")){
            PFDP= (float) 30.45;
        }

        return PFDP;
    }
    public float precio_pagado_pctr_FDP(){

        String tp = costeoE.getTipo();
        float PPFDP= (float) 0.0;

        if(tp.equals("Cultivo")) {
            PPFDP= (float) 1139.69;
        }if (tp.equals("Proceso")){
            PPFDP= (float) 530.39;

        }if(tp.equals("Comercializacion")){
            PPFDP= (float) 28.92;
        }

        return PPFDP;
    }

    public String diferencia(){
        float dif=0;
        String x="";
        if(precio_pctr_SW()>precio_pctr_FDP()){
            dif=precio_pctr_SW()-precio_pctr_FDP();
            float difp = (dif/precio_pctr_FDP())*100;
           x= "El costo por kilogramo es $"+df.format(dif)+
           " mayor al costo de esta misma rama en el promedio departamental, con lo cual se encuentra "+
           df.format(difp)+"% por encima del promedio.";
        }else if(precio_pctr_FDP()>precio_pctr_SW()){
            dif=precio_pctr_SW()-precio_pctr_FDP();
            float difp = (-dif/precio_pctr_FDP())*100;
            x= "El costo por kilogramo es $"+df.format(-dif)+
                    " menor al costo de esta misma rama en el promedio departamental, con lo cual se encuentra "+
                    df.format(difp)+"% por debajo del promedio.";
        }else{
            x="El costo por kilogramo en la rama es igual al costo promedio del departamento";
        }

        return x;
    }

    public String precio(){
        String st="";
        float dif=0;
        if(precio_pagado_pctr_FDP() > precio_pctr_SW()){
            iae.setImageResource(R.drawable.e2);
            dif=  precio_pctr_SW() - precio_pagado_pctr_FDP();
            float difp = (-dif/precio_pagado_pctr_FDP());
            float difp2= difp/2;
            float precv= (precio_pctr_SW()*difp2)+precio_pctr_SW();
            float total= 11400*precv;
            float precv2= (precio_pctr_SW()*difp)+precio_pctr_SW();
            float total2= 11400*precv2;
            st="Se presenta una situación favorable, donde el precio de venta podría incrementarse hasta en un "+
            df.format(difp*100)+"%." +
                    "Con una estimación de ganancias del "+df.format(difp*100/2)+"%, " +
                    " el precio de venta sería de $"+df.format(precv)+" ; bajo este precio, los ingresos por hectarea" +
                    " serían de $"+df.format(total)+"." +
                    "\n\nSi se quiere igualar los precios de venta nacionales, el precio estaría en $"+df.format(precv2)+
                    " con unos ingresos por hectarea de $"+df.format(total2)+".";
        }
        else if( precio_pctr_SW() > precio_pagado_pctr_FDP()){
            iae.setImageResource(R.drawable.e1);
            dif=precio_pctr_SW() - precio_pagado_pctr_FDP();
            float difp = (dif/precio_pagado_pctr_FDP());
            float prec=(precio_pctr_SW()*difp)+precio_pctr_SW();
            st="Se presenta una situación desfavorable; ya que los costos de produccion, encontrandose por encima de la media," +
                    " generan perdidas si se vende al precio promedio nacional. El precio de equilibrio de esta producción se" +
                    " encuentra por encima del promedio; siendo de $" +df.format(prec)+" por kilogramo"+
                    "\n\nSe sugiere realizar un análisis para la reducción de los costos en al menos un "+df.format(difp*100)+"% " +
                    " para dejar de presentar perdidas.";
        }else{
            iae.setImageResource(R.drawable.e3);
            st="Se encuentra produciendo en un punto de equilibrio donde los ingrsos se igualarían a los costos";
        }

        return st;
    }

    public String precioComp(){
        String st="";
        float dif=0;

        if(precio_pagado_pctr_FDP() > precio_pctr_SW()){
            dif=precio_pctr_SW() - precio_pagado_pctr_FDP();
            float difp = (-dif/precio_pagado_pctr_FDP())*100;
            st="Los costos por kilogramo son inferiores al precio de venta promedio nacional en $"+df.format(-dif)+
            " suponiendo una diferencia del "+df.format(difp)+"%. Esto supone mayores posibilidades de ganancia";
        }else if( precio_pctr_SW() > precio_pagado_pctr_FDP()){
            dif=precio_pctr_SW() - precio_pagado_pctr_FDP();
            float difp = (dif/precio_pagado_pctr_FDP())*100;
            st="Los costos por kilogramo son superiores al precio de venta promedio nacional en $"+df.format(dif)+
            " suponiendo una diferencia del "+df.format(difp)+"%. Esto supone una producción a sobrecosto, incurriendo en perdidas";
        }else{
            st="Los costos por kilogramo son iguales al precio de venta promedio nacional";
        }

        return st;
    }


    public void agen(View vista){
        Intent i = new Intent(this, AnalisisGeneral.class);
        i.putExtra("idC",costeoE.getId());

        startActivity(i);
        finish();
    }
    public void agra(View vista){
        Intent i = new Intent(this, AnalisisGrafico.class);
        i.putExtra("idC",costeoE.getId());

        startActivity(i);
        finish();
    }
    public void aesp(View vista){
        Intent i = new Intent(this, AnalisisEspecifico.class);
        i.putExtra("idC",costeoE.getId());

        startActivity(i);
        finish();
    }
    public void volver(View vista){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("idU",costeoE.getIdUsuario());
        startActivity(i);
        finish();
    }
}