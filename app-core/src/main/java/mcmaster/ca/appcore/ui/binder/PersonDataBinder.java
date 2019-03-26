package mcmaster.ca.appcore.ui.binder;

import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import mcmaster.ca.appcore.R;
import mcmaster.ca.appcore.common.InputListener;
import mcmaster.ca.appcore.datastore.ActorModel;
import mcmaster.ca.appcore.ui.viewholder.PersonViewHolder;

public class PersonDataBinder extends AbstractDataBinder<PersonViewHolder> {
    private final ActorModel actorModel;
    private final InputListener<ActorModel> listener;

    public PersonDataBinder(ActorModel actorModel, InputListener<ActorModel> listener) {
        super();
        this.actorModel = actorModel;
        this.listener = listener;
    }

    @Override
    public PersonViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(PersonViewHolder.getLayoutId(), parent);
        return new PersonViewHolder(view);
    }

    @Override
    public void bindViewHolder(PersonViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onInputReceived(actorModel);
            }
        });
        holder.nameText.setText(actorModel.name);
        holder.scoreText.setText(String.valueOf(actorModel.score));
        if (actorModel.profileUrl != null) {
            Picasso.get().load(actorModel.profileUrl)
                .resize(200, 200)
                .placeholder(R.drawable.profile_placeholder)
                .into(holder.profileIcon);
        }
    }
}
