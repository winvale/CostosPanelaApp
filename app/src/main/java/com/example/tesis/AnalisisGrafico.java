package com.example.tesis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mindfusion.charting.AxesChart;
import com.mindfusion.charting.AxisLabelType;
import com.mindfusion.charting.BarSeries;
import com.mindfusion.charting.GridType;
import com.mindfusion.charting.LineSeries;
import com.mindfusion.charting.LineType;
import com.mindfusion.charting.ScatterType;

import java.util.ArrayList;
import java.util.Collections;

public class AnalisisGrafico extends AppCompatActivity {

    public java.text.DecimalFormat df = new java.text.DecimalFormat("#,###.##");

    private AxesChart chart;
    public TextView TitG1, TitG2, TitG3, TitG4;
    private BarChart barChart,barChart2,barChart3;
    private final String[] costos=new String[] {"Costo Calculado por el usuario","Costo Promedio departamental"};
    private final String[] precios=new String[] {"precio al productor calculado (usuario)","Precio promedio departamental"};
    //private final String[] variacion=new String[] {"%"};
    private final int[] num=new int[]{1,2};
    private final float[] num2=new float[]{1,2};
    /* private final float[] num3=new float[]{1,0}; */
    private final int[] color=new int[]{Color.rgb(255, 102, 0),  Color.rgb(193, 37, 82)};

