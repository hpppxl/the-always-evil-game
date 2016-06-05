package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {
    //region Constants
    //endregion

    //region Properties & Members
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

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(MenuActivity.this, GameActivity.class);
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
    }
    //endregion

    //region Inner Classes / Interfaces
    //endregion
}
