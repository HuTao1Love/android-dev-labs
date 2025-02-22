package ru.hutao.shop.presentation.productDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ru.hutao.shop.data.models.Product
import ru.hutao.shop.databinding.ActivityProductDetailBinding
import ru.hutao.shop.presentation.main.MainActivity
import java.text.DateFormat
import java.text.SimpleDateFormat

class ProductDetailActivity : AppCompatActivity() {
    private var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()

    private lateinit var binding: ActivityProductDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getSerializableExtra("product", Product::class.java)!!

        product.let {
            binding.productName.text = it.name
            binding.productDescription.text = it.description
            binding.productPrice.text = "${it.price} ₽"
            binding.productCategory.text = "Категория: ${it.category.name}"
            binding.productCreatedAt.text = "Создано: ${dateFormat.format(it.createdAt)}"

            Glide
                .with(this)
                .load(it.image)
                .into(binding.productImage)

            binding.productCategory.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java).apply {
                    putExtra("searchQuery", "category:${product.category.name}")
                })
            }
        }
    }
}