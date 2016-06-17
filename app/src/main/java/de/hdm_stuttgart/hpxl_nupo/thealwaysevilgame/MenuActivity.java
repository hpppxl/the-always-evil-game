package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class MenuActivity extends Activity {
    //region Constants
    private static String LOG_TAG = MenuActivity.class.getSimpleName();
    private static final int MAX_BLINK_TIME = 10000;
    private static final int MIN_BLINK_TIME = 2000;
    private static final int MAX_RETURN_TIME = 100;
    private static final int MIN_RETURN_TIME = 400;
    //endregion

    //region Properties & Members
    private Handler blinkHandler = new Handler();
    private Runnable blinker = new Blinker();
    private ImageView logo;
    //endregion

    //region Constructors

    //endregion

    //region Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button startButton = (Button)findViewById(R.id.btn_startGame);
        Button aboutButton = (Button)findViewById(R.id.btn_about);
        logo = (ImageView)findViewById(R.id.img_logo);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(MenuActivity.this, MapActivity.class);
                startActivity(gameIntent);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(MenuActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
            }
        });

        blinkHandler.postDelayed(blinker, getRandom(MIN_BLINK_TIME, MAX_BLINK_TIME));
    }

    private int getRandom(int minTime, int maxTime){
        return (int)(Math.random() * (maxTime - minTime)) + minTime;
    }
    //endregion

    //region Inner Classes / Interfaces
    private enum BlinkAction {BLINK_LEFT, BLINK_RIGHT, BLINK_BOTH;

        private static final int NUM_ACTIONS = 3;
        public static BlinkAction getRandomAction() {
            int action = ((int)(Math.random() * NUM_ACTIONS));
            return BlinkAction.values()[action];
        }
    };

    private class Blinker implements Runnable {

        private BlinkAction currentAction;
        @Override
        public void run() {
            if(currentAction == null){
                //we're currently not blinking, choose a random option
                currentAction = BlinkAction.getRandomAction();
                switch(currentAction){
                    case BLINK_LEFT:
                        changeLogo(R.drawable.logo_blink_l);
                        break;
                    case BLINK_RIGHT:
                        changeLogo(R.drawable.logo_blink_r);
                        break;
                    case BLINK_BOTH:
                        changeLogo(R.drawable.logo_blink_b);
                        break;
                }
                blinkHandler.postDelayed(this, getRandom(MIN_RETURN_TIME, MAX_RETURN_TIME));
            }else if(currentAction == BlinkAction.BLINK_BOTH){
                //we finished a blink, return and restart the timer
                changeLogo(R.drawable.logo);
                currentAction = null;
                blinkHandler.postDelayed(this, getRandom(MIN_BLINK_TIME, MAX_BLINK_TIME));
            }else{
                //we have either eye closed close both and restart a timer to return to open state
                changeLogo(R.drawable.logo_blink_b);
                currentAction = BlinkAction.BLINK_BOTH;
                blinkHandler.postDelayed(this, getRandom(MIN_RETURN_TIME, MAX_RETURN_TIME));
            }
        }

        private void changeLogo(final int resId){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    logo.setImageResource(resId);
                }
            });
        }
    }
    //endregion
}
