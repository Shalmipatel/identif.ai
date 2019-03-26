package mcmaster.ca.appcore.ui.viewholder;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import mcmaster.ca.appcore.R;

public class SearchResultViewHolder extends RecyclerView.ViewHolder {
    public final TextView title;
    public final TextView time;
    public final TextView deleteButton;

    public SearchResultViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title_label);
        time = itemView.findViewById(R.id.time_label);
        deleteButton = itemView.findViewById(R.id.delete_button);
    }

    @LayoutRes
    public static int getLayoutId() {
        return R.layout.search_result_view_holder;
    }
}
