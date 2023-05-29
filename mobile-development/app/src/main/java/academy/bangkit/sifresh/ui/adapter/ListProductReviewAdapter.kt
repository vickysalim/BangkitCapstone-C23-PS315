package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.R
import academy.bangkit.sifresh.data.response.Review
import academy.bangkit.sifresh.databinding.ReviewCardBinding
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ListProductReviewAdapter(private val listItem: List<Review>) :
    RecyclerView.Adapter<ListProductReviewAdapter.ViewHolder>() {

    class ViewHolder(var binding: ReviewCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ReviewCardBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = listItem[position]

        viewHolder.binding.apply {
            tvName.text = item.username
            tvReview.text = item.review

            star1.imageTintList =
                ColorStateList.valueOf(viewHolder.itemView.context.getColor(R.color.yellow))
            if (item.rating >= 2) star2.imageTintList =
                ColorStateList.valueOf(viewHolder.itemView.context.getColor(R.color.yellow))
            if (item.rating >= 3) star3.imageTintList =
                ColorStateList.valueOf(viewHolder.itemView.context.getColor(R.color.yellow))
            if (item.rating >= 4) star4.imageTintList =
                ColorStateList.valueOf(viewHolder.itemView.context.getColor(R.color.yellow))
            if (item.rating == 5) star5.imageTintList =
                ColorStateList.valueOf(viewHolder.itemView.context.getColor(R.color.yellow))

        }
    }

    override fun getItemCount() = listItem.size
}