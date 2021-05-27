package co.nimblehq.smsforwarder.ui.screens.home

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import co.nimblehq.smsforwarder.databinding.ItemDataBinding
import co.nimblehq.smsforwarder.domain.data.Data
import co.nimblehq.smsforwarder.extension.loadImage
import co.nimblehq.smsforwarder.ui.common.ItemClickable
import co.nimblehq.smsforwarder.ui.common.ItemClickableImpl
import kotlinx.android.extensions.LayoutContainer

internal class DataAdapter :
    RecyclerView.Adapter<DataAdapter.ViewHolder>(),
    ItemClickable<DataAdapter.OnItemClick> by ItemClickableImpl() {

    var items = listOf<Data>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    internal inner class ViewHolder(
        private val binding: ItemDataBinding
    ) : RecyclerView.ViewHolder(binding.root), LayoutContainer {

        override val containerView: View
            get() = itemView

        init {
            itemView.setOnClickListener {
                notifyItemClick(OnItemClick.Item(items[adapterPosition]))
            }
        }

        fun bind(model: Data) {
            with(model) {
                with(binding) {
                    // TODO: update with the real data on the integrate task
                    tvSmsProvider.text = "Sms Provider Name"
                    tvSmsMessage.text = "Your OTP is 123456"
                }
            }
        }
    }

    sealed class OnItemClick {

        data class Item(val data: Data) : OnItemClick()
    }
}