    ArrayList<Costeo> ls,ly;
    int id;
    daoCosteo daoc;
    Costeo costeoG;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analisis_grafico);

        PieChart pastel = findViewById(R.id.pastel);
        PieChart pastel2 = findViewById(R.id.pastel2);
        chart = (AxesChart)findViewById(R.id.combi_chart);

        Bundle c= getIntent().getExtras();
        id= c.getInt("idC");
        daoc= new daoCosteo(this);
        costeoG= daoc.getCosteoById(id);

        TitG1=findViewById(R.id.TitGraph1); TitG1.setText(TitG1.getText()+" ("+costeoG.getTipo()+")");
        TitG2=findViewById(R.id.TitGraph2); TitG2.setText(TitG2.getText()+" ("+costeoG.getTipo()+")");
       /* TitG3=findViewById(R.id.TitGraph3); TitG3.setText(TitG3.getText()+" ("+costeoG.getTipo()+")"); */
        TitG4=findViewById(R.id.TitGraph4); TitG4.setText(TitG4.getText()+" ("+costeoG.getTipo()+")");

        num[0]=cost();num[1]=promedios_FDP();
        num2[0]=precio_pctr_SW();num2[1]=precio_pctr_FDP();
        float a=promedios_FDP(), b= cost();
        float VarPorCosto = ((a-b)/b)*100;
        float vari = (float) (Math.round(VarPorCosto*100.0)/100.0);

        /* num3[0]=Math.abs(vari);num3[1]=0;        */

        barChart =(BarChart)findViewById(R.id.barChart);
        barChart2=(BarChart)findViewById(R.id.barChart2);
        /*barChart3=(BarChart)findViewById(R.id.barChart3); */

        createChart();


        // Gráfico de pastel
        float mo =costeoG.getManoObra()*costeoG.getJornales();

        float x=costeoG.getInsumos();
        float y=costeoG.getTransporte();
        float z=costeoG.getOtrosGastos();
        float t=costeoG.getTotal();
        String moo ="Mano de obra "+ df.format((mo/t)*100)+"%";
        String pins="Insumos "+df.format((x/t)*100)+"%";
        String ptrs="Transporte "+df.format((y/t)*100)+"%";
        String potg="Otros Gastos "+df.format((z/t)*100)+"%";

        // String xd=df.format((x/t)*100); Se puede poner solo el numero pero sin signo "%"



        ArrayList<PieEntry> visitors =new ArrayList<>();
        visitors.add(new PieEntry(mo, moo));
        visitors.add(new PieEntry(costeoG.getInsumos(),pins));
        visitors.add(new PieEntry(costeoG.getTransporte(), ptrs));
        visitors.add(new PieEntry(costeoG.getOtrosGastos(), potg));

        PieDataSet pieDataSet = new PieDataSet( visitors, "Visitors");
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(14f);
        pieDataSet.setValueLineColor(getColor(R.color.CRIMSON));


        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);

        pastel.setData(pieData);
        pastel.getDescription().setEnabled(false);
        pastel.setCenterText("Costos");
        pastel.animate();


        //GP 2 varpor
        float vrpx=diferencia();
        String varpx=df.format(vrpx)+"%";

        ArrayList<PieEntry> visitors2 =new ArrayList<>();
        visitors2.add(new PieEntry(vrpx, varpx));

        PieDataSet pieDataSet2 = new PieDataSet( visitors2, "");
        pieDataSet2.setValueTextColor(Color.BLACK);
        pieDataSet2.setValueTextSize(14f);
        pieDataSet2.setValueLineColor(getColor(R.color.CRIMSON));


        pieDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData2 = new PieData(pieDataSet2);

        pastel2.setData(pieData2);
        pastel2.getDescription().setEnabled(false);
        pastel2.setCenterText("Variación del costo respecto a los datos de FEDEPANELA");
        pastel2.animate();


        //Gráfico de progresion

        ls= new ArrayList<>();
        ls = daoc.selectCosteoByUseryTipo(costeoG.getIdUsuario(),costeoG.getTipo());


        ArrayList gridStrokes = new ArrayList();
        gridStrokes.add(Color.rgb(0, 0, 0));
        chart.setGridStrokeColors(gridStrokes);


        float siz=ls.size();
        chart.setGridType(GridType.Crossed);
        chart.xAxisSettings.setMin(0f);
        chart.xAxisSettings.setMax(siz); //<-siz
        chart.xAxisSettings.setInterval(1f);
        chart.xAxisSettings.setLabelType(AxisLabelType.Custom);

        ArrayList xLabels = new ArrayList();


        for (int i=0; i<ls.size();i++){
            String ejx = "Costeo"+(i+1);
            Collections.addAll(xLabels, ejx );

        }
        //Collections.addAll(xLabels,"Costeo del usuario", "Costeo de fedepanela" );
        //Collections.addAll(xLabels,"Variación porcentual" );
        chart.xAxisSettings.setLabels(xLabels);



        BarSeries series1 = new BarSeries();


        ArrayList xData = new ArrayList();
        for(int i = 0; i < ls.size(); i++)
            xData.add((float)i);

        if(costeoG.getTipo().equals("Cultivo")){
            chart.setTitle("Cultivo");
            chart.yAxisSettings.setMin(0f);
            chart.yAxisSettings.setMax(15000000f);
            chart.yAxisSettings.setInterval(5000000f);
            chart.yAxisSettings.setLabelType(AxisLabelType.Scale);
            chart.yAxisSettings.setTitle("en miles");
/*
            float f1=costeoG.getTotal();
            float f2=13680760;
            Collections.addAll(yData1, f1,f2 );

            series1.setYData(yData1);

            ArrayList fillColors1 = new ArrayList();
            fillColors1.add(Color.rgb(217, 184, 162));
            series1.setFillColors(fillColors1);

            ArrayList strokeColors1 = new ArrayList();
            strokeColors1.add(Color.rgb(115, 133, 45));
            series1.setStrokeColors(strokeColors1);

            chart.addSeries(series1);

 */

        }
        if(costeoG.getTipo().equals("Proceso")){
            chart.setTitle("Proceso");
            chart.yAxisSettings.setMin(0f);
            chart.yAxisSettings.setMax(8000000f);
            chart.yAxisSettings.setInterval(2000000f);
            chart.yAxisSettings.setLabelType(AxisLabelType.Scale);
            chart.yAxisSettings.setTitle("en miles");
/*
            float f1=costeoG.getTotal();
            float f2=6366731;
            Collections.addAll(yData1, f1,f2 );

            series1.setYData(yData1);

            ArrayList fillColors1 = new ArrayList();
            fillColors1.add(Color.rgb(217, 184, 162));
            series1.setFillColors(fillColors1);

            ArrayList strokeColors1 = new ArrayList();
            strokeColors1.add(Color.rgb(115, 133, 45));
            series1.setStrokeColors(strokeColors1);

            chart.addSeries(series1);

 */


        }
        if(costeoG.getTipo().equals("Comercializacion") ){
            chart.setTitle("Comercializacion");
            chart.yAxisSettings.setMin(0f);
            chart.yAxisSettings.setMax(1500000f);
            chart.yAxisSettings.setInterval(100000f);
            chart.yAxisSettings.setLabelType(AxisLabelType.Scale);
            chart.yAxisSettings.setTitle("en miles");

/*
            float f1=costeoG.getTotal();
            float f2=347129;
            Collections.addAll(yData1, f1,f2 );

            series1.setYData(yData1);

            ArrayList fillColors1 = new ArrayList();
            fillColors1.add(Color.rgb(217, 184, 162));
            series1.setFillColors(fillColors1);

            ArrayList strokeColors1 = new ArrayList();
            strokeColors1.add(Color.rgb(115, 133, 45));
            series1.setStrokeColors(strokeColors1);

            chart.addSeries(series1);

 */
        }


