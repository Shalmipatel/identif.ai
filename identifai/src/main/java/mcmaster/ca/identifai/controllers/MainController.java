package mcmaster.ca.identifai.controllers;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mcmaster.ca.appcore.ui.BaseActivity;
import mcmaster.ca.identifai.R;

public class MainController extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        Button identifyPerson = findViewById(R.id.identifyButton);
        Button recentSearches = findViewById(R.id.viewRecentSearchesButton);
        Button savedSearchesButton = findViewById(R.id.viewSavedSearchesButton);

        identifyPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(InputController.class);
            }
        });

        recentSearches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RecentSearchesController.class);
            }
        });

        savedSearchesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SaveSearchesController.class);
            }
        });
    }
}
