package mcmaster.mcmaster.ca.image.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FeaturePosition implements Parcelable {
    @SerializedName("x")
    public final double xPos;

    @SerializedName("y")
    public final double yPos;

    protected FeaturePosition(Parcel in) {
        xPos = in.readDouble();
        yPos = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(xPos);
        dest.writeDouble(yPos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FeaturePosition> CREATOR = new Creator<FeaturePosition>() {
        @Override
        public FeaturePosition createFromParcel(Parcel in) {
            return new FeaturePosition(in);
        }

        @Override
        public FeaturePosition[] newArray(int size) {
            return new FeaturePosition[size];
        }
    };
}
