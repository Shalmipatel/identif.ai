package mcmaster.ca.text.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCreditsRs implements Parcelable {
    @SerializedName("id")
    public final int id;

    @SerializedName("cast")
    public final List<CastMember> cast;

    protected GetCreditsRs(Parcel in) {
        id = in.readInt();
        cast = in.createTypedArrayList(CastMember.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeTypedList(cast);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GetCreditsRs> CREATOR = new Creator<GetCreditsRs>() {
        @Override
        public GetCreditsRs createFromParcel(Parcel in) {
            return new GetCreditsRs(in);
        }

        @Override
        public GetCreditsRs[] newArray(int size) {
            return new GetCreditsRs[size];
        }
    };
}
