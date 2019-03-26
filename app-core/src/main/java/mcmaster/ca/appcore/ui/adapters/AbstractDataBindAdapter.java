package mcmaster.ca.appcore.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import mcmaster.ca.appcore.ui.binder.AbstractDataBinder;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDataBindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected final List<AbstractDataBinder> listItems = new ArrayList<>();

    /**
     * Note: ViewHolder created here will be cached for the same viewType value.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getDataBinder(viewType).createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        getItems().get(position).bindViewHolder(viewHolder);
    }

    @Override
    public final int getItemCount() {
        return getItems().size();
    }

    @Override
    public final int getItemViewType(int position) {
        return getItems().get(position).getViewType();
    }

    private <T extends AbstractDataBinder> T getDataBinder(int viewType) {
        for (AbstractDataBinder binder : getItems()) {
            if (binder.getViewType() == viewType) {
                return (T)binder;
            }
        }
        return null;
    }

    public List<AbstractDataBinder> getItems() {
        return listItems;
    }

}