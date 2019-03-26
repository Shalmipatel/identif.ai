package mcmaster.ca.sound.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Metadata implements Parcelable {
    @SerializedName("music")
    public final List<Music> music;

    protected Metadata(Parcel in) {
        music = in.createTypedArrayList(Music.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(music);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Metadata> CREATOR = new Creator<Metadata>() {
        @Override
        public Metadata createFromParcel(Parcel in) {
            return new Metadata(in);
        }

        @Override
        public Metadata[] newArray(int size) {
            return new Metadata[size];
        }
    };
}
