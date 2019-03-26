package mcmaster.ca.identifai.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import mcmaster.ca.appcore.common.AppPreferenceManager;
import mcmaster.ca.appcore.common.InputListener;
import mcmaster.ca.appcore.datastore.ActorModel;
import mcmaster.ca.appcore.network.models.AppSearchResult;
import mcmaster.ca.appcore.ui.BaseActivity;
import mcmaster.ca.appcore.ui.adapters.AbstractDataBindAdapter;
import mcmaster.ca.appcore.ui.binder.PersonDataBinder;
import mcmaster.ca.identifai.R;

import java.util.ArrayList;
import java.util.List;

public class OutputController extends BaseActivity {
    public static final String RESULTS_PARAM = "ResultsPARAM";
    public static final String IS_RECENT_SEARCH_PARAM = "isRecentSearch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_controller);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.output_title);
        }
        final ArrayList<ActorModel> resultList = getIntent().getParcelableArrayListExtra(RESULTS_PARAM);
        if (resultList != null) {
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new Adapter(resultList));
        }
        boolean shouldBeRecentSearch = getIntent().getBooleanExtra(IS_RECENT_SEARCH_PARAM, false);
        if (shouldBeRecentSearch) {
            saveRecentSearch(resultList);
        }

        findViewById(R.id.share_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSharing(resultList);
            }
        });

        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveResults(resultList);
            }
        });
    }

    private void saveRecentSearch(ArrayList<ActorModel> resultList) {
        AppPreferenceManager preferenceManager = new AppPreferenceManager(this);
        AppSearchResult newSearch = new AppSearchResult(resultList);
        preferenceManager.saveRecentSearchResult(newSearch);
    }

    private void saveResults(ArrayList<ActorModel> resultList) {
        AppPreferenceManager preferenceManager = new AppPreferenceManager(this);
        AppSearchResult newSearch = new AppSearchResult(resultList);
        preferenceManager.saveSearchResult(newSearch);
        displayPrompt(getString(R.string.saved_successful));
    }

    private void goToSharing(ArrayList<ActorModel> resultList) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        AppSearchResult newSearch = new AppSearchResult(resultList);
        sendIntent.putExtra(Intent.EXTRA_TEXT, newSearch.toString());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }

    private InputListener<ActorModel> createSelectionListener() {
        return new InputListener<ActorModel>() {
            @Override
            public void onInputReceived(ActorModel value) {
                Intent intent = new Intent(OutputController.this, PersonDetailController.class);
                intent.putExtra(PersonDetailController.PERSON_PARAM, value);
                startActivity(intent);
            }
        };
    }

    public class Adapter extends AbstractDataBindAdapter {
        private final List<ActorModel> resultList;

        public Adapter(List<ActorModel> resultList) {
            super();
            this.resultList = resultList;
            buildRows();
        }

        private void buildRows() {
            listItems.clear();
            for (ActorModel person : resultList) {
                listItems.add(new PersonDataBinder(person, createSelectionListener()));
            }
        }
    }
}
