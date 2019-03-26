package mcmaster.ca.appcore.network;

import android.support.annotation.NonNull;

/**
 * This contains all the Endpoints used in the Identif.ai Application.
 */
public final class RestEndpoints {
    // Image Detection Endpoints
    public static final String SIGHT_ENGINE_BASE_URL = "https://api.sightengine.com/1.0/check.json";

    // Sound Detection Endpoints

    // Text Detection Endpoints
    public static final String THE_MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3";
    public static final String THE_MOVIE_FB_BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w200";
    public static final String MOVIE_PATH = "/movie/";
    public static final String CREDITS_PATH = "/credits";
    public static final String SEARCH_MOVIE_PATH = "/search/movie";
    public static final String SEARCH_PERSON_PATH = "/search/person";

    public static String getMoviePath(@NonNull String movieId) {
        return MOVIE_PATH + movieId + CREDITS_PATH;
    }

    private RestEndpoints() {
        // Do not allow clients to instantiate
    }
}
