package ru.hutao.shop.presentation.cartActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.provider.Settings.Secure;
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.hutao.shop.R
import ru.hutao.shop.data.models.CartItem
import ru.hutao.shop.data.repositories.RetrofitInstance

class CartActivity : ComponentActivity(), ICartItemChangedListener {
    private lateinit var viewModel: CartModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var buyButton: Button
    private lateinit var adapter: CartAdapter

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.recyclerView)
        buyButton = findViewById(R.id.buy)
        adapter = CartAdapter(this)

        recyclerView.adapter = adapter

        viewModel = CartModel(Secure.getString(this.contentResolver, Secure.ANDROID_ID), RetrofitInstance.cartRepository)

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is CartState.Loading -> showLoading()
                    is CartState.Success -> showCart(state.items)
                    is CartState.Error -> showError(state.message)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.processIntent(CartIntent.LoadCart)
        }

        buyButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.processIntent(CartIntent.ClearCart)
            }

            Toast.makeText(this, "You bought it. Shop cart was cleared", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            viewModel.processIntent(CartIntent.LoadCart)
        }
    }

    private fun showLoading() { }

    private fun showCart(items: List<CartItem>) {
        recyclerView.visibility = View.VISIBLE
        Log.e("TEST", "HERE")
        adapter.submitList(items)
    }

    private fun showError(message: String) {
        recyclerView.visibility = View.GONE
        Toast.makeText(this, "Ошибка: $message", Toast.LENGTH_LONG).show()
    }

    override fun onRemoveClicked(cartItem: CartItem) {
        lifecycleScope.launch {
            viewModel.processIntent(CartIntent.RemoveFromCart(cartItem))
        }
    }

    override fun onDecreaseClicked(cartItem: CartItem) {
        lifecycleScope.launch {
            viewModel.processIntent(
                CartIntent.UpdateCartItemQuantity(cartItem.product, -1))
        }
    }

    override fun onIncreaseClicked(cartItem: CartItem) {
        lifecycleScope.launch {
            viewModel.processIntent(
                CartIntent.UpdateCartItemQuantity(cartItem.product, +1))
        }
    }
}