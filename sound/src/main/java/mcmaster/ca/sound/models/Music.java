package mcmaster.ca.sound.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Music implements Parcelable {
    @SerializedName("artists")
    public final List<Artist> artists;

    @SerializedName("title")
    public final String title;

    @SerializedName("label")
    public final String label;

    protected Music(Parcel in) {
        artists = in.createTypedArrayList(Artist.CREATOR);
        title = in.readString();
        label = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(artists);
        dest.writeString(title);
        dest.writeString(label);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };
}
