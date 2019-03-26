package mcmaster.ca.text.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CastMember implements Parcelable {
    @SerializedName("cast_id")
    public final int cast_id;

    @SerializedName("character")
    public final String character;

    @SerializedName("credit_id")
    public final String creditId;

    @SerializedName("gender")
    public final int gender;

    @SerializedName("id")
    public final int id;

    @SerializedName("name")
    public final String name;

    @SerializedName("order")
    public final int order;

    @SerializedName("profile_path")
    public final String profilePath;

    protected CastMember(Parcel in) {
        cast_id = in.readInt();
        character = in.readString();
        creditId = in.readString();
        gender = in.readInt();
        id = in.readInt();
        name = in.readString();
        order = in.readInt();
        profilePath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cast_id);
        dest.writeString(character);
        dest.writeString(creditId);
        dest.writeInt(gender);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(order);
        dest.writeString(profilePath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CastMember> CREATOR = new Creator<CastMember>() {
        @Override
        public CastMember createFromParcel(Parcel in) {
            return new CastMember(in);
        }

        @Override
        public CastMember[] newArray(int size) {
            return new CastMember[size];
        }
    };
}
