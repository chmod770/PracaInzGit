package com.example.lumi.pracainzynierska;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        setupPieChart();
    }

    private void setupPieChart()
    {
        int[] amounts=new int[5];
        String[] categories = {"Rodzina","Finanse","Edukacja","Zdrowie","Kontakty"};
        DatabaseTasks db = new DatabaseTasks(this);
        for(int i = 0 ; i < categories.length;i++)
        {
            amounts[i]=db.numberAimsInCategory(categories[i]);
        }
        List<PieEntry> pieEntries = new ArrayList<>();
        for(int i=0; i < categories.length;i++)
        {
            if(amounts[i]>0)
            pieEntries.add(new PieEntry(amounts[i],categories[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Cele");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(18);


        PieData pieData = new PieData(pieDataSet);

        Chart chart = (Chart)findViewById(R.id.chart);
        chart.setData(pieData);
        chart.animateY(1000);
        chart.invalidate();
    }
}
