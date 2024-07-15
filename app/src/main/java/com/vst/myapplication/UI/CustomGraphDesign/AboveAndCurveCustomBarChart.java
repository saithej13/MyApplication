package com.vst.myapplication.UI.CustomGraphDesign;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

//import com.example.personalproject.R;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.vst.myapplication.R;

import java.util.ArrayList;

public class AboveAndCurveCustomBarChart extends BarChartRenderer {
    // used for both TOP CORNER CURVES and the VALUES & IMAGE ON THE TOP of the bar

    private Paint myPaint;
    private ArrayList<Integer> myColors;
    private ArrayList<Integer> growPercentage;
    Context context;
    private RectF mBarShadowRectBuffer = new RectF();

    private int mRadius;
    private boolean imageDisplay=true;
    private int abovetextColor=0;
    private int barPosition=1;

    public AboveAndCurveCustomBarChart(Context context, BarDataProvider chart, ChartAnimator animator,
                                       ViewPortHandler viewPortHandler, ArrayList<Integer> myColors,
                                       ArrayList<Integer> growPercentage) {
        super(chart, animator, viewPortHandler);
        this.myPaint = new Paint();
        this.myColors = myColors;
        this.context = context;
        this.growPercentage = growPercentage;
    }
    public AboveAndCurveCustomBarChart(Context context, BarDataProvider chart, ChartAnimator animator,
                                       ViewPortHandler viewPortHandler,
                                       ArrayList<Integer> growPercentage) {
        super(chart, animator, viewPortHandler);
        this.myPaint = new Paint();
        this.context = context;
        this.growPercentage = growPercentage;
    }

    // to show whether the image display is needed or not
    public void disaplayImage(boolean imageDisplay) { this.imageDisplay = imageDisplay; }

    //to keep the text color display as needed
    public void aboveValueTextColor(int textColor) { this.abovetextColor = textColor; }
    public void textNeededBarPosition(int barPosition) { this.barPosition = barPosition-1; }

    @Override
    public void drawValues(Canvas c) {
        super.drawValues(c);
        int colorIndex = 0;
        int Listindex=0;
        for (int i = 0; i < mChart.getBarData().getDataSetCount(); i++) {
            BarBuffer buffer = mBarBuffers[i];
            float left, right, top, bottom;

            for (int j = 0; j < buffer.buffer.length * mAnimator.getPhaseX(); j += 4) {
//                myPaint.setColor(myColors.get(colorIndex % myColors.size()));  // Use modulo to avoid IndexOutOfBoundsException
                left = buffer.buffer[j];
                right = buffer.buffer[j + 2];
                top = buffer.buffer[j + 1];
                bottom = buffer.buffer[j + 3];

                // Apply custom rendering only to the second dataset growPercentage
                if (i == barPosition) {
//                        int index = (int) buffer.buffer[j]; // Assuming x-value is the index
                    int index = (int) buffer.buffer[j] % growPercentage.size(); // Use modulo to ensure a valid index
//                        int valueAtIndex = growPercentage.get(index); // Get the value from ArrayList
//                    int valueAtIndex = growPercentage.get(Listindex);
                    int valueAtIndex=0;
                    if (Listindex >= 0 && Listindex < growPercentage.size()) {
                        valueAtIndex = growPercentage.get(Listindex);
                    }
                    String customText = String.valueOf(valueAtIndex);
                    Listindex++;

//                        drawBitmapAndTextAboveBar(c, left, top, right, bottom, "5%");
//                        drawBitmapAndTextAboveBar(c, left, top, right, bottom, customText+"%");
                    if (valueAtIndex!=0)
                        drawBitmapAndTextAboveBar(c, left, top, right, bottom,valueAtIndex);
                }
                colorIndex++;
            }
        }
    }

