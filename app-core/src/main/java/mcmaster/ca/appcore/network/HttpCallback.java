package mcmaster.ca.appcore.network;

import java.io.IOException;

import okhttp3.Call;

/**
 * Wrapper class for okHttp CallBack to return a Java Model Object.
 *
 * @param <T> Generic type response.
 */
public interface HttpCallback<T> {
    void onSuccess(Call call, T response);

    void onFailure(Call call, IOException exception);
}