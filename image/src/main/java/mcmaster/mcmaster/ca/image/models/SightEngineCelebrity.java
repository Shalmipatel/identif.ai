package mcmaster.mcmaster.ca.image.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SightEngineCelebrity implements Parcelable {
    @SerializedName("name")
    public final String name;

    @SerializedName("prob")
    public final double probability;

    protected SightEngineCelebrity(Parcel in) {
        name = in.readString();
        probability = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(probability);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SightEngineCelebrity> CREATOR = new Creator<SightEngineCelebrity>() {
        @Override
        public SightEngineCelebrity createFromParcel(Parcel in) {
            return new SightEngineCelebrity(in);
        }

        @Override
        public SightEngineCelebrity[] newArray(int size) {
            return new SightEngineCelebrity[size];
        }
    };
}
