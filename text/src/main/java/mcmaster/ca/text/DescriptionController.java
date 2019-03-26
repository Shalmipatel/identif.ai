package mcmaster.ca.text;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mcmaster.ca.appcore.datastore.DataController;
import static mcmaster.ca.appcore.datastore.DataController.RESULTS_PARAM;
import mcmaster.ca.appcore.datastore.ActorModel;
import mcmaster.ca.appcore.network.HttpCallback;
import mcmaster.ca.appcore.network.RestEndpoints;
import mcmaster.ca.appcore.ui.BaseActivity;
import mcmaster.ca.text.models.CastMember;
import okhttp3.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DescriptionController extends BaseActivity {
    public static final int RESULT_CODE = 2001;
    private final TextDetectionService service = new TextDetectionService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_entry_controller);
        setTitle(R.string.enter_text);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }

        final EditText textEntry = findViewById(R.id.text_entry_edittext);
        final Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textEntry.getText() != null && !textEntry.getText().toString().isEmpty()) {
                    submitTextToApi(textEntry.getText().toString());
                } else {
                    Toast.makeText(DescriptionController.this, "Please enter a movie title", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textEntry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (textEntry.getText() != null && !textEntry.getText().toString().isEmpty()) {
                    submitTextToApi(textEntry.getText().toString());
                    return true;
                } else {
                    Toast.makeText(DescriptionController.this, "Please enter a movie title", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });

        textEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                submitButton.setBackgroundColor(getResources().getColor(
                    textEntry.getText() != null && !textEntry.getText().toString().isEmpty() ? R.color.success_green
                        : R.color.disabled_grey));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void submitTextToApi(String text) {
        showLoading();
        service.retrieveActors(text, new HttpCallback<List<CastMember>>() {
            @Override
            public void onSuccess(Call call, List<CastMember> response) {
                hideLoading();
                handleNetworkResponse(response);
            }

            @Override
            public void onFailure(Call call, IOException exception) {
                hideLoading();
            }
        });
    }

    private void handleNetworkResponse(List<CastMember> response) {
        ArrayList<ActorModel> convertedResults = new ArrayList<>();
        if (response != null && !response.isEmpty()) {
            for (int i = 0; i < Math.min(response.size(), DataController.MAX_RESULTS_FOR_EXPERT); i++) {
                CastMember member = response.get(i);
                convertedResults.add(new ActorModel(member.name, 5 - i,
                    RestEndpoints.THE_MOVIE_FB_BASE_IMAGE_URL + member.profilePath));
            }
        }
        Intent data = new Intent();
        data.putParcelableArrayListExtra(RESULTS_PARAM, convertedResults);
        setResult(RESULT_CODE, data);
        finish();
    }
}
