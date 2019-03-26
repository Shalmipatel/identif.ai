package mcmaster.mcmaster.ca.image;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;

import mcmaster.ca.appcore.network.BaseNetworkingClient;
import mcmaster.ca.appcore.network.HttpCallback;
import mcmaster.ca.appcore.network.RestEndpoints;
import mcmaster.ca.appcore.network.ServiceHttpCallback;
import mcmaster.mcmaster.ca.image.models.RetrieveCelebritiesRs;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;

/**
 * This contains all the networking logic to retrieve image detection results.
 */
public final class ImageDetectionService {
    // Helpers to convert MediaType.
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    // SightEngine Keys
    private static final String API_USER_KEY = "api_user";
    private static final String API_SECRET_KEY = "api_secret";
    private static final String MODELS_KEY = "models";
    private static final String MEDIA_KEY = "media";

    // SightEngine Values
    private static final String API_USER_VALUE = "1511439314";
    private static final String API_SECRET_VALUE = "LSbUZsUaViJycsQvCuwv";
    private static final String MODELS_VALUE = "celebrities";

    private final BaseNetworkingClient client;

    /**
     * Constructor to create a Service responsible for obtaining image results.
     */
    public ImageDetectionService() {
        this.client = new BaseNetworkingClient();
    }

    /**
     * Enqueues a request to get information about a photo of the a celebrity. This uses SightEngine to do the analysis.
     *
     * @param image
     *     The image to send to SightEngine.
     * @param callback
     *     The Success and Failure Callback to handle the {@link RetrieveCelebritiesRs}.
     */
    public void retrieveImageResults(final @NonNull Bitmap image,
        final @NonNull HttpCallback<RetrieveCelebritiesRs> callback) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Converts the Bitmap to a byte stream.
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                final byte[] bitmapdata = stream.toByteArray();

                // Creates the MultiPartForm Data used to make request.
                RequestBody requestBody = createDefaultBodyBuilder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(MEDIA_KEY, "celebrity.jpg", RequestBody.create(MEDIA_TYPE_PNG, bitmapdata))
                    .build();

                // Builds the Request to send to the network client.
                Request.Builder requestBuilder = client.createRequestBuilder(RestEndpoints.SIGHT_ENGINE_BASE_URL)
                    .post(requestBody);

                // Sends the request.
                Type type = new TypeToken<RetrieveCelebritiesRs>() {}.getType();
                client.enqueueRequest(requestBuilder.build(),
                    new ServiceHttpCallback<>(type, callback));
            }
        });

        thread.start();
    }

    /**
     * This creates a default SightEngine MultiPartForm Data.
     *
     * @return A SightEngine configured MultipartFormData.
     */
    private MultipartBody.Builder createDefaultBodyBuilder() {
        return new MultipartBody.Builder()
            .addFormDataPart(API_USER_KEY, API_USER_VALUE)
            .addFormDataPart(API_SECRET_KEY, API_SECRET_VALUE)
            .addFormDataPart(MODELS_KEY, MODELS_VALUE);
    }
}
