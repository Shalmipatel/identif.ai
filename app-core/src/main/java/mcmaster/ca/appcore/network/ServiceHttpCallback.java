package mcmaster.ca.appcore.network;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A wrapper CallBack for okHttp Callback to include a Java-Type response instead of a String.
 *
 * @param <T>
 *     Generic response object.
 */
public class ServiceHttpCallback<T> implements Callback {
    private final String TAG = ServiceHttpCallback.class.getSimpleName();
    private final Type type;

    private final HttpCallback<T> callback;

    public ServiceHttpCallback(@NonNull Type type, @NonNull HttpCallback<T> callback) {
        this.type = type;
        this.callback = callback;
    }

    private T parsedResponseBody(@NonNull String httpResponseBody) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(httpResponseBody, type);
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        callback.onFailure(call, e);
        Log.d(TAG, call.toString());
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        try {
            callback.onSuccess(call, parsedResponseBody(response.body().string()));
        } catch (IOException ioe) {
            onFailure(call, ioe);
        }
    }
}