package mcmaster.ca.appcore.ui.binder;

import android.view.View;
import android.view.ViewGroup;

import mcmaster.ca.appcore.common.AppDateFormatter;
import mcmaster.ca.appcore.common.InputListener;
import mcmaster.ca.appcore.network.models.AppSearchResult;
import mcmaster.ca.appcore.ui.viewholder.SearchResultViewHolder;

import java.util.Date;

public class SearchResultsBinder extends AbstractDataBinder<SearchResultViewHolder> {
    private final AppSearchResult result;
    private final InputListener<AppSearchResult> listener;
    public final InputListener<AppSearchResult> deleteListener;

    public SearchResultsBinder(AppSearchResult result, InputListener<AppSearchResult> listener,
        InputListener<AppSearchResult> deleteListener) {
        this.result = result;
        this.listener = listener;
        this.deleteListener = deleteListener;
    }

    @Override
    public SearchResultViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(SearchResultViewHolder.getLayoutId(), parent);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void bindViewHolder(SearchResultViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onInputReceived(result);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteListener.onInputReceived(result);
            }
        });
        Date date = AppDateFormatter.convertToDate(result.date);
        holder.title.setText(AppDateFormatter.getSimpleDate(date));
        holder.time.setText(AppDateFormatter.getTime(date));
    }
}