    private void drawBitmapAndTextAboveBar(Canvas c, float left, float top, float right, float bottom,
                                           int valueAtIndex) {

        int setDiableCustom=25;
        if (mViewPortHandler.isInBoundsTop(top) && mViewPortHandler.isInBoundsBottom(bottom)
                && mViewPortHandler.isInBoundsLeft(left+setDiableCustom) &&
                mViewPortHandler.isInBoundsRight(right-setDiableCustom)){

            Bitmap bitmap;
            int textColor=0;

            int convertedValue = Math.abs(valueAtIndex);
            String customText= convertedValue+"%";

            if (valueAtIndex>0){
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_arrow_up_1x);
                textColor = Color.parseColor("#3EA81A");
            }else{
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pink_arrow_down_1x);
                textColor =Color.parseColor("#E57676");
            }
            // Calculate the center position above the bar
            float centerX = (left + right) / 2f;
            float centerY = top - 50f; // Adjust the vertical position as needed

            // Draw the bitmap above the bar
//            c.drawBitmap(bitmap, centerX - bitmap.getWidth() / 2f, centerY - bitmap.getHeight(), null);

            // Draw custom text below the bitmap
            Paint textPaint = new Paint();
            textPaint.setTextSize(30f); // Adjust text size as needed
            textPaint.setFakeBoldText(true); // Set text to bold

            if(abovetextColor!=0)
                textPaint.setColor(abovetextColor);
            else if (textColor!=0)
                textPaint.setColor(textColor);
            else textPaint.setColor(Color.BLACK);

            if(imageDisplay)
                c.drawBitmap(bitmap, centerX - bitmap.getWidth() / 2f, centerY - bitmap.getHeight()+10, null);

            float textWidth = textPaint.measureText(customText);
            float textX = centerX - textWidth / 2f;
            float textY = centerY + 40f; // Adjust the vertical position of the text

            c.drawText(customText, textX, textY, textPaint);
        }
    }



    private void drawBitmapAndTextAboveBar(Canvas c, float left, float top, float right,
                                           float bottom, String customText) {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_arrow_up_1andhalfx);

        // Calculate the center position above the bar
        float centerX = (left + right) / 2f;
        float centerY = top - 50f; // Adjust the vertical position as needed

        // Draw the bitmap above the bar
        c.drawBitmap(bitmap, centerX - bitmap.getWidth() / 2f, centerY - bitmap.getHeight(), null);

        // Draw custom text below the bitmap
        Paint textPaint = new Paint();
