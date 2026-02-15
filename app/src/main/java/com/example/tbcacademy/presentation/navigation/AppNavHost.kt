//package com.example.tbcacademy.presentation.navigation
//
//import android.widget.Toast
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.example.tbcacademy.domain.model.food.MealType
//import com.example.tbcacademy.presentation.screens.add_food.contract.AddFoodEvent
//import com.example.tbcacademy.presentation.screens.add_food.screen.AddFoodScreen
////import com.example.tbcacademy.presentation.screens.add_food.vm.AddFoodViewModel
//import com.example.tbcacademy.presentation.screens.dashboard.screen.DashboardScreen
//import com.example.tbcacademy.presentation.screens.diary.screen.DiaryScreen
//import com.example.tbcacademy.presentation.screens.diary.screen.FoodItem
//import com.example.tbcacademy.presentation.screens.diary.vm.DiaryViewModel
//import com.example.tbcacademy.presentation.screens.login.screen.LoginScreen
//import com.example.tbcacademy.presentation.screens.quiz.screen.QuizScreen
//import com.example.tbcacademy.presentation.screens.register.screen.RegisterScreen
//import com.example.tbcacademy.presentation.screens.welcome.screen.WelcomeScreen
//import com.example.tbcacademy.presentation.screens.welcome.vm.WelcomeViewModel
//
//@Composable
//fun AppNavHost(
//    navController: NavHostController,
//    modifier: Modifier = Modifier
//) {
//
//    val context = LocalContext.current
//
//    NavHost(
//        navController = navController,
//        startDestination = Routes.WELCOME,
//        modifier = modifier
//    ) {
//
//        composable(Routes.WELCOME) {
//            val viewModel: WelcomeViewModel = hiltViewModel()
//            WelcomeScreen(
//                viewModel = viewModel,
//                navController = navController
//            )
//        }
//
//        composable(Routes.LOGIN) {
//            LoginScreen(
//                viewModel = hiltViewModel(),
//                onNavigateHome = {
//                    navController.navigate("${Routes.DASHBOARD}/2000") {
//                        popUpTo(navController.graph.startDestinationId) {
//                            inclusive = true
//                        }
//                    }
//                },
//                onNavigateToRegister = {
//                    navController.navigate(Routes.REGISTER)
//                },
//                onShowError = { message ->
//                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                }
//            )
//        }
//
//        composable(Routes.REGISTER) {
//            RegisterScreen(
//                viewModel = hiltViewModel(),
//                onNavigateHome = {
//                    navController.navigate(Routes.QUIZ) {
//                        popUpTo(Routes.REGISTER) {
//                            inclusive = true
//                        }
//                    }
//                },
//                onNavigateToLogin = {
//                    navController.navigate(Routes.LOGIN)
//                },
//                onShowError = { message ->
//                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                }
//            )
//        }
//
//        composable(Routes.QUIZ) {
//            QuizScreen(
//                viewModel = hiltViewModel(),
//                navController = navController
//            )
//        }
//
//        composable(
//            route = "${Routes.DASHBOARD}/{calories}"
//        ) { backStackEntry ->
//
//            val calories = backStackEntry.arguments
//                ?.getString("calories")
//                ?.toIntOrNull() ?: 2000
//
//            DashboardScreen(
//                navController = navController,
//                goalCalories = calories,
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//
//       /* composable("add_food/{meal}") { backStack ->
//
//            val meal =
//                MealType.valueOf(backStack.arguments?.getString("meal")!!)
//
//            val viewModel: AddFoodViewModel = hiltViewModel()
//
//            AddFoodScreen(
//                state = viewModel.state,
//                onSearch = { query ->
//                    viewModel.onEvent(AddFoodEvent.Search(query))
//                },
//                onFoodSelected = { food ->
//                    viewModel.addFood(food, meal)
//                    navController.popBackStack()
//                }
//            )
//        }
//
//
//        composable(Routes.DIARY) {
//            val viewModel: DiaryViewModel = hiltViewModel()
//
//            DiaryScreen(
//                state = viewModel.state,
//                onAddFoodClick = { meal ->
//                    navController.navigate("add_food/${meal.name}")
//                }
//            ) { }
//        }*/
//
//    }
//}