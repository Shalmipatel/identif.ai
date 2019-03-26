package mcmaster.mcmaster.ca.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.IpCons;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;

import mcmaster.ca.appcore.datastore.DataController;
import static mcmaster.ca.appcore.datastore.DataController.RESULTS_PARAM;
import mcmaster.ca.appcore.datastore.ActorModel;
import mcmaster.ca.appcore.network.HttpCallback;
import mcmaster.ca.appcore.ui.BaseActivity;
import mcmaster.ca.image.R;
import mcmaster.mcmaster.ca.image.models.RetrieveCelebritiesRs;
import mcmaster.mcmaster.ca.image.models.SightEngineCelebrity;
import mcmaster.mcmaster.ca.image.models.SightEngineFace;
import okhttp3.Call;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageEntryController extends BaseActivity {
    public Bitmap selectedImage;
    public static final int RESULT_CODE = 2002;
    private final ImageDetectionService service = new ImageDetectionService();
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_entry_controller);
        setTitle(R.string.select_image);
        Button imageEntryButton = findViewById(R.id.select_image_button);
        imageEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSelectImage();
            }
        });
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImage != null) {
                    sendImageToApi(selectedImage);
                } else {
                    Toast
                        .makeText(ImageEntryController.this, getString(R.string.select_image_error), Toast.LENGTH_SHORT)
                        .show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
            findViewById(R.id.select_image_button).setVisibility(View.GONE);

            File imgFile = new File(image.getPath());

            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                selectedImage = myBitmap;
                ImageView imageView = findViewById(R.id.image_view);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(myBitmap);
                submitButton.setBackgroundColor(getResources().getColor(R.color.success_green));
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void openSelectImage() {
        startActivityForResult(ImagePicker.create(this)
            .single()
            .returnMode(ReturnMode.ALL)
            .getIntent(this), IpCons.RC_IMAGE_PICKER);
    }

    private void sendImageToApi(Bitmap selectedImage) {
        showLoading();
        service.retrieveImageResults(selectedImage, new HttpCallback<RetrieveCelebritiesRs>() {
            @Override
            public void onSuccess(Call call, RetrieveCelebritiesRs response) {
                hideLoading();
                if (response == null || response.faces == null) {
                    // Error
                    return;
                }
                ArrayList<SightEngineCelebrity> celebrities = new ArrayList<>();
                for (SightEngineFace face : response.faces) {
                    celebrities.addAll(face.celebrities);
                }
                handleNetworkResponse(celebrities);
            }

            @Override
            public void onFailure(Call call, IOException exception) {
                hideLoading();
                displayPrompt(exception.getMessage());
            }
        });
    }

    private void handleNetworkResponse(List<SightEngineCelebrity> celebrities) {
        ArrayList<ActorModel> convertedResults = new ArrayList<>();
        if (celebrities != null && !celebrities.isEmpty()) {
            for (int i = 0; i < Math.min(celebrities.size(), DataController.MAX_RESULTS_FOR_EXPERT); i++) {
                SightEngineCelebrity member = celebrities.get(i);
                convertedResults.add(new ActorModel(member.name, 5 - i));
            }
        }
        Intent data = new Intent();
        data.putParcelableArrayListExtra(RESULTS_PARAM, convertedResults);
        setResult(RESULT_CODE, data);
        finish();
    }
}
