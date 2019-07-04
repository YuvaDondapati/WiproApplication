
package yuva.assignment.wiproapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;
import yuva.assignment.wiproapplication.R;
import yuva.assignment.wiproapplication.app.Constant;
import yuva.assignment.wiproapplication.model.Facts;

public class CountryFactsAdapter extends RecyclerView.Adapter<CountryFactsAdapter.FactsViewHolder> {

    private List<Facts> facts;
    private LayoutInflater mInflater;
    Context context;

    public static class FactsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        ImageView imageView;
        TextView description;

        public FactsViewHolder(View v) {
            super(v);
            movieTitle = (itemView.findViewById(R.id.tvTitle));
            description = (itemView.findViewById(R.id.tvDescription));
            imageView = (itemView.findViewById(R.id.countryImage));
        }
    }

    public CountryFactsAdapter(List<Facts> facts, Context context) {
        this.context = context;
        this.facts = facts;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public FactsViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new FactsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(FactsViewHolder holder, final int position) {
        String title = facts.get(position).getTitle() == null || facts.get(position).getTitle().equals("") ? Constant.NO_TITLE : facts.get(position).getTitle();
        String description = facts.get(position).getDescription() == null || facts.get(position).getDescription().equals("") ? Constant.NO_DESC : facts.get(position).getDescription();
        holder.movieTitle.setText(title);
        holder.description.setText(description);
        String imageUrl = facts.get(position).getImageHref();
        String finalUrl;
        if (imageUrl != null && imageUrl.contains("http:")) {
            finalUrl = imageUrl.replace("http", "https");
        } else {
            finalUrl = imageUrl;
        }
        Glide
                .with(context)
                .load(finalUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .fitCenter()
                        .centerCrop())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return facts.size();
    }
}