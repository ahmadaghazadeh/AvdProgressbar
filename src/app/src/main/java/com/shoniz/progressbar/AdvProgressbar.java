package com.shoniz.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by aghazadeh.a on 12/12/2016.
 */

public class AdvProgressbar extends FrameLayout {


    private LinearLayout progressBar;
    private int percent;
    TextView textView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AdvProgressbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if(!isInEditMode())
            init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public AdvProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(!isInEditMode())
            init(context,attrs);
    }


    public AdvProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())
            init(context,attrs);
    }
    public AdvProgressbar(Context context) {
        super(context);
        this.percent=0;
        if(!isInEditMode())
            init(context,R.drawable.progress_bar_background,R.drawable.progress_bar_progress, Gravity.START,Gravity.END,0,0,0,0,0,14,"",context.getResources().getColor(R.color.colorAccent));
    }

    private void init(Context context,int resIdBackground,int resIdProgressbar,int gravityProgress,int gravityText,
                      int percent,int leftPadding,int rightPadding,int topPadding,int bottomPadding,int fontSize,String fontName,int textColor)
    {
        progressBar=new LinearLayout(context);
        textView=new TextView(context);
        textView.setGravity(gravityText);
        textView.setTextSize(fontSize);
        textView.setPadding(leftPadding,topPadding,rightPadding,bottomPadding);
        progressBar.setGravity(gravityProgress);
        progressBar.setBackgroundResource(resIdProgressbar);
        if(!fontName.equals("")){
            Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/"+fontName+".ttf");
            textView.setTypeface(type);
        }
        textView.setTextColor(textColor);
        LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        progressBar.setLayoutParams(layoutParams);
        this.setPercent(percent);
        this.addView(progressBar);
        this.addView(textView);
        this.setBackgroundResource(resIdBackground);
    }
    private  void init(Context context,AttributeSet attrs)
    {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.AdvProgressbar, 0, 0);
        int resBackgroundId = a.getResourceId(R.styleable.AdvProgressbar_res_background_id,R.drawable.progress_bar_background);
        int percent = a.getInt(R.styleable.AdvProgressbar_percent,0);
        int resProgressBarId = a.getResourceId(R.styleable.AdvProgressbar_res_progressbar_bar_id,
                R.drawable.progress_bar_progress);
        int leftPadding= a.getInt(R.styleable.AdvProgressbar_text_padding_left,0);
        int rightPadding= a.getInt(R.styleable.AdvProgressbar_text_padding_right,0);
        int topPadding= a.getInt(R.styleable.AdvProgressbar_text_padding_top,0);
        int bottomPadding= a.getInt(R.styleable.AdvProgressbar_text_padding_bottom,0);
        int padding= a.getInt(R.styleable.AdvProgressbar_text_padding,0);
        int fontSize= a.getInt(R.styleable.AdvProgressbar_text_font_size,14);
        int fontColor= a.getColor(R.styleable.AdvProgressbar_text_color,context.getResources().getColor(R.color.colorAccent) );
        String fontName= a.getString(R.styleable.AdvProgressbar_text_font);
        if(padding!=0){
            if(leftPadding==0) leftPadding=padding;
            if(rightPadding==0) rightPadding=padding;
            if(topPadding==0) topPadding=padding;
            if(bottomPadding==0) bottomPadding=padding;
        }
        a.recycle();
        init(context,resBackgroundId,resProgressBarId, Gravity.START, Gravity.END,percent,leftPadding,rightPadding,topPadding,bottomPadding,fontSize,fontName,fontColor);
    }

    public void setPercent(final int percent)
    {


        if(percent >=100){
            this.percent=100;
        }
        this.percent=percent;
        int pWidth = AdvProgressbar.this.getMeasuredWidth();
        if(pWidth!=0){
            LayoutParams layoutParams=new LayoutParams(pWidth*this.percent/100,ViewGroup.LayoutParams.MATCH_PARENT);
            progressBar.setLayoutParams(layoutParams);
        }
    }

    public void setText(String text)
    {
       textView.setText(text);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        progressBar.getLayoutParams().width=parentWidth*percent/100;
    }
}
