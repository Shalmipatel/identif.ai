package mcmaster.ca.text.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public final class SearchMovieRs implements Parcelable {
    @SerializedName("page")
    public final int page;

    @SerializedName("total_results")
    public final int total_results;

    @SerializedName("total_pages")
    public final int total_pages;

    @SerializedName("results")
    public final List<MovieResult> results;

    protected SearchMovieRs(Parcel in) {
        page = in.readInt();
        total_results = in.readInt();
        total_pages = in.readInt();
        results = in.createTypedArrayList(MovieResult.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(total_results);
        dest.writeInt(total_pages);
        dest.writeTypedList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchMovieRs> CREATOR = new Creator<SearchMovieRs>() {
        @Override
        public SearchMovieRs createFromParcel(Parcel in) {
            return new SearchMovieRs(in);
        }

        @Override
        public SearchMovieRs[] newArray(int size) {
            return new SearchMovieRs[size];
        }
    };
}
