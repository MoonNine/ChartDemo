package com.exa.mj.chartdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String[] MONTH = {
            "1月",
            "2月",
            "3月",
            "4月",
            "5月",
            "6月",
            "7月",
            "8月",
            "9月",
            "10月",
            "11月",
            "12月"};

    final Integer[] PLATE = {
            50726,
            48418,
            56623,
            42277,
            59890,
            61592,
            65033,
            62148,
            62148,
            0,
            0,
            0};

    final Integer[] PROPERTY = {
            70534,
            34823,
            56403,
            0,
            64381,
            0,
            76279,
            0,
            0,
            0,
            0,
            0};

    private LineChart mLineChart;
    private List<String> mMonthList;
    private List<Integer> mPlateAvgList;
    private List<Integer> mPropertyAvgList;
    private final static int LINE_WIDTH = 1; // 折线宽度
    public static final int CIRCLE_RADIUS = 3; // 坐标点的半径
    public static final int HIGH_LIGHT_LINE_WIDTH = 1; // 点击坐标点后的指示线宽度
    public static final int GRID_LINE_WIDTH = 1; // 虚线宽度
    private Button mButton;
    int i;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLineChart = (LineChart) findViewById(R.id.community_chart);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mLineChart.post(new Runnable() {
                    @Override
                    public void run() {
                        Easing.EasingOption[] values = Easing.EasingOption.values();
                        if (i < values.length) {
//                            mLineChart.animateX(3000, values[i++]);
//                            Log.i("```", values[i - 1].name());
                        } else {
                            i = 0;
                        }
                        mLineChart.centerViewToAnimated(12, 0, YAxis.AxisDependency.LEFT, 2000);
                    }
                });
            }
        });
        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mLineChart.post(new Runnable() {
                    @Override
                    public void run() {
                        Easing.EasingOption[] values = Easing.EasingOption.values();
                        if (i < values.length) {
                            mLineChart.animateX(3000, values[i++]);
                            Log.i("```", values[i - 1].name());
                        } else {
                            i = 0;
                        }
                    }
                });
            }
        });

        initData();
        initChart();
    }

    private void initChart() {
        // 设置数据
        ArrayList<Entry> propertyList = new ArrayList<>();
        ArrayList<Entry> plateList = new ArrayList<>();

        for (int index = 0; index < mMonthList.size(); index++) {
            propertyList.add(new Entry(index,mPropertyAvgList.get(index)));
            plateList.add(new Entry(index,mPlateAvgList.get(index)));
        }

        LineDataSet propertyData = new LineDataSet(propertyList, "小区均价");
        LineDataSet plateData = new LineDataSet(plateList, "参考均价");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(propertyData);
        dataSets.add(plateData);

        LineData lineData = new LineData(dataSets);
        mLineChart.setData(lineData);

        // 设置折线颜色
        plateData.setColor(Color.parseColor("#FF8833"));
        propertyData.setColor(Color.parseColor("#00AE66"));
        // 线条宽度
        plateData.setLineWidth(LINE_WIDTH);
        propertyData.setLineWidth(LINE_WIDTH);
        // 坐标点半径
        plateData.setCircleRadius(CIRCLE_RADIUS);
        propertyData.setCircleRadius(CIRCLE_RADIUS);
        // 坐标点半径
        plateData.setCircleColor(Color.parseColor("#FF8833"));
        propertyData.setCircleColor(Color.parseColor("#00AE66"));
        // 坐标点不为空心
        plateData.setDrawCircleHole(false);
        propertyData.setDrawCircleHole(false);
        // 不显示具体坐标值
        plateData.setDrawValues(false);
        propertyData.setDrawValues(false);
        // 点击坐标圆点后的水平轴不显示
        plateData.setDrawHorizontalHighlightIndicator(false);
        propertyData.setDrawHorizontalHighlightIndicator(false);
        // 点击坐标点后的纵坐标线颜色
        plateData.setHighLightColor(Color.parseColor("#818B9D"));
        propertyData.setHighLightColor(Color.parseColor("#818B9D"));
        // 点击坐标点后的纵坐标线宽度
        plateData.setHighlightLineWidth(HIGH_LIGHT_LINE_WIDTH);
        propertyData.setHighlightLineWidth(HIGH_LIGHT_LINE_WIDTH);
        // 图表描述为空
        Description desc = new Description();
        desc.setText("");
        mLineChart.setDescription(desc);

        // 设置X轴的坐标显示字符串
        IAxisValueFormatter xFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value < 0 || value > mMonthList.size())
                    return "";
                return mMonthList.get((int) value);
            }
        };
        XAxis xAxis = mLineChart.getXAxis();
        // 设置X坐标显示
        xAxis.setValueFormatter(xFormatter);
        // 限定X坐标间隔
        xAxis.setGranularity(1F);
        // X坐标显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 不画横坐标线
        xAxis.setDrawGridLines(false);
        // X轴标识颜色
        xAxis.setTextColor(Color.parseColor("#999999"));
        // 不画坐标轴线
        xAxis.setDrawAxisLine(false);

        YAxis axisLeft = mLineChart.getAxisLeft();
        // Y轴显示
        axisLeft.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String format = new DecimalFormat("#.0").format(value / 10000F);
                if (Double.valueOf(format) == 0)
                    return "0万";
                return format + "万";
            }
        });
        // Y坐标字符串颜色
        axisLeft.setTextColor(Color.parseColor("#999999"));
        // 数量，强制显示4个
        axisLeft.setLabelCount(4, true);
        // 不画0线
        axisLeft.setDrawZeroLine(false);
        // 左边坐标线颜色
        axisLeft.setGridColor(Color.parseColor("#E6E6E6"));
        // 显示为虚线
