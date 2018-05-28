package com.uinbdg.kas.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.uinbdg.kas.R;

/**
 * Created by Blank Dev on 03/12/16.
 */
public class CustomFontTextView extends TextView {
    public CustomFontTextView(Context context) {
        super(context);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Typeface will not be applied in the layout editor.
        if (isInEditMode()) {
            return;
        }

        //To customize more items, add your objects under values->attrs
        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
        String font = styledAttrs.getString(R.styleable.CustomFontTextView_textfonts);

        styledAttrs.recycle();

        //set to font
        if (font != null) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "" + font);
            setTypeface(typeface);
        } else{
//            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Existence_Light.otf");
//            setTypeface(typeface);
        }
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
// ************** Use This Library **************************
//
// <com.blankdev.rchelper.CustomFont.CustomFontTextView
//      android:layout_width="wrap_content"
//      android:layout_height="wrap_content"
//      android:textSize="20sp"
//      android:text="Hello World!"
//      app:textfonts="YourFont.ttf"/>
//
//
// **********************************************************