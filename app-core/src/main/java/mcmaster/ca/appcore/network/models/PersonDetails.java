package mcmaster.ca.appcore.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class PersonDetails implements Parcelable {
    @SerializedName("popularity")
    public final double popularity;

    @SerializedName("id")
    public final int id;

    @SerializedName("profile_path")
    public final String profilePath;

    @SerializedName("known_for")
    public final List<KnownFor> knownFors;

    @SerializedName("adult")
    public final boolean adult;

    protected PersonDetails(Parcel in) {
        popularity = in.readDouble();
        id = in.readInt();
        profilePath = in.readString();
        knownFors = in.createTypedArrayList(KnownFor.CREATOR);
        adult = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(popularity);
        dest.writeInt(id);
        dest.writeString(profilePath);
        dest.writeTypedList(knownFors);
        dest.writeByte((byte)(adult ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PersonDetails> CREATOR = new Creator<PersonDetails>() {
        @Override
        public PersonDetails createFromParcel(Parcel in) {
            return new PersonDetails(in);
        }

        @Override
        public PersonDetails[] newArray(int size) {
            return new PersonDetails[size];
        }
    };

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Known for: ");
        sb.append("\n");
        for (KnownFor knownFor : knownFors) {
            sb.append(knownFor.title)
                .append("\n");
        }
        return sb.toString();
    }
}
