package com.example.tbcacademy

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
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

    private fun setResources() = with(binding) {
        backgroundImage.setImageResource(R.drawable.background_image)
        backButtonIcon.setImageResource(R.drawable.arrow_icon)
        bookmarkButton.setImageResource(R.drawable.bookmark_icon)
        locationIcon.setImageResource(R.drawable.location_pin_icon)
        clockIcon.setImageResource(R.drawable.clock_icon)
        weatherIcon.setImageResource(R.drawable.weather_icon)
        starIcon.setImageResource(R.drawable.star_rating_icon)
        bookButtonIcon.setImageResource(R.drawable.button_icon)

        titleText.setText(R.string.andes_mountain)
        locationText.setText(R.string.south_america)
        priceText.setText(R.string.price)
        price.setText(R.string._230)
        dollarSymbol.setText(R.string.dollar_sign)
        overviewText.setText(R.string.overview)
        detailsText.setText(R.string.details)
        hoursText.setText(R.string._8_hours)
        weatherText.setText(R.string._16_c)
        starRatingText.setText(R.string._4_5)
        descriptionText.setText(R.string.large_text)
        bookNowButton.setText(R.string.book_now)
    }
}
