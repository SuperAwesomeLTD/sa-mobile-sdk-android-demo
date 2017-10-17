package aademo.superawesome.tv.awesomeadsdemo.activities.creatives;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import aademo.superawesome.tv.awesomeadsdemo.R;
import rx.Observable;
import rx.subjects.PublishSubject;

class CreativesAdapter extends RecyclerView.Adapter<CreativesAdapter.CreativesViewHolder> {

    private Context context;
    private List<CreativesViewModel> models;
    private PublishSubject<CreativesViewModel> clickSubject = PublishSubject.create();

    CreativesAdapter(Context context, List<CreativesViewModel> models) {
        this.context = context;
        this.models = models;
    }

    void updateData (List<CreativesViewModel> models) {
        this.models = models;
        this.notifyDataSetChanged();
    }

    class CreativesViewHolder extends RecyclerView.ViewHolder {

        ImageView creativeIcon;
        TextView creativeName, creativeFormat, creativeSource, creativeOSTarget;
        LinearLayout creativeRoot;

        CreativesViewHolder(View itemView) {
            super(itemView);
            creativeIcon = itemView.findViewById(R.id.CreativeIcon);
            creativeName = itemView.findViewById(R.id.CreativeName);
            creativeFormat = itemView.findViewById(R.id.CreativeFormat);
            creativeSource = itemView.findViewById(R.id.CreativeSource);
            creativeOSTarget = itemView.findViewById(R.id.CreativeOsTarget);
            creativeRoot = itemView.findViewById(R.id.CreativeRoot);
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
        holder.creativeRoot.setBackgroundColor(position % 2 == 0 ? Color.WHITE : 0xFFF7F7F7);
        holder.creativeName.setText(model.getCreativeName());
        holder.creativeFormat.setText(model.getCreativeFormat());
        holder.creativeSource.setText(model.getCreativeSource());
        holder.creativeOSTarget.setText(model.getOSTarget());
        Picasso.with(context).load(model.getRemoteUrl()).placeholder(model.getLocalUrl()).into(holder.creativeIcon);

        holder.itemView.setOnClickListener(v -> {
            clickSubject.onNext(model);
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    Observable<CreativesViewModel> getItemClicks(){
        return clickSubject.asObservable();
    }

}
