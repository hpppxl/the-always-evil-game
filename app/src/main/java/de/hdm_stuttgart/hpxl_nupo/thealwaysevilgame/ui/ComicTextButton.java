package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by daniel on 05/06/16.
 */
public class ComicTextButton extends Button {
    private static final String COMIC_TYPEFACE_NAME = "fonts/komika_axis.ttf";


    public ComicTextButton(Context context) {
        super(context);
        setUpFont();
    }

    public ComicTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpFont();
    }

    public ComicTextButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpFont();
    }

    private void setUpFont(){
        Typeface comicFace = Typeface.createFromAsset(this.getContext().getAssets(), COMIC_TYPEFACE_NAME);
        this.setTypeface(comicFace);
    }
}
