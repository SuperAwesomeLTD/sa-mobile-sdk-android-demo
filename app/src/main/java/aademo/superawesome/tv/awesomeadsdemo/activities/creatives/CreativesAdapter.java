package aademo.superawesome.tv.awesomeadsdemo.activities.creatives;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import rx.Observable;
import rx.subjects.PublishSubject;

public class CreativesAdapter extends RecyclerView.Adapter<CreativesAdapter.CreativesViewHolder> {

    private List<CreativesViewModel> models;
    private PublishSubject<CreativesViewModel> clickSubject = PublishSubject.create();

    public CreativesAdapter(List<CreativesViewModel> models) {
        this.models = models;
    }

    public class CreativesViewHolder extends RecyclerView.ViewHolder {

        public ImageView creativeIcon;
        public TextView creativeName, creativeFormat, creativeSource;

        public CreativesViewHolder(View itemView) {
            super(itemView);
            creativeIcon = (ImageView) itemView.findViewById(R.id.CreativeIcon);
            creativeName = (TextView) itemView.findViewById(R.id.CreativeName);
            creativeFormat = (TextView) itemView.findViewById(R.id.CreativeFormat);
            creativeSource = (TextView) itemView.findViewById(R.id.CreativeSource);
        }
    }

    @Override
    public CreativesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_creatives, parent, false);
        return new CreativesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CreativesViewHolder holder, int position) {
        CreativesViewModel model = models.get(position);
        holder.creativeName.setText(model.getCreativeName());
        holder.creativeFormat.setText(model.getCreativeFormat());
        holder.creativeSource.setText(model.getCreativeSource());

        holder.itemView.setOnClickListener(v -> {
            clickSubject.onNext(model);
        });
    }

    @Override
    public void onViewRecycled(CreativesViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public Observable<CreativesViewModel> getItemClicks(){
        return clickSubject.asObservable();
    }

}
