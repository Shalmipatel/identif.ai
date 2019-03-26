package mcmaster.mcmaster.ca.image.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FaceFeatures implements Parcelable {
    @SerializedName("left_eye")
    public final FeaturePosition leftEye;

    @SerializedName("right_eye")
    public final FeaturePosition rightEye;

    @SerializedName("nose_tip")
    public final FeaturePosition noseTip;

    @SerializedName("left_mouth_corner")
    public final FeaturePosition leftMouthCorner;

    @SerializedName("right_mouth_corner")
    public final FeaturePosition rightMouthCorner;

    protected FaceFeatures(Parcel in) {
        leftEye = in.readParcelable(FeaturePosition.class.getClassLoader());
        rightEye = in.readParcelable(FeaturePosition.class.getClassLoader());
        noseTip = in.readParcelable(FeaturePosition.class.getClassLoader());
        leftMouthCorner = in.readParcelable(FeaturePosition.class.getClassLoader());
        rightMouthCorner = in.readParcelable(FeaturePosition.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(leftEye, flags);
        dest.writeParcelable(rightEye, flags);
        dest.writeParcelable(noseTip, flags);
        dest.writeParcelable(leftMouthCorner, flags);
        dest.writeParcelable(rightMouthCorner, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FaceFeatures> CREATOR = new Creator<FaceFeatures>() {
        @Override
        public FaceFeatures createFromParcel(Parcel in) {
            return new FaceFeatures(in);
        }

        @Override
        public FaceFeatures[] newArray(int size) {
            return new FaceFeatures[size];
        }
    };
}
