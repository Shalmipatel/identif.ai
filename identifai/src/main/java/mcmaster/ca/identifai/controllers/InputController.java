package mcmaster.ca.identifai.controllers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import mcmaster.ca.appcore.datastore.DataController;
import mcmaster.ca.appcore.datastore.ActorModel;
import mcmaster.ca.appcore.ui.BaseActivity;
import mcmaster.ca.identifai.R;
import static mcmaster.ca.identifai.controllers.OutputController.IS_RECENT_SEARCH_PARAM;
import mcmaster.ca.sound.AudioController;
import mcmaster.ca.text.DescriptionController;
import mcmaster.mcmaster.ca.image.ImageEntryController;

import java.util.List;

public class InputController extends BaseActivity {
    private static final int RESULTS_REQUEST_CODE = 1001;
    private final DataController dataStore = new DataController();
    private Button soundButton;
    private Button imageButton;
    private Button textButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_controller);
        setTitle(R.string.input_data);

        soundButton = findViewById(R.id.sound_buuton);
        imageButton = findViewById(R.id.image_button);
        textButton = findViewById(R.id.text_button);

        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(AudioController.class, RESULTS_REQUEST_CODE);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(ImageEntryController.class, RESULTS_REQUEST_CODE);
            }
        });
        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(DescriptionController.class, RESULTS_REQUEST_CODE);
            }
        });

        findViewById(mcmaster.ca.image.R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataStore.processResults().isEmpty()) {
                    Toast.makeText(InputController.this, getString(R.string.no_entry_yet), Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(InputController.this, OutputController.class);
                    intent.putParcelableArrayListExtra(OutputController.RESULTS_PARAM, dataStore.processResults());
                    intent.putExtra(IS_RECENT_SEARCH_PARAM, true);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If not data passed back, we should just ignore.
        if (data == null) {
            return;
        }
        if (requestCode == RESULTS_REQUEST_CODE) {
            List<ActorModel> resultList = data.getParcelableArrayListExtra(DataController.RESULTS_PARAM);
            dataStore.onReceivedPeopleResults(resultList);
        }

        if (resultCode == DescriptionController.RESULT_CODE) {
            textButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            textButton.setTextColor(Color.WHITE);
            textButton.setEnabled(false);
        }

        if (resultCode == ImageEntryController.RESULT_CODE) {
            imageButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            imageButton.setTextColor(Color.WHITE);
            imageButton.setEnabled(false);
        }

        if (resultCode == AudioController.RESULT_CODE) {
            soundButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            soundButton.setTextColor(Color.WHITE);
            soundButton.setEnabled(false);
        }
    }
}
