package yuva.assignment.wiproapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import yuva.assignment.wiproapplication.R
import yuva.assignment.wiproapplication.app.Constant
import yuva.assignment.wiproapplication.model.Facts
import butterknife.BindView
import butterknife.ButterKnife


class CountryFactsAdapter(private val facts: List<Facts>?, internal var context: Context) : RecyclerView.Adapter<CountryFactsAdapter.FactsViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    class FactsViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        @BindView(R.id.tvTitle)
        lateinit var movieTitle: TextView

        @BindView(R.id.tvDescription)
        lateinit var description: TextView

        @BindView(R.id.countryImage)
        lateinit var imageView: ImageView

        internal var moviesLayout: LinearLayout? = null

        init {
            ButterKnife.bind(this, v);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): FactsViewHolder {
        val view = mInflater.inflate(R.layout.recyclerview_row, parent, false)
        return FactsViewHolder(view)

    }

    override fun onBindViewHolder(holder: FactsViewHolder, position: Int) {

        val title = if (facts?.get(position)?.title == null || facts[position].title == "") Constant.NO_TITLE else facts[position].title
        val description = if (facts?.get(position)?.description == null || facts.get(position).description == "") Constant.NO_DESC else facts[position].description
        holder.movieTitle.text = title
        holder.description.text = description
        val imageUrl = facts?.get(position)?.imageHref
        val finalUrl: String?
        if (imageUrl != null && imageUrl.contains("http:")) {
            finalUrl = imageUrl.replace("http", "https")
        } else {
            finalUrl = imageUrl
        }
        Glide
                .with(context)
                .load(finalUrl)
                .apply(RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .fitCenter()
                        .centerCrop())
                .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        if (facts != null)
            return facts.size
        return 0;
    }
}