package mcmaster.mcmaster.ca.image.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RetrieveCelebritiesRs implements Parcelable {
    @SerializedName("status")
    public final SightEngineStatus status;

    @SerializedName("request")
    public final SightEngineRequest request;

    @SerializedName("faces")
    public final List<SightEngineFace> faces;

    protected RetrieveCelebritiesRs(Parcel in) {
        status = SightEngineStatus.valueOf(in.readString());
        request = in.readParcelable(SightEngineRequest.class.getClassLoader());
        faces = in.createTypedArrayList(SightEngineFace.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status.toString());
        dest.writeParcelable(request, flags);
        dest.writeTypedList(faces);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RetrieveCelebritiesRs> CREATOR = new Creator<RetrieveCelebritiesRs>() {
        @Override
        public RetrieveCelebritiesRs createFromParcel(Parcel in) {
            return new RetrieveCelebritiesRs(in);
        }

        @Override
        public RetrieveCelebritiesRs[] newArray(int size) {
            return new RetrieveCelebritiesRs[size];
        }
    };
}
