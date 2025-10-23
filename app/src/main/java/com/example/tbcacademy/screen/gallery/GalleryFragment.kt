package com.example.tbcacademy.screen.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentGalleryBinding
import com.example.tbcacademy.screen.gallery.adapter.CategoryAdapter
import com.example.tbcacademy.screen.gallery.adapter.GalleryAdapter
import com.example.tbcacademy.screen.gallery.adapter.model.CategoryItem
import com.example.tbcacademy.screen.gallery.adapter.model.GalleryItem

class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {

    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    private val allItems = listOf(
        GalleryItem("Belt suit blazer", "$120", R.drawable.girl1, "party"),
        GalleryItem("Summer Dress", "$80", R.drawable.girl2, "party"),
        GalleryItem("Tent", "$90", R.drawable.girl3, "camping"),
        GalleryItem("Backpack", "$100", R.drawable.girl4, "camping"),
        GalleryItem("Belt suit blazer", "$120", R.drawable.girl1, "category3"),
        GalleryItem("Summer Dress", "$80", R.drawable.girl2, "category1"),
        GalleryItem("Tent", "$90", R.drawable.girl3, "category2"),
        GalleryItem("Backpack", "$100", R.drawable.girl4, "category3"),
    )

    private val categories = listOf(
        CategoryItem(null, "All"),
        CategoryItem("\uD83C\uDF89", "party"),
        CategoryItem("\u26FA", "camping"),
        CategoryItem(null, "Category1"),
        CategoryItem(null, "Category2"),
        CategoryItem(null, "Category3"),
    )

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentGalleryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryRecycler()
        setupGalleryRecycler()
        submitInitialLists()
    }

    private fun setupCategoryRecycler() {
        categoryAdapter = CategoryAdapter { categoryName ->
            val filtered = if (categoryName.equals("All", ignoreCase = true)) {
                allItems
            } else {
                allItems.filter { it.category.equals(categoryName, ignoreCase = true) }
            }
            galleryAdapter.submitList(filtered)
        }

        binding.rvCategories.apply {
            adapter = categoryAdapter
        }
    }

    private fun setupGalleryRecycler() {
        galleryAdapter = GalleryAdapter()
        binding.rvItems.apply {
            adapter = galleryAdapter
        }
    }

    private fun submitInitialLists() {
        categoryAdapter.submitList(categories)
        galleryAdapter.submitList(allItems)
    }
}