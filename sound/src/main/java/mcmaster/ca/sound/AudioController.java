package mcmaster.ca.sound;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.acrcloud.rec.sdk.ACRCloudConfig;
import com.acrcloud.rec.sdk.ACRCloudClient;
import com.acrcloud.rec.sdk.IACRCloudListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import mcmaster.ca.appcore.datastore.DataController;
import static mcmaster.ca.appcore.datastore.DataController.RESULTS_PARAM;
import mcmaster.ca.appcore.datastore.ActorModel;
import mcmaster.ca.appcore.ui.BaseActivity;
import mcmaster.ca.sound.models.Artist;
import mcmaster.ca.sound.models.Music;
import mcmaster.ca.sound.models.SoundResult;

public class AudioController extends BaseActivity implements IACRCloudListener {
    public static final int RESULT_CODE = 3003;
    private ACRCloudClient mClient;
    private ACRCloudConfig mConfig;

    private boolean mProcessing = false;
    private boolean initState = false;
    private ProgressDialog listeningDialog;
    private String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
        setTitle(R.string.record_sound);
        ActivityCompat.requestPermissions(this,
            new String[] { Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE },
            1);

        path = Environment.getExternalStorageDirectory().toString()
            + "/acrcloud/model";

        listeningDialog = new ProgressDialog(this);
        listeningDialog.setMessage("Listening...");
        listeningDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                cancel();
            }
        });

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        Button startBtn = (Button)findViewById(R.id.start);
        startBtn.setText("Start");

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                listeningDialog.show();
                sendAudioApi();
            }
        });

        this.mConfig = new ACRCloudConfig();
        this.mConfig.acrcloudListener = this;

        this.mConfig.context = this;
        this.mConfig.host = "identify-us-west-2.acrcloud.com";
        this.mConfig.dbPath = path; // offline db path, you can change it with other path which this app can access.
        this.mConfig.accessKey = "18a2fc76d86d31abfca8b258fdfaec5a";
        this.mConfig.accessSecret = "Jx23kVVMaWhNi6mEWtnrqD1Z7WfnvLcFsUOmi2gS";
        this.mConfig.protocol = ACRCloudConfig.ACRCloudNetworkProtocol.PROTOCOL_HTTP; // PROTOCOL_HTTPS
        this.mConfig.reqMode = ACRCloudConfig.ACRCloudRecMode.REC_MODE_REMOTE;

        this.mClient = new ACRCloudClient();
        this.initState = this.mClient.initWithConfig(this.mConfig);
        if (this.initState) {
            this.mClient
                .startPreRecord(
                    3000); //sendAudioApi prerecord, you can call "this.mClient.stopPreRecord()" to stop prerecord.
        }
    }

    protected void cancel() {
        if (mProcessing && this.mClient != null) {
            mProcessing = false;
            this.mClient.cancel();
        }
    }

    public void sendAudioApi() {
        if (!this.initState) {
            Toast.makeText(this, "init error", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mProcessing) {
            mProcessing = true;
            if (this.mClient == null || !this.mClient.startRecognize()) {
                mProcessing = false;
            }
        }
    }

    @Override
    public void onResult(String result) {
        if (this.mClient != null) {
            this.mClient.cancel();
            mProcessing = false;
        }
        listeningDialog.dismiss();
        Gson gson = new GsonBuilder().create();
        Type type = new TypeToken<SoundResult>() {}.getType();
        SoundResult soundResult = gson.fromJson(result, type);
        List<Artist> celebrities = new ArrayList<>();
        for (Music song : soundResult.metadata.music) {
            celebrities.addAll(song.artists);
        }
        handleNetworkResponse(celebrities);
    }

    private void handleNetworkResponse(List<Artist> celebrities) {
        ArrayList<ActorModel> convertedResults = new ArrayList<>();
        if (celebrities != null && !celebrities.isEmpty()) {
            for (int i = 0; i < Math.min(celebrities.size(), DataController.MAX_RESULTS_FOR_EXPERT); i++) {
                Artist member = celebrities.get(i);
                convertedResults.add(new ActorModel(member.name, 5 - i));
            }
        }
        Intent data = new Intent();
        data.putParcelableArrayListExtra(RESULTS_PARAM, convertedResults);
        setResult(RESULT_CODE, data);
        finish();
    }

    @Override
    public void onVolumeChanged(double v) {
        // Intentionally left empty.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mClient != null) {
            this.mClient.release();
            this.initState = false;
            this.mClient = null;
        }
    }
}