/*
        chart.setTitle("");
        chart.yAxisSettings.setMin(0f);
        chart.yAxisSettings.setMax(100f);
        chart.yAxisSettings.setInterval(25f);
        chart.yAxisSettings.setLabelType(AxisLabelType.Scale);
        chart.yAxisSettings.setTitle("puntos porcentuales");

 */


        ArrayList yData1 = new ArrayList();

        for (int i=0; i<ls.size();i++){
           float ejy = ls.get(i).getTotal();
            Collections.addAll(yData1, ejy );
        }

        float vrp=diferencia();
        String varp=df.format(vrp);
        Collections.addAll(yData1, Float.parseFloat(varp) );

        series1.setYData(yData1);


        ArrayList fillColors1 = new ArrayList();
        fillColors1.add(Color.rgb(217, 184, 162));
        series1.setFillColors(fillColors1);



        ArrayList strokeColors1 = new ArrayList();
        strokeColors1.add(Color.rgb(115, 133, 45));
        series1.setStrokeColors(strokeColors1);
        chart.addSeries(series1);


        //linea

        LineSeries series2 = new LineSeries();
        series2.setScatterType(ScatterType.Circle);
        series2.setLineType(LineType.Line);
        series2.setScatterSize(20.0F);
        series2.setXData(xData);
        ArrayList<Float> yData2 = new ArrayList();

        for (int i=0; i<ls.size();i++){
            float ejy = ls.get(i).getTotal();
            Collections.addAll(yData2, ejy );

        }
        series2.setYData(yData2);
        ArrayList<Integer> scatterFillColors = new ArrayList();
        scatterFillColors.add(Color.argb(200, 255, 107, 125));
        series2.setScatterFillColors(scatterFillColors);
        series2.setStrokeWidth(3.0F);
        ArrayList<Integer> strokeColors2 = new ArrayList();
        strokeColors2.add(Color.rgb(243, 102, 34));
        series2.setStrokeColors(strokeColors2);
        this.chart.addSeries(series2);

        LineSeries series3 = new LineSeries();
        series3.setLineType(LineType.Area);

        chart.addSeries(series3);

    }

    //operaciones


    public float diferencia(){
        float dif=0, difp=0;
        if(precio_pctr_SW()>precio_pctr_FDP()){
            dif=precio_pctr_SW()-precio_pctr_FDP();
            difp = (dif/precio_pctr_FDP())*100;

        }else if(precio_pctr_FDP()>precio_pctr_SW()){
            dif=precio_pctr_SW()-precio_pctr_FDP();
            difp = (-dif/precio_pctr_FDP())*100;
        }

        return difp;
    }

    public String tipo_proc(){

        return costeoG.getTipo();
    }

    public float precio_pctr_SW(){

        String tp = costeoG.getTipo();
        float CTSW = cost();
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

        String tp = costeoG.getTipo();
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

    public int promedios_FDP(){
        int tipo_pc= 0;
        String tp = costeoG.getTipo();

        if(tp.equals("Cultivo")) {
            tipo_pc=13680760;
        }if (tp.equals("Proceso")){
            tipo_pc=6366731;
        }if(tp.equals("Comercializacion")){
            tipo_pc=347129;
        }

        return tipo_pc;
    }
    public int cost(){

        return costeoG.getTotal();
    }



    //=============================
    private Chart getSameChart(Chart chart, String description, int textColor, int background, int animeteY){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.getDescription().setTextColor(Color.WHITE);

        chart.animateY(animeteY);
        return chart;
    }
    private void legend(Chart chart ){
        Legend legend =chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextColor(Color.WHITE);

        ArrayList<LegendEntry> entries=new ArrayList<>();
        for (int i=0; i<costos.length;i++){
            LegendEntry entry=new LegendEntry();
            entry.formColor=color[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }

    private ArrayList<BarEntry>getBarEntries(){
        ArrayList<BarEntry>entries=new ArrayList<>();
        for (int i = 0; i < num.length; i++)
            entries.add(new BarEntry(i,num[i]));
        return entries;
    }
    private  void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(costos));
    }
    private  void axisX2(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(precios));
    }
    /*
    private  void axisX3(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(variacion));
    }

     */
    private void axisLeft(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
    }
    private  void axisRigtht (YAxis axis){
        axis.setEnabled(false);
    }

    private void legend2(Chart chart ){
        Legend legend =chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextColor(Color.WHITE);

        ArrayList<LegendEntry> entries=new ArrayList<>();
        for (int i=0; i<precios.length;i++){
            LegendEntry entry=new LegendEntry();
            entry.formColor=color[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }

    /*
    private void legend3(Chart chart ){
        Legend legend =chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextColor(Color.WHITE);

        ArrayList<LegendEntry> entries=new ArrayList<>();
        for (int i=0; i<variacion.length;i++){
            LegendEntry entry=new LegendEntry();
            entry.formColor=color[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
     */

    private ArrayList<BarEntry>getBarEntries2(){
        ArrayList<BarEntry>entries=new ArrayList<>();
        int pos=0;
        for (float i = 0; i < num2.length; i++){
            entries.add(new BarEntry(i,num2[pos]));
            pos+=1;}
        return entries;
    }
    /*
    private ArrayList<BarEntry>getBarEntries3(){
        ArrayList<BarEntry>entries=new ArrayList<>();
        int pos=0;
        for (float i = 0; i < num2.length; i++){
            entries.add(new BarEntry(i,num3[pos]));
            pos+=1;}
        return entries;
    }
     */

    private void createChart (){
        barChart=(BarChart)getSameChart(barChart,  "series",Color.RED, Color.CYAN, 4000);
        barChart.setDrawBarShadow(true);
        barChart.setDrawGridBackground(true);
        barChart.setData(getBarData());
        barChart.invalidate();

        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRigtht(barChart.getAxisRight());

        //Segundo Diagrama

        barChart2=(BarChart)getSameChart(barChart2,  "series",Color.RED, Color.CYAN, 4000);
        barChart2.setDrawBarShadow(true);
        barChart2.setDrawGridBackground(true);
        barChart2.setData(getBarData2());
        barChart2.invalidate();


        axisX2(barChart2.getXAxis());
        axisLeft(barChart2.getAxisLeft());
        axisRigtht(barChart2.getAxisRight());

        //Tercer diagrama

        /*
        barChart3=(BarChart)getSameChart(barChart3,  "series",Color.RED, Color.CYAN, 4000);
        barChart3.setDrawBarShadow(true);
        barChart3.setDrawGridBackground(true);
        barChart3.setData(getBarData3());
        barChart3.invalidate();


        axisX3(barChart3.getXAxis());
        axisLeft(barChart3.getAxisLeft());
        axisRigtht(barChart3.getAxisRight());

         */
    }
    private DataSet getData(DataSet dataSet){
        dataSet.setColors(color);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10);
        return dataSet;
    }
    private BarData getBarData () {
        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries(),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData =new BarData(barDataSet);
        barData.setBarWidth(0.60f);
        return barData;
    }
    private BarData getBarData2 () {
        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries2(),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData =new BarData(barDataSet);
        barData.setBarWidth(0.60f);
        return barData;
    }
    /*
    private BarData getBarData3 () {
        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries3(),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData =new BarData(barDataSet);
        barData.setBarWidth(0.60f);
        return barData;
    }
     */




    public void agen(View vista){
        Intent i = new Intent(this, AnalisisGeneral.class);
        i.putExtra("idC",costeoG.getId());

        startActivity(i);
        finish();
    }
    public void agra(View vista){
        Intent i = new Intent(this, AnalisisGrafico.class);
        i.putExtra("idC",costeoG.getId());

        startActivity(i);
        finish();
    }
    public void aesp(View vista){
        Intent i = new Intent(this, AnalisisEspecifico.class);
        i.putExtra("idC",costeoG.getId());

        startActivity(i);
        finish();
    }
    public void volver(View vista){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("idU",costeoG.getIdUsuario());
        startActivity(i);
    }
}