//            textPaint.setColor(Color.BLACK);
        textPaint.setColor(Color.parseColor("#E57676"));
        textPaint.setTextSize(40f); // Adjust text size as needed
        textPaint.setFakeBoldText(true); // Set text to bold

        float textWidth = textPaint.measureText(customText);
        float textX = centerX - textWidth / 2f;
        float textY = centerY + 40f; // Adjust the vertical position of the text

        c.drawText(customText, textX, textY, textPaint);
    }

    public void setRadius(int mRadius) {
        this.mRadius = mRadius;
    }

    //original code
    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());
        mBarBorderPaint.setColor(dataSet.getBarBorderColor());
        mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getBarBorderWidth()));
        mShadowPaint.setColor(dataSet.getBarShadowColor());
        boolean drawBorder = dataSet.getBarBorderWidth() > 0f;

        float phaseX = mAnimator.getPhaseX();
        float phaseY = mAnimator.getPhaseY();

        if (mChart.isDrawBarShadowEnabled()) {
            mShadowPaint.setColor(dataSet.getBarShadowColor());

            BarData barData = mChart.getBarData();

            float barWidth = barData.getBarWidth();
            float barWidthHalf = barWidth / 2.0f;
            float x;

            int i = 0;
            double count = Math.min(Math.ceil((int) (double) ((float) dataSet.getEntryCount() * phaseX)), dataSet.getEntryCount());
            while (i < count) {

                BarEntry e = dataSet.getEntryForIndex(i);

                x = e.getX();

                mBarShadowRectBuffer.left = x - barWidthHalf;
                mBarShadowRectBuffer.right = x + barWidthHalf;

                trans.rectValueToPixel(mBarShadowRectBuffer);

                if (!mViewPortHandler.isInBoundsLeft(mBarShadowRectBuffer.right)) {
                    i++;
                    continue;
                }

                if (!mViewPortHandler.isInBoundsRight(mBarShadowRectBuffer.left))
                    break;

                mBarShadowRectBuffer.top = mViewPortHandler.contentTop();
                mBarShadowRectBuffer.bottom = mViewPortHandler.contentBottom();

                c.drawRoundRect(mBarRect, mRadius, mRadius, mShadowPaint);
                i++;
            }
        }

        // initialize the buffer
        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setDataSet(index);
        buffer.setInverted(mChart.isInverted(dataSet.getAxisDependency()));
        buffer.setBarWidth(mChart.getBarData().getBarWidth());

        buffer.feed(dataSet);

        trans.pointValuesToPixel(buffer.buffer);

        boolean isSingleColor = dataSet.getColors().size() == 1;

        if (isSingleColor) {
            mRenderPaint.setColor(dataSet.getColor());
        }

        int j = 0;
        while (j < buffer.size()) {

            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2])) {
                j += 4;
                continue;
            }

            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j]))
                break;

            if (!isSingleColor) {
                // Set the color for the currently drawn value. If the index
                // is out of bounds, reuse colors.
                mRenderPaint.setColor(dataSet.getColor(j / 4));
            }

            if (dataSet.getGradientColor() != null) {
                GradientColor gradientColor = dataSet.getGradientColor();
                mRenderPaint.setShader(new LinearGradient(
                        buffer.buffer[j],
                        buffer.buffer[j + 3],
                        buffer.buffer[j],
                        buffer.buffer[j + 1],
                        gradientColor.getStartColor(),
                        gradientColor.getEndColor(),
                        Shader.TileMode.MIRROR));
            }

            if (dataSet.getGradientColors() != null) {
                mRenderPaint.setShader(new LinearGradient(
                        buffer.buffer[j],
                        buffer.buffer[j + 3],
                        buffer.buffer[j],
                        buffer.buffer[j + 1],
                        dataSet.getGradientColor(j / 4).getStartColor(),
                        dataSet.getGradientColor(j / 4).getEndColor(),
                        Shader.TileMode.MIRROR));
            }
            Path path2 = roundRect(new RectF(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2], buffer.buffer[j + 3]), mRadius, mRadius, true, true, false, false);
//                Path path2 = roundRect(new RectF(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2], buffer.buffer[j + 3]), mRadius, mRadius, true, true, true, true);
            c.drawPath(path2, mRenderPaint);
            if (drawBorder) {
                Path path = roundRect(new RectF(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2], buffer.buffer[j + 3]), mRadius, mRadius, true, true, false, false);
                c.drawPath(path, mBarBorderPaint);
            }
            j += 4;
        }
    }
    private Path roundRect(RectF rect, float rx, float ry, boolean tl,
                           boolean tr, boolean br, boolean bl) {
        float top = rect.top;
        float left = rect.left;
        float right = rect.right;
        float bottom = rect.bottom;
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) rx = width / 2;
        if (ry > height / 2) ry = height / 2;
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        path.moveTo(right, top + ry);
        if (tr)
            path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
        else {
            path.rLineTo(0, -ry);
            path.rLineTo(-rx, 0);
        }
        path.rLineTo(-widthMinusCorners, 0);
        if (tl)
            path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
        else {
            path.rLineTo(-rx, 0);
            path.rLineTo(0, ry);
        }
        path.rLineTo(0, heightMinusCorners);

        //edited code
//            if (bl)
//                path.rQuadTo(0, ry, rx, ry);//bottom-left corner
//            else {
//                path.rLineTo(0, ry);
//                path.rLineTo(rx, 0);
//            }
//
//            path.rLineTo(widthMinusCorners, 0);
//            if (br)
//                path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
//            else {
//                path.rLineTo(rx, 0);
//                path.rLineTo(0, -ry);
//            }


        //real code
        if (bl)
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
        else {
            path.rLineTo(0, ry);
            path.rLineTo(rx, 0);
        }

        path.rLineTo(widthMinusCorners, 0);
        if (br)
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        else {
            path.rLineTo(rx, 0);
            path.rLineTo(0, -ry);
        }

        path.rLineTo(0, -heightMinusCorners);

        path.close();//Given close, last lineto can be removed.

        return path;
    }
}