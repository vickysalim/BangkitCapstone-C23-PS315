package academy.bangkit.sifresh.ui.adapter

import academy.bangkit.sifresh.data.response.FreshHistoryData
import academy.bangkit.sifresh.databinding.DetectHistoryCardBinding
import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import com.bumptech.glide.Glide

class DetectHistoryAdapter(private val listDetect: List<FreshHistoryData>) :
    RecyclerView.Adapter<DetectHistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = DetectHistoryCardBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val detect = listDetect[position]
        viewHolder.bind(detect)
    }

    override fun getItemCount() = listDetect.size

    class ViewHolder(var binding: DetectHistoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isExpanded = false
        private val originalHeight: Int = convertDpToPixels(itemView.context, 130)
        private val expandedHeight: Int = convertDpToPixels(itemView.context, 250)

        init {
            binding.cardContainer.setOnClickListener {
                isExpanded = !isExpanded
                animateHeightChange()
            }
        }

        fun bind(order: FreshHistoryData) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(order.pictureUrl)
                    .into(ivItemPhoto)
                ivItemPhoto.layoutParams.height = if (isExpanded) expandedHeight else originalHeight
                gradientEffect.layoutParams.height =
                    if (isExpanded) expandedHeight else originalHeight
                ivItemPhoto.requestLayout()
                gradientEffect.requestLayout()

                tvItemName.text = order.name
                tvFreshStatus.text = if (order.isFresh == 1) "Fresh" else "Not Fresh"

                layout.visibility = if (isExpanded) View.VISIBLE else View.GONE
            }
        }

        private fun animateHeightChange() {
            val transition = ChangeBounds()
            transition.duration = 300

            val transitionListener = object : Transition.TransitionListener {
                override fun onTransitionStart(transition: Transition) {}
                override fun onTransitionEnd(transition: Transition) {
                    binding.layout.visibility = if (isExpanded) View.VISIBLE else View.GONE
                    transition.removeListener(this)
                }

                override fun onTransitionCancel(transition: Transition) {}
                override fun onTransitionPause(transition: Transition) {}
                override fun onTransitionResume(transition: Transition) {}
            }

            transition.addListener(transitionListener)

            val targetHeight = if (isExpanded) expandedHeight else originalHeight
            val animator = ValueAnimator.ofInt(binding.ivItemPhoto.height, targetHeight)
            animator.addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Int
                binding.ivItemPhoto.layoutParams.height = value
                binding.gradientEffect.layoutParams.height = value
                binding.ivItemPhoto.requestLayout()
                binding.gradientEffect.requestLayout()
            }
            animator.duration = 300
            animator.start()

            binding.layout.visibility = if (isExpanded) View.VISIBLE else View.GONE
        }

        private fun convertDpToPixels(context: Context, dp: Int): Int {
            val density = context.resources.displayMetrics.density
            return (dp * density).toInt()
        }
    }
}