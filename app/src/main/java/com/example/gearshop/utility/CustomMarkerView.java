package com.example.gearshop.utility;
import android.content.Context;
import android.widget.TextView;

import com.example.gearshop.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class CustomMarkerView extends MarkerView {

    private TextView tvContent;

    private String[] labels;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.tvContent);
    }
    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(labels[(int)e.getX()]);
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        // Adjust the tooltip's offset to position it properly
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}


