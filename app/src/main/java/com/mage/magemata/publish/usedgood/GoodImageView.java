package com.mage.magemata.publish.usedgood;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/9/10.
 */

public class GoodImageView extends android.support.v7.widget.AppCompatImageView {
    private int originalWidth;
    private int originalHeight;


    public GoodImageView(Context context) {
        super(context);
    }


    public GoodImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public GoodImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setOriginalSize(int originalWidth, int originalHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
    }


    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (originalWidth > 0 && originalHeight > 0) {
            float ratio = (float) originalWidth / (float) originalHeight;

            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            // TODO: 现在只支持固定宽度
            if (width > 0) {
                height = (int) ((float) width / ratio);
            }

            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}

