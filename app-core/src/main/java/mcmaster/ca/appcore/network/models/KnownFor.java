package mcmaster.ca.appcore.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class KnownFor implements Parcelable {
    @SerializedName("vote_average")
    public final double voteAverage;

    @SerializedName("vote_count")
    public final int voteCount;

    @SerializedName("id")
    public final int id;

    @SerializedName("video")
    public final boolean video;

    @SerializedName("media_type")
    public final String mediaType;

    @SerializedName("title")
    public final String title;

    @SerializedName("popularity")
    public final double popularity;

    @SerializedName("poster_path")
    public final String posterPath;

    @SerializedName("original_language")
    public final String originalLanguage;

    @SerializedName("backdrop_path")
    public final String backdropPath;

    @SerializedName("adult")
    public final boolean adult;

    @SerializedName("overview")
    public final String overview;

    @SerializedName("release_date")
    public final String releaseDate;

    protected KnownFor(Parcel in) {
        voteAverage = in.readDouble();
        voteCount = in.readInt();
        id = in.readInt();
        video = in.readByte() != 0;
        mediaType = in.readString();
        title = in.readString();
        popularity = in.readDouble();
        posterPath = in.readString();
        originalLanguage = in.readString();
        backdropPath = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        releaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(voteAverage);
        dest.writeInt(voteCount);
        dest.writeInt(id);
        dest.writeByte((byte)(video ? 1 : 0));
        dest.writeString(mediaType);
        dest.writeString(title);
        dest.writeDouble(popularity);
        dest.writeString(posterPath);
        dest.writeString(originalLanguage);
        dest.writeString(backdropPath);
        dest.writeByte((byte)(adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<KnownFor> CREATOR = new Creator<KnownFor>() {
        @Override
        public KnownFor createFromParcel(Parcel in) {
            return new KnownFor(in);
        }

        @Override
        public KnownFor[] newArray(int size) {
            return new KnownFor[size];
        }
    };
}
