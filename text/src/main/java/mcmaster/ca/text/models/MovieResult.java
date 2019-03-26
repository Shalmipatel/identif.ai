package mcmaster.ca.text.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public final class MovieResult implements Parcelable {
    @SerializedName("vote_count")
    public final int voteCount;

    @SerializedName("id")
    public final int id;

    @SerializedName("video")
    public final boolean video;

    @SerializedName("vote_average")
    public final double voteAverage;

    @SerializedName("title")
    public final String title;

    @SerializedName("popularity")
    public final double popularity;

    @SerializedName("poster_path")
    public final String posterPath;

    @SerializedName("original_language")
    public final String originalLanguage;

    @SerializedName("original_title")
    public final String originalTitle;

    @SerializedName("adult")
    public final boolean adult;

    @SerializedName("overview")
    public final String overview;

    @SerializedName("release_date")
    public final String release_date;

    protected MovieResult(Parcel in) {
        voteCount = in.readInt();
        id = in.readInt();
        video = in.readByte() != 0;
        voteAverage = in.readDouble();
        title = in.readString();
        popularity = in.readDouble();
        posterPath = in.readString();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        release_date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(voteCount);
        dest.writeInt(id);
        dest.writeByte((byte)(video ? 1 : 0));
        dest.writeDouble(voteAverage);
        dest.writeString(title);
        dest.writeDouble(popularity);
        dest.writeString(posterPath);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeByte((byte)(adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(release_date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieResult> CREATOR = new Creator<MovieResult>() {
        @Override
        public MovieResult createFromParcel(Parcel in) {
            return new MovieResult(in);
        }

        @Override
        public MovieResult[] newArray(int size) {
            return new MovieResult[size];
        }
    };
}
