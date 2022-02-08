package com.example.tesis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class Grafico extends AppCompatActivity {

    int a=cost();
    int b=tipo_proc();
    private BarChart barChart;
    private String[] meses=new String[] {"Costo Calculado","Costo Promedio"};
    private int[] num=new int[]{a,b};
    private int[] color=new int[]{Color.BLACK,Color.GREEN};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);

        barChart =(BarChart)findViewById(R.id.barChart);
        createChart();
    }
    public int tipo_proc(){
        int tipo_pc= 0;
        int tp = getIntent().getExtras().getInt("tp");

        if(tp==1) {
            tipo_pc= 13680760;
        }if (tp==2){
            tipo_pc=6366731;
        }if(tp==3){
            tipo_pc=347129;
        }

        return tipo_pc;
    }
    public int cost(){
        int a1 = getIntent().getExtras().getInt("v1");
        int a2 = getIntent().getExtras().getInt("v2");
        int a3 = getIntent().getExtras().getInt("v3");
        int a4 = getIntent().getExtras().getInt("v4");
        int a5 = getIntent().getExtras().getInt("v5");


        return (a1 * a2) + a3 + a4 + a5;
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
        for (int i=0; i<meses.length;i++){
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
        axis.setValueFormatter(new IndexAxisValueFormatter(meses));
    }
    private void axisLeft(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
    }
    private  void axisRigtht (YAxis axis){
        axis.setEnabled(false);
    }

    private void createChart (){
        barChart=(BarChart)getSameChart(barChart,  "series",Color.RED, Color.CYAN, 4000);
        barChart.setDrawBarShadow(true);
        barChart.setDrawGridBackground(true);
        barChart.setData(getBarData());
        barChart.invalidate();

        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRigtht(barChart.getAxisRight());
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

    public void agen(View vista){
        int tp = getIntent().getExtras().getInt("tp");
        int v1 = getIntent().getExtras().getInt("v1");
        int v2 = getIntent().getExtras().getInt("v2");
        int v3 = getIntent().getExtras().getInt("v3");
        int v4 = getIntent().getExtras().getInt("v4");
        int v5 = getIntent().getExtras().getInt("v5");
        Intent i = new Intent(this, AnalisisGeneral.class);
        i.putExtra("tp",tp);
        i.putExtra("v1",v1);
        i.putExtra("v2",v2);
        i.putExtra("v3",v3);
        i.putExtra("v4",v4);
        i.putExtra("v5",v5);
        startActivity(i);
    }
    public void agra(View vista){
        int tp = getIntent().getExtras().getInt("tp");
        int v1 = getIntent().getExtras().getInt("v1");
        int v2 = getIntent().getExtras().getInt("v2");
        int v3 = getIntent().getExtras().getInt("v3");
        int v4 = getIntent().getExtras().getInt("v4");
        int v5 = getIntent().getExtras().getInt("v5");
        Intent i = new Intent(this, AnalisisGrafico.class);
        i.putExtra("tp",tp);
        i.putExtra("v1",v1);
        i.putExtra("v2",v2);
        i.putExtra("v3",v3);
        i.putExtra("v4",v4);
        i.putExtra("v5",v5);
        startActivity(i);
    }
    public void aesp(View vista){
        int tp = getIntent().getExtras().getInt("tp");
        int v1 = getIntent().getExtras().getInt("v1");
        int v2 = getIntent().getExtras().getInt("v2");
        int v3 = getIntent().getExtras().getInt("v3");
        int v4 = getIntent().getExtras().getInt("v4");
        int v5 = getIntent().getExtras().getInt("v5");
        Intent i = new Intent(this, AnalisisEspecifico.class);
        i.putExtra("tp",tp);
        i.putExtra("v1",v1);
        i.putExtra("v2",v2);
        i.putExtra("v3",v3);
        i.putExtra("v4",v4);
        i.putExtra("v5",v5);
        startActivity(i);
    }
    public void volver(View vista){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}