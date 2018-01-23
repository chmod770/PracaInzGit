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

        Button btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupPieChart()
    {
        int[] amounts=new int[5];
        String[] categories = {"Pieniądze","Edukacja","Zdrowie","Kontakty","inne"};
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

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Liczba celów różnych kategorii");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);

        Chart chart = (Chart)findViewById(R.id.chart);
        chart.setData(pieData);
        chart.invalidate();
    }
}