//        float[] intervals = {1,0,1,0};
//        axisLeft.setGridDashedLine(new DashPathEffect(intervals,1));
        axisLeft.enableGridDashedLine(10, 10, 0);
        axisLeft.setZeroLineColor(Color.parseColor("#E6E6E6"));
        axisLeft.setZeroLineWidth(GRID_LINE_WIDTH);
        axisLeft.setGridLineWidth(GRID_LINE_WIDTH);
        // 不画坐标轴线
        axisLeft.setDrawAxisLine(false);
        // 设置纵坐标最小值
        axisLeft.setAxisMinimum(0);
        // 避免坐标值重复,如果太小的话还是会重复的
        axisLeft.setGranularity(1000F);
        // 设置纵坐标最大值
        int max = Math.max(Collections.max(mPlateAvgList), Collections.max(mPropertyAvgList));
        if (max < 40000F)
            axisLeft.setAxisMaximum(40000F);
        else
            axisLeft.setAxisMaximum(max);

        // 关闭右边的坐标轴
        YAxis axisRight = mLineChart.getAxisRight();
        axisRight.setEnabled(false);

        // 表格背景
        mLineChart.setBackgroundColor(Color.parseColor("#FFFFFF"));
        // 设置底部图例和X轴的距离
        mLineChart.setExtraBottomOffset(14);
        // 移动到最后
//        mLineChart.moveViewToX(mMonthList.size() - 1);
        // 关闭缩放
        mLineChart.setScaleXEnabled(false);
        mLineChart.setScaleYEnabled(false);
        // 横坐标放大两倍
        mLineChart.zoom(2, 0, 0, 0);

        // 底部说明
        Legend legend = mLineChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextColor(Color.parseColor("#999999"));
        legend.setFormSize(6);
        legend.setFormToTextSpace(8);
        legend.setXOffset(-4);
        legend.setXEntrySpace(50);
        MyMarkerView markerView = new MyMarkerView(this, R.layout.custom_marker_view);
        markerView.setData(mPlateAvgList, mPropertyAvgList);
        markerView.setChartView(mLineChart);
        mLineChart.setMarker(markerView);
    }

    private void initData() {
        mMonthList = Arrays.asList(MONTH);
        mPlateAvgList = Arrays.asList(PLATE);
        mPropertyAvgList = Arrays.asList(PROPERTY);
    }
}
