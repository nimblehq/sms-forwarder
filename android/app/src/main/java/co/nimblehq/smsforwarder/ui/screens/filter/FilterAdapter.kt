package co.nimblehq.smsforwarder.ui.screens.filter

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import co.nimblehq.smsforwarder.databinding.ItemFilterBinding
import co.nimblehq.smsforwarder.domain.data.Filter
import co.nimblehq.smsforwarder.extension.visibleOrGone
import co.nimblehq.smsforwarder.ui.common.ItemClickable
import co.nimblehq.smsforwarder.ui.common.ItemClickableImpl
import kotlinx.android.extensions.LayoutContainer

internal class FilterAdapter :
    RecyclerView.Adapter<FilterAdapter.ViewHolder>(),
    ItemClickable<FilterAdapter.OnItemClick> by ItemClickableImpl() {

    var items = listOf<Filter>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    internal inner class ViewHolder(
        private val binding: ItemFilterBinding
    ) : RecyclerView.ViewHolder(binding.root), LayoutContainer {

        override val containerView: View
            get() = itemView

        init {
            itemView.setOnClickListener {
                notifyItemClick(OnItemClick.Item(items[adapterPosition]))
            }
        }

        fun bind(model: Filter) {
            with(model) {
                with(binding) {
                    tvFilterSender.text = sender
                    tvFilterEmail.run {
                        text = forwardEmailAddress
                        visibleOrGone(forwardEmailAddress.isNotBlank())
                    }
                    tvFilterSlackChannel.run {
                        text = forwardSlackChannel
                        visibleOrGone(forwardSlackChannel.isNotBlank())
                    }
                }
            }
        }
    }

    sealed class OnItemClick {

        data class Item(val sms: Filter) : OnItemClick()
    }
}
