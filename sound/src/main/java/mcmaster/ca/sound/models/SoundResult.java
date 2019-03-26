package mcmaster.ca.sound.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public final class SoundResult implements Parcelable {
    @SerializedName("status")
    public final Status status;

    @SerializedName("metadata")
    public final Metadata metadata;

    protected SoundResult(Parcel in) {
        status = in.readParcelable(Status.class.getClassLoader());
        metadata = in.readParcelable(Metadata.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(status, flags);
        dest.writeParcelable(metadata, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SoundResult> CREATOR = new Creator<SoundResult>() {
        @Override
        public SoundResult createFromParcel(Parcel in) {
            return new SoundResult(in);
        }

        @Override
        public SoundResult[] newArray(int size) {
            return new SoundResult[size];
        }
    };
}
