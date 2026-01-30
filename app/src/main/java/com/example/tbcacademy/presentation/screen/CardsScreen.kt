package com.example.tbcacademy.presentation.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tbcacademy.presentation.screen.feed.vm.FeedViewModel

@Composable
fun CardsScreen(
    navigator: NavController,
    viewModel: FeedViewModel = hiltViewModel()
) {

}