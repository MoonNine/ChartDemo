package com.exa.mj.chartdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

import java.util.List;

/**
 * Created by mj on 17/1/9.
 */
public class MyMarkerView extends MarkerView{

    private final TextView mTvPlateName;
    private final TextView mTvPlatePrice;
    private final TextView mTvPropertyName;
    private final TextView mTvPropertyPrice;
    private List<Integer> mPlateAvgList;
    private List<Integer> mPropertyAvgList;
    private Chart mChart;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mTvPlateName = (TextView) findViewById(R.id.tvPlateName);
        mTvPlatePrice = (TextView) findViewById(R.id.tvPlatePrice);
        mTvPropertyName = (TextView) findViewById(R.id.tvPropertyName);
        mTvPropertyPrice = (TextView) findViewById(R.id.tvPropertyPrice);
        mTvPlateName.setText("德裕大厦" + ":");
        mTvPropertyName.setText("别墅湖高教区" + ":");
    }

    @Override
    public void setChartView(Chart chart) {
        mChart = chart;
    }

    @Override
    public Chart getChartView() {
        return mChart;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        float x = e.getX();
        mTvPlatePrice.setText(String.valueOf(mPlateAvgList.get((int) x)));
        mTvPropertyPrice.setText(String.valueOf(mPropertyAvgList.get((int) x)));
        super.refreshContent(e, highlight);
    }

    /**
     * 最终确定MarkerView位置的方法
     * @param canvas
     * @param posx
     * @param posy
     */
    @Override
    public void draw(Canvas canvas, final float posx, final float posy){
        Chart chart = getChartView();
        // 处理Y
        float terY = posy - getHeight() / 2;
        if (terY < 0)
            terY = 0;
//        if (terY + getHeight() > chart.getBottom())
//            terY = chart.getBottom() - getHeight();
        // 处理X
        float terX = posx - Utils.convertDpToPixel(6) - getWidth();
        // 由于无法确定X轴0点的位置，只能根据具体的padding自己设置
        if (isMarker2Right(chart, terX)) {
            terX = posx + Utils.convertDpToPixel(6);
        }
        // translate to the correct position and draw
        canvas.translate(terX, terY);
        draw(canvas);
    }

    private boolean isMarker2Right(Chart chart, float terX) {
        if (chart != null)
            return terX < chart.getViewPortHandler().contentLeft();
        else
            return terX < getLeftPadding();
    }

    /**
     * 由于无法确定X轴0点的位置，只能根据具体的padding自己设置
     *
     * @return Marker从点右边出现的临界值
     */
    private float getLeftPadding() {
        return Utils.convertDpToPixel(30);
    }


    public void setData(List<Integer> plateAvgList, List<Integer> propertyAvgList) {
        mPlateAvgList = plateAvgList;
        mPropertyAvgList = propertyAvgList;
    }
}
