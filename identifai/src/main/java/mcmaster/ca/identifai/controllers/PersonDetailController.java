package mcmaster.ca.identifai.controllers;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mcmaster.ca.appcore.datastore.ActorModel;
import mcmaster.ca.appcore.network.HttpCallback;
import mcmaster.ca.appcore.network.PersonDetailsService;
import mcmaster.ca.appcore.network.models.PersonSearchRs;
import mcmaster.ca.appcore.ui.BaseActivity;
import mcmaster.ca.identifai.R;
import okhttp3.Call;

import java.io.IOException;

public class PersonDetailController extends BaseActivity implements HttpCallback<PersonSearchRs> {
    public static final String PERSON_PARAM = "PersonParam";
    private ActorModel person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail_controller);
        person = getIntent().getParcelableExtra(PERSON_PARAM);

        if (person != null) {
            setTitle(person.name);
            PersonDetailsService service = new PersonDetailsService();
            showLoading();
            service.retrievePersonResults(person.name, this);
        }
    }

    @Override
    public void onSuccess(Call call, final PersonSearchRs response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideLoading();
                // Name Label
                TextView name = findViewById(R.id.name_label);
                name.setText(person.name);
                if (response.results != null && !response.results.isEmpty()) {
                    ImageView imageView = findViewById(R.id.profile_photo);
                    Picasso.get().load(person.profileUrl)
                        .resize(200, 200)
                        .placeholder(R.drawable.profile_placeholder)
                        .into(imageView);
                    TextView description = findViewById(R.id.description_label);
                    description.setText(response.results.get(0).toString());
                }

            }
        });
    }

    @Override
    public void onFailure(Call call, IOException exception) {
        hideLoading();
        displayPrompt(exception.getMessage());
    }
}
