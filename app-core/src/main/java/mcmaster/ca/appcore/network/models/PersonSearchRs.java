package mcmaster.ca.appcore.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonSearchRs implements Parcelable {
    @SerializedName("results")
    public final List<PersonDetails> results;

    protected PersonSearchRs(Parcel in) {
        results = in.createTypedArrayList(PersonDetails.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PersonSearchRs> CREATOR = new Creator<PersonSearchRs>() {
        @Override
        public PersonSearchRs createFromParcel(Parcel in) {
            return new PersonSearchRs(in);
        }

        @Override
        public PersonSearchRs[] newArray(int size) {
            return new PersonSearchRs[size];
        }
    };
}
