package com.ecamotion.floatinghintedittext;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Frontado on 23/05/2015.
 */
public class FloatingHintEditText extends LinearLayout {

    private EditText editText;
    private TextView textView;
    private LayoutParams editTextParams;
    private LayoutParams textViewParams;
    private Animation animateUp, animateDown;

    public FloatingHintEditText(Context context) {
        super(context);
        createView(null);
    }

    public FloatingHintEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        createView(attrs);
    }

    public FloatingHintEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createView(attrs);
    }

    public void createView(AttributeSet attrs){
        Context context = getContext();
        //Initialize views
        editText = new EditText(context);
        textView = new TextView(context);
        //Load animations
        animateUp = AnimationUtils.loadAnimation(context, R.anim.animate_up);
        animateDown = AnimationUtils.loadAnimation(context, R.anim.animate_down);

        // Create Default Layout
        editTextParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        textViewParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);

        editText.setLayoutParams(editTextParams);
        textView.setLayoutParams(textViewParams);
        setOrientation(LinearLayout.VERTICAL);
        createDefaultView();

        if (attrs != null) {
            createCustomLayout(attrs);
        }
        addView(textView);
        addView(editText);
        textView.setVisibility(View.INVISIBLE);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(textView.getVisibility() == View.VISIBLE && charSequence.length() == 0)
                    hideFloatingHint();
                else if(textView.getVisibility() == View.INVISIBLE && charSequence.length() > 0)
                    showFloatingHint();
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void createDefaultView() {
        editText.setGravity(Gravity.LEFT | Gravity.TOP);
        textView.setGravity(Gravity.LEFT | Gravity.BOTTOM);

        editText.setTextColor(Color.BLACK);
        textView.setTextColor(Color.BLACK);

        Context context = getContext();
        editText.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        textView.setTextAppearance(context, android.R.style.TextAppearance_Small);

        textView.setPadding(5, 2, 5, 2);
    }

    private void createCustomLayout(AttributeSet attrs){

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FloatingHintEditText, 0, 0);

        //Edit Text Attributes
        String text = typedArray.getString(R.styleable.FloatingHintEditText_text);
        ColorStateList textColor = typedArray.getColorStateList(R.styleable.FloatingHintEditText_textColor);
        int textSize = typedArray.getInt(R.styleable.FloatingHintEditText_textSize, 18);
        String textTypefaceName = typedArray.getString(R.styleable.FloatingHintEditText_textTypeface);
        int textStyle = typedArray.getInt(R.styleable.FloatingHintEditText_textStyle, Typeface.NORMAL);
        int textGravity = typedArray.getInt(R.styleable.FloatingHintEditText_textGravity, Gravity.LEFT);
        Drawable textBackground = typedArray.getDrawable(R.styleable.FloatingHintEditText_textBackground);
        boolean isPassword = typedArray.getBoolean(R.styleable.FloatingHintEditText_isPassword, false);

        //Text View Attributes
        String floatingHintText = typedArray.getString(R.styleable.FloatingHintEditText_floatingHintText);
        ColorStateList floatingHintTextColor = typedArray.getColorStateList(R.styleable.FloatingHintEditText_floatingHintTextColor);
        int floatingHintTextSize = typedArray.getInt(R.styleable.FloatingHintEditText_floatingHintTextSize, 15);
        String floatingHintTextTypefaceName = typedArray.getString(R.styleable.FloatingHintEditText_floatingHintTextTypeface);
        int floatingHintTextStyle = typedArray.getInt(R.styleable.FloatingHintEditText_floatingHintTextStyle, Typeface.NORMAL);
        int floatingHintTextGravity = typedArray.getInt(R.styleable.FloatingHintEditText_floatingHintTextGravity, Gravity.LEFT);
        Drawable floatingHintTextBackground = typedArray.getDrawable(R.styleable.FloatingHintEditText_floatingHintTextBackground);

        typedArray.recycle();

        //Set Edit Text Attributes
        if(floatingHintText != null) setTextHint(floatingHintText);
        if(text != null) setText(text);
        if(textColor != null) setTextColor(textColor);
        setTextSize(textSize);
        setTextTypeface(textTypefaceName, textStyle);
        setTextGravity(textGravity);
        if(textBackground != null) setTextBackground(textBackground);
        setPassword(isPassword);

        //Set Text View Attributes
        if(floatingHintText != null) setFloatingHintText(floatingHintText);
        if(floatingHintTextColor != null) setFloatingHintTextColor(floatingHintTextColor);
        setFloatingHintTextSize(floatingHintTextSize);
        setFloatingHintTextTypeface(floatingHintTextTypefaceName, floatingHintTextStyle);
        setFloatingHintTextGravity(floatingHintTextGravity);
        if(floatingHintTextBackground != null) setFloatingHintTextBackground(floatingHintTextBackground);
    }

    public void hideFloatingHint(){
        textView.setVisibility(View.INVISIBLE);
        textView.startAnimation(animateDown);
    }

    public void showFloatingHint(){
        textView.setVisibility(View.VISIBLE);
        textView.startAnimation(animateUp);
    }

    public void setText(String text){
        editText.setText(text);
    }

    public void setTextHint(String textHint){
        editText.setHint(textHint);
    }

    private void setTextColor(ColorStateList textColor) {
        editText.setTextColor(textColor);
    }

    public void setTextSize(int textSize){
        editText.setTextSize(textSize);
    }

    public void setTextTypeface(String textTypefaceName, int textStyle){
        try {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),
                    textTypefaceName);
            editText.setTypeface(typeface);
        } catch (Exception e) {
            editText.setTypeface(null, textStyle);
        }
    }

    public void setTextGravity(int textGravity){
        editText.setGravity(textGravity);
    }

    public void setTextBackground(Drawable textBackground){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            editText.setBackground(textBackground);
        } else{
            editText.setBackgroundDrawable(textBackground);
        }
    }

    public void setPassword(boolean isPassword){
        if(isPassword){
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void setFloatingHintText(String textFloatingHint){
        textView.setText(textFloatingHint);
    }

    public void setFloatingHintTextColor(ColorStateList floatingHintTextColor){
        textView.setTextColor(floatingHintTextColor);
    }

    public void setFloatingHintTextSize(int floatingHintTextSize){
        textView.setTextSize(floatingHintTextSize);
    }

    public void setFloatingHintTextTypeface(String floatingHintTextTypefaceName, int floatingHintTextStyle){
        try {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),
                    floatingHintTextTypefaceName);
            editText.setTypeface(typeface);
        } catch (Exception e) {
            editText.setTypeface(null, floatingHintTextStyle);
        }
    }

    public void setFloatingHintTextGravity(int floatingHintTextGravity){
        textView.setGravity(floatingHintTextGravity);
    }

    public void setFloatingHintTextBackground(Drawable floatingHintTextBackground){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            textView.setBackground(floatingHintTextBackground);
        } else{
            textView.setBackgroundDrawable(floatingHintTextBackground);
        }
    }

    public String getText(){
        return editText.getText().toString();
    }

    public String getTextHint(){
        return editText.getHint().toString();
    }

    public String getFloatingHintText(){
        return textView.getText().toString();
    }

    public EditText getEditText(){
        return editText;
    }

    public TextView getTextView(){
        return textView;
    }
}