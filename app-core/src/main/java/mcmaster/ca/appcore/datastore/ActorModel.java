package mcmaster.ca.appcore.datastore;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import mcmaster.ca.appcore.network.HttpCallback;
import mcmaster.ca.appcore.network.PersonDetailsService;
import mcmaster.ca.appcore.network.RestEndpoints;
import mcmaster.ca.appcore.network.models.PersonSearchRs;
import okhttp3.Call;

import java.io.IOException;

/**
 * This class represents a model of a person result
 */
public class ActorModel implements Parcelable, Comparable<ActorModel> {
    @SerializedName("name")
    public final String name;

    @SerializedName("score")
    public int score;

    @Nullable
    @SerializedName("profileUrl")
    public String profileUrl;

    protected ActorModel(Parcel in) {
        name = in.readString();
        score = in.readInt();
        profileUrl = in.readString();
    }

    private void attemptRetrieveDetails() {
        if (profileUrl == null) {
            PersonDetailsService service = new PersonDetailsService();
            service.retrievePersonResults(name, new HttpCallback<PersonSearchRs>() {
                @Override
                public void onSuccess(Call call, PersonSearchRs response) {
                    if (response != null && response.results != null && !response.results.isEmpty()) {
                        profileUrl = RestEndpoints.THE_MOVIE_FB_BASE_IMAGE_URL + response.results.get(0).profilePath;
                    }
                }

                @Override
                public void onFailure(Call call, IOException exception) {
                    // Silently fail.
                }
            });
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(score);
        dest.writeString(profileUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActorModel> CREATOR = new Creator<ActorModel>() {
        @Override
        public ActorModel createFromParcel(Parcel in) {
            return new ActorModel(in);
        }

        @Override
        public ActorModel[] newArray(int size) {
            return new ActorModel[size];
        }
    };

    public void increaseScore(int amount) {
        score = score + amount;
    }


    public ActorModel(String name, int score, @Nullable String profileUrl) {
        this.name = name.trim();
        this.score = score;
        this.profileUrl = profileUrl;
        attemptRetrieveDetails();
    }

    public ActorModel(String name, int score) {
        this(name, score, null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActorModel) {
            return name.equalsIgnoreCase(((ActorModel)obj).name);
        }
        return super.equals(obj);
    }

    @Override
    public int compareTo(@NonNull ActorModel actorModel) {
        return Integer.compare(actorModel.score, score);
    }
}
