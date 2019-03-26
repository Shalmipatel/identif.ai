package mcmaster.ca.appcore.ui.viewholder;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mcmaster.ca.appcore.R;

public class PersonViewHolder extends RecyclerView.ViewHolder {
    public final TextView nameText;
    public final TextView scoreText;
    public final ImageView profileIcon;

    public PersonViewHolder(View itemView) {
        super(itemView);
        nameText = itemView.findViewById(R.id.name_text);
        scoreText = itemView.findViewById(R.id.score_text);
        profileIcon = itemView.findViewById(R.id.profile_icon);
    }

    @LayoutRes
    public static int getLayoutId() {
        return R.layout.person_view_holder;
    }
}
