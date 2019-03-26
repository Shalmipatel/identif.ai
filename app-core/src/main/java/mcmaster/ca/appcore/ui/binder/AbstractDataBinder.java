package mcmaster.ca.appcore.ui.binder;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Any row in recycler view can have it's own data binder
 * It creates view holder and binds data based on the type
 *
 * @param <T>
 *     generic view holder
 */
public abstract class AbstractDataBinder<T extends RecyclerView.ViewHolder> {

    protected View getView(@LayoutRes int layoutId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

    public int getViewType() {
        return this.getClass().getSimpleName().hashCode();
    }

    abstract public T createViewHolder(ViewGroup parent);

    abstract public void bindViewHolder(T holder);

}