package ru.hutao.shop.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import ru.hutao.shop.R
import ru.hutao.shop.data.models.Product

class ProductView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val productView: ProductView = findViewById(R.id.productView)

    init {
        radius = 8f
        cardElevation = 4f
        useCompatPadding = true
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(8, 8, 8, 8) }
    }

    fun setProduct(product: Product) {
        productView.setProduct(product)
    }
}
