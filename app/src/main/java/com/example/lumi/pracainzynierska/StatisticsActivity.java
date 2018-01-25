package com.example.lumi.pracainzynierska;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity{

    int[] amounts=new int[5];
    String[] categories = {"Rodzina","Finanse","Edukacja","Zdrowie","Kontakty"};
    DatabaseTasks db = new DatabaseTasks(this);
    List<PieEntry> pieEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        final Spinner spKind = (Spinner)findViewById(R.id.spStatKind);
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(StatisticsActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.statsKind));
        //set categories from <array-list> stored in strings.xml to adapter
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKind.setAdapter(priorityAdapter);
        spKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (spKind.getSelectedItem().toString())
                {
                    case "Liczba celów w kategorii":
                        setupAimsOnCategoryPieChart();
                        break;
                    case "Liczba zadań w kategorii":
                        setupTasksOnCategoryChart();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(getApplicationContext(),"Nie ma nic",Toast.LENGTH_LONG).show();
            }
        });
    }


    private void setupTasksOnCategoryChart()
    {
        pieEntries.clear();
        for(int i = 0 ; i < categories.length;i++)
        {
            amounts[i]=db.numberTasksInCategory(categories[i]);

            if(amounts[i]>0)
            pieEntries.add(new PieEntry(amounts[i],categories[i]));
        }
        displayChart("Zadania");
    }

    private void setupAimsOnCategoryPieChart()
    {
        pieEntries.clear();
        for(int i = 0 ; i < categories.length;i++)
        {
            amounts[i]=db.numberAimsInCategory(categories[i]);
            if(amounts[i]>0)
            pieEntries.add(new PieEntry(amounts[i],categories[i]));
        }
        displayChart("Cele");
    }

    private void displayChart(String kind)
    {
        //setting pieData sets
        PieDataSet pieDataSet = new PieDataSet(pieEntries,kind);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(18);

        //creating pie data with sets
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new MyValueFormatter());

        //creating new chart
        Chart chart = (Chart)findViewById(R.id.chart);
        chart.setData(pieData);
        chart.animateY(1000);
        chart.invalidate();
    }


    private class MyValueFormatter implements IValueFormatter {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

            return new DecimalFormat("###,###,##0").format(value);
        }
    }

}
