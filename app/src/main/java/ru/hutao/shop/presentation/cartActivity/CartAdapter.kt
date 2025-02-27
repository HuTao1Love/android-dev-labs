package ru.hutao.shop.presentation.cartActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.hutao.shop.R
import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.presentation.productDetailActivity.ProductDetailActivity

class CartAdapter(val listener: ICartItemChangedListener)
    : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val cartItems = mutableListOf<CartItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newCartItems: List<CartItem>) {
        cartItems.clear()
        cartItems.addAll(newCartItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productImage: ImageView = itemView.findViewById(R.id.productImage)
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        private val productQuantity: TextView = itemView.findViewById(R.id.productQuantity)
        private val removeButton: ImageView = itemView.findViewById(R.id.removeButton)
        private val addItemButton: Button = itemView.findViewById(R.id.increaseQuantityButton)
        private val removeItemButton: Button = itemView.findViewById(R.id.decreaseQuantityButton)

        @SuppressLint("SetTextI18n")
        fun bind(cartItem: CartItem) {
            productName.text = cartItem.product.name
            productPrice.text = "${cartItem.product.price} ₽"
            productQuantity.text = "Количество: ${cartItem.quantity}"

            Glide
                .with(itemView.context)
                .load(cartItem.product.image)
                .into(productImage)

            itemView.setOnClickListener {
                val context = it.context
                val intent = Intent(context, ProductDetailActivity::class.java).apply {
                    putExtra("product", cartItem.product)
                }
                context.startActivity(intent)
            }

            removeButton.setOnClickListener {
                listener.onRemoveClicked(cartItem)
            }
            removeItemButton.setOnClickListener {
                listener.onDecreaseClicked(cartItem)
            }
            addItemButton.setOnClickListener {
                listener.onIncreaseClicked(cartItem)
            }
        }
    }
}