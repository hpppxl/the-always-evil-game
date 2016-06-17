package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MapActivity extends Activity {

    private boolean mStartAtVillage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        final ImageButton clearingButton = (ImageButton)findViewById(R.id.btn_start_clearing);
        final ImageButton villageButton = (ImageButton)findViewById(R.id.btn_start_village);
        final TextView descriptionText = (TextView)findViewById(R.id.txt_explanation);
        final TextView startGameButton = (TextView)findViewById(R.id.btn_startGame);


        clearingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                villageButton.setImageResource(R.drawable.entrance_inactive);
                clearingButton.setImageResource(R.drawable.clearing_active);
                descriptionText.setText(R.string.start_w_tutorial_label);
                mStartAtVillage = false;
            }
        });
        villageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                villageButton.setImageResource(R.drawable.entrance_active);
                clearingButton.setImageResource(R.drawable.clearing_inactive);
                descriptionText.setText(R.string.start_wo_tutorial_label);
                mStartAtVillage = true;
            }
        });

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gameIntent = new Intent(MapActivity.this, GameActivity.class);
                if(mStartAtVillage){
                    gameIntent.putExtra(GameActivity.EXTRA_START_LOCATION, GameActivity.EXTRA_LOCATION_VILLAGE);
                }else{
                    gameIntent.putExtra(GameActivity.EXTRA_START_LOCATION, GameActivity.EXTRA_LOCATION_CLEARING);
                }

                startActivity(gameIntent);
            }
        });


    }
}
