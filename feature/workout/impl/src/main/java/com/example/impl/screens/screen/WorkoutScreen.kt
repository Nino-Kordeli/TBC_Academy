package com.example.impl.screens.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.navigation.Navigator
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.PrimaryBlue
import com.example.designsystem.theme.White
import com.example.domain.model.workout.Exercise
import com.example.domain.model.workout.WorkoutCategory
import com.example.impl.screens.contract.WorkoutEvent
import com.example.impl.screens.contract.WorkoutSideEffect
import com.example.impl.screens.vm.WorkoutViewModel
import com.example.ui.base.BaseScreen

@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    BaseScreen(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
        onSideEffect = { effect ->
            when (effect) {
                is WorkoutSideEffect.ShowToast ->
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    ) { state, onEvent ->

        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PrimaryBlue)
            }
            return@BaseScreen
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, start = 25.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Exercises",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            state.categories.forEach { category ->
                WorkoutCategorySection(
                    category = category,
                    loggedIds = state.loggedExerciseIds,
                    onLogExercise = { exercise ->
                        onEvent(WorkoutEvent.LogExercise(exercise.id, exercise.caloriesBurned))
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun WorkoutCategorySection(
    category: WorkoutCategory,
    loggedIds: Set<String>,
    onLogExercise: (Exercise) -> Unit
) {
    Column {
        Text(
            text = category.title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 22.dp, top = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = category.description,
            fontSize = 14.sp,
            fontStyle = FontStyle.Normal,
            color = LightGray,
            modifier = Modifier.padding(start = 22.dp, end = 80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(start = 22.dp, end = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(category.exercises) { exercise ->
                ExerciseCard(
                    exercise = exercise,
                    isLogged = exercise.id in loggedIds,
                    onLogClick = { onLogExercise(exercise) },
                    modifier = Modifier.width(280.dp)
                )
            }
        }
    }
}

@Composable
private fun ExerciseCard(
    exercise: Exercise,
    isLogged: Boolean,
    onLogClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier.height(270.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.fillMaxSize()) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(exercise.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = exercise.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.LightGray)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(White)
                        .padding(
                            top = 14.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 14.dp
                        )
                ) {
                    Column {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = exercise.duration,
                                color = LightGray,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                text = "  |  ",
                                color = LightGray,
                                fontSize = 13.sp
                            )

                            Text(
                                text = exercise.equipment,
                                color = LightGray,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = exercise.title,
                            color = Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = exercise.description,
                            color = LightGray,
                            fontSize = 12.sp,
                            maxLines = 2
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(36.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .background(
                        if (isLogged)
                            PrimaryBlue.copy(alpha = 0.85f)
                        else
                            Color.Black.copy(alpha = 0.35f)
                    )
                    .clickable { onLogClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Log exercise",
                    tint = White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}