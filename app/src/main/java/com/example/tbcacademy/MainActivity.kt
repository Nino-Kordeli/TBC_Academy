package com.example.tbcacademy

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcacademy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        setResources()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setText(textView: TextView, @StringRes stringRes: Int) {
        textView.text = getString(stringRes)
    }

    private fun setImage(imageView: ImageView, @DrawableRes drawableRes: Int) {
        imageView.setImageResource(drawableRes)
    }

    private fun setResources() = with(binding) {
        setImage(backgroundImage, R.drawable.background_image)
        setImage(backButtonIcon, R.drawable.arrow_icon)
        setImage(bookmarkButton, R.drawable.bookmark_icon)
        setImage(locationIcon, R.drawable.location_pin_icon)
        setImage(clockIcon, R.drawable.clock_icon)
        setImage(weatherIcon, R.drawable.weather_icon)
        setImage(starIcon, R.drawable.star_rating_icon)
        setImage(bookButtonIcon, R.drawable.button_icon)

        setText(titleText, R.string.andes_mountain)
        setText(locationText, R.string.south_america)
        setText(priceText, R.string.price)
        setText(price, R.string._230)
        setText(dollarSymbol, R.string.dollar_sign)
        setText(overviewText, R.string.overview)
        setText(detailsText, R.string.details)
        setText(hoursText, R.string._8_hours)
        setText(weatherText, R.string._16_c)
        setText(starRatingText, R.string._4_5)
        setText(descriptionText, R.string.large_text)
        setText(bookNowButton, R.string.book_now)
    }
}