package com.example.tesis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AnalisisGeneral extends AppCompatActivity {
    TextView TextBase,TextTotTrab,TextTotalCosto,TextSubTit,TextSalida;
    private ImageView image;
    public java.text.DecimalFormat df = new java.text.DecimalFormat("#,###.######################################################");
    int idCos;
    daoCosteo daoc;
    Costeo costeoA;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analisis_general);

        TextBase=findViewById(R.id.TextBase);
        TextTotTrab=findViewById(R.id.TextTotTrab);
        TextTotalCosto=findViewById(R.id.TextCostoTotal);
        TextSubTit=findViewById(R.id.TextSubTit);
        image= findViewById(R.id.imageV);
        TextSalida = findViewById(R.id.TextSalida);

        Bundle b= getIntent().getExtras();
        idCos = b.getInt("idC");
        daoc= new daoCosteo(this);
        costeoA= daoc.getCosteoById(idCos);


        int a1 = costeoA.getManoObra();
        int a2 = costeoA.getJornales();
        double cost= costeoA.getTotal();
        double mobra=MOTotal();
        String tip_pc = tipo_proc();
        TextSubTit.setText("Cálculo para costos de "+tip_pc);
        TextTotTrab.setText("Con un salario de: $"+df.format(a1)+", y "+df.format(a2)+" trabajadores; el costo total asociado a la mano de obra es de:  $"+df.format(mobra));
        TextTotalCosto.setText("Con lo cual, el costo total de la actividad productiva es de: $"+df.format(cost));
    }

    /*
    public double cost(){

        int cosid = getIntent().getExtras().getInt("IdC");
        int a1 = getIntent().getExtras().getInt("v1");
        int a2 = getIntent().getExtras().getInt("v2");
        int a3 = getIntent().getExtras().getInt("v3");
        int a4 = getIntent().getExtras().getInt("v4");
        int a5 = getIntent().getExtras().getInt("v5");


        return (a1 * a2) + a3 + a4 + a5;
    }

     */

    public double MOTotal(){

        double x = costeoA.getManoObra() * costeoA.getJornales();

        return (x) ;
    }

    @SuppressLint("SetTextI18n")
    public void masCostoso(View vista){
        int i = mascos();
        double cos = costeoA.getTotal();
        if(i==1){
            double var= MOTotal();
            double percent = (var/cos)*100;
            double perc = Math.round(percent*100.0)/100.0;
            TextSalida.setText("La mano de obra representa la mayor proporción de costos en cuanto" +
                    " al proceso; siendo el "+perc+"% del total del costo. Esto se puede deber a que" +
                    " o bien el promedio de salarios de los empleados es un poco elevado, o que el número de trabajadores" +
                    " implicados está elevando los costos");
            image.setImageResource(R.drawable.i1);
        }
        if(i==2){
            int varx= costeoA.getInsumos();
            double percent = (varx/cos)*100;
            double perc = Math.round(percent*100.0)/100.0;
            TextSalida.setText("Los insumos representan la mayor proporción de costos en cuanto" +
                    " al proceso; siendo el "+perc+"% del total del costo. Esto se puede deber a que" +
                    " la maquinaria, lote o materia prima inmersos en el proceso, presentan valores muy elevados" +
                    " lo cual incrementa los costos del proceso.");
            image.setImageResource(R.drawable.i2);
        }
        if(i==3){
            int vary= costeoA.getTransporte();
            double percent = (vary/cos)*100;
            double perc = Math.round(percent*100.0)/100.0;
            TextSalida.setText("El transporte representa la mayor proporción de costos en cuanto" +
                    " al proceso; siendo el "+perc+"% del total del costo. Esto se puede deber a " +
                    "multiples causas; como el incremento de los precios del combustible, la escasez" +
                    " de transportistas o inclusive situaciones de crísis o paro");

            image.setImageResource(R.drawable.i3);
        }
        if(i==4){
            int varz= costeoA.getOtrosGastos();
            double percent = (varz/cos)*100;
            double perc = Math.round(percent*100.0)/100.0;
            TextSalida.setText("La mayor proporción de costos en cuanto" +
                    " al proceso se debe a otros costos vários; representando el "+perc+"% del total del costo." +
                    " Esto se puede deber a multiples causas; como nuevas situaciones tributarias o eventos" +
                    " inesperados de crísis o paro");
            image.setImageResource(R.drawable.i4);
        }

    }
    public String tipo_proc(){

        return costeoA.getTipo();
    }
    public int mascos(){
        int a3 = costeoA.getInsumos();
        int a4 = costeoA.getTransporte();
        int a5 = costeoA.getOtrosGastos();
        double a12 =MOTotal();
        int i=0;
        if(a12 > a3 && a12 > a4 && a12 > a5){
            i=1;
        }
        if(a3 > a12 && a3 > a4 && a3 > a5){
            i=2;
        }
        if(a4 > a3 && a4 > a12 && a4 > a5){
            i=3;
        }
        if(a5 > a3 && a5 > a4 && a5 > a12){
            i=4;
        }

        return i;
    }

    @SuppressLint("SetTextI18n")
    public void CompDepartamental(View vista){
        image.setImageResource(R.drawable.i6);
        double cost = costeoA.getTotal();
        double CTSW = cost;


        if(costeoA.getTipo().equals("Cultivo")) {
            int PFDP=1200;
            double PSW = CTSW/11400;
            int CTFDP= 13680760;
            double VarPorCosto = ((CTFDP-cost)/cost)*100;

            if(CTSW>CTFDP){
                TextSalida.setText("Los costos del proceso en el cultivo son mayores al promedio del departamento;"+
                        "los costos de cultivo promedio son de: $"+df.format(CTFDP)+" mientras los costos del presente son de: " +
                        "$"+df.format(CTSW)+". Luego, los beneficios pueden ser menores con base en el precio de producción promedio por kilogramo" +
                        " del departamento es menor; siendo: $"+Math.round(PFDP*100.0)/100.0+ "; cuando el precio calculado es de: $"+Math.round(PSW*100.0)/100.0+
                        ". Finalmente, la variación porcentual presenta que el costo del presente es "+Math.round(-VarPorCosto*100.0)/100.0+"% " +
                        "mayor que el costo promedio del departamento.");

            }else {
                TextSalida.setText("Los costos del proceso en el cultivo son menores al promedio del departamento."+
                        "Los costos de cultivo promedio son de: $"+df.format(CTFDP)+" mientras los costos del presente son de: " +
                        "$"+df.format(CTSW)+". Luego, los beneficios pueden ser mayores con base en el precio de producción promedio por kilogramo" +
                        " del departamento es mayor; siendo: $"+Math.round(PFDP*100.0)/100.0+ "; cuando el precio calculado es de: $"+Math.round(PSW*100.0)/100.0+
                        ". Finalmente, la variación porcentual presenta que el costo del presente es "+Math.round(VarPorCosto*100.0)/100.0+"% " +
                        "menor que el costo promedio del departamento.");
            }

        }if (costeoA.getTipo().equals("Proceso")){
            double PFDP=558.48;
            double PSW = CTSW/11400;
            int CTFDP=6366731;
            double VarPorCosto = ((CTFDP-cost)/cost)*100;

            if(CTSW>CTFDP){
                TextSalida.setText("Los costos inmersos en el proceso panelero son mayores al promedio del departamento;"+
                        "los costos promedio en el departamento son de: $"+df.format(CTFDP)+" mientras los costos del presente son de: " +
                        "$"+df.format(CTSW)+". Luego, los beneficios pueden ser menores con base en el precio de producción promedio por kilogramo" +
                        " del departamento es menor; siendo: $"+Math.round(PFDP*100.0)/100.0+ "; cuando el precio calculado es de: $"+Math.round(PSW*100.0)/100.0+
                        ". Finalmente, la variación porcentual presenta que el costo del presente es "+Math.round(-VarPorCosto*100.0)/100.0+"% " +
                        "mayor que el costo promedio del departamento.");

            }else {
                TextSalida.setText("Los costos inmersos en el proceso panelero son menores al promedio del departamento;"+
                        "los costos promedio en el departamento son de: $"+df.format(CTFDP)+" mientras los costos del presente son de: " +
                        "$"+df.format(CTSW)+". Luego, los beneficios pueden ser mayores con base en el precio de producción promedio por kilogramo " +
                        " del departamento es mayor; siendo: $"+Math.round(PFDP*100.0)/100.0+ "; cuando el precio calculado es de: $"+Math.round(PSW*100.0)/100.0+
                        ". Finalmente, la variación porcentual presenta que el costo del presente es "+Math.round(VarPorCosto*100.0)/100.0+"% " +
                        "menor que el costo promedio del departamento.");
            }

        }if(costeoA.getTipo().equals("Comercializacion")){

            double PFDP=30.45;
            double PSW = CTSW/11400;
            int CTFDP=347129;
            double VarPorCosto = ((CTFDP-cost)/cost)*100;

            if(CTSW>CTFDP){
                TextSalida.setText("Los costos inmersos en la comercialización son mayores al promedio del departamento;"+
                        "los costos promedio en el departamento son de: $"+df.format(CTFDP)+" mientras los costos del presente son de: " +
                        "$"+df.format(CTSW)+". Luego, los beneficios pueden ser menores con base en el precio de comercialización promedio por kilogramo" +
                        " del departamento es menor; siendo: $"+Math.round(PFDP*100.0)/100.0+ "; cuando el precio calculado es de: $"+Math.round(PSW*100.0)/100.0+
                        ". Finalmente, la variación porcentual presenta que el costo del presente es "+Math.round(-VarPorCosto*100.0)/100.0+"% " +
                        "mayor que el costo promedio del departamento.");

            }else {
                TextSalida.setText("Los costos inmersos en la comercialización son menores al promedio del departamento;"+
                        "los costos promedio en el departamento son de: $"+df.format(CTFDP)+" mientras los costos del presente son de: " +
                        "$"+df.format(CTSW)+". Luego, los beneficios pueden ser mayores con base en el precio de comercialización promedio por kilogramo " +
                        " del departamento es mayor; siendo: $"+Math.round(PFDP*100.0)/100.0+ "; cuando el precio calculado es de: $"+Math.round(PSW*100.0)/100.0+
                        ". Finalmente, la variación porcentual presenta que el costo del presente es "+Math.round(VarPorCosto*100.0)/100.0+"% " +
                        "menor que el costo promedio del departamento.");
            }

        }

    }


    public void agen(View vista){

        Intent i = new Intent(this, AnalisisGeneral.class);
        i.putExtra("idC",costeoA.getId());

        startActivity(i);
        finish();
    }
    public void agra(View vista){

        Intent i = new Intent(this, AnalisisGrafico.class);
        i.putExtra("idC",costeoA.getId());

        startActivity(i);
        finish();
    }
    public void aesp(View vista){

        Intent i = new Intent(this, AnalisisEspecifico.class);
        i.putExtra("idC",costeoA.getId());

        startActivity(i);
        finish();
    }
    public void volver(View vista){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("idU",costeoA.getIdUsuario());
        startActivity(i);
    }
}