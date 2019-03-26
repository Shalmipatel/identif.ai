package mcmaster.mcmaster.ca.image.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SightEngineFace implements Parcelable {
    @SerializedName("x1")
    public final double x1;

    @SerializedName("y1")
    public final double y1;

    @SerializedName("x2")
    public final double x2;

    @SerializedName("y2")
    public final double y2;

    @SerializedName("features")
    public final FaceFeatures features;

    @SerializedName("celebrity")
    public final List<SightEngineCelebrity> celebrities;

    protected SightEngineFace(Parcel in) {
        x1 = in.readDouble();
        y1 = in.readDouble();
        x2 = in.readDouble();
        y2 = in.readDouble();
        features = in.readParcelable(FaceFeatures.class.getClassLoader());
        celebrities = in.createTypedArrayList(SightEngineCelebrity.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(x1);
        dest.writeDouble(y1);
        dest.writeDouble(x2);
        dest.writeDouble(y2);
        dest.writeParcelable(features, flags);
        dest.writeTypedList(celebrities);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SightEngineFace> CREATOR = new Creator<SightEngineFace>() {
        @Override
        public SightEngineFace createFromParcel(Parcel in) {
            return new SightEngineFace(in);
        }

        @Override
        public SightEngineFace[] newArray(int size) {
            return new SightEngineFace[size];
        }
    };
}
