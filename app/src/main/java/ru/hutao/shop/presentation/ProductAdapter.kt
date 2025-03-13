package ru.hutao.shop.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.hutao.shop.R
import ru.hutao.shop.data.models.Product
import ru.hutao.shop.presentation.productDetailActivity.ProductDetailActivity

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val products = mutableListOf<Product>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.productImage)
        private val titleTextView: TextView = itemView.findViewById(R.id.productTitle)
        private val priceTextView: TextView = itemView.findViewById(R.id.productPrice)

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            titleTextView.text = product.name
            priceTextView.text = "${product.price} ₽"
            Glide
                .with(itemView.context)
                .load(product.image)
                .into(imageView)

            itemView.setOnClickListener {
                val context = it.context
                val intent = Intent(context, ProductDetailActivity::class.java).apply {
                    putExtra("product", product)
                }
                context.startActivity(intent)
            }
        }
    }
}