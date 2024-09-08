package com.example.plp_app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plp_app.api.ApiClient;
import com.example.plp_app.api.ApiService;
import com.example.plp_app.api.ChartCol;
import com.example.plp_app.api.ChuyenSanXuatResponse;
import com.example.plp_app.api.DashboardResponse;
import com.example.plp_app.api.ModuleData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ChartActivity extends AppCompatActivity {

    private TextView datePickerTextView;
    private TextView taskName;
    private BarChart chart;
    private String dateToSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        datePickerTextView = findViewById(R.id.date_picker_textview);
        taskName = findViewById(R.id.taskName_tv);
        // Set the current date as default
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        datePickerTextView.setText("Ngày: " + sdf.format(calendar.getTime()));

        // Set onClickListener to show DatePickerDialog
        datePickerTextView.setOnClickListener(v -> showDatePickerDialog(calendar));


        chart = findViewById(R.id.barChart);

        initChart();
    }
    private void initChart(){
        List<String> moduleIds = new ArrayList<>();
        moduleIds.add("3964b456fa27405b929ac6aa2f13ae3d");
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Call<DashboardResponse> call = apiService.getDashboardData(moduleIds, "", dateToSave);
//        Call<DashboardResponse> call = apiService.getDashBoard(dateToSave);
        call.enqueue(new Callback<DashboardResponse>() {
                    @Override
                    public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<ModuleData> data = response.body().getData();
                            if (!data.isEmpty()) {
                                setupBarChart(data.get(0));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DashboardResponse> call, Throwable t) {
                        // Handle error
                    }
                });
    }

    private void setupBarChart(ModuleData moduleData) {
        if (moduleData == null || moduleData.getChartCols() == null || moduleData.getChartCols().isEmpty()) {
            showNoDataAvailable();
            return;
        }

        taskName.setText("Biểu đồ sản lượng " + moduleData.getModuleName());
        List<BarEntry> entries = new ArrayList<>();
        List<String> xAxisLabels = new ArrayList<>();

        for (int i = 0; i < moduleData.getChartCols().size(); i++) {
            ChartCol chartCol = moduleData.getChartCols().get(i);
            entries.add(new BarEntry(i, chartCol.getQuantity()));
            xAxisLabels.add(chartCol.getTime());
        }

        BarDataSet dataSet = new BarDataSet(entries, "Sản lượng theo giờ");
        dataSet.setColor(Color.BLUE);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);
        chart.setData(barData);


        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(4);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < xAxisLabels.size()) {
                    return xAxisLabels.get(index);
                }
                return "";
            }
        });
        xAxis.setLabelRotationAngle(0f);
        xAxis.setAvoidFirstLastClipping(true);


        chart.setExtraBottomOffset(10f);

        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisRight().setEnabled(false);


        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);

        chart.setVisibleXRangeMaximum(4);
        chart.moveViewToX(0);


        chart.setFitBars(true);
        chart.animateY(1000);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        chart.invalidate();
    }


    private void showNoDataAvailable() {
        chart.setNoDataText("Không có dữ liệu");
        chart.setNoDataTextColor(Color.RED);
        chart.invalidate();
    }

    private void showDatePickerDialog(final Calendar calendar) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                AlertDialog.THEME_HOLO_LIGHT,  // Apply the desired theme
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    SimpleDateFormat sdfToSave = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    datePickerTextView.setText("Ngày: " + sdf.format(calendar.getTime()));

                    dateToSave = sdfToSave.format(calendar.getTime());
                    if (chart.getData() != null) {
                        chart.getData().clearValues();
                        chart.clear();
                        chart.invalidate(); // Đảm bảo rằng biểu đồ được reset
                    }
                    initChart();
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
