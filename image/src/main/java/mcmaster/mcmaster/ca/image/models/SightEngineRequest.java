package mcmaster.mcmaster.ca.image.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SightEngineRequest implements Parcelable {
    @SerializedName("id")
    public final String id;

    @SerializedName("timestamp")
    public final double timestamp;

    @SerializedName("operations")
    public final int operations;

    protected SightEngineRequest(Parcel in) {
        id = in.readString();
        timestamp = in.readDouble();
        operations = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeDouble(timestamp);
        dest.writeInt(operations);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SightEngineRequest> CREATOR = new Creator<SightEngineRequest>() {
        @Override
        public SightEngineRequest createFromParcel(Parcel in) {
            return new SightEngineRequest(in);
        }

        @Override
        public SightEngineRequest[] newArray(int size) {
            return new SightEngineRequest[size];
        }
    };
}
