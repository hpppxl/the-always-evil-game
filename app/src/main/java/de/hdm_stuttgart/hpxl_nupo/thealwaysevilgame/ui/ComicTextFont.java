package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by daniel on 05/06/16.
 */
public class ComicTextFont extends TextView {
    //region Constants
    private static final String COMIC_TYPEFACE_NAME = "fonts/komika_axis.ttf";
    //endregion

    //region Properties & Members
    //endregion

    //region Constructors
    public ComicTextFont(Context context) {
        super(context);
        setUpFont();
    }

    public ComicTextFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpFont();
    }

    public ComicTextFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpFont();
    }

    public ComicTextFont(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUpFont();
    }
    //endregion

    //region Methods
    private void setUpFont(){
        Typeface comicFace = Typeface.createFromAsset(this.getContext().getAssets(), COMIC_TYPEFACE_NAME);
        this.setTypeface(comicFace);
    }
    //endregion

    //region Inner Classes / Interfaces
    //endregion
}
