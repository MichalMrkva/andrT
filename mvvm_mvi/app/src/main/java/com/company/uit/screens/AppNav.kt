package com.company.uit.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.company.uit.screens.home.HomeScreen
import com.company.uit.screens.mvi.MviScreen
import com.company.uit.screens.mvvm.MvvmScreen

@Composable
fun AppNavigation() {
	val navController = rememberNavController()
	NavHost(
		navController,
		startDestination = Screens.Home
	) {
		composable<Screens.Mvi> {
			MviScreen(navController)
		}
		composable<Screens.Home> {
			HomeScreen(navController)
		}
		composable<Screens.Mvvm> {
			MvvmScreen(navController)
		}
	}
}