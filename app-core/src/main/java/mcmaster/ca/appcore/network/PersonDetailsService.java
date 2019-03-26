package mcmaster.ca.appcore.network;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;

import mcmaster.ca.appcore.network.models.PersonSearchRs;
import okhttp3.Request;

import java.lang.reflect.Type;

public final class PersonDetailsService {
    // Keys
    private static final String API_KEY = "api_key";
    private static final String LANGUAGE = "language";
    private static final String QUERY = "query";

    // Values
    private static final String MOVIE_DB_API_KEY = "6a527439d35b5f40b5e32e67288aeadc";
    private static final String LANGUAGE_VALUE = "en-US";

    private final BaseNetworkingClient client;

    public PersonDetailsService() {
        client = new BaseNetworkingClient();
    }

    /**
     * Creates a request to get a list of movies matching the query.
     *
     * @param query
     *     The movie to search.
     * @param callback
     *     The success and failure callback.
     */
    public void retrievePersonResults(@NonNull String query,
        @NonNull HttpCallback<PersonSearchRs> callback) {
        // Build the URL.
        Uri uri = Uri.parse(RestEndpoints.THE_MOVIE_DB_BASE_URL + RestEndpoints.SEARCH_PERSON_PATH)
            .buildUpon()
            .appendQueryParameter(API_KEY, MOVIE_DB_API_KEY)
            .appendQueryParameter(LANGUAGE, LANGUAGE_VALUE)
            .appendQueryParameter(QUERY, query)
            .build();

        // Build the Request
        Request.Builder requestBuilder = client.createRequestBuilder(uri.toString());

        // Send the Request.
        Type type = new TypeToken<PersonSearchRs>() {}.getType();
        client.enqueueRequest(requestBuilder.build(),
            new ServiceHttpCallback<>(type, callback));
    }
}
